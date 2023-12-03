package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.ArrayList;
import java.util.List;

public record SmallPartsRecipe(Ingredient material,
                               List<Item> craftables) implements Recipe<Container> {

	/**
	 * Used to check if a recipe matches current crafting inventory.
	 *
	 * @param container the <code>Container</code> instance
	 * @param level     the current <code>Level</code>
	 * @return boolean
	 */
	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return new ItemStack(Items.AIR);
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return new ItemStack(Items.AIR);
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height.
	 *
	 * @param width  the width of the grid
	 * @param height the height of the grid
	 * @return boolean
	 */
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
		return new ItemStack(BlockRegistry.SMALL_PARTS_TABLE.get());
	}

	/**
	 * Get the recipe serializer.
	 *
	 * @return RecipeSerializer
	 */
	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.SMALL_PARTS_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 *
	 * @return RecipeType
	 */
	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.SMALL_PARTS_RECIPE_TYPE.get();
	}

	/**
	 * Get the recipe's ingredients.
	 *
	 * @return NonNullList extending Ingredient
	 */
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

	public static class Serializer implements RecipeSerializer<SmallPartsRecipe> {

		private static final Codec<SmallPartsRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Ingredient.CODEC.fieldOf("material").forGetter(recipe -> recipe.material),
								Codec.list(Codec.STRING).fieldOf("craftables").forGetter(recipe -> {
									List<String> craftables = new ArrayList<>(recipe.craftables.size());
									for (Item item : recipe.craftables) {
										craftables.add(BuiltInRegistries.ITEM.getKey(item).toString());
									}
									return craftables;
								})
						)
						.apply(instance, (material, craftables) -> {
									List<Item> craftables1 = new ArrayList<>(craftables.size());
									for (String s : craftables) {
										craftables1.add(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(s)));
									}

									return new SmallPartsRecipe(material, craftables1);
								}
						));

		@Override
		public Codec<SmallPartsRecipe> codec() {
			return CODEC;
		}

		@Override
		public SmallPartsRecipe fromNetwork(FriendlyByteBuf buffer) {
			Ingredient material = Ingredient.fromNetwork(buffer);

			String craft = buffer.readUtf();
			String s = craft.replace("[", "").replace("]", "").replace(" ", "");
			String[] s1 = s.split(",");

			List<Item> craftables = new ArrayList<>(s1.length);
			for (String s2 : s1) {
				craftables.add(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(s2)));
			}

			return new SmallPartsRecipe(material, craftables);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, SmallPartsRecipe recipe) {
			recipe.material.toNetwork(buffer);
			List<ResourceLocation> craftables = new ArrayList<>(recipe.craftables.size());
			for (Item item : recipe.craftables) {
				craftables.add(BuiltInRegistries.ITEM.getKey(item));
			}
			buffer.writeUtf(craftables.toString());
		}
	}
}