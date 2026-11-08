package net.sergofoox.versecraft.entity.ai;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.sergofoox.versecraft.entity.TuffGolem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuffGolemSearchItemGoal extends Goal {

    private final TuffGolem golem;
    private final double speedModifier;
    private ItemEntity targetItem;
    private int repathCooldown;

    public TuffGolemSearchItemGoal(TuffGolem golem, double speedModifier) {
        this.golem = golem;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.golem.isAlive() || this.golem.isDeadOrDying() || this.golem.isBusyWithAnim() || !this.golem.getMainHandItem().isEmpty()) {
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

        List<TuffGolem> otherGolems = this.golem.level().getEntitiesOfClass(
                TuffGolem.class,
                searchBox,
                other -> other != this.golem && other.isAlive() && other.getTargetedItem() != null
        );

        Map<ItemEntity, Integer> claimCounts = new HashMap<>();
        for (TuffGolem other : otherGolems) {
            ItemEntity target = other.getTargetedItem();
            if (target != null) {
                claimCounts.merge(target, 1, Integer::sum);
            }
        }

        List<ItemEntity> unclaimedItems = new ArrayList<>();
        List<ItemEntity> partiallyClaimedItems = new ArrayList<>();

        for (ItemEntity item : items) {
            int claims = claimCounts.getOrDefault(item, 0);
            if (claims == 0) {
                unclaimedItems.add(item);
            } else if (claims < item.getItem().getCount()) {
                partiallyClaimedItems.add(item);
            }
        }

        ItemEntity chosen;
        if (!unclaimedItems.isEmpty()) {
            unclaimedItems.sort(Comparator.comparingDouble(this.golem::distanceToSqr));
            chosen = unclaimedItems.get(0);
        } else if (!partiallyClaimedItems.isEmpty()) {
            partiallyClaimedItems.sort(Comparator.comparingDouble(this.golem::distanceToSqr));
            chosen = partiallyClaimedItems.get(0);
        } else {
            return false;
        }

        this.targetItem = chosen;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.golem.isAlive()
                && !this.golem.isDeadOrDying()
                && !this.golem.isBusyWithAnim()
                && this.targetItem != null
                && this.targetItem.isAlive()
                && !this.targetItem.getItem().isEmpty()
                && this.golem.getMainHandItem().isEmpty();
    }

    @Override
    public void start() {
        this.golem.setTargetedItem(this.targetItem);
        this.golem.setTargetingItem(true);
        this.repathCooldown = 0;
        if (this.targetItem != null) {
            this.golem.getNavigation().moveTo(this.targetItem, this.speedModifier);
        }
    }

    @Override
    public void stop() {
        this.golem.setTargetedItem(null);
        this.golem.setTargetingItem(false);
        this.targetItem = null;
        this.golem.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (!this.golem.isAlive() || this.golem.isDeadOrDying() || this.targetItem == null || !this.targetItem.isAlive() || this.targetItem.getItem().isEmpty()) {
            this.stop();
            return;
        }

        this.golem.getLookControl().setLookAt(this.targetItem, 30.0F, 30.0F);

        double distSq = this.golem.distanceToSqr(this.targetItem);

        if (distSq <= 3.25D || this.golem.getBoundingBox().inflate(0.8D, 0.5D, 0.8D).intersects(this.targetItem.getBoundingBox())) {
            ItemStack singleItem = this.targetItem.getItem().split(1);
            this.golem.setItemSlot(EquipmentSlot.MAINHAND, singleItem);
            if (this.targetItem.getItem().isEmpty()) {
                this.targetItem.discard();
            }
            this.golem.playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.0F);
            this.stop();
            return;
        }

        if (this.repathCooldown > 0) {
            this.repathCooldown--;
        } else {
            this.repathCooldown = 10;
            this.golem.getNavigation().moveTo(this.targetItem, this.speedModifier);
        }
    }
}
