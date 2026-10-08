package net.sergofoox.versecraft.client.renderer.entity;

import net.minecraft.client.model.animal.cow.BabyCowModel;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.sergofoox.versecraft.VerseCraft;
import net.sergofoox.versecraft.client.renderer.entity.layers.MoobloomFlowerLayer;
import net.sergofoox.versecraft.client.renderer.entity.state.MoobloomRenderState;
import net.sergofoox.versecraft.entity.Moobloom;
import net.sergofoox.versecraft.registry.RegisterBlocks;

public class MoobloomRenderer extends AgeableMobRenderer<Moobloom, MoobloomRenderState, CowModel> {

    private static final Identifier TEXTURE = VerseCraft.id("textures/entity/moobloom/moobloom.png");
    private static final Identifier BABY_TEXTURE = VerseCraft.id("textures/entity/moobloom/moobloom_baby.png");
    private static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
    private final BlockModelResolver blockModelResolver;

    public MoobloomRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel(context.bakeLayer(ModelLayers.MOOSHROOM)), new BabyCowModel(context.bakeLayer(ModelLayers.MOOSHROOM_BABY)), 0.7F);
        this.blockModelResolver = context.getBlockModelResolver();
        this.addLayer(new MoobloomFlowerLayer(this));
    }

    @Override
    public MoobloomRenderState createRenderState() {
        return new MoobloomRenderState();
    }

    @Override
    public void extractRenderState(Moobloom entity, MoobloomRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.flowerMask = entity.getFlowerMask();
        this.blockModelResolver.update(state.flowerModel, RegisterBlocks.BUTTERCUP.defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
    }

    @Override
    public Identifier getTextureLocation(MoobloomRenderState state) {
        return state.isBaby ? BABY_TEXTURE : TEXTURE;
    }
}
