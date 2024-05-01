package tech.anonymoushacker1279.immersiveweapons.data.loot;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LootTableGenerator extends LootTableProvider {

	public LootTableGenerator(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, Set.of(), List.of(), provider);
	}

	@Override
	public List<SubProviderEntry> getTables() {
		return ImmutableList.of(
				new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK),
				new SubProviderEntry(ChestLootTables::new, LootContextParamSets.CHEST),
				new SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY));
	}
}