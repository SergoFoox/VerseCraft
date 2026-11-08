package net.sergofoox.versecraft.mixin;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.blockentity.state.PistonHeadRenderState;
import net.sergofoox.versecraft.util.CarriedRenderStateHolder;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PistonHeadRenderState.class)
public abstract class PistonHeadRenderStateMixin implements CarriedRenderStateHolder {

    @Unique
    @Nullable
    private BlockEntityRenderState versecraft$carriedRenderState;

    @Override
    public @Nullable BlockEntityRenderState versecraft$getCarriedRenderState() {
        return this.versecraft$carriedRenderState;
    }

    @Override
    public void versecraft$setCarriedRenderState(@Nullable BlockEntityRenderState state) {
        this.versecraft$carriedRenderState = state;
    }
}
