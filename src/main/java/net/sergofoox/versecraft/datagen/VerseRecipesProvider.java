package net.sergofoox.versecraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import net.sergofoox.versecraft.registry.RegisterItems;
import net.sergofoox.versecraft.tags.VerseBlocksTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class VerseRecipesProvider extends FabricRecipeProvider {

    public VerseRecipesProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(
            HolderLookup.@NonNull Provider registries,
            @NonNull BootstrapContext<Recipe<?>> recipes,
            @NonNull BootstrapContext<Advancement> advancements
    ) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {

                //Acacia Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.ACACIA_MOSAIC, Blocks.ACACIA_SLAB);

                stairBuilder(RegisterBlocks.ACACIA_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.ACACIA_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.ACACIA_MOSAIC), has(RegisterBlocks.ACACIA_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ACACIA_MOSAIC_SLAB, RegisterBlocks.ACACIA_MOSAIC);

                //Azalea Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.AZALEA_MOSAIC, RegisterBlocks.AZALEA_SLAB);

                stairBuilder(RegisterBlocks.AZALEA_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.AZALEA_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.AZALEA_MOSAIC), has(RegisterBlocks.AZALEA_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.AZALEA_MOSAIC_SLAB, RegisterBlocks.AZALEA_MOSAIC);

                //Birch Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.BIRCH_MOSAIC, Blocks.BIRCH_SLAB);

                stairBuilder(RegisterBlocks.BIRCH_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.BIRCH_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.BIRCH_MOSAIC), has(RegisterBlocks.BIRCH_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.BIRCH_MOSAIC_SLAB, RegisterBlocks.BIRCH_MOSAIC);

                //Cherry Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.CHERRY_MOSAIC, Blocks.CHERRY_SLAB);

                stairBuilder(RegisterBlocks.CHERRY_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.CHERRY_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.CHERRY_MOSAIC), has(RegisterBlocks.CHERRY_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHERRY_MOSAIC_SLAB, RegisterBlocks.CHERRY_MOSAIC);

                //Crimson Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.CRIMSON_MOSAIC, Blocks.CRIMSON_SLAB);

                stairBuilder(RegisterBlocks.CRIMSON_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.CRIMSON_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.CRIMSON_MOSAIC), has(RegisterBlocks.CRIMSON_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CRIMSON_MOSAIC_SLAB, RegisterBlocks.CRIMSON_MOSAIC);

                //Dark Oak Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.DARK_OAK_MOSAIC, Blocks.DARK_OAK_SLAB);

                stairBuilder(RegisterBlocks.DARK_OAK_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.DARK_OAK_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.DARK_OAK_MOSAIC), has(RegisterBlocks.DARK_OAK_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DARK_OAK_MOSAIC_SLAB, RegisterBlocks.DARK_OAK_MOSAIC);

                //Jungle Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.JUNGLE_MOSAIC, Blocks.JUNGLE_SLAB);

                stairBuilder(RegisterBlocks.JUNGLE_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.JUNGLE_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.JUNGLE_MOSAIC), has(RegisterBlocks.JUNGLE_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.JUNGLE_MOSAIC_SLAB, RegisterBlocks.JUNGLE_MOSAIC);

                //Mangrove Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.MANGROVE_MOSAIC, Blocks.MANGROVE_SLAB);

                stairBuilder(RegisterBlocks.MANGROVE_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.MANGROVE_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.MANGROVE_MOSAIC), has(RegisterBlocks.MANGROVE_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.MANGROVE_MOSAIC_SLAB, RegisterBlocks.MANGROVE_MOSAIC);


                //Oak Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.OAK_MOSAIC, Blocks.OAK_SLAB);

                stairBuilder(RegisterBlocks.OAK_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.OAK_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.OAK_MOSAIC), has(RegisterBlocks.OAK_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.OAK_MOSAIC_SLAB, RegisterBlocks.OAK_MOSAIC);

                //Spruce Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.SPRUCE_MOSAIC, Blocks.SPRUCE_SLAB);

                stairBuilder(RegisterBlocks.SPRUCE_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.SPRUCE_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.SPRUCE_MOSAIC), has(RegisterBlocks.SPRUCE_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.SPRUCE_MOSAIC_SLAB, RegisterBlocks.SPRUCE_MOSAIC);

                //Pale Oak Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.PALE_OAK_MOSAIC, Blocks.PALE_OAK_SLAB);

                stairBuilder(RegisterBlocks.PALE_OAK_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.PALE_OAK_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.PALE_OAK_MOSAIC), has(RegisterBlocks.PALE_OAK_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.PALE_OAK_MOSAIC_SLAB, RegisterBlocks.PALE_OAK_MOSAIC);

                //Poplar Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.POPLAR_MOSAIC, Blocks.POPLAR_SLAB);

                stairBuilder(RegisterBlocks.POPLAR_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.POPLAR_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.POPLAR_MOSAIC), has(RegisterBlocks.POPLAR_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POPLAR_MOSAIC_SLAB, RegisterBlocks.POPLAR_MOSAIC);

                //Warded Mosaic
                mosaicBuilder(RecipeCategory.DECORATIONS, RegisterBlocks.WARPED_MOSAIC, Blocks.WARPED_SLAB);

                stairBuilder(RegisterBlocks.WARPED_MOSAIC_STAIRS, Ingredient.of(RegisterBlocks.WARPED_MOSAIC))
                        .unlockedBy(getHasName(RegisterBlocks.WARPED_MOSAIC), has(RegisterBlocks.WARPED_MOSAIC))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.WARPED_MOSAIC_SLAB, RegisterBlocks.WARPED_MOSAIC);

                //Tuff
                smeltingResultFromBase(RegisterBlocks.CRACKED_TUFF_BRICKS, Blocks.TUFF_BRICKS);

                //Calcite
                stairBuilder(RegisterBlocks.CALCITE_STAIRS, Ingredient.of(Blocks.CALCITE))
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .save(this.output);
                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_SLAB, Blocks.CALCITE);
                wall(RecipeCategory.DECORATIONS, RegisterBlocks.CALCITE_WALL, Blocks.CALCITE);
                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_CALCITE, RegisterBlocks.CALCITE_SLAB);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_STAIRS, Blocks.CALCITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_SLAB, Blocks.CALCITE, 2);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.CALCITE_WALL, Blocks.CALCITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_CALCITE, Blocks.CALCITE, 1);

                //Polished Calcite
                polished(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE, Blocks.CALCITE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE, Blocks.CALCITE, 1);

                stairBuilder(RegisterBlocks.POLISHED_CALCITE_STAIRS, Ingredient.of(RegisterBlocks.POLISHED_CALCITE))
                        .unlockedBy(getHasName(RegisterBlocks.POLISHED_CALCITE), has(RegisterBlocks.POLISHED_CALCITE))
                        .save(this.output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE_STAIRS, RegisterBlocks.POLISHED_CALCITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE_STAIRS, Blocks.CALCITE, 1);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE_SLAB, RegisterBlocks.POLISHED_CALCITE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE_SLAB, RegisterBlocks.POLISHED_CALCITE, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_CALCITE_SLAB, Blocks.CALCITE, 2);

                wall(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_CALCITE_WALL, RegisterBlocks.POLISHED_CALCITE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_CALCITE_WALL, RegisterBlocks.POLISHED_CALCITE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_CALCITE_WALL, Blocks.CALCITE, 1);

                //Calcite Bricks
                polished(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS, RegisterBlocks.POLISHED_CALCITE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS, RegisterBlocks.POLISHED_CALCITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS, Blocks.CALCITE, 1);

                smeltingResultFromBase(RegisterBlocks.CRACKED_CALCITE_BRICKS, RegisterBlocks.CALCITE_BRICKS);

                stairBuilder(RegisterBlocks.CALCITE_BRICKS_STAIRS, Ingredient.of(RegisterBlocks.CALCITE_BRICKS))
                        .unlockedBy(getHasName(RegisterBlocks.CALCITE_BRICKS), has(RegisterBlocks.CALCITE_BRICKS))
                        .save(this.output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS_STAIRS, RegisterBlocks.CALCITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS_STAIRS, RegisterBlocks.POLISHED_CALCITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS_STAIRS, Blocks.CALCITE, 1);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS_SLAB, RegisterBlocks.CALCITE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS_SLAB, RegisterBlocks.CALCITE_BRICKS, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS_SLAB, RegisterBlocks.POLISHED_CALCITE, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CALCITE_BRICKS_SLAB, Blocks.CALCITE, 2);

                wall(RecipeCategory.DECORATIONS, RegisterBlocks.CALCITE_BRICKS_WALL, RegisterBlocks.CALCITE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.CALCITE_BRICKS_WALL, RegisterBlocks.CALCITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.CALCITE_BRICKS_WALL, RegisterBlocks.POLISHED_CALCITE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.CALCITE_BRICKS_WALL, Blocks.CALCITE, 1);

                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_CALCITE_BRICKS, RegisterBlocks.CALCITE_BRICKS_SLAB);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_CALCITE_BRICKS, RegisterBlocks.CALCITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_CALCITE_BRICKS, RegisterBlocks.POLISHED_CALCITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_CALCITE_BRICKS, Blocks.CALCITE, 1);

                //Dripstone
                stairBuilder(RegisterBlocks.DRIPSTONE_STAIRS, Ingredient.of(Blocks.DRIPSTONE_BLOCK))
                        .unlockedBy(getHasName(Blocks.DRIPSTONE_BLOCK), has(Blocks.DRIPSTONE_BLOCK))
                        .save(this.output);
                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_SLAB, Blocks.DRIPSTONE_BLOCK);
                wall(RecipeCategory.DECORATIONS, RegisterBlocks.DRIPSTONE_WALL, Blocks.DRIPSTONE_BLOCK);
                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DRIPSTONE, RegisterBlocks.DRIPSTONE_SLAB);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_STAIRS, Blocks.DRIPSTONE_BLOCK, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_SLAB, Blocks.DRIPSTONE_BLOCK, 2);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.DRIPSTONE_WALL, Blocks.DRIPSTONE_BLOCK, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DRIPSTONE, Blocks.DRIPSTONE_BLOCK, 1);

                //Polished Dripstone
                polished(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DRIPSTONE, Blocks.DRIPSTONE_BLOCK);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DRIPSTONE, Blocks.DRIPSTONE_BLOCK, 1);

                stairBuilder(RegisterBlocks.POLISHED_DRIPSTONE_STAIRS, Ingredient.of(RegisterBlocks.POLISHED_DRIPSTONE))
                        .unlockedBy(getHasName(RegisterBlocks.POLISHED_DRIPSTONE), has(RegisterBlocks.POLISHED_DRIPSTONE))
                        .save(this.output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DRIPSTONE_STAIRS, RegisterBlocks.POLISHED_DRIPSTONE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DRIPSTONE_STAIRS, Blocks.DRIPSTONE_BLOCK, 1);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DRIPSTONE_SLAB, RegisterBlocks.POLISHED_DRIPSTONE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DRIPSTONE_SLAB, RegisterBlocks.POLISHED_DRIPSTONE, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.POLISHED_DRIPSTONE_SLAB, Blocks.DRIPSTONE_BLOCK, 2);

                wall(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_DRIPSTONE_WALL, RegisterBlocks.POLISHED_DRIPSTONE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_DRIPSTONE_WALL, RegisterBlocks.POLISHED_DRIPSTONE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_DRIPSTONE_WALL, Blocks.DRIPSTONE_BLOCK, 1);

                //Dripstone Bricks
                polished(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS, RegisterBlocks.POLISHED_DRIPSTONE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS, RegisterBlocks.POLISHED_DRIPSTONE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS, Blocks.DRIPSTONE_BLOCK, 1);

                smeltingResultFromBase(RegisterBlocks.CRACKED_DRIPSTONE_BRICKS, RegisterBlocks.DRIPSTONE_BRICKS);

                stairBuilder(RegisterBlocks.DRIPSTONE_BRICKS_STAIRS, Ingredient.of(RegisterBlocks.DRIPSTONE_BRICKS))
                        .unlockedBy(getHasName(RegisterBlocks.DRIPSTONE_BRICKS), has(RegisterBlocks.DRIPSTONE_BRICKS))
                        .save(this.output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS_STAIRS, RegisterBlocks.DRIPSTONE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS_STAIRS, RegisterBlocks.POLISHED_DRIPSTONE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS_STAIRS, Blocks.DRIPSTONE_BLOCK, 1);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS_SLAB, RegisterBlocks.DRIPSTONE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS_SLAB, RegisterBlocks.DRIPSTONE_BRICKS, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS_SLAB, RegisterBlocks.POLISHED_DRIPSTONE, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DRIPSTONE_BRICKS_SLAB, Blocks.DRIPSTONE_BLOCK, 2);

                wall(RecipeCategory.DECORATIONS, RegisterBlocks.DRIPSTONE_BRICKS_WALL, RegisterBlocks.DRIPSTONE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.DRIPSTONE_BRICKS_WALL, RegisterBlocks.DRIPSTONE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.DRIPSTONE_BRICKS_WALL, RegisterBlocks.POLISHED_DRIPSTONE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.DRIPSTONE_BRICKS_WALL, Blocks.DRIPSTONE_BLOCK, 1);

                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DRIPSTONE_BRICKS, RegisterBlocks.DRIPSTONE_BRICKS_SLAB);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DRIPSTONE_BRICKS, RegisterBlocks.DRIPSTONE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DRIPSTONE_BRICKS, RegisterBlocks.POLISHED_DRIPSTONE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DRIPSTONE_BRICKS, Blocks.DRIPSTONE_BLOCK, 1);

                //Chiseled Andesite
                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE, Blocks.ANDESITE_SLAB);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE, Blocks.ANDESITE, 1);

                // Polished Andesite
                wall(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_ANDESITE_WALL, Blocks.POLISHED_ANDESITE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_ANDESITE_WALL, Blocks.POLISHED_ANDESITE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_ANDESITE_WALL, Blocks.ANDESITE, 1);

                //Andesite Bricks
                polished(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS, Blocks.ANDESITE, 1);

                smeltingResultFromBase(RegisterBlocks.CRACKED_ANDESITE_BRICKS, RegisterBlocks.ANDESITE_BRICKS);

                stairBuilder(RegisterBlocks.ANDESITE_BRICKS_STAIRS, Ingredient.of(RegisterBlocks.ANDESITE_BRICKS))
                        .unlockedBy(getHasName(RegisterBlocks.ANDESITE_BRICKS), has(RegisterBlocks.ANDESITE_BRICKS))
                        .save(this.output);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS_STAIRS, RegisterBlocks.ANDESITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS_STAIRS, Blocks.POLISHED_ANDESITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS_STAIRS, Blocks.ANDESITE, 1);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS_SLAB, RegisterBlocks.ANDESITE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS_SLAB, RegisterBlocks.ANDESITE_BRICKS, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS_SLAB, Blocks.POLISHED_ANDESITE, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.ANDESITE_BRICKS_SLAB, Blocks.ANDESITE, 2);

                wall(RecipeCategory.DECORATIONS, RegisterBlocks.ANDESITE_BRICKS_WALL, RegisterBlocks.ANDESITE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.ANDESITE_BRICKS_WALL, RegisterBlocks.ANDESITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.ANDESITE_BRICKS_WALL,  Blocks.POLISHED_ANDESITE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.ANDESITE_BRICKS_WALL, Blocks.ANDESITE, 1);

                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, RegisterBlocks.ANDESITE_BRICKS_SLAB);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, RegisterBlocks.ANDESITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_ANDESITE_BRICKS, Blocks.ANDESITE, 1);

                //Chiseled Diorite
                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE, Blocks.DIORITE_SLAB);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE, Blocks.DIORITE, 1);

                // Polished Diorite
                wall(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_DIORITE_WALL, Blocks.POLISHED_DIORITE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_DIORITE_WALL, Blocks.POLISHED_DIORITE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_DIORITE_WALL, Blocks.DIORITE, 1);

                //Diorite Bricks

                polished(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS, Blocks.POLISHED_DIORITE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS, Blocks.POLISHED_DIORITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS, Blocks.DIORITE, 1);

                smeltingResultFromBase(RegisterBlocks.CRACKED_DIORITE_BRICKS, RegisterBlocks.DIORITE_BRICKS);

                stairBuilder(RegisterBlocks.DIORITE_BRICKS_STAIRS, Ingredient.of(RegisterBlocks.DIORITE_BRICKS))
                        .unlockedBy(getHasName(RegisterBlocks.DIORITE_BRICKS), has(RegisterBlocks.DIORITE_BRICKS))
                        .save(this.output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS_STAIRS, RegisterBlocks.DIORITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS_STAIRS, Blocks.POLISHED_DIORITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS_STAIRS, Blocks.DIORITE, 1);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS_SLAB, RegisterBlocks.DIORITE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS_SLAB, RegisterBlocks.DIORITE_BRICKS, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS_SLAB, Blocks.POLISHED_DIORITE, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.DIORITE_BRICKS_SLAB, Blocks.DIORITE, 2);

                wall(RecipeCategory.DECORATIONS, RegisterBlocks.DIORITE_BRICKS_WALL, RegisterBlocks.DIORITE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.DIORITE_BRICKS_WALL, RegisterBlocks.DIORITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.DIORITE_BRICKS_WALL,  Blocks.POLISHED_DIORITE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.DIORITE_BRICKS_WALL, Blocks.DIORITE, 1);

                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, RegisterBlocks.DIORITE_BRICKS_SLAB);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, RegisterBlocks.DIORITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, Blocks.POLISHED_DIORITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_DIORITE_BRICKS, Blocks.DIORITE, 1);

                //Chiseled Granite
                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE, Blocks.GRANITE_SLAB);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE, Blocks.GRANITE, 1);

                // Polished Granite
                wall(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_GRANITE_WALL, Blocks.POLISHED_GRANITE);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_GRANITE_WALL, Blocks.POLISHED_GRANITE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.POLISHED_GRANITE_WALL, Blocks.GRANITE, 1);

                //Granite Bricks

                polished(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS, Blocks.POLISHED_GRANITE);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS, Blocks.POLISHED_GRANITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS, Blocks.GRANITE, 1);

                smeltingResultFromBase(RegisterBlocks.CRACKED_GRANITE_BRICKS, RegisterBlocks.GRANITE_BRICKS);

                stairBuilder(RegisterBlocks.GRANITE_BRICKS_STAIRS, Ingredient.of(RegisterBlocks.GRANITE_BRICKS))
                        .unlockedBy(getHasName(RegisterBlocks.GRANITE_BRICKS), has(RegisterBlocks.GRANITE_BRICKS))
                        .save(this.output);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS_STAIRS, RegisterBlocks.GRANITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS_STAIRS, Blocks.POLISHED_GRANITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS_STAIRS, Blocks.GRANITE, 1);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS_SLAB, RegisterBlocks.GRANITE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS_SLAB, RegisterBlocks.GRANITE_BRICKS, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS_SLAB, Blocks.POLISHED_GRANITE, 2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.GRANITE_BRICKS_SLAB, Blocks.GRANITE, 2);

                wall(RecipeCategory.DECORATIONS, RegisterBlocks.GRANITE_BRICKS_WALL, RegisterBlocks.GRANITE_BRICKS);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.GRANITE_BRICKS_WALL, RegisterBlocks.GRANITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.GRANITE_BRICKS_WALL,  Blocks.POLISHED_GRANITE, 1);
                stonecutterResultFromBase(RecipeCategory.DECORATIONS, RegisterBlocks.GRANITE_BRICKS_WALL, Blocks.GRANITE, 1);

                chiseled(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, RegisterBlocks.GRANITE_BRICKS_SLAB);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, RegisterBlocks.GRANITE_BRICKS, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, Blocks.POLISHED_GRANITE, 1);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.CHISELED_GRANITE_BRICKS, Blocks.GRANITE, 1);

                // Soul Jack o'Lantern
                shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.SOUL_JACK_O_LANTERN)
                        .define('A', Blocks.CARVED_PUMPKIN)
                        .define('B', Blocks.SOUL_TORCH)
                        .pattern("A")
                        .pattern("B")
                        .unlockedBy(getHasName(Blocks.CARVED_PUMPKIN), has(Blocks.CARVED_PUMPKIN))
                        .save(this.output);

                // Copper Jack o'Lantern
                shaped(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.COPPER_JACK_O_LANTERN)
                        .define('A', Blocks.CARVED_PUMPKIN)
                        .define('B', Blocks.COPPER_TORCH)
                        .pattern("A")
                        .pattern("B")
                        .unlockedBy(getHasName(Blocks.CARVED_PUMPKIN), has(Blocks.CARVED_PUMPKIN))
                        .save(this.output);

                // Copper Campfire
                shaped(RecipeCategory.DECORATIONS, RegisterBlocks.COPPER_CAMPFIRE)
                        .define('L', ItemTags.LOGS)
                        .define('S', Items.STICK)
                        .define('#', Items.RAW_COPPER)
                        .pattern(" S ")
                        .pattern("S#S")
                        .pattern("LLL")
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .unlockedBy(getHasName(Items.RAW_COPPER), has(Items.RAW_COPPER))
                        .save(this.output);

                oneToOneConversionRecipe(Items.DYE.cyan(), RegisterBlocks.POP_FLOWER, "cyan_dye");
                oneToOneConversionRecipe(Items.DYE.yellow(), RegisterBlocks.BUTTERCUP, "yellow_dye");
                oneToOneConversionRecipe(Items.DYE.lightBlue(), RegisterBlocks.BLUE_ROSE, "light_blue_dye");
                oneToOneConversionRecipe(Items.DYE.lightBlue(), RegisterBlocks.ICEFLOWER, "light_blue_dye");

                //Azalea
                planksFromLog(RegisterBlocks.AZALEA_PLANKS, VerseBlocksTags.Items.AZALEA_LOGS, 4);

                woodFromLogs(RegisterBlocks.AZALEA_WOOD, RegisterBlocks.AZALEA_LOG);
                woodFromLogs(RegisterBlocks.STRIPPED_AZALEA_WOOD, RegisterBlocks.STRIPPED_AZALEA_LOG);

                stairBuilder(RegisterBlocks.AZALEA_STAIRS, Ingredient.of(RegisterBlocks.AZALEA_PLANKS))
                        .unlockedBy(getHasName(RegisterBlocks.AZALEA_PLANKS), has(RegisterBlocks.AZALEA_PLANKS))
                        .save(this.output);

                slab(RecipeCategory.BUILDING_BLOCKS, RegisterBlocks.AZALEA_SLAB, RegisterBlocks.AZALEA_PLANKS);

                doorBuilder(RegisterBlocks.AZALEA_DOOR, Ingredient.of(RegisterBlocks.AZALEA_PLANKS))
                        .unlockedBy(getHasName(RegisterBlocks.AZALEA_PLANKS), has(RegisterBlocks.AZALEA_PLANKS))
                        .save(this.output);

                trapdoorBuilder(RegisterBlocks.AZALEA_TRAPDOOR, Ingredient.of(RegisterBlocks.AZALEA_PLANKS))
                        .unlockedBy(getHasName(RegisterBlocks.AZALEA_PLANKS), has(RegisterBlocks.AZALEA_PLANKS))
                        .save(this.output);

                shaped(RecipeCategory.DECORATIONS, RegisterBlocks.AZALEA_FENCE, 3)
                        .define('W', RegisterBlocks.AZALEA_PLANKS)
                        .define('#', Items.STICK)
                        .pattern("W#W")
                        .pattern("W#W")
                        .group("wooden_fence")
                        .unlockedBy(getHasName(RegisterBlocks.AZALEA_PLANKS), has(RegisterBlocks.AZALEA_PLANKS))
                        .save(this.output);

                shaped(RecipeCategory.REDSTONE, RegisterBlocks.AZALEA_FENCE_GATE, 1)
                        .define('W', RegisterBlocks.AZALEA_PLANKS)
                        .define('#', Items.STICK)
                        .pattern("#W#")
                        .pattern("#W#")
                        .group("wooden_fence_gate")
                        .unlockedBy(getHasName(RegisterBlocks.AZALEA_PLANKS), has(RegisterBlocks.AZALEA_PLANKS))
                        .save(this.output);

                shapeless(RecipeCategory.REDSTONE, RegisterBlocks.AZALEA_BUTTON)
                        .requires(RegisterBlocks.AZALEA_PLANKS)
                        .group("wooden_button")
                        .unlockedBy(getHasName(RegisterBlocks.AZALEA_PLANKS), has(RegisterBlocks.AZALEA_PLANKS))
                        .save(this.output);

                pressurePlate(RegisterBlocks.AZALEA_PRESSURE_PLATE, RegisterBlocks.AZALEA_PLANKS);

                shaped(RecipeCategory.DECORATIONS, RegisterItems.AZALEA_SIGN, 3)
                        .define('#', RegisterBlocks.AZALEA_PLANKS)
                        .define('X', Items.STICK)
                        .pattern("###")
                        .pattern("###")
                        .pattern(" X ")
                        .group("wooden_sign")
                        .unlockedBy(getHasName(RegisterBlocks.AZALEA_PLANKS), has(RegisterBlocks.AZALEA_PLANKS))
                        .save(this.output);

                hangingSignBuilder(RegisterItems.AZALEA_HANGING_SIGN, Ingredient.of(RegisterBlocks.STRIPPED_AZALEA_LOG))
                        .group("hanging_sign")
                        .unlockedBy(getHasName(RegisterBlocks.STRIPPED_AZALEA_LOG), has(RegisterBlocks.STRIPPED_AZALEA_LOG))
                        .save(this.output);

                shaped(RecipeCategory.DECORATIONS, RegisterBlocks.AZALEA_SHELF, 6)
                        .define('#', RegisterBlocks.STRIPPED_AZALEA_LOG)
                        .pattern("###")
                        .pattern("   ")
                        .pattern("###")
                        .group("shelf")
                        .unlockedBy(getHasName(RegisterBlocks.STRIPPED_AZALEA_LOG), has(RegisterBlocks.STRIPPED_AZALEA_LOG))
                        .save(this.output);

                woodenBoat(RegisterItems.AZALEA_BOAT, RegisterBlocks.AZALEA_PLANKS);
                chestBoat(RegisterItems.AZALEA_CHEST_BOAT, RegisterItems.AZALEA_BOAT);
            }
        };
    }
}
