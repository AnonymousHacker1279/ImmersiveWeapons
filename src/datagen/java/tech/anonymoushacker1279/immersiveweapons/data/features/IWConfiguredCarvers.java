package tech.anonymoushacker1279.immersiveweapons.data.features;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver.Shape;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWConfiguredCarvers {

	public static final ResourceKey<WorldCarver> TRENCH = createKey("trench");
	public static final ResourceKey<WorldCarver> TILTROS_WASTES = createKey("tiltros_wastes");

	private static ResourceKey<WorldCarver> createKey(String name) {
		return ResourceKey.create(Registries.CARVER, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstrapContext<WorldCarver> context) {
		context.register(TRENCH, new CanyonWorldCarver(
				0.5f,
				UniformHeight.of(VerticalAnchor.absolute(60),
						VerticalAnchor.absolute(128)),
				UniformFloat.of(-0.125f, 0.125f),
				new Shape(
						UniformFloat.of(0.75f, 1.0f),
						TrapezoidFloat.of(0, 6, 2),
						3,
						UniformFloat.of(0.75f, 1.0f),
						1,
						0,
						ConstantFloat.of(0.75f)
				)));

		context.register(TILTROS_WASTES, new CanyonWorldCarver(
				0.65f,
				TrapezoidHeight.of(VerticalAnchor.absolute(0),
						VerticalAnchor.absolute(256),
						64),
				UniformFloat.of(3.0f, 7.0f),
				new Shape(
						ConstantFloat.of(15.0f),
						ConstantFloat.of(4.0f),
						3,
						ConstantFloat.of(3.0f),
						3,
						3,
						ConstantFloat.of(3.0f)
				)));
	}
}