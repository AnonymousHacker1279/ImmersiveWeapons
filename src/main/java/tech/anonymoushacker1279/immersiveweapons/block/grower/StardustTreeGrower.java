package tech.anonymoushacker1279.immersiveweapons.block.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWConfiguredFeatures;

public class StardustTreeGrower extends AbstractTreeGrower {

	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean hasFlowers) {
		return IWConfiguredFeatures.STARDUST_TREE_CONFIGURATION;
	}
}