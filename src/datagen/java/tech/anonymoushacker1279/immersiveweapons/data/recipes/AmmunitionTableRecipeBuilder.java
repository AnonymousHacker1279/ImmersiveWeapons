package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe.MaterialGroup;

import java.util.*;
import java.util.function.Consumer;

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

	public AmmunitionTableRecipeBuilder unlocks(String pName, CriterionTriggerInstance pCriterion) {
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
		pFinishedRecipeConsumer.accept(new AmmunitionTableRecipeBuilder.Result(pId, type, materials, result, advancement,
				new ResourceLocation(pId.getNamespace(), "recipes/" + pId.getPath())));
	}

	private void ensureValid(ResourceLocation pId) {
		if (advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + pId);
		}
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
				jsonObject.add("ingredient", group.getIngredient().toJson());
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
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return type;
		}

		@Override
		@Nullable
		public JsonObject serializeAdvancement() {
			return advancement.serializeToJson();
		}

		@Override
		@Nullable
		public ResourceLocation getAdvancementId() {
			return advancementId;
		}
	}
}