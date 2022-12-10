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
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.Objects;
import java.util.function.Consumer;

public class TeslaSynthesizerRecipeBuilder {

	private final Ingredient block;
	private final Ingredient material1;
	private final Ingredient material2;
	private final int cookTime;
	private final Item result;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final RecipeSerializer<?> type;

	public TeslaSynthesizerRecipeBuilder(RecipeSerializer<?> type, Ingredient block, Ingredient material1, Ingredient material2, int cookTime, Item result) {
		this.type = type;
		this.block = block;
		this.material1 = material1;
		this.material2 = material2;
		this.cookTime = cookTime;
		this.result = result;
	}

	public static TeslaSynthesizerRecipeBuilder synthesizing(Ingredient block, Ingredient material1, Ingredient material2, int cookTime, Item pResult) {
		return new TeslaSynthesizerRecipeBuilder(DeferredRegistryHandler.TESLA_SYNTHESIZER_RECIPE_SERIALIZER.get(), block, material1, material2, cookTime, pResult);
	}

	public TeslaSynthesizerRecipeBuilder unlocks(String pName, CriterionTriggerInstance pCriterion) {
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
		pFinishedRecipeConsumer.accept(new TeslaSynthesizerRecipeBuilder.Result(pId, this.type, this.block,
				this.material1, this.material2, this.cookTime, this.result, this.advancement,
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
		private final Ingredient block;
		private final Ingredient material1;
		private final Ingredient material2;
		private final int cookTime;
		private final Item result;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

		public Result(ResourceLocation pId, RecipeSerializer<?> pType, Ingredient block, Ingredient material1,
		              Ingredient material2, int cookTime, Item result, Advancement.Builder pAdvancement,
		              ResourceLocation pAdvancementId) {
			this.id = pId;
			this.type = pType;
			this.block = block;
			this.material1 = material1;
			this.material2 = material2;
			this.cookTime = cookTime;
			this.result = result;
			this.advancement = pAdvancement;
			this.advancementId = pAdvancementId;
		}

		public void serializeRecipeData(JsonObject pJson) {
			pJson.add("block", this.block.toJson());
			pJson.add("material1", this.material1.toJson());
			pJson.add("material2", this.material2.toJson());
			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
			pJson.add("result", resultObject);
			pJson.addProperty("cookTime", this.cookTime);
		}

		/**
		 * Gets the ID for the recipe.
		 */
		public ResourceLocation getId() {
			return this.id;
		}

		public RecipeSerializer<?> getType() {
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