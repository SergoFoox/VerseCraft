package net.sergofoox.versecraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.sergofoox.versecraft.registry.RegisterEntityTypes;
import net.sergofoox.versecraft.tags.VerseEntityTypeTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class VerseEntityTypeTagsProvider extends FabricTagsProvider.EntityTypeTagsProvider {

    public VerseEntityTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        tag(EntityTypeTags.BOAT)
                .add(key(RegisterEntityTypes.AZALEA_BOAT))
                .add(key(RegisterEntityTypes.AZALEA_CHEST_BOAT));

        tag(VerseEntityTypeTags.DISALLOWED_MANSION_MONSTERS)
                .add(key(EntityTypes.ZOMBIE))
                .add(key(EntityTypes.ZOMBIE_VILLAGER))
                .add(key(EntityTypes.DROWNED))
                .add(key(EntityTypes.HUSK))
                .add(key(EntityTypes.SKELETON))
                .add(key(EntityTypes.STRAY))
                .add(key(EntityTypes.BOGGED))
                .add(key(EntityTypes.WITHER_SKELETON))
                .add(key(EntityTypes.CREEPER))
                .add(key(EntityTypes.SPIDER))
                .add(key(EntityTypes.CAVE_SPIDER))
                .add(key(EntityTypes.SLIME))
                .add(key(EntityTypes.ENDERMAN))
                .add(key(EntityTypes.PHANTOM));

        tag(VerseEntityTypeTags.DISALLOWED_PALE_GARDEN_SURFACE_MONSTERS)
                .add(key(EntityTypes.ZOMBIE))
                .add(key(EntityTypes.ZOMBIE_VILLAGER))
                .add(key(EntityTypes.DROWNED))
                .add(key(EntityTypes.HUSK))
                .add(key(EntityTypes.SKELETON))
                .add(key(EntityTypes.STRAY))
                .add(key(EntityTypes.BOGGED))
                .add(key(EntityTypes.CREEPER))
                .add(key(EntityTypes.SPIDER))
                .add(key(EntityTypes.CAVE_SPIDER))
                .add(key(EntityTypes.SLIME))
                .add(key(EntityTypes.ENDERMAN))
                .add(key(EntityTypes.WITCH))
                .add(key(EntityTypes.PHANTOM));
    }

    private static ResourceKey<EntityType<?>> key(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityType).orElseThrow();
    }
}
