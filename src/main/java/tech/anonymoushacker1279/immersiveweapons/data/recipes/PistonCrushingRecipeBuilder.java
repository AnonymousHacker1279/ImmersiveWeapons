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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;

import javax.annotation.Nullable;

public class PistonCrushingRecipeBuilder {

	private final Block block;
	private final Item result;
	private final int minCount;
	private final int maxCount;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final RecipeSerializer<?> type;

	public PistonCrushingRecipeBuilder(RecipeSerializer<?> type, Block block, Item result, int minCount, int maxCount) {
		this.type = type;
		this.block = block;
		this.result = result;
		this.minCount = minCount;
		this.maxCount = maxCount;
	}

	public static PistonCrushingRecipeBuilder crushing(Block block, Item result, int minCount, int maxCount) {
		return new PistonCrushingRecipeBuilder(RecipeSerializerRegistry.PISTON_CRUSHING_RECIPE_SERIALIZER.get(), block, result, minCount, maxCount);
	}

	public PistonCrushingRecipeBuilder unlocks(String pName, Criterion<?> pCriterion) {
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
		output.accept(new PistonCrushingRecipeBuilder.Result(pId, type, block, result, minCount, maxCount, advancement,
				new ResourceLocation(pId.getNamespace(), "recipes/" + pId.getPath())));
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final Block block;
		private final Item result;
		private final int minCount;
		private final int maxCount;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

		public Result(ResourceLocation pId, RecipeSerializer<?> pType, Block block, Item result, int minCount, int maxCount,
		              Advancement.Builder advancement, ResourceLocation advancementId) {

			this.id = pId;
			this.type = pType;
			this.block = block;
			this.result = result;
			this.minCount = minCount;
			this.maxCount = maxCount;
			this.advancement = advancement;
			this.advancementId = advancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject pJson) {
			pJson.addProperty("block", BuiltInRegistries.BLOCK.getKey(block).toString());

			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", BuiltInRegistries.ITEM.getKey(result).toString());
			pJson.add("result", resultObject);

			pJson.addProperty("minCount", minCount);
			pJson.addProperty("maxCount", maxCount);
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