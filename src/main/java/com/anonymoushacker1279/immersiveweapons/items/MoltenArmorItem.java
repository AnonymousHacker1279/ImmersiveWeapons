package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoltenArmorItem extends ArmorItem{

	private boolean isLeggings = false;
	
	public MoltenArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
		super(material, slot, (new Item.Properties().group(DeferredRegistryHandler.ITEM_GROUP).isImmuneToFire()));
		if (type == 2) {
			isLeggings = true;
		}
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return (isLeggings == false ? ImmersiveWeapons.MOD_ID+":textures/armor/molten_layer_1.png" : ImmersiveWeapons.MOD_ID+":textures/armor/molten_layer_2.png");
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() && 
				player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() && 
				player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() && 
				player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) 
			{
				if (player.isInLava()) {
					player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 140, 1, false, false));
				} else if (player.getLastDamageSource() == DamageSource.IN_FIRE || player.getLastDamageSource() == DamageSource.ON_FIRE) {
					player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 140, 1, false, false));
					player.extinguish();
				}
			}
	}
}
