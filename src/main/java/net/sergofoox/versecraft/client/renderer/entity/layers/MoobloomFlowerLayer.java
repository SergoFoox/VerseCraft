package net.sergofoox.versecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.sergofoox.versecraft.client.renderer.entity.state.MoobloomRenderState;

public class MoobloomFlowerLayer extends RenderLayer<MoobloomRenderState, CowModel> {

    public MoobloomFlowerLayer(RenderLayerParent<MoobloomRenderState, CowModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector buffer, int packedLight, MoobloomRenderState state, float yRot, float xRot) {
        if (state.isBaby || state.flowerModel.isEmpty() || state.flowerMask == 0) {
            return;
        }
        boolean outlineOnly = state.appearsGlowing() && state.isInvisible;
        if (state.isInvisible && !outlineOnly) {
            return;
        }
        int overlayCoords = LivingEntityRenderer.getOverlayCoords(state, 0.0F);

        if ((state.flowerMask & (1 << 0)) != 0) {
            poseStack.pushPose();
            poseStack.translate(0.2F, -0.35F, 0.5F);
            poseStack.rotateDegrees(Axis.YP, -48.0F);
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            submitFlowerBlock(poseStack, buffer, packedLight, outlineOnly, state.outlineColor, state.flowerModel, overlayCoords);
            poseStack.popPose();
        }

        if ((state.flowerMask & (1 << 1)) != 0) {
            poseStack.pushPose();
            poseStack.translate(0.2F, -0.35F, 0.5F);
            poseStack.rotateDegrees(Axis.YP, 42.0F);
            poseStack.translate(0.1F, 0.0F, -0.6F);
            poseStack.rotateDegrees(Axis.YP, -48.0F);
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            submitFlowerBlock(poseStack, buffer, packedLight, outlineOnly, state.outlineColor, state.flowerModel, overlayCoords);
            poseStack.popPose();
        }

        if ((state.flowerMask & (1 << 2)) != 0) {
            poseStack.pushPose();
            this.getParentModel().getHead().translateAndRotate(poseStack);
            poseStack.translate(0.0F, -0.7F, -0.2F);
            poseStack.rotateDegrees(Axis.YP, -78.0F);
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            submitFlowerBlock(poseStack, buffer, packedLight, outlineOnly, state.outlineColor, state.flowerModel, overlayCoords);
            poseStack.popPose();
        }
    }

    private void submitFlowerBlock(PoseStack poseStack, SubmitNodeCollector buffer, int packedLight, boolean outlineOnly, int outlineColor, BlockModelRenderState model, int overlayCoords) {
        if (outlineOnly) {
            model.submitOnlyOutline(poseStack, buffer, packedLight, overlayCoords, outlineColor);
        } else {
            model.submit(poseStack, buffer, packedLight, overlayCoords, outlineColor);
        }
    }
}
