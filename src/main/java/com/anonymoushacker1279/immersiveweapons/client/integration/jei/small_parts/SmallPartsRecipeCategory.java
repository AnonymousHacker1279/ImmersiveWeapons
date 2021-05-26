package com.anonymoushacker1279.immersiveweapons.client.integration.jei.small_parts;

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
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class SmallPartsRecipeCategory implements IRecipeCategory<SmallPartsRecipe> {

	public static final ResourceLocation UID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "small_parts");
	protected static final int MATERIAL_SLOT = 0;
	protected static final int BLUEPRINT_SLOT = 1;
	protected static final int OUTPUT_SLOT = 2;
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/gui/jei/small_parts_table.png");
	private final IDrawable background;
	private final IDrawable icon;

	public SmallPartsRecipeCategory(IGuiHelper guiHelper) {
		icon = guiHelper.createDrawableIngredient(new ItemStack(DeferredRegistryHandler.SMALL_PARTS_TABLE.get()));
		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 125, 18);
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<? extends SmallPartsRecipe> getRecipeClass() {
		return SmallPartsRecipe.class;
	}

	@Override
	public String getTitle() {
		return new TranslationTextComponent("gui.jei.category.small_parts").getString();
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
	public void setIngredients(SmallPartsRecipe recipe, IIngredients ingredients) {
		NonNullList<Ingredient> inputs = NonNullList.create();
		inputs.addAll(recipe.getIngredients());

		ingredients.setInputIngredients(inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SmallPartsRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(MATERIAL_SLOT, true, 0, 0);
		guiItemStacks.init(BLUEPRINT_SLOT, true, 49, 0);
		guiItemStacks.init(OUTPUT_SLOT, false, 107, 0);

		guiItemStacks.set(ingredients);
	}

}