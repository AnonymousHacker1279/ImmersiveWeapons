package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

import java.util.List;

public class AmmunitionTableRecipe implements Recipe<RecipeInput> {

	public static final MapCodec<AmmunitionTableRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
							Codec.STRING.optionalFieldOf("group", "").forGetter(AmmunitionTableRecipe::group),
							Codec.list(MaterialGroup.CODEC).fieldOf("materials").forGetter(AmmunitionTableRecipe::materials),
							ItemStackTemplate.CODEC.fieldOf("result").forGetter(AmmunitionTableRecipe::result)
					)
					.apply(instance, AmmunitionTableRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, AmmunitionTableRecipe> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			AmmunitionTableRecipe::group,
			MaterialGroup.STREAM_CODEC.apply(ByteBufCodecs.list()),
			AmmunitionTableRecipe::materials,
			ItemStackTemplate.STREAM_CODEC,
			AmmunitionTableRecipe::result,
			AmmunitionTableRecipe::new
	);
	public static final RecipeSerializer<AmmunitionTableRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final String group;
	private final List<MaterialGroup> materials;
	private final ItemStackTemplate result;

	@Nullable
	private PlacementInfo placementInfo;

	public AmmunitionTableRecipe(String group, List<MaterialGroup> materials, ItemStackTemplate result) {
		this.group = group;
		this.materials = materials;
		this.result = result;
	}

	public String group() {
		return group;
	}

	public ItemStackTemplate result() {
		return result;
	}

	public List<MaterialGroup> materials() {
		return materials;
	}

	@Override
	public boolean matches(RecipeInput input, Level level) {
		for (int i = 0; i < materials.size(); i++) {
			ItemStack itemStack = input.getItem(i);
			MaterialGroup materialGroup = materials.get(i);

			if (materialGroup.ingredient().test(itemStack)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack assemble(RecipeInput input) {
		return result.create();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public boolean showNotification() {
		return false;
	}

	@Override
	public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
		return RecipeSerializerRegistry.AMMUNITION_TABLE_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<? extends Recipe<RecipeInput>> getType() {
		return RecipeTypeRegistry.AMMUNITION_TABLE_RECIPE_TYPE.get();
	}

	@Override
	public PlacementInfo placementInfo() {
		if (placementInfo == null) {
			placementInfo = PlacementInfo.create(materials.getFirst().ingredient());
		}

		return placementInfo;
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}

	public record MaterialGroup(Ingredient ingredient, float density, float baseMultiplier) {

		private static final Codec<MaterialGroup> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Ingredient.CODEC.fieldOf("ingredient").forGetter(materialGroup -> materialGroup.ingredient),
								Codec.FLOAT.fieldOf("density").forGetter(materialGroup -> materialGroup.density),
								Codec.FLOAT.fieldOf("base_multiplier").forGetter(materialGroup -> materialGroup.baseMultiplier)
						)
						.apply(instance, MaterialGroup::new)
		);
		private static final StreamCodec<RegistryFriendlyByteBuf, MaterialGroup> STREAM_CODEC = StreamCodec.composite(
				Ingredient.CONTENTS_STREAM_CODEC,
				MaterialGroup::ingredient,
				ByteBufCodecs.FLOAT,
				MaterialGroup::density,
				ByteBufCodecs.FLOAT,
				MaterialGroup::baseMultiplier,
				MaterialGroup::new
		);

		/**
		 * Represents a group of materials within a recipe for the Ammunition Table.
		 *
		 * @param ingredient     an <code>Ingredient</code>
		 * @param density        the density of the material
		 * @param baseMultiplier the base multiplier for the material (how much this item is worth)
		 *                       <p>
		 *                       For example, an ingot may be 1x, while its nugget is 1/9th of that, or 0.11x.
		 */
		public MaterialGroup {
		}

		public MaterialGroup(TagKey<Item> tagKey, float density, float baseMultiplier) {
			this(Ingredient.of(HolderSet.emptyNamed(BuiltInRegistries.ITEM, tagKey)), density, baseMultiplier);
		}
	}
}