package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

import java.util.List;

public class SmallPartsRecipe implements Recipe<RecipeInput> {

	private final Ingredient input;
	private final List<ItemStack> craftables;
	private final String group;
	@Nullable
	private PlacementInfo placementInfo;

	public SmallPartsRecipe(String group, Ingredient material, List<ItemStack> craftables) {
		this.group = group;
		this.input = material;
		this.craftables = craftables;
	}

	public String group() {
		return group;
	}

	public Ingredient input() {
		return input;
	}

	public List<ItemStack> craftables() {
		return craftables;
	}

	@Override
	public RecipeSerializer<SmallPartsRecipe> getSerializer() {
		return RecipeSerializerRegistry.SMALL_PARTS_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<SmallPartsRecipe> getType() {
		return RecipeTypeRegistry.SMALL_PARTS_RECIPE_TYPE.get();
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;  // TODO: custom category
	}

	@Override
	public boolean matches(RecipeInput input, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public PlacementInfo placementInfo() {
		if (placementInfo == null) {
			placementInfo = PlacementInfo.create(input);
		}

		return placementInfo;
	}

	public interface Factory<T extends SmallPartsRecipe> {
		T create(String group, Ingredient material, List<ItemStack> craftables);
	}

	public static class Serializer<T extends SmallPartsRecipe> implements RecipeSerializer<T> {

		private final MapCodec<T> codec;
		private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

		public Serializer(SmallPartsRecipe.Factory<T> factory) {
			codec = RecordCodecBuilder.mapCodec(
					instance -> instance.group(
							Codec.STRING.optionalFieldOf("group", "").forGetter(SmallPartsRecipe::group),
							Ingredient.CODEC.fieldOf("material").forGetter(SmallPartsRecipe::input),
							Codec.list(ItemStack.CODEC).fieldOf("craftables").forGetter(SmallPartsRecipe::craftables)
					).apply(instance, factory::create)
			);

			streamCodec = StreamCodec.composite(
					ByteBufCodecs.STRING_UTF8,
					SmallPartsRecipe::group,
					Ingredient.CONTENTS_STREAM_CODEC,
					SmallPartsRecipe::input,
					ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
					SmallPartsRecipe::craftables,
					factory::create
			);
		}

		@Override
		public MapCodec<T> codec() {
			return this.codec;
		}

		public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
			return this.streamCodec;
		}
	}
}