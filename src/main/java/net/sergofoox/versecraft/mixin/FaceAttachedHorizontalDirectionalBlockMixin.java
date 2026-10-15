package net.sergofoox.versecraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FaceAttachedHorizontalDirectionalBlock.class)
public abstract class FaceAttachedHorizontalDirectionalBlockMixin {

    @Inject(
            method = "canAttach(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void versecraft$canAttachToPiston(
            LevelReader level, BlockPos pos, Direction direction,
            CallbackInfoReturnable<Boolean> cir
    ) {
        BlockPos relative = pos.relative(direction);
        BlockState state = level.getBlockState(relative);
        if (state.getBlock() instanceof PistonBaseBlock) {
            Direction pistonFacing = state.getValue(PistonBaseBlock.FACING);
            if (direction.getOpposite() != pistonFacing) {
                cir.setReturnValue(true);
            }
        } else if (state.is(Blocks.MOVING_PISTON)) {
            BlockEntity be = level.getBlockEntity(relative);
            if (be instanceof PistonMovingBlockEntity pmbe) {
                if (pmbe.isSourcePiston() || pmbe.getMovedState().getBlock() instanceof PistonBaseBlock) {
                    Direction pistonFacing = pmbe.getMovedState().hasProperty(PistonBaseBlock.FACING)
                            ? pmbe.getMovedState().getValue(PistonBaseBlock.FACING)
                            : (state.hasProperty(MovingPistonBlock.FACING) ? state.getValue(MovingPistonBlock.FACING) : null);
                    if (pistonFacing != null && direction.getOpposite() != pistonFacing) {
                        cir.setReturnValue(true);
                    }
                }
            } else if (state.hasProperty(MovingPistonBlock.FACING)) {
                Direction pistonFacing = state.getValue(MovingPistonBlock.FACING);
                if (direction.getOpposite() != pistonFacing) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
