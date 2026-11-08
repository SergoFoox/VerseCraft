package net.sergofoox.versecraft.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
        return this.golem.isAlive() && !this.golem.isDeadOrDying() && !this.golem.isBusyWithAnim() && this.golem.wantsToSleep() && this.golem.getOriginPos() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.golem.isAlive() && !this.golem.isDeadOrDying() && !this.golem.isBusyWithAnim() && this.canUse();
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
        if (!this.golem.isAlive() || this.golem.isDeadOrDying()) {
            this.stop();
            return;
        }

        BlockPos home = this.golem.getOriginPos();
        if (home == null) {
            return;
        }

        double targetX = home.getX() + 0.5D;
        double targetZ = home.getZ() + 0.5D;
        double targetY = home.getY();

        Vec3 pos = this.golem.position();
        double toTargetX = targetX - pos.x;
        double toTargetZ = targetZ - pos.z;
        double horizDistSq = toTargetX * toTargetX + toTargetZ * toTargetZ;
        double dy = Math.abs(pos.y - targetY);

        boolean onHomeBlock = (this.golem.blockPosition().equals(home) && horizDistSq <= 0.16D && dy < 0.5D);
        boolean closeEnough = (horizDistSq <= 0.04D && dy < 0.5D);
        boolean stuckNearHome = (horizDistSq <= 0.36D && dy < 0.5D && this.golem.getNavigation().isStuck());

        if (onHomeBlock || closeEnough || stuckNearHome) {
            this.golem.getNavigation().stop();
            this.golem.setDeltaMovement(Vec3.ZERO);
            this.golem.startGoingToSleep();
            return;
        }

        if (this.golem.shouldYieldToDeeperGolem() && this.yieldTicks < 40) {
            this.yieldTicks++;
            this.golem.getNavigation().stop();
            this.golem.setDeltaMovement(Vec3.ZERO);
            return;
        }
        this.yieldTicks = 0;

        Direction facing = this.golem.getOriginFacing();
        if (horizDistSq <= 0.35D && facing != null) {
            Vec3 lookVec = Vec3.atLowerCornerOf(facing.getUnitVec3i());
            this.golem.getLookControl().setLookAt(pos.x + lookVec.x * 2.0D, pos.y + 0.5D, pos.z + lookVec.z * 2.0D, 30.0F, 30.0F);
        } else {
            this.golem.getLookControl().setLookAt(targetX, targetY + 0.5D, targetZ, 30.0F, 30.0F);
        }

        this.golem.getMoveControl().setWantedPosition(targetX, targetY, targetZ, this.speedModifier);

        if (horizDistSq > 1.5D || dy >= 0.5D) {
            if (this.repathCooldown > 0) {
                this.repathCooldown--;
            } else if (this.golem.getNavigation().isDone() || this.golem.getNavigation().getPath() == null) {
                this.repathCooldown = 15;
                this.golem.getNavigation().moveTo(targetX, targetY, targetZ, this.speedModifier);
            }
        }
    }
}
