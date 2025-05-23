package tech.anonymoushacker1279.immersiveweapons.data.dimensions;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.dimension.DimensionType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.OptionalLong;

public class DimensionTypeGenerator {

	public static final ResourceLocation TILTROS_LEVEL_ID = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "tiltros");
	public static final ResourceKey<DimensionType> TILTROS_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, TILTROS_LEVEL_ID);

	public static void bootstrap(BootstrapContext<DimensionType> context) {
		context.register(TILTROS_DIMENSION_TYPE, new DimensionType(
				OptionalLong.empty(),
				false,
				false,
				false,
				true,
				16.0D,
				true,
				false,
				-64,
				256,
				256,
				BlockTags.INFINIBURN_OVERWORLD,
				TILTROS_LEVEL_ID,
				0.0F,
				new DimensionType.MonsterSettings(false,
						false,
						BiasedToBottomInt.of(0, 7),
						0)));
	}
}