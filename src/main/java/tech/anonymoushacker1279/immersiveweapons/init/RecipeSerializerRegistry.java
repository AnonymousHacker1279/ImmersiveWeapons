package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.*;

import java.util.function.Supplier;

public class RecipeSerializerRegistry {

	// Recipe Register
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ImmersiveWeapons.MOD_ID);

	// Recipes
	public static final Supplier<RecipeSerializer<SmallPartsRecipe>> SMALL_PARTS_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("small_parts", SmallPartsRecipe.Serializer::new);
	public static final Supplier<RecipeSerializer<TeslaSynthesizerRecipe>> TESLA_SYNTHESIZER_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("tesla_synthesizer", TeslaSynthesizerRecipe.Serializer::new);
	public static final Supplier<RecipeSerializer<BarrelTapRecipe>> BARREL_TAP_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("barrel_tap", BarrelTapRecipe.Serializer::new);
	public static final Supplier<RecipeSerializer<AstralCrystalRecipe>> ASTRAL_CRYSTAL_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("astral_crystal", AstralCrystalRecipe.Serializer::new);
	public static final Supplier<RecipeSerializer<PistonCrushingRecipe>> PISTON_CRUSHING_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("piston_crushing", PistonCrushingRecipe.Serializer::new);
	public static final Supplier<RecipeSerializer<AmmunitionTableRecipe>> AMMUNITION_TABLE_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("ammunition_table", AmmunitionTableRecipe.Serializer::new);
	public static final Supplier<RecipeSerializer<StarForgeRecipe>> STAR_FORGE_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("star_forge", StarForgeRecipe.Serializer::new);
}