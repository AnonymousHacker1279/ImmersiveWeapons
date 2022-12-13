package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature;

import com.google.gson.JsonElement;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;

public class WorldCarvers {

	public static CanyonCarverConfiguration TRENCH_CARVER_CONFIGURATION;
	public static CanyonCarverConfiguration TILTROS_WASTES_CARVER_CONFIGURATION;

	public static void init(RegistryOps<JsonElement> registryOps) {
		TRENCH_CARVER_CONFIGURATION = new CanyonCarverConfiguration(
				0.35f,
				BiasedToBottomHeight.of(VerticalAnchor.absolute(0),
						VerticalAnchor.absolute(172),
						68),
				ConstantFloat.of(0.75f),
				VerticalAnchor.absolute(0),
				CarverDebugSettings.DEFAULT,
				registryOps.getter(Registries.BLOCK).get().getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
				UniformFloat.of(0.0f, 16f),
				new CanyonShapeConfiguration(
						ConstantFloat.of(15.0f),
						ConstantFloat.of(1.0f),
						2,
						ConstantFloat.of(2.0f),
						1,
						1
				)
		);

		TILTROS_WASTES_CARVER_CONFIGURATION = new CanyonCarverConfiguration(
				0.65f,
				TrapezoidHeight.of(VerticalAnchor.absolute(0),
						VerticalAnchor.absolute(256),
						64),
				ConstantFloat.of(3.0f),
				VerticalAnchor.absolute(-48),
				CarverDebugSettings.DEFAULT,
				registryOps.getter(Registries.BLOCK).get().getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
				UniformFloat.of(3.0f, 7.0f),
				new CanyonShapeConfiguration(
						ConstantFloat.of(15.0f),
						ConstantFloat.of(4.0f),
						3,
						ConstantFloat.of(3.0f),
						3,
						3
				)
		);
	}
}