package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

public class PistonCrushingRecipe extends SingleItemRecipe {

	private final int minCount;
	private final int maxCount;

	public PistonCrushingRecipe(String group, Ingredient input, ItemStack result, int minCount, int maxCount) {
		super(group, input, result);
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
		return RecipeBookCategories.CRAFTING_MISC; // TODO: custom category
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	/**
	 * Get a random drop amount based on the min and max count (inclusive).
	 */
	public int getRandomDropAmount() {
		return minCount + (int) (Math.random() * ((maxCount - minCount) + 1));
	}

	public interface Factory<T extends PistonCrushingRecipe> {
		T create(String group, Ingredient blockItem, ItemStack result, int minCount, int maxCount);
	}

	public static class Serializer<T extends PistonCrushingRecipe> implements RecipeSerializer<T> {

		private final MapCodec<T> codec;
		private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

		public Serializer(PistonCrushingRecipe.Factory<T> factory) {
			codec = RecordCodecBuilder.mapCodec(
					instance -> instance.group(
									Codec.STRING.optionalFieldOf("group", "").forGetter(SingleItemRecipe::group),
									Ingredient.CODEC.fieldOf("blockItem").forGetter(SingleItemRecipe::input),
									ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(SingleItemRecipe::result),
									Codec.INT.fieldOf("minCount").forGetter(PistonCrushingRecipe::getMinCount),
									Codec.INT.fieldOf("maxCount").forGetter(PistonCrushingRecipe::getMaxCount)
							)
							.apply(instance, factory::create)
			);

			streamCodec = StreamCodec.composite(
					ByteBufCodecs.STRING_UTF8,
					SingleItemRecipe::group,
					Ingredient.CONTENTS_STREAM_CODEC,
					SingleItemRecipe::input,
					ItemStack.STREAM_CODEC,
					SingleItemRecipe::result,
					ByteBufCodecs.INT,
					PistonCrushingRecipe::getMinCount,
					ByteBufCodecs.INT,
					PistonCrushingRecipe::getMaxCount,
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