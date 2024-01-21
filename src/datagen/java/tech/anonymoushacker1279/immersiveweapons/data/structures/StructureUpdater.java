package tech.anonymoushacker1279.immersiveweapons.data.structures;

import com.google.common.hash.Hashing;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

public class StructureUpdater implements DataProvider {

	private final PackOutput output;
	private final MultiPackResourceManager resources;

	/**
	 * This data provider is used to update old NBT structures to the latest version. Any updated structure files will
	 * need to be copied out of the generated data folder and into the appropriate resource pack folder.
	 *
	 * @param helper an <code>ExistingFileHelper</code> instance
	 * @param output a <code>PackOutput</code> instance
	 */
	public StructureUpdater(ExistingFileHelper helper, PackOutput output) {
		this.output = output;

		try {
			Field serverData = ExistingFileHelper.class.getDeclaredField("serverData");
			serverData.setAccessible(true);
			resources = (MultiPackResourceManager) serverData.get(helper);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		try {
			for (Entry<ResourceLocation, Resource> entry : resources.listResources("structures", location -> true).entrySet()) {
				if (entry.getKey().getNamespace().equals(ImmersiveWeapons.MOD_ID)) {
					ResourceLocation location = entry.getKey();
					Resource resource = entry.getValue();

					CompoundTag inputNBT = NbtIo.readCompressed(resource.open());
					CompoundTag updatedNBT = updateNBT(inputNBT);

					if (!updatedNBT.equals(inputNBT)) {
						ImmersiveWeapons.LOGGER.info("Updating old NBT structure: {}", location);
						writeNBT(location, updatedNBT, cache);
					}
				}
			}

			return CompletableFuture.completedFuture(null);
		} catch (IOException e) {
			return CompletableFuture.failedFuture(e);
		}
	}

	/**
	 * Write an NBT file to the output folder.
	 *
	 * @param location the <code>ResourceLocation</code> of the resource
	 * @param data     the <code>CompoundTag</code> data
	 * @param cache    a <code>CachedOutput</code> instance
	 * @throws IOException if an error occurs while writing the file
	 */
	private void writeNBT(ResourceLocation location, CompoundTag data, CachedOutput cache) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		NbtIo.writeCompressed(data, outputStream);
		byte[] bytes = outputStream.toByteArray();
		Path outputPath = output.getOutputFolder().resolve("data/" + location.getNamespace() + "/" + location.getPath());
		cache.writeIfNeeded(outputPath, bytes, Hashing.sha256().hashBytes(bytes));
	}

	/**
	 * Update NBT data to the latest version. This is done by passing the data through a datafixer and then saving
	 * it back to NBT.
	 *
	 * @param nbt the <code>CompoundTag</code> to update
	 * @return CompoundTag
	 */
	private static CompoundTag updateNBT(CompoundTag nbt) {
		final CompoundTag updatedNBT = DataFixTypes.STRUCTURE.updateToCurrentVersion(DataFixers.getDataFixer(), nbt, nbt.getInt("DataVersion"));
		StructureTemplate template = new StructureTemplate();
		template.load(BuiltInRegistries.BLOCK.asLookup(), updatedNBT);
		return template.save(new CompoundTag());
	}

	@Override
	public String getName() {
		return "Structure updater for mod id " + ImmersiveWeapons.MOD_ID;
	}
}