package tech.anonymoushacker1279.immersiveweapons.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CelestialFuryEnchantment extends Enchantment {

	public CelestialFuryEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
		super(rarity, category, slots);
	}

	@Override
	public int getMinCost(int level) {
		return 35;
	}

	@Override
	public int getMaxCost(int level) {
		return 50;
	}

}