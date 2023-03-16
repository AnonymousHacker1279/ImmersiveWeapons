package tech.anonymoushacker1279.immersiveweapons.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ExcessiveForceEnchantment extends Enchantment {

	public ExcessiveForceEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
		super(rarity, enchantmentCategory, equipmentSlots);
	}

	@Override
	public int getMinCost(int pEnchantmentLevel) {
		return 1 + (pEnchantmentLevel - 1) * 20;
	}

	@Override
	public int getMaxCost(int pEnchantmentLevel) {
		return getMinCost(pEnchantmentLevel) + 20;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}
}