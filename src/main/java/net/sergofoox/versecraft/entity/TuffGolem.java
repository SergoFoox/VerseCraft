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
import net.sergofoox.versecraft.block.entity.TuffGolemStatueBlockEntity;
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
    private static final EntityDataAccessor<Direction> DATA_ORIGIN_FACING =
            SynchedEntityData.defineId(TuffGolem.class, EntityDataSerializers.DIRECTION);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sleepStartAnimationState = new AnimationState();
    public final AnimationState sleepOverAnimationState = new AnimationState();

    private int animTicks = 0;
    private int awakeTicks = 0;
    private boolean targetingItem = false;
    @Nullable
    private ItemEntity targetedItem = null;

    @Nullable
    private BlockPos originPos = null;

    public TuffGolem(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ANIM_STATE, STATE_IDLE);
        builder.define(DATA_ORIGIN_FACING, Direction.NORTH);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.STEP_HEIGHT, 1.0D)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER, 0.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TuffGolemSearchItemGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new TuffGolemReturnHomeGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TuffGolemWanderNearHomeGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public boolean isBusyWithAnim() {
        byte state = this.entityData.get(DATA_ANIM_STATE);
        return state == STATE_SLEEP_START || state == STATE_SLEEP_OVER;
    }

    public boolean isSleepingAnim() {
        return this.entityData.get(DATA_ANIM_STATE) == STATE_SLEEP_START;
    }

    public void startGoingToSleep() {
        this.entityData.set(DATA_ANIM_STATE, STATE_SLEEP_START);
        this.animTicks = 40;
        this.getNavigation().stop();
        this.setDeltaMovement(Vec3.ZERO);
        if (this.originPos != null) {
            this.setPos(this.originPos.getX() + 0.5D, this.getY(), this.originPos.getZ() + 0.5D);
        }
        Direction facing = this.getOriginFacing();
        if (facing != null) {
            this.setYRot(facing.toYRot());
            this.setYHeadRot(facing.toYRot());
            this.setYBodyRot(facing.toYRot());
        }
        if (!this.level().isClientSide()) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.TUFF_STEP, SoundSource.NEUTRAL, 1.0F, 0.8F);
        }
    }

    public void startWakingUp() {
        this.entityData.set(DATA_ANIM_STATE, STATE_SLEEP_OVER);
        this.animTicks = 40;
        this.awakeTicks = 0;
        this.getNavigation().stop();
        this.setDeltaMovement(Vec3.ZERO);
        Direction facing = this.getOriginFacing();
        if (facing != null) {
            this.setYRot(facing.toYRot());
            this.setYHeadRot(facing.toYRot());
            this.setYBodyRot(facing.toYRot());
        }
        if (!this.level().isClientSide()) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.TUFF_STEP, SoundSource.NEUTRAL, 1.0F, 1.2F);
        }
    }

    public boolean isTargetingItem() {
        return this.targetingItem;
    }

    public void setTargetingItem(boolean targeting) {
        this.targetingItem = targeting;
        if (!targeting) {
            this.targetedItem = null;
        }
    }

    @Nullable
    public ItemEntity getTargetedItem() {
        return this.targetedItem;
    }

    public void setTargetedItem(@Nullable ItemEntity item) {
        this.targetedItem = item;
    }

    public int getAwakeTicks() {
        return this.awakeTicks;
    }

    public boolean hasNearbyItemToCollect() {
        if (!this.isAlive() || this.isDeadOrDying() || !this.getMainHandItem().isEmpty()) {
            return false;
        }
        AABB box = this.getBoundingBox().inflate(16.0D, 6.0D, 16.0D);
        List<ItemEntity> items = this.level().getEntitiesOfClass(
                ItemEntity.class,
                box,
                item -> item.isAlive() && !item.getItem().isEmpty()
        );
        return !items.isEmpty();
    }

    public boolean wantsToSleep() {
        if (!this.getMainHandItem().isEmpty()) {
            return true;
        }
        return !this.targetingItem && this.awakeTicks >= 600 && !this.hasNearbyItemToCollect();
    }

    public boolean shouldYieldToDeeperGolem() {
        if (this.originPos == null) {
            return false;
        }

        AABB searchBox = this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D);
        List<TuffGolem> others = this.level().getEntitiesOfClass(
                TuffGolem.class,
                searchBox,
                other -> other != this
                        && other.isAlive()
                        && !other.isBusyWithAnim()
                        && other.wantsToSleep()
                        && other.getOriginPos() != null
                        && !other.getOriginPos().equals(this.originPos)
        );

        if (others.isEmpty()) {
            return false;
        }

        for (TuffGolem other : others) {
            BlockPos otherHome = other.getOriginPos();
            if (otherHome == null) {
                continue;
            }

            double otherDistToHomeSq = other.distanceToSqr(Vec3.atBottomCenterOf(otherHome));
            if (otherDistToHomeSq <= 0.36D) {
                continue;
            }

            Path path = other.getNavigation().getPath();
            if (path == null) {
                continue;
            }

            for (int i = path.getNextNodeIndex(); i < path.getNodeCount(); i++) {
                BlockPos nodePos = path.getNode(i).asBlockPos();
                if (nodePos.distSqr(this.originPos) <= 2.25D) {
                    double myDistToOtherHomeSq = this.distanceToSqr(Vec3.atBottomCenterOf(otherHome));
                    if (otherDistToHomeSq > myDistToOtherHomeSq) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isHomeBlockedByOtherGolem() {
        if (this.originPos == null) {
            return false;
        }
        AABB homeBox = new AABB(this.originPos).inflate(0.5D);
        List<TuffGolem> atHome = this.level().getEntitiesOfClass(
                TuffGolem.class,
                homeBox,
                g -> g != this && g.isAlive()
        );
        return !atHome.isEmpty();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (this.isSleepingAnim()) {
            this.startWakingUp();
        }
        return super.hurtServer(level, source, amount);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isBusyWithAnim()) {
            this.getNavigation().stop();
            this.setDeltaMovement(Vec3.ZERO);
            Direction facing = this.getOriginFacing();
            if (facing != null) {
                this.setYRot(facing.toYRot());
                this.setYHeadRot(facing.toYRot());
                this.setYBodyRot(facing.toYRot());
            }
        }
    }

    @Override
    public void push(Entity entity) {
        if (this.isBusyWithAnim() || (entity instanceof TuffGolem other && other.isBusyWithAnim())) {
            return;
        }
        super.push(entity);
    }

    @Override
    protected void doPush(Entity entity) {
        if (this.isBusyWithAnim() || (entity instanceof TuffGolem other && other.isBusyWithAnim())) {
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

        if (this.isDeadOrDying() || !this.isAlive()) {
            return;
        }

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
                this.setOriginFacing(this.getDirection());
            }

            byte state = this.entityData.get(DATA_ANIM_STATE);

            if (state == STATE_SLEEP_START) {
                this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
                Direction facing = this.getOriginFacing();
                if (facing != null) {
                    this.setYRot(facing.toYRot());
                    this.setYHeadRot(facing.toYRot());
                    this.setYBodyRot(facing.toYRot());
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
                    Direction statueFacing = facing != null ? facing : this.getDirection();
                    BlockState statueState = RegisterBlocks.TUFF_GOLEM_STATUE.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, statueFacing);
                    this.level().setBlock(home, statueState, Block.UPDATE_ALL);

                    if (this.level().getBlockEntity(home) instanceof TuffGolemStatueBlockEntity be) {
                        be.setItem(this.getMainHandItem().copy());
                    }

                    this.level().playSound(null, home, SoundEvents.TUFF_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    this.discard();
                    return;
                }
            } else if (state == STATE_SLEEP_OVER) {
                this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
                Direction facing = this.getOriginFacing();
                if (facing != null) {
                    this.setYRot(facing.toYRot());
                    this.setYHeadRot(facing.toYRot());
                    this.setYBodyRot(facing.toYRot());
                }
                this.animTicks--;
                if (this.animTicks <= 0) {
                    this.entityData.set(DATA_ANIM_STATE, STATE_IDLE);
                }
            } else {
                if (this.targetingItem) {
                    this.awakeTicks = 0;
                } else {
                    this.awakeTicks++;
                }

                if (this.getMainHandItem().isEmpty()) {
                    List<ItemEntity> nearby = this.level().getEntitiesOfClass(
                            ItemEntity.class,
                            this.getBoundingBox().inflate(1.0D, 0.5D, 1.0D),
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
    public void die(DamageSource damageSource) {
        this.targetingItem = false;
        this.targetedItem = null;
        super.die(damageSource);
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if ((!this.isAlive() || this.isDeadOrDying()) && !stack.isEmpty()) {
            return;
        }
        if (slot == EquipmentSlot.MAINHAND && stack.getCount() > 1) {
            stack = stack.copyWithCount(1);
        }
        super.setItemSlot(slot, stack);
    }

    @Override
    public boolean wantsToPickUp(ServerLevel level, ItemStack stack) {
        if (!this.isAlive() || this.isDeadOrDying() || this.isBusyWithAnim() || !this.getMainHandItem().isEmpty()) {
            return false;
        }
        return super.wantsToPickUp(level, stack);
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        if (!this.isAlive() || this.isDeadOrDying()) {
            return false;
        }
        return super.canHoldItem(stack);
    }

    @Override
    protected void pickUpItem(ServerLevel level, ItemEntity itemEntity) {
        if (!this.isAlive() || this.isDeadOrDying() || this.isBusyWithAnim()) {
            return;
        }
        super.pickUpItem(level, itemEntity);
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
        return this.entityData.get(DATA_ORIGIN_FACING);
    }

    public void setOriginFacing(Direction facing) {
        this.entityData.set(DATA_ORIGIN_FACING, facing != null ? facing : Direction.NORTH);
    }

    @Override
    protected int decreaseAirSupply(int air) {
        return air;
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
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
        output.putString("origin_facing", this.getOriginFacing().getName());
        output.putByte("anim_state", this.entityData.get(DATA_ANIM_STATE));
        output.putInt("anim_ticks", this.animTicks);
        output.putInt("awake_ticks", this.awakeTicks);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        if (input.getInt("origin_x").isPresent() && input.getInt("origin_y").isPresent() && input.getInt("origin_z").isPresent()) {
            this.originPos = new BlockPos(
                    input.getIntOr("origin_x", 0),
                    input.getIntOr("origin_y", 0),
                    input.getIntOr("origin_z", 0)
            );
        }
        Direction loadedFacing = Direction.byName(input.getStringOr("origin_facing", "north"));
        this.setOriginFacing(loadedFacing != null ? loadedFacing : Direction.NORTH);
        this.entityData.set(DATA_ANIM_STATE, input.getByteOr("anim_state", STATE_IDLE));
        this.animTicks = input.getIntOr("anim_ticks", 0);
        this.awakeTicks = input.getIntOr("awake_ticks", 0);
    }
}
