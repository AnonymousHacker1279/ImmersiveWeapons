package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

public record PistonCrushingRecipe(ResourceLocation blockLocation,
                                   ItemStack result,
                                   int minCount,
                                   int maxCount) implements Recipe<Container> {

	/**
	 * Used to check if a recipe matches current crafting inventory.
	 * Since this is never used in an inventory, it always returns false.
	 *
	 * @param container the <code>Container</code> instance
	 * @param level     the current <code>Level</code>
	 * @return boolean
	 */
	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	public boolean matches(Block block) {
		return this.blockLocation.equals(ForgeRegistries.BLOCKS.getKey(block));
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return result;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return result.copy();
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height.
	 *
	 * @param width  the width of the grid
	 * @param height the height of the grid
	 * @return boolean
	 */
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	/**
	 * Get the recipe's blockLocation.
	 *
	 * @return Block
	 */
	@Nullable
	public Block getBlock() {
		return ForgeRegistries.BLOCKS.getValue(blockLocation);
	}

	/**
	 * Get a random drop amount based on the min and max count (inclusive).
	 */
	public int getRandomDropAmount() {
		return minCount + (int) (Math.random() * ((maxCount - minCount) + 1));
	}

	/**
	 * Get the toast symbol.
	 *
	 * @return ItemStack
	 */
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(Blocks.PISTON);
	}

	/**
	 * Get the recipe serializer.
	 *
	 * @return RecipeSerializer
	 */
	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializerRegistry.PISTON_CRUSHING_RECIPE_SERIALIZER.get();
	}

	/**
	 * Get the recipe type.
	 *
	 * @return RecipeType
	 */
	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.PISTON_CRUSHING_RECIPE_TYPE.get();
	}

	public static class Serializer implements RecipeSerializer<PistonCrushingRecipe> {

		private static final Codec<PistonCrushingRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ResourceLocation.CODEC.fieldOf("blockLocation").forGetter(recipe -> recipe.blockLocation),
								CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
								Codec.INT.fieldOf("minCount").forGetter(recipe -> recipe.minCount),
								Codec.INT.fieldOf("maxCount").forGetter(recipe -> recipe.maxCount)
						)
						.apply(instance, PistonCrushingRecipe::new)
		);

		@Override
		public Codec<PistonCrushingRecipe> codec() {
			return CODEC;
		}

		@Override
		public PistonCrushingRecipe fromNetwork(FriendlyByteBuf buffer) {
			ResourceLocation block = buffer.readResourceLocation();
			ItemStack result = buffer.readItem();
			int minCount = buffer.readInt();
			int maxCount = buffer.readInt();

			return new PistonCrushingRecipe(block, result, minCount, maxCount);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, PistonCrushingRecipe recipe) {
			buffer.writeResourceLocation(recipe.blockLocation);
			buffer.writeItem(recipe.result);
			buffer.writeInt(recipe.minCount);
			buffer.writeInt(recipe.maxCount);
		}
	}
}