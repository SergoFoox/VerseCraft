package net.sergofoox.versecraft.registry;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class RegisterCreativeInventorySorting {


    public static void sortInventory() {

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(output -> {

            //Acacia Mosaic
            output.insertAfter(Blocks.ACACIA_PLANKS, RegisterBlocks.ACACIA_MOSAIC);
            output.insertAfter(Blocks.ACACIA_STAIRS, RegisterBlocks.ACACIA_MOSAIC_STAIRS);
            output.insertAfter(Blocks.ACACIA_SLAB, RegisterBlocks.ACACIA_MOSAIC_SLAB);

            //Birch Mosaic
            output.insertAfter(Blocks.BIRCH_PLANKS, RegisterBlocks.BIRCH_MOSAIC);
            output.insertAfter(Blocks.BIRCH_STAIRS, RegisterBlocks.BIRCH_MOSAIC_STAIRS);
            output.insertAfter(Blocks.BIRCH_SLAB, RegisterBlocks.BIRCH_MOSAIC_SLAB);

            //Cherry Mosaic
            output.insertAfter(Blocks.CHERRY_PLANKS, RegisterBlocks.CHERRY_MOSAIC);
            output.insertAfter(Blocks.CHERRY_STAIRS, RegisterBlocks.CHERRY_MOSAIC_STAIRS);
            output.insertAfter(Blocks.CHERRY_SLAB, RegisterBlocks.CHERRY_MOSAIC_SLAB);

            //Crimson Mosaic
            output.insertAfter(Blocks.CRIMSON_PLANKS, RegisterBlocks.CRIMSON_MOSAIC);
            output.insertAfter(Blocks.CRIMSON_STAIRS, RegisterBlocks.CRIMSON_MOSAIC_STAIRS);
            output.insertAfter(Blocks.CRIMSON_SLAB, RegisterBlocks.CRIMSON_MOSAIC_SLAB);

            //Dark Oak Mosaic
            output.insertAfter(Blocks.DARK_OAK_PLANKS, RegisterBlocks.DARK_OAK_MOSAIC);
            output.insertAfter(Blocks.DARK_OAK_STAIRS, RegisterBlocks.DARK_OAK_MOSAIC_STAIRS);
            output.insertAfter(Blocks.DARK_OAK_SLAB, RegisterBlocks.DARK_OAK_MOSAIC_SLAB);

            //Jungle Mosaic
            output.insertAfter(Blocks.JUNGLE_PLANKS, RegisterBlocks.JUNGLE_MOSAIC);
            output.insertAfter(Blocks.JUNGLE_STAIRS, RegisterBlocks.JUNGLE_MOSAIC_STAIRS);
            output.insertAfter(Blocks.JUNGLE_SLAB, RegisterBlocks.JUNGLE_MOSAIC_SLAB);

            //Mangrove Mosaic
            output.insertAfter(Blocks.MANGROVE_PLANKS, RegisterBlocks.MANGROVE_MOSAIC);
            output.insertAfter(Blocks.MANGROVE_STAIRS, RegisterBlocks.MANGROVE_MOSAIC_STAIRS);
            output.insertAfter(Blocks.MANGROVE_SLAB, RegisterBlocks.MANGROVE_MOSAIC_SLAB);

            //Oak Mosaic
            output.insertAfter(Blocks.OAK_PLANKS, RegisterBlocks.OAK_MOSAIC);
            output.insertAfter(Blocks.OAK_STAIRS, RegisterBlocks.OAK_MOSAIC_STAIRS);
            output.insertAfter(Blocks.OAK_SLAB, RegisterBlocks.OAK_MOSAIC_SLAB);

            //Spruce Mosaic
            output.insertAfter(Blocks.SPRUCE_PLANKS, RegisterBlocks.SPRUCE_MOSAIC);
            output.insertAfter(Blocks.SPRUCE_STAIRS, RegisterBlocks.SPRUCE_MOSAIC_STAIRS);
            output.insertAfter(Blocks.SPRUCE_SLAB, RegisterBlocks.SPRUCE_MOSAIC_SLAB);

            //Pale Oak Mosaic
            output.insertAfter(Blocks.PALE_OAK_PLANKS, RegisterBlocks.PALE_OAK_MOSAIC);
            output.insertAfter(Blocks.PALE_OAK_STAIRS, RegisterBlocks.PALE_OAK_MOSAIC_STAIRS);
            output.insertAfter(Blocks.PALE_OAK_SLAB, RegisterBlocks.PALE_OAK_MOSAIC_SLAB);

            //Poplar Mosaic

            output.insertAfter(Blocks.POPLAR_PLANKS, RegisterBlocks.POPLAR_MOSAIC);
            output.insertAfter(Blocks.POPLAR_STAIRS, RegisterBlocks.POPLAR_MOSAIC_STAIRS);
            output.insertAfter(Blocks.POPLAR_SLAB, RegisterBlocks.POPLAR_MOSAIC_SLAB);

            //Warped Mosaic
            output.insertAfter(Blocks.WARPED_PLANKS, RegisterBlocks.WARPED_MOSAIC);
            output.insertAfter(Blocks.WARPED_STAIRS, RegisterBlocks.WARPED_MOSAIC_STAIRS);
            output.insertAfter(Blocks.WARPED_SLAB, RegisterBlocks.WARPED_MOSAIC_SLAB);


            //Cracked Tuff
            output.insertAfter(Blocks.TUFF_BRICKS, RegisterBlocks.CRACKED_TUFF_BRICKS);

            //Calcite
            output.insertAfter(Blocks.REINFORCED_DEEPSLATE, Blocks.CALCITE);
            output.insertAfter(Blocks.CALCITE, RegisterBlocks.CALCITE_STAIRS);
            output.insertAfter(RegisterBlocks.CALCITE_STAIRS, RegisterBlocks.CALCITE_SLAB);
            output.insertAfter(RegisterBlocks.CALCITE_SLAB, RegisterBlocks.CALCITE_WALL);
            output.insertAfter(RegisterBlocks.CALCITE_WALL, RegisterBlocks.CHISELED_CALCITE);
            output.insertAfter(RegisterBlocks.CHISELED_CALCITE, RegisterBlocks.POLISHED_CALCITE);

            //Polished Calcite
            output.insertAfter(RegisterBlocks.POLISHED_CALCITE, RegisterBlocks.POLISHED_CALCITE_STAIRS);
            output.insertAfter(RegisterBlocks.POLISHED_CALCITE_STAIRS, RegisterBlocks.POLISHED_CALCITE_SLAB);
            output.insertAfter(RegisterBlocks.POLISHED_CALCITE_SLAB, RegisterBlocks.POLISHED_CALCITE_WALL);
            output.insertAfter(RegisterBlocks.POLISHED_CALCITE_WALL, RegisterBlocks.CALCITE_BRICKS);

            //Calcite Bricks
            output.insertAfter(RegisterBlocks.CALCITE_BRICKS, RegisterBlocks.CRACKED_CALCITE_BRICKS);
            output.insertAfter(RegisterBlocks.CRACKED_CALCITE_BRICKS, RegisterBlocks.CALCITE_BRICKS_STAIRS);
            output.insertAfter(RegisterBlocks.CALCITE_BRICKS_STAIRS, RegisterBlocks.CALCITE_BRICKS_SLAB);
            output.insertAfter(RegisterBlocks.CALCITE_BRICKS_SLAB, RegisterBlocks.CALCITE_BRICKS_WALL);
            output.insertAfter(RegisterBlocks.CALCITE_BRICKS_WALL, RegisterBlocks.CHISELED_CALCITE_BRICKS);

            //Dripstone
            output.insertAfter(Blocks.CHISELED_TUFF_BRICKS, Blocks.DRIPSTONE_BLOCK);
            output.insertAfter(Blocks.DRIPSTONE_BLOCK, RegisterBlocks.DRIPSTONE_STAIRS);
            output.insertAfter(RegisterBlocks.DRIPSTONE_STAIRS, RegisterBlocks.DRIPSTONE_SLAB);
            output.insertAfter(RegisterBlocks.DRIPSTONE_SLAB, RegisterBlocks.DRIPSTONE_WALL);
            output.insertAfter(RegisterBlocks.DRIPSTONE_WALL, RegisterBlocks.CHISELED_DRIPSTONE);

            //Polished Dripstone
            output.insertAfter(RegisterBlocks.CHISELED_DRIPSTONE, RegisterBlocks.POLISHED_DRIPSTONE);
            output.insertAfter(RegisterBlocks.POLISHED_DRIPSTONE, RegisterBlocks.POLISHED_DRIPSTONE_STAIRS);
            output.insertAfter(RegisterBlocks.POLISHED_DRIPSTONE_STAIRS, RegisterBlocks.POLISHED_DRIPSTONE_SLAB);
            output.insertAfter(RegisterBlocks.POLISHED_DRIPSTONE_SLAB, RegisterBlocks.POLISHED_DRIPSTONE_WALL);

            //Dripstone Bricks
            output.insertAfter(RegisterBlocks.POLISHED_DRIPSTONE_WALL, RegisterBlocks.DRIPSTONE_BRICKS);
            output.insertAfter(RegisterBlocks.DRIPSTONE_BRICKS, RegisterBlocks.CRACKED_DRIPSTONE_BRICKS);
            output.insertAfter(RegisterBlocks.CRACKED_DRIPSTONE_BRICKS, RegisterBlocks.DRIPSTONE_BRICKS_STAIRS);
            output.insertAfter(RegisterBlocks.DRIPSTONE_BRICKS_STAIRS, RegisterBlocks.DRIPSTONE_BRICKS_SLAB);
            output.insertAfter(RegisterBlocks.DRIPSTONE_BRICKS_SLAB, RegisterBlocks.DRIPSTONE_BRICKS_WALL);
            output.insertAfter(RegisterBlocks.DRIPSTONE_BRICKS_WALL, RegisterBlocks.CHISELED_DRIPSTONE_BRICKS);

            //Andesite
            output.insertAfter(Blocks.ANDESITE_WALL, RegisterBlocks.CHISELED_ANDESITE);
            output.insertAfter(Blocks.POLISHED_ANDESITE_SLAB, RegisterBlocks.POLISHED_ANDESITE_WALL);

            //Andesite Bricks
            output.insertAfter(RegisterBlocks.POLISHED_ANDESITE_WALL, RegisterBlocks.ANDESITE_BRICKS);
            output.insertAfter(RegisterBlocks.ANDESITE_BRICKS, RegisterBlocks.CRACKED_ANDESITE_BRICKS);
            output.insertAfter(RegisterBlocks.CRACKED_ANDESITE_BRICKS, RegisterBlocks.ANDESITE_BRICKS_STAIRS);
            output.insertAfter(RegisterBlocks.ANDESITE_BRICKS_STAIRS, RegisterBlocks.ANDESITE_BRICKS_SLAB);
            output.insertAfter(RegisterBlocks.ANDESITE_BRICKS_SLAB, RegisterBlocks.ANDESITE_BRICKS_WALL);
            output.insertAfter(RegisterBlocks.ANDESITE_BRICKS_WALL, RegisterBlocks.CHISELED_ANDESITE_BRICKS);

            //Diorite
            output.insertAfter(Blocks.DIORITE_WALL, RegisterBlocks.CHISELED_DIORITE);
            output.insertAfter(Blocks.POLISHED_DIORITE_SLAB, RegisterBlocks.POLISHED_DIORITE_WALL);

            //Diorite Bricks
            output.insertAfter(RegisterBlocks.POLISHED_DIORITE_WALL, RegisterBlocks.DIORITE_BRICKS);
            output.insertAfter(RegisterBlocks.DIORITE_BRICKS, RegisterBlocks.CRACKED_DIORITE_BRICKS);
            output.insertAfter(RegisterBlocks.CRACKED_DIORITE_BRICKS, RegisterBlocks.DIORITE_BRICKS_STAIRS);
            output.insertAfter(RegisterBlocks.DIORITE_BRICKS_STAIRS, RegisterBlocks.DIORITE_BRICKS_SLAB);
            output.insertAfter(RegisterBlocks.DIORITE_BRICKS_SLAB, RegisterBlocks.DIORITE_BRICKS_WALL);
            output.insertAfter(RegisterBlocks.DIORITE_BRICKS_WALL, RegisterBlocks.CHISELED_DIORITE_BRICKS);

            //Granite
            output.insertAfter(Blocks.GRANITE_WALL, RegisterBlocks.CHISELED_GRANITE);
            output.insertAfter(Blocks.POLISHED_GRANITE_SLAB, RegisterBlocks.POLISHED_GRANITE_WALL);

            //Granite Bricks
            output.insertAfter(RegisterBlocks.POLISHED_GRANITE_WALL, RegisterBlocks.GRANITE_BRICKS);
            output.insertAfter(RegisterBlocks.GRANITE_BRICKS, RegisterBlocks.CRACKED_GRANITE_BRICKS);
            output.insertAfter(RegisterBlocks.CRACKED_GRANITE_BRICKS, RegisterBlocks.GRANITE_BRICKS_STAIRS);
            output.insertAfter(RegisterBlocks.GRANITE_BRICKS_STAIRS, RegisterBlocks.GRANITE_BRICKS_SLAB);
            output.insertAfter(RegisterBlocks.GRANITE_BRICKS_SLAB, RegisterBlocks.GRANITE_BRICKS_WALL);
            output.insertAfter(RegisterBlocks.GRANITE_BRICKS_WALL, RegisterBlocks.CHISELED_GRANITE_BRICKS);

            //Azalea
            output.insertAfter(Blocks.ACACIA_BUTTON, RegisterBlocks.AZALEA_LOG);
            output.insertAfter(RegisterBlocks.AZALEA_LOG, RegisterBlocks.AZALEA_WOOD);
            output.insertAfter(RegisterBlocks.AZALEA_WOOD, RegisterBlocks.STRIPPED_AZALEA_LOG);
            output.insertAfter(RegisterBlocks.STRIPPED_AZALEA_LOG, RegisterBlocks.STRIPPED_AZALEA_WOOD);
            output.insertAfter(RegisterBlocks.STRIPPED_AZALEA_WOOD, RegisterBlocks.AZALEA_PLANKS);
            output.insertAfter(RegisterBlocks.AZALEA_PLANKS, RegisterBlocks.AZALEA_MOSAIC);
            output.insertAfter(RegisterBlocks.AZALEA_MOSAIC, RegisterBlocks.AZALEA_STAIRS);
            output.insertAfter(RegisterBlocks.AZALEA_STAIRS, RegisterBlocks.AZALEA_MOSAIC_STAIRS);
            output.insertAfter(RegisterBlocks.AZALEA_MOSAIC_STAIRS, RegisterBlocks.AZALEA_SLAB);
            output.insertAfter(RegisterBlocks.AZALEA_SLAB, RegisterBlocks.AZALEA_MOSAIC_SLAB);
            output.insertAfter(RegisterBlocks.AZALEA_MOSAIC_SLAB, RegisterBlocks.AZALEA_FENCE);
            output.insertAfter(RegisterBlocks.AZALEA_FENCE, RegisterBlocks.AZALEA_FENCE_GATE);
            output.insertAfter(RegisterBlocks.AZALEA_FENCE_GATE, RegisterBlocks.AZALEA_DOOR);
            output.insertAfter(RegisterBlocks.AZALEA_DOOR, RegisterBlocks.AZALEA_TRAPDOOR);
            output.insertAfter(RegisterBlocks.AZALEA_TRAPDOOR, RegisterBlocks.AZALEA_PRESSURE_PLATE);
            output.insertAfter(RegisterBlocks.AZALEA_PRESSURE_PLATE, RegisterBlocks.AZALEA_BUTTON);


        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {

            //Poterry Sherd
            output.insertAfter(Items.BURN_POTTERY_SHERD, RegisterItems.DRAGON_POTTERY_SHERD);
            output.insertAfter(RegisterItems.DRAGON_POTTERY_SHERD, RegisterItems.EYE_POTTERY_SHERD);
            output.insertAfter(RegisterItems.EYE_POTTERY_SHERD, RegisterItems.EGG_POTTERY_SHERD);
            output.insertAfter(RegisterItems.EGG_POTTERY_SHERD, RegisterItems.PILLAGER_POTTERY_SHERD);
            output.insertAfter(RegisterItems.PILLAGER_POTTERY_SHERD, RegisterItems.PORTAL_POTTERY_SHERD);
            output.insertAfter(RegisterItems.PORTAL_POTTERY_SHERD, RegisterItems.SWORD_POTTERY_SHERD);


        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> {

            //Azalea Functional
            output.insertAfter(Blocks.ACACIA_SHELF, RegisterBlocks.AZALEA_SHELF);
            output.insertAfter(Blocks.ACACIA_HANGING_SIGN, RegisterItems.AZALEA_SIGN);
            output.insertAfter(RegisterItems.AZALEA_SIGN, RegisterItems.AZALEA_HANGING_SIGN);

            //Other
            output.insertAfter(Blocks.CAMPFIRE, RegisterBlocks.COPPER_CAMPFIRE);

        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {

            //Azalea Boat
            output.insertAfter(Items.ACACIA_CHEST_BOAT, RegisterItems.AZALEA_BOAT);
            output.insertAfter(RegisterItems.AZALEA_BOAT, RegisterItems.AZALEA_CHEST_BOAT);

        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> {

            //Other
            output.insertAfter(Blocks.ACACIA_LOG, RegisterBlocks.AZALEA_LOG);
            output.insertAfter(Blocks.JACK_O_LANTERN, RegisterBlocks.COPPER_JACK_O_LANTERN);
            output.insertAfter(RegisterBlocks.COPPER_JACK_O_LANTERN, RegisterBlocks.SOUL_JACK_O_LANTERN);

            //Plants
            output.insertAfter(Blocks.TORCHFLOWER, RegisterBlocks.ICEFLOWER);
            output.insertAfter(RegisterBlocks.ICEFLOWER, RegisterBlocks.BLUE_ROSE);
            output.insertAfter(Blocks.PINK_PETALS, RegisterBlocks.POP_FLOWER);
            output.insertAfter(RegisterBlocks.POP_FLOWER, RegisterBlocks.GLOWSHROOM);
            output.insertAfter(Blocks.SHORT_GRASS, RegisterBlocks.SHORT_MYCELIUM_GRASS);
            output.insertAfter(Blocks.TALL_GRASS, RegisterBlocks.TALL_MYCELIUM_GRASS);



            output.insertAfter(Items.PITCHER_POD, RegisterItems.ICEFLOWER_SEEDS);
            output.insertAfter(RegisterItems.ICEFLOWER_SEEDS, RegisterItems.BLUE_ROSE_SEEDS);

        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> {
            //Food
            output.insertAfter(Items.CHORUS_FRUIT, RegisterItems.CHERRY);
        });
    }
}
