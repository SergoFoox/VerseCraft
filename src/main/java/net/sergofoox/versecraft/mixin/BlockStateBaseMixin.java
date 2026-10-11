package net.sergofoox.versecraft.mixin;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.sergofoox.versecraft.tags.VerseBlocksTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
}
