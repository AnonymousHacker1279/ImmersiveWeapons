package tech.anonymoushacker1279.immersiveweapons.world.level.saveddata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.ArrayList;
import java.util.List;

public class CelestialLanternData extends SavedData {

	public static final Codec<CelestialLanternData> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
					Codec.list(BlockPos.CODEC).fieldOf("celestial_lanterns").forGetter(CelestialLanternData::getAllLanterns)
			).apply(instance, CelestialLanternData::new)
	);

	public static final SavedDataType<CelestialLanternData> TYPE = new SavedDataType<>(
			Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "celestial_lanterns"),
			CelestialLanternData::new,
			CODEC
	);

	private final List<BlockPos> allLanterns = new ArrayList<>(30);

	public CelestialLanternData() {
	}

	public CelestialLanternData(List<BlockPos> allLanterns) {
		// Older saves may contain duplicate entries
		for (BlockPos pos : allLanterns) {
			if (!this.allLanterns.contains(pos)) {
				this.allLanterns.add(pos);
			}
		}
	}

	public static CelestialLanternData getData(MinecraftServer server) {
		return server.overworld().getDataStorage().computeIfAbsent(TYPE);
	}

	public List<BlockPos> getAllLanterns() {
		return allLanterns;
	}

	/// Register a lantern. This is called every time a lantern block entity loads, so it must be idempotent.
	public void addLantern(BlockPos pos) {
		if (!allLanterns.contains(pos)) {
			allLanterns.add(pos);
			setDirty();
		}
	}

	public void removeLantern(BlockPos pos) {
		if (allLanterns.remove(pos)) {
			setDirty();
		}
	}
}