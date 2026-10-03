package tech.anonymoushacker1279.iwcompatbridge.plugin.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;
import tech.anonymoushacker1279.iwcompatbridge.plugin.jei.JEIPluginHandler;

public class AmmunitionTableRecipeCategory extends AbstractRecipeCategory<AmmunitionTableRecipe> {

	private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(IWCompatBridge.MOD_ID,
			"textures/gui/jei/ammunition_table.png");
	private final IDrawable background;

	public AmmunitionTableRecipeCategory(IGuiHelper guiHelper) {
		super(JEIPluginHandler.AMMUNITION_TABLE,
				Component.translatable("gui.jei.category.ammunition_table"),
				guiHelper.createDrawableItemLike(BlockRegistry.AMMUNITION_TABLE.get()),
				105,
				36);

		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 105, 36);
	}

	@Override
	public void draw(AmmunitionTableRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics, 0, 0);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, AmmunitionTableRecipe recipe, IFocusGroup focuses) {
		NonNullList<ItemStack> ingredients = NonNullList.create();
		for (AmmunitionTableRecipe.MaterialGroup group : recipe.materials()) {
			ingredients.addAll(group.ingredient().getValues().stream()
					.map(ingredient -> ingredient.value().getDefaultInstance())
					.toList());
		}

		builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
				.addIngredients(VanillaTypes.ITEM_STACK, ingredients);

		builder.addSlot(RecipeIngredientRole.OUTPUT, 84, 10)
				.add(recipe.result());
	}
}