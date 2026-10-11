package net.sergofoox.versecraft.util;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import org.jspecify.annotations.Nullable;

public interface CarriedRenderStateHolder {
    @Nullable
    BlockEntityRenderState versecraft$getCarriedRenderState();

    void versecraft$setCarriedRenderState(@Nullable BlockEntityRenderState state);
}
