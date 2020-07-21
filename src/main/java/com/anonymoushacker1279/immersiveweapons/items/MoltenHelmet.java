package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.CustomArmorMaterials;
import com.anonymoushacker1279.immersiveweapons.util.DeferredRegistryHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class MoltenHelmet extends ArmorItem {

	public MoltenHelmet() {
		super(CustomArmorMaterials.MOLTEN, EquipmentSlotType.HEAD, (new Item.Properties().group(ImmersiveWeapons.TAB)));
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return ImmersiveWeapons.MOD_ID+":textures/armor/molten_layer_1.png";
	}

	// Tick and add fire resistance when all armor pieces are worn
	@Override
	public void onArmorTick(ItemStack itemStack, World world, PlayerEntity player) {
		RegistryObject<MoltenHelmet> helmet = DeferredRegistryHandler.MOLTEN_HELMET;
		RegistryObject<MoltenChestplate> chestplate = DeferredRegistryHandler.MOLTEN_CHESTPLATE;
		RegistryObject<MoltenLeggings> leggings = DeferredRegistryHandler.MOLTEN_LEGGINGS;
		RegistryObject<MoltenBoots> boots = DeferredRegistryHandler.MOLTEN_BOOTS;
		
		if(helmet == DeferredRegistryHandler.MOLTEN_HELMET) {
			if(chestplate == DeferredRegistryHandler.MOLTEN_CHESTPLATE) {
				if(leggings == DeferredRegistryHandler.MOLTEN_LEGGINGS) {
					if(boots == DeferredRegistryHandler.MOLTEN_BOOTS) {
						if(player.isInLava()) {
							if(!world.isRemote()) {
								player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0, true, false));
							}
						}
					}
				}
			}
		}
	}
	/*
	 if(helmet == DeferredRegistryHandler.MOLTEN_HELMET && chestplate == DeferredRegistryHandler.MOLTEN_CHESTPLATE && leggings == DeferredRegistryHandler.MOLTEN_LEGGINGS && boots == DeferredRegistryHandler.MOLTEN_BOOTS && !player.isInLava()) {
			if(!world.isRemote) {
				player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0, true, false));
			} 
		}
	 */
}
