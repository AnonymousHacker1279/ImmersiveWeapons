package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;

import java.util.Objects;
import java.util.function.Consumer;

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

	public AstralCrystalRecipeBuilder unlocks(String pName, CriterionTriggerInstance pCriterion) {
		advancement.addCriterion(pName, pCriterion);
		return this;
	}

	public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pId) {
		save(pFinishedRecipeConsumer, new ResourceLocation(pId));
	}

	public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pId) {
		ensureValid(pId);
		advancement.parent(new ResourceLocation("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
				.rewards(AdvancementRewards.Builder.recipe(pId)).requirements(RequirementsStrategy.OR);
		pFinishedRecipeConsumer.accept(new AstralCrystalRecipeBuilder.Result(pId, type, primaryMaterial, secondaryMaterial, result, resultCount, advancement,
				new ResourceLocation(pId.getNamespace(), "recipes/" + pId.getPath())));
	}

	private void ensureValid(ResourceLocation pId) {
		if (advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + pId);
		}
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
			pJson.add("primaryMaterial", primaryMaterial.toJson());
			pJson.add("secondaryMaterial", secondaryMaterial.toJson());

			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result)).toString());
			pJson.add("result", resultObject);

			pJson.addProperty("resultCount", resultCount);
		}

		/**
		 * Gets the ID for the recipe.
		 */
		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return type;
		}

		/**
		 * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
		 */
		@Override
		@Nullable
		public JsonObject serializeAdvancement() {
			return advancement.serializeToJson();
		}

		/**
		 * Gets the ID for the advancement associated with this recipe.
		 */
		@Override
		@Nullable
		public ResourceLocation getAdvancementId() {
			return advancementId;
		}
	}
}