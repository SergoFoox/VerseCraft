package net.sergofoox.versecraft.entity.model;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractBedBlock;
import net.minecraft.world.level.block.CopperGolemStatueBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.sergofoox.versecraft.VerseCraft;
import net.sergofoox.versecraft.entity.TuffGolem;

import java.util.EnumMap;
import java.util.Map;

public class TuffGolemRenderer extends MobRenderer<TuffGolem, TuffGolemRenderState, TuffGolemModel> {

    private static final Map<DyeColor, Identifier> TEXTURES = new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) {
            TEXTURES.put(color, VerseCraft.id("textures/entity/tuff_golem/tuff_golem_" + color.getName() + ".png"));
        }
    }

    public TuffGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new TuffGolemModel(context.bakeLayer(TuffGolemModel.TUFF_GOLEM)), 0.4F);
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public Identifier getTextureLocation(TuffGolemRenderState state) {
        return TEXTURES.getOrDefault(state.color, TEXTURES.get(DyeColor.RED));
    }

    @Override
    public TuffGolemRenderState createRenderState() {
        return new TuffGolemRenderState();
    }

    @Override
    public void extractRenderState(TuffGolem entity, TuffGolemRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        ArmedEntityRenderState.extractArmedEntityRenderState(entity, state, this.itemModelResolver, partialTick);

        state.color = entity.getColor();

        if (entity.isBusyWithAnim()) {
            Direction facing = entity.getOriginFacing();
            if (facing != null) {
                float targetYaw = facing.toYRot();
                state.bodyRot = targetYaw;
                state.yRot = targetYaw;
            }
        }

        ItemStack rightStack = state.rightHandItemStack;
        boolean rightIsDoor = !rightStack.isEmpty()
                && (rightStack.getItem() instanceof BlockItem blockItem)
                && (blockItem.getBlock() instanceof DoorBlock);
        boolean rightIsCopperGolem = !rightStack.isEmpty()
                && (rightStack.getItem() instanceof BlockItem blockItem)
                && (blockItem.getBlock() instanceof CopperGolemStatueBlock);

        state.isRightHandDoor = rightIsDoor;
        state.isRightHandCopperGolemStatue = rightIsCopperGolem;
        state.isRightHandBlock = !rightStack.isEmpty()
                && (rightStack.getItem() instanceof BlockItem blockItem)
                && !(blockItem.getBlock() instanceof AbstractBedBlock)
                && !(blockItem.getBlock() instanceof DoorBlock)
                && !rightIsCopperGolem
                && !(rightStack.getItem() instanceof BannerItem);

        ItemStack leftStack = state.leftHandItemStack;
        boolean leftIsDoor = !leftStack.isEmpty()
                && (leftStack.getItem() instanceof BlockItem blockItem)
                && (blockItem.getBlock() instanceof DoorBlock);
        boolean leftIsCopperGolem = !leftStack.isEmpty()
                && (leftStack.getItem() instanceof BlockItem blockItem)
                && (blockItem.getBlock() instanceof CopperGolemStatueBlock);

        state.isLeftHandDoor = leftIsDoor;
        state.isLeftHandCopperGolemStatue = leftIsCopperGolem;
        state.isLeftHandBlock = !leftStack.isEmpty()
                && (leftStack.getItem() instanceof BlockItem blockItem)
                && !(blockItem.getBlock() instanceof AbstractBedBlock)
                && !(blockItem.getBlock() instanceof DoorBlock)
                && !leftIsCopperGolem
                && !(leftStack.getItem() instanceof BannerItem);

        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.walkingAnimationState.copyFrom(entity.walkingAnimationState);
        state.sleepStartAnimationState.copyFrom(entity.sleepStartAnimationState);
        state.sleepOverAnimationState.copyFrom(entity.sleepOverAnimationState);
    }
}
