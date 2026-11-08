package net.sergofoox.versecraft.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.AbstractCow;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.sergofoox.versecraft.VerseCraft;
import net.sergofoox.versecraft.entity.Moobloom;
import net.sergofoox.versecraft.entity.TuffGolem;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class RegisterEntityTypes {

    public static final EntityType<Boat> AZALEA_BOAT = registerBoat(
            "azalea_boat",
            () -> RegisterItems.AZALEA_BOAT
    );

    public static final EntityType<ChestBoat> AZALEA_CHEST_BOAT = registerChestBoat(
            "azalea_chest_boat",
            () -> RegisterItems.AZALEA_CHEST_BOAT
    );

    public static final EntityType<Moobloom> MOOBLOOM = registerMob(
            "moobloom",
            Moobloom::new,
            MobCategory.CREATURE,
            builder -> builder
                    .defaultAttributes(AbstractCow::createAttributes)
                    .spawnPlacement(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules),
            0.9F, 1.4F, 1.3F, 1.36F, 10
    );

    public static final EntityType<TuffGolem> TUFF_GOLEM = registerMob(
            "tuff_golem",
            TuffGolem::new,
            MobCategory.CREATURE,
            builder -> builder
                    .defaultAttributes(TuffGolem::createAttributes)
                    .spawnPlacement(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules),
            0.6F, 0.9F, 0.7F, 0.8F, 10
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

    private static <T extends Mob> EntityType<T> registerMob(
            String name,
            EntityType.EntityFactory<T> factory,
            MobCategory category,
            UnaryOperator<FabricEntityType.Builder.Mob<T>> mobConfigurator,
            float width, float height, float eyeHeight, float passengerAttachment, int clientTrackingRange
    ) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, VerseCraft.id(name));
        return Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                key,
                FabricEntityType.Builder.createMob(factory, category, mobConfigurator)
                        .sized(width, height)
                        .eyeHeight(eyeHeight)
                        .passengerAttachments(passengerAttachment)
                        .clientTrackingRange(clientTrackingRange)
                        .build(key)
        );
    }

    public static void registerEntitiesTypes() {
        VerseCraft.LOGGER.info("Registering Entities for " + VerseCraft.MOD_ID);
    }
}
