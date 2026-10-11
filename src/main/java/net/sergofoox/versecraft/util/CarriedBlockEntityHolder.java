package net.sergofoox.versecraft.util;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public interface CarriedBlockEntityHolder {
    @Nullable
    CompoundTag versecraft$getCarriedData();

    void versecraft$setCarriedData(@Nullable CompoundTag data);
}
