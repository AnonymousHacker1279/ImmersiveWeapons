package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public class BarrelTapRecipe implements Recipe<Container> {

	protected final Ingredient material;
	protected final int materialCount;
	protected final ItemStack result;
	protected final String group;

	public BarrelTapRecipe(String group, Ingredient material, int materialCount, ItemStack result) {
		this.material = material;
		this.materialCount = materialCount;
		this.result = result;
		this.group = group;
	}

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

	public static class Serializer<T extends BarrelTapRecipe> implements RecipeSerializer<T> {

		private final BarrelTapRecipe.Factory<T> factory;
		private final Codec<T> codec;

		public Serializer(BarrelTapRecipe.Factory<T> factory) {
			this.factory = factory;
			this.codec = RecordCodecBuilder.create(
					instance -> instance.group(
									ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
									Ingredient.CODEC.fieldOf("material").forGetter(recipe -> recipe.material),
									Codec.INT.fieldOf("materialCount").forGetter(recipe -> recipe.materialCount),
									ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
							)
							.apply(instance, factory::create)
			);
		}

		@Override
		public Codec<T> codec() {
			return codec;
		}

		@Override
		public T fromNetwork(FriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			Ingredient material = Ingredient.fromNetwork(buffer);
			int materialCount = buffer.readInt();
			ItemStack result = buffer.readItem();

			return factory.create(group, material, materialCount, result);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, BarrelTapRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.material.toNetwork(buffer);
			buffer.writeInt(recipe.materialCount);
			buffer.writeItem(recipe.result);
		}
	}
}