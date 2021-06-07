package com.anonymoushacker1279.immersiveweapons.item;

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

import java.util.List;

public class TeslaArmorItem extends ArmorItem {

	public boolean armorIsToggled = false;
	private boolean isLeggings = false;
	private int countdown = 0;

	public TeslaArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
		super(material, slot, (new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP)));
		if (type == 2) {
			isLeggings = true;
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return (!isLeggings ? ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_1.png" : ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_2.png");
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.TESLA_HELMET.get() &&
				player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.TESLA_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.TESLA_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.TESLA_BOOTS.get()) {
			if (ClientModEventSubscriber.toggleArmorEffect.consumeClick()) {
				if (!armorIsToggled) {
					armorIsToggled = true;
					world.playLocalSound(player.getX(), player.getY(), player.getZ(), DeferredRegistryHandler.TESLA_ARMOR_POWER_UP.get(), SoundCategory.NEUTRAL, 0.9f, 1, false);
				} else {
					armorIsToggled = false;
					world.playLocalSound(player.getX(), player.getY(), player.getZ(), DeferredRegistryHandler.TESLA_ARMOR_POWER_DOWN.get(), SoundCategory.NEUTRAL, 0.9f, 1, false);
					countdown = 0;
				}
			}

			if (armorIsToggled) {
				List<Entity> entity = player.level.getEntities(player, player.getBoundingBox().move(-3, -3, -3).expandTowards(6, 6, 6));

				if (!entity.isEmpty()) {
					for (Entity element : entity) {
						if (element.showVehicleHealth()) {
							((LivingEntity) element).addEffect(new EffectInstance(Effects.WEAKNESS, 100, 0, false, false));
							((LivingEntity) element).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0, false, false));
							((LivingEntity) element).addEffect(new EffectInstance(Effects.CONFUSION, 100, 0, false, false));
							effectNoise(world, player);
						}
					}
				}
			}

		}
	}

	private void effectNoise(World world, PlayerEntity player) {
		if (countdown == 0 && Config.TESLA_ARMOR_EFFECT_SOUND.get()) {
			world.playSound(player, player.blockPosition(), DeferredRegistryHandler.TESLA_ARMOR_EFFECT.get(), SoundCategory.NEUTRAL, 0.65f, 1);
			countdown = 120;
		} else if (countdown > 0) {
			countdown--;
		}
	}
}