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

public record BarrelTapRecipe(Ingredient material,
                              int materialCount,
                              ItemStack result) implements Recipe<Container> {

	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
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
	public ItemStack getResultItem(RegistryAccess registryAccess) {
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
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(material);
		return defaultedList;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public static class Serializer implements RecipeSerializer<BarrelTapRecipe> {

		private static final Codec<BarrelTapRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Ingredient.CODEC.fieldOf("material").forGetter(recipe -> recipe.material),
								Codec.INT.fieldOf("materialCount").forGetter(recipe -> recipe.materialCount),
								CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
						)
						.apply(instance, BarrelTapRecipe::new)
		);

		@Override
		public Codec<BarrelTapRecipe> codec() {
			return CODEC;
		}

		@Override
		public BarrelTapRecipe fromNetwork(FriendlyByteBuf buffer) {
			Ingredient material = Ingredient.fromNetwork(buffer);
			int materialCount = buffer.readInt();
			ItemStack result = buffer.readItem();

			return new BarrelTapRecipe(material, materialCount, result);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, BarrelTapRecipe recipe) {
			recipe.material.toNetwork(buffer);
			buffer.writeInt(recipe.materialCount);
			buffer.writeItem(recipe.result);
		}
	}
}