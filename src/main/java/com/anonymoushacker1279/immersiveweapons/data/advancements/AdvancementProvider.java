package com.anonymoushacker1279.immersiveweapons.data.advancements;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class AdvancementProvider implements DataProvider {

	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	private final DataGenerator generator;
	private final List<Consumer<Consumer<Advancement>>> tabs = ImmutableList.of(new ImmersiveWeaponsAdvancements());

	public AdvancementProvider(DataGenerator dataGenerator) {
		generator = dataGenerator;
	}

	@Override
	public void run(HashCache cache) {
		Path outputFolder = generator.getOutputFolder();
		Set<ResourceLocation> resourceLocations = Sets.newHashSet();
		Consumer<Advancement> advancementConsumer = (advancement) -> {
			if (!resourceLocations.add(advancement.getId())) {
				throw new IllegalStateException("Duplicate advancement " + advancement.getId());
			} else {
				Path path = createPath(outputFolder, advancement);

				try {
					DataProvider.save(GSON, cache, advancement.deconstruct().serializeToJson(), path);
				} catch (IOException exception) {
					ImmersiveWeapons.LOGGER.error("Couldn't save advancement {}", path, exception);
				}

			}
		};

		for (Consumer<Consumer<Advancement>> tab : tabs) {
			tab.accept(advancementConsumer);
		}
	}

	private static Path createPath(Path path, Advancement advancement) {
		String namespace = advancement.getId().getNamespace();
		return path.resolve("data/" + namespace + "/advancements/" + advancement.getId().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Advancements";
	}
}