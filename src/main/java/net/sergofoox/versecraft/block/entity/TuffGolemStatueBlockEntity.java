package net.sergofoox.versecraft.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sergofoox.versecraft.registry.RegisterBlockEntities;
import org.jetbrains.annotations.Nullable;

public class TuffGolemStatueBlockEntity extends BlockEntity {

    private ItemStack item = ItemStack.EMPTY;
    private DyeColor color = DyeColor.RED;

    public TuffGolemStatueBlockEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(RegisterBlockEntities.TUFF_GOLEM_STATUE, pos, state);
        this.color = color != null ? color : DyeColor.RED;
    }

    public TuffGolemStatueBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, DyeColor.RED);
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item != null ? item : ItemStack.EMPTY;
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void setColor(DyeColor color) {
        this.color = color != null ? color : DyeColor.RED;
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null && !this.item.isEmpty()) {
            Containers.dropItemStack(this.level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, this.item.copy());
            this.item = ItemStack.EMPTY;
        }
        super.preRemoveSideEffects(pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("item", ItemStack.OPTIONAL_CODEC, this.item);
        output.putString("color", this.color.getName());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.item = input.read("item", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
        this.color = DyeColor.byName(input.getStringOr("color", this.color != null ? this.color.getName() : "red"), DyeColor.RED);
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
