package net.sergofoox.versecraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Rotations;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sergofoox.versecraft.util.ArmorStandPoseAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin extends LivingEntity implements ArmorStandPoseAccessor {

    @Shadow public abstract void setShowArms(boolean showArms);
    @Shadow public abstract void setHeadPose(Rotations rotations);
    @Shadow public abstract void setBodyPose(Rotations rotations);
    @Shadow public abstract void setLeftArmPose(Rotations rotations);
    @Shadow public abstract void setRightArmPose(Rotations rotations);
    @Shadow public abstract void setLeftLegPose(Rotations rotations);
    @Shadow public abstract void setRightLegPose(Rotations rotations);

    @Shadow public abstract Rotations getHeadPose();
    @Shadow public abstract Rotations getBodyPose();
    @Shadow public abstract Rotations getLeftArmPose();
    @Shadow public abstract Rotations getRightArmPose();
    @Shadow public abstract Rotations getLeftLegPose();
    @Shadow public abstract Rotations getRightLegPose();

    @Unique
    private int versecraft$poseIndex = 0;
    @Unique
    private int versecraft$lastRedstoneSignal = 0;

    @Unique private static final long VERSECRAFT$TRANSITION_DURATION_MS = 180L;
    @Unique private long versecraft$transitionStartTime = 0L;

    @Unique private Rotations versecraft$startHead;
    @Unique private Rotations versecraft$startBody;
    @Unique private Rotations versecraft$startLeftArm;
    @Unique private Rotations versecraft$startRightArm;
    @Unique private Rotations versecraft$startLeftLeg;
    @Unique private Rotations versecraft$startRightLeg;

    @Unique private Rotations versecraft$targetHead;
    @Unique private Rotations versecraft$targetBody;
    @Unique private Rotations versecraft$targetLeftArm;
    @Unique private Rotations versecraft$targetRightArm;
    @Unique private Rotations versecraft$targetLeftLeg;
    @Unique private Rotations versecraft$targetRightLeg;

    @Unique
    private record BedrockPose(
            Rotations head,
            Rotations body,
            Rotations rightArm,
            Rotations leftArm,
            Rotations rightLeg,
            Rotations leftLeg
    ) {}

    @Unique
    private static final BedrockPose[] VERSECRAFT$BEDROCK_POSES = new BedrockPose[]{
            new BedrockPose(new Rotations(0.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(-15.0f, 0.0f, 10.0f), new Rotations(-10.0f, 0.0f, -10.0f), new Rotations(1.0f, 0.0f, 1.0f), new Rotations(-1.0f, 0.0f, -1.0f)),
            new BedrockPose(new Rotations(0.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f)),
            new BedrockPose(new Rotations(15.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 2.0f), new Rotations(-60.0f, -20.0f, -10.0f), new Rotations(-30.0f, 15.0f, 15.0f), new Rotations(1.0f, 0.0f, 1.0f), new Rotations(-1.0f, 0.0f, -1.0f)),
            new BedrockPose(new Rotations(-5.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 2.0f), new Rotations(-60.0f, 20.0f, -10.0f), new Rotations(10.0f, 0.0f, -5.0f), new Rotations(3.0f, 3.0f, 3.0f), new Rotations(-3.0f, -3.0f, -3.0f)),
            new BedrockPose(new Rotations(-15.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, -2.0f), new Rotations(-110.0f, 50.0f, 0.0f), new Rotations(20.0f, 0.0f, -10.0f), new Rotations(-5.0f, 3.0f, 3.0f), new Rotations(5.0f, -3.0f, -3.0f)),
            new BedrockPose(new Rotations(-15.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(-110.0f, -35.0f, 0.0f), new Rotations(-110.0f, 35.0f, 0.0f), new Rotations(-5.0f, 3.0f, 3.0f), new Rotations(5.0f, -3.0f, -3.0f)),
            new BedrockPose(new Rotations(-15.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(-110.0f, 35.0f, 0.0f), new Rotations(-110.0f, -35.0f, 0.0f), new Rotations(-5.0f, 3.0f, 3.0f), new Rotations(5.0f, -3.0f, -3.0f)),
            new BedrockPose(new Rotations(0.0f, 0.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(-70.0f, -40.0f, 0.0f), new Rotations(10.0f, 0.0f, -5.0f), new Rotations(1.0f, 0.0f, 1.0f), new Rotations(-1.0f, 0.0f, -1.0f)),
            new BedrockPose(new Rotations(16.0f, 20.0f, 0.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(-114.0f, 0.0f, 89.0f), new Rotations(4.0f, 8.0f, -123.0f), new Rotations(8.0f, 20.0f, 4.0f), new Rotations(-14.0f, -18.0f, -16.0f)),
            new BedrockPose(new Rotations(-10.0f, 0.0f, -5.0f), new Rotations(0.0f, 0.0f, 0.0f), new Rotations(-100.0f, 0.0f, 0.0f), new Rotations(-105.0f, 0.0f, 0.0f), new Rotations(-46.0f, 0.0f, 0.0f), new Rotations(7.0f, 0.0f, 0.0f)),
            new BedrockPose(new Rotations(-5.0f, 18.0f, 0.0f), new Rotations(0.0f, 22.0f, 0.0f), new Rotations(0.0f, 84.0f, 111.0f), new Rotations(8.0f, 0.0f, -114.0f), new Rotations(0.0f, 23.0f, -13.0f), new Rotations(-111.0f, 55.0f, 0.0f)),
            new BedrockPose(new Rotations(-10.0f, -20.0f, 0.0f), new Rotations(0.0f, -18.0f, 0.0f), new Rotations(8.0f, 90.0f, 111.0f), new Rotations(0.0f, 0.0f, -112.0f), new Rotations(-119.0f, -42.0f, 0.0f), new Rotations(0.0f, 0.0f, 13.0f)),
            new BedrockPose(new Rotations(-4.0f, 67.0f, 0.0f), new Rotations(0.0f, 8.0f, 0.0f), new Rotations(-99.0f, 63.0f, 0.0f), new Rotations(16.0f, 32.0f, -8.0f), new Rotations(4.0f, 63.0f, 8.0f), new Rotations(0.0f, -75.0f, -8.0f))
    };

    protected ArmorStandMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
    private void versecraft$initShowArms(EntityType<? extends ArmorStand> type, Level level, CallbackInfo ci) {
        this.setShowArms(true);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void versecraft$onInteract(Player player, InteractionHand hand, Vec3 hitPos, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
            int nextIndex = (this.versecraft$poseIndex + 1) % VERSECRAFT$BEDROCK_POSES.length;
            this.versecraft$poseIndex = nextIndex;

            if (!this.level().isClientSide()) {
                this.versecraft$setPoseIndex(nextIndex);
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.ARMOR_STAND_HIT, SoundSource.PLAYERS, 0.4f, 1.2f);
            } else {
                this.versecraft$applyClientTargetPose(VERSECRAFT$BEDROCK_POSES[nextIndex]);
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(method = "onSyncedDataUpdated", at = @At("TAIL"))
    private void versecraft$onSyncedDataUpdated(EntityDataAccessor<?> key, CallbackInfo ci) {
        if (this.level().isClientSide()) {
            this.versecraft$checkPoseChanged();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.versecraft$checkPoseChanged();
        } else if (this.isAlive()) {
            BlockPos pos = this.blockPosition();
            int signal = this.level().getBestNeighborSignal(pos);
            if (signal == 0) {
                signal = this.level().getBestNeighborSignal(pos.below());
            }

            if (signal != this.versecraft$lastRedstoneSignal) {
                if (signal > 0) {
                    int targetPose = Math.min(signal, 12);
                    this.versecraft$setPoseIndex(targetPose);
                }
                this.versecraft$lastRedstoneSignal = signal;
            }
        }
    }

    @Unique
    private void versecraft$applyClientTargetPose(BedrockPose pose) {
        versecraft$startTransitionTo(pose.head(), pose.body(), pose.leftArm(), pose.rightArm(), pose.leftLeg(), pose.rightLeg());
    }

    @Unique
    private void versecraft$checkPoseChanged() {
        Rotations head = this.getHeadPose();
        Rotations body = this.getBodyPose();
        Rotations leftArm = this.getLeftArmPose();
        Rotations rightArm = this.getRightArmPose();
        Rotations leftLeg = this.getLeftLegPose();
        Rotations rightLeg = this.getRightLegPose();

        if (this.versecraft$targetHead == null) {
            this.versecraft$targetHead = head;
            this.versecraft$targetBody = body;
            this.versecraft$targetLeftArm = leftArm;
            this.versecraft$targetRightArm = rightArm;
            this.versecraft$targetLeftLeg = leftLeg;
            this.versecraft$targetRightLeg = rightLeg;

            this.versecraft$startHead = head;
            this.versecraft$startBody = body;
            this.versecraft$startLeftArm = leftArm;
            this.versecraft$startRightArm = rightArm;
            this.versecraft$startLeftLeg = leftLeg;
            this.versecraft$startRightLeg = rightLeg;
            return;
        }

        if (!head.equals(this.versecraft$targetHead)
                || !body.equals(this.versecraft$targetBody)
                || !leftArm.equals(this.versecraft$targetLeftArm)
                || !rightArm.equals(this.versecraft$targetRightArm)
                || !leftLeg.equals(this.versecraft$targetLeftLeg)
                || !rightLeg.equals(this.versecraft$targetRightLeg)) {

            versecraft$startTransitionTo(head, body, leftArm, rightArm, leftLeg, rightLeg);
        }
    }

    @Unique
    private void versecraft$startTransitionTo(Rotations head, Rotations body, Rotations leftArm, Rotations rightArm, Rotations leftLeg, Rotations rightLeg) {
        long now = System.currentTimeMillis();
        float currentFactor = this.versecraft$getCurrentEasedFactor(now);

        this.versecraft$startHead = versecraft$lerp(this.versecraft$startHead, this.versecraft$targetHead, currentFactor);
        this.versecraft$startBody = versecraft$lerp(this.versecraft$startBody, this.versecraft$targetBody, currentFactor);
        this.versecraft$startLeftArm = versecraft$lerp(this.versecraft$startLeftArm, this.versecraft$targetLeftArm, currentFactor);
        this.versecraft$startRightArm = versecraft$lerp(this.versecraft$startRightArm, this.versecraft$targetRightArm, currentFactor);
        this.versecraft$startLeftLeg = versecraft$lerp(this.versecraft$startLeftLeg, this.versecraft$targetLeftLeg, currentFactor);
        this.versecraft$startRightLeg = versecraft$lerp(this.versecraft$startRightLeg, this.versecraft$targetRightLeg, currentFactor);

        this.versecraft$targetHead = head;
        this.versecraft$targetBody = body;
        this.versecraft$targetLeftArm = leftArm;
        this.versecraft$targetRightArm = rightArm;
        this.versecraft$targetLeftLeg = leftLeg;
        this.versecraft$targetRightLeg = rightLeg;

        this.versecraft$transitionStartTime = now;
    }

    @Unique
    private float versecraft$getCurrentEasedFactor(long now) {
        if (this.versecraft$transitionStartTime == 0L) return 1.0f;
        long elapsed = now - this.versecraft$transitionStartTime;
        if (elapsed >= VERSECRAFT$TRANSITION_DURATION_MS) return 1.0f;
        float t = (float) elapsed / (float) VERSECRAFT$TRANSITION_DURATION_MS;
        float inv = 1.0f - t;
        return 1.0f - inv * inv * inv;
    }

    @Unique
    private static Rotations versecraft$lerp(Rotations from, Rotations to, float factor) {
        if (from == null) return to;
        if (to == null) return from;
        return new Rotations(
                Mth.lerp(factor, from.x(), to.x()),
                Mth.lerp(factor, from.y(), to.y()),
                Mth.lerp(factor, from.z(), to.z())
        );
    }

    @Override
    public boolean versecraft$isInterpolating() {
        return System.currentTimeMillis() - this.versecraft$transitionStartTime < VERSECRAFT$TRANSITION_DURATION_MS;
    }

    @Override
    public Rotations versecraft$getInterpolatedHeadPose(float partialTick) {
        float f = this.versecraft$getCurrentEasedFactor(System.currentTimeMillis());
        return versecraft$lerp(this.versecraft$startHead, this.versecraft$targetHead, f);
    }

    @Override
    public Rotations versecraft$getInterpolatedBodyPose(float partialTick) {
        float f = this.versecraft$getCurrentEasedFactor(System.currentTimeMillis());
        return versecraft$lerp(this.versecraft$startBody, this.versecraft$targetBody, f);
    }

    @Override
    public Rotations versecraft$getInterpolatedLeftArmPose(float partialTick) {
        float f = this.versecraft$getCurrentEasedFactor(System.currentTimeMillis());
        return versecraft$lerp(this.versecraft$startLeftArm, this.versecraft$targetLeftArm, f);
    }

    @Override
    public Rotations versecraft$getInterpolatedRightArmPose(float partialTick) {
        float f = this.versecraft$getCurrentEasedFactor(System.currentTimeMillis());
        return versecraft$lerp(this.versecraft$startRightArm, this.versecraft$targetRightArm, f);
    }

    @Override
    public Rotations versecraft$getInterpolatedLeftLegPose(float partialTick) {
        float f = this.versecraft$getCurrentEasedFactor(System.currentTimeMillis());
        return versecraft$lerp(this.versecraft$startLeftLeg, this.versecraft$targetLeftLeg, f);
    }

    @Override
    public Rotations versecraft$getInterpolatedRightLegPose(float partialTick) {
        float f = this.versecraft$getCurrentEasedFactor(System.currentTimeMillis());
        return versecraft$lerp(this.versecraft$startRightLeg, this.versecraft$targetRightLeg, f);
    }

    @Unique
    private void versecraft$setPoseIndex(int index) {
        this.versecraft$poseIndex = index;
        BedrockPose pose = VERSECRAFT$BEDROCK_POSES[index];
        this.setHeadPose(pose.head());
        this.setBodyPose(pose.body());
        this.setRightArmPose(pose.rightArm());
        this.setLeftArmPose(pose.leftArm());
        this.setRightLegPose(pose.rightLeg());
        this.setLeftLegPose(pose.leftLeg());
    }
}
