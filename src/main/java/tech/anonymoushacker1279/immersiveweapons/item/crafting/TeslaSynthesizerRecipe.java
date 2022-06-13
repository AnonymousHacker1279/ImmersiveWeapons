package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public record TeslaSynthesizerRecipe(ResourceLocation recipeId,
                                     Ingredient blockIngredient,
                                     Ingredient material1,
                                     Ingredient material2,
                                     ItemStack result,
                                     int cookTime) implements Recipe<Container> {

	/**
	 * Constructor for SmallPartsRecipe.
	 *
	 * @param recipeId        the <code>ResourceLocation</code> for the recipe
	 * @param blockIngredient the first <code>Ingredient</code>
	 * @param material1       the second <code>Ingredient</code>
	 * @param material2       the third <code>Ingredient</code>
	 * @param result          the result <code>ItemStack</code>
	 * @param cookTime        the cooking time
	 */
	public TeslaSynthesizerRecipe {
	}

	/**
	 * Get the cook time.
	 *
	 * @return int
	 */
	public int getCookTime() {
		return cookTime;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory.
	 *
	 * @param inv     the <code>IInventory</code> instance
	 * @param worldIn the current <code>World</code>
	 * @return boolean
	 */
	@Override
	public boolean matches(Container inv, @NotNull Level worldIn) {
		return blockIngredient.test(inv.getItem(0)) && material1.test(inv.getItem(1)) && material2.test(inv.getItem(2));
	}

	/**
	 * Returns an Item that is the result of this recipe.
	 *
	 * @param inv the <code>IInventory</code> instance
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack assemble(Container inv) {
		ItemStack itemstack = result.copy();
		CompoundTag compoundTag = inv.getItem(4).getTag();
		if (compoundTag != null) {
			itemstack.setTag(compoundTag.copy());
		}

		return itemstack;
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
		return width * height >= 2;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 *
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack getResultItem() {
		return result;
	}

	/**
	 * Get the toast symbol.
	 *
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack getToastSymbol() {
		return new ItemStack(DeferredRegistryHandler.TESLA_SYNTHESIZER.get());
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
	 * @return IRecipeSerializer
	 */
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return DeferredRegistryHandler.TESLA_SYNTHESIZER_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 *
	 * @return IRecipeType
	 */
	@Override
	public @NotNull RecipeType<?> getType() {
		return CustomRecipeTypes.TESLA_SYNTHESIZER;
	}

	/**
	 * Get the recipe's ingredients.
	 *
	 * @return NonNullList extending Ingredient
	 */
	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(blockIngredient);
		defaultedList.add(material1);
		defaultedList.add(material2);
		return defaultedList;
	}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<TeslaSynthesizerRecipe> {
		/**
		 * Serialize from JSON.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param json     the <code>JsonObject</code> instance
		 * @return TeslaSynthesizerRecipe
		 */
		@Override
		public @NotNull TeslaSynthesizerRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
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
		public TeslaSynthesizerRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
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
		public void toNetwork(@NotNull FriendlyByteBuf buffer, TeslaSynthesizerRecipe recipe) {
			recipe.blockIngredient.toNetwork(buffer);
			recipe.material1.toNetwork(buffer);
			recipe.material2.toNetwork(buffer);
			buffer.writeItem(recipe.result);
		}
	}
}