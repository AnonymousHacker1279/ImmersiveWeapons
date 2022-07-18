package tech.anonymoushacker1279.immersiveweapons.block.grower;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.data.modifiers.FeatureBiomeModifiers.PlacedFeatures;

public class StardustTreeGrower extends AbstractTreeGrower {

	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource randomSource, boolean hasFlowers) {
		return PlacedFeatures.STARDUST_TREE_HOLDER;
	}
}