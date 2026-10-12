package net.sergofoox.versecraft.mixin;

import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pillager.class)
public abstract class PillagerMixin {

    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private void versecraft$onGetArmPose(CallbackInfoReturnable<AbstractIllager.IllagerArmPose> cir) {
        Pillager self = (Pillager) (Object) this;
        if (self.getMainHandItem().is(Items.BANNER.white())) {
            cir.setReturnValue(AbstractIllager.IllagerArmPose.NEUTRAL);
        }
    }
}
