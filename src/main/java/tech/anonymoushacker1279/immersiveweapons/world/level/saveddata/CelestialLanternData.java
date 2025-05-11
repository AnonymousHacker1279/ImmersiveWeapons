package tech.anonymoushacker1279.immersiveweapons.world.level.saveddata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;

public class CelestialLanternData extends SavedData {

	public static final Codec<CelestialLanternData> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
					Codec.list(BlockPos.CODEC).fieldOf("celestial_lanterns").forGetter(CelestialLanternData::getAllLanterns)
			).apply(instance, CelestialLanternData::new)
	);

	public static final SavedDataType<CelestialLanternData> TYPE = new SavedDataType<>(
			"iw_celestial_lanterns",
			CelestialLanternData::new,
			CODEC
	);

	private final List<BlockPos> allLanterns = new ArrayList<>(30);

	public CelestialLanternData() {
	}

	public CelestialLanternData(List<BlockPos> allLanterns) {
		this.allLanterns.addAll(allLanterns);
	}

	public static CelestialLanternData getData(MinecraftServer server) {
		return server.overworld().getDataStorage().computeIfAbsent(TYPE);
	}

	public List<BlockPos> getAllLanterns() {
		return allLanterns;
	}

	public void addLantern(BlockPos pos) {
		allLanterns.add(pos);
		setDirty();
	}

	public void removeLantern(BlockPos pos) {
		allLanterns.remove(pos);
		setDirty();
	}
}