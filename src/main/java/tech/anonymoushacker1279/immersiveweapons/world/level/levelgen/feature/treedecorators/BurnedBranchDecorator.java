package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.BranchBlock;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BurnedBranchDecorator extends TreeDecorator {

	public static final Codec<BurnedBranchDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability")
			.xmap(BurnedBranchDecorator::new, (decorator) -> decorator.probability).codec();
	private final float probability;

	public BurnedBranchDecorator(float chance) {
		probability = chance;
	}

	@Override
	protected @NotNull TreeDecoratorType<?> type() {
		return TreeDecoratorType.COCOA;
	}

	@Override
	public void place(@NotNull LevelSimulatedReader simulatedReader, @NotNull BiConsumer<BlockPos, BlockState> biConsumer,
	                  Random random, @NotNull List<BlockPos> pos, @NotNull List<BlockPos> posList) {

		if (random.nextFloat() <= probability) {
			pos.forEach((blockPos) -> {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					if (random.nextFloat() <= 0.25f) {
						Direction oppositeDirection = direction.getOpposite();
						BlockPos position = blockPos.offset(oppositeDirection.getStepX(), 0, oppositeDirection.getStepZ());

						if (Feature.isAir(simulatedReader, position)) {
							biConsumer.accept(position, DeferredRegistryHandler.BURNED_OAK_BRANCH.get().defaultBlockState()
									.setValue(BranchBlock.FACING, direction));
						}
					}
				}
			});
		}
	}
}