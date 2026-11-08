package net.sergofoox.versecraft;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.sergofoox.versecraft.datagen.*;

public class VerseCraftDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(VerseBlocksLootTableProvider::new);
        pack.addProvider(VerseRecipesProvider::new);
        pack.addProvider(VerseBlockTagsProvider::new);
        pack.addProvider(VerseItemTagsProvider::new);
        pack.addProvider(VerseEntityTypeTagsProvider::new);
    }
}
