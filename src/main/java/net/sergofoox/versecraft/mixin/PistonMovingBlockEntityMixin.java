package net.sergofoox.versecraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sergofoox.versecraft.util.CarriedBlockEntityHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonMovingBlockEntity.class)
public abstract class PistonMovingBlockEntityMixin implements CarriedBlockEntityHolder {

    @Shadow
    public abstract BlockState getMovedState();

    @Unique
    @Nullable
    private CompoundTag versecraft$carriedData;

    @Override
    @Nullable
    public CompoundTag versecraft$getCarriedData() {
        return this.versecraft$carriedData;
    }

    @Override
    public void versecraft$setCarriedData(@Nullable CompoundTag data) {
        this.versecraft$carriedData = data;
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void versecraft$saveCarriedData(ValueOutput output, CallbackInfo ci) {
        if (this.versecraft$carriedData != null) {
            output.store("VerseCraftCarriedData", CompoundTag.CODEC, this.versecraft$carriedData);
        }
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void versecraft$loadCarriedData(ValueInput input, CallbackInfo ci) {
        this.versecraft$carriedData = input.read("VerseCraftCarriedData", CompoundTag.CODEC).orElse(null);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;neighborChanged(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/redstone/Orientation;)V"
            )
    )
    private static void versecraft$restoreInTick(Level level, BlockPos pos, BlockState state, PistonMovingBlockEntity entity, CallbackInfo ci) {
        if (entity instanceof CarriedBlockEntityHolder holder) {
            versecraft$applyCarriedData(level, pos, entity.getMovedState(), holder.versecraft$getCarriedData());
        }
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;updateOrDestroy(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;I)V"
            )
    )
    private static void versecraft$restoreBeforeDrop(Level level, BlockPos pos, BlockState state, PistonMovingBlockEntity entity, CallbackInfo ci) {
        if (entity instanceof CarriedBlockEntityHolder holder) {
            versecraft$applyCarriedData(level, pos, entity.getMovedState(), holder.versecraft$getCarriedData());
        }
    }

    @Inject(
            method = "finalTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;neighborChanged(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/redstone/Orientation;)V"
            )
    )
    private void versecraft$restoreInFinalTick(CallbackInfo ci) {
        BlockEntity self = (BlockEntity) (Object) this;
        Level level = self.getLevel();
        if (level != null && (Object) this instanceof CarriedBlockEntityHolder holder) {
            versecraft$applyCarriedData(level, self.getBlockPos(), this.getMovedState(), holder.versecraft$getCarriedData());
        }
    }

    @Unique
    private static void versecraft$applyCarriedData(Level level, BlockPos pos, BlockState state, @Nullable CompoundTag data) {
        if (data == null || level.isClientSide()) {
            return;
        }
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null && state.getBlock() instanceof EntityBlock entityBlock) {
            be = entityBlock.newBlockEntity(pos, state);
            if (be != null) {
                level.setBlockEntity(be);
            }
        }
        if (be != null) {
            ValueInput input = TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), data);
            be.loadWithComponents(input);
            be.setChanged();
        }
    }
}
