package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.ClientModEventSubscriber;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class CobaltArmorItem extends ArmorItem {

	private boolean isLeggings = false;
	private boolean effectEnabled = false;

	public CobaltArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
		super(material, slot, (new Item.Properties().group(DeferredRegistryHandler.ITEM_GROUP)));
		if (type == 2) {
			isLeggings = true;
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_2.png");
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.COBALT_HELMET.get() &&
				player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.COBALT_CHESTPLATE.get() &&
				player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.COBALT_LEGGINGS.get() &&
				player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.COBALT_BOOTS.get()) {
			if (player.getUniqueID().toString().equals("086ab520-a158-3f3b-b711-6f10d99b4969") || player.getUniqueID().toString().equals("94f11dac-d1bc-46da-877b-c69f533f2da2")) {
				if (ClientModEventSubscriber.toggleArmorEffect.isPressed()) {
					effectEnabled = !effectEnabled;
				}

				if (effectEnabled) {
					world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getPosX(), player.getPosY() + 2.2D, player.getPosZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
					world.addParticle(ParticleTypes.FLAME, player.getPosX(), player.getPosY() + 2.2D, player.getPosZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));

					player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 1, 4, false, false));
					player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1, 1, false, false));
					player.addPotionEffect(new EffectInstance(Effects.SPEED, 1, 1, false, false));
				}
			}
		}
	}
}