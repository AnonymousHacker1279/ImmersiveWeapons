package tech.anonymoushacker1279.immersiveweapons.data.loot;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.*;

public class LootTableGenerator extends LootTableProvider {

	public LootTableGenerator(PackOutput output) {
		super(output, Set.of(), List.of());
	}

	@Override
	public List<SubProviderEntry> getTables() {
		return ImmutableList.of(
				new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK),
				new SubProviderEntry(ChestLootTables::new, LootContextParamSets.CHEST),
				new SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY));
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
		map.forEach((resourceLocation, lootTable) -> lootTable.validate(validationContext));
	}
}