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

public class SmallPartsRecipe implements IRecipe<IInventory> {

	private final Ingredient material;
	private final Ingredient blueprint;
	private final ItemStack result;
	private final ResourceLocation recipeId;

	public SmallPartsRecipe(ResourceLocation recipeId, Ingredient material, Ingredient blueprint, ItemStack result) {
		this.recipeId = recipeId;
		this.material = material;
		this.blueprint = blueprint;
		this.result = result;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return this.material.test(inv.getItem(0)) && this.blueprint.test(inv.getItem(1));
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack assemble(IInventory inv) {
		ItemStack itemstack = this.result.copy();
		CompoundNBT compoundnbt = inv.getItem(0).getTag();
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

	public boolean isValidAdditionItem(ItemStack blueprint) {
		return this.blueprint.test(blueprint);
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(DeferredRegistryHandler.SMALL_PARTS_TABLE.get());
	}

	@Override
	public ResourceLocation getId() {
		return this.recipeId;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return DeferredRegistryHandler.SMALL_PARTS_RECIPE_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return ICustomRecipeType.SMALL_PARTS;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(material);
		defaultedList.add(blueprint);
		return defaultedList;
	}

	public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SmallPartsRecipe> {
		@Override
		public SmallPartsRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "material"));
			Ingredient ingredient1 = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "blueprint"));
			ItemStack itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
			return new SmallPartsRecipe(recipeId, ingredient, ingredient1, itemstack);
		}

		@Override
		public SmallPartsRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
			ItemStack itemstack = buffer.readItem();
			return new SmallPartsRecipe(recipeId, ingredient, ingredient1, itemstack);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, SmallPartsRecipe recipe) {
			recipe.material.toNetwork(buffer);
			recipe.blueprint.toNetwork(buffer);
			buffer.writeItem(recipe.result);
		}
	}
}