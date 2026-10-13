package net.sergofoox.versecraft.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sergofoox.versecraft.block.entity.TuffGolemBlockEntity;
import net.sergofoox.versecraft.entity.ai.TuffGolemReturnHomeGoal;
import net.sergofoox.versecraft.entity.ai.TuffGolemSearchItemGoal;
import net.sergofoox.versecraft.entity.ai.TuffGolemWanderNearHomeGoal;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TuffGolem extends AbstractGolem {

    public static final byte STATE_IDLE = 0;
    public static final byte STATE_SLEEP_START = 1;
    public static final byte STATE_SLEEP_OVER = 2;

    private static final EntityDataAccessor<Byte> DATA_ANIM_STATE =
            SynchedEntityData.defineId(TuffGolem.class, EntityDataSerializers.BYTE);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sleepStartAnimationState = new AnimationState();
    public final AnimationState sleepOverAnimationState = new AnimationState();

    private int animTicks = 0;
    private int awakeTicks = 0;

    @Nullable
    private BlockPos originPos;
    private Direction originFacing = Direction.NORTH;

    public TuffGolem(EntityType<? extends TuffGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ANIM_STATE, STATE_IDLE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.STEP_HEIGHT, 0.6D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TuffGolemReturnHomeGoal(this, 1.05D));
        this.goalSelector.addGoal(2, new TuffGolemSearchItemGoal(this, 1.15D));
        this.goalSelector.addGoal(3, new TuffGolemWanderNearHomeGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return !TuffGolem.this.isBusyWithAnim() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !TuffGolem.this.isBusyWithAnim() && super.canContinueToUse();
            }
        });
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND && stack.getCount() > 1) {
            stack = stack.copyWithCount(1);
        }
        super.setItemSlot(slot, stack);
    }

    public boolean isSleepingAnim() {
        return this.entityData.get(DATA_ANIM_STATE) == STATE_SLEEP_START;
    }

    public boolean isWakingUpAnim() {
        return this.entityData.get(DATA_ANIM_STATE) == STATE_SLEEP_OVER;
    }

    public boolean isBusyWithAnim() {
        byte s = this.entityData.get(DATA_ANIM_STATE);
        return s == STATE_SLEEP_START || s == STATE_SLEEP_OVER;
    }

    public boolean wantsToSleep() {
        return !this.getMainHandItem().isEmpty() || this.awakeTicks >= 600;
    }

    public boolean shouldYieldToDeeperGolem() {
        if (this.originPos == null) {
            return false;
        }

        AABB searchBox = this.getBoundingBox().inflate(24.0D, 8.0D, 24.0D);
        List<TuffGolem> nearby = this.level().getEntitiesOfClass(
                TuffGolem.class,
                searchBox,
                other -> other != this
                        && other.isAlive()
                        && !other.isBusyWithAnim()
                        && other.wantsToSleep()
                        && other.getOriginPos() != null
                        && !other.getOriginPos().equals(this.originPos)
        );

        if (nearby.isEmpty()) {
            return false;
        }

        for (TuffGolem other : nearby) {
            BlockPos otherHome = other.getOriginPos();
            if (otherHome == null) {
                continue;
            }

            Path path = other.getNavigation().createPath(otherHome, 0);
            if (path != null) {
                for (int i = 0; i < path.getNodeCount(); i++) {
                    BlockPos nodePos = path.getNodePos(i);
                    if (nodePos.equals(this.originPos) || nodePos.closerThan(this.originPos, 1.25D)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void startSleeping() {
        if (!this.level().isClientSide()) {
            this.entityData.set(DATA_ANIM_STATE, STATE_SLEEP_START);
            this.animTicks = 23;
            this.awakeTicks = 0;
            this.getNavigation().stop();
            this.setDeltaMovement(Vec3.ZERO);
        }
    }

    public void startWakingUp() {
        if (!this.level().isClientSide()) {
            this.entityData.set(DATA_ANIM_STATE, STATE_SLEEP_OVER);
            this.animTicks = 24;
            this.awakeTicks = 0;
            this.getNavigation().stop();
            this.setDeltaMovement(Vec3.ZERO);
            if (this.originFacing != null) {
                this.setYRot(this.originFacing.toYRot());
                this.setYHeadRot(this.originFacing.toYRot());
                this.setYBodyRot(this.originFacing.toYRot());
            }
        }
    }

    @Override
    public void push(Entity entity) {
        if (this.isBusyWithAnim() || entity instanceof TuffGolem) {
            return;
        }
        super.push(entity);
    }

    @Override
    protected void doPush(Entity entity) {
        if (this.isBusyWithAnim() || entity instanceof TuffGolem) {
            return;
        }
        super.doPush(entity);
    }

    @Override
    public boolean isPushable() {
        return !this.isBusyWithAnim() && super.isPushable();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            byte state = this.entityData.get(DATA_ANIM_STATE);
            if (state == STATE_SLEEP_START) {
                this.idleAnimationState.stop();
                this.walkingAnimationState.stop();
                this.sleepOverAnimationState.stop();
                this.sleepStartAnimationState.startIfStopped(this.tickCount);
            } else if (state == STATE_SLEEP_OVER) {
                this.idleAnimationState.stop();
                this.walkingAnimationState.stop();
                this.sleepStartAnimationState.stop();
                this.sleepOverAnimationState.startIfStopped(this.tickCount);
            } else {
                this.sleepStartAnimationState.stop();
                this.sleepOverAnimationState.stop();
                this.idleAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            if (this.originPos == null) {
                this.originPos = this.blockPosition();
                this.originFacing = this.getDirection();
            }

            byte state = this.entityData.get(DATA_ANIM_STATE);

            if (state == STATE_SLEEP_START) {
                this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
                if (this.originPos != null) {
                    double targetX = this.originPos.getX() + 0.5D;
                    double targetZ = this.originPos.getZ() + 0.5D;
                    double curX = this.getX();
                    double curZ = this.getZ();
                    this.setPos(curX + (targetX - curX) * 0.25D, this.getY(), curZ + (targetZ - curZ) * 0.25D);
                }
                if (this.originFacing != null) {
                    this.setYRot(this.originFacing.toYRot());
                    this.setYHeadRot(this.originFacing.toYRot());
                    this.setYBodyRot(this.originFacing.toYRot());
                }
                this.animTicks--;
                if (this.animTicks <= 0) {
                    BlockPos home = this.originPos != null ? this.originPos : this.blockPosition();
                    if (!this.level().getBlockState(home).canBeReplaced()) {
                        for (Direction dir : Direction.Plane.HORIZONTAL) {
                            BlockPos adjacent = home.relative(dir);
                            if (this.level().getBlockState(adjacent).canBeReplaced() && !this.level().getBlockState(adjacent.below()).isAir()) {
                                home = adjacent;
                                break;
                            }
                        }
                    }
                    Direction facing = this.originFacing != null ? this.originFacing : this.getDirection();
                    BlockState statueState = RegisterBlocks.TUFF_GOLEM_BLOCK.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, facing);
                    this.level().setBlock(home, statueState, Block.UPDATE_ALL);

                    if (this.level().getBlockEntity(home) instanceof TuffGolemBlockEntity be) {
                        be.setItem(this.getMainHandItem().copy());
                    }

                    this.level().playSound(null, home, SoundEvents.TUFF_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    this.discard();
                    return;
                }
            } else if (state == STATE_SLEEP_OVER) {
                this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
                if (this.originFacing != null) {
                    this.setYRot(this.originFacing.toYRot());
                    this.setYHeadRot(this.originFacing.toYRot());
                    this.setYBodyRot(this.originFacing.toYRot());
                }
                this.animTicks--;
                if (this.animTicks <= 0) {
                    this.entityData.set(DATA_ANIM_STATE, STATE_IDLE);
                }
            } else {
                this.awakeTicks++;

                if (this.getMainHandItem().isEmpty()) {
                    List<ItemEntity> nearby = this.level().getEntitiesOfClass(
                            ItemEntity.class,
                            this.getBoundingBox().inflate(1.5D, 1.0D, 1.5D),
                            item -> item.isAlive() && !item.getItem().isEmpty()
                    );
                    if (!nearby.isEmpty()) {
                        ItemEntity item = nearby.get(0);
                        ItemStack singleItem = item.getItem().split(1);
                        this.setItemSlot(EquipmentSlot.MAINHAND, singleItem);
                        if (item.getItem().isEmpty()) {
                            item.discard();
                        }
                        this.playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.0F);
                    }
                }
            }
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack playerItem = player.getItemInHand(hand);
        ItemStack golemItem = this.getMainHandItem();

        if (golemItem.isEmpty() && !playerItem.isEmpty()) {
            if (this.isBusyWithAnim()) {
                return InteractionResult.PASS;
            }
            if (!this.level().isClientSide()) {
                ItemStack given = playerItem.split(1);
                this.setItemSlot(EquipmentSlot.MAINHAND, given);
                this.playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        } else if (!golemItem.isEmpty() && playerItem.isEmpty()) {
            if (!this.level().isClientSide()) {
                if (!player.getInventory().add(golemItem.copy())) {
                    Containers.dropItemStack(this.level(), player.getX(), player.getY(), player.getZ(), golemItem.copy());
                }
                this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                this.playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.0F);
                if (this.isSleepingAnim()) {
                    this.startWakingUp();
                }
            }
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Nullable
    public BlockPos getOriginPos() {
        return this.originPos;
    }

    public void setOriginPos(@Nullable BlockPos pos) {
        this.originPos = pos;
    }

    public Direction getOriginFacing() {
        return this.originFacing;
    }

    public void setOriginFacing(Direction facing) {
        this.originFacing = facing != null ? facing : Direction.NORTH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.TUFF_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.TUFF_BREAK;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.TUFF_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        if (!this.getMainHandItem().isEmpty()) {
            this.spawnAtLocation(level, this.getMainHandItem());
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (this.originPos != null) {
            output.putInt("origin_x", this.originPos.getX());
            output.putInt("origin_y", this.originPos.getY());
            output.putInt("origin_z", this.originPos.getZ());
        }
        output.putString("origin_facing", this.originFacing.getName());
        output.putByte("anim_state", this.entityData.get(DATA_ANIM_STATE));
        output.putInt("anim_ticks", this.animTicks);
        output.putInt("awake_ticks", this.awakeTicks);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        if (input.contains("origin_x") && input.contains("origin_y") && input.contains("origin_z")) {
            this.originPos = new BlockPos(
                    input.getIntOr("origin_x", 0),
                    input.getIntOr("origin_y", 0),
                    input.getIntOr("origin_z", 0)
            );
        }
        Direction facing = Direction.byName(input.getStringOr("origin_facing", "north"));
        this.originFacing = facing != null ? facing : Direction.NORTH;
        this.entityData.set(DATA_ANIM_STATE, input.getByteOr("anim_state", STATE_IDLE));
        this.animTicks = input.getIntOr("anim_ticks", 0);
        this.awakeTicks = input.getIntOr("awake_ticks", 0);
    }
}
