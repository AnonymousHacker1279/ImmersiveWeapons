package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractDyingSoldierEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractWanderingWarriorEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.HansEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractFieldMedicEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractMinutemanEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	/**
	 * Event handler for the RegistryEvent.Register event.
	 * @param event the <code>RegistryEvent.Register</code> instance
	 */
	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		((AbstractArrowItem) (DeferredRegistryHandler.COPPER_ARROW.get())).setItemReference(DeferredRegistryHandler.COPPER_ARROW);
		((AbstractArrowItem) (DeferredRegistryHandler.IRON_ARROW.get())).setItemReference(DeferredRegistryHandler.IRON_ARROW);
		((AbstractArrowItem) (DeferredRegistryHandler.DIAMOND_ARROW.get())).setItemReference(DeferredRegistryHandler.DIAMOND_ARROW);
		((AbstractArrowItem) (DeferredRegistryHandler.GOLD_ARROW.get())).setItemReference(DeferredRegistryHandler.GOLD_ARROW);
		((AbstractArrowItem) (DeferredRegistryHandler.STONE_ARROW.get())).setItemReference(DeferredRegistryHandler.STONE_ARROW);
		((AbstractArrowItem) (DeferredRegistryHandler.WOOD_ARROW.get())).setItemReference(DeferredRegistryHandler.WOOD_ARROW);
		((AbstractArrowItem) (DeferredRegistryHandler.NETHERITE_ARROW.get())).setItemReference(DeferredRegistryHandler.NETHERITE_ARROW);
		((AbstractArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW);
		((AbstractArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED);
		((AbstractArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN);
		((AbstractArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE);
		((AbstractArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE);
		((AbstractArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW);

		((AbstractArrowItem) (DeferredRegistryHandler.COPPER_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.COPPER_MUSKET_BALL);
		((AbstractArrowItem) (DeferredRegistryHandler.WOOD_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.WOOD_MUSKET_BALL);
		((AbstractArrowItem) (DeferredRegistryHandler.STONE_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.STONE_MUSKET_BALL);
		((AbstractArrowItem) (DeferredRegistryHandler.IRON_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.IRON_MUSKET_BALL);
		((AbstractArrowItem) (DeferredRegistryHandler.GOLD_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.GOLD_MUSKET_BALL);
		((AbstractArrowItem) (DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.DIAMOND_MUSKET_BALL);
		((AbstractArrowItem) (DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.NETHERITE_MUSKET_BALL);
		((AbstractArrowItem) (DeferredRegistryHandler.FLARE.get())).setItemReference(DeferredRegistryHandler.FLARE);
	}

	/**
	 * Event handler for the EntityAttributeCreationEvent.
	 * @param event the <code>EntityAttributeCreationEvent</code> instance
	 */
	@SubscribeEvent
	public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
		event.put(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), AbstractDyingSoldierEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.MINUTEMAN_ENTITY.get(), AbstractMinutemanEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.FIELD_MEDIC_ENTITY.get(), AbstractFieldMedicEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), AbstractWanderingWarriorEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.HANS_ENTITY.get(), HansEntity.registerAttributes().build());
	}
}