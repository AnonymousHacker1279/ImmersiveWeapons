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

public class AstralCrystalRecipe implements Recipe<Container> {

	protected final Ingredient primaryMaterial;
	protected final Ingredient secondaryMaterial;
	protected final ItemStack result;
	protected final String group;

	public AstralCrystalRecipe(String group, Ingredient primaryMaterial, Ingredient secondaryMaterial, ItemStack itemStack) {
		this.primaryMaterial = primaryMaterial;
		this.secondaryMaterial = secondaryMaterial;
		this.result = itemStack;
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

	public static class Serializer<T extends AstralCrystalRecipe> implements RecipeSerializer<T> {

		private final AstralCrystalRecipe.Factory<T> factory;
		private final Codec<T> codec;

		public Serializer(AstralCrystalRecipe.Factory<T> factory) {
			this.factory = factory;
			this.codec = RecordCodecBuilder.create(
					instance -> instance.group(
									ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
									Ingredient.CODEC.fieldOf("primaryMaterial").forGetter(recipe -> recipe.primaryMaterial),
									Ingredient.CODEC.fieldOf("secondaryMaterial").forGetter(recipe -> recipe.secondaryMaterial),
									ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
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
			Ingredient primaryMaterial = Ingredient.fromNetwork(buffer);
			Ingredient secondaryMaterial = Ingredient.fromNetwork(buffer);
			ItemStack result = buffer.readItem();

			return factory.create(group, primaryMaterial, secondaryMaterial, result);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AstralCrystalRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.primaryMaterial.toNetwork(buffer);
			recipe.secondaryMaterial.toNetwork(buffer);
			buffer.writeItem(recipe.result);
		}
	}
}