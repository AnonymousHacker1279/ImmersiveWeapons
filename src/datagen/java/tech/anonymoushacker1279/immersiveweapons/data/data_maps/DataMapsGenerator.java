package tech.anonymoushacker1279.immersiveweapons.data.data_maps;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Transformable;
import net.neoforged.neoforge.registries.datamaps.builtin.VibrationFrequency;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.GameEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.FlammablePowder;

import java.util.concurrent.CompletableFuture;

public class DataMapsGenerator extends DataMapProvider {

	public DataMapsGenerator(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void gather(Provider provider) {
		builder(NeoForgeDataMaps.VIBRATION_FREQUENCIES)
				.add(GameEventRegistry.FLASHBANG_EXPLODE, new VibrationFrequency(15), false)
				.add(GameEventRegistry.SMOKE_GRENADE_HISS, new VibrationFrequency(14), false)
				.add(GameEventRegistry.PANIC_ALARM_TRIGGER, new VibrationFrequency(15), false);

		builder(AbstractGunItem.POWDER_TYPE)
				.add(ItemRegistry.SULFUR_DUST.get().builtInRegistryHolder(), new FlammablePowder(0.9f, -0.05f, 2, 3), false)
				.add(ItemRegistry.BLACKPOWDER.get().builtInRegistryHolder(), new FlammablePowder(0.75f, 0.025f, 2, 2), false)
				.add(Items.GUNPOWDER.builtInRegistryHolder(), new FlammablePowder(0.33f, 0.05f, 1, 1), false)
				.add(Items.BLAZE_POWDER.builtInRegistryHolder(), new FlammablePowder(0.25f, 0.1f, 1, 0, true), false);

		builder(NeoForgeDataMaps.TRANSFORMABLES)
				.add(BlockRegistry.BURNED_OAK_LOG.key(), Transformable.stripping(BlockRegistry.BURNED_OAK_LOG.get(), BlockRegistry.STRIPPED_BURNED_OAK_LOG.get()), false)
				.add(BlockRegistry.BURNED_OAK_WOOD.key(), Transformable.stripping(BlockRegistry.BURNED_OAK_WOOD.get(), BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get()), false)
				.add(BlockRegistry.STARDUST_LOG.key(), Transformable.stripping(BlockRegistry.STARDUST_LOG.get(), BlockRegistry.STRIPPED_STARDUST_LOG.get()), false)
				.add(BlockRegistry.STARDUST_WOOD.key(), Transformable.stripping(BlockRegistry.STARDUST_WOOD.get(), BlockRegistry.STRIPPED_STARDUST_WOOD.get()), false);
	}
}