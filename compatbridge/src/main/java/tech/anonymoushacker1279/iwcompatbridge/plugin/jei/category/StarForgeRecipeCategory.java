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
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;
import tech.anonymoushacker1279.iwcompatbridge.plugin.jei.JEIPluginHandler;

public class StarForgeRecipeCategory extends AbstractRecipeCategory<StarForgeRecipe> {

	private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(IWCompatBridge.MOD_ID,
			"textures/gui/jei/star_forge.png");
	private final IDrawable background;

	public StarForgeRecipeCategory(IGuiHelper guiHelper) {
		super(JEIPluginHandler.STAR_FORGE,
				Component.translatable("gui.jei.category.star_forge"),
				guiHelper.createDrawableItemLike(BlockRegistry.STAR_FORGE_CONTROLLER.get()),
				95,
				26);

		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 95, 26);
	}

	@Override
	public void draw(StarForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics, 0, 0);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, StarForgeRecipe recipe, IFocusGroup focuses) {
		ItemStack ingot = recipe.primaryMaterial().getValues().get(0).value().getDefaultInstance();

		builder.addSlot(RecipeIngredientRole.INPUT, 1, 5)
				.add(ingot.copyWithCount(recipe.primaryMaterialCount()));

		if (recipe.secondaryMaterial().isPresent()) {
			ItemStack secondaryMaterial = recipe.secondaryMaterial().get().getValues().get(0).value().getDefaultInstance();
			builder.addSlot(RecipeIngredientRole.INPUT, 27, 5)
					.add(secondaryMaterial.copyWithCount(recipe.secondaryMaterialCount()));
		}

		builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 5)
				.add(recipe.result());
	}
}