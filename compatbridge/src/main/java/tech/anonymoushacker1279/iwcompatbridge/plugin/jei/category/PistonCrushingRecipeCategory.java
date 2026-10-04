package tech.anonymoushacker1279.iwcompatbridge.plugin.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.PistonCrushingRecipe;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;
import tech.anonymoushacker1279.iwcompatbridge.plugin.jei.JEIPluginHandler;

public class PistonCrushingRecipeCategory extends AbstractRecipeCategory<PistonCrushingRecipe> {

	private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(IWCompatBridge.MOD_ID,
			"textures/gui/jei/piston_crushing.png");
	private final IDrawable background;

	/// Constructor for PistonCrushingRecipeCategory.
	///
	/// @param guiHelper a `IGuiHelper` instance
	public PistonCrushingRecipeCategory(IGuiHelper guiHelper) {
		super(JEIPluginHandler.PISTON_CRUSHING,
				Component.translatable("gui.jei.category.piston_crushing"),
				guiHelper.createDrawableItemLike(Blocks.PISTON),
				100,
				50);

		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 100, 50);
	}

	@Override
	public void draw(PistonCrushingRecipe recipe, IRecipeSlotsView recipeSlotsView,
	                 GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {

		background.draw(guiGraphics, 0, 0);

		MutableComponent dropString = Component.translatable("gui.jei.category.piston_crushing.note", recipe.getMinCount(), recipe.getMaxCount());
		Minecraft minecraft = Minecraft.getInstance();
		Font fontRenderer = minecraft.font;
		int width = fontRenderer.width(dropString);
		guiGraphics.text(fontRenderer, dropString, background.getWidth() - width, 40, 0x808080, false);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, PistonCrushingRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 2, 17)
				.add(recipe.input());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 17)
				.add(recipe.result());
	}
}