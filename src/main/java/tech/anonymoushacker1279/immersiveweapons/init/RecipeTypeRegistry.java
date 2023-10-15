package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.*;

@SuppressWarnings({"unused"})
public class RecipeTypeRegistry {

	// Recipe Type Register
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ImmersiveWeapons.MOD_ID);

	public static final RegistryObject<RecipeType<SmallPartsRecipe>> SMALL_PARTS_RECIPE_TYPE = RECIPE_TYPES.register("small_parts", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return ImmersiveWeapons.MOD_ID + ":small_parts";
		}
	});
	public static final RegistryObject<RecipeType<TeslaSynthesizerRecipe>> TESLA_SYNTHESIZER_RECIPE_TYPE = RECIPE_TYPES.register("tesla_synthesizer", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return ImmersiveWeapons.MOD_ID + ":tesla_synthesizer";
		}
	});
	public static final RegistryObject<RecipeType<BarrelTapRecipe>> BARREL_TAP_RECIPE_TYPE = RECIPE_TYPES.register("barrel_tap", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return ImmersiveWeapons.MOD_ID + ":barrel_tap";
		}
	});
	public static final RegistryObject<RecipeType<AstralCrystalRecipe>> ASTRAL_CRYSTAL_RECIPE_TYPE = RECIPE_TYPES.register("astral_crystal", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return ImmersiveWeapons.MOD_ID + ":astral_crystal";
		}
	});
	public static final RegistryObject<RecipeType<PistonCrushingRecipe>> PISTON_CRUSHING_RECIPE_TYPE = RECIPE_TYPES.register("piston_crushing", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return ImmersiveWeapons.MOD_ID + ":piston_crushing";
		}
	});
	public static final RegistryObject<RecipeType<AmmunitionTableRecipe>> AMMUNITION_TABLE_RECIPE_TYPE = RECIPE_TYPES.register("ammunition_table", () -> new RecipeType<>() {
		@Override
		public String toString() {
			return ImmersiveWeapons.MOD_ID + ":ammunition_table";
		}
	});
}