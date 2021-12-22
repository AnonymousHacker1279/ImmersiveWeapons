package com.anonymoushacker1279.immersiveweapons.data.loot;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.*;

public class LootTableGenerator extends LootTableProvider {

	private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> subProviders =
			ImmutableList.of(
					Pair.of(ChestLootTables::new, LootContextParamSets.CHEST),
					Pair.of(EntityLootTables::new, LootContextParamSets.ENTITY));


	public LootTableGenerator(DataGenerator pGenerator) {
		super(pGenerator);
	}

	@Override
	protected @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
		return subProviders;
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationContext) {
		map.forEach((resourceLocation, lootTable) -> {
			LootTables.validate(validationContext, resourceLocation, lootTable);
		});
	}
}