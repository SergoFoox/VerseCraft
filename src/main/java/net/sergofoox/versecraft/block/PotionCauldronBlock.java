package net.sergofoox.versecraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.sergofoox.versecraft.block.entity.PotionCauldronBlockEntity;
import org.jetbrains.annotations.Nullable;

public class PotionCauldronBlock extends LayeredCauldronBlock implements EntityBlock {

    public PotionCauldronBlock(Properties properties) {
        super(Biome.Precipitation.RAIN, CauldronInteractions.EMPTY, properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PotionCauldronBlockEntity(pos, state);
    }

    @Override
    public void handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        // В Bedrock Edition дождь не влияет на котлы с зельем
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isInside) {
        // В Bedrock Edition зелья в котле не тушат огонь и не накладывают эффекты на сущностей
    }

    @Override
    protected boolean canReceiveStalactiteDrip(Fluid fluid) {
        // Капельники не наполняют и не разбавляют котлы с зельями
        return false;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(3) == 0) {
            if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity be) {
                int color = be.getColor();
                int fillLevel = state.getValue(LEVEL);
                double y = pos.getY() + (0.375 + (fillLevel - 1) * 0.28);
                double x = pos.getX() + 0.25 + random.nextDouble() * 0.5;
                double z = pos.getZ() + 0.25 + random.nextDouble() * 0.5;
                level.addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, color), x, y, z, 0.0, 0.02, 0.0);
            }
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity be)) {
            return InteractionResult.PASS;
        }

        PotionContents cauldronContents = be.getPotionContents();
        int currentLevel = state.getValue(LEVEL);

        // 1. Попытка налить зелье (обычное, взрывное, оседающее)
        if (itemStack.has(DataComponents.POTION_CONTENTS)) {
            PotionContents heldContents = itemStack.get(DataComponents.POTION_CONTENTS);
            if (heldContents != null) {
                if (heldContents.is(Potions.WATER)) {
                    // Вода в зелье -> испарение со звуком взрыва (смешивание жидкостей)
                    triggerEvaporation(level, pos, player, hand, itemStack, Items.GLASS_BOTTLE);
                    return InteractionResult.SUCCESS;
                }

                if (isSamePotion(cauldronContents, heldContents)) {
                    if (currentLevel < 3) {
                        if (!level.isClientSide()) {
                            level.setBlock(pos, state.setValue(LEVEL, currentLevel + 1), 3);
                            if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity newBe) {
                                newBe.setPotionContents(cauldronContents);
                            }
                            if (!player.hasInfiniteMaterials()) {
                                player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
                            }
                            level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.PASS;
                } else {
                    // Разные зелья -> испарение со звуком взрыва (как в Bedrock)
                    triggerEvaporation(level, pos, player, hand, itemStack, Items.GLASS_BOTTLE);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        // 2. Попытка налить ведро воды или лавы
        if (itemStack.is(Items.WATER_BUCKET) || itemStack.is(Items.LAVA_BUCKET)) {
            triggerEvaporation(level, pos, player, hand, itemStack, Items.BUCKET);
            return InteractionResult.SUCCESS;
        }

        // 3. Забор зелья пустой бутылочкой
        if (itemStack.is(Items.GLASS_BOTTLE)) {
            if (!level.isClientSide()) {
                if (currentLevel > 1) {
                    level.setBlock(pos, state.setValue(LEVEL, currentLevel - 1), 3);
                    if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity newBe) {
                        newBe.setPotionContents(cauldronContents);
                    }
                } else {
                    level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
                }

                ItemStack potionBottle = new ItemStack(Items.POTION);
                potionBottle.set(DataComponents.POTION_CONTENTS, cauldronContents);

                // Работает одинаково правильно как в выживании, так и в креативе (как с ведрами воды)
                player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, potionBottle));
                level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));
            }
            return InteractionResult.SUCCESS;
        }

        // 4. Окунание стрел (создание стрел с эффектами зелья, эксклюзив Bedrock)
        if (itemStack.is(Items.ARROW)) {
            if (!level.isClientSide()) {
                int heldCount = itemStack.getCount();
                int maxDippable;
                if (currentLevel == 3) {
                    maxDippable = 64; // Полный котёл позволяет окунуть полный стак (64 стрелы)
                } else if (currentLevel == 2) {
                    maxDippable = 32;
                } else {
                    maxDippable = 16;
                }
                int arrowsToTip = Math.min(heldCount, maxDippable);

                int levelsConsumed;
                if (arrowsToTip > 32) {
                    levelsConsumed = 3;
                } else if (arrowsToTip > 16) {
                    levelsConsumed = 2;
                } else {
                    levelsConsumed = 1;
                }

                ItemStack tippedArrows = new ItemStack(Items.TIPPED_ARROW, arrowsToTip);
                tippedArrows.set(DataComponents.POTION_CONTENTS, cauldronContents);

                if (!player.hasInfiniteMaterials()) {
                    if (arrowsToTip == heldCount) {
                        player.setItemInHand(hand, tippedArrows);
                    } else {
                        itemStack.shrink(arrowsToTip);
                        if (!player.getInventory().add(tippedArrows)) {
                            Block.popResource(level, pos, tippedArrows);
                        }
                    }
                } else {
                    if (!player.getInventory().add(tippedArrows)) {
                        Block.popResource(level, pos, tippedArrows);
                    }
                }

                int newLevel = currentLevel - levelsConsumed;
                if (newLevel <= 0) {
                    level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
                } else {
                    level.setBlock(pos, state.setValue(LEVEL, newLevel), 3);
                    if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity newBe) {
                        newBe.setPotionContents(cauldronContents);
                    }
                }

                level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0f, 1.2f);
                level.playSound(null, pos, SoundEvents.SPLASH_POTION_BREAK, SoundSource.BLOCKS, 0.4f, 1.4f);
                player.awardStat(Stats.ITEM_USED.get(Items.ARROW));
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static boolean isSamePotion(PotionContents a, PotionContents b) {
        if (a == null || b == null) {
            return false;
        }
        return a.potion().equals(b.potion()) && a.customEffects().equals(b.customEffects());
    }

    public static void triggerEvaporation(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack itemStack, @Nullable Item returnItem) {
        if (!level.isClientSide()) {
            level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 10, 0.25, 0.25, 0.25, 0.05);
                serverLevel.sendParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 5, 0.15, 0.15, 0.15, 0.05);
            }
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 0.5f, 1.5f);
            if (!player.hasInfiniteMaterials()) {
                if (returnItem != null) {
                    player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(returnItem)));
                } else {
                    itemStack.shrink(1);
                }
            }
        }
    }
}
