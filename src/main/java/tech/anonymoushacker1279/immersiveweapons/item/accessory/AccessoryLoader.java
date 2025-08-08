package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncAccessoryDataPayload;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AccessoryLoader extends SimpleJsonResourceReloadListener<Accessory> {

	public static final HashMap<Item, Accessory> ACCESSORIES = new HashMap<>(50);
	public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "accessories");

	public AccessoryLoader() {
		super(Accessory.CODEC, FileToIdConverter.json("accessories"));
	}

	@Override
	protected void apply(Map<ResourceLocation, Accessory> map, ResourceManager resourceManager, ProfilerFiller profiler) {
		ACCESSORIES.clear();

		map.forEach((id, element) -> {
			try {
				ACCESSORIES.put(element.item().value(), element);
			} catch (Exception e) {
				throw new IllegalStateException("Failed to load accessory data from " + id, e);
			}
		});

		// Sync accessories to clients
		if (ServerLifecycleHooks.getCurrentServer() != null) {
			final SyncAccessoryDataPayload payload = new SyncAccessoryDataPayload(new HashSet<>(ACCESSORIES.values()));
			PacketDistributor.sendToAllPlayers(payload);
		}
	}
}