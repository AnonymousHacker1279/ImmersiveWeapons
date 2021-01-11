package com.anonymoushacker1279.immersiveweapons.items;

import java.util.List;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.ClientModEventSubscriber;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class TeslaArmorItem extends ArmorItem {
	
	private boolean isLeggings = false;

	public TeslaArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
		super(material, slot, (new Item.Properties().group(DeferredRegistryHandler.ITEM_GROUP)));
		if (type == 2) {
			isLeggings = true;
		}
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return (isLeggings == false ? ImmersiveWeapons.MOD_ID+":textures/armor/tesla_layer_1.png" : ImmersiveWeapons.MOD_ID+":textures/armor/tesla_layer_2.png");
	}
	
	public boolean armorIsToggled = false;
	
	@Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if(player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.TESLA_HELMET.get() && 
			player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.TESLA_CHESTPLATE.get() && 
			player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.TESLA_LEGGINGS.get() && 
			player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.TESLA_BOOTS.get()) 
		{
			if (ClientModEventSubscriber.toggleArmorEffect.isPressed()) {
				if (armorIsToggled == false) {
					armorIsToggled = true;
					world.playSound(player.getPosX(), player.getPosY(), player.getPosZ(), DeferredRegistryHandler.TESLA_ARMOR_POWER_UP.get(), SoundCategory.NEUTRAL, 0.9f, 1, false);
					// world.playSound(player, player.getPosition(), DeferredRegistryHandler.TESLA_ARMOR_POWER_UP.get(), SoundCategory.PLAYERS, 0.9f, 1);
				} else if (armorIsToggled == true) {
					armorIsToggled = false;
					world.playSound(player.getPosX(), player.getPosY(), player.getPosZ(), DeferredRegistryHandler.TESLA_ARMOR_POWER_DOWN.get(), SoundCategory.NEUTRAL, 0.9f, 1, false);
					countdown = 0;
				}
			}
			
			if (armorIsToggled == true) {
				List<Entity> entity = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().offset(-3, -3, -3).expand(6, 6, 6));
				
				if (!entity.isEmpty()) {
					for (java.util.Iterator<Entity> iterator = entity.iterator(); iterator.hasNext();) {
						Entity element = iterator.next();
							if (element.isLiving()) {
								((LivingEntity) element).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 100, 1, false, false));
								((LivingEntity) element).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 1, false, false));
								((LivingEntity) element).addPotionEffect(new EffectInstance(Effects.NAUSEA, 100, 1, false, false));
		
								effectNoise(world, player);
							}
					}
				}
			}
			
		}
	}

	private int countdown = 0;
	
	private void effectNoise(World world, PlayerEntity player) {
		if (countdown == 0 && Config.TESLA_ARMOR_EFFECT_SOUND.get() == true) {
			world.playSound(player, player.getPosition(), DeferredRegistryHandler.TESLA_ARMOR_EFFECT.get(), SoundCategory.NEUTRAL, 0.65f, 1);
			countdown = 120;
		} else if (countdown > 0){
			countdown --;
		}
	}
}
