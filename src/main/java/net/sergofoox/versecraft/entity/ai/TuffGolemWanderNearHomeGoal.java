package net.sergofoox.versecraft.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.sergofoox.versecraft.entity.TuffGolem;

import java.util.EnumSet;

public class TuffGolemWanderNearHomeGoal extends Goal {

    private final TuffGolem golem;
    private final double speedModifier;
    private int idleCooldown = 0;

    public TuffGolemWanderNearHomeGoal(TuffGolem golem, double speedModifier) {
        this.golem = golem;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.golem.isBusyWithAnim() || !this.golem.getMainHandItem().isEmpty()) {
            return false;
        }

        if (this.idleCooldown > 0) {
            this.idleCooldown--;
            return false;
        }

        return this.golem.getNavigation().isDone();
    }

    @Override
    public boolean canContinueToUse() {
        return !this.golem.isBusyWithAnim() && !this.golem.getNavigation().isDone() && this.golem.getMainHandItem().isEmpty();
    }

    @Override
    public void start() {
        BlockPos home = this.golem.getOriginPos();
        if (home == null) {
            home = this.golem.blockPosition();
        }

        Level level = this.golem.level();

        BlockPos target = null;
        for (int i = 0; i < 10; i++) {
            double angle = this.golem.getRandom().nextDouble() * Math.PI * 2.0;
            double radius = 2.0 + this.golem.getRandom().nextDouble() * 4.0;

            int x = (int) Math.floor(home.getX() + 0.5 + Math.cos(angle) * radius);
            int z = (int) Math.floor(home.getZ() + 0.5 + Math.sin(angle) * radius);

            for (int dy = 2; dy >= -2; dy--) {
                BlockPos check = new BlockPos(x, home.getY() + dy, z);
                BlockState state = level.getBlockState(check);
                BlockState below = level.getBlockState(check.below());
                if (state.canBeReplaced() && !below.isAir() && below.isFaceSturdy(level, check.below(), Direction.UP)) {
                    Path path = this.golem.getNavigation().createPath(check, 0);
                    if (path != null && path.canReach()) {
                        target = check;
                        break;
                    }
                }
            }
            if (target != null) {
                break;
            }
        }

        if (target != null) {
            this.golem.getNavigation().moveTo(target.getX() + 0.5D, target.getY(), target.getZ() + 0.5D, this.speedModifier);
        } else {
            this.golem.getNavigation().moveTo(home.getX() + 0.5D, home.getY(), home.getZ() + 0.5D, this.speedModifier);
        }
    }

    @Override
    public void stop() {
        this.idleCooldown = 15 + this.golem.getRandom().nextInt(20);
        this.golem.getNavigation().stop();
    }
}
