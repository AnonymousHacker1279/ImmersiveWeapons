package com.anonymoushacker1279.immersiveweapons.item.crafting;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
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

public class TeslaSynthesizerRecipe implements Recipe<Container> {

	private final int cookTime;
	private final Ingredient blockIngredient;
	private final Ingredient material1;
	private final Ingredient material2;
	private final ItemStack result;
	private final ResourceLocation recipeId;

	/**
	 * Constructor for SmallPartsRecipe.
	 * @param recipeId the <code>ResourceLocation</code> for the recipe
	 * @param blockIngredient the first <code>Ingredient</code>
	 * @param material1 the second <code>Ingredient</code>
	 * @param material2 the third <code>Ingredient</code>
	 * @param result the result <code>ItemStack</code>
	 * @param cookTime the cooking time
	 */
	TeslaSynthesizerRecipe(ResourceLocation recipeId, Ingredient blockIngredient, Ingredient material1, Ingredient material2, ItemStack result, int cookTime) {
		this.recipeId = recipeId;
		this.blockIngredient = blockIngredient;
		this.material1 = material1;
		this.material2 = material2;
		this.result = result;
		this.cookTime = cookTime;
	}

	/**
	 * Get the cook time.
	 * @return int
	 */
	public int getCookTime() {
		return cookTime;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory.
	 * @param inv the <code>IInventory</code> instance
	 * @param worldIn the current <code>World</code>
	 * @return boolean
	 */
	@Override
	public boolean matches(Container inv, Level worldIn) {
		return blockIngredient.test(inv.getItem(0)) && material1.test(inv.getItem(1)) && material2.test(inv.getItem(2));
	}

	/**
	 * Returns an Item that is the result of this recipe.
	 * @param inv the <code>IInventory</code> instance
	 * @return ItemStack
	 */
	@Override
	public ItemStack assemble(Container inv) {
		ItemStack itemstack = result.copy();
		CompoundTag compoundnbt = inv.getItem(4).getTag();
		if (compoundnbt != null) {
			itemstack.setTag(compoundnbt.copy());
		}

		return itemstack;
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height.
	 * @param width the width of the grid
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
	 * @return ItemStack
	 */
	@Override
	public ItemStack getResultItem() {
		return result;
	}

	/**
	 * Get the toast symbol.
	 * @return ItemStack
	 */
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(DeferredRegistryHandler.TESLA_SYNTHESIZER.get());
	}

	/**
	 * Get the recipe ID.
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getId() {
		return recipeId;
	}

	/**
	 * Get the recipe serializer.
	 * @return IRecipeSerializer
	 */
	@Override
	public RecipeSerializer<?> getSerializer() {
		return DeferredRegistryHandler.TESLA_SYNTHEZISER_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 * @return IRecipeType
	 */
	@Override
	public RecipeType<?> getType() {
		return ICustomRecipeType.TESLA_SYNTHESIZER;
	}

	/**
	 * Get the recipe's ingredients.
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

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<TeslaSynthesizerRecipe> {
		/**
		 * Serialize from JSON.
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param json the <code>JsonObject</code> instance
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
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param buffer the <code>PacketBuffer</code> instance
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