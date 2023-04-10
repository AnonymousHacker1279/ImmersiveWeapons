package tech.anonymoushacker1279.immersiveweapons.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.*;

public class EndlessMusketPouchEnchantment extends Enchantment {

	public EndlessMusketPouchEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] applicableSlots) {
		super(rarity, category, applicableSlots);
	}

	@Override
	public int getMinCost(int pEnchantmentLevel) {
		return 20;
	}

	@Override
	public int getMaxCost(int pEnchantmentLevel) {
		return 50;
	}

	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		return !(enchantment instanceof MendingEnchantment) && super.checkCompatibility(enchantment);
	}
}