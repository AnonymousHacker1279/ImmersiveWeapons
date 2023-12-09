package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.advancements.*;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import javax.annotation.Nullable;
import java.util.*;

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
	public void save(RecipeOutput output, ResourceLocation location) {
		Advancement.Builder advancementBuilder = output.advancement()
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location))
				.rewards(AdvancementRewards.Builder.recipe(location))
				.requirements(Strategy.OR);

		criteria.forEach(advancementBuilder::addCriterion);

		output.accept(location, getRecipe(), advancementBuilder.build(location.withPrefix("recipes/" + ImmersiveWeapons.MOD_ID + "/" + Objects.requireNonNullElse(group, getRecipe().getGroup()) + "/")));
	}

	protected abstract Recipe<?> getRecipe();
}