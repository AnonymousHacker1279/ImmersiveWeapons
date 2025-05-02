package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.input.StarForgeRecipeInput;

import java.util.Optional;

public class StarForgeRecipe implements Recipe<StarForgeRecipeInput> {

	private final String group;
	private final Ingredient primaryMaterial;
	private final int primaryMaterialCount;
	private final Optional<Ingredient> secondaryMaterial;
	private final int secondaryMaterialCount;
	private final ItemStack result;
	private final int smeltTime;

	@Nullable
	private PlacementInfo placementInfo;

	public StarForgeRecipe(String group, Ingredient primaryMaterial, int primaryMaterialCount, Optional<Ingredient> secondaryMaterial,
	                       int secondaryMaterialCount, int smeltTime, ItemStack result) {

		this.group = group;
		this.primaryMaterial = primaryMaterial;
		this.primaryMaterialCount = primaryMaterialCount;
		this.secondaryMaterial = secondaryMaterial;
		this.secondaryMaterialCount = secondaryMaterialCount;
		this.result = result;
		this.smeltTime = smeltTime;
	}

	public String group() {
		return group;
	}

	public ItemStack result() {
		return result;
	}

	public Ingredient primaryMaterial() {
		return primaryMaterial;
	}

	public int primaryMaterialCount() {
		return primaryMaterialCount;
	}

	public Optional<Ingredient> secondaryMaterial() {
		return secondaryMaterial;
	}

	public int secondaryMaterialCount() {
		return secondaryMaterialCount;
	}

	public int smeltTime() {
		return smeltTime;
	}

	@Override
	public boolean matches(StarForgeRecipeInput input, Level level) {
		if (input.primaryMaterial().isEmpty() || input.primaryMaterial().getCount() < primaryMaterialCount) {
			return false;
		}

		if (secondaryMaterial.isPresent() && input.secondaryMaterial() != null) {
			return !input.secondaryMaterial().isEmpty() && input.secondaryMaterial().getCount() >= secondaryMaterialCount;
		}

		return true;
	}

	@Override
	public ItemStack assemble(StarForgeRecipeInput input, HolderLookup.Provider registries) {
		return result;
	}

	@Override
	public RecipeSerializer<StarForgeRecipe> getSerializer() {
		return RecipeSerializerRegistry.STAR_FORGE_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<StarForgeRecipe> getType() {
		return RecipeTypeRegistry.STAR_FORGE_RECIPE_TYPE.get();
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;  // TODO: custom category
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public PlacementInfo placementInfo() {
		if (placementInfo == null) {
			placementInfo = PlacementInfo.create(primaryMaterial);
		}

		return placementInfo;
	}

	public interface Factory<T extends StarForgeRecipe> {
		T create(String group, Ingredient ingot, int ingotCount, Optional<Ingredient> secondaryMaterial, int secondaryMaterialCount,
		         int smeltTime, ItemStack result);
	}

	public static class Serializer<T extends StarForgeRecipe> implements RecipeSerializer<T> {

		private final MapCodec<T> codec;
		private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

		public Serializer(StarForgeRecipe.Factory<T> factory) {
			codec = RecordCodecBuilder.mapCodec(
					instance -> instance.group(
									Codec.STRING.optionalFieldOf("group", "").forGetter(StarForgeRecipe::group),
									Ingredient.CODEC.fieldOf("primary_material").forGetter(StarForgeRecipe::primaryMaterial),
									Codec.INT.fieldOf("primary_material_count").forGetter(StarForgeRecipe::primaryMaterialCount),
									Ingredient.CODEC.optionalFieldOf("secondary_material").forGetter(StarForgeRecipe::secondaryMaterial),
									Codec.INT.fieldOf("secondary_material_count").forGetter(StarForgeRecipe::secondaryMaterialCount),
									Codec.INT.fieldOf("smelt_time").forGetter(StarForgeRecipe::smeltTime),
									ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(StarForgeRecipe::result)
							)
							.apply(instance, factory::create)
			);

			streamCodec = StreamCodec.composite(
					ByteBufCodecs.STRING_UTF8,
					StarForgeRecipe::group,
					Ingredient.CONTENTS_STREAM_CODEC,
					StarForgeRecipe::primaryMaterial,
					ByteBufCodecs.INT,
					StarForgeRecipe::primaryMaterialCount,
					Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC,
					StarForgeRecipe::secondaryMaterial,
					ByteBufCodecs.INT,
					StarForgeRecipe::secondaryMaterialCount,
					ByteBufCodecs.INT,
					StarForgeRecipe::smeltTime,
					ItemStack.STREAM_CODEC,
					StarForgeRecipe::result,
					factory::create
			);
		}

		@Override
		public MapCodec<T> codec() {
			return this.codec;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
			return this.streamCodec;
		}
	}
}