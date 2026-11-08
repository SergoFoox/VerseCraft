package net.sergofoox.versecraft.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.Path;
import net.sergofoox.versecraft.entity.Moobloom;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MoobloomEatButtercupGoal extends MoveToBlockGoal {
    private final Moobloom moobloom;
    private final int searchRadius;
    private final int verticalRange;
    private int eatingTicks;
    private boolean hasEaten;
    @Nullable
    private Path cachedPath;
    private final Map<BlockPos, Long> unreachableCache = new HashMap<>();

    public MoobloomEatButtercupGoal(Moobloom moobloom, double speedModifier, int searchRange) {
        super(moobloom, speedModifier, searchRange, 3);
        this.moobloom = moobloom;
        this.searchRadius = searchRange;
        this.verticalRange = 3;
        this.nextStartTick = this.nextStartTick(this.moobloom);
    }

    public void onFlowerLost() {
        this.nextStartTick = 0;
    }

    private boolean isTemporarilyUnreachable(BlockPos pos) {
        Long expireTick = this.unreachableCache.get(pos);
        if (expireTick == null) {
            return false;
        }
        if (this.moobloom.level().getGameTime() > expireTick) {
            this.unreachableCache.remove(pos);
            return false;
        }
        return true;
    }

    private void markUnreachable(BlockPos pos) {
        if (this.unreachableCache.size() > 50) {
            this.unreachableCache.clear();
        }
        this.unreachableCache.put(pos.immutable(), this.moobloom.level().getGameTime() + 400L);
    }

    @Override
    public boolean canUse() {
        if (!this.moobloom.isAlive()) {
            return false;
        }
        if (this.moobloom.isWaitingForBee()) {
            return false;
        }
        if (this.moobloom.isGolden() && this.moobloom.getFlowerMask() == Moobloom.ALL_FLOWERS) {
            return false;
        }
        if (this.moobloom.getFlowerMask() != Moobloom.ALL_FLOWERS) {
            if (!this.moobloom.isGolden() && this.nextStartTick > 40) {
                this.nextStartTick = this.nextStartTick(this.mob);
            }
        }
        if (this.nextStartTick > 0) {
            this.nextStartTick--;
            return false;
        }
        this.nextStartTick = this.nextStartTick(this.mob);

        if (this.moobloom.isGolden()) {
            if (this.moobloom.getRandom().nextFloat() >= 0.5F) {
                return false;
            }
        }

        return this.findNearestBlock();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.hasEaten) {
            return false;
        }
        if (this.eatingTicks > 0) {
            return this.eatingTicks <= 40;
        }
        if (!this.moobloom.isAlive()) {
            return false;
        }
        if (this.moobloom.isWaitingForBee()) {
            return false;
        }
        if (this.moobloom.isGolden() && this.moobloom.getFlowerMask() == Moobloom.ALL_FLOWERS) {
            return false;
        }
        if (!this.moobloom.isGolden() && this.moobloom.getFlowerMask() == Moobloom.ALL_FLOWERS) {
            if (!this.moobloom.level().getBlockState(this.blockPos).is(Blocks.GOLDEN_DANDELION)) {
                return false;
            }
        }
        if (this.tryTicks > 400) {
            this.markUnreachable(this.blockPos);
            return false;
        }
        return super.canContinueToUse();
    }

    @Override
    public double acceptedDistance() {
        return 1.4D;
    }

    @Override
    protected BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    protected void moveMobToBlock() {
        if (this.cachedPath != null) {
            this.mob.getNavigation().moveTo(this.cachedPath, this.speedModifier);
            this.cachedPath = null;
        } else {
            this.mob.getNavigation().moveTo(
                    (double) this.blockPos.getX() + 0.5D,
                    (double) this.blockPos.getY(),
                    (double) this.blockPos.getZ() + 0.5D,
                    this.speedModifier
            );
        }
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 15 == 0 || this.mob.getNavigation().isDone();
    }

    @Override
    protected boolean findNearestBlock() {
        int range = this.searchRadius;
        int verticalSearch = this.verticalRange;
        BlockPos mobPos = this.mob.blockPosition();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int y = 0; y <= verticalSearch; y = y > 0 ? -y : 1 - y) {
            for (int l = 0; l < range; ++l) {
                for (int m = 0; m <= l; m = m > 0 ? -m : 1 - m) {
                    for (int n = m < l && m > -l ? l : 0; n <= l; n = n > 0 ? -n : 1 - n) {
                        mutablePos.setWithOffset(mobPos, m, y, n);
                        if (this.mob.isWithinHome(mutablePos) && this.isValidTarget(this.mob.level(), mutablePos)) {
                            if (this.isTemporarilyUnreachable(mutablePos)) {
                                continue;
                            }
                            Path path = this.mob.getNavigation().createPath(mutablePos, 1);
                            if (path != null && path.canReach()) {
                                this.blockPos = mutablePos.immutable();
                                this.cachedPath = path;
                                return true;
                            } else {
                                this.markUnreachable(mutablePos);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        boolean isGoldenDandelion = level.getBlockState(pos).is(Blocks.GOLDEN_DANDELION);
        if (isGoldenDandelion) {
            if (this.moobloom.isGolden()) {
                return this.moobloom.getFlowerMask() != Moobloom.ALL_FLOWERS;
            }
            return true;
        }

        boolean isButtercup = level.getBlockState(pos).is(RegisterBlocks.BUTTERCUP);
        return this.moobloom.getFlowerMask() != Moobloom.ALL_FLOWERS && isButtercup;
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
        this.cachedPath = null;
        this.nextStartTick = this.nextStartTick(this.mob);
    }

    @Override
    protected int nextStartTick(PathfinderMob mob) {
        if (this.moobloom.isGolden()) {
            return 300 + mob.getRandom().nextInt(300);
        }
        if (this.moobloom.getFlowerMask() == Moobloom.ALL_FLOWERS) {
            return 1200 + mob.getRandom().nextInt(1200);
        }
        return 20 + mob.getRandom().nextInt(20);
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
                    boolean isButtercup = serverLevel.getBlockState(this.blockPos).is(RegisterBlocks.BUTTERCUP);
                    boolean isGoldenDandelion = serverLevel.getBlockState(this.blockPos).is(Blocks.GOLDEN_DANDELION);
                    if (isButtercup || isGoldenDandelion) {
                        boolean mobGriefing = serverLevel.getGameRules().get(GameRules.MOB_GRIEFING);
                        if (mobGriefing) {
                            serverLevel.destroyBlock(this.blockPos, false);
                        }
                        if (isGoldenDandelion && !this.moobloom.isGolden()) {
                            this.moobloom.turnGolden();
                        } else {
                            this.moobloom.onAteButtercup();
                        }
                    }
                }
            } else if (this.eatingTicks >= 40) {
                this.hasEaten = true;
            }
        }
    }
}
