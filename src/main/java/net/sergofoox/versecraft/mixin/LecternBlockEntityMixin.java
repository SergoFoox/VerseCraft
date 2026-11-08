package net.sergofoox.versecraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LecternBlockEntity.class)
public abstract class LecternBlockEntityMixin {
    @Inject(method = "preRemoveSideEffects", at = @At("HEAD"), cancellable = true)
    private void versecraft$guardPreRemove(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!state.hasProperty(LecternBlock.HAS_BOOK) || !state.hasProperty(LecternBlock.FACING)) {
            ci.cancel();
        }
    }
}
