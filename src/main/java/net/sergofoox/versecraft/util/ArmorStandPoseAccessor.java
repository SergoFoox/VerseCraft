package net.sergofoox.versecraft.util;

import net.minecraft.core.Rotations;

public interface ArmorStandPoseAccessor {
    boolean versecraft$isInterpolating();
    Rotations versecraft$getInterpolatedHeadPose(float partialTick);
    Rotations versecraft$getInterpolatedBodyPose(float partialTick);
    Rotations versecraft$getInterpolatedLeftArmPose(float partialTick);
    Rotations versecraft$getInterpolatedRightArmPose(float partialTick);
    Rotations versecraft$getInterpolatedLeftLegPose(float partialTick);
    Rotations versecraft$getInterpolatedRightLegPose(float partialTick);
}
