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
		super(material, slot, (new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP)));
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
		if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.COBALT_HELMET.get() &&
				player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.COBALT_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.COBALT_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.COBALT_BOOTS.get()) {
			if (player.getUUID().toString().equals("086ab520-a158-3f3b-b711-6f10d99b4969") || player.getUUID().toString().equals("94f11dac-d1bc-46da-877b-c69f533f2da2")) {
				if (ClientModEventSubscriber.toggleArmorEffect.consumeClick()) {
					effectEnabled = !effectEnabled;
				}

				if (effectEnabled) {
					world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX(), player.getY() + 2.2D, player.getZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
					world.addParticle(ParticleTypes.FLAME, player.getX(), player.getY() + 2.2D, player.getZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));

					player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 1, 4, false, false));
					player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 1, 1, false, false));
					player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1, 1, false, false));
				}
			}
		}
	}
}