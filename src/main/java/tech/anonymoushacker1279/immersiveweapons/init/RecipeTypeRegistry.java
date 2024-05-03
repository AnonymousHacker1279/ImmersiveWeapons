package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.*;

import java.util.function.Supplier;

public class RecipeTypeRegistry {

	// Recipe Type Register
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ImmersiveWeapons.MOD_ID);

	public static final Supplier<RecipeType<SmallPartsRecipe>> SMALL_PARTS_RECIPE_TYPE = RECIPE_TYPES.register("small_parts", () -> RecipeType.simple(new ResourceLocation(ImmersiveWeapons.MOD_ID, "small_parts")));
	public static final Supplier<RecipeType<TeslaSynthesizerRecipe>> TESLA_SYNTHESIZER_RECIPE_TYPE = RECIPE_TYPES.register("tesla_synthesizer", () -> RecipeType.simple(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_synthesizer")));
	public static final Supplier<RecipeType<BarrelTapRecipe>> BARREL_TAP_RECIPE_TYPE = RECIPE_TYPES.register("barrel_tap", () -> RecipeType.simple(new ResourceLocation(ImmersiveWeapons.MOD_ID, "barrel_tap")));
	public static final Supplier<RecipeType<AstralCrystalRecipe>> ASTRAL_CRYSTAL_RECIPE_TYPE = RECIPE_TYPES.register("astral_crystal", () -> RecipeType.simple(new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_crystal")));
	public static final Supplier<RecipeType<PistonCrushingRecipe>> PISTON_CRUSHING_RECIPE_TYPE = RECIPE_TYPES.register("piston_crushing", () -> RecipeType.simple(new ResourceLocation(ImmersiveWeapons.MOD_ID, "piston_crushing")));
	public static final Supplier<RecipeType<AmmunitionTableRecipe>> AMMUNITION_TABLE_RECIPE_TYPE = RECIPE_TYPES.register("ammunition_table", () -> RecipeType.simple(new ResourceLocation(ImmersiveWeapons.MOD_ID, "ammunition_table")));
	public static final Supplier<RecipeType<StarForgeRecipe>> STAR_FORGE_RECIPE_TYPE = RECIPE_TYPES.register("star_forge", () -> RecipeType.simple(new ResourceLocation(ImmersiveWeapons.MOD_ID, "star_forge")));
}