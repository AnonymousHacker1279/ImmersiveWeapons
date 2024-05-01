package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.ArrayList;
import java.util.List;

public record SmallPartsRecipe(String group, Ingredient material, List<Item> craftables) implements Recipe<Container> {

	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, Provider provider) {
		return new ItemStack(Items.AIR);
	}

	@Override
	public ItemStack getResultItem(Provider provider) {
		return new ItemStack(Items.AIR);
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BlockRegistry.SMALL_PARTS_TABLE.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.SMALL_PARTS_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.SMALL_PARTS_RECIPE_TYPE.get();
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(material);
		return defaultedList;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public interface Factory<T extends SmallPartsRecipe> {
		T create(String group, Ingredient material, List<Item> craftables);
	}

	public static class Serializer implements RecipeSerializer<SmallPartsRecipe> {

		private static final MapCodec<SmallPartsRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
						Ingredient.CODEC.fieldOf("material").forGetter(recipe -> recipe.material),
						Codec.list(ResourceLocation.CODEC).fieldOf("craftables").forGetter(recipe -> {
							List<ResourceLocation> craftables = new ArrayList<>(recipe.craftables.size());
							for (Item item : recipe.craftables) {
								craftables.add(BuiltInRegistries.ITEM.getKey(item));
							}

							return craftables;
						})
				).apply(instance, (group, material, craftables) -> {
					List<Item> items = new ArrayList<>(craftables.size());
					for (ResourceLocation id : craftables) {
						items.add(BuiltInRegistries.ITEM.get(id));
					}

					return new SmallPartsRecipe(group, material, items);
				})
		);

		private static final StreamCodec<RegistryFriendlyByteBuf, SmallPartsRecipe> STREAM_CODEC = StreamCodec.of(
				SmallPartsRecipe.Serializer::toNetwork, SmallPartsRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<SmallPartsRecipe> codec() {
			return CODEC;
		}

		public StreamCodec<RegistryFriendlyByteBuf, SmallPartsRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static SmallPartsRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			Ingredient material = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			List<ResourceLocation> resourceLocations = ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buffer);
			List<Item> items = new ArrayList<>(resourceLocations.size());
			for (ResourceLocation id : resourceLocations) {
				items.add(BuiltInRegistries.ITEM.get(id));
			}

			return new SmallPartsRecipe(group, material, items);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, SmallPartsRecipe recipe) {
			buffer.writeUtf(recipe.group);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.material);
			List<ResourceLocation> resourceLocations = new ArrayList<>(recipe.craftables.size());
			for (Item item : recipe.craftables) {
				resourceLocations.add(BuiltInRegistries.ITEM.getKey(item));
			}

			ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()).encode(buffer, resourceLocations);
		}
	}
}