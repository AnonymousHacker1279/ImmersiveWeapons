package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public record TeslaSynthesizerRecipe(ResourceLocation recipeId,
                                     Ingredient blockIngredient,
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
	 * Get the recipe ID.
	 *
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getId() {
		return recipeId;
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

	public static class Serializer implements RecipeSerializer<TeslaSynthesizerRecipe> {

		/**
		 * Serialize from JSON.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param json     the <code>JsonObject</code> instance
		 * @return TeslaSynthesizerRecipe
		 */
		@Override
		public TeslaSynthesizerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			Ingredient blockIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "block"));
			Ingredient material1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "material1"));
			Ingredient material2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "material2"));
			int cookTime = GsonHelper.getAsInt(json, "cookTime");
			ItemStack result = new ItemStack(ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result")));
			return new TeslaSynthesizerRecipe(recipeId, blockIngredient, material1, material2, result, cookTime);
		}

		/**
		 * Serialize from JSON on the network.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param buffer   the <code>PacketBuffer</code> instance
		 * @return TeslaSynthesizerRecipe
		 */
		@Override
		public TeslaSynthesizerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			Ingredient blockIngredient = Ingredient.fromNetwork(buffer);
			Ingredient material1 = Ingredient.fromNetwork(buffer);
			Ingredient material2 = Ingredient.fromNetwork(buffer);
			int cookTime = buffer.readInt();
			ItemStack result = buffer.readItem();
			return new TeslaSynthesizerRecipe(recipeId, blockIngredient, material1, material2, result, cookTime);
		}

		/**
		 * Serialize to JSON on the network.
		 *
		 * @param buffer the <code>PacketBuffer</code> instance
		 * @param recipe the <code>TeslaSynthesizerRecipe</code> instance
		 */
		@Override
		public void toNetwork(FriendlyByteBuf buffer, TeslaSynthesizerRecipe recipe) {
			recipe.blockIngredient.toNetwork(buffer);
			recipe.material1.toNetwork(buffer);
			recipe.material2.toNetwork(buffer);
			buffer.writeItem(recipe.result);
		}
	}
}