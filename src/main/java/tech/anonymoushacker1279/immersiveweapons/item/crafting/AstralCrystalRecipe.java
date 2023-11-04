package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public record AstralCrystalRecipe(Ingredient primaryMaterial,
                                  Ingredient secondaryMaterial,
                                  ItemStack result,
                                  int resultCount) implements Recipe<Container> {

	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return result;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
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

	public int getResultCount() {
		return resultCount;
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

	public static class Serializer implements RecipeSerializer<AstralCrystalRecipe> {

		private static final Codec<AstralCrystalRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Ingredient.CODEC.fieldOf("primaryMaterial").forGetter(recipe -> recipe.primaryMaterial),
								Ingredient.CODEC.fieldOf("secondaryMaterial").forGetter(recipe -> recipe.secondaryMaterial),
								CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
								Codec.INT.fieldOf("resultCount").forGetter(recipe -> recipe.resultCount)
						)
						.apply(instance, AstralCrystalRecipe::new)
		);

		@Override
		public Codec<AstralCrystalRecipe> codec() {
			return CODEC;
		}

		@Override
		public AstralCrystalRecipe fromNetwork(FriendlyByteBuf buffer) {
			Ingredient primaryMaterial = Ingredient.fromNetwork(buffer);
			Ingredient secondaryMaterial = Ingredient.fromNetwork(buffer);
			ItemStack result = buffer.readItem();
			int resultCount = buffer.readInt();

			return new AstralCrystalRecipe(primaryMaterial, secondaryMaterial, result, resultCount);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AstralCrystalRecipe recipe) {
			recipe.primaryMaterial.toNetwork(buffer);
			recipe.secondaryMaterial.toNetwork(buffer);
			buffer.writeItem(recipe.result);
			buffer.writeInt(recipe.resultCount);
		}
	}
}