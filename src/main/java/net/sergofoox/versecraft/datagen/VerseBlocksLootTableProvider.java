package net.sergofoox.versecraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.sergofoox.versecraft.block.BlueRoseCropBlock;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import net.sergofoox.versecraft.registry.RegisterItems;

import java.util.concurrent.CompletableFuture;

public class VerseBlocksLootTableProvider extends FabricBlockLootSubProvider {

    public VerseBlocksLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {


        //Azalea
        dropSelf(RegisterBlocks.AZALEA_LOG);
        dropSelf(RegisterBlocks.STRIPPED_AZALEA_LOG);
        dropSelf(RegisterBlocks.AZALEA_WOOD);
        dropSelf(RegisterBlocks.STRIPPED_AZALEA_WOOD);
        addBlocksDrops(RegisterBlocks.AZALEA_PLANKS, RegisterBlocks.AZALEA_STAIRS, RegisterBlocks.AZALEA_SLAB);
        dropSelf(RegisterBlocks.AZALEA_FENCE);
        dropSelf(RegisterBlocks.AZALEA_FENCE_GATE);
        add(RegisterBlocks.AZALEA_DOOR, this::createDoorTable);
        dropSelf(RegisterBlocks.AZALEA_TRAPDOOR);
        dropSelf(RegisterBlocks.AZALEA_BUTTON);
        dropSelf(RegisterBlocks.AZALEA_SHELF);
        dropSelf(RegisterBlocks.AZALEA_SIGN);
        dropSelf(RegisterBlocks.AZALEA_HANGING_SIGN);

        //Mosaic
        addBlocksDrops(RegisterBlocks.ACACIA_MOSAIC, RegisterBlocks.ACACIA_MOSAIC_STAIRS, RegisterBlocks.ACACIA_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.AZALEA_MOSAIC, RegisterBlocks.AZALEA_MOSAIC_STAIRS, RegisterBlocks.AZALEA_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.BIRCH_MOSAIC, RegisterBlocks.BIRCH_MOSAIC_STAIRS, RegisterBlocks.BIRCH_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.CHERRY_MOSAIC, RegisterBlocks.CHERRY_MOSAIC_STAIRS, RegisterBlocks.CHERRY_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.CRIMSON_MOSAIC, RegisterBlocks.CRIMSON_MOSAIC_STAIRS, RegisterBlocks.CRIMSON_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.DARK_OAK_MOSAIC, RegisterBlocks.DARK_OAK_MOSAIC_STAIRS, RegisterBlocks.DARK_OAK_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.JUNGLE_MOSAIC, RegisterBlocks.JUNGLE_MOSAIC_STAIRS, RegisterBlocks.JUNGLE_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.MANGROVE_MOSAIC, RegisterBlocks.MANGROVE_MOSAIC_STAIRS, RegisterBlocks.MANGROVE_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.OAK_MOSAIC, RegisterBlocks.OAK_MOSAIC_STAIRS, RegisterBlocks.OAK_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.SPRUCE_MOSAIC, RegisterBlocks.SPRUCE_MOSAIC_STAIRS, RegisterBlocks.SPRUCE_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.PALE_OAK_MOSAIC, RegisterBlocks.PALE_OAK_MOSAIC_STAIRS, RegisterBlocks.PALE_OAK_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.POPLAR_MOSAIC, RegisterBlocks.POPLAR_MOSAIC_STAIRS, RegisterBlocks.POPLAR_MOSAIC_SLAB);
        addBlocksDrops(RegisterBlocks.WARPED_MOSAIC, RegisterBlocks.WARPED_MOSAIC_STAIRS, RegisterBlocks.WARPED_MOSAIC_SLAB);

        //Tuff
        dropSelf(RegisterBlocks.CRACKED_TUFF_BRICKS);
        dropSelf(RegisterBlocks.TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.WHITE_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.ORANGE_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.MAGENTA_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.LIGHT_BLUE_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.YELLOW_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.LIME_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.PINK_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.GRAY_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.LIGHT_GRAY_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.CYAN_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.PURPLE_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.BLUE_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.BROWN_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.GREEN_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.RED_TUFF_GOLEM_STATUE);
        dropSelf(RegisterBlocks.BLACK_TUFF_GOLEM_STATUE);

        //Calcite
        dropSelf(RegisterBlocks.CALCITE_STAIRS);
        dropSelf(RegisterBlocks.CALCITE_SLAB);
        dropSelf(RegisterBlocks.CALCITE_WALL);
        dropSelf(RegisterBlocks.CHISELED_CALCITE);
        addBlocksDrops(RegisterBlocks.POLISHED_CALCITE, RegisterBlocks.POLISHED_CALCITE_STAIRS, RegisterBlocks.POLISHED_CALCITE_SLAB);
        dropSelf(RegisterBlocks.POLISHED_CALCITE_WALL);
        addBlocksDrops(RegisterBlocks.CALCITE_BRICKS, RegisterBlocks.CALCITE_BRICKS_STAIRS, RegisterBlocks.CALCITE_BRICKS_SLAB);
        dropSelf(RegisterBlocks.CRACKED_CALCITE_BRICKS);
        dropSelf(RegisterBlocks.CALCITE_BRICKS_WALL);
        dropSelf(RegisterBlocks.CHISELED_CALCITE_BRICKS);

        //Dripstone
        dropSelf(RegisterBlocks.DRIPSTONE_STAIRS);
        dropSelf(RegisterBlocks.DRIPSTONE_SLAB);
        dropSelf(RegisterBlocks.DRIPSTONE_WALL);
        dropSelf(RegisterBlocks.CHISELED_DRIPSTONE);
        addBlocksDrops(RegisterBlocks.POLISHED_DRIPSTONE, RegisterBlocks.POLISHED_DRIPSTONE_STAIRS, RegisterBlocks.POLISHED_DRIPSTONE_SLAB);
        dropSelf(RegisterBlocks.POLISHED_DRIPSTONE_WALL);
        addBlocksDrops(RegisterBlocks.DRIPSTONE_BRICKS, RegisterBlocks.DRIPSTONE_BRICKS_STAIRS, RegisterBlocks.DRIPSTONE_BRICKS_SLAB);
        dropSelf(RegisterBlocks.CRACKED_DRIPSTONE_BRICKS);
        dropSelf(RegisterBlocks.DRIPSTONE_BRICKS_WALL);
        dropSelf(RegisterBlocks.CHISELED_DRIPSTONE_BRICKS);

        //Andesite
        dropSelf(RegisterBlocks.CHISELED_ANDESITE);
        dropSelf(RegisterBlocks.POLISHED_ANDESITE_WALL);
        addBlocksDrops(RegisterBlocks.ANDESITE_BRICKS, RegisterBlocks.ANDESITE_BRICKS_STAIRS, RegisterBlocks.ANDESITE_BRICKS_SLAB);
        dropSelf(RegisterBlocks.CRACKED_ANDESITE_BRICKS);
        dropSelf(RegisterBlocks.ANDESITE_BRICKS_WALL);
        dropSelf(RegisterBlocks.CHISELED_ANDESITE_BRICKS);

        //Diorite
        dropSelf(RegisterBlocks.CHISELED_DIORITE);
        dropSelf(RegisterBlocks.POLISHED_DIORITE_WALL);
        addBlocksDrops(RegisterBlocks.DIORITE_BRICKS, RegisterBlocks.DIORITE_BRICKS_STAIRS, RegisterBlocks.DIORITE_BRICKS_SLAB);
        dropSelf(RegisterBlocks.CRACKED_DIORITE_BRICKS);
        dropSelf(RegisterBlocks.DIORITE_BRICKS_WALL);
        dropSelf(RegisterBlocks.CHISELED_DIORITE_BRICKS);

        //Granite
        dropSelf(RegisterBlocks.CHISELED_GRANITE);
        dropSelf(RegisterBlocks.POLISHED_GRANITE_WALL);
        addBlocksDrops(RegisterBlocks.GRANITE_BRICKS, RegisterBlocks.GRANITE_BRICKS_STAIRS, RegisterBlocks.GRANITE_BRICKS_SLAB);
        dropSelf(RegisterBlocks.CRACKED_GRANITE_BRICKS);
        dropSelf(RegisterBlocks.GRANITE_BRICKS_WALL);
        dropSelf(RegisterBlocks.CHISELED_GRANITE_BRICKS);

        //Plants
        dropSelf(RegisterBlocks.ICEFLOWER);
        dropSelf(RegisterBlocks.BLUE_ROSE);
        dropSelf(RegisterBlocks.GLOWSHROOM);
        dropSelf(RegisterBlocks.POP_FLOWER);
        dropSelf(RegisterBlocks.BUTTERCUP);

        add(RegisterBlocks.BLUE_ROSE_CROP,
                this.applyExplosionDecay(
                        RegisterBlocks.BLUE_ROSE_CROP,
                        LootTable.lootTable()
                                .setRandomSequence(RegisterBlocks.BLUE_ROSE_CROP.getLootTable().orElseThrow().identifier())
                                .withPool(
                                        LootPool.lootPool().add(LootItem.lootTableItem(RegisterItems.BLUE_ROSE_SEEDS))
                                )
                )
        );

        add(RegisterBlocks.ICEFLOWER_CROP,
                this.applyExplosionDecay(
                        RegisterBlocks.ICEFLOWER_CROP,
                        LootTable.lootTable()
                                .setRandomSequence(RegisterBlocks.ICEFLOWER_CROP.getLootTable().orElseThrow().identifier())
                                .withPool(
                                        LootPool.lootPool().add(LootItem.lootTableItem(RegisterItems.ICEFLOWER_SEEDS))
                                )
                )
        );

        add(RegisterBlocks.SHORT_MYCELIUM_GRASS,
                createShearsOrSilkTouchOnlyDrop(RegisterBlocks.SHORT_MYCELIUM_GRASS)
                        .setRandomSequence(RegisterBlocks.SHORT_MYCELIUM_GRASS.getLootTable().orElseThrow().identifier())
        );

        add(RegisterBlocks.TALL_MYCELIUM_GRASS,
                createShearsOrSilkTouchOnlyDrop(RegisterBlocks.TALL_MYCELIUM_GRASS)
                        .setRandomSequence(RegisterBlocks.TALL_MYCELIUM_GRASS.getLootTable().orElseThrow().identifier())
        );

        add(RegisterBlocks.COPPER_CAMPFIRE, block -> createSilkTouchDispatchTable(
                block,
                applyExplosionCondition(
                        block,
                        LootItem.lootTableItem(Items.RAW_COPPER)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))
                )
        )).setRandomSequence(block.getLootTable().orElseThrow().identifier()));

        dropPottedContents(RegisterBlocks.POTTED_BLUE_ROSE);
        dropPottedContents(RegisterBlocks.POTTED_ICEFLOWER);
        dropPottedContents(RegisterBlocks.POTTED_POP_FLOWER);
        dropPottedContents(RegisterBlocks.POTTED_BUTTERCUP);

        //Other
        dropSelf(RegisterBlocks.COPPER_JACK_O_LANTERN);
        dropSelf(RegisterBlocks.SOUL_JACK_O_LANTERN);

    }

    private void addBlocksDrops(Block block, Block stairs, Block slab) {
        dropSelf(block);
        dropSelf(stairs);
        add(slab, this::createSlabItemTable);
    }
}
