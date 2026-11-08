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
import net.sergofoox.versecraft.block.PotionCauldronBlock;
import net.sergofoox.versecraft.registry.RegisterBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PotionCauldronBlockEntity extends BlockEntity {

    private PotionContents potionContents = PotionContents.EMPTY;
    private PotionCauldronBlock.PotionBottleType bottleType = PotionCauldronBlock.PotionBottleType.NORMAL;

    public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.POTION_CAULDRON, pos, state);
    }

    public PotionContents getPotionContents() {
        return this.potionContents;
    }

    public PotionCauldronBlock.PotionBottleType getBottleType() {
        return this.bottleType;
    }

    public void setPotion(PotionContents potionContents, PotionCauldronBlock.PotionBottleType bottleType) {
        this.potionContents = potionContents;
        this.bottleType = bottleType != null ? bottleType : PotionCauldronBlock.PotionBottleType.NORMAL;
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public void setPotionContents(PotionContents potionContents) {
        setPotion(potionContents, this.bottleType);
    }

    public void setBottleType(PotionCauldronBlock.PotionBottleType bottleType) {
        setPotion(this.potionContents, bottleType);
    }

    public int getColor() {
        return 0xFF000000 | (this.potionContents.getColor() & 0xFFFFFF);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("potion", PotionContents.CODEC, this.potionContents);
        output.putInt("bottle_type", this.bottleType.getId());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.read("potion", PotionContents.CODEC).ifPresent(p -> this.potionContents = p);
        this.bottleType = PotionCauldronBlock.PotionBottleType.byId(input.getIntOr("bottle_type", 0));
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
