package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.ArrayList;
import java.util.List;

public record AmmunitionTableRecipe(ResourceLocation recipeId,
                                    List<MaterialGroup> materials,
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
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	public List<MaterialGroup> getMaterials() {
		return materials;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.AMMUNITION_TABLE.get());
	}

	@Override
	public ResourceLocation getId() {
		return recipeId;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.AMMUNITION_TABLE_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.AMMUNITION_TABLE_RECIPE_TYPE.get();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (MaterialGroup group : materials) {
			ingredients.add(group.getIngredient());
		}
		return ingredients;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public static class Serializer implements RecipeSerializer<AmmunitionTableRecipe> {

		@Override
		public AmmunitionTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			List<MaterialGroup> materials = new ArrayList<>(5);
			GsonHelper.getAsJsonArray(json, "materials").forEach(jsonElement -> {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				Ingredient ingredient = Ingredient.fromJson(jsonObject.get("ingredient"));
				float density = jsonObject.get("density").getAsFloat();
				float baseMultiplier = jsonObject.get("base_multiplier").getAsFloat();

				materials.add(new MaterialGroup(ingredient, density, baseMultiplier));
			});

			ItemStack result = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));

			return new AmmunitionTableRecipe(recipeId, materials, result);
		}

		@Override
		public AmmunitionTableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			List<MaterialGroup> materials = new ArrayList<>(5);
			int materialCount = buffer.readInt();
			for (int i = 0; i < materialCount; i++) {
				Ingredient ingredient = Ingredient.fromNetwork(buffer);
				float density = buffer.readFloat();
				float baseMultiplier = buffer.readFloat();

				materials.add(new MaterialGroup(ingredient, density, baseMultiplier));
			}

			ItemStack result = buffer.readItem();

			return new AmmunitionTableRecipe(recipeId, materials, result);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AmmunitionTableRecipe recipe) {
			buffer.writeInt(recipe.materials.size());
			recipe.materials.forEach(materialGroup -> {
				materialGroup.getIngredient().toNetwork(buffer);
				buffer.writeFloat(materialGroup.getDensity());
				buffer.writeFloat(materialGroup.getBaseMultiplier());
			});

			buffer.writeItem(recipe.result);
		}
	}

	public static class MaterialGroup {

		final Ingredient stack;
		final float density;
		final float baseMultiplier;

		/**
		 * Represents a group of materials within a recipe for the Ammunition Table.
		 *
		 * @param ingredient     an <code>Ingredient</code>
		 * @param density        the density of the material
		 * @param baseMultiplier the base multiplier for the material (how much this item is worth)
		 *                       <p>
		 *                       For example, an ingot may be 1x, while its nugget is 1/9th of that, or 0.11x.
		 */
		public MaterialGroup(Ingredient ingredient, float density, float baseMultiplier) {
			this.stack = ingredient;
			this.density = density;
			this.baseMultiplier = baseMultiplier;
		}

		public MaterialGroup(TagKey<Item> tagKey, float density, float baseMultiplier) {
			this(Ingredient.of(tagKey), density, baseMultiplier);
		}

		public Ingredient getIngredient() {
			return stack;
		}

		public float getDensity() {
			return density;
		}

		public float getBaseMultiplier() {
			return baseMultiplier;
		}
	}
}