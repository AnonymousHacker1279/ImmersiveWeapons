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

	public TeslaSynthesizerRecipe(ResourceLocation recipeId, Ingredient blockIngredient, Ingredient material1, Ingredient material2, ItemStack result, int cookTime) {
		this.recipeId = recipeId;
		this.blockIngredient = blockIngredient;
		this.material1 = material1;
		this.material2 = material2;
		this.result = result;
		TeslaSynthesizerRecipe.cookTime = cookTime;
	}

	public static Object getCookTime() {
		return cookTime;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return this.blockIngredient.test(inv.getItem(0)) && this.material1.test(inv.getItem(1)) && this.material2.test(inv.getItem(2));
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack assemble(IInventory inv) {
		ItemStack itemstack = this.result.copy();
		CompoundNBT compoundnbt = inv.getItem(4).getTag();
		if (compoundnbt != null) {
			itemstack.setTag(compoundnbt.copy());
		}

		return itemstack;
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	@Override
	public ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(DeferredRegistryHandler.TESLA_SYNTHESIZER.get());
	}

	@Override
	public ResourceLocation getId() {
		return this.recipeId;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return DeferredRegistryHandler.TESLA_SYNTHEZISER_RECIPE_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return ICustomRecipeType.TESLA_SYNTHESIZER;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(blockIngredient);
		defaultedList.add(material1);
		defaultedList.add(material2);
		return defaultedList;
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TeslaSynthesizerRecipe> {
		@Override
		public TeslaSynthesizerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			Ingredient blockIngredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "block"));
			Ingredient material1 = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "material1"));
			Ingredient material2 = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "material2"));
			TeslaSynthesizerRecipe.cookTime = JSONUtils.getAsInt(json, "cookTime");
			ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
			return new TeslaSynthesizerRecipe(recipeId, blockIngredient, material1, material2, result, cookTime);
		}

		@Override
		public TeslaSynthesizerRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			Ingredient blockIngredient = Ingredient.fromNetwork(buffer);
			Ingredient material1 = Ingredient.fromNetwork(buffer);
			Ingredient material2 = Ingredient.fromNetwork(buffer);
			TeslaSynthesizerRecipe.cookTime = buffer.readInt();
			ItemStack result = buffer.readItem();
			return new TeslaSynthesizerRecipe(recipeId, blockIngredient, material1, material2, result, cookTime);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, TeslaSynthesizerRecipe recipe) {
			recipe.blockIngredient.toNetwork(buffer);
			recipe.material1.toNetwork(buffer);
			recipe.material2.toNetwork(buffer);
			buffer.writeItem(recipe.result);
		}
	}
}