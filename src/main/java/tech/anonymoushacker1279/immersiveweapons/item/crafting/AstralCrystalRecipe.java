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
import tech.anonymoushacker1279.immersiveweapons.item.crafting.input.AstralCrystalRecipeInput;

public class AstralCrystalRecipe implements Recipe<AstralCrystalRecipeInput> {

	private final String group;
	private final Ingredient material;
	private final Ingredient catalyst;
	private final ItemStack result;

	@Nullable
	private PlacementInfo placementInfo;

	public AstralCrystalRecipe(String group, Ingredient material, Ingredient catalyst, ItemStack result) {
		this.group = group;
		this.material = material;
		this.catalyst = catalyst;
		this.result = result;
	}

	public String group() {
		return group;
	}

	public ItemStack result() {
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
	public ItemStack assemble(AstralCrystalRecipeInput input, HolderLookup.Provider registries) {
		return result;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public PlacementInfo placementInfo() {
		if (placementInfo == null) {
			placementInfo = PlacementInfo.create(material);
		}

		return placementInfo;
	}

	public interface Factory<T extends AstralCrystalRecipe> {
		T create(String group, Ingredient material, Ingredient catalyst, ItemStack result);
	}

	public static class Serializer<T extends AstralCrystalRecipe> implements RecipeSerializer<T> {

		private final MapCodec<T> codec;
		private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

		public Serializer(AstralCrystalRecipe.Factory<T> factory) {
			codec = RecordCodecBuilder.mapCodec(
					instance -> instance.group(
									Codec.STRING.optionalFieldOf("group", "").forGetter(AstralCrystalRecipe::group),
									Ingredient.CODEC.fieldOf("material").forGetter(AstralCrystalRecipe::material),
									Ingredient.CODEC.fieldOf("catalyst").forGetter(AstralCrystalRecipe::catalyst),
									ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(AstralCrystalRecipe::result)
							)
							.apply(instance, factory::create)
			);

			streamCodec = StreamCodec.composite(
					ByteBufCodecs.STRING_UTF8,
					AstralCrystalRecipe::group,
					Ingredient.CONTENTS_STREAM_CODEC,
					AstralCrystalRecipe::material,
					Ingredient.CONTENTS_STREAM_CODEC,
					AstralCrystalRecipe::catalyst,
					ItemStack.STREAM_CODEC,
					AstralCrystalRecipe::result,
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