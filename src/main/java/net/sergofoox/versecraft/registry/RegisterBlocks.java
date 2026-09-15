package net.sergofoox.versecraft.registry;

import net.fabricmc.fabric.api.item.v1.BlockTransformerHelper;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import net.sergofoox.versecraft.VerseCraft;
import net.sergofoox.versecraft.block.*;
import net.sergofoox.versecraft.block.IceflowerCropBlock;

import java.util.Properties;
import java.util.function.Function;

public class RegisterBlocks {

    //Azalea

    public static final BlockSetType AZALEA_BLOCK_SET = BlockSetTypeBuilder.copyOf(BlockSetType.CHERRY)
            .register(VerseCraft.id("azalea"));

    public static final WoodType AZALEA_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.CHERRY)
            .register(VerseCraft.id("azalea"), AZALEA_BLOCK_SET);

    public static final Block AZALEA_LOG = register("azalea_log",
            properties -> new RotatedPillarBlock(properties.strength(2.0f,2.0f).sound(SoundType.CHERRY_WOOD))
    );
    public static final Block STRIPPED_AZALEA_LOG = register("stripped_azalea_log",
            properties -> new RotatedPillarBlock(properties.strength(2.0f,2.0f).sound(SoundType.CHERRY_WOOD))
    );
    public static final Block AZALEA_WOOD = register("azalea_wood",
            properties -> new RotatedPillarBlock(properties.strength(2.0f,2.0f).sound(SoundType.CHERRY_WOOD))
    );
    public static final Block STRIPPED_AZALEA_WOOD = register("stripped_azalea_wood",
            properties -> new RotatedPillarBlock(properties.strength(2.0f,2.0f).sound(SoundType.CHERRY_WOOD))
    );

    public static final Block AZALEA_PLANKS = register("azalea_planks",
            properties -> new Block(properties.strength(2.0f, 3.0f).sound(SoundType.CHERRY_WOOD))
    );
    public static final Block AZALEA_STAIRS = register("azalea_stairs",
            properties -> new StairBlock(AZALEA_PLANKS.defaultBlockState(), properties.strength(2.0f, 3.0f).sound(SoundType.CHERRY_WOOD))
    );
    public static final Block AZALEA_SLAB = register("azalea_slab",
            properties -> new SlabBlock(properties.strength(2.0f, 3.0f).sound(SoundType.CHERRY_WOOD))
    );

    public static final Block AZALEA_FENCE = register("azalea_fence",
            properties -> new FenceBlock(properties.strength(2.0f, 3.0f).sound(SoundType.CHERRY_WOOD))
    );
    public static final Block AZALEA_FENCE_GATE = register("azalea_fence_gate",
            properties -> new FenceGateBlock(AZALEA_WOOD_TYPE, properties.strength(2.0f, 3.0f).sound(SoundType.CHERRY_WOOD))
    );

    public static final Block AZALEA_DOOR = register("azalea_door",
            properties -> new DoorBlock(AZALEA_BLOCK_SET, properties.strength(3.0f,3.0f).sound(SoundType.CHERRY_WOOD).noOcclusion())
    );
    public static final Block AZALEA_TRAPDOOR = register("azalea_trapdoor",
            properties -> new TrapDoorBlock(AZALEA_BLOCK_SET, properties.strength(3.0f,3.0f).sound(SoundType.CHERRY_WOOD).noOcclusion())
    );

    public static final Block AZALEA_BUTTON = register("azalea_button",
            properties -> new ButtonBlock(AZALEA_BLOCK_SET, 30, properties.noCollision().strength(0.5f).sound(SoundType.CHERRY_WOOD))
    );
    public static final Block AZALEA_PRESSURE_PLATE = register("azalea_pressure_plate",
            properties -> new PressurePlateBlock(AZALEA_BLOCK_SET, properties.noCollision().strength(0.5f).sound(SoundType.CHERRY_WOOD))
    );

    public static final Block AZALEA_SHELF = register("azalea_shelf",
            properties -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SHELF)
                    .mapColor(AZALEA_PLANKS.defaultMapColor())
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, "azalea_shelf"))))
    );

    public static final Block AZALEA_SIGN = registerBlockWithoutBlockItem("azalea_sign",
            props -> new StandingSignBlock(AZALEA_WOOD_TYPE,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SIGN)
                            .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, "azalea_sign"))))
    );

    public static final Block AZALEA_WALL_SIGN = registerBlockWithoutBlockItem("azalea_wall_sign",
            props -> new WallSignBlock(AZALEA_WOOD_TYPE,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_WALL_SIGN)
                            .overrideLootTable(AZALEA_SIGN.getLootTable())
                            .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, "azalea_wall_sign"))))
    );

    public static final Block AZALEA_HANGING_SIGN = registerBlockWithoutBlockItem("azalea_hanging_sign",
            props -> new CeilingHangingSignBlock(AZALEA_WOOD_TYPE,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_HANGING_SIGN)
                            .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, "azalea_hanging_sign"))))
    );

    public static final Block AZALEA_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("azalea_wall_hanging_sign",
            props -> new WallHangingSignBlock(AZALEA_WOOD_TYPE,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_WALL_HANGING_SIGN)
                            .overrideLootTable(AZALEA_HANGING_SIGN.getLootTable())
                            .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, "azalea_wall_hanging_sign"))))
    );

    //Acacia Mosaic

    public static final Block ACACIA_MOSAIC = register("acacia_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.WOOD)));

    public static final Block ACACIA_MOSAIC_STAIRS = register("acacia_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.ACACIA_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.WOOD)));

    public static final Block ACACIA_MOSAIC_SLAB = register("acacia_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.WOOD)));

    //Azalea Mosaic

    public static final Block AZALEA_MOSAIC = register("azalea_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.CHERRY_WOOD)));

    public static final Block AZALEA_MOSAIC_STAIRS = register("azalea_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.AZALEA_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.CHERRY_WOOD)));

    public static final Block AZALEA_MOSAIC_SLAB = register("azalea_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.CHERRY_WOOD)));

    //Birch Mosaic

    public static final Block BIRCH_MOSAIC = register("birch_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.WOOD)));

    public static final Block BIRCH_MOSAIC_STAIRS = register("birch_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.BIRCH_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.WOOD)));

    public static final Block BIRCH_MOSAIC_SLAB = register("birch_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.WOOD)));

    //Cherry Mosaic

    public static final Block CHERRY_MOSAIC = register("cherry_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.CHERRY_WOOD)));

    public static final Block CHERRY_MOSAIC_STAIRS = register("cherry_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.CHERRY_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.CHERRY_WOOD)));

    public static final Block CHERRY_MOSAIC_SLAB = register("cherry_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.CHERRY_WOOD)));

    //Crimson Mosaic

    public static final Block CRIMSON_MOSAIC = register("crimson_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.NETHER_WOOD)));

    public static final Block CRIMSON_MOSAIC_STAIRS = register("crimson_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.CRIMSON_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.NETHER_WOOD)));

    public static final Block CRIMSON_MOSAIC_SLAB = register("crimson_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.NETHER_WOOD)));

    //Dark Oak Mosaic

    public static final Block DARK_OAK_MOSAIC = register("dark_oak_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.WOOD)));

    public static final Block DARK_OAK_MOSAIC_STAIRS = register("dark_oak_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.DARK_OAK_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.WOOD)));

    public static final Block DARK_OAK_MOSAIC_SLAB = register("dark_oak_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.WOOD)));

    //Jungle Mosaic

    public static final Block JUNGLE_MOSAIC = register("jungle_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.WOOD)));

    public static final Block JUNGLE_MOSAIC_STAIRS = register("jungle_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.JUNGLE_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.WOOD)));

    public static final Block JUNGLE_MOSAIC_SLAB = register("jungle_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.WOOD)));

    //Mangrove Mosaic

    public static final Block MANGROVE_MOSAIC = register("mangrove_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.WOOD)));

    public static final Block MANGROVE_MOSAIC_STAIRS = register("mangrove_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.MANGROVE_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.WOOD)));

    public static final Block MANGROVE_MOSAIC_SLAB = register("mangrove_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.WOOD)));

    //Oak Mosaic

    public static final Block OAK_MOSAIC = register("oak_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.WOOD)));

    public static final Block OAK_MOSAIC_STAIRS = register("oak_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.OAK_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.WOOD)));

    public static final Block OAK_MOSAIC_SLAB = register("oak_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.WOOD)));

    //Spruce Mosaic

    public static final Block SPRUCE_MOSAIC = register("spruce_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.WOOD)));

    public static final Block SPRUCE_MOSAIC_STAIRS = register("spruce_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.SPRUCE_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.WOOD)));

    public static final Block SPRUCE_MOSAIC_SLAB = register("spruce_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.WOOD)));

    //Pale Oak Mosaic

    public static final Block PALE_OAK_MOSAIC = register("pale_oak_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.WOOD)));

    public static final Block PALE_OAK_MOSAIC_STAIRS = register("pale_oak_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.PALE_OAK_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.WOOD)));

    public static final Block PALE_OAK_MOSAIC_SLAB = register("pale_oak_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.WOOD)));

    //Poplar Mosaic

    public static final Block POPLAR_MOSAIC = register("poplar_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.WOOD)));

    public static final Block POPLAR_MOSAIC_STAIRS = register("poplar_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.PALE_OAK_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.WOOD)));

    public static final Block POPLAR_MOSAIC_SLAB = register("poplar_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.WOOD)));

    //Warped Mosaic

    public static final Block WARPED_MOSAIC = register("warped_mosaic",
            properties -> new Block(properties.strength(2f,3f)
                    .sound(SoundType.NETHER_WOOD)));

    public static final Block WARPED_MOSAIC_STAIRS = register("warped_mosaic_stairs",
            properties -> new StairBlock(RegisterBlocks.WARPED_MOSAIC.defaultBlockState(),
                    properties.strength(2f,3f).sound(SoundType.NETHER_WOOD)));

    public static final Block WARPED_MOSAIC_SLAB = register("warped_mosaic_slab",
            properties -> new SlabBlock(properties.strength(2f,3f).sound(SoundType.NETHER_WOOD)));

    //Tuff

    public static final Block CRACKED_TUFF_BRICKS = register("cracked_tuff_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.TUFF_BRICKS).requiresCorrectToolForDrops()));

    //Calcite

    public static final Block CALCITE_STAIRS = register("calcite_stairs",
            properties -> new StairBlock(Blocks.CALCITE.defaultBlockState(),
                    properties.strength(0.75f,0.75f).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final Block CALCITE_SLAB = register("calcite_slab",
            properties -> new SlabBlock(properties.strength(0.75f,0.75f).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));


    public static final Block CALCITE_WALL = register("calcite_wall",
            properties -> new WallBlock(properties.strength(0.75f,0.75f).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));


    public static final Block CHISELED_CALCITE = register("chiseled_calcite",
            properties -> new Block(properties.strength(0.75f,0.75f)
                    .sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    //Polished Calcite

    public static final Block POLISHED_CALCITE = register("polished_calcite",
            properties -> new Block(properties.strength(0.75f,0.75f)
                    .sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final Block POLISHED_CALCITE_STAIRS = register("polished_calcite_stairs",
            properties -> new StairBlock(RegisterBlocks.POLISHED_CALCITE.defaultBlockState(),
                    properties.strength(0.75f,0.75f).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final Block POLISHED_CALCITE_SLAB = register("polished_calcite_slab",
            properties -> new SlabBlock(properties.strength(0.75f,0.75f).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final Block POLISHED_CALCITE_WALL = register("polished_calcite_wall",
            properties -> new WallBlock(properties.strength(0.75f,0.75f).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    //Calcite Bricks

    public static final Block CALCITE_BRICKS = register("calcite_bricks",
            properties -> new Block(properties.strength(0.75f,0.75f)
                    .sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final Block CRACKED_CALCITE_BRICKS = register("cracked_calcite_bricks",
            properties -> new Block(properties.strength(0.75f,0.75f)
                    .sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final Block CALCITE_BRICKS_STAIRS = register("calcite_bricks_stairs",
            properties -> new StairBlock(RegisterBlocks.CALCITE_BRICKS.defaultBlockState(),
                    properties.strength(0.75f,0.75f).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final Block CALCITE_BRICKS_SLAB = register("calcite_bricks_slab",
            properties -> new SlabBlock(properties.strength(0.75f,0.75f).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final Block CALCITE_BRICKS_WALL = register("calcite_bricks_wall",
            properties -> new WallBlock(properties.strength(0.75f,0.75f).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final Block CHISELED_CALCITE_BRICKS = register("chiseled_calcite_bricks",
            properties -> new Block(properties.strength(0.75f,0.75f)
                    .sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    //Dripstone

    public static final Block DRIPSTONE_STAIRS = register("dripstone_stairs",
            properties -> new StairBlock(Blocks.DRIPSTONE_BLOCK.defaultBlockState(),
                    properties.strength(1.5f,1f).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block DRIPSTONE_SLAB = register("dripstone_slab",
            properties -> new SlabBlock(properties.strength(1.5f,1f).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block DRIPSTONE_WALL = register("dripstone_wall",
            properties -> new WallBlock(properties.strength(1.5f,1f).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block CHISELED_DRIPSTONE = register("chiseled_dripstone",
            properties -> new Block(properties.strength(1.5f,1f)
                    .sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    //Polished Dripstone

    public static final Block POLISHED_DRIPSTONE = register("polished_dripstone",
            properties -> new Block(properties.strength(1.5f,1f)
                    .sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block POLISHED_DRIPSTONE_STAIRS = register("polished_dripstone_stairs",
            properties -> new StairBlock(RegisterBlocks.POLISHED_DRIPSTONE.defaultBlockState(),
                    properties.strength(1.5f,1f).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block POLISHED_DRIPSTONE_SLAB = register("polished_dripstone_slab",
            properties -> new SlabBlock(properties.strength(1.5f,1f).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block POLISHED_DRIPSTONE_WALL = register("polished_dripstone_wall",
            properties -> new WallBlock(properties.strength(1.5f,1f).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    //Dripstone Bricks

    public static final Block DRIPSTONE_BRICKS = register("dripstone_bricks",
            properties -> new Block(properties.strength(1.5f,1f)
                    .sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block CRACKED_DRIPSTONE_BRICKS = register("cracked_dripstone_bricks",
            properties -> new Block(properties.strength(1.5f,1f)
                    .sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block DRIPSTONE_BRICKS_STAIRS = register("dripstone_bricks_stairs",
            properties -> new StairBlock(RegisterBlocks.DRIPSTONE_BRICKS.defaultBlockState(),
                    properties.strength(1.5f,1f).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block DRIPSTONE_BRICKS_SLAB = register("dripstone_bricks_slab",
            properties -> new SlabBlock(properties.strength(1.5f,1f).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block DRIPSTONE_BRICKS_WALL = register("dripstone_bricks_wall",
            properties -> new WallBlock(properties.strength(1.5f,1f).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final Block CHISELED_DRIPSTONE_BRICKS = register("chiseled_dripstone_bricks",
            properties -> new Block(properties.strength(1.5f,1f)
                    .sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    //Diorite

    public static final Block CHISELED_DIORITE = register("chiseled_diorite",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block POLISHED_DIORITE_WALL = register("polished_diorite_wall",
            properties -> new WallBlock(properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block DIORITE_BRICKS = register("diorite_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block CRACKED_DIORITE_BRICKS = register("cracked_diorite_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block DIORITE_BRICKS_STAIRS = register("diorite_bricks_stairs",
            properties -> new StairBlock(RegisterBlocks.DIORITE_BRICKS.defaultBlockState(),
                    properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block DIORITE_BRICKS_SLAB = register("diorite_bricks_slab",
            properties -> new SlabBlock(properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block DIORITE_BRICKS_WALL = register("diorite_bricks_wall",
            properties -> new WallBlock(properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block CHISELED_DIORITE_BRICKS = register("chiseled_diorite_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    //Andesite

    public static final Block CHISELED_ANDESITE = register("chiseled_andesite",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block POLISHED_ANDESITE_WALL = register("polished_andesite_wall",
            properties -> new WallBlock(properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block ANDESITE_BRICKS = register("andesite_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block CRACKED_ANDESITE_BRICKS = register("cracked_andesite_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block ANDESITE_BRICKS_STAIRS = register("andesite_bricks_stairs",
            properties -> new StairBlock(RegisterBlocks.ANDESITE_BRICKS.defaultBlockState(),
                    properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block ANDESITE_BRICKS_SLAB = register("andesite_bricks_slab",
            properties -> new SlabBlock(properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block ANDESITE_BRICKS_WALL = register("andesite_bricks_wall",
            properties -> new WallBlock(properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block CHISELED_ANDESITE_BRICKS = register("chiseled_andesite_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    //Granite

    public static final Block CHISELED_GRANITE = register("chiseled_granite",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block POLISHED_GRANITE_WALL = register("polished_granite_wall",
            properties -> new WallBlock(properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block GRANITE_BRICKS = register("granite_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block CRACKED_GRANITE_BRICKS = register("cracked_granite_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block GRANITE_BRICKS_STAIRS = register("granite_bricks_stairs",
            properties -> new StairBlock(RegisterBlocks.GRANITE_BRICKS.defaultBlockState(),
                    properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block GRANITE_BRICKS_SLAB = register("granite_bricks_slab",
            properties -> new SlabBlock(properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block GRANITE_BRICKS_WALL = register("granite_bricks_wall",
            properties -> new WallBlock(properties.strength(1.5f,6f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block CHISELED_GRANITE_BRICKS = register("chiseled_granite_bricks",
            properties -> new Block(properties.strength(1.5f,6f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final Block COPPER_CAMPFIRE = register("copper_campfire",
            properties -> new CampfireBlock(false, 1, properties.strength(2.0f,2.0f).sound(SoundType.WOOD).noOcclusion().lightLevel(state -> state.getValue(CampfireBlock.LIT) ? 15 : 0))
    );

    public static final Block SOUL_JACK_O_LANTERN = register("soul_jack_o_lantern",
            p -> new CarvedPumpkinBlock(p.strength(1.0F).sound(SoundType.WOOD).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 10) .pushReaction(PushReaction.POPPED))
    );
    public static final Block COPPER_JACK_O_LANTERN = register("copper_jack_o_lantern",
            p -> new CarvedPumpkinBlock(p.strength(1.0F).sound(SoundType.WOOD).mapColor(MapColor.COLOR_ORANGE).lightLevel(state -> 15) .pushReaction(PushReaction.POPPED))
    );

    //Plants

    public static final Block BLUE_ROSE = register("blue_rose",
            properties -> new FlowerBlock(MobEffects.SATURATION, 0.0F, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.PUSH)
    );

    public static final Block BLUE_ROSE_CROP = registerBlockWithoutBlockItem("blue_rose_crop",
            properties -> new BlueRoseCropBlock(properties
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.PUSH))
    );

    public static final Block POTTED_BLUE_ROSE = registerBlockWithoutBlockItem("potted_blue_rose",
            properties -> new FlowerPotBlock(BLUE_ROSE, properties
                    .instabreak()
                    .noOcclusion()
                    .pushReaction(PushReaction.PUSH))
    );

    public static final Block ICEFLOWER = register("iceflower",
            properties -> new FlowerBlock(MobEffects.SATURATION, 0.0F, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.PUSH)
    );

    public static final Block ICEFLOWER_CROP = register("iceflower_crop",
            IceflowerCropBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.PUSH)
    );

    public static final Block POTTED_ICEFLOWER = register("potted_iceflower",
            properties -> new FlowerPotBlock(ICEFLOWER, properties),
            Blocks.flowerPotProperties()
    );

    public static final Block POP_FLOWER = register("pop_flower",
            properties -> new FlowerBlock(MobEffects.SATURATION, 0.0F, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.PUSH)
    );

    public static final Block POTTED_POP_FLOWER = register("potted_pop_flower",
            properties -> new FlowerPotBlock(POP_FLOWER, properties),
            Blocks.flowerPotProperties()
    );

    public static final Block GLOWSHROOM = register("glowshroom",
            FireflyBushBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.FIREFLY_BUSH)
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.FUNGUS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .lightLevel(state -> 6)
    );

    public static final Block SHORT_MYCELIUM_GRASS = register("short_mycelium_grass",
            ShortMyceliumGrass::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_DRY_GRASS)
                    .mapColor(MapColor.PLANT)
                    .replaceable()
                    .noOcclusion()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.PUSH)
    );

    public static final Block TALL_MYCELIUM_GRASS = register("tall_mycelium_grass",
            TallMyceliumGrass::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_DRY_GRASS)
                    .mapColor(MapColor.PLANT)
                    .replaceable()
                    .noOcclusion()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.PUSH)
    );

    private static void registerFuels() {
        addFuel(AZALEA_LOG, 300);
        addFuel(STRIPPED_AZALEA_LOG, 300);
        addFuel(AZALEA_WOOD, 300);
        addFuel(STRIPPED_AZALEA_WOOD, 300);
        addFuel(AZALEA_PLANKS, 300);
        addFuel(AZALEA_STAIRS, 300);
        addFuel(AZALEA_SLAB, 150);
        addFuel(AZALEA_FENCE, 300);
        addFuel(AZALEA_FENCE_GATE, 300);
        addFuel(AZALEA_PRESSURE_PLATE, 300);
        addFuel(AZALEA_BUTTON, 100);
        addFuel(AZALEA_TRAPDOOR, 300);
        addFuel(AZALEA_DOOR, 200);
        addFuel(AZALEA_SHELF, 300);
        addFuel(AZALEA_MOSAIC, 300);
        addFuel(AZALEA_MOSAIC_STAIRS, 300);
        addFuel(AZALEA_MOSAIC_SLAB, 150);
        addFuel(RegisterItems.AZALEA_SIGN, 200);
        addFuel(RegisterItems.AZALEA_HANGING_SIGN, 200);
        addFuel(RegisterItems.AZALEA_BOAT, 1200);
        addFuel(RegisterItems.AZALEA_CHEST_BOAT, 1200);

        addFuel(ACACIA_MOSAIC, 300);
        addFuel(ACACIA_MOSAIC_STAIRS, 300);
        addFuel(ACACIA_MOSAIC_SLAB, 150);

        addFuel(BIRCH_MOSAIC, 300);
        addFuel(BIRCH_MOSAIC_STAIRS, 300);
        addFuel(BIRCH_MOSAIC_SLAB, 150);

        addFuel(CHERRY_MOSAIC, 300);
        addFuel(CHERRY_MOSAIC_STAIRS, 300);
        addFuel(CHERRY_MOSAIC_SLAB, 150);

        addFuel(DARK_OAK_MOSAIC, 300);
        addFuel(DARK_OAK_MOSAIC_STAIRS, 300);
        addFuel(DARK_OAK_MOSAIC_SLAB, 150);

        addFuel(JUNGLE_MOSAIC, 300);
        addFuel(JUNGLE_MOSAIC_STAIRS, 300);
        addFuel(JUNGLE_MOSAIC_SLAB, 150);

        addFuel(MANGROVE_MOSAIC, 300);
        addFuel(MANGROVE_MOSAIC_STAIRS, 300);
        addFuel(MANGROVE_MOSAIC_SLAB, 150);

        addFuel(OAK_MOSAIC, 300);
        addFuel(OAK_MOSAIC_STAIRS, 300);
        addFuel(OAK_MOSAIC_SLAB, 150);

        addFuel(SPRUCE_MOSAIC, 300);
        addFuel(SPRUCE_MOSAIC_STAIRS, 300);
        addFuel(SPRUCE_MOSAIC_SLAB, 150);

        addFuel(PALE_OAK_MOSAIC, 300);
        addFuel(PALE_OAK_MOSAIC_STAIRS, 300);
        addFuel(PALE_OAK_MOSAIC_SLAB, 150);
    }

    private static void registerFlammability() {

        FlammableBlockRegistry fireRegistry = FlammableBlockRegistry.getDefaultInstance();

        //Azalea
        fireRegistry.add(AZALEA_LOG, 5, 5);
        fireRegistry.add(STRIPPED_AZALEA_LOG, 5, 5);
        fireRegistry.add(AZALEA_WOOD, 5, 5);
        fireRegistry.add(STRIPPED_AZALEA_WOOD, 5, 5);
        fireRegistry.add(AZALEA_PLANKS, 5, 20);
        fireRegistry.add(AZALEA_STAIRS, 5, 20);
        fireRegistry.add(AZALEA_SLAB, 5, 20);

        fireRegistry.add(AZALEA_MOSAIC, 5, 20);
        fireRegistry.add(AZALEA_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(AZALEA_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(ACACIA_MOSAIC, 5, 20);
        fireRegistry.add(ACACIA_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(ACACIA_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(BIRCH_MOSAIC, 5, 20);
        fireRegistry.add(BIRCH_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(BIRCH_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(CHERRY_MOSAIC, 5, 20);
        fireRegistry.add(CHERRY_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(CHERRY_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(DARK_OAK_MOSAIC, 5, 20);
        fireRegistry.add(DARK_OAK_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(DARK_OAK_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(JUNGLE_MOSAIC, 5, 20);
        fireRegistry.add(JUNGLE_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(JUNGLE_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(MANGROVE_MOSAIC, 5, 20);
        fireRegistry.add(MANGROVE_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(MANGROVE_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(OAK_MOSAIC, 5, 20);
        fireRegistry.add(OAK_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(OAK_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(SPRUCE_MOSAIC, 5, 20);
        fireRegistry.add(SPRUCE_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(SPRUCE_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(PALE_OAK_MOSAIC, 5, 20);
        fireRegistry.add(PALE_OAK_MOSAIC_STAIRS, 5, 20);
        fireRegistry.add(PALE_OAK_MOSAIC_SLAB, 5, 20);

        fireRegistry.add(RegisterBlocks.ICEFLOWER, 100, 60);
        fireRegistry.add(RegisterBlocks.BLUE_ROSE, 100, 60);
        fireRegistry.add(RegisterBlocks.TALL_MYCELIUM_GRASS, 100, 60);
        fireRegistry.add(RegisterBlocks.SHORT_MYCELIUM_GRASS, 100, 60);
        fireRegistry.add(RegisterBlocks.POP_FLOWER, 100, 60);
    }

    private static void registerComposting() {

        addCompostable(GLOWSHROOM, 0.50F);
        addCompostable(BLUE_ROSE, 0.50F);
        addCompostable(ICEFLOWER, 0.50F);
        addCompostable(RegisterItems.BLUE_ROSE_SEEDS, 0.30F);
        addCompostable(RegisterItems.ICEFLOWER_SEEDS, 0.30F);
        addCompostable(POP_FLOWER, 0.30F);
        addCompostable(SHORT_MYCELIUM_GRASS, 0.30F);
        addCompostable(TALL_MYCELIUM_GRASS, 0.50F);
        addCompostable(RegisterItems.CHERRY, 0.65F);

    }

    private static void strippingWood() {
        BlockTransformerHelper.registerStripping(AZALEA_LOG, STRIPPED_AZALEA_LOG.defaultBlockState());
        BlockTransformerHelper.registerStripping(AZALEA_WOOD, STRIPPED_AZALEA_WOOD.defaultBlockState());
    }


    private static Block registerBlockWithoutBlockItem(String name, Function<BlockBehaviour.Properties, Block> function) {
        Block toRegister = function.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name))));
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name), toRegister);
    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> function) {
        Block toRegister = function.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name), toRegister);

    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        Block toRegister = function.apply(properties.setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name), toRegister);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name),
                new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix()
                        .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name)))));
    }

    private static void addFuel(ItemLike itemConvertible, int burnTicks) {
        Item item = itemConvertible.asItem();
        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(item, builder -> {
                builder.set(
                        DataComponents.COOKING_FUEL,
                        new CookingFuel(
                                new ResolvableInt.Constant(burnTicks),
                                new ResolvableFloat.Constant(1.0F)
                        )
                );
            });
        });
    }

    private static void addCompostable(ItemLike itemConvertible, float chance) {
        ResourceKey<ContextIntProvider> providerKey;
        if (chance <= 0.35f) {
            providerKey = ContextIntProviders.COMPOSTABLE_LOW;          // 30%
        } else if (chance <= 0.55f) {
            providerKey = ContextIntProviders.COMPOSTABLE_LOW_MEDIUM;   // 50%
        } else if (chance <= 0.75f) {
            providerKey = ContextIntProviders.COMPOSTABLE_MEDIUM;       // 65%
        } else if (chance <= 0.90f) {
            providerKey = ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH;  // 85%
        } else {
            providerKey = ContextIntProviders.COMPOSTABLE_ALWAYS_ADD_ONE; // 100%
        }

        Item item = itemConvertible.asItem();
        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(item, builder -> {
                builder.set(DataComponents.COMPOSTABLE, new Compostable(providerKey));
            });
        });
    }

    public static void registerBlocks() {
        VerseCraft.LOGGER.info("Registering Blocks for " + VerseCraft.MOD_ID);

        BlockEntityTypes.SHELF.addValidBlock(AZALEA_SHELF);
        BlockEntityTypes.SIGN.addValidBlock(AZALEA_SIGN);
        BlockEntityTypes.SIGN.addValidBlock(AZALEA_WALL_SIGN);
        BlockEntityTypes.HANGING_SIGN.addValidBlock(AZALEA_HANGING_SIGN);
        BlockEntityTypes.HANGING_SIGN.addValidBlock(AZALEA_WALL_HANGING_SIGN);
        BlockEntityTypes.CAMPFIRE.addValidBlock(COPPER_CAMPFIRE);


        strippingWood();
        registerFuels();
        registerFlammability();
        registerComposting();
    }
}
