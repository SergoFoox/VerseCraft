package net.sergofoox.versecraft.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.sergofoox.versecraft.entity.TuffGolem;

import java.util.EnumSet;

public class TuffGolemReturnHomeGoal extends Goal {

    private final TuffGolem golem;
    private final double speedModifier;
    private int repathCooldown = 0;
    private int yieldTicks = 0;

    public TuffGolemReturnHomeGoal(TuffGolem golem, double speedModifier) {
        this.golem = golem;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return !this.golem.isBusyWithAnim() && this.golem.wantsToSleep() && this.golem.getOriginPos() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.golem.isBusyWithAnim() && this.canUse();
    }

    @Override
    public void start() {
        this.repathCooldown = 0;
        this.yieldTicks = 0;
        BlockPos home = this.golem.getOriginPos();
        if (home != null) {
            this.golem.getNavigation().moveTo(home.getX() + 0.5D, home.getY(), home.getZ() + 0.5D, this.speedModifier);
        }
    }

    @Override
    public void stop() {
        this.golem.getNavigation().stop();
    }

    @Override
    public void tick() {
        BlockPos home = this.golem.getOriginPos();
        if (home == null) {
            return;
        }

        double targetX = home.getX() + 0.5D;
        double targetZ = home.getZ() + 0.5D;
        double targetY = home.getY();

        this.golem.getLookControl().setLookAt(targetX, targetY + 0.5D, targetZ, 30.0F, 30.0F);

        Vec3 pos = this.golem.position();
        double dx = pos.x - targetX;
        double dz = pos.z - targetZ;
        double horizDistSq = dx * dx + dz * dz;
        double dy = Math.abs(pos.y - targetY);

        boolean atCenter = (horizDistSq <= 0.09D && dy < 0.5D);
        boolean stoppedAtHome = (this.golem.blockPosition().equals(home) && horizDistSq <= 0.18D && dy < 0.5D && this.golem.getDeltaMovement().horizontalDistanceSqr() < 0.0005D);
        boolean stuckAtHome = (horizDistSq <= 0.36D && dy < 0.5D && this.golem.getNavigation().isStuck());

        if (atCenter || stoppedAtHome || stuckAtHome) {
            if (this.golem.shouldYieldToDeeperGolem() && this.yieldTicks < 60) {
                this.yieldTicks++;
                this.golem.getNavigation().stop();
                this.golem.setDeltaMovement(Vec3.ZERO);
                return;
            }
            this.golem.startSleeping();
            return;
        }

        if (horizDistSq <= 2.25D && dy < 1.0D) {
            this.golem.getMoveControl().setWantedPosition(targetX, targetY, targetZ, this.speedModifier);
        } else {
            if (this.repathCooldown > 0) {
                this.repathCooldown--;
            } else if (this.golem.getNavigation().isDone() || this.golem.getNavigation().getPath() == null) {
                this.repathCooldown = 15;
                this.golem.getNavigation().moveTo(targetX, targetY, targetZ, this.speedModifier);
            }
        }
    }
}
