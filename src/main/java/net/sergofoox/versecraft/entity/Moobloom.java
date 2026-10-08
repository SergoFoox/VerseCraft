package net.sergofoox.versecraft.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.cow.AbstractCow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.sergofoox.versecraft.entity.ai.MoobloomWaitForBeeGoal;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import net.sergofoox.versecraft.registry.RegisterEntityTypes;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Moobloom extends AbstractCow implements Shearable {

    public static final int FLOWER_COUNT = 3;
    public static final byte ALL_FLOWERS = 0b111; // 7

    private static final EntityDataAccessor<Byte> DATA_FLOWER_MASK =
            SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.BYTE);

    @Nullable
    private SuspiciousStewEffects stewEffects;

    private final int[] flowerRegrowTimers = new int[FLOWER_COUNT];
    private final UUID[] flowerReservations = new UUID[FLOWER_COUNT];
    private final Set<UUID> activePollinators = new HashSet<>();

    public Moobloom(EntityType<? extends Moobloom> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLOWER_MASK, ALL_FLOWERS);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MoobloomWaitForBeeGoal(this));
        super.registerGoals();
    }

    public byte getFlowerMask() {
        return this.entityData.get(DATA_FLOWER_MASK);
    }

    public void setFlowerMask(byte mask) {
        this.entityData.set(DATA_FLOWER_MASK, mask);
    }

    public boolean hasFlower(int slot) {
        return (this.getFlowerMask() & (1 << slot)) != 0;
    }

    public boolean hasAvailableFlower() {
        if (!this.isAlive() || this.isBaby()) {
            return false;
        }
        byte mask = this.getFlowerMask();
        for (int i = 0; i < FLOWER_COUNT; i++) {
            if ((mask & (1 << i)) != 0 && this.flowerReservations[i] == null) {
                return true;
            }
        }
        return false;
    }

    public synchronized int claimFlowerSlot(Bee bee) {
        if (!this.isAlive() || this.isBaby()) {
            return -1;
        }
        byte mask = this.getFlowerMask();
        UUID beeUuid = bee.getUUID();

        // If this bee already has a reservation, return it
        for (int i = 0; i < FLOWER_COUNT; i++) {
            if ((mask & (1 << i)) != 0 && beeUuid.equals(this.flowerReservations[i])) {
                return i;
            }
        }

        // Find the first available unreserved flower
        for (int i = 0; i < FLOWER_COUNT; i++) {
            if ((mask & (1 << i)) != 0 && this.flowerReservations[i] == null) {
                this.flowerReservations[i] = beeUuid;
                return i;
            }
        }
        return -1;
    }

    public synchronized void releaseFlowerSlot(int slot, Bee bee) {
        if (slot >= 0 && slot < FLOWER_COUNT) {
            if (bee.getUUID().equals(this.flowerReservations[slot])) {
                this.flowerReservations[slot] = null;
            }
        }
    }

    public boolean isFlowerReservedBy(int slot, Bee bee) {
        if (slot >= 0 && slot < FLOWER_COUNT) {
            return bee.getUUID().equals(this.flowerReservations[slot]);
        }
        return false;
    }

    public void addActivePollinator(Bee bee) {
        this.activePollinators.add(bee.getUUID());
    }

    public void removeActivePollinator(Bee bee) {
        this.activePollinators.remove(bee.getUUID());
    }

    public void clearAllActivePollinators() {
        this.activePollinators.clear();
        for (int i = 0; i < FLOWER_COUNT; i++) {
            this.flowerReservations[i] = null;
        }
    }

    public boolean isWaitingForBee() {
        return !this.activePollinators.isEmpty();
    }

    public Vec3 getFlowerWorldPos(int slot) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        if (slot == 2) {
            // Head flower: projects forward along head yaw
            float headRad = (float) Math.toRadians(this.yHeadRot);
            double cos = Math.cos(headRad);
            double sin = Math.sin(headRad);
            double fwdZ = 0.65;
            double offX = -sin * fwdZ;
            double offZ = cos * fwdZ;
            return new Vec3(x + offX, y + 1.85, z + offZ);
        }

        // Back flowers: rotated along body yaw
        float bodyRad = (float) Math.toRadians(this.yBodyRot);
        double cos = Math.cos(bodyRad);
        double sin = Math.sin(bodyRad);

        // Slot 0: back right, Slot 1: back left
        double localX = (slot == 0) ? 0.25 : -0.2;
        double localZ = (slot == 0) ? -0.35 : 0.15;

        double offX = -sin * localZ + cos * localX;
        double offZ = cos * localZ + sin * localX;

        return new Vec3(x + offX, y + 1.8, z + offZ);
    }

    public void onPollinationSuccess(int slot) {
        if (slot >= 0 && slot < FLOWER_COUNT) {
            byte newMask = (byte) (this.getFlowerMask() & ~(1 << slot));
            this.setFlowerMask(newMask);
            this.flowerRegrowTimers[slot] = 2400; // 2 minutes
            this.flowerReservations[slot] = null;

            if (this.level() instanceof ServerLevel serverLevel) {
                Vec3 flowerPos = this.getFlowerWorldPos(slot);
                serverLevel.sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        flowerPos.x, flowerPos.y, flowerPos.z,
                        8, 0.25, 0.25, 0.25, 0.05
                );
                serverLevel.playSound(null, this.blockPosition(), SoundEvents.BEE_POLLINATE, SoundSource.NEUTRAL, 1.0F, 1.2F);
            }
        }
    }

    public void regrowFlower(int slot) {
        if (slot >= 0 && slot < FLOWER_COUNT) {
            byte newMask = (byte) (this.getFlowerMask() | (1 << slot));
            this.setFlowerMask(newMask);
            this.flowerReservations[slot] = null;

            if (this.level() instanceof ServerLevel serverLevel) {
                Vec3 flowerPos = this.getFlowerWorldPos(slot);
                serverLevel.sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        flowerPos.x, flowerPos.y, flowerPos.z,
                        6, 0.2, 0.2, 0.2, 0.02
                );
                serverLevel.playSound(null, this.blockPosition(), SoundEvents.CHORUS_FLOWER_GROW, SoundSource.NEUTRAL, 0.8F, 1.2F);
            }
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        this.clearAllActivePollinators();
        return super.hurtServer(level, source, amount);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide()) {
            byte mask = this.getFlowerMask();
            for (int i = 0; i < FLOWER_COUNT; i++) {
                if ((mask & (1 << i)) == 0) {
                    if (this.flowerRegrowTimers[i] > 0) {
                        this.flowerRegrowTimers[i]--;
                        if (this.flowerRegrowTimers[i] <= 0) {
                            this.regrowFlower(i);
                        }
                    }
                }
            }

            // Cleanup any active pollinators that died or moved far away
            if (!this.activePollinators.isEmpty() && this.tickCount % 20 == 0) {
                this.activePollinators.removeIf(uuid -> {
                    Entity entity = ((ServerLevel) this.level()).getEntity(uuid);
                    return entity == null || !entity.isAlive() || this.distanceToSqr(entity) > 64.0;
                });
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.BOWL) && !this.isBaby()) {
            boolean hasStew = this.stewEffects != null;
            ItemStack stewStack = new ItemStack(Items.SUSPICIOUS_STEW);
            if (this.stewEffects != null) {
                stewStack.set(net.minecraft.core.component.DataComponents.SUSPICIOUS_STEW_EFFECTS, this.stewEffects);
                this.stewEffects = null;
            } else {
                stewStack.set(
                        net.minecraft.core.component.DataComponents.SUSPICIOUS_STEW_EFFECTS,
                        new SuspiciousStewEffects(java.util.List.of(
                                new SuspiciousStewEffects.Entry(net.minecraft.world.effect.MobEffects.SATURATION, 7)
                        ))
                );
            }

            ItemStack resultStack = ItemUtils.createFilledResult(itemStack, player, stewStack, false);
            player.setItemInHand(hand, resultStack);
            SoundEvent sound = hasStew ? SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY : SoundEvents.MOOSHROOM_MILK;
            this.playSound(sound, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }

        if (itemStack.is(Items.SHEARS) && this.readyForShearing()) {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.shear(serverLevel, SoundSource.PLAYERS, itemStack);
                this.gameEvent(GameEvent.SHEAR, player);
                itemStack.hurtAndBreak(1, player, hand.asEquipmentSlot());
            }
            return InteractionResult.SUCCESS;
        }

        // Кормление цветком для загадочного рагу (как у коричневого грибного мушрума — ТОЛЬКО для взрослых)
        if (!this.isBaby()) {
            Optional<SuspiciousStewEffects> stewEffectsOpt = Optional.ofNullable(SuspiciousEffectHolder.tryGet(itemStack.getItem()))
                    .map(SuspiciousEffectHolder::getSuspiciousEffects);
            if (stewEffectsOpt.isPresent()) {
                if (this.stewEffects != null) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(
                                ParticleTypes.SMOKE,
                                this.getX(),
                                this.getY(0.5),
                                this.getZ(),
                                1,
                                0.0,
                                0.0,
                                0.0,
                                0.0
                        );
                    }
                    return InteractionResult.SUCCESS;
                }

                if (!this.level().isClientSide()) {
                    this.stewEffects = stewEffectsOpt.get();
                    this.playSound(SoundEvents.MOOSHROOM_EAT, 2.0F, 1.0F);
                    itemStack.consume(1, player);
                    SpellParticleOption particle = SpellParticleOption.create(ParticleTypes.EFFECT, -1, 1.0F);
                    for (int i = 0; i < 4; ++i) {
                        ((ServerLevel) this.level()).sendParticles(
                                particle,
                                this.getX() + this.random.nextDouble() / 2.0,
                                this.getY(0.5),
                                this.getZ() + this.random.nextDouble() / 2.0,
                                1, 0.0, 0.0, 0.0, 0.0
                        );
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void shear(ServerLevel serverLevel, SoundSource soundSource, ItemStack itemStack) {
        this.clearAllActivePollinators();
        serverLevel.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, soundSource, 1.0F, 1.0F);
        this.convertTo(EntityTypes.COW, ConversionParams.single(this, false, false), cow -> {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            for (int i = 0; i < 5; ++i) {
                serverLevel.addFreshEntity(new ItemEntity(
                        serverLevel, this.getX(), this.getY(1.0), this.getZ(), new ItemStack(RegisterBlocks.BUTTERCUP)
                ));
            }
        });
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(net.minecraft.tags.ItemTags.COW_FOOD);
    }

    @Override
    @Nullable
    public Moobloom getBreedOffspring(ServerLevel level, net.minecraft.world.entity.AgeableMob otherParent) {
        return RegisterEntityTypes.MOOBLOOM.create(level, EntitySpawnReason.BREEDING);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.storeNullable("stew_effects", SuspiciousStewEffects.CODEC, this.stewEffects);
        output.putByte("flower_mask", this.getFlowerMask());
        output.putInt("flower_timer_0", this.flowerRegrowTimers[0]);
        output.putInt("flower_timer_1", this.flowerRegrowTimers[1]);
        output.putInt("flower_timer_2", this.flowerRegrowTimers[2]);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.stewEffects = input.read("stew_effects", SuspiciousStewEffects.CODEC).orElse(null);
        this.setFlowerMask(input.getByteOr("flower_mask", ALL_FLOWERS));
        this.flowerRegrowTimers[0] = input.getIntOr("flower_timer_0", 0);
        this.flowerRegrowTimers[1] = input.getIntOr("flower_timer_1", 0);
        this.flowerRegrowTimers[2] = input.getIntOr("flower_timer_2", 0);
    }
}
