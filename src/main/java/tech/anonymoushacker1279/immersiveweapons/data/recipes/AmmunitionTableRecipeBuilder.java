package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe.MaterialGroup;

import javax.annotation.Nullable;
import java.util.*;

public class AmmunitionTableRecipeBuilder {

	private final List<MaterialGroup> materials = new ArrayList<>(5);
	private final Item result;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final RecipeSerializer<?> type;

	public AmmunitionTableRecipeBuilder(RecipeSerializer<?> type, List<MaterialGroup> materials, Item result) {
		this.type = type;
		this.materials.addAll(materials);
		this.result = result;
	}

	public static AmmunitionTableRecipeBuilder crafting(List<MaterialGroup> materials, Item result) {
		return new AmmunitionTableRecipeBuilder(RecipeSerializerRegistry.AMMUNITION_TABLE_RECIPE_SERIALIZER.get(), materials, result);
	}

	public AmmunitionTableRecipeBuilder unlocks(String pName, Criterion<?> pCriterion) {
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
		output.accept(new AmmunitionTableRecipeBuilder.Result(pId, type, materials, result, advancement,
				new ResourceLocation(pId.getNamespace(), "recipes/" + pId.getPath())));
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final List<MaterialGroup> materials;
		private final Item result;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

		public Result(ResourceLocation pId, RecipeSerializer<?> pType, List<MaterialGroup> materials,
		              Item result, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
			id = pId;
			type = pType;
			this.materials = materials;
			this.result = result;
			advancement = pAdvancement;
			advancementId = pAdvancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject pJson) {
			JsonArray jsonMaterialsArray = new JsonArray();
			for (MaterialGroup group : materials) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.add("ingredient", group.getIngredient().toJson(true));
				jsonObject.addProperty("density", group.getDensity());
				jsonObject.addProperty("base_multiplier", group.getBaseMultiplier());
				jsonMaterialsArray.add(jsonObject);
			}

			pJson.add("materials", jsonMaterialsArray);

			JsonObject resultObject = new JsonObject();
			resultObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result)).toString());
			pJson.add("result", resultObject);
		}

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