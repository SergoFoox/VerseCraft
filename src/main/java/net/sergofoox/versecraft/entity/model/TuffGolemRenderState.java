package net.sergofoox.versecraft.entity.model;

import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.DyeColor;

public class TuffGolemRenderState extends ArmedEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sleepStartAnimationState = new AnimationState();
    public final AnimationState sleepOverAnimationState = new AnimationState();

    public DyeColor color = DyeColor.RED;

    public boolean isRightHandBlock = false;
    public boolean isLeftHandBlock = false;
    public boolean isRightHandDoor = false;
    public boolean isLeftHandDoor = false;
    public boolean isRightHandCopperGolemStatue = false;
    public boolean isLeftHandCopperGolemStatue = false;
}
