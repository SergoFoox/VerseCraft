package net.sergofoox.versecraft.entity.model;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.Identifier;
import net.sergofoox.versecraft.VerseCraft;
import net.sergofoox.versecraft.entity.TuffGolem;

public class TuffGolemRenderer extends MobRenderer<TuffGolem, TuffGolemRenderState, TuffGolemModel> {

    private static final Identifier TEXTURE = VerseCraft.id("textures/entity/tuff_golem/tuff_golem.png");

    public TuffGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new TuffGolemModel(context.bakeLayer(TuffGolemModel.TUFF_GOLEM)), 0.4F);
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public Identifier getTextureLocation(TuffGolemRenderState state) {
        return TEXTURE;
    }

    @Override
    public TuffGolemRenderState createRenderState() {
        return new TuffGolemRenderState();
    }

    @Override
    public void extractRenderState(TuffGolem entity, TuffGolemRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        ArmedEntityRenderState.extractArmedEntityRenderState(entity, state, this.itemModelResolver, partialTick);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.walkingAnimationState.copyFrom(entity.walkingAnimationState);
        state.sleepStartAnimationState.copyFrom(entity.sleepStartAnimationState);
        state.sleepOverAnimationState.copyFrom(entity.sleepOverAnimationState);
    }
}
