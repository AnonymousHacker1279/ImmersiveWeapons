package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public record StarForgeRecipe(Ingredient ingot, int ingotCount, Ingredient secondaryMaterial,
                              int secondaryMaterialCount,
                              int smeltTime, ItemStack result) implements Recipe<Container> {

	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return new ItemStack(Items.AIR);
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	/**
	 * Get the toast symbol.
	 *
	 * @return ItemStack
	 */
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.STAR_FORGE_CONTROLLER.get());
	}

	/**
	 * Get the recipe serializer.
	 *
	 * @return RecipeSerializer
	 */
	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.STAR_FORGE_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 *
	 * @return RecipeType
	 */
	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.STAR_FORGE_RECIPE_TYPE.get();
	}

	/**
	 * Get the recipe's ingredients.
	 *
	 * @return NonNullList extending Ingredient
	 */
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(ingot);
		defaultedList.add(secondaryMaterial);
		return defaultedList;
	}

	public ItemStack getIngot() {
		if (ingot.getItems().length > 0) {
			return ingot.getItems()[0].copyWithCount(ingotCount);
		} else {
			return ItemStack.EMPTY;
		}
	}

	public ItemStack getSecondaryMaterial() {
		if (secondaryMaterial.getItems().length > 0) {
			return secondaryMaterial.getItems()[0].copyWithCount(secondaryMaterialCount);
		} else {
			return ItemStack.EMPTY;
		}
	}

	public ItemStack getResult() {
		return result.copy();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public static class Serializer implements RecipeSerializer<StarForgeRecipe> {

		private static final Codec<StarForgeRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Ingredient.CODEC.fieldOf("ingot").forGetter(recipe -> recipe.ingot),
								Codec.INT.fieldOf("ingot_count").forGetter(recipe -> recipe.ingotCount),
								Ingredient.CODEC.fieldOf("secondary_material").forGetter(recipe -> recipe.secondaryMaterial),
								Codec.INT.fieldOf("secondary_material_count").forGetter(recipe -> recipe.secondaryMaterialCount),
								Codec.INT.fieldOf("smelt_time").forGetter(recipe -> recipe.smeltTime),
								CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
						)
						.apply(instance, StarForgeRecipe::new));

		@Override
		public Codec<StarForgeRecipe> codec() {
			return CODEC;
		}

		@Override
		public StarForgeRecipe fromNetwork(FriendlyByteBuf buffer) {
			Ingredient ingot = Ingredient.fromNetwork(buffer);
			int ingotCount = buffer.readInt();
			Ingredient secondaryMaterial = Ingredient.fromNetwork(buffer);
			int secondaryMaterialCount = buffer.readInt();
			int smeltTime = buffer.readInt();
			ItemStack result = buffer.readItem();

			return new StarForgeRecipe(ingot, ingotCount, secondaryMaterial, secondaryMaterialCount, smeltTime, result);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, StarForgeRecipe recipe) {
			recipe.ingot.toNetwork(buffer);
			buffer.writeInt(recipe.ingotCount);
			recipe.secondaryMaterial.toNetwork(buffer);
			buffer.writeInt(recipe.secondaryMaterialCount);
			buffer.writeInt(recipe.smeltTime);
			buffer.writeItem(recipe.result);
		}
	}
}