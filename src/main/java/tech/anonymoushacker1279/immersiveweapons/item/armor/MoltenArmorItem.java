package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class MoltenArmorItem extends Item {

	public MoltenArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties) {
		super(properties.humanoidArmor(material, armorType));
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
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