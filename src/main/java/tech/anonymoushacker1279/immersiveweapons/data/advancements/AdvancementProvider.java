package tech.anonymoushacker1279.immersiveweapons.data.advancements;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class AdvancementProvider implements DataProvider {

	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	private final DataGenerator generator;
	private final List<Consumer<Consumer<Advancement>>> tabs;

	/**
	 * Constructor for AdvancementProvider.
	 *
	 * @param dataGenerator the <code>DataGenerator</code> instance
	 */
	public AdvancementProvider(DataGenerator dataGenerator, List<Consumer<Consumer<Advancement>>> advancements) {
		generator = dataGenerator;
		tabs = advancements;
	}

	/**
	 * Create model paths.
	 *
	 * @param path        the <code>Path</code> to save at
	 * @param advancement the <code>Advancement</code> to use
	 * @return Path
	 */
	private static Path createPath(Path path, Advancement advancement) {
		String namespace = advancement.getId().getNamespace();
		return path.resolve("data/" + namespace + "/advancements/" + advancement.getId().getPath() + ".json");
	}

	/**
	 * Run the model provider.
	 *
	 * @param cachedOutput the <code>CachedOutput</code> instance
	 */
	@Override
	public void run(@NotNull CachedOutput cachedOutput) {
		Path outputFolder = generator.getOutputFolder();
		Set<ResourceLocation> resourceLocations = Sets.newHashSet();
		Consumer<Advancement> advancementConsumer = (advancement) -> {
			if (!resourceLocations.add(advancement.getId())) {
				throw new IllegalStateException("Duplicate advancement " + advancement.getId());
			} else {
				Path path = createPath(outputFolder, advancement);

				try {
					DataProvider.saveStable(cachedOutput, advancement.deconstruct().serializeToJson(), path);
				} catch (IOException exception) {
					ImmersiveWeapons.LOGGER.error("Couldn't save advancement {}", path, exception);
				}

			}
		};

		for (Consumer<Consumer<Advancement>> tab : tabs) {
			tab.accept(advancementConsumer);
		}
	}

	/**
	 * Get the name of the provider.
	 *
	 * @return String
	 */
	@Override
	public @NotNull String getName() {
		return "Advancements";
	}
}