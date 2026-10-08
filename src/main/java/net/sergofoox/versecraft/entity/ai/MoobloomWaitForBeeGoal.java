package net.sergofoox.versecraft.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.sergofoox.versecraft.entity.Moobloom;

import java.util.EnumSet;

public class MoobloomWaitForBeeGoal extends Goal {
    private final Moobloom moobloom;

    public MoobloomWaitForBeeGoal(Moobloom moobloom) {
        this.moobloom = moobloom;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return this.moobloom.isWaitingForBee();
    }

    @Override
    public boolean canContinueToUse() {
        return this.moobloom.isWaitingForBee();
    }

    @Override
    public void start() {
        this.moobloom.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.moobloom.getNavigation().stop();
    }
}
