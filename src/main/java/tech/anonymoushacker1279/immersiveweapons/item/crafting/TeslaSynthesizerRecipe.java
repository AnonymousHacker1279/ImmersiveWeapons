package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public record TeslaSynthesizerRecipe(Ingredient blockIngredient,
                                     Ingredient material1,
                                     Ingredient material2,
                                     ItemStack result,
                                     int cookTime) implements Recipe<Container> {

	/**
	 * Get the cook time.
	 *
	 * @return int
	 */
	public int getCookTime() {
		return cookTime;
	}

	@Override
	public boolean matches(Container container, Level level) {
		return blockIngredient.test(container.getItem(0))
				&& material1.test(container.getItem(1))
				&& material2.test(container.getItem(2));
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		ItemStack resultStack = result.copy();
		CompoundTag compoundTag = container.getItem(4).getTag();
		if (compoundTag != null) {
			resultStack.setTag(compoundTag.copy());
		}

		return resultStack;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return result.copy();
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height.
	 *
	 * @param width  the width of the grid
	 * @param height the height of the grid
	 * @return boolean
	 */
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	/**
	 * Get the toast symbol.
	 *
	 * @return ItemStack
	 */
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.TESLA_SYNTHESIZER.get());
	}

	/**
	 * Get the recipe serializer.
	 *
	 * @return IRecipeSerializer
	 */
	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.TESLA_SYNTHESIZER_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 *
	 * @return IRecipeType
	 */
	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.TESLA_SYNTHESIZER_RECIPE_TYPE.get();
	}

	/**
	 * Get the recipe's ingredients.
	 *
	 * @return NonNullList extending Ingredient
	 */
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

	public static class Serializer implements RecipeSerializer<TeslaSynthesizerRecipe> {

		private static final Codec<TeslaSynthesizerRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Ingredient.CODEC.fieldOf("block").forGetter(recipe -> recipe.blockIngredient),
								Ingredient.CODEC.fieldOf("material1").forGetter(recipe -> recipe.material1),
								Ingredient.CODEC.fieldOf("material2").forGetter(recipe -> recipe.material2),
								CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
								Codec.INT.fieldOf("cookTime").forGetter(recipe -> recipe.cookTime)
						)
						.apply(instance, TeslaSynthesizerRecipe::new)
		);

		@Override
		public Codec<TeslaSynthesizerRecipe> codec() {
			return CODEC;
		}

		@Override
		public @Nullable TeslaSynthesizerRecipe fromNetwork(FriendlyByteBuf byteBuf) {
			Ingredient blockIngredient = Ingredient.fromNetwork(byteBuf);
			Ingredient material1 = Ingredient.fromNetwork(byteBuf);
			Ingredient material2 = Ingredient.fromNetwork(byteBuf);
			int cookTime = byteBuf.readInt();
			ItemStack result = byteBuf.readItem();
			return new TeslaSynthesizerRecipe(blockIngredient, material1, material2, result, cookTime);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, TeslaSynthesizerRecipe recipe) {
			recipe.blockIngredient.toNetwork(buffer);
			recipe.material1.toNetwork(buffer);
			recipe.material2.toNetwork(buffer);
			buffer.writeItem(recipe.result);
		}
	}
}