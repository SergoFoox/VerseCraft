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

    public TuffGolemReturnHomeGoal(TuffGolem golem, double speedModifier) {
        this.golem = golem;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return !this.golem.isBusyWithAnim() && !this.golem.getMainHandItem().isEmpty() && this.golem.getOriginPos() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.golem.isBusyWithAnim() && this.canUse();
    }

    @Override
    public void start() {
        this.repathCooldown = 0;
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

        // Arrival detection:
        // Must be standing inside home block and centered (within 0.28 blocks of center)
        boolean arrived = (currentPos.equals(home) && horizDistSq <= 0.08D && dy < 0.5D)
                || (currentPos.equals(home) && horizDistSq <= 0.16D && dy < 0.5D && this.golem.getNavigation().isDone());

        if (arrived) {
            this.golem.startSleeping();
            return;
        }

        // Close to home (< 2.5 blocks): direct MoveControl guides golem smoothly to the exact center
        if (horizDistSq <= 6.25D && dy < 1.0D) {
            this.golem.getMoveControl().setWantedPosition(targetX, targetY, targetZ, this.speedModifier);
        } else {
            // Further away: use navigation path, only repathing when path finishes or every 30 ticks
            if (this.repathCooldown > 0) {
                this.repathCooldown--;
            } else if (this.golem.getNavigation().isDone()) {
                this.repathCooldown = 30;
                this.golem.getNavigation().moveTo(targetX, targetY, targetZ, this.speedModifier);
            }
        }
    }
}
