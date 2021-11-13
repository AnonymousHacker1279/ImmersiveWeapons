package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractDyingSoldierEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractWanderingWarriorEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.HansEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.LavaRevenantEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractFieldMedicEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractMinutemanEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
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
		DeferredRegistryHandler.COPPER_ARROW.get().setItemReference(DeferredRegistryHandler.COPPER_ARROW);
		DeferredRegistryHandler.IRON_ARROW.get().setItemReference(DeferredRegistryHandler.IRON_ARROW);
		DeferredRegistryHandler.COBALT_ARROW.get().setItemReference(DeferredRegistryHandler.COBALT_ARROW);
		DeferredRegistryHandler.DIAMOND_ARROW.get().setItemReference(DeferredRegistryHandler.DIAMOND_ARROW);
		DeferredRegistryHandler.GOLD_ARROW.get().setItemReference(DeferredRegistryHandler.GOLD_ARROW);
		DeferredRegistryHandler.STONE_ARROW.get().setItemReference(DeferredRegistryHandler.STONE_ARROW);
		DeferredRegistryHandler.WOOD_ARROW.get().setItemReference(DeferredRegistryHandler.WOOD_ARROW);
		DeferredRegistryHandler.NETHERITE_ARROW.get().setItemReference(DeferredRegistryHandler.NETHERITE_ARROW);
		DeferredRegistryHandler.SMOKE_BOMB_ARROW.get().setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW);
		DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED.get().setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED);
		DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get().setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN);
		DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get().setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE);
		DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get().setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE);
		DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get().setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW);

		DeferredRegistryHandler.COPPER_MUSKET_BALL.get().setItemReference(DeferredRegistryHandler.COPPER_MUSKET_BALL);
		DeferredRegistryHandler.WOOD_MUSKET_BALL.get().setItemReference(DeferredRegistryHandler.WOOD_MUSKET_BALL);
		DeferredRegistryHandler.STONE_MUSKET_BALL.get().setItemReference(DeferredRegistryHandler.STONE_MUSKET_BALL);
		DeferredRegistryHandler.IRON_MUSKET_BALL.get().setItemReference(DeferredRegistryHandler.IRON_MUSKET_BALL);
		DeferredRegistryHandler.COBALT_MUSKET_BALL.get().setItemReference(DeferredRegistryHandler.COBALT_MUSKET_BALL);
		DeferredRegistryHandler.GOLD_MUSKET_BALL.get().setItemReference(DeferredRegistryHandler.GOLD_MUSKET_BALL);
		DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get().setItemReference(DeferredRegistryHandler.DIAMOND_MUSKET_BALL);
		DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get().setItemReference(DeferredRegistryHandler.NETHERITE_MUSKET_BALL);
		DeferredRegistryHandler.FLARE.get().setItemReference(DeferredRegistryHandler.FLARE);
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
		event.put(DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(), LavaRevenantEntity.registerAttributes().build());
	}
}