package tech.anonymoushacker1279.immersiveweapons.data.accessories;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class AccessoryDataProvider implements DataProvider {

	private final PackOutput packOutput;

	public AccessoryDataProvider(PackOutput output) {
		this.packOutput = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		Path path = this.packOutput.getOutputFolder();
		Set<Accessory> accessories = getAccessories();
		List<CompletableFuture<?>> futures = new ArrayList<>(5);

		accessories.forEach((type) -> {
			Identifier id = Objects.requireNonNull(type.item().getKey()).identifier();
			Path filePath = path.resolve("data/" + id.getNamespace() + "/accessories/" + id.getPath() + ".json");

			DataResult<JsonElement> accessoryResult = Accessory.CODEC.encodeStart(JsonOps.INSTANCE, type);
			JsonElement accessoryElement = accessoryResult.result().orElseThrow(() -> new IllegalStateException("Failed to encode accessory data for " + id));
			futures.add(DataProvider.saveStable(cache, accessoryElement, filePath));
		});

		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}

	protected abstract Set<Accessory> getAccessories();

	@Override
	public String getName() {
		return "Accessory Data";
	}
}