package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;

import javax.annotation.Nullable;
import java.util.Objects;

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
		return new TeslaSynthesizerRecipeBuilder(RecipeSerializerRegistry.TESLA_SYNTHESIZER_RECIPE_SERIALIZER.get(), block, material1, material2, cookTime, pResult);
	}

	public TeslaSynthesizerRecipeBuilder unlocks(String pName, Criterion<?> pCriterion) {
		advancement.addCriterion(pName, pCriterion);
		return this;
	}

	public void save(RecipeOutput output, String pId) {
		save(output, new ResourceLocation(pId));
	}

	public void save(RecipeOutput output, ResourceLocation pId) {
		advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
				.rewards(AdvancementRewards.Builder.recipe(pId)).requirements(Strategy.OR);
		output.accept(new TeslaSynthesizerRecipeBuilder.Result(pId, type, block,
				material1, material2, cookTime, result, advancement,
				new ResourceLocation(pId.getNamespace(), "recipes/" + pId.getPath())));
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
			id = pId;
			type = pType;
			this.block = block;
			this.material1 = material1;
			this.material2 = material2;
			this.cookTime = cookTime;
			this.result = result;
			advancement = pAdvancement;
			advancementId = pAdvancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject pJson) {
			pJson.add("block", block.toJson(true));
			pJson.add("material1", material1.toJson(true));
			pJson.add("material2", material2.toJson(true));
			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result)).toString());
			pJson.add("result", resultObject);
			pJson.addProperty("cookTime", cookTime);
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