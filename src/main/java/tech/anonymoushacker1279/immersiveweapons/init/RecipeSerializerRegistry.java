package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.*;

@SuppressWarnings({"unused"})
public class RecipeSerializerRegistry {

	// Recipe Register
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ImmersiveWeapons.MOD_ID);

	// Recipes
	public static final RegistryObject<RecipeSerializer<SmallPartsRecipe>> SMALL_PARTS_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("small_parts", SmallPartsRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<TeslaSynthesizerRecipe>> TESLA_SYNTHESIZER_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("tesla_synthesizer", TeslaSynthesizerRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<BarrelTapRecipe>> BARREL_TAP_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("barrel_tap", BarrelTapRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<AstralCrystalRecipe>> ASTRAL_CRYSTAL_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("astral_crystal", AstralCrystalRecipe.Serializer::new);
}