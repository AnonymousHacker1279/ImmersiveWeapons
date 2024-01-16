package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public record StarForgeRecipe(String group, Ingredient ingot, int ingotCount, Ingredient secondaryMaterial,
                              int secondaryMaterialCount, int smeltTime,
                              ItemStack result) implements Recipe<Container> {

	public boolean matches(Container container, Level level) {
		return false;
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

	public static class Serializer<T extends StarForgeRecipe> implements RecipeSerializer<T> {

		protected final StarForgeRecipe.Factory<T> factory;
		protected final Codec<T> codec;

		public Serializer(StarForgeRecipe.Factory<T> factory) {
			this.factory = factory;
			this.codec = RecordCodecBuilder.create(
					instance -> instance.group(
									ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(StarForgeRecipe::group),
									Ingredient.CODEC.fieldOf("ingot").forGetter(StarForgeRecipe::ingot),
									Codec.INT.fieldOf("ingot_count").forGetter(StarForgeRecipe::ingotCount),
									Ingredient.CODEC.fieldOf("secondary_material").forGetter(StarForgeRecipe::secondaryMaterial),
									Codec.INT.fieldOf("secondary_material_count").forGetter(StarForgeRecipe::secondaryMaterialCount),
									Codec.INT.fieldOf("smelt_time").forGetter(StarForgeRecipe::smeltTime),
									ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(StarForgeRecipe::result)
							)
							.apply(instance, factory::create));
		}

		@Override
		public Codec<T> codec() {
			return codec;
		}

		@Override
		public T fromNetwork(FriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			Ingredient ingot = Ingredient.fromNetwork(buffer);
			int ingotCount = buffer.readInt();
			Ingredient secondaryMaterial = Ingredient.fromNetwork(buffer);
			int secondaryMaterialCount = buffer.readInt();
			int smeltTime = buffer.readInt();
			ItemStack result = buffer.readItem();

			return factory.create(group, ingot, ingotCount, secondaryMaterial, secondaryMaterialCount, smeltTime, result);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, StarForgeRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.ingot.toNetwork(buffer);
			buffer.writeInt(recipe.ingotCount);
			recipe.secondaryMaterial.toNetwork(buffer);
			buffer.writeInt(recipe.secondaryMaterialCount);
			buffer.writeInt(recipe.smeltTime);
			buffer.writeItem(recipe.result);
		}
	}
}