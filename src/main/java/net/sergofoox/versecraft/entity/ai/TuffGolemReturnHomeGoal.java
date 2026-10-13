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
        BlockPos currentPos = this.golem.blockPosition();

        boolean arrived = (horizDistSq <= 0.06D && dy < 0.5D)
                || (currentPos.equals(home) && horizDistSq <= 0.16D && dy < 0.5D)
                || (currentPos.equals(home) && dy < 0.5D && this.golem.getDeltaMovement().horizontalDistanceSqr() < 0.0005D)
                || (horizDistSq <= 0.64D && dy < 0.5D && (this.golem.getNavigation().isDone() || this.golem.getNavigation().isStuck()));

        if (arrived) {
            if (this.golem.shouldYieldToDeeperGolem() && this.yieldTicks < 300) {
                this.yieldTicks++;
                this.golem.getNavigation().stop();
                this.golem.setDeltaMovement(Vec3.ZERO);
                return;
            }
            this.golem.startSleeping();
            return;
        }

        if (horizDistSq <= 3.0D && dy < 1.0D) {
            this.golem.getMoveControl().setWantedPosition(targetX, targetY, targetZ, this.speedModifier);
        } else {
            if (this.repathCooldown > 0) {
                this.repathCooldown--;
            } else if (this.golem.getNavigation().isDone() || this.golem.getNavigation().getPath() == null) {
                this.repathCooldown = 20;
                this.golem.getNavigation().moveTo(targetX, targetY, targetZ, this.speedModifier);
            }
        }
    }
}
