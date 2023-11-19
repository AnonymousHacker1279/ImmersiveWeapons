package tech.anonymoushacker1279.immersiveweapons.data.recipes;

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
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;

import javax.annotation.Nullable;

public class AstralCrystalRecipeBuilder {

	private final Ingredient primaryMaterial;
	private final Ingredient secondaryMaterial;
	private final Item result;
	private final int resultCount;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final RecipeSerializer<?> type;

	public AstralCrystalRecipeBuilder(RecipeSerializer<?> type, Ingredient primaryMaterial, Ingredient secondaryMaterial, Item result, int resultCount) {
		this.type = type;
		this.primaryMaterial = primaryMaterial;
		this.secondaryMaterial = secondaryMaterial;
		this.resultCount = resultCount;
		this.result = result;
	}

	public static AstralCrystalRecipeBuilder sorcery(Ingredient primaryMaterial, Ingredient secondaryMaterial, Item result, int resultCount) {
		return new AstralCrystalRecipeBuilder(RecipeSerializerRegistry.ASTRAL_CRYSTAL_RECIPE_SERIALIZER.get(), primaryMaterial, secondaryMaterial, result, resultCount);
	}

	public AstralCrystalRecipeBuilder unlocks(String pName, Criterion<?> pCriterion) {
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
		output.accept(new AstralCrystalRecipeBuilder.Result(pId, type, primaryMaterial, secondaryMaterial, result, resultCount, advancement,
				new ResourceLocation(pId.getNamespace(), "recipes/" + pId.getPath())));
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final Ingredient primaryMaterial;
		private final Ingredient secondaryMaterial;
		private final Item result;
		private final int resultCount;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

		public Result(ResourceLocation pId, RecipeSerializer<?> pType, Ingredient primaryMaterial,
		              Ingredient secondaryMaterial, Item result, int resultCount, Advancement.Builder pAdvancement,
		              ResourceLocation pAdvancementId) {
			id = pId;
			type = pType;
			this.primaryMaterial = primaryMaterial;
			this.secondaryMaterial = secondaryMaterial;
			this.resultCount = resultCount;
			this.result = result;
			advancement = pAdvancement;
			advancementId = pAdvancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject pJson) {
			pJson.add("primaryMaterial", primaryMaterial.toJson(true));
			pJson.add("secondaryMaterial", secondaryMaterial.toJson(true));

			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", BuiltInRegistries.ITEM.getKey(result).toString());
			pJson.add("result", resultObject);

			pJson.addProperty("resultCount", resultCount);
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