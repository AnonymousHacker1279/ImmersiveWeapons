package com.anonymoushacker1279.immersiveweapons.item.crafting;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class TeslaSynthesizerRecipe implements IRecipe<IInventory> {

	private static int cookTime = 0;
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
		TeslaSynthesizerRecipe.cookTime = cookTime;
	}

	/**
	 * Get the cook time.
	 * @return int
	 */
	public static int getCookTime() {
		return cookTime;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory.
	 * @param inv the <code>IInventory</code> instance
	 * @param worldIn the current <code>World</code>
	 * @return boolean
	 */
	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return blockIngredient.test(inv.getItem(0)) && material1.test(inv.getItem(1)) && material2.test(inv.getItem(2));
	}

	/**
	 * Returns an Item that is the result of this recipe.
	 * @param inv the <code>IInventory</code> instance
	 * @return ItemStack
	 */
	@Override
	public ItemStack assemble(IInventory inv) {
		ItemStack itemstack = result.copy();
		CompoundNBT compoundnbt = inv.getItem(4).getTag();
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
	public IRecipeSerializer<?> getSerializer() {
		return DeferredRegistryHandler.TESLA_SYNTHEZISER_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 * @return IRecipeType
	 */
	@Override
	public IRecipeType<?> getType() {
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

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TeslaSynthesizerRecipe> {
		/**
		 * Serialize from JSON.
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param json the <code>JsonObject</code> instance
		 * @return TeslaSynthesizerRecipe
		 */
		@Override
		public TeslaSynthesizerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			Ingredient blockIngredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "block"));
			Ingredient material1 = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "material1"));
			Ingredient material2 = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "material2"));
			TeslaSynthesizerRecipe.cookTime = JSONUtils.getAsInt(json, "cookTime");
			ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
			return new TeslaSynthesizerRecipe(recipeId, blockIngredient, material1, material2, result, cookTime);
		}

		/**
		 * Serialize from JSON on the network.
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param buffer the <code>PacketBuffer</code> instance
		 * @return TeslaSynthesizerRecipe
		 */
		@Override
		public TeslaSynthesizerRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			Ingredient blockIngredient = Ingredient.fromNetwork(buffer);
			Ingredient material1 = Ingredient.fromNetwork(buffer);
			Ingredient material2 = Ingredient.fromNetwork(buffer);
			TeslaSynthesizerRecipe.cookTime = buffer.readInt();
			ItemStack result = buffer.readItem();
			return new TeslaSynthesizerRecipe(recipeId, blockIngredient, material1, material2, result, cookTime);
		}

		/**
		 * Serialize to JSON on the network.
		 * @param buffer the <code>PacketBuffer</code> instance
		 * @param recipe the <code>TeslaSynthesizerRecipe</code> instance
		 */
		@Override
		public void toNetwork(PacketBuffer buffer, TeslaSynthesizerRecipe recipe) {
			recipe.blockIngredient.toNetwork(buffer);
			recipe.material1.toNetwork(buffer);
			recipe.material2.toNetwork(buffer);
			buffer.writeItem(recipe.result);
		}
	}
}