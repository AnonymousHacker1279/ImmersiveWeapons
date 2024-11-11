package tech.anonymoushacker1279.immersiveweapons.data.data_maps;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.*;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.FlammablePowder;

import java.util.concurrent.CompletableFuture;

public class DataMapsGenerator extends DataMapProvider {

	public DataMapsGenerator(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void gather() {
		builder(NeoForgeDataMaps.VIBRATION_FREQUENCIES)
				.add(GameEventRegistry.FLASHBANG_EXPLODE, new VibrationFrequency(15), false)
				.add(GameEventRegistry.SMOKE_GRENADE_HISS, new VibrationFrequency(14), false)
				.add(GameEventRegistry.PANIC_ALARM_TRIGGER, new VibrationFrequency(15), false);

		builder(NeoForgeDataMaps.COMPOSTABLES)
				.add(BlockItemRegistry.STARDUST_SAPLING_ITEM.get().builtInRegistryHolder(), new Compostable(0.3f), false)
				.add(BlockItemRegistry.STARDUST_LEAVES_ITEM.get().builtInRegistryHolder(), new Compostable(0.5f), false)
				.add(BlockItemRegistry.BURNED_OAK_BRANCH_ITEM.get().builtInRegistryHolder(), new Compostable(0.3f), false)
				.add(BlockItemRegistry.MOONGLOW_ITEM.get().builtInRegistryHolder(), new Compostable(0.65f), false)
				.add(BlockItemRegistry.DEATHWEED_ITEM.get().builtInRegistryHolder(), new Compostable(0.65f), false)
				.add(ItemRegistry.CHOCOLATE_BAR.get().builtInRegistryHolder(), new Compostable(0.2f), false)
				.add(ItemRegistry.MRE.get().builtInRegistryHolder(), new Compostable(1.0f), false);

		builder(AbstractGunItem.POWDER_TYPE)
				.add(ItemRegistry.SULFUR_DUST.get().builtInRegistryHolder(), new FlammablePowder(0.9f, -0.05f, 2, 3), false)
				.add(ItemRegistry.BLACKPOWDER.get().builtInRegistryHolder(), new FlammablePowder(0.75f, 0.025f, 2, 2), false)
				.add(Items.GUNPOWDER.builtInRegistryHolder(), new FlammablePowder(0.33f, 0.05f, 1, 1), false)
				.add(Items.BLAZE_POWDER.builtInRegistryHolder(), new FlammablePowder(0.25f, 0.1f, 1, 0, true), false);
	}
}