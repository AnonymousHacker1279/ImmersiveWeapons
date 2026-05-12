package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

public class PistonCrushingRecipe extends SingleItemRecipe {

	public static final MapCodec<PistonCrushingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
							Ingredient.CODEC.fieldOf("blockItem").forGetter(SingleItemRecipe::input),
							ItemStackTemplate.CODEC.fieldOf("result").forGetter(SingleItemRecipe::result),
							Codec.INT.fieldOf("minCount").forGetter(PistonCrushingRecipe::getMinCount),
							Codec.INT.fieldOf("maxCount").forGetter(PistonCrushingRecipe::getMaxCount)
					)
					.apply(instance, PistonCrushingRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, PistonCrushingRecipe> STREAM_CODEC = StreamCodec.composite(
			Ingredient.CONTENTS_STREAM_CODEC,
			SingleItemRecipe::input,
			ItemStackTemplate.STREAM_CODEC,
			SingleItemRecipe::result,
			ByteBufCodecs.INT,
			PistonCrushingRecipe::getMinCount,
			ByteBufCodecs.INT,
			PistonCrushingRecipe::getMaxCount,
			PistonCrushingRecipe::new
	);
	public static final RecipeSerializer<PistonCrushingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final int minCount;
	private final int maxCount;

	public PistonCrushingRecipe(Ingredient input, ItemStackTemplate result, int minCount, int maxCount) {
		super(new CommonInfo(false), input, result);
		this.minCount = minCount;
		this.maxCount = maxCount;
	}

	public int getMinCount() {
		return minCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	@Override
	public RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
		return RecipeSerializerRegistry.PISTON_CRUSHING_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<? extends SingleItemRecipe> getType() {
		return RecipeTypeRegistry.PISTON_CRUSHING_RECIPE_TYPE.get();
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public String group() {
		return "";
	}

	/**
	 * Get a random drop amount based on the min and max count (inclusive).
	 */
	public int getRandomDropAmount() {
		return minCount + (int) (Math.random() * ((maxCount - minCount) + 1));
	}
}