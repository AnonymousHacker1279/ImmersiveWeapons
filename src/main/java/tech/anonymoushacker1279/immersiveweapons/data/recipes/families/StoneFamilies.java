package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.Collection;
import java.util.function.Supplier;

public record StoneFamilies(Supplier<? extends Block> stone,
                            Supplier<? extends Block> bricks,
                            Supplier<? extends Block> slab,
                            Supplier<? extends Block> stairs,
                            Supplier<? extends Block> pillar) {

	public static final StoneFamilies CLOUD_MARBLE = new StoneFamilies(
			DeferredRegistryHandler.CLOUD_MARBLE,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICKS,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS,
			DeferredRegistryHandler.CLOUD_MARBLE_PILLAR
	);

	public static final Collection<StoneFamilies> FAMILIES = ImmutableList.of(CLOUD_MARBLE);
}