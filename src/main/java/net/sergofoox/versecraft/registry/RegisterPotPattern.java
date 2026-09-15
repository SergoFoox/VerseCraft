package net.sergofoox.versecraft.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.sergofoox.versecraft.VerseCraft;

public class RegisterPotPattern {

    public static final ResourceKey<DecoratedPotPattern> DRAGON = createKey("dragon");
    public static final ResourceKey<DecoratedPotPattern> EYE = createKey("eye");
    public static final ResourceKey<DecoratedPotPattern> EGG = createKey("egg");
    public static final ResourceKey<DecoratedPotPattern> PILLAGER = createKey("pillager");
    public static final ResourceKey<DecoratedPotPattern> PORTAL = createKey("portal");
    public static final ResourceKey<DecoratedPotPattern> SWORD = createKey("sword");


    private static ResourceKey<DecoratedPotPattern> createKey(String name) {
        return ResourceKey.create(Registries.DECORATED_POT_PATTERN, VerseCraft.id(name));
    }
}
