package net.sergofoox.versecraft.entity.ai;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.sergofoox.versecraft.entity.TuffGolem;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class TuffGolemSearchItemGoal extends Goal {

    private final TuffGolem golem;
    private final double speedModifier;
    private ItemEntity targetItem;
    private int repathCooldown;
    private int reevaluateCooldown;
    private int stuckTicks;
    private final Map<ItemEntity, Long> unreachableCooldowns = new WeakHashMap<>();

    public TuffGolemSearchItemGoal(TuffGolem golem, double speedModifier) {
        this.golem = golem;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private void cleanCooldowns() {
        long currentTick = this.golem.level().getGameTime();
        Iterator<Map.Entry<ItemEntity, Long>> it = this.unreachableCooldowns.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ItemEntity, Long> entry = it.next();
            if (currentTick >= entry.getValue() || !entry.getKey().isAlive()) {
                it.remove();
            }
        }
    }

    private boolean isValidCandidate(ItemEntity item) {
        if (!item.isAlive() || item.getItem().isEmpty()) {
            return false;
        }
        Long expireTime = this.unreachableCooldowns.get(item);
        return expireTime == null || this.golem.level().getGameTime() >= expireTime;
    }

    private void markUnreachable(ItemEntity item) {
        if (item != null) {
            this.unreachableCooldowns.put(item, this.golem.level().getGameTime() + 100L);
        }
    }

    @Override
    public boolean canUse() {
        if (this.golem.isBusyWithAnim() || !this.golem.getMainHandItem().isEmpty()) {
            return false;
        }

        cleanCooldowns();

        AABB searchBox = this.golem.getBoundingBox().inflate(16.0D, 6.0D, 16.0D);
        List<ItemEntity> items = this.golem.level().getEntitiesOfClass(
                ItemEntity.class,
                searchBox,
                this::isValidCandidate
        );

        if (items.isEmpty()) {
            return false;
        }

        items.sort(Comparator.comparingDouble(this.golem::distanceToSqr));

        for (ItemEntity candidate : items) {
            Path path = this.golem.getNavigation().createPath(candidate, 0);
            if (path != null && (path.canReach() || path.getDistToTarget() <= 2.5F)) {
                this.targetItem = candidate;
                return true;
            }
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
        this.repathCooldown = 0;
        this.reevaluateCooldown = 10;
        this.stuckTicks = 0;
        if (this.targetItem != null) {
            this.golem.getNavigation().moveTo(this.targetItem.getX(), this.targetItem.getY(), this.targetItem.getZ(), this.speedModifier);
        }
    }

    @Override
    public void stop() {
        this.targetItem = null;
        this.stuckTicks = 0;
        this.golem.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.targetItem == null) {
            return;
        }

        this.golem.getLookControl().setLookAt(this.targetItem, 30.0F, 30.0F);

        double distSq = this.golem.distanceToSqr(this.targetItem);

        if (distSq <= 6.25D || this.golem.getBoundingBox().inflate(1.2D, 0.8D, 1.2D).intersects(this.targetItem.getBoundingBox())) {
            ItemStack singleItem = this.targetItem.getItem().split(1);
            this.golem.setItemSlot(EquipmentSlot.MAINHAND, singleItem);
            if (this.targetItem.getItem().isEmpty()) {
                this.targetItem.discard();
            }
            this.golem.playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.0F);
            this.stop();
            return;
        }

        if (this.reevaluateCooldown > 0) {
            this.reevaluateCooldown--;
        } else {
            this.reevaluateCooldown = 10;
            ItemEntity closer = findCloserReachableItem(distSq);
            if (closer != null) {
                this.targetItem = closer;
                this.stuckTicks = 0;
                this.repathCooldown = 20;
                this.golem.getNavigation().moveTo(this.targetItem.getX(), this.targetItem.getY(), this.targetItem.getZ(), this.speedModifier);
                return;
            }
        }

        if (this.repathCooldown > 0) {
            this.repathCooldown--;
        } else if (this.golem.getNavigation().isDone() || this.golem.getNavigation().getPath() == null) {
            this.repathCooldown = 20;
            this.golem.getNavigation().moveTo(this.targetItem.getX(), this.targetItem.getY(), this.targetItem.getZ(), this.speedModifier);
        }

        if (this.golem.getNavigation().isStuck()) {
            this.stuckTicks++;
            if (this.stuckTicks > 40) {
                markUnreachable(this.targetItem);
                this.stop();
            }
        } else {
            this.stuckTicks = 0;
        }
    }

    private ItemEntity findCloserReachableItem(double currentDistSq) {
        AABB searchBox = this.golem.getBoundingBox().inflate(16.0D, 6.0D, 16.0D);
        List<ItemEntity> items = this.golem.level().getEntitiesOfClass(
                ItemEntity.class,
                searchBox,
                item -> item != this.targetItem && isValidCandidate(item)
        );

        if (items.isEmpty()) {
            return null;
        }

        items.sort(Comparator.comparingDouble(this.golem::distanceToSqr));

        for (ItemEntity candidate : items) {
            double candDistSq = this.golem.distanceToSqr(candidate);
            if (candDistSq >= currentDistSq - 2.25D) {
                break;
            }

            Path path = this.golem.getNavigation().createPath(candidate, 0);
            if (path != null && (path.canReach() || path.getDistToTarget() <= 2.5F)) {
                return candidate;
            }
        }

        return null;
    }
}
