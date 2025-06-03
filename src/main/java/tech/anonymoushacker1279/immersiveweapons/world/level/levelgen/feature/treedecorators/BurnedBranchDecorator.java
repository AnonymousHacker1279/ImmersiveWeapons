package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.block.BranchBlock;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.TreeDecoratorRegistry;

public class BurnedBranchDecorator extends TreeDecorator {

	public static final MapCodec<BurnedBranchDecorator> CODEC = Codec.floatRange(0.0F, 1.0F)
			.fieldOf("probability")
			.xmap(BurnedBranchDecorator::new, (decorator) -> decorator.probability);
	private final float probability;

	public BurnedBranchDecorator(float chance) {
		probability = chance;
	}

	@Override
	protected @NotNull TreeDecoratorType<?> type() {
		return TreeDecoratorRegistry.BURNED_BRANCH_DECORATOR.get();
	}

	@Override
	public void place(Context context) {
		if (context.random().nextFloat() <= probability) {
			context.logs().forEach((blockPos) -> {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					if (context.random().nextFloat() <= 0.25f) {
						Direction oppositeDirection = direction.getOpposite();
						BlockPos position = blockPos.offset(oppositeDirection.getStepX(), 0, oppositeDirection.getStepZ());

						if (context.isAir(position)) {
							context.setBlock(position, BlockRegistry.BURNED_OAK_BRANCH.get().defaultBlockState()
									.setValue(BranchBlock.FACING, direction));
						}
					}
				}
			});
		}
	}
}