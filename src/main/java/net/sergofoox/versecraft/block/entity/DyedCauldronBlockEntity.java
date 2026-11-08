package net.sergofoox.versecraft.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sergofoox.versecraft.registry.RegisterBlockEntities;
import org.jetbrains.annotations.Nullable;

public class DyedCauldronBlockEntity extends BlockEntity {

    public static final int DEFAULT_WATER_COLOR = 0xFF3F76E4;
    private int color = DEFAULT_WATER_COLOR;

    public DyedCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.DYED_CAULDRON, pos, state);
    }

    public int getColor() {
        return 0xFF000000 | (this.color & 0xFFFFFF);
    }

    public void setColor(int color) {
        this.color = 0xFF000000 | (color & 0xFFFFFF);
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("color", this.color);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.color = 0xFF000000 | (input.getIntOr("color", DEFAULT_WATER_COLOR) & 0xFFFFFF);
        if (this.level != null && this.level.isClientSide()) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveCustomOnly(provider);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
