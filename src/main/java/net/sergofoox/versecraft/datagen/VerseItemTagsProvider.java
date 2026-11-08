package net.sergofoox.versecraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import net.sergofoox.versecraft.registry.RegisterItems;
import net.sergofoox.versecraft.tags.VerseBlocksTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class VerseItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {

    public VerseItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {

        tag(VerseBlocksTags.Items.AZALEA_LOGS)
                .add(key(RegisterBlocks.AZALEA_LOG))
                .add(key(RegisterBlocks.AZALEA_WOOD))
                .add(key(RegisterBlocks.STRIPPED_AZALEA_LOG))
                .add(key(RegisterBlocks.STRIPPED_AZALEA_WOOD));

        tag(ItemTags.LOGS)
                .addTag(VerseBlocksTags.Items.AZALEA_LOGS);

        tag(ItemTags.LOGS_THAT_BURN)
                .addTag(VerseBlocksTags.Items.AZALEA_LOGS);

        tag(ItemTags.BEE_FOOD)
                .add(key(RegisterBlocks.BLUE_ROSE))
                .add(key(RegisterBlocks.ICEFLOWER))
                .add(key(RegisterBlocks.POP_FLOWER))
                .add(key(RegisterBlocks.BUTTERCUP));

        tag(ItemTags.BOATS)
                .add(key(RegisterItems.AZALEA_BOAT));

        tag(ItemTags.CHEST_BOATS)
                .add(key(RegisterItems.AZALEA_CHEST_BOAT));

        tag(ItemTags.DECORATED_POT_SHERDS)
                .add(key(RegisterItems.DRAGON_POTTERY_SHERD))
                .add(key(RegisterItems.EGG_POTTERY_SHERD))
                .add(key(RegisterItems.EYE_POTTERY_SHERD))
                .add(key(RegisterItems.PILLAGER_POTTERY_SHERD))
                .add(key(RegisterItems.PORTAL_POTTERY_SHERD))
                .add(key(RegisterItems.SWORD_POTTERY_SHERD));

        tag(ItemTags.FENCE_GATES)
                .add(key(RegisterBlocks.AZALEA_FENCE_GATE));

        tag(BlockItemTags.FLOWERS.item())
                .add(key(RegisterBlocks.BLUE_ROSE))
                .add(key(RegisterBlocks.ICEFLOWER))
                .add(key(RegisterBlocks.POP_FLOWER))
                .add(key(RegisterBlocks.BUTTERCUP));

        tag(BlockItemTags.HANGING_SIGNS.item())
                .add(key(RegisterBlocks.AZALEA_HANGING_SIGN));

        tag(BlockItemTags.PLANKS.item())
                .add(key(RegisterBlocks.AZALEA_PLANKS));

        tag(BlockItemTags.SIGNS.item())
                .add(key(RegisterBlocks.AZALEA_SIGN));

        tag(BlockItemTags.SLABS.item())
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

        tag(BlockItemTags.SMALL_FLOWERS.item())
                .add(key(RegisterBlocks.BLUE_ROSE))
                .add(key(RegisterBlocks.ICEFLOWER))
                .add(key(RegisterBlocks.POP_FLOWER))
                .add(key(RegisterBlocks.BUTTERCUP));

        tag(BlockItemTags.STAIRS.item())
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

        tag(BlockItemTags.WALLS.item())
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

        tag(BlockItemTags.WOODEN_BUTTONS.item())
                .add(key(RegisterBlocks.AZALEA_BUTTON));

        tag(BlockItemTags.WOODEN_DOORS.item())
                .add(key(RegisterBlocks.AZALEA_DOOR));

        tag(BlockItemTags.WOODEN_FENCES.item())
                .add(key(RegisterBlocks.AZALEA_FENCE));

        tag(BlockItemTags.WOODEN_PRESSURE_PLATES.item())
                .add(key(RegisterBlocks.AZALEA_PRESSURE_PLATE));

        tag(BlockItemTags.WOODEN_SLABS.item())
                .add(key(RegisterBlocks.AZALEA_SLAB));

        tag(BlockItemTags.WOODEN_STAIRS.item())
                .add(key(RegisterBlocks.AZALEA_STAIRS));

        tag(BlockItemTags.WOODEN_TRAPDOORS.item())
                .add(key(RegisterBlocks.AZALEA_TRAPDOOR));
    }

    private static ResourceKey<Item> key(ItemLike item) {
        return BuiltInRegistries.ITEM.getResourceKey(item.asItem()).orElseThrow();
    }
}
