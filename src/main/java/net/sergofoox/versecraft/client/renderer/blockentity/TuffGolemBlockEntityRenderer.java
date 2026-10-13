package net.sergofoox.versecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AbstractBedBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec3;
import net.sergofoox.versecraft.block.entity.TuffGolemBlockEntity;

public class TuffGolemBlockEntityRenderer implements BlockEntityRenderer<TuffGolemBlockEntity, TuffGolemBlockRenderState> {

    // === Параметры для 3D блоков ===
    public static final float BLOCK_X_OFFSET = 0.0F;
    public static final float BLOCK_Y_OFFSET = 0.725F;
    public static final float BLOCK_Z_OFFSET = -0.66F;
    public static final float BLOCK_SCALE = 0.65F;

    // === Параметры для флагов / баннеров ===
    public static final float BANNER_X_OFFSET = 0.0F;
    public static final float BANNER_Y_OFFSET = 0.5825F;
    public static final float BANNER_Z_OFFSET = -0.65F;
    public static final float BANNER_SCALE = 0.5F;
    public static final float BANNER_X_ROTATION = 90.0F;

    // === Параметры для кроватей ===
    public static final float BED_X_OFFSET = 0.0F;
    public static final float BED_Y_OFFSET = 0.62F;
    public static final float BED_Z_OFFSET = -0.5625F;
    public static final float BED_SCALE = 0.45F;

    // === Параметры для шипов (капельник, серные шипы) ===
    public static final float SPIKE_X_OFFSET = 0.0F;
    public static final float SPIKE_Y_OFFSET = 0.5825F;
    public static final float SPIKE_Z_OFFSET = -0.65F;
    public static final float SPIKE_SCALE = 0.5F;
    public static final float SPIKE_X_ROTATION = 90.0F;

    // === Параметры для плоских предметов (2D items, инструменты, еда, ведра и др.) ===
    public static final float ITEM_X_OFFSET = 0.0F;
    public static final float ITEM_Y_OFFSET = 0.5825F;
    public static final float ITEM_Z_OFFSET = -0.5625F;
    public static final float ITEM_SCALE = 0.5F;
    public static final float ITEM_X_ROTATION = 90.0F;

    private final ItemModelResolver itemModelResolver;

    public TuffGolemBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public TuffGolemBlockRenderState createRenderState() {
        return new TuffGolemBlockRenderState();
    }

    @Override
    public void extractRenderState(TuffGolemBlockEntity blockEntity, TuffGolemBlockRenderState renderState, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderState.extractBase(blockEntity, renderState, crumblingOverlay);
        if (blockEntity.getBlockState().hasProperty(HorizontalDirectionalBlock.FACING)) {
            renderState.facing = blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        }
        ItemStack stack = blockEntity.getItem();
        if (!stack.isEmpty()) {
            this.itemModelResolver.updateForTopItem(renderState.item, stack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
            renderState.isBanner = stack.getItem() instanceof BannerItem;
            renderState.isBed = (stack.getItem() instanceof BlockItem blockItem) && (blockItem.getBlock() instanceof AbstractBedBlock);
            renderState.isSpike = stack.is(Items.POINTED_DRIPSTONE) || stack.is(Items.SULFUR_SPIKE);
            // 3D-блоками считаются:
            // 1. Модели с объемным затенением (usesBlockLight)
            // 2. Либо BlockItem с достаточной 3D-толщиной модели (zSize > 0.15), как например калиброванный скалк-сенсор с gui_light: front
            // Исключения: ведра (DispensibleContainerItem, в т.ч. ведро со снегом), кровати, флаги и шипы (обрабатываются отдельно)
            boolean is3dGeometry = renderState.item.usesBlockLight() || (stack.getItem() instanceof BlockItem && renderState.item.getModelBoundingBox().getZsize() > 0.15);
            renderState.isBlock = is3dGeometry && !(stack.getItem() instanceof DispensibleContainerItem) && !renderState.isBed && !renderState.isBanner && !renderState.isSpike;
        } else {
            renderState.item.clear();
            renderState.isBlock = false;
            renderState.isBed = false;
            renderState.isBanner = false;
            renderState.isSpike = false;
        }
    }

    @Override
    public void submit(TuffGolemBlockRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (!renderState.item.isEmpty()) {
            poseStack.pushPose();
            // Центр блока
            poseStack.translate(0.5F, 0.0F, 0.5F);
            // Поворот по направлению FACING
            poseStack.rotateDegrees(Axis.YP, -renderState.facing.toYRot() + 180.0F);

            if (renderState.isBanner) {
                // Флаг / Баннер: лежит плашмя на коврике, сдвинут вперед для зазора от носа
                poseStack.translate(BANNER_X_OFFSET, BANNER_Y_OFFSET, BANNER_Z_OFFSET);
                poseStack.rotateDegrees(Axis.XP, BANNER_X_ROTATION);
                poseStack.scale(BANNER_SCALE, BANNER_SCALE, BANNER_SCALE);
            } else if (renderState.isSpike) {
                // Шипы (капельник, серные шипы): лежат плашмя на коврике, сдвинуты вперед для идеального центрирования
                poseStack.translate(SPIKE_X_OFFSET, SPIKE_Y_OFFSET, SPIKE_Z_OFFSET);
                poseStack.rotateDegrees(Axis.XP, SPIKE_X_ROTATION);
                poseStack.scale(SPIKE_SCALE, SPIKE_SCALE, SPIKE_SCALE);
            } else if (renderState.isBed) {
                // Кровать: лежит горизонтально ножками вниз на коврике
                poseStack.translate(BED_X_OFFSET, BED_Y_OFFSET, BED_Z_OFFSET);
                poseStack.rotateDegrees(Axis.XP, 90.0F);
                poseStack.scale(BED_SCALE, BED_SCALE, BED_SCALE);
            } else if (renderState.isBlock) {
                // 3D блок: стоит вертикально на коврике
                poseStack.translate(BLOCK_X_OFFSET, BLOCK_Y_OFFSET, BLOCK_Z_OFFSET);
                poseStack.scale(BLOCK_SCALE, BLOCK_SCALE, BLOCK_SCALE);
            } else {
                // Плоский предмет: лежит плашмя на коврике
                poseStack.translate(ITEM_X_OFFSET, ITEM_Y_OFFSET, ITEM_Z_OFFSET);
                poseStack.rotateDegrees(Axis.XP, ITEM_X_ROTATION);
                poseStack.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
            }

            renderState.item.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
