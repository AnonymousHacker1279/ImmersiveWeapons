package tech.anonymoushacker1279.immersiveweapons.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class NightmarishStareEnchantment extends Enchantment {

	public NightmarishStareEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
		super(rarity, enchantmentCategory, equipmentSlots);
	}

	@Override
	public int getMinCost(int level) {
		return 1 + (level - 1) * 10;
	}

	@Override
	public int getMaxCost(int level) {
		return super.getMinCost(level) + 15;
	}

	@Override
	public int getMaxLevel() {
		return 7;
	}
}