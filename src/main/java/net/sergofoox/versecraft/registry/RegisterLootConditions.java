package net.sergofoox.versecraft.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.sergofoox.versecraft.VerseCraft;
import net.sergofoox.versecraft.loot.RandomDifficultyChanceCondition;

public class RegisterLootConditions {

    public static final MapCodec<RandomDifficultyChanceCondition> RANDOM_DIFFICULTY_CHANCE =
            Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, VerseCraft.id("random_difficulty_chance"), RandomDifficultyChanceCondition.MAP_CODEC);

    public static void registerLootConditions() {
    }
}
