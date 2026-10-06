package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.input.AmmunitionForgeRecipeInput;

/// A recipe for the Ammunition Forge. One material is cast in a mold, producing the result. The mold is not consumed.
public class AmmunitionForgeRecipe implements Recipe<AmmunitionForgeRecipeInput> {

	public static final int DEFAULT_COOKING_TIME = 100;

	public static final MapCodec<AmmunitionForgeRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
							Codec.STRING.optionalFieldOf("group", "").forGetter(AmmunitionForgeRecipe::group),
							Ingredient.CODEC.fieldOf("material").forGetter(AmmunitionForgeRecipe::material),
							Ingredient.CODEC.fieldOf("mold").forGetter(AmmunitionForgeRecipe::mold),
							ItemStackTemplate.CODEC.fieldOf("result").forGetter(AmmunitionForgeRecipe::result),
							Codec.INT.optionalFieldOf("cooking_time", DEFAULT_COOKING_TIME).forGetter(AmmunitionForgeRecipe::cookingTime)
					)
					.apply(instance, AmmunitionForgeRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, AmmunitionForgeRecipe> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			AmmunitionForgeRecipe::group,
			Ingredient.CONTENTS_STREAM_CODEC,
			AmmunitionForgeRecipe::material,
			Ingredient.CONTENTS_STREAM_CODEC,
			AmmunitionForgeRecipe::mold,
			ItemStackTemplate.STREAM_CODEC,
			AmmunitionForgeRecipe::result,
			ByteBufCodecs.VAR_INT,
			AmmunitionForgeRecipe::cookingTime,
			AmmunitionForgeRecipe::new
	);
	public static final RecipeSerializer<AmmunitionForgeRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final String group;
	private final Ingredient material;
	private final Ingredient mold;
	private final ItemStackTemplate result;
	private final int cookingTime;

	@Nullable
	private PlacementInfo placementInfo;

	public AmmunitionForgeRecipe(String group, Ingredient material, Ingredient mold, ItemStackTemplate result, int cookingTime) {
		this.group = group;
		this.material = material;
		this.mold = mold;
		this.result = result;
		this.cookingTime = cookingTime;
	}

	public String group() {
		return group;
	}

	public Ingredient material() {
		return material;
	}

	public Ingredient mold() {
		return mold;
	}

	public ItemStackTemplate result() {
		return result;
	}

	public int cookingTime() {
		return cookingTime;
	}

	@Override
	public boolean matches(AmmunitionForgeRecipeInput input, Level level) {
		return material.test(input.material()) && mold.test(input.mold());
	}

	@Override
	public ItemStack assemble(AmmunitionForgeRecipeInput input) {
		return result.create();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public boolean showNotification() {
		return false;
	}

	@Override
	public RecipeSerializer<? extends Recipe<AmmunitionForgeRecipeInput>> getSerializer() {
		return RecipeSerializerRegistry.AMMUNITION_FORGE_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<? extends Recipe<AmmunitionForgeRecipeInput>> getType() {
		return RecipeTypeRegistry.AMMUNITION_FORGE_RECIPE_TYPE.get();
	}

	@Override
	public PlacementInfo placementInfo() {
		if (placementInfo == null) {
			placementInfo = PlacementInfo.create(material);
		}

		return placementInfo;
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}
}
