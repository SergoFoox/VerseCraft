package net.sergofoox.versecraft.entity.ai;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.sergofoox.versecraft.entity.TuffGolem;
import net.sergofoox.versecraft.registry.RegisterEntityTypes;

public class TuffGolemSpawn {

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClientSide()) {
                return InteractionResult.PASS;
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = hitResult.getBlockPos().above();
                if (player.getItemInHand(hand).is(Blocks.CARVED_PUMPKIN.asItem())) {
                    if (checkStructure((ServerLevel) world, blockPos.below())) {
                        spawnTuffGolem((ServerLevel) world, blockPos, player.getYRot() + 180.0F, player.getDirection().getOpposite());
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
                && world.getBlockState(pos).is(Blocks.WOOL.red())
                && world.getBlockState(pos.above()).isAir();
    }

    private static void spawnTuffGolem(ServerLevel world, BlockPos topBlockPos, float yaw, Direction facing) {
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
        world.addFreshEntity(golem);

        world.levelEvent(2001, woolBlockPos, Block.getId(Blocks.CARVED_PUMPKIN.defaultBlockState()));
        world.levelEvent(2001, woolBlockPos, Block.getId(Blocks.WOOL.red().defaultBlockState()));
        world.levelEvent(2001, tuffBlockPos, Block.getId(Blocks.TUFF.defaultBlockState()));
    }
}
