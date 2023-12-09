package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

public class PistonCrushingRecipe implements Recipe<Container> {

	protected final ResourceLocation block;
	protected final ItemStack result;
	protected final int minCount;
	protected final int maxCount;
	protected final String group;

	public PistonCrushingRecipe(String group, ResourceLocation block, ItemStack result, int minCount, int maxCount) {
		this.block = block;
		this.result = result;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.group = group;
	}

	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	public boolean matches(Block block) {
		return this.block.equals(BuiltInRegistries.BLOCK.getKey(block));
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return result;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	public Block getBlock() {
		return BuiltInRegistries.BLOCK.get(block);
	}

	/**
	 * Get a random drop amount based on the min and max count (inclusive).
	 */
	public int getRandomDropAmount() {
		return minCount + (int) (Math.random() * ((maxCount - minCount) + 1));
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(Blocks.PISTON);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.PISTON_CRUSHING_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.PISTON_CRUSHING_RECIPE_TYPE.get();
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public interface Factory<T extends PistonCrushingRecipe> {
		T create(String group, ResourceLocation block, ItemStack result, int minCount, int maxCount);
	}

	public static class Serializer<T extends PistonCrushingRecipe> implements RecipeSerializer<T> {

		private final PistonCrushingRecipe.Factory<T> factory;
		private final Codec<T> codec;

		public Serializer(PistonCrushingRecipe.Factory<T> factory) {
			this.factory = factory;
			this.codec = RecordCodecBuilder.create(
					instance -> instance.group(
									ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
									ResourceLocation.CODEC.fieldOf("block").forGetter(recipe -> recipe.block),
									ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
									Codec.INT.fieldOf("minCount").forGetter(recipe -> recipe.minCount),
									Codec.INT.fieldOf("maxCount").forGetter(recipe -> recipe.maxCount)
							)
							.apply(instance, factory::create)
			);
		}

		@Override
		public Codec<T> codec() {
			return codec;
		}

		@Override
		public T fromNetwork(FriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			ResourceLocation block = buffer.readResourceLocation();
			ItemStack result = buffer.readItem();
			int minCount = buffer.readInt();
			int maxCount = buffer.readInt();

			return factory.create(group, block, result, minCount, maxCount);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, PistonCrushingRecipe recipe) {
			buffer.writeUtf(recipe.group);
			buffer.writeResourceLocation(recipe.block);
			buffer.writeItem(recipe.result);
			buffer.writeInt(recipe.minCount);
			buffer.writeInt(recipe.maxCount);
		}
	}
}