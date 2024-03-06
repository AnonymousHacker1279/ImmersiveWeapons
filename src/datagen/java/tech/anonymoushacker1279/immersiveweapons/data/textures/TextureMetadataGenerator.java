package tech.anonymoushacker1279.immersiveweapons.data.textures;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.data.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.markers.TextureMetadataMarker;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Automatically generates animated texture metadata for items and blocks. Registry fields must be annotated with
 * {@link TextureMetadataMarker} to be included in the generation process.
 * <p>
 * Fields under {@link ItemRegistry} and {@link BlockRegistry} are automatically scanned for the annotation.
 */
public class TextureMetadataGenerator implements DataProvider {

	private final PackOutput packOutput;
	protected static final List<TextureMetadata> METADATA = new ArrayList<>(5);

	public TextureMetadataGenerator(PackOutput output) {
		this.packOutput = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput pOutput) {
		addTextures();

		Path path = packOutput.getOutputFolder();
		List<CompletableFuture<?>> futures = new ArrayList<>(5);

		METADATA.forEach(builder -> {
			Path filePath = path.resolve("assets/" + ImmersiveWeapons.MOD_ID + "/textures/" + builder.name() + ".png.mcmeta");

			DataResult<JsonElement> metadataResult = TextureMetadata.CODEC.encodeStart(JsonOps.INSTANCE, builder);
			JsonElement metadataElement = metadataResult.map(jsonElement -> {
						// Remove the name field
						JsonObject jsonObject = jsonElement.getAsJsonObject();
						jsonObject.remove("name");

						// Remove fields that are the default value
						if (!builder.interpolate()) {
							jsonObject.remove("interpolate");
						}
						if (builder.frames().isEmpty()) {
							jsonObject.remove("frames");
						}

						return jsonElement;
					})
					.result()
					.orElseThrow(() -> new IllegalStateException("Failed to encode texture metadata for " + builder.name()));

			// Everything is under a parent "animation" tag
			JsonObject parent = new JsonObject();
			parent.add("animation", metadataElement);

			futures.add(DataProvider.saveStable(pOutput, parent, filePath));
		});

		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}

	private void addTextures() {
		for (Field field : ItemRegistry.class.getDeclaredFields()) {
			if (field.isAnnotationPresent(TextureMetadataMarker.class)) {
				TextureMetadataMarker marker = field.getAnnotation(TextureMetadataMarker.class);

				try {
					if (field.get(null) instanceof DeferredHolder<?, ?> holder) {
						String name = holder.getKey().location().getPath();

						switch (marker.predefinedGroup()) {
							case STARSTORM_ITEMS -> ItemMetadataBuilder(name, 4)
									.setInterpolate(true)
									.setFrameOrder(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
									.build();
							case VENTUS_TOOLS -> ItemMetadataBuilder(name, 5).build();
							default -> ItemMetadataBuilder(name, marker.frameTime())
									.setInterpolate(marker.interpolate())
									.setFrameOrder(marker.frames())
									.build();
						}
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}

		for (Field field : BlockRegistry.class.getDeclaredFields()) {
			if (field.isAnnotationPresent(TextureMetadataMarker.class)) {
				TextureMetadataMarker marker = field.getAnnotation(TextureMetadataMarker.class);

				try {
					if (field.get(null) instanceof DeferredHolder<?, ?> deferredHolder) {
						String name = deferredHolder.getKey().location().getPath();

						BlockMetadataBuilder(name, marker.frameTime())
								.setInterpolate(marker.interpolate())
								.setFrameOrder(marker.frames())
								.build();
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "Texture Metadata";
	}

	public record TextureMetadata(String name, int frameTime, boolean interpolate, List<Integer> frames) {

		public static final Codec<TextureMetadata> CODEC = RecordCodecBuilder.create(inst -> inst.group(
				Codec.STRING.fieldOf("name").forGetter((metadata) -> metadata.name),
				Codec.INT.fieldOf("frametime").forGetter((metadata) -> metadata.frameTime),
				Codec.BOOL.fieldOf("interpolate").forGetter((metadata) -> metadata.interpolate),
				Codec.INT.listOf().fieldOf("frames").forGetter((metadata) -> metadata.frames)
		).apply(inst, TextureMetadata::new));
	}

	protected TextureMetadataBuilder ItemMetadataBuilder(String name, int frameTime) {
		return new TextureMetadataBuilder("item/" + name, frameTime);
	}

	protected TextureMetadataBuilder BlockMetadataBuilder(String name, int frameTime) {
		return new TextureMetadataBuilder("block/" + name, frameTime);
	}

	public static class TextureMetadataBuilder {

		private final String name;
		private final int frameTime;
		private boolean interpolate;
		private final List<Integer> frames = new ArrayList<>(5);

		private TextureMetadataBuilder(String name, int frameTime) {
			this.name = name;
			this.frameTime = frameTime;
		}

		public TextureMetadataBuilder setInterpolate(boolean interpolate) {
			this.interpolate = interpolate;
			return this;
		}

		public TextureMetadataBuilder setFrameOrder(int... frames) {
			for (int frame : frames) {
				this.frames.add(frame);
			}

			return this;
		}

		public TextureMetadataBuilder setFrameOrder(List<Integer> frames) {
			this.frames.addAll(frames);
			return this;
		}

		public void build() {
			METADATA.add(new TextureMetadata(name, frameTime, interpolate, frames));
		}
	}
}