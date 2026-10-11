package net.sergofoox.versecraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.sergofoox.versecraft.tags.VerseBlocksTags;
import net.sergofoox.versecraft.util.CarriedBlockEntityHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBaseBlock.class)
public abstract class PistonBaseBlockMixin {

    @Inject(
            method = "isPushable",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;hasBlockEntity()Z"
            ),
            cancellable = true
    )
    private static void versecraft$allowMovableBlockEntities(
            BlockState state, Level level, BlockPos pos, Direction moveDir, boolean allowDestroy, Direction pistonFacing,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (versecraft$isPushableBlockEntity(state)) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(
            method = "moveBlocks",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/piston/MovingPistonBlock;newMovingBlockEntity(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;ZZ)Lnet/minecraft/world/level/block/entity/BlockEntity;"
            )
    )
    private BlockEntity versecraft$captureAndTransferBlockEntity(
            BlockPos pos, BlockState state, BlockState movedState, Direction direction, boolean extending, boolean isSourcePiston,
            Level level, BlockPos pistonPos, Direction pistonFacing, boolean isExtending
    ) {
        BlockEntity movingPistonBe = MovingPistonBlock.newMovingBlockEntity(pos, state, movedState, direction, extending, isSourcePiston);
        if (!isSourcePiston && movedState.hasBlockEntity()) {
            Direction moveDir = extending ? direction : direction.getOpposite();
            BlockPos oldPos = pos.relative(moveDir.getOpposite());
            BlockEntity oldBe = level.getBlockEntity(oldPos);
            if (oldBe != null) {
                CompoundTag data = oldBe.saveWithoutMetadata(level.registryAccess());
                level.removeBlockEntity(oldPos);
                if (movingPistonBe instanceof CarriedBlockEntityHolder holder) {
                    holder.versecraft$setCarriedData(data);
                }
            }
        }
        return movingPistonBe;
    }

    @Unique
    private static boolean versecraft$isPushableBlockEntity(BlockState state) {
        if (state.is(VerseBlocksTags.Blocks.IMMOVABLE)) {
            return false;
        }
        Block block = state.getBlock();
        return block != Blocks.SPAWNER
                && block != Blocks.TRIAL_SPAWNER
                && block != Blocks.VAULT
                && block != Blocks.BEACON
                && block != Blocks.CONDUIT
                && block != Blocks.END_PORTAL
                && block != Blocks.END_GATEWAY
                && block != Blocks.CREAKING_HEART
                && block != Blocks.ENCHANTING_TABLE
                && block != Blocks.ENDER_CHEST
                && block != Blocks.JUKEBOX
                && block != Blocks.SCULK_SENSOR
                && block != Blocks.CALIBRATED_SCULK_SENSOR
                && block != Blocks.SCULK_CATALYST
                && block != Blocks.SCULK_SHRIEKER
                && block != Blocks.GRINDSTONE
                && block != Blocks.LODESTONE;
    }
}
