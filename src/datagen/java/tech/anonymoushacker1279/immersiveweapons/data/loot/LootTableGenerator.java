package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class LootTableGenerator extends LootTableProvider {

	public LootTableGenerator() {
		super(Set.of(), List.of(
				new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK),
				new SubProviderEntry(ChestLootTables::new, LootContextParamSets.CHEST),
				new SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY)));
	}
}
