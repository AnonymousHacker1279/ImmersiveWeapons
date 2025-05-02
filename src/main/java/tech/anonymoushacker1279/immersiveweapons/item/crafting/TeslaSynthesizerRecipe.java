package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.input.TeslaSynthesizerRecipeInput;

public class TeslaSynthesizerRecipe implements Recipe<TeslaSynthesizerRecipeInput> {

	private final String group;
	private final Ingredient material1;
	private final Ingredient material2;
	private final Ingredient material3;
	private final ItemStack result;
	private final int cookTime;

	@Nullable
	private PlacementInfo placementInfo;

	public TeslaSynthesizerRecipe(String group, Ingredient material1, Ingredient material2,
	                              Ingredient material3, ItemStack result, int cookTime) {
		this.group = group;
		this.material1 = material1;
		this.material2 = material2;
		this.material3 = material3;
		this.result = result;
		this.cookTime = cookTime;
	}

	public String group() {
		return group;
	}

	public ItemStack result() {
		return result;
	}

	public Ingredient material1() {
		return material1;
	}

	public Ingredient material2() {
		return material2;
	}

	public Ingredient material3() {
		return material3;
	}

	public int getCookTime() {
		return cookTime;
	}

	@Override
	public RecipeSerializer<TeslaSynthesizerRecipe> getSerializer() {
		return RecipeSerializerRegistry.TESLA_SYNTHESIZER_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<TeslaSynthesizerRecipe> getType() {
		return RecipeTypeRegistry.TESLA_SYNTHESIZER_RECIPE_TYPE.get();
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;  // TODO: custom category
	}

	@Override
	public boolean matches(TeslaSynthesizerRecipeInput input, Level level) {
		return material1.test(input.getItem(0)) && material2.test(input.getItem(1)) && material3.test(input.getItem(2));
	}

	@Override
	public ItemStack assemble(TeslaSynthesizerRecipeInput input, HolderLookup.Provider registries) {
		return result;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public PlacementInfo placementInfo() {
		if (placementInfo == null) {
			placementInfo = PlacementInfo.create(material1);
		}

		return placementInfo;
	}

	public interface Factory<T extends TeslaSynthesizerRecipe> {
		T create(String group, Ingredient material1, Ingredient material2, Ingredient material3, ItemStack result, int cookTime);
	}

	public static class Serializer<T extends TeslaSynthesizerRecipe> implements RecipeSerializer<T> {

		private final MapCodec<T> codec;
		private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

		public Serializer(TeslaSynthesizerRecipe.Factory<T> factory) {
			codec = RecordCodecBuilder.mapCodec(
					instance -> instance.group(
									Codec.STRING.optionalFieldOf("group", "").forGetter(TeslaSynthesizerRecipe::group),
									Ingredient.CODEC.fieldOf("material1").forGetter(TeslaSynthesizerRecipe::material1),
									Ingredient.CODEC.fieldOf("material2").forGetter(TeslaSynthesizerRecipe::material2),
									Ingredient.CODEC.fieldOf("material3").forGetter(TeslaSynthesizerRecipe::material3),
									ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(TeslaSynthesizerRecipe::result),
									Codec.INT.fieldOf("cookTime").forGetter(TeslaSynthesizerRecipe::getCookTime)
							)
							.apply(instance, factory::create)
			);

			streamCodec = StreamCodec.composite(
					ByteBufCodecs.STRING_UTF8,
					TeslaSynthesizerRecipe::group,
					Ingredient.CONTENTS_STREAM_CODEC,
					TeslaSynthesizerRecipe::material1,
					Ingredient.CONTENTS_STREAM_CODEC,
					TeslaSynthesizerRecipe::material2,
					Ingredient.CONTENTS_STREAM_CODEC,
					TeslaSynthesizerRecipe::material3,
					ItemStack.STREAM_CODEC,
					TeslaSynthesizerRecipe::result,
					ByteBufCodecs.INT,
					TeslaSynthesizerRecipe::getCookTime,
					factory::create
			);
		}

		@Override
		public MapCodec<T> codec() {
			return this.codec;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
			return this.streamCodec;
		}
	}
}