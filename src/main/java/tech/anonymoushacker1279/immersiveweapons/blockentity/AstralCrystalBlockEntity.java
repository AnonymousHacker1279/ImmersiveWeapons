package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AstralCrystalRecipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.input.AstralCrystalRecipeInput;

import java.util.Optional;

public class AstralCrystalBlockEntity extends AbstractInventoryBlockEntity {

	public AstralCrystalBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.ASTRAL_CRYSTAL_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new AstralCrystalBlockEntity(blockPos, blockState);
	}

	@Nullable
	public RecipeHolder<AstralCrystalRecipe> getRecipe(RecipeManager manager, ServerLevel serverLevel) {
		// Ensure all items stored are the same
		boolean f = getInventory().stream().allMatch(p -> p.getItem() == getInventory().getFirst().getItem());

		if (f) {
			Optional<RecipeHolder<AstralCrystalRecipe>> optional = manager.getRecipeFor(RecipeTypeRegistry.ASTRAL_CRYSTAL_RECIPE_TYPE.get(),
					new AstralCrystalRecipeInput(getInventory().getFirst(), ItemStack.EMPTY),
					serverLevel);

			if (optional.isPresent()) {
				return optional.get();
			}
		}

		return null;
	}
}