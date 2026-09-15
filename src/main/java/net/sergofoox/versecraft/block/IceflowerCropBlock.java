package net.sergofoox.versecraft.block;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.sergofoox.versecraft.registry.RegisterBlocks;
import net.sergofoox.versecraft.registry.RegisterItems;

public class IceflowerCropBlock extends BlueRoseCropBlock {

    @Override
    public BlockState getStateForAge(int age) {
        return age == MAX_AGE ? RegisterBlocks.ICEFLOWER.defaultBlockState() : super.getStateForAge(age);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return RegisterItems.ICEFLOWER_SEEDS;
    }

    public IceflowerCropBlock(Properties properties) {
        super(properties);
    }
}
