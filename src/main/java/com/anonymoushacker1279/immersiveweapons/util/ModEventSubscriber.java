package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractDyingSoldierEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.AbstractWanderingWarriorEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.HansEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractFieldMedicEntity;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractMinutemanEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrowItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		((CustomArrowItem) (DeferredRegistryHandler.COPPER_ARROW.get())).setItemReference(DeferredRegistryHandler.COPPER_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.IRON_ARROW.get())).setItemReference(DeferredRegistryHandler.IRON_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.DIAMOND_ARROW.get())).setItemReference(DeferredRegistryHandler.DIAMOND_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.GOLD_ARROW.get())).setItemReference(DeferredRegistryHandler.GOLD_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.STONE_ARROW.get())).setItemReference(DeferredRegistryHandler.STONE_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.WOOD_ARROW.get())).setItemReference(DeferredRegistryHandler.WOOD_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.NETHERITE_ARROW.get())).setItemReference(DeferredRegistryHandler.NETHERITE_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED);
		((CustomArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN);
		((CustomArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE);
		((CustomArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE);
		((CustomArrowItem) (DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get())).setItemReference(DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW);

		((CustomArrowItem) (DeferredRegistryHandler.COPPER_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.COPPER_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.WOOD_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.WOOD_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.STONE_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.STONE_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.IRON_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.IRON_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.GOLD_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.GOLD_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.DIAMOND_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.NETHERITE_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.FLARE.get())).setItemReference(DeferredRegistryHandler.FLARE);
	}

	@SubscribeEvent
	public static void entityAttributeCreationEvent(final EntityAttributeCreationEvent event) {
		event.put(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), AbstractDyingSoldierEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.MINUTEMAN_ENTITY.get(), AbstractMinutemanEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.FIELD_MEDIC_ENTITY.get(), AbstractFieldMedicEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), AbstractWanderingWarriorEntity.registerAttributes().build());
		event.put(DeferredRegistryHandler.HANS_ENTITY.get(), HansEntity.registerAttributes().build());
	}
}