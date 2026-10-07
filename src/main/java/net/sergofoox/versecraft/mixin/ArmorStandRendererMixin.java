package net.sergofoox.versecraft.mixin;

import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.sergofoox.versecraft.util.ArmorStandPoseAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandRenderer.class)
public abstract class ArmorStandRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ArmorStand;Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;F)V",
            at = @At("TAIL")
    )
    private void versecraft$smoothPoseInterpolation(ArmorStand entity, ArmorStandRenderState state, float partialTick, CallbackInfo ci) {
        if (entity instanceof ArmorStandPoseAccessor accessor && accessor.versecraft$isInterpolating()) {
            state.headPose = accessor.versecraft$getInterpolatedHeadPose(partialTick);
            state.bodyPose = accessor.versecraft$getInterpolatedBodyPose(partialTick);
            state.leftArmPose = accessor.versecraft$getInterpolatedLeftArmPose(partialTick);
            state.rightArmPose = accessor.versecraft$getInterpolatedRightArmPose(partialTick);
            state.leftLegPose = accessor.versecraft$getInterpolatedLeftLegPose(partialTick);
            state.rightLegPose = accessor.versecraft$getInterpolatedRightLegPose(partialTick);
        }
    }
}
