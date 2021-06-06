package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.item.Pike;
import com.anonymoushacker1279.immersiveweapons.item.PikeItem.*;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class AddAttributesAfterSetup {

	public static boolean hasCompletedClientSetup = false;

	public static void init() {

		hasCompletedClientSetup = true;

		// Pike Special Attributes
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		ImmutableMultimap<Attribute, AttributeModifier> newAttributes = builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(Pike.ATTACK_REACH_MODIFIER, "Weapon modifier", 0.5d, AttributeModifier.Operation.ADDITION)).build();
		Builder<Attribute, AttributeModifier> combineBuilder = ImmutableMultimap.builder();

		// Wood Pike Item
		combineBuilder.putAll(WoodPikeItem.woodPikeAttributes);
		combineBuilder.putAll(newAttributes);
		WoodPikeItem.woodPikeAttributes = combineBuilder.build();

		// Stone Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(StonePikeItem.stonePikeAttributes);
		combineBuilder.putAll(newAttributes);
		StonePikeItem.stonePikeAttributes = combineBuilder.build();

		// Gold Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(GoldPikeItem.goldPikeAttributes);
		combineBuilder.putAll(newAttributes);
		GoldPikeItem.goldPikeAttributes = combineBuilder.build();

		// Copper Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(CopperPikeItem.copperPikeAttributes);
		combineBuilder.putAll(newAttributes);
		CopperPikeItem.copperPikeAttributes = combineBuilder.build();

		// Iron Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(IronPikeItem.ironPikeAttributes);
		combineBuilder.putAll(newAttributes);
		IronPikeItem.ironPikeAttributes = combineBuilder.build();

		// Diamond Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(DiamondPikeItem.diamondPikeAttributes);
		combineBuilder.putAll(newAttributes);
		DiamondPikeItem.diamondPikeAttributes = combineBuilder.build();

		// Netherite Pike Item
		combineBuilder = ImmutableMultimap.builder();
		combineBuilder.putAll(NetheritePikeItem.netheritePikeAttributes);
		combineBuilder.putAll(newAttributes);
		NetheritePikeItem.netheritePikeAttributes = combineBuilder.build();
	}
}