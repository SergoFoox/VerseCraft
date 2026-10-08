package net.sergofoox.versecraft.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gamerules.GameRules;
import net.sergofoox.versecraft.entity.Moobloom;
import net.sergofoox.versecraft.registry.RegisterBlocks;

public class MoobloomEatButtercupGoal extends MoveToBlockGoal {
    private final Moobloom moobloom;
    private int eatingTicks;
    private boolean hasEaten;

    public MoobloomEatButtercupGoal(Moobloom moobloom, double speedModifier, int searchRange) {
        super(moobloom, speedModifier, searchRange, 3);
        this.moobloom = moobloom;
    }

    @Override
    public boolean canUse() {
        if (!this.moobloom.isAlive() || this.moobloom.isBaby()) {
            return false;
        }
        if (this.moobloom.getFlowerMask() == Moobloom.ALL_FLOWERS) {
            return false;
        }
        if (this.moobloom.isWaitingForBee()) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.hasEaten) {
            return false;
        }
        if (!this.moobloom.isAlive() || this.moobloom.isBaby()) {
            return false;
        }
        if (this.moobloom.isWaitingForBee()) {
            return false;
        }
        if (this.moobloom.getFlowerMask() == Moobloom.ALL_FLOWERS) {
            return false;
        }
        if (this.eatingTicks > 0) {
            return this.eatingTicks <= 40;
        }
        if (this.tryTicks > 200) {
            return false;
        }
        return super.canContinueToUse();
    }

    @Override
    public double acceptedDistance() {
        return 2.2D;
    }

    @Override
    protected BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return level.getBlockState(pos).is(RegisterBlocks.BUTTERCUP);
    }

    @Override
    public void start() {
        this.eatingTicks = 0;
        this.hasEaten = false;
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        this.eatingTicks = 0;
        this.hasEaten = false;
        this.nextStartTick = this.nextStartTick(this.mob);
    }

    @Override
    protected int nextStartTick(PathfinderMob mob) {
        return 100 + mob.getRandom().nextInt(100);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isReachedTarget()) {
            this.moobloom.getNavigation().stop();
            this.moobloom.getLookControl().setLookAt(
                    this.blockPos.getX() + 0.5D,
                    this.blockPos.getY() + 0.2D,
                    this.blockPos.getZ() + 0.5D
            );

            if (this.eatingTicks == 0) {
                this.moobloom.level().broadcastEntityEvent(this.moobloom, (byte) 10);
                this.moobloom.playSound(SoundEvents.MOOSHROOM_EAT, 1.5F, 1.0F);
            }

            this.eatingTicks++;

            if (this.eatingTicks == 32) {
                if (this.moobloom.level() instanceof ServerLevel serverLevel) {
                    if (serverLevel.getBlockState(this.blockPos).is(RegisterBlocks.BUTTERCUP)) {
                        boolean mobGriefing = serverLevel.getGameRules().get(GameRules.MOB_GRIEFING);
                        if (mobGriefing) {
                            serverLevel.destroyBlock(this.blockPos, false);
                        }
                        this.moobloom.onAteButtercup();
                    }
                }
            } else if (this.eatingTicks >= 40) {
                this.hasEaten = true;
            }
        }
    }
}
