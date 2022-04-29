package com.anonymoushacker1279.immersiveweapons.client.integration.jei.category;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.integration.jei.ImmersiveWeaponsJEIPlugin;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.*;
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
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/gui/jei/small_parts_table.png");
	private final IDrawable background;
	private final IDrawable icon;

	/**
	 * Constructor for SmallPartsRecipeCategory.
	 *
	 * @param guiHelper a <code>IGuiHelper</code> instance
	 */
	public SmallPartsRecipeCategory(IGuiHelper guiHelper) {
		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(DeferredRegistryHandler.SMALL_PARTS_TABLE.get()));
		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 125, 18);
	}

	/**
	 * Get the UID of the category.
	 *
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getUid() {
		return UID;
	}

	/**
	 * Get the recipe class for this category.
	 *
	 * @return Class extending SmallPartsRecipe
	 */
	@Override
	public @NotNull Class<? extends SmallPartsRecipe> getRecipeClass() {
		return SmallPartsRecipe.class;
	}

	@Override
	public @NotNull RecipeType<SmallPartsRecipe> getRecipeType() {
		return ImmersiveWeaponsJEIPlugin.SMALL_PARTS;
	}

	/**
	 * Get the title of the recipe category.
	 *
	 * @return String
	 */
	@Override
	public @NotNull Component getTitle() {
		return new TranslatableComponent("gui.jei.category.small_parts");
	}

	/**
	 * Get the background.
	 *
	 * @return IDrawable
	 */
	@Override
	public @NotNull IDrawable getBackground() {
		return background;
	}

	/**
	 * Get the icon.
	 *
	 * @return IDrawable
	 */
	@Override
	public @NotNull IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, SmallPartsRecipe recipe, @NotNull IFocusGroup focuses) {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		ingredients.addAll(recipe.getIngredients());

		builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
				.addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.CATALYST, 50, 1)
				.addIngredients(recipe.getIngredients().get(1));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1)
				.addItemStack(recipe.getResultItem());
	}
}