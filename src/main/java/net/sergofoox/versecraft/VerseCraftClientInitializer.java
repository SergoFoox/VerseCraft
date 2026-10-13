package net.sergofoox.versecraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.state.BlockState;
import net.sergofoox.versecraft.block.entity.DyedCauldronBlockEntity;
import net.sergofoox.versecraft.block.entity.PotionCauldronBlockEntity;
import net.sergofoox.versecraft.client.renderer.blockentity.TuffGolemBlockEntityRenderer;
import net.sergofoox.versecraft.client.renderer.entity.MoobloomRenderer;
import net.sergofoox.versecraft.entity.model.TuffGolemModel;
import net.sergofoox.versecraft.entity.model.TuffGolemRenderer;
import net.sergofoox.versecraft.registry.RegisterBlockEntities;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import net.sergofoox.versecraft.registry.RegisterEntityTypes;

import java.util.List;

public class VerseCraftClientInitializer implements ClientModInitializer {

    public static final ModelLayerLocation AZALEA_BOAT_LAYER = new ModelLayerLocation(
            VerseCraft.id("boat/azalea"), "main"
    );
    public static final ModelLayerLocation AZALEA_CHEST_BOAT_LAYER = new ModelLayerLocation(
            VerseCraft.id("chest_boat/azalea"), "main"
    );

    @Override
    public void onInitializeClient() {

        ModelLayerRegistry.registerModelLayer(AZALEA_BOAT_LAYER, BoatModel::createBoatModel);
        ModelLayerRegistry.registerModelLayer(AZALEA_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
        ModelLayerRegistry.registerModelLayer(TuffGolemModel.TUFF_GOLEM, TuffGolemModel::getTexturedModelData);

        EntityRendererRegistry.register(RegisterEntityTypes.AZALEA_BOAT, context -> new BoatRenderer(context, AZALEA_BOAT_LAYER));
        EntityRendererRegistry.register(RegisterEntityTypes.AZALEA_CHEST_BOAT, context -> new BoatRenderer(context, AZALEA_CHEST_BOAT_LAYER));
        EntityRendererRegistry.register(RegisterEntityTypes.MOOBLOOM, MoobloomRenderer::new);
        EntityRendererRegistry.register(RegisterEntityTypes.TUFF_GOLEM, TuffGolemRenderer::new);

        BlockEntityRendererRegistry.register(RegisterBlockEntities.TUFF_GOLEM_BLOCK_ENTITY, TuffGolemBlockEntityRenderer::new);

        BlockColorRegistry.register(
                List.of(new BlockTintSource() {
                    @Override
                    public int color(BlockState state) {
                        return DyedCauldronBlockEntity.DEFAULT_WATER_COLOR;
                    }

                    @Override
                    public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                        if (level != null && pos != null) {
                            if (level.getBlockEntity(pos) instanceof DyedCauldronBlockEntity be) {
                                return be.getColor();
                            }
                        }
                        return DyedCauldronBlockEntity.DEFAULT_WATER_COLOR;
                    }
                }),
                RegisterBlocks.DYED_WATER_CAULDRON
        );

        BlockColorRegistry.register(
                List.of(new BlockTintSource() {
                    @Override
                    public int color(BlockState state) {
                        return PotionContents.getColorOptional(List.of()).orElse(0x385DC6);
                    }

                    @Override
                    public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                        if (level != null && pos != null) {
                            if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity be) {
                                return be.getColor();
                            }
                        }
                        return PotionContents.getColorOptional(List.of()).orElse(0x385DC6);
                    }
                }),
                RegisterBlocks.POTION_CAULDRON
        );
    }
}
