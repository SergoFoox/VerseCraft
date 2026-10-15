package net.sergofoox.versecraft.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;
import net.sergofoox.versecraft.VerseCraft;
import net.sergofoox.versecraft.entity.animations.TuffGolemAnimations;

public class TuffGolemModel extends EntityModel<TuffGolemRenderState> implements ArmedModel<TuffGolemRenderState> {
    public static final ModelLayerLocation TUFF_GOLEM = new ModelLayerLocation(VerseCraft.id("tuff_golem"), "main");

    private final ModelPart bone;
    private final ModelPart leg_r;
    private final ModelPart leg_l;
    private final ModelPart body;
    private final ModelPart body_overlay;
    private final ModelPart nose;
    private final ModelPart eye;
    private final ModelPart eye2;
    private final ModelPart arm_l;
    private final ModelPart arm_r;

    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation sleepStartAnimation;
    private final KeyframeAnimation sleepOverAnimation;

    public TuffGolemModel(ModelPart root) {
        super(root);
        this.bone = root.getChild("bone");
        this.leg_r = this.bone.getChild("leg_r");
        this.leg_l = this.bone.getChild("leg_l");
        this.body = this.bone.getChild("body");
        this.body_overlay = this.body.getChild("body_overlay");
        this.nose = this.body.getChild("nose");
        this.eye = this.body.getChild("eye");
        this.eye2 = this.body.getChild("eye2");
        this.arm_l = this.body.getChild("arm_l");
        this.arm_r = this.body.getChild("arm_r");

        this.walkingAnimation = TuffGolemAnimations.WALK.bake(root);
        this.idleAnimation = TuffGolemAnimations.IDLE.bake(root);
        this.sleepStartAnimation = TuffGolemAnimations.SLEEP_START.bake(root);
        this.sleepOverAnimation = TuffGolemAnimations.SLEEP_OVER.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        bone.addOrReplaceChild("leg_r", CubeListBuilder.create().texOffs(30, 0).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -5.0F, 0.0F));

        bone.addOrReplaceChild("leg_l", CubeListBuilder.create().texOffs(43, 5).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -5.0F, 0.0F));

        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -16.0F, -6.0F, 10.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 1.0F));

        body.addOrReplaceChild("body_overlay", CubeListBuilder.create().texOffs(0, 26).addBox(-5.0F, 1.0F, -5.0F, 10.0F, 18.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -17.0F, -1.0F));

        body.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, -6.0F));

        body.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(44, 0).addBox(-5.0F, -4.0F, 0.1F, 10.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -6.0F));

        body.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(42, 44).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -6.0F));

        body.addOrReplaceChild("arm_l", CubeListBuilder.create().texOffs(41, 15).addBox(0.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -8.0F, -1.0F));

        body.addOrReplaceChild("arm_r", CubeListBuilder.create().texOffs(47, 30).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -8.0F, -1.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(TuffGolemRenderState state) {
        super.setupAnim(state);
        if (state.sleepStartAnimationState.isStarted()) {
            this.sleepStartAnimation.apply(state.sleepStartAnimationState, state.ageInTicks);
        } else if (state.sleepOverAnimationState.isStarted()) {
            this.sleepOverAnimation.apply(state.sleepOverAnimationState, state.ageInTicks);
        } else {
            this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2.5F, 2.5F);
            this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        }

        boolean hasRightItem = !state.rightHandItemState.isEmpty() || !state.rightHandItemStack.isEmpty();
        boolean hasLeftItem = !state.leftHandItemState.isEmpty() || !state.leftHandItemStack.isEmpty();

        if (hasRightItem) {
            if (state.sleepStartAnimationState.isStarted() || state.sleepOverAnimationState.isStarted()) {
                this.arm_r.xRot = -0.35F;
            } else {
                this.arm_r.xRot = this.arm_r.xRot * 0.5F - 0.35F;
            }
            this.arm_r.yRot = 0.0F;
            this.arm_r.zRot = 0.0F;
        }
        if (hasLeftItem) {
            if (state.sleepStartAnimationState.isStarted() || state.sleepOverAnimationState.isStarted()) {
                this.arm_l.xRot = -0.35F;
            } else {
                this.arm_l.xRot = this.arm_l.xRot * 0.5F - 0.35F;
            }
            this.arm_l.yRot = 0.0F;
            this.arm_l.zRot = 0.0F;
        }
    }

    @Override
    public void translateToHand(TuffGolemRenderState state, HumanoidArm arm, PoseStack poseStack) {
        this.bone.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        ModelPart armPart = arm == HumanoidArm.RIGHT ? this.arm_r : this.arm_l;
        armPart.translateAndRotate(poseStack);

        boolean isCopperGolem = arm == HumanoidArm.RIGHT ? state.isRightHandCopperGolemStatue : state.isLeftHandCopperGolemStatue;
        boolean isBlock = arm == HumanoidArm.RIGHT ? state.isRightHandBlock : state.isLeftHandBlock;
        boolean isDoor = arm == HumanoidArm.RIGHT ? state.isRightHandDoor : state.isLeftHandDoor;
        float sign = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;

        if (isCopperGolem) {
            poseStack.translate(-sign * 1.0F / 16.0F, -6.0F / 16.0F, 0.75F / 16.0F);
        } else if (isBlock) {
            poseStack.translate(-sign * 1.0F / 16.0F, -3.5F / 16.0F, 0.5F / 16.0F);
        } else if (isDoor) {
            poseStack.translate(-sign * 1.0F / 16.0F, -1.0F / 16.0F, 1.0F / 16.0F);
        } else {
            poseStack.translate(-sign * 1.0F / 16.0F, -2.5F / 16.0F, 1.0F / 16.0F);
        }
    }
}
