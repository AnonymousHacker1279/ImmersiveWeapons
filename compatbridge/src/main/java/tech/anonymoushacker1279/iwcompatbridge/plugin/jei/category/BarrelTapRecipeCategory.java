package tech.anonymoushacker1279.iwcompatbridge.plugin.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.BarrelTapRecipe;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;
import tech.anonymoushacker1279.iwcompatbridge.plugin.jei.JEIPluginHandler;

public class BarrelTapRecipeCategory extends AbstractRecipeCategory<BarrelTapRecipe> {

	private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(IWCompatBridge.MOD_ID,
			"textures/gui/jei/barrel_tap_fermenting.png");
	private final IDrawable background;

	/// Constructor for BarrelTapRecipeCategory.
	///
	/// @param guiHelper a `IGuiHelper` instance
	public BarrelTapRecipeCategory(IGuiHelper guiHelper) {
		super(JEIPluginHandler.BARREL_TAP,
				Component.translatable("gui.jei.category.barrel_tap_fermenting"),
				guiHelper.createDrawableItemLike(BlockRegistry.BARREL_TAP.get()),
				162,
				102);

		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 162, 102);
	}

	@Override
	public void draw(BarrelTapRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics, 0, 0);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, BarrelTapRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 39, 69)
				.add(new ItemStack(recipe.input().getValues().get(0), recipe.getMaterialCount()));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 137, 42)
				.add(recipe.result());
	}
}