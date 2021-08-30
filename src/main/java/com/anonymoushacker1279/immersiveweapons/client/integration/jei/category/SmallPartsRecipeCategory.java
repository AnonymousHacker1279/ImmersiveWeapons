package com.anonymoushacker1279.immersiveweapons.client.integration.jei.category;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class SmallPartsRecipeCategory implements IRecipeCategory<SmallPartsRecipe> {

	public static final ResourceLocation UID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "small_parts");
	protected static final int MATERIAL_SLOT = 0;
	protected static final int BLUEPRINT_SLOT = 1;
	protected static final int OUTPUT_SLOT = 2;
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/gui/jei/small_parts_table.png");
	private final IDrawable background;
	private final IDrawable icon;

	/**
	 * Constructor for SmallPartsRecipeCategory.
	 * @param guiHelper a <code>IGuiHelper</code> instance
	 */
	public SmallPartsRecipeCategory(IGuiHelper guiHelper) {
		icon = guiHelper.createDrawableIngredient(new ItemStack(DeferredRegistryHandler.SMALL_PARTS_TABLE.get()));
		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 125, 18);
	}

	/**
	 * Get the UID of the category.
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getUid() {
		return UID;
	}

	/**
	 * Get the recipe class for this category.
	 * @return Class extending SmallPartsRecipe
	 */
	@Override
	public @NotNull Class<? extends SmallPartsRecipe> getRecipeClass() {
		return SmallPartsRecipe.class;
	}

	/**
	 * Get the title of the recipe category.
	 * @return String
	 */
	@Override
	public @NotNull Component getTitle() {
		return new TranslatableComponent("gui.jei.category.small_parts");
	}

	/**
	 * Get the background.
	 * @return IDrawable
	 */
	@Override
	public @NotNull IDrawable getBackground() {
		return background;
	}

	/**
	 * Get the icon.
	 * @return IDrawable
	 */
	@Override
	public @NotNull IDrawable getIcon() {
		return icon;
	}

	/**
	 * Set the ingredients in a recipe.
	 * @param recipe a <code>SmallPartsRecipe</code> instance
	 * @param ingredients the <code>IIngredients</code> being passed
	 */
	@Override
	public void setIngredients(SmallPartsRecipe recipe, IIngredients ingredients) {
		NonNullList<Ingredient> inputs = NonNullList.create();
		inputs.addAll(recipe.getIngredients());

		ingredients.setInputIngredients(inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
	}

	/**
	 * Set up the recipe layout.
	 * @param recipeLayout a <code>IRecipeLayout</code> instance
	 * @param recipe a <code>SmallPartsRecipe</code> instance
	 * @param ingredients the <code>IIngredients</code> being passed
	 */
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, @NotNull SmallPartsRecipe recipe, @NotNull IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(MATERIAL_SLOT, true, 0, 0);
		guiItemStacks.init(BLUEPRINT_SLOT, true, 49, 0);
		guiItemStacks.init(OUTPUT_SLOT, false, 107, 0);

		guiItemStacks.set(ingredients);
	}

}