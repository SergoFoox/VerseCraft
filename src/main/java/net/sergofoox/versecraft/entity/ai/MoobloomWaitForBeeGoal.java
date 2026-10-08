package net.sergofoox.versecraft.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.bee.Bee;
import net.sergofoox.versecraft.entity.Moobloom;

import java.util.EnumSet;

public class MoobloomWaitForBeeGoal extends Goal {
    private final Moobloom moobloom;

    public MoobloomWaitForBeeGoal(Moobloom moobloom) {
        this.moobloom = moobloom;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
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
        Bee bee = this.moobloom.getPollinatingBee();
        if (bee != null && bee.isAlive()) {
            this.moobloom.getLookControl().setLookAt(bee, 15.0F, 15.0F);
        }
    }
}
