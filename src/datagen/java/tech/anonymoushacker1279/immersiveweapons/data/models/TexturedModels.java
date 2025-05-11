package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class TexturedModels {

	public static final TexturedModel.Provider TRIVIAL_CUBE_CUTOUT_MIPPED = TexturedModel.createDefault(block -> new TextureMapping()
					.put(TextureSlot.ALL, TextureMapping.getBlockTexture(block)),
			IWModelTemplates.TRIVIAL_CUBE_CUTOUT_MIPPED
	);
	public static final TexturedModel.Provider TRIVIAL_CUBE_TRANSLUCENT = TexturedModel.createDefault(block -> new TextureMapping()
					.put(TextureSlot.ALL, TextureMapping.getBlockTexture(block)),
			IWModelTemplates.TRIVIAL_CUBE_TRANSLUCENT
	);
	public static final TexturedModel.Provider STARDUST_LEAVES = TexturedModel.createDefault(block -> new TextureMapping()
					.put(TextureSlot.ALL, TextureMapping.getBlockTexture(block))
					.put(TextureSlot.LAYER0, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_granule_overlay"))
					.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)),
			IWModelTemplates.STARDUST_LEAVES
	);
	public static final TexturedModel.Provider STONE_ORE = TexturedModel.createDefault(block -> new TextureMapping()
					.put(TextureSlot.ALL, TextureMapping.getBlockTexture(Blocks.STONE))
					.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)),
			IWModelTemplates.ORE
	);
	public static final TexturedModel.Provider DEEPSLATE_ORE = TexturedModel.createDefault(block -> new TextureMapping()
					.put(TextureSlot.ALL, TextureMapping.getBlockTexture(Blocks.DEEPSLATE))
					.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)),
			IWModelTemplates.ORE
	);
	public static final TexturedModel.Provider NETHERRACK_ORE = TexturedModel.createDefault(block -> new TextureMapping()
					.put(TextureSlot.ALL, TextureMapping.getBlockTexture(Blocks.NETHERRACK))
					.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)),
			IWModelTemplates.ORE
	);
	public static final TexturedModel.Provider SMOOTH_QUARTZ_ORE = TexturedModel.createDefault(block -> new TextureMapping()
					.put(TextureSlot.ALL, TextureMapping.getBlockTexture(Blocks.SMOOTH_QUARTZ))
					.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)),
			IWModelTemplates.ORE
	);
	public static final TexturedModel.Provider END_STONE_ORE = TexturedModel.createDefault(block -> new TextureMapping()
					.put(TextureSlot.ALL, TextureMapping.getBlockTexture(Blocks.END_STONE))
					.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)),
			IWModelTemplates.ORE
	);
	public static final TexturedModel.Provider TABLE = TexturedModel.createDefault(block -> new TextureMapping()
					.put(TextureSlot.ALL, TextureMapping.getBlockTexture(block))
					.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block)),
			IWModelTemplates.TABLE
	);
}