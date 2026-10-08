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
        this.searchCooldown = 30 + this.bee.getRandom().nextInt(20);

        List<Moobloom> mooblooms = this.bee.level().getEntitiesOfClass(
                Moobloom.class,
                this.bee.getBoundingBox().inflate(16.0, 8.0, 16.0),
                Moobloom::canBePollinated
        );

        if (mooblooms.isEmpty()) {
            return false;
        }

        this.targetMoobloom = mooblooms.stream()
                .min(Comparator.comparingDouble(m -> m.distanceToSqr(this.bee)))
                .orElse(null);

        return this.targetMoobloom != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.targetMoobloom == null || !this.targetMoobloom.isAlive() || this.targetMoobloom.isBaby()) {
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
        this.timeToLive = 300;
        if (this.targetMoobloom != null) {
            this.targetMoobloom.setReservedByBee(this.bee);
            Vec3 hoverPos = this.getFlowerHoverPos();
            this.bee.getNavigation().moveTo(hoverPos.x, hoverPos.y, hoverPos.z, 1.25);
        }
    }

    @Override
    public void stop() {
        if (this.targetMoobloom != null && this.targetMoobloom.getPollinatingBee() == this.bee) {
            this.targetMoobloom.clearReservedBee();
        }
        this.targetMoobloom = null;
        this.pollinatingTicks = 0;
        this.bee.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private Vec3 getFlowerHoverPos() {
        // Adult cow back is at ~1.35, flower extends to ~1.75.
        // Hovering at 1.85 puts the bee right above the flower on the cow's back.
        return this.targetMoobloom.position().add(0, 1.85, 0);
    }

    @Override
    public void tick() {
        if (this.targetMoobloom == null) {
            return;
        }

        this.timeToLive--;
        Vec3 hoverPos = this.getFlowerHoverPos();
        double distSq = this.bee.distanceToSqr(hoverPos);

        // When approaching within ~4 blocks, tell Moobloom to stop and wait
        if (distSq < 18.0) {
            this.targetMoobloom.startBeePollination(this.bee);
        }

        if (distSq > 1.5) {
            // Approach the air position directly above the flower
            this.bee.getNavigation().moveTo(hoverPos.x, hoverPos.y, hoverPos.z, 1.2);
            this.bee.getLookControl().setLookAt(hoverPos.x, hoverPos.y, hoverPos.z, 30.0F, 30.0F);
        } else {
            // Close range: stop path navigation to prevent ground/side clipping
            this.bee.getNavigation().stop();

            this.pollinatingTicks++;

            // Gentle hovering animation above the flower
            double bobY = hoverPos.y + Math.sin(this.pollinatingTicks * 0.25) * 0.05;
            this.bee.getMoveControl().setWantedPosition(hoverPos.x, bobY, hoverPos.z, 0.4);

            // Dampen horizontal drift so bee stays centered over the flower
            Vec3 delta = this.bee.getDeltaMovement();
            this.bee.setDeltaMovement(delta.x * 0.6, delta.y * 0.8, delta.z * 0.6);

            // Look down directly at the flowers on the cow's back
            this.bee.getLookControl().setLookAt(this.targetMoobloom.getX(), this.targetMoobloom.getY() + 1.35, this.targetMoobloom.getZ(), 30.0F, 30.0F);

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
                this.bee.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.2F);

                if (this.bee.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                            ParticleTypes.HAPPY_VILLAGER,
                            this.targetMoobloom.getX(),
                            this.targetMoobloom.getY() + 1.4,
                            this.targetMoobloom.getZ(),
                            8, 0.35, 0.3, 0.35, 0.05
                    );
                }

                this.targetMoobloom.onPollinationSuccess();
                this.targetMoobloom = null;
            }
        }
    }
}
