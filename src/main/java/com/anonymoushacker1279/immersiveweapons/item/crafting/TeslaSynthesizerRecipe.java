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

	private final Ingredient blockIngredient;
	private final Ingredient material1;
	private final Ingredient material2;
	private final ItemStack result;
	private final ResourceLocation recipeId;
	private static int cookTime = 0;

	public TeslaSynthesizerRecipe(ResourceLocation recipeId, Ingredient blockIngredient, Ingredient material1, Ingredient material2, ItemStack result, int cookTime) {
		this.recipeId = recipeId;
		this.blockIngredient = blockIngredient;
		this.material1 = material1;
		this.material2 = material2;
		this.result = result;
		TeslaSynthesizerRecipe.cookTime = cookTime;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return this.blockIngredient.test(inv.getStackInSlot(0)) && this.material1.test(inv.getStackInSlot(1)) && this.material2.test(inv.getStackInSlot(2));
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		ItemStack itemstack = this.result.copy();
		CompoundNBT compoundnbt = inv.getStackInSlot(4).getTag();
		if (compoundnbt != null) {
			itemstack.setTag(compoundnbt.copy());
		}

		return itemstack;
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	@Override
	public ItemStack getRecipeOutput() {
		return this.result;
	}

	@Override
	public ItemStack getIcon() {
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

	public static Object getCookTime() {
		return cookTime;
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TeslaSynthesizerRecipe> {
		@Override
		public TeslaSynthesizerRecipe read(ResourceLocation recipeId, JsonObject json) {
			Ingredient blockIngredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "block"));
			Ingredient material1 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "material1"));
			Ingredient material2 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "material2"));
			TeslaSynthesizerRecipe.cookTime = JSONUtils.getInt(json, "cookTime");
			ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
			return new TeslaSynthesizerRecipe(recipeId, blockIngredient, material1, material2, result, cookTime);
		}

		@Override
		public TeslaSynthesizerRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
			Ingredient blockIngredient = Ingredient.read(buffer);
			Ingredient material1 = Ingredient.read(buffer);
			Ingredient material2 = Ingredient.read(buffer);
			TeslaSynthesizerRecipe.cookTime = buffer.readInt();
			ItemStack result = buffer.readItemStack();
			return new TeslaSynthesizerRecipe(recipeId, blockIngredient, material1, material2, result, cookTime);
		}

		@Override
		public void write(PacketBuffer buffer, TeslaSynthesizerRecipe recipe) {
			recipe.blockIngredient.write(buffer);
			recipe.material1.write(buffer);
			recipe.material2.write(buffer);
			buffer.writeItemStack(recipe.result);
		}
	}
}