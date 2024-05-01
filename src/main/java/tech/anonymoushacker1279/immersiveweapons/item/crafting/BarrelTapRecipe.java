package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public record BarrelTapRecipe(String group, Ingredient material, int materialCount,
                              ItemStack result) implements Recipe<Container> {

	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, Provider provider) {
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	public Ingredient getMaterial() {
		return material;
	}

	public int getMaterialCount() {
		return materialCount;
	}

	@Override
	public ItemStack getResultItem(Provider provider) {
		return result.copy();
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.BARREL_TAP.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.BARREL_TAP_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.BARREL_TAP_RECIPE_TYPE.get();
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(material);
		return defaultedList;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public interface Factory<T extends BarrelTapRecipe> {
		T create(String group, Ingredient material, int materialCount, ItemStack result);
	}

	public static class Serializer implements RecipeSerializer<BarrelTapRecipe> {

		private static final MapCodec<BarrelTapRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC.fieldOf("material").forGetter(recipe -> recipe.material),
								Codec.INT.fieldOf("materialCount").forGetter(recipe -> recipe.materialCount),
								ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
						)
						.apply(instance, BarrelTapRecipe::new)
		);

		private static final StreamCodec<RegistryFriendlyByteBuf, BarrelTapRecipe> STREAM_CODEC = StreamCodec.of(
				BarrelTapRecipe.Serializer::toNetwork, BarrelTapRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<BarrelTapRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, BarrelTapRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static BarrelTapRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			Ingredient material = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			int materialCount = buffer.readInt();
			ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);

			return new BarrelTapRecipe(group, material, materialCount, result);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, BarrelTapRecipe recipe) {
			buffer.writeUtf(recipe.group);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.material);
			buffer.writeInt(recipe.materialCount);
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
		}
	}
}