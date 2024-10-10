package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public record TeslaSynthesizerRecipe(String group, Ingredient blockIngredient, Ingredient material1,
                                     Ingredient material2, ItemStack result,
                                     int cookTime) implements Recipe<RecipeInput> {

	@Override
	public boolean matches(RecipeInput input, Level level) {
		return blockIngredient.test(input.getItem(0))
				&& material1.test(input.getItem(1))
				&& material2.test(input.getItem(2));
	}

	@Override
	public ItemStack assemble(RecipeInput input, Provider registries) {
		return result;
	}

	@Override
	public ItemStack getResultItem(Provider provider) {
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.TESLA_SYNTHESIZER.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.TESLA_SYNTHESIZER_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.TESLA_SYNTHESIZER_RECIPE_TYPE.get();
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(blockIngredient);
		defaultedList.add(material1);
		defaultedList.add(material2);
		return defaultedList;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public int getCookTime() {
		return cookTime;
	}

	public interface Factory<T extends TeslaSynthesizerRecipe> {
		T create(String group, Ingredient blockIngredient, Ingredient material1, Ingredient material2, ItemStack result, int cookTime);
	}

	public static class Serializer implements RecipeSerializer<TeslaSynthesizerRecipe> {

		private static final MapCodec<TeslaSynthesizerRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC.fieldOf("block").forGetter(recipe -> recipe.blockIngredient),
								Ingredient.CODEC.fieldOf("material1").forGetter(recipe -> recipe.material1),
								Ingredient.CODEC.fieldOf("material2").forGetter(recipe -> recipe.material2),
								ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
								Codec.INT.fieldOf("cookTime").forGetter(recipe -> recipe.cookTime)
						)
						.apply(instance, TeslaSynthesizerRecipe::new)
		);

		private static final StreamCodec<RegistryFriendlyByteBuf, TeslaSynthesizerRecipe> STREAM_CODEC = StreamCodec.of(
				TeslaSynthesizerRecipe.Serializer::toNetwork, TeslaSynthesizerRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<TeslaSynthesizerRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, TeslaSynthesizerRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static TeslaSynthesizerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			Ingredient blockIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			Ingredient material1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			Ingredient material2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			int cookTime = buffer.readInt();
			ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);

			return new TeslaSynthesizerRecipe(group, blockIngredient, material1, material2, result, cookTime);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, TeslaSynthesizerRecipe recipe) {
			buffer.writeUtf(recipe.group);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.blockIngredient);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.material1);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.material2);
			buffer.writeInt(recipe.cookTime);
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
		}
	}
}