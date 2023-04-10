package tech.anonymoushacker1279.immersiveweapons.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ExtendedReachEnchantment extends Enchantment {

	public ExtendedReachEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
		super(rarity, enchantmentCategory, equipmentSlots);
	}

	@Override
	public int getMinCost(int pEnchantmentLevel) {
		return 15;
	}

	@Override
	public int getMaxCost(int pEnchantmentLevel) {
		return 30;
	}

}