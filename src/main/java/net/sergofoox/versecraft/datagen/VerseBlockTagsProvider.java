package net.sergofoox.versecraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import net.sergofoox.versecraft.tags.VerseBlocksTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class VerseBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {

    public VerseBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(key(RegisterBlocks.CRACKED_TUFF_BRICKS))
                .add(key(RegisterBlocks.TUFF_GOLEM_STATUE))
                .add(key(RegisterBlocks.DRIPSTONE_STAIRS))
                .add(key(RegisterBlocks.DRIPSTONE_SLAB))
                .add(key(RegisterBlocks.DRIPSTONE_WALL))
                .add(key(RegisterBlocks.CHISELED_DRIPSTONE))
                .add(key(RegisterBlocks.POLISHED_DRIPSTONE))
                .add(key(RegisterBlocks.POLISHED_DRIPSTONE_STAIRS))
                .add(key(RegisterBlocks.POLISHED_DRIPSTONE_SLAB))
                .add(key(RegisterBlocks.POLISHED_DRIPSTONE_WALL))
                .add(key(RegisterBlocks.DRIPSTONE_BRICKS))
                .add(key(RegisterBlocks.CRACKED_DRIPSTONE_BRICKS))
                .add(key(RegisterBlocks.DRIPSTONE_BRICKS_STAIRS))
                .add(key(RegisterBlocks.DRIPSTONE_BRICKS_SLAB))
                .add(key(RegisterBlocks.DRIPSTONE_BRICKS_WALL))
                .add(key(RegisterBlocks.CHISELED_DRIPSTONE_BRICKS))
                .add(key(RegisterBlocks.POLISHED_DIORITE_WALL))
                .add(key(RegisterBlocks.CHISELED_DIORITE))
                .add(key(RegisterBlocks.DIORITE_BRICKS))
                .add(key(RegisterBlocks.CRACKED_DIORITE_BRICKS))
                .add(key(RegisterBlocks.DIORITE_BRICKS_STAIRS))
                .add(key(RegisterBlocks.DIORITE_BRICKS_SLAB))
                .add(key(RegisterBlocks.DIORITE_BRICKS_WALL))
                .add(key(RegisterBlocks.CHISELED_DIORITE_BRICKS))
                .add(key(RegisterBlocks.POLISHED_ANDESITE_WALL))
                .add(key(RegisterBlocks.CHISELED_ANDESITE))
                .add(key(RegisterBlocks.ANDESITE_BRICKS))
                .add(key(RegisterBlocks.CRACKED_ANDESITE_BRICKS))
                .add(key(RegisterBlocks.ANDESITE_BRICKS_STAIRS))
                .add(key(RegisterBlocks.ANDESITE_BRICKS_SLAB))
                .add(key(RegisterBlocks.ANDESITE_BRICKS_WALL))
                .add(key(RegisterBlocks.CHISELED_ANDESITE_BRICKS))
                .add(key(RegisterBlocks.POLISHED_GRANITE_WALL))
                .add(key(RegisterBlocks.CHISELED_GRANITE))
                .add(key(RegisterBlocks.GRANITE_BRICKS))
                .add(key(RegisterBlocks.CRACKED_GRANITE_BRICKS))
                .add(key(RegisterBlocks.GRANITE_BRICKS_STAIRS))
                .add(key(RegisterBlocks.GRANITE_BRICKS_SLAB))
                .add(key(RegisterBlocks.GRANITE_BRICKS_WALL))
                .add(key(RegisterBlocks.CHISELED_GRANITE_BRICKS))
                .add(key(RegisterBlocks.CALCITE_STAIRS))
                .add(key(RegisterBlocks.CALCITE_SLAB))
                .add(key(RegisterBlocks.CALCITE_WALL))
                .add(key(RegisterBlocks.CHISELED_CALCITE))
                .add(key(RegisterBlocks.POLISHED_CALCITE))
                .add(key(RegisterBlocks.POLISHED_CALCITE_STAIRS))
                .add(key(RegisterBlocks.POLISHED_CALCITE_SLAB))
                .add(key(RegisterBlocks.POLISHED_CALCITE_WALL))
                .add(key(RegisterBlocks.CALCITE_BRICKS))
                .add(key(RegisterBlocks.CRACKED_CALCITE_BRICKS))
                .add(key(RegisterBlocks.CALCITE_BRICKS_STAIRS))
                .add(key(RegisterBlocks.CALCITE_BRICKS_SLAB))
                .add(key(RegisterBlocks.CALCITE_BRICKS_WALL))
                .add(key(RegisterBlocks.CHISELED_CALCITE_BRICKS));

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(key(RegisterBlocks.AZALEA_MOSAIC))
                .add(key(RegisterBlocks.AZALEA_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.AZALEA_MOSAIC_SLAB))
                .add(key(RegisterBlocks.ACACIA_MOSAIC))
                .add(key(RegisterBlocks.ACACIA_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.ACACIA_MOSAIC_SLAB))
                .add(key(RegisterBlocks.BIRCH_MOSAIC))
                .add(key(RegisterBlocks.BIRCH_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.BIRCH_MOSAIC_SLAB))
                .add(key(RegisterBlocks.CHERRY_MOSAIC))
                .add(key(RegisterBlocks.CHERRY_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.CHERRY_MOSAIC_SLAB))
                .add(key(RegisterBlocks.CRIMSON_MOSAIC))
                .add(key(RegisterBlocks.CRIMSON_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.CRIMSON_MOSAIC_SLAB))
                .add(key(RegisterBlocks.DARK_OAK_MOSAIC))
                .add(key(RegisterBlocks.DARK_OAK_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.DARK_OAK_MOSAIC_SLAB))
                .add(key(RegisterBlocks.JUNGLE_MOSAIC))
                .add(key(RegisterBlocks.JUNGLE_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.JUNGLE_MOSAIC_SLAB))
                .add(key(RegisterBlocks.MANGROVE_MOSAIC))
                .add(key(RegisterBlocks.MANGROVE_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.MANGROVE_MOSAIC_SLAB))
                .add(key(RegisterBlocks.OAK_MOSAIC))
                .add(key(RegisterBlocks.OAK_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.OAK_MOSAIC_SLAB))
                .add(key(RegisterBlocks.SPRUCE_MOSAIC))
                .add(key(RegisterBlocks.SPRUCE_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.SPRUCE_MOSAIC_SLAB))
                .add(key(RegisterBlocks.PALE_OAK_MOSAIC))
                .add(key(RegisterBlocks.PALE_OAK_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.PALE_OAK_MOSAIC_SLAB))
                .add(key(RegisterBlocks.POPLAR_MOSAIC))
                .add(key(RegisterBlocks.POPLAR_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.POPLAR_MOSAIC_SLAB))
                .add(key(RegisterBlocks.WARPED_MOSAIC))
                .add(key(RegisterBlocks.WARPED_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.WARPED_MOSAIC_SLAB))
                .add(key(RegisterBlocks.SOUL_JACK_O_LANTERN))
                .add(key(RegisterBlocks.COPPER_JACK_O_LANTERN))
                .add(key(RegisterBlocks.COPPER_CAMPFIRE));

        tag(VerseBlocksTags.Blocks.AZALEA_LOGS)
                .add(key(RegisterBlocks.AZALEA_LOG))
                .add(key(RegisterBlocks.AZALEA_WOOD))
                .add(key(RegisterBlocks.STRIPPED_AZALEA_LOG))
                .add(key(RegisterBlocks.STRIPPED_AZALEA_WOOD));

        tag(BlockItemTags.LOGS_THAT_BURN.block())
                .addTag(VerseBlocksTags.Blocks.AZALEA_LOGS);

        tag(BlockTags.LOGS)
                .addTag(VerseBlocksTags.Blocks.AZALEA_LOGS);

        tag(BlockTags.BEE_ATTRACTIVE)
                .add(key(RegisterBlocks.BLUE_ROSE))
                .add(key(RegisterBlocks.ICEFLOWER))
                .add(key(RegisterBlocks.POP_FLOWER))
                .add(key(RegisterBlocks.BUTTERCUP));

        tag(BlockTags.CAMPFIRES)
                .add(key(RegisterBlocks.COPPER_CAMPFIRE));

        tag(BlockTags.CEILING_HANGING_SIGNS)
                .add(key(RegisterBlocks.AZALEA_HANGING_SIGN));

        tag(BlockTags.CROPS)
                .add(key(RegisterBlocks.BLUE_ROSE_CROP))
                .add(key(RegisterBlocks.ICEFLOWER_CROP));

        tag(BlockTags.FENCE_GATES)
                .add(key(RegisterBlocks.AZALEA_FENCE_GATE));

        tag(BlockTags.FLOWERS)
                .add(key(RegisterBlocks.BLUE_ROSE))
                .add(key(RegisterBlocks.ICEFLOWER))
                .add(key(RegisterBlocks.POP_FLOWER))
                .add(key(RegisterBlocks.BUTTERCUP));

        tag(BlockTags.FLOWER_POTS)
                .add(key(RegisterBlocks.POTTED_BLUE_ROSE))
                .add(key(RegisterBlocks.POTTED_ICEFLOWER))
                .add(key(RegisterBlocks.POTTED_POP_FLOWER))
                .add(key(RegisterBlocks.POTTED_BUTTERCUP));

        tag(BlockTags.MAINTAINS_FARMLAND)
                .add(key(RegisterBlocks.BLUE_ROSE))
                .add(key(RegisterBlocks.BLUE_ROSE_CROP))
                .add(key(RegisterBlocks.ICEFLOWER))
                .add(key(RegisterBlocks.ICEFLOWER_CROP))
                .add(key(RegisterBlocks.BUTTERCUP));

        tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(key(RegisterBlocks.AZALEA_LOG));

        tag(BlockTags.PLANKS)
                .add(key(RegisterBlocks.AZALEA_PLANKS));

        tag(BlockTags.SLABS)
                .add(key(RegisterBlocks.AZALEA_SLAB))
                .add(key(RegisterBlocks.ACACIA_MOSAIC_SLAB))
                .add(key(RegisterBlocks.AZALEA_MOSAIC_SLAB))
                .add(key(RegisterBlocks.BIRCH_MOSAIC_SLAB))
                .add(key(RegisterBlocks.CHERRY_MOSAIC_SLAB))
                .add(key(RegisterBlocks.CRIMSON_MOSAIC_SLAB))
                .add(key(RegisterBlocks.DARK_OAK_MOSAIC_SLAB))
                .add(key(RegisterBlocks.JUNGLE_MOSAIC_SLAB))
                .add(key(RegisterBlocks.MANGROVE_MOSAIC_SLAB))
                .add(key(RegisterBlocks.OAK_MOSAIC_SLAB))
                .add(key(RegisterBlocks.SPRUCE_MOSAIC_SLAB))
                .add(key(RegisterBlocks.PALE_OAK_MOSAIC_SLAB))
                .add(key(RegisterBlocks.POPLAR_MOSAIC_SLAB))
                .add(key(RegisterBlocks.WARPED_MOSAIC_SLAB))
                .add(key(RegisterBlocks.CALCITE_SLAB))
                .add(key(RegisterBlocks.POLISHED_CALCITE_SLAB))
                .add(key(RegisterBlocks.CALCITE_BRICKS_SLAB))
                .add(key(RegisterBlocks.DRIPSTONE_SLAB))
                .add(key(RegisterBlocks.POLISHED_DRIPSTONE_SLAB))
                .add(key(RegisterBlocks.DRIPSTONE_BRICKS_SLAB))
                .add(key(RegisterBlocks.DIORITE_BRICKS_SLAB))
                .add(key(RegisterBlocks.ANDESITE_BRICKS_SLAB))
                .add(key(RegisterBlocks.GRANITE_BRICKS_SLAB));

        tag(BlockTags.SMALL_FLOWERS)
                .add(key(RegisterBlocks.BLUE_ROSE))
                .add(key(RegisterBlocks.ICEFLOWER))
                .add(key(RegisterBlocks.POP_FLOWER))
                .add(key(RegisterBlocks.BUTTERCUP));

        tag(BlockTags.STAIRS)
                .add(key(RegisterBlocks.AZALEA_STAIRS))
                .add(key(RegisterBlocks.ACACIA_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.AZALEA_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.BIRCH_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.CHERRY_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.CRIMSON_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.DARK_OAK_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.JUNGLE_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.MANGROVE_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.OAK_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.SPRUCE_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.PALE_OAK_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.POPLAR_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.WARPED_MOSAIC_STAIRS))
                .add(key(RegisterBlocks.CALCITE_STAIRS))
                .add(key(RegisterBlocks.POLISHED_CALCITE_STAIRS))
                .add(key(RegisterBlocks.CALCITE_BRICKS_STAIRS))
                .add(key(RegisterBlocks.DRIPSTONE_STAIRS))
                .add(key(RegisterBlocks.POLISHED_DRIPSTONE_STAIRS))
                .add(key(RegisterBlocks.DRIPSTONE_BRICKS_STAIRS))
                .add(key(RegisterBlocks.DIORITE_BRICKS_STAIRS))
                .add(key(RegisterBlocks.ANDESITE_BRICKS_STAIRS))
                .add(key(RegisterBlocks.GRANITE_BRICKS_STAIRS));

        tag(BlockTags.STANDING_SIGNS)
                .add(key(RegisterBlocks.AZALEA_SIGN));

        tag(BlockTags.WALL_HANGING_SIGNS)
                .add(key(RegisterBlocks.AZALEA_WALL_HANGING_SIGN));

        tag(BlockTags.WALL_SIGNS)
                .add(key(RegisterBlocks.AZALEA_WALL_SIGN));

        tag(BlockTags.WALLS)
                .add(key(RegisterBlocks.CALCITE_WALL))
                .add(key(RegisterBlocks.POLISHED_CALCITE_WALL))
                .add(key(RegisterBlocks.CALCITE_BRICKS_WALL))
                .add(key(RegisterBlocks.DRIPSTONE_WALL))
                .add(key(RegisterBlocks.POLISHED_DRIPSTONE_WALL))
                .add(key(RegisterBlocks.DRIPSTONE_BRICKS_WALL))
                .add(key(RegisterBlocks.POLISHED_DIORITE_WALL))
                .add(key(RegisterBlocks.DIORITE_BRICKS_WALL))
                .add(key(RegisterBlocks.POLISHED_ANDESITE_WALL))
                .add(key(RegisterBlocks.ANDESITE_BRICKS_WALL))
                .add(key(RegisterBlocks.POLISHED_GRANITE_WALL))
                .add(key(RegisterBlocks.GRANITE_BRICKS_WALL));

        tag(BlockTags.WOODEN_BUTTONS)
                .add(key(RegisterBlocks.AZALEA_BUTTON));

        tag(BlockTags.WOODEN_DOORS)
                .add(key(RegisterBlocks.AZALEA_DOOR));

        tag(BlockTags.WOODEN_FENCES)
                .add(key(RegisterBlocks.AZALEA_FENCE));

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(key(RegisterBlocks.AZALEA_PRESSURE_PLATE));

        tag(BlockTags.WOODEN_SHELVES)
                .add(key(RegisterBlocks.AZALEA_SHELF));

        tag(BlockTags.WOODEN_SLABS)
                .add(key(RegisterBlocks.AZALEA_SLAB));

        tag(BlockTags.WOODEN_STAIRS)
                .add(key(RegisterBlocks.AZALEA_STAIRS));

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(key(RegisterBlocks.AZALEA_TRAPDOOR));

        tag(BlockTags.WASHED_AWAY_BY_FLUIDS)
                .add(key(RegisterBlocks.BLUE_ROSE))
                .add(key(RegisterBlocks.BLUE_ROSE_CROP))
                .add(key(RegisterBlocks.POTTED_BLUE_ROSE))
                .add(key(RegisterBlocks.ICEFLOWER))
                .add(key(RegisterBlocks.ICEFLOWER_CROP))
                .add(key(RegisterBlocks.POTTED_ICEFLOWER))
                .add(key(RegisterBlocks.POP_FLOWER))
                .add(key(RegisterBlocks.POTTED_POP_FLOWER))
                .add(key(RegisterBlocks.BUTTERCUP))
                .add(key(RegisterBlocks.POTTED_BUTTERCUP))
                .add(key(RegisterBlocks.GLOWSHROOM))
                .add(key(RegisterBlocks.SHORT_MYCELIUM_GRASS))
                .add(key(RegisterBlocks.TALL_MYCELIUM_GRASS));

        tag(BlockTags.REPLACEABLE)
                .add(key(RegisterBlocks.SHORT_MYCELIUM_GRASS))
                .add(key(RegisterBlocks.TALL_MYCELIUM_GRASS));

        tag(VerseBlocksTags.Blocks.IMMOVABLE)
                .add(key(Blocks.SPAWNER))
                .add(key(Blocks.TRIAL_SPAWNER))
                .add(key(Blocks.VAULT))
                .add(key(Blocks.BEACON))
                .add(key(Blocks.CONDUIT))
                .add(key(Blocks.END_PORTAL))
                .add(key(Blocks.END_GATEWAY))
                .add(key(Blocks.CREAKING_HEART))
                .add(key(Blocks.ENCHANTING_TABLE))
                .add(key(Blocks.ENDER_CHEST))
                .add(key(Blocks.JUKEBOX))
                .add(key(Blocks.SCULK_SENSOR))
                .add(key(Blocks.CALIBRATED_SCULK_SENSOR))
                .add(key(Blocks.SCULK_CATALYST))
                .add(key(Blocks.SCULK_SHRIEKER))
                .add(key(Blocks.GRINDSTONE))
                .add(key(Blocks.LODESTONE));
    }

    private static ResourceKey<Block> key(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
    }
}
