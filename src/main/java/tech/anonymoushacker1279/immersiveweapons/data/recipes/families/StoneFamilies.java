package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

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
			BlockRegistry.CLOUD_MARBLE,
			BlockRegistry.CLOUD_MARBLE_BRICKS, true,
			BlockRegistry.CLOUD_MARBLE_BRICK_SLAB,
			BlockRegistry.CLOUD_MARBLE_BRICK_STAIRS,
			BlockRegistry.CLOUD_MARBLE_BRICK_WALL,
			BlockRegistry.CLOUD_MARBLE_PILLAR,
			null, null, null, null, null, null
	);

	public static final StoneFamilies BLOOD_SANDSTONE = new StoneFamilies(
			BlockRegistry.BLOOD_SAND,
			BlockRegistry.BLOOD_SANDSTONE, false,
			BlockRegistry.BLOOD_SANDSTONE_SLAB,
			BlockRegistry.BLOOD_SANDSTONE_STAIRS,
			BlockRegistry.BLOOD_SANDSTONE_WALL,
			null,
			BlockRegistry.CHISELED_BLOOD_SANDSTONE,
			BlockRegistry.CUT_BLOOD_SANDSTONE,
			BlockRegistry.CUT_BLOOD_SANDSTONE_SLAB,
			BlockRegistry.SMOOTH_BLOOD_SANDSTONE,
			BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB,
			BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS
	);

	public static final Collection<StoneFamilies> FAMILIES = ImmutableList.of(CLOUD_MARBLE, BLOOD_SANDSTONE);
}