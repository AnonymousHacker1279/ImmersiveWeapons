package tech.anonymoushacker1279.immersiveweapons.world.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodItemProperties {

	public static final FoodProperties CHOCOLATE_BAR = new FoodProperties.Builder()
			.nutrition(2)
			.saturationModifier(0.2F)
			.fast()
			.build();
	public static final FoodProperties MRE = new FoodProperties.Builder()
			.nutrition(11)
			.saturationModifier(12.6f)
			.build();
	public static final FoodProperties MOLDY_BREAD = new FoodProperties.Builder()
			.nutrition(2)
			.saturationModifier(0.2F)
			.effect(() -> new MobEffectInstance(MobEffects.POISON, 400, 0), 0.4f)
			.build();
}