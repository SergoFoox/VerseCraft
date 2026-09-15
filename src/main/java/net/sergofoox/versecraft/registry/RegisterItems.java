package net.sergofoox.versecraft.registry;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.sergofoox.versecraft.VerseCraft;

import java.util.function.Function;

public class RegisterItems {

    public static final Item DRAGON_POTTERY_SHERD = registerSherd("dragon_pottery_sherd", RegisterPotPattern.DRAGON);
    public static final Item EYE_POTTERY_SHERD = registerSherd("eye_pottery_sherd", RegisterPotPattern.EYE);
    public static final Item EGG_POTTERY_SHERD = registerSherd("egg_pottery_sherd", RegisterPotPattern.EGG);
    public static final Item PILLAGER_POTTERY_SHERD = registerSherd("pillager_pottery_sherd", RegisterPotPattern.PILLAGER);
    public static final Item PORTAL_POTTERY_SHERD = registerSherd("portal_pottery_sherd", RegisterPotPattern.PORTAL);
    public static final Item SWORD_POTTERY_SHERD = registerSherd("sword_pottery_sherd", RegisterPotPattern.SWORD);

    public static final Item AZALEA_SIGN = registerItem("azalea_sign",
            props -> new StandingAndWallBlockItem(
                    RegisterBlocks.AZALEA_SIGN,
                    RegisterBlocks.AZALEA_WALL_SIGN,
                    Direction.DOWN,
                    props.stacksTo(16).signText()
            )
    );

    public static final Item AZALEA_HANGING_SIGN = registerItem("azalea_hanging_sign",
            props -> new HangingSignItem(
                    RegisterBlocks.AZALEA_HANGING_SIGN,
                    RegisterBlocks.AZALEA_WALL_HANGING_SIGN,
                    props.stacksTo(16)
            )
    );

    public static final Item AZALEA_BOAT = registerItem(
            "azalea_boat",
            properties -> new BoatItem(RegisterEntityTypes.AZALEA_BOAT, properties.stacksTo(1))
    );

    public static final Item AZALEA_CHEST_BOAT = registerItem(
            "azalea_chest_boat",
            properties -> new BoatItem(RegisterEntityTypes.AZALEA_CHEST_BOAT, properties.stacksTo(1))
    );

    public static final Item CHERRY = registerItem("cherry", properties -> new Item(properties
            .food(RegisterFood.CHERRY)));

    public static final Item BLUE_ROSE_SEEDS = registerItem("blue_rose_seeds",
            properties -> new BlockItem(RegisterBlocks.BLUE_ROSE_CROP, properties)
    );

    public static final Item ICEFLOWER_SEEDS = registerItem("iceflower_seeds",
            properties -> new BlockItem(RegisterBlocks.ICEFLOWER_CROP, properties)
    );

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name)))));
    }

    private static Item registerSherd(String name, ResourceKey<DecoratedPotPattern> pattern) {
        return registerItem(name, props -> new Item(
                props.rarity(Rarity.UNCOMMON)
                        .delayedComponent(DataComponents.PROVIDES_POTTERY_PATTERN,
                                provider -> provider.get(pattern).orElse(null))
        ));
    }
    public static void registerItems() {
        VerseCraft.LOGGER.info("Registering Items for Mod" + VerseCraft.MOD_ID);
    }

}
