package net.sergofoox.versecraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.sergofoox.versecraft.tags.VerseBlocksTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Shadow public abstract Block getBlock();

    @Inject(method = "getPistonPushReaction", at = @At("HEAD"), cancellable = true)
    private void versecraft$modifyPushReaction(CallbackInfoReturnable<PushReaction> cir) {
        BlockState self = (BlockState) (Object) this;
        if (this.getBlock() instanceof CampfireBlock || self.is(BlockTags.CAMPFIRES)) {
            cir.setReturnValue(PushReaction.POPPED);
        } else if (this.getBlock() == Blocks.BELL) {
            cir.setReturnValue(PushReaction.PUSH_PULL);
        } else if (self.is(VerseBlocksTags.Blocks.IMMOVABLE) || this.getBlock() == Blocks.GRINDSTONE || this.getBlock() == Blocks.LODESTONE) {
            cir.setReturnValue(PushReaction.IMMOVEABLE);
        }
    }

    @Inject(
            method = "isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/SupportType;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void versecraft$allowPistonAttachments(
            BlockGetter level, BlockPos pos, Direction direction, SupportType supportType,
            CallbackInfoReturnable<Boolean> cir
    ) {
        versecraft$checkPistonSturdy(level, pos, direction, cir);
    }

    @Inject(
            method = "isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void versecraft$allowPistonAttachments3(
            BlockGetter level, BlockPos pos, Direction direction,
            CallbackInfoReturnable<Boolean> cir
    ) {
        versecraft$checkPistonSturdy(level, pos, direction, cir);
    }

    @Unique
    private void versecraft$checkPistonSturdy(
            BlockGetter level, BlockPos pos, Direction direction,
            CallbackInfoReturnable<Boolean> cir
    ) {
        BlockState state = (BlockState) (Object) this;
        if (this.getBlock() instanceof PistonBaseBlock) {
            Direction pistonFacing = state.getValue(PistonBaseBlock.FACING);
            if (direction != pistonFacing) {
                cir.setReturnValue(true);
            }
        } else if (this.getBlock() instanceof MovingPistonBlock) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof PistonMovingBlockEntity pmbe) {
                if (pmbe.isSourcePiston() || pmbe.getMovedState().getBlock() instanceof PistonBaseBlock) {
                    Direction pistonFacing = pmbe.getMovedState().hasProperty(PistonBaseBlock.FACING)
                            ? pmbe.getMovedState().getValue(PistonBaseBlock.FACING)
                            : (state.hasProperty(MovingPistonBlock.FACING) ? state.getValue(MovingPistonBlock.FACING) : null);
                    if (pistonFacing != null && direction != pistonFacing) {
                        cir.setReturnValue(true);
                    }
                }
            } else if (state.hasProperty(MovingPistonBlock.FACING)) {
                Direction pistonFacing = state.getValue(MovingPistonBlock.FACING);
                if (direction != pistonFacing) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
