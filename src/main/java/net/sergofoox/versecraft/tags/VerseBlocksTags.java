package net.sergofoox.versecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.sergofoox.versecraft.VerseCraft;


public class VerseBlocksTags {

    public static class Blocks {
        public static final TagKey<Block> AZALEA_LOGS = tag("azalea_logs");
        public static final TagKey<Block> IMMOVABLE = tag("immovable");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> AZALEA_LOGS = tag("azalea_logs");


        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(VerseCraft.MOD_ID, name));
        }
    }
}
