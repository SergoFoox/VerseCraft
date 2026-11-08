package net.sergofoox.versecraft.mixin;

import net.minecraft.world.entity.animal.bee.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Bee.class)
public interface BeeInvoker {
    @Invoker("setHasNectar")
    void versecraft$invokeSetHasNectar(boolean hasNectar);
}
