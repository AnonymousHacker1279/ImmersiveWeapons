package tech.anonymoushacker1279.immersiveweapons.item.utility;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStackTemplate;
import org.jspecify.annotations.Nullable;

public class CraftingToolItem extends Item {

	/// A basic item type that simply is not used up during crafting.
	///
	/// @param properties the `Properties` for the item
	public CraftingToolItem(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable ItemStackTemplate getCraftingRemainder(ItemInstance instance) {
		return new ItemStackTemplate(this);
	}
}