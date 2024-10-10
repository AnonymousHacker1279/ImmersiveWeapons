package tech.anonymoushacker1279.immersiveweapons.world.level.saveddata;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;

public class IWSavedData extends SavedData {

	private static final Factory<IWSavedData> FACTORY = new Factory<>(IWSavedData::new, IWSavedData::load);

	private final Set<BlockPos> allLanterns = new HashSet<>(30);

	@Override
	public CompoundTag save(CompoundTag tag, Provider registries) {
		// Handle Celestial Lantern block locations
		ListTag lanterns = new ListTag();
		allLanterns.forEach(pos -> lanterns.add(NbtUtils.writeBlockPos(pos)));
		tag.put("celestial_lanterns", lanterns);

		return tag;
	}

	private static IWSavedData load(CompoundTag tag, Provider registries) {
		IWSavedData data = new IWSavedData();
		Tag t = tag.get("celestial_lanterns");
		if (t instanceof ListTag lanterns) {
			for (int i = 0; i < lanterns.size(); i++) {
				int[] pos = lanterns.getIntArray(i);
				data.allLanterns.add(new BlockPos(pos[0], pos[1], pos[2]));
			}
		}

		return data;
	}

	public static IWSavedData getData(MinecraftServer server) {
		return server.overworld().getDataStorage().computeIfAbsent(FACTORY, "iw_data");
	}

	public Set<BlockPos> getAllLanterns() {
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