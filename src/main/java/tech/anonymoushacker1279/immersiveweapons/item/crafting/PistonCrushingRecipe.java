package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

public record PistonCrushingRecipe(ResourceLocation recipeId,
                                   Block block,
                                   ItemStack result,
                                   int minCount,
                                   int maxCount) implements Recipe<Container> {

	/**
	 * Constructor for PistonCrushingRecipe. The values for minCount and maxCount are
	 * inclusive.
	 *
	 * @param recipeId the <code>ResourceLocation</code> for the recipe
	 * @param block    the block to be crushed
	 * @param result   an <code>ItemStack</code> of the result
	 * @param minCount the minimum number of items dropped
	 * @param maxCount the maximum number of items dropped
	 */
	public PistonCrushingRecipe {
	}

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
		return this.block == block;
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
	 * Get the recipe's block.
	 *
	 * @return Block
	 */
	public Block getBlock() {
		return block;
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
	 * Get the recipe ID.
	 *
	 * @return ResourceLocation
	 */
	@Override
	public ResourceLocation getId() {
		return recipeId;
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

	@Override
	public boolean isSpecial() {
		return true;
	}

	public static class Serializer implements RecipeSerializer<PistonCrushingRecipe> {
		/**
		 * Serialize from JSON.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param json     the <code>JsonObject</code> instance
		 * @return AstralCrystalRecipe
		 */
		@Override
		public PistonCrushingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(GsonHelper.getAsString(json, "block")));
			ItemStack result = new ItemStack(ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result")));
			int minCount = GsonHelper.getAsInt(json, "minCount");
			int maxCount = GsonHelper.getAsInt(json, "maxCount");

			if (block == null) {
				throw new IllegalStateException("Block " + GsonHelper.getAsString(json, "block") + " not found in the registry");
			}

			return new PistonCrushingRecipe(recipeId, block, result, minCount, maxCount);
		}

		/**
		 * Serialize from JSON on the network.
		 *
		 * @param recipeId the <code>ResourceLocation</code> for the recipe
		 * @param buffer   the <code>FriendlyByteBuf</code> instance
		 * @return AstralCrystalRecipe
		 */
		@Override
		public PistonCrushingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			Block block = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
			ItemStack result = buffer.readItem();
			int minCount = buffer.readInt();
			int maxCount = buffer.readInt();

			if (block == null) {
				throw new IllegalStateException("Block " + buffer.readResourceLocation() + " not found in the registry");
			}

			return new PistonCrushingRecipe(recipeId, block, result, minCount, maxCount);
		}

		/**
		 * Serialize to JSON on the network.
		 *
		 * @param buffer the <code>FriendlyByteBuf</code> instance
		 * @param recipe the <code>AstralCrystalRecipe</code> instance
		 */
		@Override
		public void toNetwork(FriendlyByteBuf buffer, PistonCrushingRecipe recipe) {
			ResourceLocation block = ForgeRegistries.BLOCKS.getKey(recipe.block);

			if (block == null) {
				throw new IllegalStateException("Block " + recipe.block + " not found in the registry");
			}

			buffer.writeResourceLocation(block);
			buffer.writeItem(recipe.result);
			buffer.writeInt(recipe.minCount);
			buffer.writeInt(recipe.maxCount);
		}
	}
}