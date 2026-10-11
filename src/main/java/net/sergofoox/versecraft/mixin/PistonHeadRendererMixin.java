package net.sergofoox.versecraft.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.PistonHeadRenderer;
import net.minecraft.client.renderer.blockentity.state.BellRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.blockentity.state.LecternRenderState;
import net.minecraft.client.renderer.blockentity.state.PistonHeadRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.CopperChestBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import net.sergofoox.versecraft.util.CarriedRenderStateHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonHeadRenderer.class)
public abstract class PistonHeadRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/level/block/piston/PistonMovingBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/PistonHeadRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
            at = @At("TAIL")
    )
    private void versecraft$extractCarriedRenderState(
            PistonMovingBlockEntity entity,
            PistonHeadRenderState state,
            float partialTick,
            Vec3 cameraPos,
            ModelFeatureRenderer.CrumblingOverlay overlay,
            CallbackInfo ci
    ) {
        if (state instanceof CarriedRenderStateHolder holder) {
            BlockState movedState = entity.getMovedState();
            if (movedState != null && movedState.getBlock() instanceof AbstractChestBlock) {
                holder.versecraft$setCarriedRenderState(versecraft$createChestRenderState(movedState, entity, state, overlay));
            } else if (movedState != null && movedState.is(Blocks.LECTERN)) {
                holder.versecraft$setCarriedRenderState(versecraft$createLecternRenderState(movedState, entity, state, overlay));
            } else if (movedState != null && movedState.is(Blocks.BELL)) {
                holder.versecraft$setCarriedRenderState(versecraft$createBellRenderState(entity, state, overlay));
            } else {
                holder.versecraft$setCarriedRenderState(null);
            }
        }
    }

    @Inject(
            method = "submit(Lnet/minecraft/client/renderer/blockentity/state/PistonHeadRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitMovingBlock(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/block/MovingBlockRenderState;I)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void versecraft$renderCarriedBlockEntity(
            PistonHeadRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState cameraRenderState,
            CallbackInfo ci
    ) {
        if (state instanceof CarriedRenderStateHolder holder) {
            BlockEntityRenderState carriedState = holder.versecraft$getCarriedRenderState();
            if (carriedState != null) {
                Minecraft.getInstance().getBlockEntityRenderDispatcher().submit(
                        carriedState, poseStack, submitNodeCollector, cameraRenderState
                );
            }
        }
    }

    @Unique
    private static ChestRenderState versecraft$createChestRenderState(
            BlockState movedState,
            PistonMovingBlockEntity entity,
            PistonHeadRenderState pistonState,
            ModelFeatureRenderer.CrumblingOverlay overlay
    ) {
        ChestRenderState chestState = new ChestRenderState();
        chestState.blockPos = entity.getBlockPos();
        chestState.lightCoords = pistonState.lightCoords;
        chestState.breakProgress = overlay;
        chestState.open = 0.0f;

        ChestType type = movedState.hasProperty(ChestBlock.TYPE) ? movedState.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        Direction facing = movedState.hasProperty(ChestBlock.FACING) ? movedState.getValue(ChestBlock.FACING) : Direction.NORTH;
        chestState.type = type;
        chestState.facing = facing;

        if (movedState.getBlock() instanceof CopperChestBlock copperChest) {
            chestState.blockEntityType = BlockEntityTypes.CHEST;
            chestState.material = switch (copperChest.getState()) {
                case UNAFFECTED -> ChestRenderState.ChestMaterialType.COPPER_UNAFFECTED;
                case EXPOSED -> ChestRenderState.ChestMaterialType.COPPER_EXPOSED;
                case WEATHERED -> ChestRenderState.ChestMaterialType.COPPER_WEATHERED;
                case OXIDIZED -> ChestRenderState.ChestMaterialType.COPPER_OXIDIZED;
            };
        } else if (movedState.is(Blocks.TRAPPED_CHEST)) {
            chestState.blockEntityType = BlockEntityTypes.TRAPPED_CHEST;
            chestState.material = ChestRenderState.ChestMaterialType.TRAPPED;
        } else if (ChestRenderer.xmasTextures()) {
            chestState.blockEntityType = BlockEntityTypes.CHEST;
            chestState.material = ChestRenderState.ChestMaterialType.CHRISTMAS;
        } else {
            chestState.blockEntityType = BlockEntityTypes.CHEST;
            chestState.material = ChestRenderState.ChestMaterialType.REGULAR;
        }

        return chestState;
    }

    @Unique
    private static LecternRenderState versecraft$createLecternRenderState(
            BlockState movedState,
            PistonMovingBlockEntity entity,
            PistonHeadRenderState pistonState,
            ModelFeatureRenderer.CrumblingOverlay overlay
    ) {
        LecternRenderState lecternState = new LecternRenderState();
        lecternState.blockPos = entity.getBlockPos();
        lecternState.lightCoords = pistonState.lightCoords;
        lecternState.breakProgress = overlay;
        lecternState.blockEntityType = BlockEntityTypes.LECTERN;
        lecternState.hasBook = movedState.hasProperty(LecternBlock.HAS_BOOK) && movedState.getValue(LecternBlock.HAS_BOOK);
        Direction facing = movedState.hasProperty(LecternBlock.FACING) ? movedState.getValue(LecternBlock.FACING) : Direction.NORTH;
        lecternState.yRot = facing.getClockWise().toYRot();
        return lecternState;
    }

    @Unique
    private static BellRenderState versecraft$createBellRenderState(
            PistonMovingBlockEntity entity,
            PistonHeadRenderState pistonState,
            ModelFeatureRenderer.CrumblingOverlay overlay
    ) {
        BellRenderState bellState = new BellRenderState();
        bellState.blockPos = entity.getBlockPos();
        bellState.lightCoords = pistonState.lightCoords;
        bellState.breakProgress = overlay;
        bellState.blockEntityType = BlockEntityTypes.BELL;
        bellState.ticks = 0.0f;
        bellState.shakeDirection = null;
        return bellState;
    }
}
