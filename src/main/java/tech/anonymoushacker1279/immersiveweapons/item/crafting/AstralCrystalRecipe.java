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
import tech.anonymoushacker1279.immersiveweapons.item.crafting.input.AstralCrystalRecipeInput;

public class AstralCrystalRecipe implements Recipe<AstralCrystalRecipeInput> {

	public static final MapCodec<AstralCrystalRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
							Codec.STRING.optionalFieldOf("group", "").forGetter(AstralCrystalRecipe::group),
							Ingredient.CODEC.fieldOf("material").forGetter(AstralCrystalRecipe::material),
							Ingredient.CODEC.fieldOf("catalyst").forGetter(AstralCrystalRecipe::catalyst),
							ItemStackTemplate.CODEC.fieldOf("result").forGetter(AstralCrystalRecipe::result)
					)
					.apply(instance, AstralCrystalRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, AstralCrystalRecipe> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			AstralCrystalRecipe::group,
			Ingredient.CONTENTS_STREAM_CODEC,
			AstralCrystalRecipe::material,
			Ingredient.CONTENTS_STREAM_CODEC,
			AstralCrystalRecipe::catalyst,
			ItemStackTemplate.STREAM_CODEC,
			AstralCrystalRecipe::result,
			AstralCrystalRecipe::new
	);
	public static final RecipeSerializer<AstralCrystalRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final String group;
	private final Ingredient material;
	private final Ingredient catalyst;
	private final ItemStackTemplate result;

	@Nullable
	private PlacementInfo placementInfo;

	public AstralCrystalRecipe(String group, Ingredient material, Ingredient catalyst, ItemStackTemplate result) {
		this.group = group;
		this.material = material;
		this.catalyst = catalyst;
		this.result = result;
	}

	public String group() {
		return group;
	}

	public ItemStackTemplate result() {
		return result;
	}

	public Ingredient material() {
		return material;
	}

	public Ingredient catalyst() {
		return catalyst;
	}

	@Override
	public RecipeSerializer<AstralCrystalRecipe> getSerializer() {
		return RecipeSerializerRegistry.ASTRAL_CRYSTAL_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<AstralCrystalRecipe> getType() {
		return RecipeTypeRegistry.ASTRAL_CRYSTAL_RECIPE_TYPE.get();
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}

	@Override
	public boolean matches(AstralCrystalRecipeInput input, Level level) {
		return material.test(input.getItem(0));
	}

	@Override
	public ItemStack assemble(AstralCrystalRecipeInput input) {
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
	public PlacementInfo placementInfo() {
		if (placementInfo == null) {
			placementInfo = PlacementInfo.create(material);
		}

		return placementInfo;
	}
}