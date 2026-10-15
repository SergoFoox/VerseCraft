package net.sergofoox.versecraft.entity.ai;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.sergofoox.versecraft.entity.TuffGolem;
import net.sergofoox.versecraft.registry.RegisterEntityTypes;

import java.util.IdentityHashMap;
import java.util.Map;

public class TuffGolemSpawn {

    private static final Map<Block, DyeColor> WOOL_COLORS = new IdentityHashMap<>();

    static {
        for (DyeColor color : DyeColor.values()) {
            WOOL_COLORS.put(Blocks.WOOL.pick(color), color);
        }
    }

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClientSide()) {
                return InteractionResult.PASS;
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = hitResult.getBlockPos().above();
                if (player.getItemInHand(hand).is(Blocks.CARVED_PUMPKIN.asItem())) {
                    BlockPos woolPos = blockPos.below();
                    if (checkStructure((ServerLevel) world, woolPos)) {
                        BlockState woolState = world.getBlockState(woolPos);
                        DyeColor color = WOOL_COLORS.get(woolState.getBlock());
                        spawnTuffGolem((ServerLevel) world, blockPos, player.getYRot() + 180.0F, player.getDirection().getOpposite(), color, woolState);
                        if (!player.isCreative()) {
                            player.getItemInHand(hand).shrink(1);
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }

            return InteractionResult.PASS;
        });
    }

    private static boolean checkStructure(ServerLevel world, BlockPos pos) {
        return world.getBlockState(pos.below()).is(Blocks.TUFF)
                && WOOL_COLORS.containsKey(world.getBlockState(pos).getBlock())
                && world.getBlockState(pos.above()).isAir();
    }

    private static void spawnTuffGolem(ServerLevel world, BlockPos topBlockPos, float yaw, Direction facing, DyeColor color, BlockState woolState) {
        BlockPos woolBlockPos = topBlockPos.below();
        BlockPos tuffBlockPos = woolBlockPos.below();

        world.setBlockAndUpdate(woolBlockPos, Blocks.AIR.defaultBlockState());
        world.setBlockAndUpdate(tuffBlockPos, Blocks.AIR.defaultBlockState());

        TuffGolem golem = new TuffGolem(RegisterEntityTypes.TUFF_GOLEM, world);
        golem.snapTo(tuffBlockPos.getX() + 0.5, tuffBlockPos.getY(), tuffBlockPos.getZ() + 0.5, yaw, 0.0F);
        golem.setYHeadRot(yaw);
        golem.setYBodyRot(yaw);
        golem.setOriginPos(tuffBlockPos);
        golem.setOriginFacing(facing);
        if (color != null) {
            golem.setColor(color);
        }
        world.addFreshEntity(golem);

        world.levelEvent(2001, woolBlockPos, Block.getId(Blocks.CARVED_PUMPKIN.defaultBlockState()));
        world.levelEvent(2001, woolBlockPos, Block.getId(woolState));
        world.levelEvent(2001, tuffBlockPos, Block.getId(Blocks.TUFF.defaultBlockState()));
    }
}
