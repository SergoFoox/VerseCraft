package net.sergofoox.versecraft.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.entity.monster.illager.Vindicator;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.sergofoox.versecraft.VerseCraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Raider.class)
public abstract class RaiderMixin {

    @Unique
    private static final ResourceKey<LootTable> VERSECRAFT$RAIDER_DROPS = ResourceKey.create(
            Registries.LOOT_TABLE,
            VerseCraft.id("entities/raider_drops")
    );

    @Unique
    private boolean versecraft$wasInActiveRaid;

    @Inject(method = "die", at = @At("HEAD"))
    private void versecraft$onDieHead(DamageSource source, CallbackInfo ci) {
        Raider self = (Raider) (Object) this;
        this.versecraft$wasInActiveRaid = self.hasActiveRaid();
    }

    @Inject(method = "die", at = @At("TAIL"))
    private void versecraft$onDieTail(DamageSource source, CallbackInfo ci) {
        if (this.versecraft$wasInActiveRaid) {
            this.versecraft$wasInActiveRaid = false;
            Raider self = (Raider) (Object) this;
            if (self instanceof Pillager || self instanceof Vindicator) {
                if (self.level() instanceof ServerLevel serverLevel) {
                    if (serverLevel.getGameRules().get(GameRules.MOB_DROPS)) {
                        boolean hitByPlayer = self.getLastHurtByPlayerMemoryTime() > 0;
                        self.dropFromLootTable(serverLevel, source, hitByPlayer, VERSECRAFT$RAIDER_DROPS);
                    }
                }
            }
        }
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void versecraft$checkForWhiteBannerSurrender(CallbackInfo ci) {
        Raider self = (Raider) (Object) this;
        if (!self.isAlive() || !(self.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (self.getMainHandItem().is(Items.BANNER.white())) {
            self.setAggressive(false);
            if (self.getTarget() != null) {
                self.setTarget(null);
            }
            return;
        }

        if (!(self instanceof Pillager) || !self.isHolding(Items.CROSSBOW)) {
            return;
        }

        if (self.tickCount % 4 != 0) {
            return;
        }

        AABB searchBox = self.getBoundingBox().inflate(3.0, 1.5, 3.0);
        List<ItemEntity> nearbyBanners = serverLevel.getEntitiesOfClass(
                ItemEntity.class,
                searchBox,
                itemEntity -> !itemEntity.isRemoved() && versecraft$isPlainWhiteBanner(itemEntity.getItem())
        );

        if (nearbyBanners.isEmpty()) {
            return;
        }

        ItemEntity bannerEntity = null;
        double minDistanceSq = Double.MAX_VALUE;
        for (ItemEntity candidate : nearbyBanners) {
            double distSq = self.distanceToSqr(candidate);
            if (distSq < minDistanceSq) {
                minDistanceSq = distSq;
                bannerEntity = candidate;
            }
        }

        if (bannerEntity == null) {
            return;
        }

        self.setTarget(null);

        if (minDistanceSq <= 0.81D || self.getBoundingBox().inflate(0.5, 0.5, 0.5).intersects(bannerEntity.getBoundingBox())) {
            ItemStack singleBanner = bannerEntity.getItem().split(1);
            if (bannerEntity.getItem().isEmpty()) {
                bannerEntity.discard();
            }
            self.take(bannerEntity, 1);

            ItemStack weapon = self.getMainHandItem().copy();
            self.spawnAtLocation(serverLevel, weapon);

            self.setItemSlot(EquipmentSlot.MAINHAND, singleBanner);
            self.setDropChance(EquipmentSlot.MAINHAND, 1.0F);

            self.setAggressive(false);
            self.setTarget(null);
            Raid raid = self.getCurrentRaid();
            if (raid != null) {
                raid.removeFromRaid(serverLevel, self, true);
            }

            self.playSound(SoundEvents.ITEM_PICKUP, 0.8F, 1.0F);
            return;
        }

        self.getLookControl().setLookAt(bannerEntity, 30.0F, 30.0F);
        self.getMoveControl().setWantedPosition(bannerEntity.getX(), bannerEntity.getY(), bannerEntity.getZ(), 1.0);
        if (self.getNavigation().isDone() || self.tickCount % 10 == 0) {
            self.getNavigation().moveTo(bannerEntity, 1.0);
        }
    }

    @Unique
    private static boolean versecraft$isPlainWhiteBanner(ItemStack stack) {
        if (!stack.is(Items.BANNER.white())) {
            return false;
        }
        BannerPatternLayers layers = stack.get(DataComponents.BANNER_PATTERNS);
        return layers == null || layers.layers().isEmpty();
    }
}
