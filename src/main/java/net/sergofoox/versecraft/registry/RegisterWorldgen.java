package net.sergofoox.versecraft.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.sergofoox.versecraft.VerseCraft;

public class RegisterWorldgen {

    private static final ResourceKey<PlacedFeature> POP_FLOWER_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, VerseCraft.id("pop_flower_placed"));
    private static final ResourceKey<PlacedFeature> SHORT_MYCELIUM_GRASS_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, VerseCraft.id("short_mycelium_grass_placed"));
    private static final ResourceKey<PlacedFeature> TALL_MYCELIUM_GRASS_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, VerseCraft.id("tall_mycelium_grass_placed"));
    private static final ResourceKey<PlacedFeature> GLOWSHROOM_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, VerseCraft.id("glowshroom_placed"));


    public static void init() {
        //Feature Biome
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.MUSHROOM_FIELDS), GenerationStep.Decoration.VEGETAL_DECORATION, POP_FLOWER_PLACED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.MUSHROOM_FIELDS), GenerationStep.Decoration.VEGETAL_DECORATION, SHORT_MYCELIUM_GRASS_PLACED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.MUSHROOM_FIELDS), GenerationStep.Decoration.VEGETAL_DECORATION, TALL_MYCELIUM_GRASS_PLACED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.MUSHROOM_FIELDS), GenerationStep.Decoration.VEGETAL_DECORATION, GLOWSHROOM_PLACED);

        //Mob spawn
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SAVANNA), MobCategory.CREATURE, EntityTypes.LLAMA,8,1,4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_SAVANNA),MobCategory.CREATURE,EntityTypes.LLAMA,8,1,4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE),MobCategory.CREATURE,EntityTypes.PARROT,40,1,2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE),MobCategory.CREATURE,EntityTypes.OCELOT,30,1,2);
    }

}
