package com.anonymoushacker1279.immersiveweapons.data.recipes;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class SmallPartsRecipeBuilder {

	private final Ingredient material;
	private final Ingredient blueprint;
	private final Item result;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final RecipeSerializer<?> type;

	public SmallPartsRecipeBuilder(RecipeSerializer<?> type, Ingredient material, Ingredient blueprint, Item result) {
		this.type = type;
		this.material = material;
		this.blueprint = blueprint;
		this.result = result;
	}

	public static SmallPartsRecipeBuilder tinker(Ingredient material, Ingredient blueprint, Item pResult) {
		return new SmallPartsRecipeBuilder(DeferredRegistryHandler.SMALL_PARTS_RECIPE_SERIALIZER.get(), material, blueprint, pResult);
	}

	public SmallPartsRecipeBuilder unlocks(String pName, CriterionTriggerInstance pCriterion) {
		this.advancement.addCriterion(pName, pCriterion);
		return this;
	}

	public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pId) {
		this.save(pFinishedRecipeConsumer, new ResourceLocation(pId));
	}

	public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pId) {
		this.ensureValid(pId);
		this.advancement.parent(new ResourceLocation("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
				.rewards(AdvancementRewards.Builder.recipe(pId)).requirements(RequirementsStrategy.OR);
		pFinishedRecipeConsumer.accept(new SmallPartsRecipeBuilder.Result(pId, this.type, this.material,
				this.blueprint, this.result, this.advancement,
				new ResourceLocation(pId.getNamespace(), "recipes/" +
						Objects.requireNonNull(this.result.getItemCategory()).getRecipeFolderName() + "/" + pId.getPath())));
	}

	private void ensureValid(ResourceLocation pId) {
		if (this.advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + pId);
		}
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final Ingredient material;
		private final Ingredient blueprint;
		private final Item result;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

		public Result(ResourceLocation pId, RecipeSerializer<?> pType, Ingredient material, Ingredient blueprint,
		              Item result, Advancement.Builder pAdvancement,
		              ResourceLocation pAdvancementId) {
			this.id = pId;
			this.type = pType;
			this.material = material;
			this.blueprint = blueprint;
			this.result = result;
			this.advancement = pAdvancement;
			this.advancementId = pAdvancementId;
		}

		public void serializeRecipeData(JsonObject pJson) {
			pJson.add("material", this.material.toJson());
			pJson.add("blueprint", this.blueprint.toJson());
			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
			pJson.add("result", resultObject);
		}

		/**
		 * Gets the ID for the recipe.
		 */
		public @NotNull ResourceLocation getId() {
			return this.id;
		}

		public @NotNull RecipeSerializer<?> getType() {
			return this.type;
		}

		/**
		 * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
		 */
		@Nullable
		public JsonObject serializeAdvancement() {
			return this.advancement.serializeToJson();
		}

		/**
		 * Gets the ID for the advancement associated with this recipe.
		 */
		@Nullable
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}
	}
}