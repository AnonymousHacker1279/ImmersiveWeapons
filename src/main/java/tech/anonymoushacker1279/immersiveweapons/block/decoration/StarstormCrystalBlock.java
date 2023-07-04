package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.List;

public class StarstormCrystalBlock extends AmethystClusterBlock {

	private boolean brokenByPiston = false;

	public StarstormCrystalBlock(int size, int offset, Properties properties) {
		super(size, offset, properties);
	}

	public void handlePistonCrushing(Level level, BlockPos pos) {
		// If the block is being destroyed by a piston that is above it, drop a Starstorm Ingot
		ItemStack drop = new ItemStack(ItemRegistry.STARSTORM_SHARD.get());
		drop.setCount(level.getRandom().nextIntBetweenInclusive(2, 4));
		Block.popResource(level, pos, drop);

		// There is a 15% chance to spawn a Starmite
		if (level.getRandom().nextFloat() <= 0.15) {
			StarmiteEntity entity = EntityRegistry.STARMITE_ENTITY.get().create(level);
			if (entity != null) {
				entity.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
				level.addFreshEntity(entity);
			}
		}

		brokenByPiston = true;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		return brokenByPiston ? List.of() : super.getDrops(state, builder);
	}

	@Override
	public @Nullable PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}
}