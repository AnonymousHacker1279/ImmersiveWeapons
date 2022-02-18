package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.item.gauntlet.*;
import com.anonymoushacker1279.immersiveweapons.item.materials.CustomArmorMaterials;
import com.anonymoushacker1279.immersiveweapons.item.pike.*;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;

public class PostSetupHandler {

	public static boolean hasCompletedClientSetup = false;

	/**
	 * Initialize attributes which must be applied after setup.
	 */
	public static void init() {

		hasCompletedClientSetup = true;

		// Pike Special Attributes
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		ImmutableMultimap<Attribute, AttributeModifier> newAttributes = builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(GeneralUtilities.ATTACK_REACH_MODIFIER, "Weapon modifier", 0.5d, AttributeModifier.Operation.ADDITION)).build();
		Builder<Attribute, AttributeModifier> combineBuilder = ImmutableMultimap.builder();

		// Wood Pike Item
		combineBuilder.putAll(WoodPikeItem.pikeAttributes);
		combineBuilder.putAll(newAttributes);
		WoodPikeItem.pikeAttributes = combineBuilder.build();

		// Stone Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(StonePikeItem.pikeAttributes);
		combineBuilder.putAll(newAttributes);
		StonePikeItem.pikeAttributes = combineBuilder.build();

		// Gold Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(GoldPikeItem.pikeAttributes);
		combineBuilder.putAll(newAttributes);
		GoldPikeItem.pikeAttributes = combineBuilder.build();

		// Copper Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(CopperPikeItem.pikeAttributes);
		combineBuilder.putAll(newAttributes);
		CopperPikeItem.pikeAttributes = combineBuilder.build();

		// Iron Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(IronPikeItem.pikeAttributes);
		combineBuilder.putAll(newAttributes);
		IronPikeItem.pikeAttributes = combineBuilder.build();

		// Cobalt Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(CobaltPikeItem.pikeAttributes);
		combineBuilder.putAll(newAttributes);
		CobaltPikeItem.pikeAttributes = combineBuilder.build();

		// Diamond Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(DiamondPikeItem.pikeAttributes);
		combineBuilder.putAll(newAttributes);
		DiamondPikeItem.pikeAttributes = combineBuilder.build();

		// Netherite Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(NetheritePikeItem.pikeAttributes);
		combineBuilder.putAll(newAttributes);
		NetheritePikeItem.pikeAttributes = combineBuilder.build();

		// Gauntlet Special Attributes
		builder = ImmutableMultimap.builder();
		newAttributes = builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(GeneralUtilities.ATTACK_REACH_MODIFIER, "Weapon modifier", -2.0d, Operation.ADDITION)).build();
		combineBuilder = ImmutableMultimap.builder();

		// Wood Gauntlet Item
		combineBuilder.putAll(WoodGauntletItem.gauntletAttributes);
		combineBuilder.putAll(newAttributes);
		WoodGauntletItem.gauntletAttributes = combineBuilder.build();

		// Stone Gauntlet Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(StoneGauntletItem.gauntletAttributes);
		combineBuilder.putAll(newAttributes);
		StoneGauntletItem.gauntletAttributes = combineBuilder.build();

		// Gold Gauntlet Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(GoldGauntletItem.gauntletAttributes);
		combineBuilder.putAll(newAttributes);
		GoldGauntletItem.gauntletAttributes = combineBuilder.build();

		// Copper Gauntlet Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(CopperGauntletItem.gauntletAttributes);
		combineBuilder.putAll(newAttributes);
		CopperGauntletItem.gauntletAttributes = combineBuilder.build();

		// Iron Gauntlet Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(IronGauntletItem.gauntletAttributes);
		combineBuilder.putAll(newAttributes);
		IronGauntletItem.gauntletAttributes = combineBuilder.build();

		// Cobalt Gauntlet Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(CobaltGauntletItem.gauntletAttributes);
		combineBuilder.putAll(newAttributes);
		CobaltGauntletItem.gauntletAttributes = combineBuilder.build();

		// Diamond Gauntlet Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(DiamondGauntletItem.gauntletAttributes);
		combineBuilder.putAll(newAttributes);
		DiamondGauntletItem.gauntletAttributes = combineBuilder.build();

		// Netherite Gauntlet Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(NetheriteGauntletItem.gauntletAttributes);
		combineBuilder.putAll(newAttributes);
		NetheriteGauntletItem.gauntletAttributes = combineBuilder.build();

		// Add custom logs to be stripped in AxeItem
		AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES).put(DeferredRegistryHandler.BURNED_OAK_LOG.get(), DeferredRegistryHandler.STRIPPED_BURNED_OAK_LOG.get()).put(DeferredRegistryHandler.BURNED_OAK_WOOD.get(), DeferredRegistryHandler.STRIPPED_BURNED_OAK_WOOD.get()).build();

		// Set custom armor equip sounds, as these don't exist during the initialization of materials
		CustomArmorMaterials.TESLA.setEquipSound(DeferredRegistryHandler.TESLA_ARMOR_EQUIP.get());
	}
}