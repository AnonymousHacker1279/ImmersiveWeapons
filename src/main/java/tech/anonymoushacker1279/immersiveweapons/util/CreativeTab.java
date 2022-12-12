package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.PotionRegistry;

import java.util.*;

public class CreativeTab extends CreativeModeTab {

	/**
	 * Constructor for CreativeTab.
	 *
	 * @param label the tab label
	 */
	public CreativeTab(String label) {
		super(label);
	}

	/**
	 * Set the tab icon.
	 *
	 * @return ItemStack
	 */
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemRegistry.TESLA_SWORD.get());
	}

	@Override
	public void fillItemList(NonNullList<ItemStack> itemStack) {
		Collection<RegistryObject<Item>> registryObjects = ItemRegistry.ITEMS.getEntries();
		List<Item> items = new ArrayList<>(registryObjects.size());
		registryObjects.stream().map(RegistryObject::get).forEach(items::add);

		for (Item item : items) {
			item.fillItemCategory(this, itemStack);
		}

		// Add potion items, which are not in the item registry.
		// Celestial potions
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.LONG_CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.LONG_CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.LONG_CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.LONG_CELESTIAL_BREW_POTION.get()));
		// Death potions
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.STRONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.STRONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.STRONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.STRONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), PotionRegistry.LONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.LONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), PotionRegistry.LONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), PotionRegistry.LONG_DEATH_POTION.get()));
	}
}