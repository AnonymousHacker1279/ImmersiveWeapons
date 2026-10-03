package tech.anonymoushacker1279.iwcompatbridge.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class IWCBDeferredRegistryHandler {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ImmersiveWeapons.MOD_ID);

	public static final Supplier<CreativeModeTab> IWCB_TAB = CREATIVE_MODE_TABS.register("iwcompatbridge_tab", () -> CreativeModeTab.builder()
			.icon(() -> ItemRegistry.MOLTEN_SWORD.get().getDefaultInstance())
			.title(Component.translatable("itemGroup.iwcompatbridge.creative_tab"))
			.build());

	public static void init(IEventBus modEventBus) {
		IWCompatBridge.LOGGER.info("Initializing deferred registry for items");
		IWCBItemRegistry.ITEMS.register(modEventBus);

		IWCompatBridge.LOGGER.info("Initializing deferred registry for creative tabs");
		CREATIVE_MODE_TABS.register(modEventBus);

		if (ModList.get().isLoaded("mekanism")) {
			IWCompatBridge.LOGGER.info("Mekanism detected: initializing related registry objects");

			// IWCompatBridge.LOGGER.info("Initializing deferred registry for Mekanism chemicals");
			// IWCBChemicalRegistry.CHEMICALS.register(modEventBus);
		}
	}

	public static void setupCreativeTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTab() == IWCBDeferredRegistryHandler.IWCB_TAB.get()) {
			Collection<DeferredHolder<Item, ? extends Item>> registryObjects = IWCBItemRegistry.ITEMS.getEntries();
			List<Item> items = new ArrayList<>(registryObjects.size());
			registryObjects.stream().map(Supplier::get).forEach(items::add);

			for (Item item : items) {
				event.accept(item);
			}
		}
	}
}