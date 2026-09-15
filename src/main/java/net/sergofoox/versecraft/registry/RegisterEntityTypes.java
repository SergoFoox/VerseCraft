package net.sergofoox.versecraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.Item;
import net.sergofoox.versecraft.VerseCraft;

import java.util.function.Supplier;

public class RegisterEntityTypes {

    public static final EntityType<Boat> AZALEA_BOAT = registerBoat(
            "azalea_boat",
            () -> RegisterItems.AZALEA_BOAT
    );

    public static final EntityType<ChestBoat> AZALEA_CHEST_BOAT = registerChestBoat(
            "azalea_chest_boat",
            () -> RegisterItems.AZALEA_CHEST_BOAT
    );


    private static EntityType<Boat> registerBoat(String name, Supplier<Item> dropItem) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, VerseCraft.id(name));
        return Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                key,
                EntityType.Builder.<Boat>of((t, l) -> new Boat(t, l, dropItem), MobCategory.MISC)
                        .sized(1.375F, 0.5625F)
                        .eyeHeight(0.5625F)
                        .clientTrackingRange(10)
                        .build(key)
        );
    }

    private static EntityType<ChestBoat> registerChestBoat(String name, Supplier<Item> dropItem) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, VerseCraft.id(name));
        return Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                key,
                EntityType.Builder.<ChestBoat>of((t, l) -> new ChestBoat(t, l, dropItem), MobCategory.MISC)
                        .sized(1.375F, 0.5625F)
                        .eyeHeight(0.5625F)
                        .clientTrackingRange(10)
                        .build(key)
        );
    }

    public static void registerEntitiesTypes() {
        VerseCraft.LOGGER.info("Registering Entities for " + VerseCraft.MOD_ID);
    }
}