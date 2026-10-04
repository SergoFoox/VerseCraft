package net.sergofoox.versecraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.sergofoox.versecraft.registry.RegisterEntityTypes;
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
    }

    private static ResourceKey<EntityType<?>> key(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityType).orElseThrow();
    }
}