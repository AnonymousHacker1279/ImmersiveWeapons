package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public record AstralCrystalRecipe(ResourceLocation recipeId,
                                  Ingredient primaryMaterial,
                                  Ingredient secondaryMaterial,
                                  ItemStack result,
                                  int resultCount) implements Recipe<Container> {

	/**
	 * Constructor for AstralCrystalRecipe.
	 *
	 * @param recipeId          the <code>ResourceLocation</code> for the recipe
	 * @param primaryMaterial   the first <code>Ingredient</code>
	 * @param secondaryMaterial the number of materials needed for the recipe
	 * @param result            an <code>ItemStack</code> of the result
	 * @param resultCount       the number of results
	 */
	public AstralCrystalRecipe {
	}

	/**
	 * Used to check if a recipe matches current crafting inventory.
	 *
	 * @param container the <code>Container</code> instance
	 * @param level     the current <code>Level</code>
	 * @return boolean
	 */
	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return result;
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

	public Ingredient getPrimaryMaterial() {
		return primaryMaterial;
	}

	public Ingredient getSecondaryMaterial() {
		return secondaryMaterial;
	}

	/**
	 * Get the recipe's result count.
	 *
	 * @return int
	 */
	public int getResultCount() {
		return resultCount;
	}

	/**
	 * Get the toast symbol.
	 *
	 * @return ItemStack
	 */
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.ASTRAL_CRYSTAL.get());
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
	 * @return RecipeSerializer
	 */
	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.ASTRAL_CRYSTAL_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 *
	 * @return RecipeType
	 */
	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.ASTRAL_CRYSTAL_RECIPE_TYPE.get();
	}

	/**
	 * Get the recipe's ingredients.
	 *
	 * @return NonNullList extending Ingredient
	 */
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(primaryMaterial);
		defaultedList.add(secondaryMaterial);
		return defaultedList;
	}

	public static class Serializer implements RecipeSerializer<AstralCrystalRecipe> {
		/**
		 * Serialize from JSON.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param json     the <code>JsonObject</code> instance
		 * @return AstralCrystalRecipe
		 */
		@Override
		public AstralCrystalRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			Ingredient primaryMaterial = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "primaryMaterial"));
			Ingredient secondaryMaterial = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "secondaryMaterial"));
			ItemStack result = new ItemStack(ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result")));
			int resultCount = GsonHelper.getAsInt(json, "resultCount");

			return new AstralCrystalRecipe(recipeId, primaryMaterial, secondaryMaterial, result, resultCount);
		}

		/**
		 * Serialize from JSON on the network.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param buffer   the <code>FriendlyByteBuf</code> instance
		 * @return AstralCrystalRecipe
		 */
		@Override
		public AstralCrystalRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			Ingredient primaryMaterial = Ingredient.fromNetwork(buffer);
			Ingredient secondaryMaterial = Ingredient.fromNetwork(buffer);
			ItemStack result = buffer.readItem();
			int resultCount = buffer.readInt();

			return new AstralCrystalRecipe(recipeId, primaryMaterial, secondaryMaterial, result, resultCount);
		}

		/**
		 * Serialize to JSON on the network.
		 *
		 * @param buffer the <code>FriendlyByteBuf</code> instance
		 * @param recipe the <code>AstralCrystalRecipe</code> instance
		 */
		@Override
		public void toNetwork(FriendlyByteBuf buffer, AstralCrystalRecipe recipe) {
			recipe.primaryMaterial.toNetwork(buffer);
			recipe.secondaryMaterial.toNetwork(buffer);
			buffer.writeItem(recipe.result);
			buffer.writeInt(recipe.resultCount);
		}
	}
}