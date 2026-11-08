package net.sergofoox.versecraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutPiece;
import net.sergofoox.versecraft.block.entity.PotionCauldronBlockEntity;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SwampHutPiece.class)
public abstract class SwampHutPieceMixin extends StructurePiece {

    protected SwampHutPieceMixin(StructurePieceType type, int genDepth, BoundingBox boundingBox) {
        super(type, genDepth, boundingBox);
    }

    @Inject(method = "postProcess", at = @At("TAIL"))
    private void versecraft$spawnPotionCauldron(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox chunkBB, ChunkPos chunkPos, BlockPos pivot, CallbackInfo ci) {
        BlockPos pos = this.getWorldPos(4, 2, 6);
        if (chunkBB.isInside(pos) && level.getBlockState(pos).is(Blocks.CAULDRON)) {
            List<Holder<Potion>> potions = List.of(
                    Potions.HEALING,
                    Potions.FIRE_RESISTANCE,
                    Potions.SWIFTNESS,
                    Potions.POISON,
                    Potions.WATER_BREATHING,
                    Potions.SLOWNESS,
                    Potions.WEAKNESS
            );
            int fillLevel = 1 + random.nextInt(2);
            Holder<Potion> potion = potions.get(random.nextInt(potions.size()));
            BlockState state = RegisterBlocks.POTION_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, fillLevel);
            level.setBlock(pos, state, 2);
            if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity be) {
                be.setPotionContents(new PotionContents(potion));
            }
        }
    }
}
