package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public record AstralCrystalRecipe(String group, Ingredient primaryMaterial, Ingredient secondaryMaterial,
                                  ItemStack result) implements Recipe<RecipeInput> {

	@Override
	public boolean matches(RecipeInput input, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(RecipeInput input, Provider registries) {
		return result;
	}

	@Override
	public ItemStack getResultItem(Provider provider) {
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	public Ingredient getPrimaryMaterial() {
		return primaryMaterial;
	}

	public Ingredient getSecondaryMaterial() {
		return secondaryMaterial;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.ASTRAL_CRYSTAL.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.ASTRAL_CRYSTAL_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 *
	 * @return RecipeType
	 */
	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.ASTRAL_CRYSTAL_RECIPE_TYPE.get();
	}

	@Override
	public String getGroup() {
		return group;
	}

	/**
	 * Get the recipe's ingredients.
	 *
	 * @return NonNullList extending Ingredient
	 */
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(primaryMaterial);
		defaultedList.add(secondaryMaterial);
		return defaultedList;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public interface Factory<T extends AstralCrystalRecipe> {
		T create(String group, Ingredient primaryMaterial, Ingredient secondaryMaterial, ItemStack result);
	}

	public static class Serializer implements RecipeSerializer<AstralCrystalRecipe> {

		private static final MapCodec<AstralCrystalRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC.fieldOf("primaryMaterial").forGetter(recipe -> recipe.primaryMaterial),
								Ingredient.CODEC.fieldOf("secondaryMaterial").forGetter(recipe -> recipe.secondaryMaterial),
								ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
						)
						.apply(instance, AstralCrystalRecipe::new)
		);

		private static final StreamCodec<RegistryFriendlyByteBuf, AstralCrystalRecipe> STREAM_CODEC = StreamCodec.of(
				AstralCrystalRecipe.Serializer::toNetwork, AstralCrystalRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<AstralCrystalRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, AstralCrystalRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static AstralCrystalRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			Ingredient primaryMaterial = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			Ingredient secondaryMaterial = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);

			return new AstralCrystalRecipe(group, primaryMaterial, secondaryMaterial, result);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, AstralCrystalRecipe recipe) {
			buffer.writeUtf(recipe.group);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.primaryMaterial);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.secondaryMaterial);
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
		}
	}
}