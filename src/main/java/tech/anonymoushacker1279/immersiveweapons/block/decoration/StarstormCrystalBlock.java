package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public class StarstormCrystalBlock extends AmethystClusterBlock {

	private static boolean brokenByPiston = false;

	public StarstormCrystalBlock(int size, int offset, Properties properties) {
		super(size, offset, properties);
	}

	public static void handlePistonCrushing(Level level, BlockPos pos) {
		// If the block is being destroyed by a piston that is above it, drop a Starstorm Ingot
		ItemStack drop = new ItemStack(ItemRegistry.STARSTORM_SHARD.get());
		drop.setCount(GeneralUtilities.getRandomNumber(2, 5));
		Block.popResource(level, pos, drop);

		// There is a 15% chance to spawn a Starmite
		if (GeneralUtilities.getRandomNumber(0.0f, 1.0f) <= 0.15) {
			StarmiteEntity entity = EntityRegistry.STARMITE_ENTITY.get().create(level);
			if (entity != null) {
				entity.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
				level.addFreshEntity(entity);
			}
		}

		brokenByPiston = true;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return brokenByPiston ? List.of() : super.getDrops(state, builder);
	}
}