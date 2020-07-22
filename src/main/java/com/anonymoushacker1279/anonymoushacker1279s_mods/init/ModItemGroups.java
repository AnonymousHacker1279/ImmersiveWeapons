package com.anonymoushacker1279.anonymoushacker1279s_mods.init;

import com.anonymoushacker1279.anonymoushacker1279s_mods.AnonymousHacker1279s_Mods;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
public class ModItemGroups extends ItemGroup {

	private final Supplier<ItemStack> iconSupplier;

	public ModItemGroups(final String name, final Supplier<ItemStack> iconSupplier) {
		super(name);
		this.iconSupplier = iconSupplier;
	}

	@Override
	public ItemStack createIcon() {
		return iconSupplier.get();
	}
	
	public static final ItemGroup MOD_ITEM_GROUP = new ModItemGroups(AnonymousHacker1279s_Mods.MODID, () -> new ItemStack(ModItems.MOLTEN_SWORD));
	
}