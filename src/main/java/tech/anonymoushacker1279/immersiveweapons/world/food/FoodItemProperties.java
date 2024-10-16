package tech.anonymoushacker1279.immersiveweapons.world.food;

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
}