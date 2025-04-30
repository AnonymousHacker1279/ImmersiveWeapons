package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class MoltenArmorItem extends ArmorItem {

	public MoltenArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties) {
		super(material, armorType, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (entity instanceof Player player) {
			if (ArmorUtils.isWearingMoltenArmor(player)) {
				if (player.isInLava()) {
					player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, false));
					player.makeStuckInBlock(Blocks.LAVA.defaultBlockState(), new Vec3(5.5D, 5.5D, 5.5D));
					player.clearFire();
				} else if (player.isOnFire()) {
					player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, false));
					player.clearFire();
				}
			}
		}
	}
}