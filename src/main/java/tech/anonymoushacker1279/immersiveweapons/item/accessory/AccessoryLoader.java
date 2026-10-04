package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncAccessoryDataPayload;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AccessoryLoader extends SimpleJsonResourceReloadListener<Accessory> {

	/// The loaded accessories. This is an immutable snapshot that is replaced as a whole on reload or sync, so it is safe
	/// to read from any thread. Do not mutate it; use [#setAccessories(Collection)] instead.
	public static volatile Map<Item, Accessory> ACCESSORIES = Map.of();
	public static final Identifier ID = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "accessories");

	/// Replace the loaded accessories, e.g. with the set synced from the server.
	///
	/// @param accessories the accessories to use
	public static void setAccessories(Collection<Accessory> accessories) {
		Map<Item, Accessory> loaded = new HashMap<>(accessories.size());
		accessories.forEach(accessory -> loaded.put(accessory.item().value(), accessory));
		ACCESSORIES = Map.copyOf(loaded);
	}

	public AccessoryLoader() {
		super(Accessory.CODEC, FileToIdConverter.json("accessories"));
	}

	@Override
	protected void apply(Map<Identifier, Accessory> map, ResourceManager resourceManager, ProfilerFiller profiler) {
		Map<Item, Accessory> loaded = new HashMap<>(map.size());

		map.forEach((id, element) -> {
			try {
				loaded.put(element.item().value(), element);
			} catch (Exception e) {
				throw new IllegalStateException("Failed to load accessory data from " + id, e);
			}
		});

		ACCESSORIES = Map.copyOf(loaded);

		// Sync accessories to clients
		if (ServerLifecycleHooks.getCurrentServer() != null) {
			final SyncAccessoryDataPayload payload = new SyncAccessoryDataPayload(new HashSet<>(ACCESSORIES.values()));
			PacketDistributor.sendToAllPlayers(payload);
		}
	}
}