package net.sergofoox.versecraft.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ConversionParams;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sergofoox.versecraft.entity.ai.MoobloomWaitForBeeGoal;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import net.sergofoox.versecraft.registry.RegisterEntityTypes;
import org.jetbrains.annotations.Nullable;

public class Moobloom extends AbstractCow implements Shearable {

    @Nullable
    private SuspiciousStewEffects stewEffects;
    private int pollinationCooldown = 0;
    @Nullable
    private Bee pollinatingBee = null;
    private boolean isWaitingForBee = false;

    public Moobloom(EntityType<? extends Moobloom> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MoobloomWaitForBeeGoal(this));
        super.registerGoals();
    }

    public boolean canBePollinated() {
        return this.isAlive() && !this.isBaby() && this.pollinationCooldown <= 0 && this.pollinatingBee == null;
    }

    public void setReservedByBee(Bee bee) {
        this.pollinatingBee = bee;
    }

    public void startBeePollination(Bee bee) {
        this.pollinatingBee = bee;
        this.isWaitingForBee = true;
        this.getNavigation().stop();
    }

    public boolean isWaitingForBee() {
        return this.isWaitingForBee && this.pollinatingBee != null && this.pollinatingBee.isAlive() && this.distanceToSqr(this.pollinatingBee) < 36.0;
    }

    @Nullable
    public Bee getPollinatingBee() {
        return this.pollinatingBee;
    }

    public void clearReservedBee() {
        this.pollinatingBee = null;
        this.isWaitingForBee = false;
    }

    public void onPollinationSuccess() {
        this.pollinationCooldown = 2400; // 2 minutes
        this.clearReservedBee();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        this.clearReservedBee();
        return super.hurtServer(level, source, amount);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.pollinationCooldown > 0) {
            this.pollinationCooldown--;
        }

        if (this.pollinatingBee != null && (!this.pollinatingBee.isAlive() || this.distanceToSqr(this.pollinatingBee) > 64.0)) {
            this.clearReservedBee();
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

        if (itemStack.is(BlockItemTags.SMALL_FLOWERS.item())) {
            Block block = Block.byItem(itemStack.getItem());
            if (block instanceof FlowerBlock flowerBlock) {
                if (!this.level().isClientSide()) {
                    this.stewEffects = flowerBlock.getSuspiciousEffects();
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
        this.clearReservedBee();
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
        output.putInt("pollination_cooldown", this.pollinationCooldown);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.stewEffects = input.read("stew_effects", SuspiciousStewEffects.CODEC).orElse(null);
        this.pollinationCooldown = input.getIntOr("pollination_cooldown", 0);
    }
}
