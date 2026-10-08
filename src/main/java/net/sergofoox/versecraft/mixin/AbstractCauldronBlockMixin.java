package net.sergofoox.versecraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.sergofoox.versecraft.block.DyedCauldronBlock;
import net.sergofoox.versecraft.block.PotionCauldronBlock;
import net.sergofoox.versecraft.block.entity.DyedCauldronBlockEntity;
import net.sergofoox.versecraft.block.entity.PotionCauldronBlockEntity;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractCauldronBlock.class)
public abstract class AbstractCauldronBlockMixin {

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void versecraft$cauldronInteractions(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        // 1. Наливание зелья в пустой котёл
        if (state.is(Blocks.CAULDRON) && PotionCauldronBlock.isPotionItem(itemStack)) {
            PotionContents contents = itemStack.get(DataComponents.POTION_CONTENTS);
            if (contents != null) {
                if (contents.is(Potions.WATER)) {
                    // Обычная бутылочка с водой обрабатывается ванильной CauldronInteraction.FILL_WATER.
                    // Для взрывной и туманной бутылочек с водой добавляем обработку здесь:
                    if (itemStack.is(Items.SPLASH_POTION) || itemStack.is(Items.LINGERING_POTION)) {
                        if (!level.isClientSide()) {
                            level.setBlock(pos, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 1), 3);
                            player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE), false));
                            level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.35f, 1.0f);
                        }
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        return;
                    }
                } else {
                    if (!level.isClientSide()) {
                        PotionCauldronBlock.PotionBottleType bottleType = PotionCauldronBlock.PotionBottleType.byItemStack(itemStack);
                        BlockState newState = RegisterBlocks.POTION_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 1);
                        level.setBlock(pos, newState, 3);
                        if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity be) {
                            be.setPotion(contents, bottleType != null ? bottleType : PotionCauldronBlock.PotionBottleType.NORMAL);
                        }
                        player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE), false));
                        level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.35f, 1.0f);
                    }
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    return;
                }
            }
        }

        // 2. Взаимодействие с водным котлом
        if (state.is(Blocks.WATER_CAULDRON)) {
            // Краситель (или костная мука, лазурит, какао-бобы, чернильный мешок) -> Окрашивание воды (Bedrock mechanic)
            DyeColor dyeColor = DyedCauldronBlock.getDyeColor(itemStack);
            if (dyeColor != null) {
                if (!level.isClientSide()) {
                    int fillLevel = state.getValue(LayeredCauldronBlock.LEVEL) * 2;
                    BlockState newState = RegisterBlocks.DYED_WATER_CAULDRON.defaultBlockState().setValue(DyedCauldronBlock.LEVEL, fillLevel);
                    level.setBlock(pos, newState, 3);
                    if (level.getBlockEntity(pos) instanceof DyedCauldronBlockEntity be) {
                        int color = DyedItemColor.applyDyes((DyedItemColor) null, List.of(dyeColor)).rgb();
                        be.setColor(color);
                    }
                    if (!player.hasInfiniteMaterials()) {
                        itemStack.shrink(1);
                    }
                    level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.35f, 1.0f);
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }

            // Наливание зелья в водный котёл -> взрывное испарение (смешивание разных жидкостей)
            if (PotionCauldronBlock.isPotionItem(itemStack)) {
                PotionContents contents = itemStack.get(DataComponents.POTION_CONTENTS);
                if (contents != null) {
                    if (contents.is(Potions.WATER)) {
                        if (itemStack.is(Items.SPLASH_POTION) || itemStack.is(Items.LINGERING_POTION)) {
                            int levelVal = state.getValue(LayeredCauldronBlock.LEVEL);
                            if (levelVal < 3) {
                                if (!level.isClientSide()) {
                                    level.setBlock(pos, state.setValue(LayeredCauldronBlock.LEVEL, levelVal + 1), 3);
                                    player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE), false));
                                    level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.35f, 1.0f);
                                }
                                cir.setReturnValue(InteractionResult.SUCCESS);
                                return;
                            }
                        }
                    } else {
                        PotionCauldronBlock.triggerEvaporation(level, pos, player, hand, itemStack, Items.GLASS_BOTTLE);
                        cir.setReturnValue(InteractionResult.SUCCESS);
                        return;
                    }
                }
            }
        }

        // 3. Наливание зелья в лавовый котёл -> взрывное испарение
        if (state.is(Blocks.LAVA_CAULDRON) && PotionCauldronBlock.isPotionItem(itemStack)) {
            PotionContents contents = itemStack.get(DataComponents.POTION_CONTENTS);
            if (contents != null) {
                PotionCauldronBlock.triggerEvaporation(level, pos, player, hand, itemStack, Items.GLASS_BOTTLE);
                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }
        }

        // 4. Наливание зелья в котёл с рыхлым снегом -> взрывное испарение
        if (state.is(Blocks.POWDER_SNOW_CAULDRON) && PotionCauldronBlock.isPotionItem(itemStack)) {
            PotionContents contents = itemStack.get(DataComponents.POTION_CONTENTS);
            if (contents != null) {
                PotionCauldronBlock.triggerEvaporation(level, pos, player, hand, itemStack, Items.GLASS_BOTTLE);
                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }
        }
    }
}
