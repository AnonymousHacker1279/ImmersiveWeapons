package com.anonymoushacker1279.immersiveweapons.client.integration.jei.recipe_category;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.TeslaSynthesizerRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class TeslaSynthesizerRecipeCategory implements IRecipeCategory<TeslaSynthesizerRecipe> {

	public static final ResourceLocation UID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_synthesizer");
	protected static final int BLOCK_INGREDIENT = 0;
	protected static final int MATERIAL_INGREDIENT_1 = 1;
	protected static final int MATERIAL_INGREDIENT_2 = 2;
	protected static final int OUTPUT_SLOT = 3;
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/gui/jei/tesla_synthesizer.png");
	private final IDrawable background;
	private final IDrawable icon;

	public TeslaSynthesizerRecipeCategory(IGuiHelper guiHelper) {
		icon = guiHelper.createDrawableIngredient(new ItemStack(DeferredRegistryHandler.TESLA_SYNTHESIZER.get()));
		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 132, 54);
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<? extends TeslaSynthesizerRecipe> getRecipeClass() {
		return TeslaSynthesizerRecipe.class;
	}

	@Override
	public String getTitle() {
		return new TranslationTextComponent("gui.jei.category.tesla_synthesizer").getString();
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setIngredients(TeslaSynthesizerRecipe recipe, IIngredients ingredients) {
		NonNullList<Ingredient> inputs = NonNullList.create();
		inputs.addAll(recipe.getIngredients());

		ingredients.setInputIngredients(inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TeslaSynthesizerRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(BLOCK_INGREDIENT, true, 0, 0);
		guiItemStacks.init(MATERIAL_INGREDIENT_1, true, 25, 0);
		guiItemStacks.init(MATERIAL_INGREDIENT_2, true, 50, 0);
		guiItemStacks.init(OUTPUT_SLOT, false, 109, 18);

		guiItemStacks.set(ingredients);
	}
}