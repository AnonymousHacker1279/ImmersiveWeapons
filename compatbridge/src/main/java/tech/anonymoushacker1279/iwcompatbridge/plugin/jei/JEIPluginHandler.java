package tech.anonymoushacker1279.iwcompatbridge.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.*;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionTableMenu;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;
import tech.anonymoushacker1279.iwcompatbridge.plugin.jei.category.*;

@JeiPlugin
@SuppressWarnings("unused")
public class JEIPluginHandler implements IModPlugin {

	public static final IRecipeType<AstralCrystalRecipe> ASTRAL_CRYSTAL =
			IRecipeType.create(ImmersiveWeapons.MOD_ID, "astral_crystal", AstralCrystalRecipe.class);
	public static final IRecipeType<BarrelTapRecipe> BARREL_TAP =
			IRecipeType.create(ImmersiveWeapons.MOD_ID, "barrel_tap", BarrelTapRecipe.class);
	public static final IRecipeType<PistonCrushingRecipe> PISTON_CRUSHING =
			IRecipeType.create(ImmersiveWeapons.MOD_ID, "piston_crushing", PistonCrushingRecipe.class);
	public static final IRecipeType<AmmunitionTableRecipe> AMMUNITION_TABLE =
			IRecipeType.create(ImmersiveWeapons.MOD_ID, "ammunition_table", AmmunitionTableRecipe.class);
	public static final IRecipeType<StarForgeRecipe> STAR_FORGE =
			IRecipeType.create(ImmersiveWeapons.MOD_ID, "star_forge", StarForgeRecipe.class);

	/// Get the plugin UID.
	///
	/// @return Identifier
	@Override
	public Identifier getPluginUid() {
		return Identifier.fromNamespaceAndPath(IWCompatBridge.MOD_ID, "jei_plugin");
	}

	/// Register recipe catalysts.
	///
	/// @param registration an `IRecipeCatalystRegistration` instance
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addCraftingStation(ASTRAL_CRYSTAL, new ItemStack(BlockRegistry.ASTRAL_CRYSTAL.get()));
		registration.addCraftingStation(BARREL_TAP, new ItemStack(BlockRegistry.BARREL_TAP.get()));
		registration.addCraftingStation(PISTON_CRUSHING, new ItemStack(Blocks.PISTON));
		registration.addCraftingStation(AMMUNITION_TABLE, new ItemStack(BlockRegistry.AMMUNITION_TABLE.get()));
		registration.addCraftingStation(STAR_FORGE, new ItemStack(BlockRegistry.STAR_FORGE_CONTROLLER.get()));
	}

	/// Register recipes.
	///
	/// @param registration an `IRecipeRegistration` instance
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = getRecipeManager();

		if (recipeManager == null) {
			return;
		}

		registration.addRecipes(ASTRAL_CRYSTAL, recipeManager
				.getRecipes()
				.stream()
				.filter(holder -> holder.value().getType() == RecipeTypeRegistry.ASTRAL_CRYSTAL_RECIPE_TYPE.get())
				.map(RecipeHolder::value)
				.map(recipe -> (AstralCrystalRecipe) recipe)
				.toList());

		registration.addRecipes(BARREL_TAP, recipeManager
				.getRecipes()
				.stream()
				.filter(holder -> holder.value().getType() == RecipeTypeRegistry.BARREL_TAP_RECIPE_TYPE.get())
				.map(RecipeHolder::value)
				.map(recipe -> (BarrelTapRecipe) recipe)
				.toList());

		registration.addRecipes(PISTON_CRUSHING, recipeManager
				.getRecipes()
				.stream()
				.filter(holder -> holder.value().getType() == RecipeTypeRegistry.PISTON_CRUSHING_RECIPE_TYPE.get())
				.map(RecipeHolder::value)
				.map(recipe -> (PistonCrushingRecipe) recipe)
				.toList());

		registration.addRecipes(AMMUNITION_TABLE, recipeManager
				.getRecipes()
				.stream()
				.filter(holder -> holder.value().getType() == RecipeTypeRegistry.AMMUNITION_TABLE_RECIPE_TYPE.get())
				.map(RecipeHolder::value)
				.map(recipe -> (AmmunitionTableRecipe) recipe)
				.toList());

		registration.addRecipes(STAR_FORGE, recipeManager
				.getRecipes()
				.stream()
				.filter(holder -> holder.value().getType() == RecipeTypeRegistry.STAR_FORGE_RECIPE_TYPE.get())
				.map(RecipeHolder::value)
				.map(recipe -> (StarForgeRecipe) recipe)
				.toList());

		registration.addItemStackInfo(BlockRegistry.ASTRAL_CRYSTAL.get().asItem().getDefaultInstance(), Component.translatable("gui.jei.item.astral_crystal.info"));
		registration.addItemStackInfo(BlockRegistry.BARREL_TAP.get().asItem().getDefaultInstance(), Component.translatable("gui.jei.item.barrel_tap.info"));
		registration.addItemStackInfo(Blocks.PISTON.asItem().getDefaultInstance(), Component.translatable("gui.jei.item.piston_crushing.info"));
		registration.addItemStackInfo(BlockRegistry.AMMUNITION_TABLE.get().asItem().getDefaultInstance(), Component.translatable("gui.jei.item.ammunition_table.info"));
		registration.addItemStackInfo(BlockRegistry.STAR_FORGE_CONTROLLER.get().asItem().getDefaultInstance(), Component.translatable("gui.jei.item.star_forge.info"));
	}

	/// Register recipe categories.
	///
	/// @param registration an `IRecipeCategoryRegistration` instance
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new AstralCrystalRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new BarrelTapRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new PistonCrushingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new AmmunitionTableRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new StarForgeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	}

	/// Register recipe transfer handlers.
	///
	/// @param registration an `IRecipeTransferRegistration` instance
	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(AmmunitionTableMenu.class,
				MenuTypeRegistry.AMMUNITION_TABLE_MENU.get(), AMMUNITION_TABLE,
				0, 6, 7, 36);
		registration.addRecipeTransferHandler(StarForgeMenu.class,
				MenuTypeRegistry.STAR_FORGE_MENU.get(), STAR_FORGE,
				0, 2, 3, 36);
	}

	@Override
	public void registerModInfo(IModInfoRegistration modAliasRegistration) {
		modAliasRegistration.addModAliases(ImmersiveWeapons.MOD_ID, "iw");
		modAliasRegistration.addModAliases(IWCompatBridge.MOD_ID, "iwcb");
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerFromDataComponentTypes(ItemRegistry.CHOCOLATE_BAR.get(), DataComponentTypeRegistry.IS_EXPLOSIVE.get());
		registration.registerFromDataComponentTypes(ItemRegistry.MUSKET.get(), DataComponentTypeRegistry.SCOPE.get());
	}

	@Nullable
	private static RecipeManager getRecipeManager() {
		if (ServerLifecycleHooks.getCurrentServer() != null) {
			return ServerLifecycleHooks.getCurrentServer().getRecipeManager();
		}

		return null;
	}
}