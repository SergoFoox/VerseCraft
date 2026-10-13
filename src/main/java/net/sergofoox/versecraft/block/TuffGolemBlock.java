package net.sergofoox.versecraft.block;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sergofoox.versecraft.block.entity.TuffGolemBlockEntity;
import net.sergofoox.versecraft.entity.TuffGolem;
import net.sergofoox.versecraft.registry.RegisterEntityTypes;
import org.jetbrains.annotations.Nullable;

public class TuffGolemBlock extends HorizontalDirectionalBlock implements EntityBlock {

    private static final VoxelShape BASE_SHAPE = Shapes.or(
            Block.box(3.0, 4.0, 3.0, 13.0, 16.0, 13.0),
            Block.box(3.5, 0.0, 6.0, 12.5, 4.0, 10.0),
            Block.box(3.0, 3.0, 0.0, 13.0, 9.0, 3.0),
            Block.box(7.0, 11.0, 1.0, 9.0, 15.0, 3.0),
            Block.box(0.0, 3.0, 3.0, 3.0, 14.0, 10.0),
            Block.box(13.0, 3.0, 3.0, 16.0, 14.0, 10.0)
    );
    private static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateHorizontal(BASE_SHAPE);
    private static final VoxelShape COLLISION_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);

    public TuffGolemBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.getOrDefault(state.getValue(FACING), BASE_SHAPE);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TuffGolemBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return handleInteraction(state, level, pos, player, hand);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return handleInteraction(state, level, pos, player, InteractionHand.MAIN_HAND);
    }

    private InteractionResult handleInteraction(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(pos) instanceof TuffGolemBlockEntity be) {
            ItemStack heldItem = be.getItem().copy();
            ItemStack playerHand = player.getItemInHand(hand);

            if (!heldItem.isEmpty()) {
                be.setItem(ItemStack.EMPTY);
                if (!player.getInventory().add(heldItem)) {
                    Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), heldItem);
                }

                Direction facing = state.getValue(FACING);
                level.removeBlock(pos, false);

                TuffGolem golem = new TuffGolem(RegisterEntityTypes.TUFF_GOLEM, level);
                golem.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, facing.toYRot(), 0.0F);
                golem.setYHeadRot(facing.toYRot());
                golem.setYBodyRot(facing.toYRot());
                golem.setOriginPos(pos);
                golem.setOriginFacing(facing);
                golem.startWakingUp();
                level.addFreshEntity(golem);

                level.playSound(null, pos, SoundEvents.TUFF_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.5F, 1.0F);
                return InteractionResult.SUCCESS;
            } else {
                if (!playerHand.isEmpty()) {
                    ItemStack placed = playerHand.split(1);
                    be.setItem(placed);
                    level.playSound(null, pos, SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                } else {
                    Direction facing = state.getValue(FACING);
                    level.removeBlock(pos, false);

                    TuffGolem golem = new TuffGolem(RegisterEntityTypes.TUFF_GOLEM, level);
                    golem.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, facing.toYRot(), 0.0F);
                    golem.setYHeadRot(facing.toYRot());
                    golem.setYBodyRot(facing.toYRot());
                    golem.setOriginPos(pos);
                    golem.setOriginFacing(facing);
                    golem.startWakingUp();
                    level.addFreshEntity(golem);

                    level.playSound(null, pos, SoundEvents.TUFF_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        if (level.getBlockEntity(pos) instanceof TuffGolemBlockEntity be) {
            if (!be.getItem().isEmpty()) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), be.getItem());
            }
        }
        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
    }
}
