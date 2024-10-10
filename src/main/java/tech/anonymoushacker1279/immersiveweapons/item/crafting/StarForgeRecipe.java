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

public record StarForgeRecipe(String group, Ingredient ingot, int ingotCount, Ingredient secondaryMaterial,
                              int secondaryMaterialCount, int smeltTime,
                              ItemStack result) implements Recipe<RecipeInput> {

	@Override
	public boolean matches(RecipeInput input, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(RecipeInput input, Provider registries) {
		return result;
	}

	public boolean matches(Container container) {
		ItemStack ingotStack = container.getItem(0);
		ItemStack secondaryMaterialStack = container.getItem(1);

		if (ingotStack.getItem().equals(getIngot().getItem()) && ingotStack.getCount() >= ingotCount) {
			return secondaryMaterialStack.getItem().equals(getSecondaryMaterial().getItem()) && secondaryMaterialStack.getCount() >= secondaryMaterialCount;
		}

		return false;
	}

	@Override
	public ItemStack getResultItem(Provider provider) {
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.STAR_FORGE_CONTROLLER.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.STAR_FORGE_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.STAR_FORGE_RECIPE_TYPE.get();
	}

	@Override
	public String getGroup() {
		return group;
	}

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

	public ItemStack result() {
		return result.copy();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public interface Factory<T extends StarForgeRecipe> {
		T create(String group, Ingredient ingot, int ingotCount, Ingredient secondaryMaterial, int secondaryMaterialCount,
		         int smeltTime, ItemStack result);
	}

	public static class Serializer implements RecipeSerializer<StarForgeRecipe> {

		private static final MapCodec<StarForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(StarForgeRecipe::group),
								Ingredient.CODEC.fieldOf("ingot").forGetter(StarForgeRecipe::ingot),
								Codec.INT.fieldOf("ingot_count").forGetter(StarForgeRecipe::ingotCount),
								Ingredient.CODEC.fieldOf("secondary_material").forGetter(StarForgeRecipe::secondaryMaterial),
								Codec.INT.fieldOf("secondary_material_count").forGetter(StarForgeRecipe::secondaryMaterialCount),
								Codec.INT.fieldOf("smelt_time").forGetter(StarForgeRecipe::smeltTime),
								ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(StarForgeRecipe::result)
						)
						.apply(instance, StarForgeRecipe::new)
		);

		private static final StreamCodec<RegistryFriendlyByteBuf, StarForgeRecipe> STREAM_CODEC = StreamCodec.of(
				StarForgeRecipe.Serializer::toNetwork, StarForgeRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<StarForgeRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, StarForgeRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static StarForgeRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {

			String group = buffer.readUtf();
			Ingredient ingot = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			int ingotCount = buffer.readInt();
			Ingredient secondaryMaterial = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			int secondaryMaterialCount = buffer.readInt();
			int smeltTime = buffer.readInt();
			ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);

			return new StarForgeRecipe(group, ingot, ingotCount, secondaryMaterial, secondaryMaterialCount, smeltTime, result);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, StarForgeRecipe recipe) {
			buffer.writeUtf(recipe.group);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingot);
			buffer.writeInt(recipe.ingotCount);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.secondaryMaterial);
			buffer.writeInt(recipe.secondaryMaterialCount);
			buffer.writeInt(recipe.smeltTime);
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
		}
	}
}