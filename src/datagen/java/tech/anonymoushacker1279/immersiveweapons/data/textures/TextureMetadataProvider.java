package tech.anonymoushacker1279.immersiveweapons.data.textures;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.data.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class TextureMetadataProvider implements DataProvider {

	private final PackOutput packOutput;
	protected static final List<TextureMetadata> metadata = new ArrayList<>(5);

	public TextureMetadataProvider(PackOutput output) {
		this.packOutput = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput pOutput) {
		addTextures();

		Path path = packOutput.getOutputFolder();
		List<CompletableFuture<?>> futures = new ArrayList<>(5);

		metadata.forEach(builder -> {
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

	protected abstract void addTextures();

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
		private boolean interpolate = false;
		private final List<Integer> frames = new ArrayList<>(5);

		private TextureMetadataBuilder(String name, int frameTime) {
			this.name = name;
			this.frameTime = frameTime;
		}

		public TextureMetadataBuilder interpolate() {
			this.interpolate = true;
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
			metadata.add(new TextureMetadata(name, frameTime, interpolate, frames));
		}
	}
}