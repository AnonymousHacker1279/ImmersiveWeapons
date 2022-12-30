package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

// TODO: Purge class and move key to a dimension generator class
public class Dimensions {

	public static final ResourceKey<Level> TILTROS = ResourceKey.create(Registries.DIMENSION,
			new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros"));

}