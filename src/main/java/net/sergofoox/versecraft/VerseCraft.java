package net.sergofoox.versecraft;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import net.sergofoox.versecraft.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerseCraft implements ModInitializer {
    public static final String MOD_ID = "versecraft";


    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



    @Override
    public void onInitialize() {

        RegisterBlocks.registerBlocks();
        RegisterCreativeInventorySorting.sortInventory();
        RegisterItems.registerItems();
        RegisterEntityTypes.registerEntitiesTypes();
        RegisterWorldgen.init();
        LOGGER.info("Hello Fabric world!");
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
