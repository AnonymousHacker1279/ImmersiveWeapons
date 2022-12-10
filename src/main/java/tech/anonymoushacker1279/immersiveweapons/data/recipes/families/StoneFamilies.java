package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.Collection;
import java.util.function.Supplier;

public record StoneFamilies(Supplier<? extends Block> stone,
                            Supplier<? extends Block> bricks,
                            boolean bricksHaveStonecutterRecipe,
                            Supplier<? extends Block> slab,
                            Supplier<? extends Block> stairs,
                            Supplier<? extends Block> wall,
                            @Nullable Supplier<? extends Block> pillar,
                            @Nullable Supplier<? extends Block> chiseled,
                            @Nullable Supplier<? extends Block> cut,
                            @Nullable Supplier<? extends Block> cutSlab,
                            @Nullable Supplier<? extends Block> smooth,
                            @Nullable Supplier<? extends Block> smoothSlab,
                            @Nullable Supplier<? extends Block> smoothStairs) {

	public static final StoneFamilies CLOUD_MARBLE = new StoneFamilies(
			DeferredRegistryHandler.CLOUD_MARBLE,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICKS, true,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS,
			DeferredRegistryHandler.CLOUD_MARBLE_BRICK_WALL,
			DeferredRegistryHandler.CLOUD_MARBLE_PILLAR,
			null, null, null, null, null, null
	);

	public static final StoneFamilies BLOOD_SANDSTONE = new StoneFamilies(
			DeferredRegistryHandler.BLOOD_SAND,
			DeferredRegistryHandler.BLOOD_SANDSTONE, false,
			DeferredRegistryHandler.BLOOD_SANDSTONE_SLAB,
			DeferredRegistryHandler.BLOOD_SANDSTONE_STAIRS,
			DeferredRegistryHandler.BLOOD_SANDSTONE_WALL,
			null,
			DeferredRegistryHandler.CHISELED_BLOOD_SANDSTONE,
			DeferredRegistryHandler.CUT_BLOOD_SANDSTONE,
			DeferredRegistryHandler.CUT_BLOOD_SANDSTONE_SLAB,
			DeferredRegistryHandler.SMOOTH_BLOOD_SANDSTONE,
			DeferredRegistryHandler.SMOOTH_BLOOD_SANDSTONE_SLAB,
			DeferredRegistryHandler.SMOOTH_BLOOD_SANDSTONE_STAIRS
	);

	public static final Collection<StoneFamilies> FAMILIES = ImmutableList.of(CLOUD_MARBLE, BLOOD_SANDSTONE);
}