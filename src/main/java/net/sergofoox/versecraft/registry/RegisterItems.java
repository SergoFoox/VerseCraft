package net.sergofoox.versecraft.registry;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.sergofoox.versecraft.VerseCraft;

import java.util.function.Function;

public class RegisterItems {

    public static final Item DRAGON_POTTERY_SHERD = registerItem("dragon_pottery_sherd",
            props -> new Item(props.potPattern(RegisterPotPattern.DRAGON).rarity(Rarity.UNCOMMON)));

    public static final Item EYE_POTTERY_SHERD = registerItem("eye_pottery_sherd",
            props -> new Item(props.potPattern(RegisterPotPattern.EYE).rarity(Rarity.UNCOMMON)));

    public static final Item EGG_POTTERY_SHERD = registerItem("egg_pottery_sherd",
            props -> new Item(props.potPattern(RegisterPotPattern.EGG).rarity(Rarity.UNCOMMON)));

    public static final Item PILLAGER_POTTERY_SHERD = registerItem("pillager_pottery_sherd",
            props -> new Item(props.potPattern(RegisterPotPattern.PILLAGER).rarity(Rarity.UNCOMMON)));

    public static final Item PORTAL_POTTERY_SHERD = registerItem("portal_pottery_sherd",
            props -> new Item(props.potPattern(RegisterPotPattern.PORTAL).rarity(Rarity.UNCOMMON)));

    public static final Item SWORD_POTTERY_SHERD = registerItem("sword_pottery_sherd",
            props -> new Item(props.potPattern(RegisterPotPattern.SWORD).rarity(Rarity.UNCOMMON)));

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

    public static void registerItems() {
        VerseCraft.LOGGER.info("Registering Items for Mod" + VerseCraft.MOD_ID);
    }

}
