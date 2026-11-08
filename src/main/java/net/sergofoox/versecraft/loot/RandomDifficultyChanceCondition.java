package net.sergofoox.versecraft.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record RandomDifficultyChanceCondition(float defaultChance, float peacefulChance, float hardChance) implements LootItemCondition {

    public static final MapCodec<RandomDifficultyChanceCondition> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.FLOAT.optionalFieldOf("default_chance", 0.65f).forGetter(RandomDifficultyChanceCondition::defaultChance),
            Codec.FLOAT.optionalFieldOf("peaceful", 0.0f).forGetter(RandomDifficultyChanceCondition::peacefulChance),
            Codec.FLOAT.optionalFieldOf("hard", 0.8f).forGetter(RandomDifficultyChanceCondition::hardChance)
    ).apply(inst, RandomDifficultyChanceCondition::new));

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return MAP_CODEC;
    }

    @Override
    public boolean test(LootContext context) {
        Difficulty difficulty = context.getLevel().getDifficulty();
        float chance = switch (difficulty) {
            case PEACEFUL -> this.peacefulChance;
            case HARD -> this.hardChance;
            default -> this.defaultChance;
        };
        return context.getRandom().nextFloat() < chance;
    }
}
