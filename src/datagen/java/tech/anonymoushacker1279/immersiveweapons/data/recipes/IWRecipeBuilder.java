package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public abstract class IWRecipeBuilder implements RecipeBuilder {

	protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>(5);
	@Nullable
	protected String group;

	@Override
	public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		criteria.put(name, criterion);
		return this;
	}

	@Override
	public RecipeBuilder group(@Nullable String groupName) {
		group = groupName;
		return this;
	}

	@Override
	public void save(RecipeOutput output, ResourceKey<Recipe<?>> resourceKey) {
		Advancement.Builder advancementBuilder = output.advancement()
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
				.rewards(AdvancementRewards.Builder.recipe(resourceKey))
				.requirements(Strategy.OR);

		criteria.forEach(advancementBuilder::addCriterion);

		output.accept(resourceKey,
				getRecipe(),
				advancementBuilder.build(resourceKey.location()
						.withPrefix("recipes/" + ImmersiveWeapons.MOD_ID + "/" + Objects.requireNonNullElse(group, getRecipe().group()) + "/")));
	}

	protected abstract Recipe<?> getRecipe();
}