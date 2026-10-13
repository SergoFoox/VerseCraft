package net.sergofoox.versecraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.sergofoox.versecraft.VerseCraft;
import net.sergofoox.versecraft.block.entity.DyedCauldronBlockEntity;
import net.sergofoox.versecraft.block.entity.PotionCauldronBlockEntity;
import net.sergofoox.versecraft.block.entity.TuffGolemBlockEntity;

import java.util.Set;

public class RegisterBlockEntities {

    public static final BlockEntityType<DyedCauldronBlockEntity> DYED_CAULDRON = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            VerseCraft.id("dyed_cauldron"),
            new BlockEntityType<>(DyedCauldronBlockEntity::new, Set.of(RegisterBlocks.DYED_WATER_CAULDRON))
    );

    public static final BlockEntityType<PotionCauldronBlockEntity> POTION_CAULDRON = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            VerseCraft.id("potion_cauldron"),
            new BlockEntityType<>(PotionCauldronBlockEntity::new, Set.of(RegisterBlocks.POTION_CAULDRON))
    );

    public static final BlockEntityType<TuffGolemBlockEntity> TUFF_GOLEM_BLOCK_ENTITY = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            VerseCraft.id("tuff_golem_block"),
            new BlockEntityType<>(TuffGolemBlockEntity::new, Set.of(RegisterBlocks.TUFF_GOLEM_BLOCK))
    );

    public static void registerBlockEntities() {
        VerseCraft.LOGGER.info("Registering Block Entities for " + VerseCraft.MOD_ID);
    }
}
