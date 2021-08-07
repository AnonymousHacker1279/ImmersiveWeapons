package com.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators;

import com.anonymoushacker1279.immersiveweapons.block.BranchBlock;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BurnedBranchDecorator extends TreeDecorator {

	public static final Codec<BurnedBranchDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(BurnedBranchDecorator::new, (decorator) -> decorator.probability).codec();
	private final float probability;

	public BurnedBranchDecorator(float chance) {
		probability = chance;
	}

	@Override
	protected @NotNull TreeDecoratorType<?> type() {
		return TreeDecoratorType.COCOA;
	}

	@Override
	public void place(@NotNull LevelSimulatedReader simulatedReader, @NotNull BiConsumer<BlockPos, BlockState> biConsumer, Random random, @NotNull List<BlockPos> pos, @NotNull List<BlockPos> posList) {
		if (!(random.nextFloat() >= probability)) {
			int i = pos.get(0).getY();
			pos.stream().filter((blockPos) -> blockPos.getY() - i <= 8).forEach((blockPos) -> {
				for(Direction direction : Direction.Plane.HORIZONTAL) {
					if (random.nextFloat() <= 0.25F) {
						Direction direction1 = direction.getOpposite();
						BlockPos blockpos = blockPos.offset(direction1.getStepX(), 0, direction1.getStepZ());
						if (Feature.isAir(simulatedReader, blockpos)) {
							biConsumer.accept(blockpos, DeferredRegistryHandler.BURNED_OAK_BRANCH.get().defaultBlockState().setValue(BranchBlock.FACING, direction));
						}
					}
				}
			});
		}
	}
}