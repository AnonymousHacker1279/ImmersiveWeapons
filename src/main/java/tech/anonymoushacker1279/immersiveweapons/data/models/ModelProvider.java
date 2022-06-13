package tech.anonymoushacker1279.immersiveweapons.data.models;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import net.minecraft.data.*;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.nio.file.Path;
import java.util.*;
import java.util.function.*;

public class ModelProvider implements DataProvider {

	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	private final DataGenerator generator;

	/**
	 * Constructor for ModelProvider.
	 *
	 * @param dataGenerator the <code>DataGenerator</code> instance
	 */
	public ModelProvider(DataGenerator dataGenerator) {
		generator = dataGenerator;
	}

	/**
	 * Create model paths.
	 *
	 * @param path             the <code>Path</code> to save at
	 * @param resourceLocation the <code>ResourceLocation</code> to use
	 * @return Path
	 */
	private static Path createModelPath(Path path, ResourceLocation resourceLocation) {
		String namespace = resourceLocation.getNamespace();
		return path.resolve("assets/" + namespace + "/models/" + resourceLocation.getPath() + ".json");
	}

	/**
	 * Run the model provider.
	 *
	 * @param hashCache the <code>HashCache</code> instance
	 */
	@Override
	public void run(@NotNull HashCache hashCache) {
		Path getOutputFolder = generator.getOutputFolder();
		Map<ResourceLocation, Supplier<JsonElement>> locationSupplierHashMap = Maps.newHashMap();
		Set<Item> itemHashSet = Sets.newHashSet();
		BiConsumer<ResourceLocation, Supplier<JsonElement>> locationSupplierBiConsumer = (location, elementSupplier) -> {
			Supplier<JsonElement> jsonElementSupplier = locationSupplierHashMap.put(location, elementSupplier);
			if (jsonElementSupplier != null) {
				throw new IllegalStateException("Duplicate model definition for " + location);
			}
		};
		Map<Block, BlockStateGenerator> blockStateGeneratorHashMap = Maps.newHashMap();
		Consumer<BlockStateGenerator> blockStateGeneratorConsumer = (blockStateGenerator) -> {
			Block block = blockStateGenerator.getBlock();
			BlockStateGenerator blockstategenerator = blockStateGeneratorHashMap.put(block, blockStateGenerator);
			if (blockstategenerator != null) {
				throw new IllegalStateException("Duplicate blockstate definition for " + block);
			}
		};
		Objects.requireNonNull(itemHashSet);
		Consumer<Item> modelGenerator = itemHashSet::add;
		(new BlockModelGenerator(blockStateGeneratorConsumer, locationSupplierBiConsumer, modelGenerator)).run();
		(new ItemModelGenerator(locationSupplierBiConsumer)).run();
		saveCollection(hashCache, getOutputFolder, locationSupplierHashMap, ModelProvider::createModelPath);
		saveCollection(hashCache, getOutputFolder, blockStateGeneratorHashMap, ModelProvider::createBlockStatePath);
	}

	/**
	 * Save collections.
	 *
	 * @param hashCache  the <code>HashCache</code> instance
	 * @param path       the <code>Path</code> to save at
	 * @param map        the <code>Map</code> extending Supplier, extending JsonElement
	 * @param biFunction the <code>BiFunction</code> path
	 * @param <T>        the provider
	 */
	private <T> void saveCollection(HashCache hashCache, Path path, Map<T, ? extends Supplier<JsonElement>> map,
	                                BiFunction<Path, T, Path> biFunction) {

		map.forEach((t, supplier) -> {
			Path save = biFunction.apply(path, t);

			try {
				DataProvider.save(GSON, hashCache, supplier.get(), save);
			} catch (Exception e) {
				ImmersiveWeapons.LOGGER.error("Couldn't save {}", save, e);
			}

		});
	}

	private static Path createBlockStatePath(Path path, Block block) {
		ResourceLocation namespace = ForgeRegistries.BLOCKS.getKey(block);
		return path.resolve("assets/" + Objects.requireNonNull(namespace).getNamespace() + "/blockstates/"
				+ namespace.getPath() + ".json");
	}

	/**
	 * Get the name of the provider.
	 *
	 * @return String
	 */
	@Override
	public @NotNull String getName() {
		return "Model Provider";
	}
}