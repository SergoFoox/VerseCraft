package net.sergofoox.versecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.sergofoox.versecraft.VerseCraft;

public class VerseEntityTypeTags {
    public static final TagKey<EntityType<?>> DISALLOWED_MANSION_MONSTERS = tag("disallowed_mansion_monsters");

    private static TagKey<EntityType<?>> tag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name));
    }
}
