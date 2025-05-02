package tech.anonymoushacker1279.immersiveweapons.world.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class FoodItemProperties {

	public static final FoodProperties CHOCOLATE_BAR = new FoodProperties.Builder()
			.nutrition(2)
			.saturationModifier(0.2F)
			.build();
	public static final FoodProperties MRE = new FoodProperties.Builder()
			.nutrition(11)
			.saturationModifier(12.6f)
			.build();
	public static final FoodProperties MOLDY_BREAD = new FoodProperties.Builder()
			.nutrition(2)
			.saturationModifier(0.2F)
			.build();

	public static final Consumable CHOCOLATE_BAR_CONSUMABLE = Consumables.defaultFood()
			.consumeSeconds(0.8f)
			.build();
	public static final Consumable MOLDY_BREAD_CONSUMABLE = Consumables.defaultFood()
			.onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 400, 0)))
			.build();
}