package com.anonymoushacker1279.immersiveweapons.item.armor;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class MoltenArmorItem extends ArmorItem {

	private boolean isLeggings = false;

	/**
	 * Constructor for MoltenArmorItem.
	 * @param material the <code>IArmorMaterial</code> for the item
	 * @param slot the <code>EquipmentSlotType</code>
	 * @param type type ID
	 */
	public MoltenArmorItem(ArmorMaterial material, EquipmentSlot slot, int type) {
		super(material, slot, (new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant()));
		if (type == 2) {
			isLeggings = true;
		}
	}

	/**
	 * Get the armor texture.
	 * @param stack the <code>ItemStack</code> instance
	 * @param entity the <code>Entity</code> wearing the armor
	 * @param slot the <code>EquipmentSlotType</code>
	 * @param type type ID
	 * @return String
	 */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/molten_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/molten_layer_2.png");
	}

	/**
	 * Runs once per tick while armor is equipped
	 * @param stack the <code>ItemStack</code> instance
	 * @param world the <code>World</code> the player is in
	 * @param player the <code>PlayerEntity</code> wearing the armor
	 */
	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			if (player.isInLava()) {
				player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, false));
				player.makeStuckInBlock(Blocks.LAVA.defaultBlockState(), new Vec3(5.5D, 5.5D, 5.5D));
				player.clearFire();
			} else if (player.getLastDamageSource() == DamageSource.IN_FIRE || player.getLastDamageSource() == DamageSource.ON_FIRE) {
				player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, false));
				player.clearFire();
			}
		}
	}
}