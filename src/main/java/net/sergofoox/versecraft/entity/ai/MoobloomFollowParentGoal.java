package net.sergofoox.versecraft.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.sergofoox.versecraft.entity.Moobloom;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class MoobloomFollowParentGoal extends Goal {
    public static final int HORIZONTAL_SCAN_RANGE = 8;
    public static final int VERTICAL_SCAN_RANGE = 4;
    public static final double DONT_FOLLOW_IF_CLOSER_THAN_SQ = 9.0;
    public static final double STOP_FOLLOW_IF_FARTHER_THAN_SQ = 256.0;

    private final Moobloom moobloom;
    @Nullable
    private Moobloom parent;
    private final double speedModifier;
    private int timeToRecalculatePath;

    public MoobloomFollowParentGoal(Moobloom moobloom, double speedModifier) {
        this.moobloom = moobloom;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.moobloom.getAge() >= 0) {
            return false;
        }

        List<Moobloom> candidates = this.moobloom.level().getEntitiesOfClass(
                Moobloom.class,
                this.moobloom.getBoundingBox().inflate(HORIZONTAL_SCAN_RANGE, VERTICAL_SCAN_RANGE, HORIZONTAL_SCAN_RANGE)
        );
        Moobloom bestParent = null;
        double minDistanceSq = Double.MAX_VALUE;

        for (Moobloom candidate : candidates) {
            if (candidate.getAge() >= 0 && candidate.isGolden() == this.moobloom.isGolden()) {
                double distSq = this.moobloom.distanceToSqr(candidate);
                if (distSq <= minDistanceSq) {
                    minDistanceSq = distSq;
                    bestParent = candidate;
                }
            }
        }

        if (bestParent == null || minDistanceSq < DONT_FOLLOW_IF_CLOSER_THAN_SQ) {
            return false;
        }

        this.parent = bestParent;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.moobloom.getAge() >= 0) {
            return false;
        }
        if (this.parent == null || !this.parent.isAlive() || this.parent.isGolden() != this.moobloom.isGolden()) {
            return false;
        }
        double distSq = this.moobloom.distanceToSqr(this.parent);
        return distSq >= DONT_FOLLOW_IF_CLOSER_THAN_SQ && distSq <= STOP_FOLLOW_IF_FARTHER_THAN_SQ;
    }

    @Override
    public void start() {
        this.timeToRecalculatePath = 0;
    }

    @Override
    public void stop() {
        this.parent = null;
    }

    @Override
    public void tick() {
        if (this.parent != null && --this.timeToRecalculatePath <= 0) {
            this.timeToRecalculatePath = this.adjustedTickDelay(10);
            this.moobloom.getNavigation().moveTo(this.parent, this.speedModifier);
        }
    }
}
