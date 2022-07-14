package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public record BarrelTapRecipe(ResourceLocation recipeId,
                              Ingredient material,
                              int materialCount,
                              ItemStack result) implements Recipe<Container> {

	/**
	 * Constructor for BarrelTapRecipe.
	 *
	 * @param recipeId      the <code>ResourceLocation</code> for the recipe
	 * @param material      the first <code>Ingredient</code>
	 * @param materialCount the number of materials needed for the recipe
	 * @param result        an <code>ItemStack</code> formed from the material
	 */
	public BarrelTapRecipe {
	}

	/**
	 * Used to check if a recipe matches current crafting inventory.
	 *
	 * @param container the <code>Container</code> instance
	 * @param level     the current <code>Level</code>
	 * @return boolean
	 */
	@Override
	public boolean matches(@NotNull Container container, @NotNull Level level) {
		return false;
	}

	/**
	 * Returns an Item that is the result of this recipe.
	 *
	 * @param container the <code>Container</code> instance
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack assemble(@NotNull Container container) {
		return result;
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

	public Ingredient getMaterial() {
		return material;
	}

	public int getMaterialCount() {
		return materialCount;
	}

	@Override
	public @NotNull ItemStack getResultItem() {
		return result.copy();
	}

	/**
	 * Get the toast symbol.
	 *
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack getToastSymbol() {
		return new ItemStack(DeferredRegistryHandler.BARREL_TAP.get());
	}

	/**
	 * Get the recipe ID.
	 *
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getId() {
		return recipeId;
	}

	/**
	 * Get the recipe serializer.
	 *
	 * @return RecipeSerializer
	 */
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return DeferredRegistryHandler.BARREL_TAP_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 *
	 * @return RecipeType
	 */
	@Override
	public @NotNull RecipeType<?> getType() {
		return DeferredRegistryHandler.BARREL_TAP_RECIPE_TYPE.get();
	}

	/**
	 * Get the recipe's ingredients.
	 *
	 * @return NonNullList extending Ingredient
	 */
	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(material);
		return defaultedList;
	}

	public static class Serializer implements RecipeSerializer<BarrelTapRecipe> {
		/**
		 * Serialize from JSON.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param json     the <code>JsonObject</code> instance
		 * @return SmallPartsRecipe
		 */
		@Override
		public @NotNull BarrelTapRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
			Ingredient material = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "material"));
			int materialCount = GsonHelper.getAsInt(json, "materialCount");
			ItemStack result = new ItemStack(ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result")));

			return new BarrelTapRecipe(recipeId, material, materialCount, result);
		}

		/**
		 * Serialize from JSON on the network.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param buffer   the <code>FriendlyByteBuf</code> instance
		 * @return BarrelTapRecipe
		 */
		@Override
		public BarrelTapRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
			Ingredient material = Ingredient.fromNetwork(buffer);
			int materialCount = buffer.readInt();
			ItemStack result = buffer.readItem();

			return new BarrelTapRecipe(recipeId, material, materialCount, result);
		}

		/**
		 * Serialize to JSON on the network.
		 *
		 * @param buffer the <code>FriendlyByteBuf</code> instance
		 * @param recipe the <code>BarrelTapRecipe</code> instance
		 */
		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, BarrelTapRecipe recipe) {
			recipe.material.toNetwork(buffer);
			buffer.writeInt(recipe.materialCount);
			buffer.writeItem(recipe.result);
		}
	}
}