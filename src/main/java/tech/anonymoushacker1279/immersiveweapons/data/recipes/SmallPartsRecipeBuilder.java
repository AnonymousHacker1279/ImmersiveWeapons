package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import com.google.gson.JsonArray;
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
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.List;
import java.util.function.Consumer;

public class SmallPartsRecipeBuilder {

	private final Ingredient material;
	private final List<Item> craftables;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final RecipeSerializer<?> type;

	public SmallPartsRecipeBuilder(RecipeSerializer<?> type, Ingredient material, List<Item> craftables) {
		this.type = type;
		this.material = material;
		this.craftables = craftables;
	}

	public static SmallPartsRecipeBuilder tinker(Ingredient material, List<Item> craftables) {
		return new SmallPartsRecipeBuilder(DeferredRegistryHandler.SMALL_PARTS_RECIPE_SERIALIZER.get(), material, craftables);
	}

	public SmallPartsRecipeBuilder unlocks(String pName, CriterionTriggerInstance pCriterion) {
		advancement.addCriterion(pName, pCriterion);
		return this;
	}

	public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pId) {
		save(pFinishedRecipeConsumer, new ResourceLocation(pId));
	}

	public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation id) {
		ensureValid(id);
		advancement.parent(new ResourceLocation("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
		pFinishedRecipeConsumer.accept(new SmallPartsRecipeBuilder.Result(id,
				type, material, craftables, advancement,
				new ResourceLocation(id.getNamespace(), "recipes/" + ImmersiveWeapons.MOD_ID + "/" + id.getPath())));
	}

	private void ensureValid(ResourceLocation pId) {
		if (advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + pId);
		}
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final Ingredient material;
		private final List<Item> craftables;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<?> type;

		public Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient material,
		              List<Item> craftables, Advancement.Builder advancement,
		              ResourceLocation advancementId) {
			this.id = id;
			this.type = type;
			this.material = material;
			this.craftables = craftables;
			this.advancement = advancement;
			this.advancementId = advancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			json.add("material", material.toJson());
			JsonArray resultArray = new JsonArray();

			for (Item item : craftables) {
				resultArray.add(String.valueOf(ForgeRegistries.ITEMS.getKey(item)));
			}

			json.add("craftables", resultArray);
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