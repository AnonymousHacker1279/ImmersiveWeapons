package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.ArrayList;
import java.util.List;

public record SmallPartsRecipe(ResourceLocation recipeId,
                               Ingredient material,
                               List<Item> craftables) implements Recipe<Container> {

	/**
	 * Constructor for SmallPartsRecipe.
	 *
	 * @param recipeId   the <code>ResourceLocation</code> for the recipe
	 * @param material   the first <code>Ingredient</code>
	 * @param craftables a <code>List</code> containing <code>Item</code>s that are formed from the material
	 */
	public SmallPartsRecipe {
	}

	/**
	 * Used to check if a recipe matches current crafting inventory.
	 *
	 * @param container the <code>Container</code> instance
	 * @param level     the current <code>Level</code>
	 * @return boolean
	 */
	@Override
	public boolean matches(@NotNull Container container, @NotNull Level level) {
		return false;
	}

	/**
	 * Returns an Item that is the result of this recipe.
	 *
	 * @param container the <code>Container</code> instance
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack assemble(@NotNull Container container) {
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

	@Override
	public @NotNull ItemStack getResultItem() {
		return new ItemStack(Items.AIR);
	}

	/**
	 * Get the toast symbol.
	 *
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack getToastSymbol() {
		return new ItemStack(DeferredRegistryHandler.SMALL_PARTS_TABLE.get());
	}

	/**
	 * Get the recipe ID.
	 *
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getId() {
		return recipeId;
	}

	/**
	 * Get the recipe serializer.
	 *
	 * @return RecipeSerializer
	 */
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return DeferredRegistryHandler.SMALL_PARTS_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 *
	 * @return RecipeType
	 */
	@Override
	public @NotNull RecipeType<?> getType() {
		return DeferredRegistryHandler.SMALL_PARTS_RECIPE_TYPE.get();
	}

	/**
	 * Get the recipe's ingredients.
	 *
	 * @return NonNullList extending Ingredient
	 */
	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(material);
		return defaultedList;
	}

	public static class Serializer implements RecipeSerializer<SmallPartsRecipe> {
		/**
		 * Serialize from JSON.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param json     the <code>JsonObject</code> instance
		 * @return SmallPartsRecipe
		 */
		@Override
		public @NotNull SmallPartsRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
			Ingredient material = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "material"));
			JsonArray craftablesArray = GsonHelper.getAsJsonArray(json, "craftables");

			List<Item> craftables = new ArrayList<>(craftablesArray.size());
			for (JsonElement element : craftablesArray) {
				craftables.add(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(element.getAsString())));
			}

			return new SmallPartsRecipe(recipeId, material, craftables);
		}

		/**
		 * Serialize from JSON on the network.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param buffer   the <code>FriendlyByteBuf</code> instance
		 * @return SmallPartsRecipe
		 */
		@Override
		public SmallPartsRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
			Ingredient material = Ingredient.fromNetwork(buffer);

			String craft = buffer.readUtf();
			String s = craft.replace("[", "").replace("]", "").replace(" ", "");
			String[] s1 = s.split(",");

			List<Item> craftables = new ArrayList<>(s1.length);
			for (String s2 : s1) {
				craftables.add(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(s2)));
			}

			return new SmallPartsRecipe(recipeId, material, craftables);
		}

		/**
		 * Serialize to JSON on the network.
		 *
		 * @param buffer the <code>FriendlyByteBuf</code> instance
		 * @param recipe the <code>SmallPartsRecipe</code> instance
		 */
		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, SmallPartsRecipe recipe) {
			recipe.material.toNetwork(buffer);
			List<ResourceLocation> craftables = new ArrayList<>(recipe.craftables.size());
			for (Item item : recipe.craftables) {
				craftables.add(ForgeRegistries.ITEMS.getKey(item));
			}
			buffer.writeUtf(craftables.toString());
		}
	}
}