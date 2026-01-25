package tech.anonymoushacker1279.immersiveweapons.data.dimensions;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.world.NeoForgeEnvironmentAttributes;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class DimensionTypeGenerator {

	public static final Identifier TILTROS = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "tiltros");
	public static final ResourceKey<DimensionType> TILTROS_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, TILTROS);

	public static void bootstrap(BootstrapContext<DimensionType> context) {
		context.register(TILTROS_DIMENSION_TYPE, new DimensionType(
				true,
				false,
				false,
				16.0D,
				-64,
				256,
				256,
				BlockTags.INFINIBURN_OVERWORLD,
				0.05f,
				new DimensionType.MonsterSettings(BiasedToBottomInt.of(0, 7), 0),
				DimensionType.Skybox.OVERWORLD,
				DimensionType.CardinalLightType.DEFAULT,
				EnvironmentAttributeMap.builder()
						.set(NeoForgeEnvironmentAttributes.CUSTOM_SKYBOX, TILTROS)
						.build(),
				context.lookup(Registries.TIMELINE).getOrThrow(TimelineTags.UNIVERSAL)
		));
	}
}