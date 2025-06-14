package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;

public class MoltenArmorItem extends Item implements TickableArmor {

	public MoltenArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties) {
		super(properties.humanoidArmor(material, armorType));
	}

	@Override
	public void playerTick(Level level, Player player) {
		if (ArmorUtils.isWearingMoltenArmor(player)) {
			if (player.isInLava()) {
				player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, false));
				player.clearFire();
			} else if (player.isOnFire()) {
				player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, false));
				player.clearFire();
			}
		}
	}
}