package net.sergofoox.versecraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.sergofoox.versecraft.tags.VerseEntityTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {

    @Inject(method = "getMobsAt", at = @At("RETURN"), cancellable = true)
    private void versecraft$filterMansionSpawns(Level level, StructureManager structureManager, MobCategory category, BlockPos pos, CallbackInfoReturnable<WeightedList<MobSpawnSettings.SpawnerData>> cir) {
        if (category != MobCategory.MONSTER) {
            return;
        }

        StructureStart start = structureManager.getStructureWithPieceAt(pos, holder -> holder.is(BuiltinStructures.WOODLAND_MANSION));
        if (start != null && start.isValid()) {
            WeightedList<MobSpawnSettings.SpawnerData> original = cir.getReturnValue();
            if (original == null || original.isEmpty()) {
                return;
            }

            List<Weighted<MobSpawnSettings.SpawnerData>> filtered = original.unwrap().stream()
                    .filter(entry -> !versecraft$isDisallowedMansionMonster(entry.value().type()))
                    .toList();

            if (filtered.isEmpty()) {
                cir.setReturnValue(MobSpawnSettings.EMPTY_MOB_LIST);
            } else if (filtered.size() != original.unwrap().size()) {
                cir.setReturnValue(WeightedList.of(filtered));
            }
        }
    }

    @Unique
    private static boolean versecraft$isDisallowedMansionMonster(EntityType<?> type) {
        return type.builtInRegistryHolder().is(VerseEntityTypeTags.DISALLOWED_MANSION_MONSTERS);
    }
}
