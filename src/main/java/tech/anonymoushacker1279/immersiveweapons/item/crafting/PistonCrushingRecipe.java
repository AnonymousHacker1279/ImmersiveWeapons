package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

public record PistonCrushingRecipe(String group, ResourceLocation block, ItemStack result, int minCount,
                                   int maxCount) implements Recipe<RecipeInput> {

	@Override
	public boolean matches(RecipeInput input, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(RecipeInput input, Provider registries) {
		return result;
	}

	public boolean matches(Block block) {
		return this.block.equals(BuiltInRegistries.BLOCK.getKey(block));
	}

	@Override
	public ItemStack getResultItem(Provider provider) {
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

	public int getMinCount() {
		return minCount;
	}

	public int getMaxCount() {
		return maxCount;
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

	public static class Serializer implements RecipeSerializer<PistonCrushingRecipe> {

		private static final MapCodec<PistonCrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								ResourceLocation.CODEC.fieldOf("block").forGetter(recipe -> recipe.block),
								ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
								Codec.INT.fieldOf("minCount").forGetter(recipe -> recipe.minCount),
								Codec.INT.fieldOf("maxCount").forGetter(recipe -> recipe.maxCount)
						)
						.apply(instance, PistonCrushingRecipe::new)
		);

		private static final StreamCodec<RegistryFriendlyByteBuf, PistonCrushingRecipe> STREAM_CODEC = StreamCodec.of(
				PistonCrushingRecipe.Serializer::toNetwork, PistonCrushingRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<PistonCrushingRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, PistonCrushingRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static PistonCrushingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			ResourceLocation block = buffer.readResourceLocation();
			ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
			int minCount = buffer.readInt();
			int maxCount = buffer.readInt();

			return new PistonCrushingRecipe(group, block, result, minCount, maxCount);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, PistonCrushingRecipe recipe) {
			buffer.writeUtf(recipe.group);
			buffer.writeResourceLocation(recipe.block);
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
			buffer.writeInt(recipe.minCount);
			buffer.writeInt(recipe.maxCount);
		}
	}
}