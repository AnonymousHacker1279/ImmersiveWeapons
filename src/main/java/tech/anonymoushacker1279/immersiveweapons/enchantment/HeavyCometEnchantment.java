package tech.anonymoushacker1279.immersiveweapons.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class HeavyCometEnchantment extends Enchantment {

	public HeavyCometEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
		super(rarity, category, slots);
	}

	@Override
	public int getMinCost(int level) {
		return 1 + (level - 1) * 20;
	}

	@Override
	public int getMaxCost(int level) {
		return getMinCost(level) + 20;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}
}