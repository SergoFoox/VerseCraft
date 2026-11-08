package net.sergofoox.versecraft.client.renderer.blockentity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class TuffGolemStatueBlockRenderState extends BlockEntityRenderState {
    public Direction facing = Direction.NORTH;
    public boolean isBlock = false;
    public boolean isCopperGolemStatue = false;
    public boolean isBed = false;
    public boolean isBanner = false;
    public boolean isSpike = false;
    public boolean isDoor = false;
    public final ItemStackRenderState item = new ItemStackRenderState();
}
