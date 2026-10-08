package net.sergofoox.versecraft.client.renderer.entity.model;

import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.sergofoox.versecraft.client.renderer.entity.state.MoobloomRenderState;

public class MoobloomModel extends CowModel {

    public MoobloomModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        if (state instanceof MoobloomRenderState moobloomState) {
            if (moobloomState.headEatPositionScale > 0.0F) {
                this.getHead().y += moobloomState.headEatPositionScale * 9.0F * moobloomState.ageScale;
                this.getHead().xRot = moobloomState.headEatAngleScale;
            }
        }
    }
}
