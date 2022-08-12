package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.Collection;
import java.util.function.Supplier;

public record StoneFamilies(Supplier<? extends Block> stone,
                            Supplier<? extends Block> bricks,
                            boolean bricksHaveStonecutterRecipe,
                            Supplier<? extends Block> slab,
                            Supplier<? extends Block> stairs,
                            Supplier<? extends Block> pillar,
                            Supplier<? extends Block> wall,
                            Supplier<? extends Block> chiseled,
                            Supplier<? extends Block> cut,
                            Supplier<? extends Block> cutSlab,
                            Supplier<? extends Block> smooth,
                            Supplier<? extends Block> smoothSlab,
                            Supplier<? extends Block> smoothStairs) {

	public static final StoneFamilies CLOUD_MARBLE = new StoneFamilies(
			DeferredRegistryHandler.CLOUD_MARBLE,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICKS, true,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS,
			DeferredRegistryHandler.CLOUD_MARBLE_PILLAR,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICK_WALL,
			null, null, null, null, null, null
	);

	public static final StoneFamilies BLOOD_SANDSTONE = new StoneFamilies(
			DeferredRegistryHandler.BLOOD_SAND,
			DeferredRegistryHandler.BLOOD_SANDSTONE, false,
			DeferredRegistryHandler.BLOOD_SANDSTONE_SLAB,
			DeferredRegistryHandler.BLOOD_SANDSTONE_STAIRS,
			null,
			DeferredRegistryHandler.BLOOD_SANDSTONE_WALL,
			DeferredRegistryHandler.CHISELED_BLOOD_SANDSTONE,
			DeferredRegistryHandler.CUT_BLOOD_SANDSTONE,
			DeferredRegistryHandler.CUT_BLOOD_SANDSTONE_SLAB,
			DeferredRegistryHandler.SMOOTH_BLOOD_SANDSTONE,
			DeferredRegistryHandler.SMOOTH_BLOOD_SANDSTONE_SLAB,
			DeferredRegistryHandler.SMOOTH_BLOOD_SANDSTONE_STAIRS
	);

	public static final Collection<StoneFamilies> FAMILIES = ImmutableList.of(CLOUD_MARBLE, BLOOD_SANDSTONE);
}