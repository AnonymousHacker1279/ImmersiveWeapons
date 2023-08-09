package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;

import java.util.Objects;
import java.util.function.Consumer;

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

	public PistonCrushingRecipeBuilder unlocks(String pName, CriterionTriggerInstance pCriterion) {
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
		pFinishedRecipeConsumer.accept(new PistonCrushingRecipeBuilder.Result(pId, type, block, result, minCount, maxCount, advancement,
				new ResourceLocation(pId.getNamespace(), "recipes/" + pId.getPath())));
	}

	private void ensureValid(ResourceLocation pId) {
		if (advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + pId);
		}
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
			pJson.addProperty("block", Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).toString());

			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result)).toString());
			pJson.add("result", resultObject);

			pJson.addProperty("minCount", minCount);
			pJson.addProperty("maxCount", maxCount);
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