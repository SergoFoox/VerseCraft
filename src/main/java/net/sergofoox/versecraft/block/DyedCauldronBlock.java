package net.sergofoox.versecraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.sergofoox.versecraft.block.entity.DyedCauldronBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DyedCauldronBlock extends AbstractCauldronBlock implements EntityBlock {

    // В Bedrock Edition котлы имеют 6 уровней наполнения (fill_level 0..6).
    // Полный котёл = 6 уровней (хватает на покраску 6 элементов брони).
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 1, 6);

    public DyedCauldronBlock(Properties properties) {
        super(properties, CauldronInteractions.EMPTY);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 6));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.getValue(LEVEL) == 6;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        return (state.getValue(LEVEL) + 1) / 2;
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return (6.0 + (double) state.getValue(LEVEL) * 1.5) / 16.0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyedCauldronBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        // Попытка налить зелье или воду из бутылочки -> конфликт жидкостей и испарение
        if (PotionCauldronBlock.isPotionItem(itemStack)) {
            PotionCauldronBlock.triggerEvaporation(level, pos, player, hand, itemStack, Items.GLASS_BOTTLE);
            return InteractionResult.SUCCESS;
        }

        // Попытка налить ведро воды, лавы или рыхлого снега -> конфликт жидкостей и испарение
        if (itemStack.is(Items.WATER_BUCKET) || itemStack.is(Items.LAVA_BUCKET) || itemStack.is(Items.POWDER_SNOW_BUCKET)) {
            PotionCauldronBlock.triggerEvaporation(level, pos, player, hand, itemStack, Items.BUCKET);
            return InteractionResult.SUCCESS;
        }

        // 1 & 2. Добавление и смешивание красителей (включая красители-заменители из Bedrock)
        DyeColor dyeColor = getDyeColor(itemStack);
        if (dyeColor != null) {
            if (!level.isClientSide()) {
                if (level.getBlockEntity(pos) instanceof DyedCauldronBlockEntity be) {
                    int oldColor = be.getColor();
                    int newColor = DyedItemColor.applyDyes(new DyedItemColor(oldColor), List.of(dyeColor)).rgb();
                    be.setColor(newColor);
                }
                if (!player.hasInfiniteMaterials()) {
                    itemStack.shrink(1);
                }
                level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.35f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }

        // 3. Покраска кожаной/волчьей брони (заменяет цвет брони, расходует 1 уровень из 6)
        if (itemStack.is(ItemTags.CAULDRON_CAN_REMOVE_DYE)) {
            if (!level.isClientSide()) {
                int color = DyedCauldronBlockEntity.DEFAULT_WATER_COLOR;
                if (level.getBlockEntity(pos) instanceof DyedCauldronBlockEntity be) {
                    color = be.getColor();
                }
                itemStack.set(DataComponents.DYED_COLOR, new DyedItemColor(color));
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.35f, 1.0f);
                lowerFillLevel(state, level, pos);
            }
            return InteractionResult.SUCCESS;
        }

        // 4. Забор воды пустым ведром при полном котле (уровень 6) -> обычное ведро воды
        if (itemStack.is(Items.BUCKET) && state.getValue(LEVEL) == 6) {
            if (!level.isClientSide()) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.WATER_BUCKET), false));
                level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }

        // 5. Забор воды пустой стеклянной бутылочкой -> обычная бутылочка с водой (расходует 2 уровня из 6)
        if (itemStack.is(Items.GLASS_BOTTLE)) {
            if (!level.isClientSide()) {
                ItemStack bottle = PotionContents.createItemStack(Items.POTION, Potions.WATER);
                player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, bottle, false));
                level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.35f, 1.0f);
                
                int current = state.getValue(LEVEL);
                int color = DyedCauldronBlockEntity.DEFAULT_WATER_COLOR;
                if (level.getBlockEntity(pos) instanceof DyedCauldronBlockEntity be) {
                    color = be.getColor();
                }
                if (current > 2) {
                    BlockState newState = state.setValue(LEVEL, current - 2);
                    level.setBlock(pos, newState, 3);
                    if (level.getBlockEntity(pos) instanceof DyedCauldronBlockEntity be) {
                        be.setColor(color);
                    }
                } else {
                    level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static @Nullable DyeColor getDyeColor(ItemStack stack) {
        if (stack.has(DataComponents.DYE)) {
            return stack.get(DataComponents.DYE);
        }
        if (stack.is(Items.BONE_MEAL)) return DyeColor.WHITE;
        if (stack.is(Items.INK_SAC)) return DyeColor.BLACK;
        if (stack.is(Items.LAPIS_LAZULI)) return DyeColor.BLUE;
        if (stack.is(Items.COCOA_BEANS)) return DyeColor.BROWN;
        return null;
    }

    public static void lowerFillLevel(BlockState state, Level level, BlockPos pos) {
        int current = state.getValue(LEVEL);
        int color = DyedCauldronBlockEntity.DEFAULT_WATER_COLOR;
        if (level.getBlockEntity(pos) instanceof DyedCauldronBlockEntity be) {
            color = be.getColor();
        }
        if (current > 1) {
            BlockState newState = state.setValue(LEVEL, current - 1);
            level.setBlock(pos, newState, 3);
            if (level.getBlockEntity(pos) instanceof DyedCauldronBlockEntity be) {
                be.setColor(color);
            }
        } else {
            level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
        }
    }
}
