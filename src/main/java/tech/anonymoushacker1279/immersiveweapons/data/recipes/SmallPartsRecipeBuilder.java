package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class SmallPartsRecipeBuilder {

	private final Ingredient material;
	private final List<Item> craftables;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final RecipeSerializer<?> type;

	public SmallPartsRecipeBuilder(RecipeSerializer<?> type, Ingredient material, List<Item> craftables) {
		this.type = type;
		this.material = material;
		this.craftables = craftables;
	}

	public static SmallPartsRecipeBuilder tinker(Ingredient material, List<Item> craftables) {
		return new SmallPartsRecipeBuilder(RecipeSerializerRegistry.SMALL_PARTS_RECIPE_SERIALIZER.get(), material, craftables);
	}

	public SmallPartsRecipeBuilder unlocks(String pName, Criterion<?> pCriterion) {
		advancement.addCriterion(pName, pCriterion);
		return this;
	}

	public void save(RecipeOutput output, String pId) {
		save(output, new ResourceLocation(pId));
	}

	public void save(RecipeOutput output, ResourceLocation id) {
		advancement.parent(new ResourceLocation("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id)).requirements(Strategy.OR);
		output.accept(new SmallPartsRecipeBuilder.Result(id,
				type, material, craftables, advancement,
				new ResourceLocation(id.getNamespace(), "recipes/" + ImmersiveWeapons.MOD_ID + "/" + id.getPath())));
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final Ingredient material;
		private final List<Item> craftables;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

		public Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient material,
		              List<Item> craftables, Advancement.Builder advancement,
		              ResourceLocation advancementId) {
			this.id = id;
			this.type = type;
			this.material = material;
			this.craftables = craftables;
			this.advancement = advancement;
			this.advancementId = advancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			json.add("material", material.toJson(true));
			JsonArray resultArray = new JsonArray();

			for (Item item : craftables) {
				resultArray.add(String.valueOf(BuiltInRegistries.ITEM.getKey(item)));
			}

			json.add("craftables", resultArray);
		}

		/**
		 * Gets the ID for the recipe.
		 */
		@Override
		public ResourceLocation id() {
			return id;
		}

		@Override
		public RecipeSerializer<?> type() {
			return type;
		}

		@Nullable
		@Override
		public AdvancementHolder advancement() {
			return advancement.build(advancementId);
		}
	}
}