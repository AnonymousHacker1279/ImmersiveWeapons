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
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;

import javax.annotation.Nullable;

public class StarForgeRecipeBuilder {

	private final Ingredient ingot;
	private final int ingotCount;
	private final Ingredient secondaryMaterial;
	private final int secondaryMaterialCount;
	private final Item result;
	private final int smeltTime;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final RecipeSerializer<?> type;

	public StarForgeRecipeBuilder(RecipeSerializer<?> type, Ingredient ingot, int ingotCount, Ingredient secondaryMaterial, int secondaryMaterialCount, Item result, int smeltTime) {
		this.type = type;
		this.ingot = ingot;
		this.ingotCount = ingotCount;
		this.secondaryMaterial = secondaryMaterial;
		this.secondaryMaterialCount = secondaryMaterialCount;
		this.result = result;
		this.smeltTime = smeltTime;
	}

	public static StarForgeRecipeBuilder forge(Ingredient ingot, int ingotCount, Ingredient secondaryMaterial, int secondaryMaterialCount, Item result, int smeltTime) {
		return new StarForgeRecipeBuilder(RecipeSerializerRegistry.STAR_FORGE_RECIPE_SERIALIZER.get(), ingot, ingotCount, secondaryMaterial, secondaryMaterialCount, result, smeltTime);
	}

	public StarForgeRecipeBuilder unlocks(String pName, Criterion<?> pCriterion) {
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
		output.accept(new StarForgeRecipeBuilder.Result(id,
				type, ingot, ingotCount, secondaryMaterial, secondaryMaterialCount, result, smeltTime, advancement,
				new ResourceLocation(id.getNamespace(), "recipes/" + ImmersiveWeapons.MOD_ID + "/" + id.getPath())));
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final Ingredient ingot;
		private final int ingotCount;
		private final Ingredient secondaryMaterial;
		private final int secondaryMaterialCount;
		private final Item result;
		private final int smeltTime;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

		public Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient ingot, int ingotCount,
		              Ingredient secondaryMaterial, int secondaryMaterialCount, Item result, int smeltTime,
		              Advancement.Builder advancement, ResourceLocation advancementId) {
			this.id = id;
			this.type = type;
			this.ingot = ingot;
			this.ingotCount = ingotCount;
			this.secondaryMaterial = secondaryMaterial;
			this.secondaryMaterialCount = secondaryMaterialCount;
			this.result = result;
			this.smeltTime = smeltTime;
			this.advancement = advancement;
			this.advancementId = advancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			json.add("ingot", ingot.toJson(true));
			json.addProperty("ingot_count", ingotCount);

			json.add("secondary_material", secondaryMaterial.toJson(true));
			json.addProperty("secondary_material_count", secondaryMaterialCount);

			json.addProperty("smelt_time", smeltTime);

			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", BuiltInRegistries.ITEM.getKey(result).toString());
			json.add("result", resultObject);
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