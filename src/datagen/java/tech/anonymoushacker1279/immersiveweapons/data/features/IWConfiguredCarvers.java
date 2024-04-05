package tech.anonymoushacker1279.immersiveweapons.data.features;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWConfiguredCarvers {

	public static final ResourceKey<ConfiguredWorldCarver<?>> TRENCH = createKey("trench");
	public static final ResourceKey<ConfiguredWorldCarver<?>> TILTROS_WASTES = createKey("tiltros_wastes");

	private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> context) {
		HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		context.register(TRENCH, new ConfiguredWorldCarver<>(WorldCarver.CANYON,
				new CanyonCarverConfiguration(
						0.5f,
						UniformHeight.of(VerticalAnchor.absolute(60),
								VerticalAnchor.absolute(128)),
						ConstantFloat.of(0.75f),
						VerticalAnchor.absolute(0),
						CarverDebugSettings.DEFAULT,
						blocks.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
						UniformFloat.of(-0.125f, 0.125f),
						new CanyonShapeConfiguration(
								UniformFloat.of(0.75f, 1.0f),
								TrapezoidFloat.of(0, 6, 2),
								3,
								UniformFloat.of(0.75f, 1.0f),
								1,
								0
						)
				)));

		context.register(TILTROS_WASTES, new ConfiguredWorldCarver<>(WorldCarver.CANYON,
				new CanyonCarverConfiguration(
						0.65f,
						TrapezoidHeight.of(VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(256),
								64),
						ConstantFloat.of(3.0f),
						VerticalAnchor.absolute(-48),
						CarverDebugSettings.DEFAULT,
						blocks.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
						UniformFloat.of(3.0f, 7.0f),
						new CanyonShapeConfiguration(
								ConstantFloat.of(15.0f),
								ConstantFloat.of(4.0f),
								3,
								ConstantFloat.of(3.0f),
								3,
								3
						)
				)));
	}
}