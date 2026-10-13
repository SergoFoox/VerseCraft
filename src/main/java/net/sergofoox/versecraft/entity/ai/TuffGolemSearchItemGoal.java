package net.sergofoox.versecraft.entity.ai;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.sergofoox.versecraft.entity.TuffGolem;

import java.util.EnumSet;
import java.util.List;

public class TuffGolemSearchItemGoal extends Goal {

    private final TuffGolem golem;
    private final double speedModifier;
    private ItemEntity targetItem;

    public TuffGolemSearchItemGoal(TuffGolem golem, double speedModifier) {
        this.golem = golem;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.golem.isBusyWithAnim() || !this.golem.getMainHandItem().isEmpty()) {
            return false;
        }

        AABB searchBox = this.golem.getBoundingBox().inflate(16.0D, 6.0D, 16.0D);
        List<ItemEntity> items = this.golem.level().getEntitiesOfClass(
                ItemEntity.class,
                searchBox,
                item -> item.isAlive() && !item.getItem().isEmpty()
        );

        if (items.isEmpty()) {
            return false;
        }

        ItemEntity closest = null;
        double closestDist = Double.MAX_VALUE;
        for (ItemEntity item : items) {
            double dist = this.golem.distanceToSqr(item);
            if (dist < closestDist) {
                closestDist = dist;
                closest = item;
            }
        }

        if (closest != null) {
            this.targetItem = closest;
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.golem.isBusyWithAnim()
                && this.targetItem != null
                && this.targetItem.isAlive()
                && !this.targetItem.getItem().isEmpty()
                && this.golem.getMainHandItem().isEmpty()
                && this.golem.distanceToSqr(this.targetItem) < 256.0D;
    }

    @Override
    public void start() {
        if (this.targetItem != null) {
            this.golem.getNavigation().moveTo(this.targetItem.getX(), this.targetItem.getY(), this.targetItem.getZ(), this.speedModifier);
        }
    }

    @Override
    public void stop() {
        this.targetItem = null;
        this.golem.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.targetItem == null) {
            return;
        }

        this.golem.getLookControl().setLookAt(this.targetItem, 30.0F, 30.0F);

        double distSq = this.golem.distanceToSqr(this.targetItem);

        // Immediate pickup when within reach (up to 2.0 blocks or hitbox touch)
        if (distSq <= 4.0D || this.golem.getBoundingBox().inflate(0.8D).intersects(this.targetItem.getBoundingBox())) {
            ItemStack singleItem = this.targetItem.getItem().split(1);
            this.golem.setItemSlot(EquipmentSlot.MAINHAND, singleItem);
            if (this.targetItem.getItem().isEmpty()) {
                this.targetItem.discard();
            }
            this.golem.playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.0F);
            this.stop();
            return;
        }

        this.golem.getNavigation().moveTo(this.targetItem.getX(), this.targetItem.getY(), this.targetItem.getZ(), this.speedModifier);
    }
}
