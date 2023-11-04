package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;

import javax.annotation.Nullable;
import java.util.Objects;

public class BarrelTapRecipeBuilder {

	private final Ingredient material;
	private final int materialCount;
	private final Item result;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final RecipeSerializer<?> type;

	public BarrelTapRecipeBuilder(RecipeSerializer<?> type, Ingredient material, int materialCount, Item result) {
		this.type = type;
		this.material = material;
		this.materialCount = materialCount;
		this.result = result;
	}

	public static BarrelTapRecipeBuilder fermenting(Ingredient block, int materialCount, Item pResult) {
		return new BarrelTapRecipeBuilder(RecipeSerializerRegistry.BARREL_TAP_RECIPE_SERIALIZER.get(), block, materialCount, pResult);
	}

	public BarrelTapRecipeBuilder unlocks(String pName, Criterion<?> pCriterion) {
		advancement.addCriterion(pName, pCriterion);
		return this;
	}

	public void save(RecipeOutput output, String pId) {
		save(output, new ResourceLocation(pId));
	}

	public void save(RecipeOutput output, ResourceLocation pId) {
		advancement.parent(new ResourceLocation("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
				.rewards(AdvancementRewards.Builder.recipe(pId)).requirements(Strategy.OR);
		output.accept(new BarrelTapRecipeBuilder.Result(pId, type, material, materialCount, result, advancement,
				new ResourceLocation(pId.getNamespace(), "recipes/" + pId.getPath())));
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final Ingredient material;
		private final int materialCount;
		private final Item result;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

		public Result(ResourceLocation pId, RecipeSerializer<?> pType, Ingredient material,
		              int materialCount, Item result, Advancement.Builder pAdvancement,
		              ResourceLocation pAdvancementId) {
			id = pId;
			type = pType;
			this.material = material;
			this.materialCount = materialCount;
			this.result = result;
			advancement = pAdvancement;
			advancementId = pAdvancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject pJson) {
			pJson.add("material", material.toJson(true));
			pJson.addProperty("materialCount", materialCount);
			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result)).toString());
			pJson.add("result", resultObject);
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