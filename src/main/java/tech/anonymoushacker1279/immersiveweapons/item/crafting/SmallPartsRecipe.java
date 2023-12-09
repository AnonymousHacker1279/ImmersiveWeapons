package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.ArrayList;
import java.util.List;

public class SmallPartsRecipe implements Recipe<Container> {

	public final Ingredient material;
	public final List<Item> craftables;
	protected final String group;

	public SmallPartsRecipe(String group, Ingredient material, List<Item> craftables) {
		this.material = material;
		this.craftables = craftables;
		this.group = group;
	}

	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return new ItemStack(Items.AIR);
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
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

	public static class Serializer<T extends SmallPartsRecipe> implements RecipeSerializer<T> {

		private final SmallPartsRecipe.Factory<T> factory;
		private final Codec<T> codec;

		public Serializer(SmallPartsRecipe.Factory<T> factory) {
			this.factory = factory;
			this.codec = RecordCodecBuilder.create(
					instance -> instance.group(
							ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
							Ingredient.CODEC.fieldOf("material").forGetter(recipe -> recipe.material),
							Codec.list(Codec.STRING).fieldOf("craftables").forGetter(recipe -> {
								List<String> craftables = new ArrayList<>(recipe.craftables.size());
								for (Item item : recipe.craftables) {
									craftables.add(BuiltInRegistries.ITEM.getKey(item).toString());
								}
								return craftables;
							})
					).apply(instance, (group, material, craftables) -> {
						List<Item> craftables1 = new ArrayList<>(craftables.size());
						for (String s : craftables) {
							craftables1.add(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(s)));
						}
						return factory.create(group, material, craftables1);
					})
			);
		}

		@Override
		public Codec<T> codec() {
			return codec;
		}

		@Override
		public T fromNetwork(FriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			Ingredient material = Ingredient.fromNetwork(buffer);

			String craft = buffer.readUtf();
			String s = craft.replace("[", "").replace("]", "").replace(" ", "");
			String[] s1 = s.split(",");

			List<Item> craftables = new ArrayList<>(s1.length);
			for (String s2 : s1) {
				craftables.add(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(s2)));
			}

			return factory.create(group, material, craftables);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, SmallPartsRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.material.toNetwork(buffer);
			List<ResourceLocation> craftables = new ArrayList<>(recipe.craftables.size());
			for (Item item : recipe.craftables) {
				craftables.add(BuiltInRegistries.ITEM.getKey(item));
			}
			buffer.writeUtf(craftables.toString());
		}
	}
}