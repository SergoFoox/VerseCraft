package net.sergofoox.versecraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DryVegetationBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sergofoox.versecraft.registry.RegisterBlocks;

public class TallMyceliumGrass extends DryVegetationBlock implements BonemealableBlock {
    private static final VoxelShape SHAPE = Block.column(14.0, 0.0, 16.0);

    public TallMyceliumGrass(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, BonemealSource bonemealSource) {
        return BonemealableBlock.hasSpreadableNeighbourPos(levelReader, blockPos, RegisterBlocks.SHORT_MYCELIUM_GRASS.defaultBlockState());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState, BonemealSource bonemealSource) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState, BonemealSource bonemealSource) {
        BonemealableBlock.findSpreadableNeighbourPos(serverLevel, blockPos, RegisterBlocks.SHORT_MYCELIUM_GRASS.defaultBlockState())
                .ifPresent(pos -> serverLevel.setBlockAndUpdate(pos, RegisterBlocks.SHORT_MYCELIUM_GRASS.defaultBlockState()));
    }
}
