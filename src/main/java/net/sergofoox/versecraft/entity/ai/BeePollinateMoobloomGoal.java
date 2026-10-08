package net.sergofoox.versecraft.entity.ai;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.phys.Vec3;
import net.sergofoox.versecraft.entity.Moobloom;
import net.sergofoox.versecraft.mixin.BeeInvoker;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class BeePollinateMoobloomGoal extends Goal {
    private final Bee bee;
    @Nullable
    private Moobloom targetMoobloom;
    private int targetFlowerSlot = -1;
    private int pollinatingTicks;
    private int searchCooldown;
    private int timeToLive;

    public BeePollinateMoobloomGoal(Bee bee) {
        this.bee = bee;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.bee.hasNectar() || this.bee.hasStung() || this.bee.isAngry()) {
            return false;
        }
        if (this.searchCooldown-- > 0) {
            return false;
        }
        this.searchCooldown = 20 + this.bee.getRandom().nextInt(20);

        List<Moobloom> mooblooms = this.bee.level().getEntitiesOfClass(
                Moobloom.class,
                this.bee.getBoundingBox().inflate(16.0, 8.0, 16.0),
                Moobloom::hasAvailableFlower
        );

        if (mooblooms.isEmpty()) {
            return false;
        }

        mooblooms.sort(Comparator.comparingDouble(m -> m.distanceToSqr(this.bee)));

        for (Moobloom moobloom : mooblooms) {
            int slot = moobloom.claimFlowerSlot(this.bee);
            if (slot != -1) {
                this.targetMoobloom = moobloom;
                this.targetFlowerSlot = slot;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.targetMoobloom == null || !this.targetMoobloom.isAlive() || this.targetMoobloom.isBaby()) {
            return false;
        }
        if (this.targetFlowerSlot < 0 || !this.targetMoobloom.hasFlower(this.targetFlowerSlot)) {
            return false;
        }
        if (!this.targetMoobloom.isFlowerReservedBy(this.targetFlowerSlot, this.bee)) {
            return false;
        }
        if (this.bee.hasNectar() || this.bee.hasStung() || this.bee.isAngry()) {
            return false;
        }
        return this.timeToLive > 0;
    }

    @Override
    public void start() {
        this.pollinatingTicks = 0;
        this.timeToLive = 400;
        if (this.targetMoobloom != null && this.targetFlowerSlot != -1) {
            Vec3 hoverPos = this.targetMoobloom.getFlowerWorldPos(this.targetFlowerSlot);
            this.bee.getNavigation().moveTo(hoverPos.x, hoverPos.y, hoverPos.z, 1.25);
        }
    }

    @Override
    public void stop() {
        if (this.targetMoobloom != null) {
            this.targetMoobloom.removeActivePollinator(this.bee);
            if (this.targetFlowerSlot != -1) {
                this.targetMoobloom.releaseFlowerSlot(this.targetFlowerSlot, this.bee);
            }
        }
        this.targetMoobloom = null;
        this.targetFlowerSlot = -1;
        this.pollinatingTicks = 0;
        this.bee.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.targetMoobloom == null || this.targetFlowerSlot < 0) {
            return;
        }

        this.timeToLive--;
        Vec3 hoverPos = this.targetMoobloom.getFlowerWorldPos(this.targetFlowerSlot);
        double distSq = this.bee.distanceToSqr(hoverPos);

        // When approaching within ~4 blocks, tell Moobloom to stop and wait
        if (distSq < 18.0) {
            this.targetMoobloom.addActivePollinator(this.bee);
        }

        if (distSq > 1.2) {
            // Approach the air position directly above the specific flower
            this.bee.getNavigation().moveTo(hoverPos.x, hoverPos.y, hoverPos.z, 1.2);
            this.bee.getLookControl().setLookAt(hoverPos.x, hoverPos.y, hoverPos.z, 30.0F, 30.0F);
        } else {
            // Close range: stop path navigation to prevent ground/side clipping
            this.bee.getNavigation().stop();

            this.pollinatingTicks++;

            // Gentle hovering animation above the specific flower
            double bobY = hoverPos.y + Math.sin(this.pollinatingTicks * 0.25) * 0.05;
            this.bee.getMoveControl().setWantedPosition(hoverPos.x, bobY, hoverPos.z, 0.4);

            // Dampen horizontal drift so bee stays centered over the flower
            Vec3 delta = this.bee.getDeltaMovement();
            this.bee.setDeltaMovement(delta.x * 0.6, delta.y * 0.8, delta.z * 0.6);

            // Look down directly at the flower
            this.bee.getLookControl().setLookAt(hoverPos.x, hoverPos.y - 0.5, hoverPos.z, 30.0F, 30.0F);

            // Particles falling from bee onto the flower
            if (this.bee.level() instanceof ServerLevel serverLevel) {
                if (this.pollinatingTicks % 4 == 0) {
                    serverLevel.sendParticles(
                            ParticleTypes.FALLING_NECTAR,
                            hoverPos.x + (this.bee.getRandom().nextDouble() - 0.5) * 0.25,
                            hoverPos.y - 0.15,
                            hoverPos.z + (this.bee.getRandom().nextDouble() - 0.5) * 0.25,
                            1, 0, 0, 0, 0
                    );
                }
            }

            if (this.pollinatingTicks % 15 == 0) {
                this.bee.playSound(SoundEvents.BEE_POLLINATE, 0.8F, 1.0F + this.bee.getRandom().nextFloat() * 0.2F);
            }

            // Completed pollination after ~3.5 seconds
            if (this.pollinatingTicks >= 70) {
                if (this.bee instanceof BeeInvoker invoker) {
                    invoker.versecraft$invokeSetHasNectar(true);
                }
                this.bee.resetTicksWithoutNectarSinceExitingHive();

                int slot = this.targetFlowerSlot;
                Moobloom moobloom = this.targetMoobloom;
                this.targetFlowerSlot = -1;
                this.targetMoobloom = null;

                moobloom.removeActivePollinator(this.bee);
                moobloom.onPollinationSuccess(slot);
            }
        }
    }
}
