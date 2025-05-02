package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

public class BarrelTapRecipe extends SingleItemRecipe {

	private final int materialCount;

	public BarrelTapRecipe(String group, Ingredient material, int materialCount, ItemStack result) {
		super(group, material, result);
		this.materialCount = materialCount;
	}

	public int getMaterialCount() {
		return materialCount;
	}

	@Override
	public boolean matches(SingleRecipeInput input, Level level) {
		return super.matches(input, level) && input.getItem(0).getCount() >= materialCount;
	}

	@Override
	public RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
		return RecipeSerializerRegistry.BARREL_TAP_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<? extends SingleItemRecipe> getType() {
		return RecipeTypeRegistry.BARREL_TAP_RECIPE_TYPE.get();
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;  // TODO: custom category
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public interface Factory<T extends BarrelTapRecipe> {
		T create(String group, Ingredient material, int materialCount, ItemStack result);
	}

	public static class Serializer<T extends BarrelTapRecipe> implements RecipeSerializer<T> {

		private final MapCodec<T> codec;
		private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

		public Serializer(BarrelTapRecipe.Factory<T> factory) {
			codec = RecordCodecBuilder.mapCodec(
					instance -> instance.group(
									Codec.STRING.optionalFieldOf("group", "").forGetter(SingleItemRecipe::group),
									Ingredient.CODEC.fieldOf("material").forGetter(SingleItemRecipe::input),
									Codec.INT.fieldOf("materialCount").forGetter(BarrelTapRecipe::getMaterialCount),
									ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(SingleItemRecipe::result)
							)
							.apply(instance, factory::create)
			);

			streamCodec = StreamCodec.composite(
					ByteBufCodecs.STRING_UTF8,
					SingleItemRecipe::group,
					Ingredient.CONTENTS_STREAM_CODEC,
					SingleItemRecipe::input,
					ByteBufCodecs.INT,
					BarrelTapRecipe::getMaterialCount,
					ItemStack.STREAM_CODEC,
					SingleItemRecipe::result,
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