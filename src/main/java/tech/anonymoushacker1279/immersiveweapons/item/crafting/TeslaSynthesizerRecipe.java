package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public class TeslaSynthesizerRecipe implements Recipe<Container> {

	protected final Ingredient blockIngredient;
	protected final Ingredient material1;
	protected final Ingredient material2;
	protected final ItemStack result;
	protected final int cookTime;
	protected final String group;

	public TeslaSynthesizerRecipe(String group, Ingredient blockIngredient, Ingredient material1, Ingredient material2, ItemStack result, int cookTime) {
		this.blockIngredient = blockIngredient;
		this.material1 = material1;
		this.material2 = material2;
		this.result = result;
		this.cookTime = cookTime;
		this.group = group;
	}


	@Override
	public boolean matches(Container container, Level level) {
		return blockIngredient.test(container.getItem(0))
				&& material1.test(container.getItem(1))
				&& material2.test(container.getItem(2));
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		ItemStack resultStack = result.copy();
		CompoundTag compoundTag = container.getItem(4).getTag();
		if (compoundTag != null) {
			resultStack.setTag(compoundTag.copy());
		}

		return resultStack;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.TESLA_SYNTHESIZER.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.TESLA_SYNTHESIZER_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.TESLA_SYNTHESIZER_RECIPE_TYPE.get();
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(blockIngredient);
		defaultedList.add(material1);
		defaultedList.add(material2);
		return defaultedList;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public int getCookTime() {
		return cookTime;
	}

	public interface Factory<T extends TeslaSynthesizerRecipe> {
		T create(String group, Ingredient blockIngredient, Ingredient material1, Ingredient material2, ItemStack result, int cookTime);
	}

	public static class Serializer<T extends TeslaSynthesizerRecipe> implements RecipeSerializer<T> {

		private final TeslaSynthesizerRecipe.Factory<T> factory;
		private final Codec<T> codec;

		public Serializer(TeslaSynthesizerRecipe.Factory<T> factory) {
			this.factory = factory;
			this.codec = RecordCodecBuilder.create(
					instance -> instance.group(
									ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
									Ingredient.CODEC.fieldOf("block").forGetter(recipe -> recipe.blockIngredient),
									Ingredient.CODEC.fieldOf("material1").forGetter(recipe -> recipe.material1),
									Ingredient.CODEC.fieldOf("material2").forGetter(recipe -> recipe.material2),
									ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
									Codec.INT.fieldOf("cookTime").forGetter(recipe -> recipe.cookTime)
							)
							.apply(instance, factory::create)
			);
		}

		@Override
		public Codec<T> codec() {
			return codec;
		}

		@Override
		public T fromNetwork(FriendlyByteBuf byteBuf) {
			String group = byteBuf.readUtf();
			Ingredient blockIngredient = Ingredient.fromNetwork(byteBuf);
			Ingredient material1 = Ingredient.fromNetwork(byteBuf);
			Ingredient material2 = Ingredient.fromNetwork(byteBuf);
			int cookTime = byteBuf.readInt();
			ItemStack result = byteBuf.readItem();

			return factory.create(group, blockIngredient, material1, material2, result, cookTime);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, TeslaSynthesizerRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.blockIngredient.toNetwork(buffer);
			recipe.material1.toNetwork(buffer);
			recipe.material2.toNetwork(buffer);
			buffer.writeItem(recipe.result);
		}
	}
}