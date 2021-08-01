package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.item.pike.*;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
		ImmutableMultimap<Attribute, AttributeModifier> newAttributes = builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(PikeItem.ATTACK_REACH_MODIFIER, "Weapon modifier", 0.5d, AttributeModifier.Operation.ADDITION)).build();
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
	}
}