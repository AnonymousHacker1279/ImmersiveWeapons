package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

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
	public @NotNull ItemStack makeIcon() {
		return new ItemStack(DeferredRegistryHandler.TESLA_SWORD.get());
	}

	@Override
	public void fillItemList(@NotNull NonNullList<ItemStack> itemStack) {
		Collection<RegistryObject<Item>> registryObjects = DeferredRegistryHandler.ITEMS.getEntries();
		List<Item> items = new ArrayList<>(registryObjects.size());
		registryObjects.stream().map(RegistryObject::get).forEach(items::add);

		for (Item item : items) {
			item.fillItemCategory(this, itemStack);
		}

		// Add potion items, which are not in the item registry.
		// Celestial potions
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), DeferredRegistryHandler.CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), DeferredRegistryHandler.CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), DeferredRegistryHandler.CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.LONG_CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), DeferredRegistryHandler.LONG_CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), DeferredRegistryHandler.LONG_CELESTIAL_BREW_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), DeferredRegistryHandler.LONG_CELESTIAL_BREW_POTION.get()));
		// Death potions
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), DeferredRegistryHandler.DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), DeferredRegistryHandler.DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), DeferredRegistryHandler.DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.STRONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), DeferredRegistryHandler.STRONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), DeferredRegistryHandler.STRONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), DeferredRegistryHandler.STRONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.POTION), DeferredRegistryHandler.LONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), DeferredRegistryHandler.LONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), DeferredRegistryHandler.LONG_DEATH_POTION.get()));
		itemStack.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), DeferredRegistryHandler.LONG_DEATH_POTION.get()));
	}
}