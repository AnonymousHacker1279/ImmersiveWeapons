package tech.anonymoushacker1279.iwcompatbridge.init;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;

import java.util.function.Supplier;

public class IWCBItemRegistry {

	// Item Register
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IWCompatBridge.MOD_ID);

	public static final Supplier<Item> COBALT_SHARD = ITEMS.registerItem("cobalt_shard", Item::new);
	public static final Supplier<Item> COBALT_CLUMP = ITEMS.registerItem("cobalt_clump", Item::new);
	public static final Supplier<Item> COBALT_DIRTY_DUST = ITEMS.registerItem("cobalt_dirty_dust", Item::new);
	public static final Supplier<Item> COBALT_DUST = ITEMS.registerItem("cobalt_dust", Item::new);
	public static final Supplier<Item> COBALT_CRYSTAL = ITEMS.registerItem("cobalt_crystal", Item::new);
}