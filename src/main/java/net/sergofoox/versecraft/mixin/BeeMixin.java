package net.sergofoox.versecraft.mixin;

import net.minecraft.world.entity.animal.bee.Bee;
import net.sergofoox.versecraft.entity.ai.BeePollinateMoobloomGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bee.class)
public abstract class BeeMixin {
    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void versecraft$registerBeeGoals(CallbackInfo ci) {
        Bee bee = (Bee) (Object) this;
        bee.getGoalSelector().addGoal(4, new BeePollinateMoobloomGoal(bee));
    }
}
