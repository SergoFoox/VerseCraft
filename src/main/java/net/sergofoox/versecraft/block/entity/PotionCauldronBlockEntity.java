package net.sergofoox.versecraft.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sergofoox.versecraft.registry.RegisterBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PotionCauldronBlockEntity extends BlockEntity {

    private PotionContents potionContents = PotionContents.EMPTY;

    public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.POTION_CAULDRON, pos, state);
    }

    public PotionContents getPotionContents() {
        return this.potionContents;
    }

    public void setPotionContents(PotionContents potionContents) {
        this.potionContents = potionContents;
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public int getColor() {
        return 0xFF000000 | (this.potionContents.getColor() & 0xFFFFFF);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("potion", PotionContents.CODEC, this.potionContents);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.read("potion", PotionContents.CODEC).ifPresent(p -> this.potionContents = p);
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
