package tech.anonymoushacker1279.immersiveweapons.block.grower;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class StardustTreeGrower extends AbstractTreeGrower {

	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean hasFlowers) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_tree"));
	}
}