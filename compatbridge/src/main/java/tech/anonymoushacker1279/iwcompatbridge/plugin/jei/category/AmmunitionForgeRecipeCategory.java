package tech.anonymoushacker1279.iwcompatbridge.plugin.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionForgeRecipe;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;
import tech.anonymoushacker1279.iwcompatbridge.plugin.jei.JEIPluginHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmmunitionForgeRecipeCategory extends AbstractRecipeCategory<AmmunitionForgeRecipe> {

	private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(IWCompatBridge.MOD_ID,
			"textures/gui/jei/ammunition_forge.png");

	private static final int WIDTH = 94;
	private static final int HEIGHT = 54;
	private static final int FLAME_X = 13;
	private static final int FLAME_Y = 20;
	private static final int ARROW_X = 36;
	private static final int ARROW_Y = 18;

	private static List<ItemStack> fuels;

	private final IGuiHelper guiHelper;
	private final IDrawable background;
	private final IDrawableAnimated flame;
	private final Map<Integer, IDrawableAnimated> arrows = new HashMap<>();

	public AmmunitionForgeRecipeCategory(IGuiHelper guiHelper) {
		super(JEIPluginHandler.AMMUNITION_FORGE,
				Component.translatable("gui.jei.category.ammunition_forge"),
				guiHelper.createDrawableItemLike(BlockRegistry.AMMUNITION_FORGE.get()),
				WIDTH,
				HEIGHT);

		this.guiHelper = guiHelper;
		background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, WIDTH, HEIGHT);
		flame = guiHelper.createAnimatedRecipeFlame(300);
	}

	@Override
	public void draw(AmmunitionForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics, 0, 0);
		flame.draw(guiGraphics, FLAME_X, FLAME_Y);

		int cookingTime = recipe.cookingTime();
		arrows.computeIfAbsent(cookingTime, time -> guiHelper.createAnimatedRecipeArrow(time))
				.draw(guiGraphics, ARROW_X, ARROW_Y);

		Component time = Component.translatable("gui.jei.category.ammunition_forge.note", String.format("%.2f", cookingTime / 20.0F));
		Font font = Minecraft.getInstance().font;
		int width = font.width(time);
		guiGraphics.text(font, time, ARROW_X + (24 - width) / 2, 38, 0x808080, false);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, AmmunitionForgeRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
				.addIngredients(VanillaTypes.ITEM_STACK, toStacks(recipe.material()));

		builder.addSlot(RecipeIngredientRole.INPUT, 25, 1)
				.addIngredients(VanillaTypes.ITEM_STACK, toStacks(recipe.mold()));

		builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 13, 37)
				.addIngredients(VanillaTypes.ITEM_STACK, getFuels());

		builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 19)
				.add(recipe.result().create());
	}

	private static List<ItemStack> getFuels() {
		if (fuels == null) {
			fuels = BuiltInRegistries.ITEM.stream()
					.map(Item::getDefaultInstance)
					.filter(stack -> !stack.isEmpty() && stack.has(DataComponents.COOKING_FUEL))
					.toList();
		}

		return fuels;
	}

	private static List<ItemStack> toStacks(net.minecraft.world.item.crafting.Ingredient ingredient) {
		return ingredient.getValues().stream()
				.map(holder -> holder.value().getDefaultInstance())
				.toList();
	}
}
