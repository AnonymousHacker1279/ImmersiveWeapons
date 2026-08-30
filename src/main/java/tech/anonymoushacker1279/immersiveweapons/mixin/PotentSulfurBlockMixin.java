package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.PotentSulfurBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PotentSulfurState;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

@Mixin(PotentSulfurBlock.class)
public class PotentSulfurBlockMixin<T extends BlockEntity> {

	@ModifyReturnValue(method = "getTicker", at = @At("RETURN"))
	private @Nullable BlockEntityTicker<T> getTicker(@Nullable BlockEntityTicker<T> original,
	                                                 @Local(name = "blockState", argsOnly = true) BlockState blockState,
	                                                 @Local(name = "client") boolean client) {

		PotentSulfurState sulfurState = blockState.getValue(PotentSulfurBlock.STATE);
		if (original != null && !client && (sulfurState == PotentSulfurState.ERUPTING || sulfurState == PotentSulfurState.CONTINUOUS)) {
			return original.andThen((level, pos, _, _) -> {
				if (level.getRandom().nextFloat() > 0.25f) return;

				int waterBlocks = 0;
				BlockPos waterLevelPos = pos.above();
				while (level.getFluidState(waterLevelPos.above(waterBlocks)).getType() == Fluids.WATER && waterBlocks < 5) {
					waterBlocks++;
				}

				int radius = 3;
				float depositChance = (sulfurState == PotentSulfurState.ERUPTING
						? 0.01f + ((waterBlocks - 1) * 0.01f)
						: 0.0001f + ((waterBlocks - 1) * 0.0001f));

				for (int x = -radius; x < radius; x++) {
					for (int y = -radius; y < radius + waterBlocks; y++) {
						for (int z = -radius; z < radius; z++) {
							BlockPos checkPos = pos.offset(x, y, z);

							if (x == 0 || y == 1 || z == 0) continue;    // ignore the block above the potent sulfur

							BlockState aboveState = level.getBlockState(checkPos.above());
							if ((aboveState.isAir() || aboveState.getFluidState().getType() == Fluids.WATER)
									&& level.getBlockState(checkPos).isFaceSturdy(level, pos, Direction.UP)) {

								if (level.getRandom().nextFloat() < depositChance) {
									level.setBlockAndUpdate(checkPos.above(), BlockRegistry.MINERAL_DEPOSIT.get().defaultBlockState());
								}
							}
						}
					}
				}
			});
		}

		return original;
	}
}