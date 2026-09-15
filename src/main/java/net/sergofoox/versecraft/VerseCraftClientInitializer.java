package net.sergofoox.versecraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.sergofoox.versecraft.registry.RegisterEntityTypes;

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

        EntityRendererRegistry.register(RegisterEntityTypes.AZALEA_BOAT, context -> new BoatRenderer(context, AZALEA_BOAT_LAYER));
        EntityRendererRegistry.register(RegisterEntityTypes.AZALEA_CHEST_BOAT, context -> new BoatRenderer(context, AZALEA_CHEST_BOAT_LAYER));
    }
}

