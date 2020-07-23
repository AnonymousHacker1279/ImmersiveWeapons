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

		if(player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get() &&
				!world.isRemote && player.isInLava()
		) {
			player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0, true, false));
		}
	}
}
