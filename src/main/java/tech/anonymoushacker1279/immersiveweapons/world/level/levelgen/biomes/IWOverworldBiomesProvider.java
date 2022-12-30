package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWBiomes;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class IWOverworldBiomesProvider extends Region {

	public IWOverworldBiomesProvider(ResourceLocation name, RegionType type, int weight) {
		super(name, type, weight);
	}

	@Override
	public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {

		addModifiedVanillaOverworldBiomes(mapper, modifier -> modifier.replaceBiome(Biomes.PLAINS, IWBiomes.BATTLEFIELD));
	}
}