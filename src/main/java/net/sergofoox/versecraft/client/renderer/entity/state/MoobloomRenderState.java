package net.sergofoox.versecraft.client.renderer.entity.state;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class MoobloomRenderState extends LivingEntityRenderState {
    public final BlockModelRenderState flowerModel = new BlockModelRenderState();
    public byte flowerMask = 7;
    public boolean isGolden;
    public float headEatPositionScale;
    public float headEatAngleScale;
}
