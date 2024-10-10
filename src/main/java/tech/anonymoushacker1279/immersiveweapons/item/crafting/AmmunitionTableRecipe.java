package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.ArrayList;
import java.util.List;

public record AmmunitionTableRecipe(String group, List<MaterialGroup> materials,
                                    ItemStack result) implements Recipe<RecipeInput> {

	@Override
	public boolean matches(RecipeInput input, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(RecipeInput input, Provider registries) {
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getResultItem(Provider provider) {
		return result.copy();
	}

	public List<MaterialGroup> getMaterials() {
		return materials;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.AMMUNITION_TABLE.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.AMMUNITION_TABLE_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.AMMUNITION_TABLE_RECIPE_TYPE.get();
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (MaterialGroup group : materials) {
			ingredients.add(group.getIngredient());
		}
		return ingredients;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public interface Factory<T extends AmmunitionTableRecipe> {
		T create(String group, List<MaterialGroup> materials, ItemStack result);
	}

	public static class Serializer implements RecipeSerializer<AmmunitionTableRecipe> {

		private static final MapCodec<AmmunitionTableRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								Codec.list(MaterialGroup.CODEC).fieldOf("materials").forGetter(recipe -> recipe.materials),
								ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
						)
						.apply(instance, AmmunitionTableRecipe::new)
		);

		private static final StreamCodec<RegistryFriendlyByteBuf, AmmunitionTableRecipe> STREAM_CODEC = StreamCodec.of(
				AmmunitionTableRecipe.Serializer::toNetwork, AmmunitionTableRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<AmmunitionTableRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, AmmunitionTableRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static AmmunitionTableRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			String group = buffer.readUtf();

			List<MaterialGroup> materials = new ArrayList<>(5);
			int materialCount = buffer.readInt();
			for (int i = 0; i < materialCount; i++) {
				Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
				float density = buffer.readFloat();
				float baseMultiplier = buffer.readFloat();

				materials.add(new MaterialGroup(ingredient, density, baseMultiplier));
			}

			ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);

			return new AmmunitionTableRecipe(group, materials, result);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, AmmunitionTableRecipe recipe) {
			buffer.writeUtf(recipe.group);
			buffer.writeInt(recipe.materials.size());
			recipe.materials.forEach(materialGroup -> {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, materialGroup.getIngredient());
				buffer.writeFloat(materialGroup.getDensity());
				buffer.writeFloat(materialGroup.getBaseMultiplier());
			});

			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
		}
	}

	public record MaterialGroup(Ingredient stack, float density, float baseMultiplier) {

		/**
		 * Represents a group of materials within a recipe for the Ammunition Table.
		 *
		 * @param stack          an <code>Ingredient</code>
		 * @param density        the density of the material
		 * @param baseMultiplier the base multiplier for the material (how much this item is worth)
		 *                       <p>
		 *                       For example, an ingot may be 1x, while its nugget is 1/9th of that, or 0.11x.
		 */
		public MaterialGroup {
		}

		private static final Codec<MaterialGroup> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Ingredient.CODEC.fieldOf("ingredient").forGetter(materialGroup -> materialGroup.stack),
								Codec.FLOAT.fieldOf("density").forGetter(materialGroup -> materialGroup.density),
								Codec.FLOAT.fieldOf("base_multiplier").forGetter(materialGroup -> materialGroup.baseMultiplier)
						)
						.apply(instance, MaterialGroup::new)
		);

		public MaterialGroup(TagKey<Item> tagKey, float density, float baseMultiplier) {
			this(Ingredient.of(tagKey), density, baseMultiplier);
		}

		public Ingredient getIngredient() {
			return stack;
		}

		public float getDensity() {
			return density;
		}

		public float getBaseMultiplier() {
			return baseMultiplier;
		}
	}
}