package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

public class BarrelTapRecipe extends SingleItemRecipe {

	public static final MapCodec<BarrelTapRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
							Ingredient.CODEC.fieldOf("material").forGetter(SingleItemRecipe::input),
							Codec.INT.fieldOf("materialCount").forGetter(BarrelTapRecipe::getMaterialCount),
							ItemStackTemplate.CODEC.fieldOf("result").forGetter(SingleItemRecipe::result)
					)
					.apply(instance, BarrelTapRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, BarrelTapRecipe> STREAM_CODEC = StreamCodec.composite(
			Ingredient.CONTENTS_STREAM_CODEC,
			SingleItemRecipe::input,
			ByteBufCodecs.INT,
			BarrelTapRecipe::getMaterialCount,
			ItemStackTemplate.STREAM_CODEC,
			SingleItemRecipe::result,
			BarrelTapRecipe::new
	);
	public static final RecipeSerializer<BarrelTapRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final int materialCount;

	public BarrelTapRecipe(Ingredient material, int materialCount, ItemStackTemplate result) {
		super(new CommonInfo(false), material, result);
		this.materialCount = materialCount;
	}

	public int getMaterialCount() {
		return materialCount;
	}

	@Override
	public boolean matches(SingleRecipeInput input, Level level) {
		return super.matches(input, level) && input.getItem(0).getCount() >= materialCount;
	}

	@Override
	public RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
		return RecipeSerializerRegistry.BARREL_TAP_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<? extends SingleItemRecipe> getType() {
		return RecipeTypeRegistry.BARREL_TAP_RECIPE_TYPE.get();
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
}