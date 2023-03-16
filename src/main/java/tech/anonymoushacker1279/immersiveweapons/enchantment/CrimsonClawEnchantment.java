package tech.anonymoushacker1279.immersiveweapons.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CrimsonClawEnchantment extends Enchantment {

	public CrimsonClawEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
		super(rarity, enchantmentCategory, equipmentSlots);
	}

	@Override
	public int getMinCost(int pEnchantmentLevel) {
		return 1 + (pEnchantmentLevel - 1) * 15;
	}

	@Override
	public int getMaxCost(int pEnchantmentLevel) {
		return getMinCost(pEnchantmentLevel) + 15;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}
}