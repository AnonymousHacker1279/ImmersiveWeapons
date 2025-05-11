package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.*;
import tech.anonymoushacker1279.immersiveweapons.block.barbed_wire.BarbedWireBlock;
import tech.anonymoushacker1279.immersiveweapons.block.barbed_wire.BarbedWireFenceBlock;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import tech.anonymoushacker1279.immersiveweapons.block.core.StrippablePillarBlock;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.*;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.*;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomSkullBlock;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomSkullTypes;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomWallSkullBlock;
import tech.anonymoushacker1279.immersiveweapons.block.misc.ChampionKeycardBlock;
import tech.anonymoushacker1279.immersiveweapons.block.misc.MedicStatueBlock;
import tech.anonymoushacker1279.immersiveweapons.block.misc.MinutemanStatueBlock;
import tech.anonymoushacker1279.immersiveweapons.block.misc.TiltrosPortalBlock;
import tech.anonymoushacker1279.immersiveweapons.block.mud.DriedMudBlock;
import tech.anonymoushacker1279.immersiveweapons.block.mud.HardenedMudWindowBlock;
import tech.anonymoushacker1279.immersiveweapons.block.mud.IWMudBlock;
import tech.anonymoushacker1279.immersiveweapons.block.properties.WoodTypes;
import tech.anonymoushacker1279.immersiveweapons.block.sign.CustomCeilingHangingSignBlock;
import tech.anonymoushacker1279.immersiveweapons.block.sign.CustomStandingSignBlock;
import tech.anonymoushacker1279.immersiveweapons.block.sign.CustomWallHangingSignBlock;
import tech.anonymoushacker1279.immersiveweapons.block.sign.CustomWallSignBlock;
import tech.anonymoushacker1279.immersiveweapons.block.star_forge.SolarLensBlock;
import tech.anonymoushacker1279.immersiveweapons.block.star_forge.StarForgeControllerBlock;
import tech.anonymoushacker1279.immersiveweapons.util.markers.DatagenExclusionMarker;
import tech.anonymoushacker1279.immersiveweapons.util.markers.DatagenExclusionMarker.Type;
import tech.anonymoushacker1279.immersiveweapons.util.markers.LanguageEntryOverride;
import tech.anonymoushacker1279.immersiveweapons.util.markers.TextureMetadataMarker;
import tech.anonymoushacker1279.immersiveweapons.world.level.CustomBlockSetTypes;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class BlockRegistry {

	// Block Register
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ImmersiveWeapons.MOD_ID);

	public static final ResourceKey<ConfiguredFeature<?, ?>> STARDUST_TREE_CONFIGURATION = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "stardust_tree"));

	// Blocks
	// Breakable via pickaxe
	// Wooden tier
	public static final Supplier<BarrelTapBlock> BARREL_TAP = BLOCKS.registerBlock("barrel_tap", (properties) -> new BarrelTapBlock(properties.mapColor(MapColor.METAL).mapColor(MapColor.METAL).strength(1.0f).sound(SoundType.METAL)));
	public static final Supplier<MortarBlock> MORTAR = BLOCKS.registerBlock("mortar", (properties) -> new MortarBlock(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0f).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<Block> CLOUD_MARBLE = BLOCKS.registerBlock("cloud_marble", (properties) -> new Block(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<Block> CLOUD_MARBLE_BRICKS = BLOCKS.registerBlock("cloud_marble_bricks", (properties) -> new Block(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<RotatedPillarBlock> CLOUD_MARBLE_PILLAR = BLOCKS.registerBlock("cloud_marble_pillar", (properties) -> new RotatedPillarBlock(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<StairBlock> CLOUD_MARBLE_BRICK_STAIRS = BLOCKS.registerBlock("cloud_marble_brick_stairs", (properties) -> new StairBlock(CLOUD_MARBLE_BRICKS.get().defaultBlockState(), properties));
	public static final Supplier<SlabBlock> CLOUD_MARBLE_BRICK_SLAB = BLOCKS.registerBlock("cloud_marble_brick_slab", (properties) -> new SlabBlock(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<WallBlock> CLOUD_MARBLE_BRICK_WALL = BLOCKS.registerBlock("cloud_marble_brick_wall", WallBlock::new);
	public static final Supplier<FlagPoleBlock> FLAG_POLE = BLOCKS.registerBlock("flag_pole", (properties) -> new FlagPoleBlock(properties.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> AMERICAN_FLAG = BLOCKS.registerBlock("american_flag", (properties) -> new FlagBlock(properties.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> GADSDEN_FLAG = BLOCKS.registerBlock("gadsden_flag", (properties) -> new FlagBlock(properties.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> CANADIAN_FLAG = BLOCKS.registerBlock("canadian_flag", (properties) -> new FlagBlock(properties.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> MEXICAN_FLAG = BLOCKS.registerBlock("mexican_flag", (properties) -> new FlagBlock(properties.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> BRITISH_FLAG = BLOCKS.registerBlock("british_flag", (properties) -> new FlagBlock(properties.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> TROLL_FLAG = BLOCKS.registerBlock("troll_flag", (properties) -> new FlagBlock(properties.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> IMMERSIVE_WEAPONS_FLAG = BLOCKS.registerBlock("immersive_weapons_flag", (properties) -> new FlagBlock(properties.mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<Block> BLOOD_SANDSTONE = BLOCKS.registerBlock("blood_sandstone", (properties) -> new Block(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<SlabBlock> BLOOD_SANDSTONE_SLAB = BLOCKS.registerBlock("blood_sandstone_slab", SlabBlock::new);
	public static final Supplier<StairBlock> BLOOD_SANDSTONE_STAIRS = BLOCKS.registerBlock("blood_sandstone_stairs", (properties) -> new StairBlock(BLOOD_SANDSTONE.get().defaultBlockState(), properties));
	public static final Supplier<WallBlock> BLOOD_SANDSTONE_WALL = BLOCKS.registerBlock("blood_sandstone_wall", WallBlock::new);
	public static final Supplier<BasicOrientableBlock> CHISELED_BLOOD_SANDSTONE = BLOCKS.registerBlock("chiseled_blood_sandstone", BasicOrientableBlock::new);
	public static final Supplier<BasicOrientableBlock> CUT_BLOOD_SANDSTONE = BLOCKS.registerBlock("cut_blood_sandstone", BasicOrientableBlock::new);
	public static final Supplier<SlabBlock> CUT_BLOOD_SANDSTONE_SLAB = BLOCKS.registerBlock("cut_blood_sandstone_slab", SlabBlock::new);
	public static final Supplier<Block> SMOOTH_BLOOD_SANDSTONE = BLOCKS.registerBlock("smooth_blood_sandstone", (properties) -> new Block(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<SlabBlock> SMOOTH_BLOOD_SANDSTONE_SLAB = BLOCKS.registerBlock("smooth_blood_sandstone_slab", SlabBlock::new);
	public static final Supplier<StairBlock> SMOOTH_BLOOD_SANDSTONE_STAIRS = BLOCKS.registerBlock("smooth_blood_sandstone_stairs", (properties) -> new StairBlock(SMOOTH_BLOOD_SANDSTONE.get().defaultBlockState(), properties));
	// Stone tier
	public static final Supplier<SpotlightBlock> SPOTLIGHT = BLOCKS.registerBlock("spotlight", (properties) -> new SpotlightBlock(properties.mapColor(MapColor.METAL).strength(2.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion().lightLevel((state) -> 0)));
	public static final Supplier<PanelBlock> IRON_PANEL = BLOCKS.registerBlock("iron_panel", (properties) -> new PanelBlock(properties.mapColor(MapColor.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	@LanguageEntryOverride("Iron Panel (Bars)")
	public static final Supplier<PanelBlock> IRON_PANEL_BARS = BLOCKS.registerBlock("iron_panel_bars", (properties) -> new PanelBlock(properties.mapColor(MapColor.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<DropExperienceBlock> SULFUR_ORE = BLOCKS.registerBlock("sulfur_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> DEEPSLATE_SULFUR_ORE = BLOCKS.registerBlock("deepslate_sulfur_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(4.5f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> NETHER_SULFUR_ORE = BLOCKS.registerBlock("nether_sulfur_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.4f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	@LanguageEntryOverride("Block of Raw Sulfur")
	public static final Supplier<Block> RAW_SULFUR_BLOCK = BLOCKS.registerBlock("raw_sulfur_block", (properties) -> new Block(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.4f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<Block> POTASSIUM_NITRATE_ORE = BLOCKS.registerBlock("potassium_nitrate_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<AstralCrystalBlock> ASTRAL_CRYSTAL = BLOCKS.registerBlock("astral_crystal", (properties) -> new AstralCrystalBlock(7, 3, properties.mapColor(MapColor.COLOR_PURPLE).randomTicks().strength(0.4f).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().lightLevel((blockState) -> 6)));
	public static final Supplier<BiodomeLifeSupportUnitBlock> BIODOME_LIFE_SUPPORT_UNIT = BLOCKS.registerBlock("biodome_life_support_unit", (properties) -> new BiodomeLifeSupportUnitBlock(properties.mapColor(MapColor.METAL).strength(4.0f).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops()));
	public static final Supplier<Block> RUSTED_IRON_BLOCK = BLOCKS.registerBlock("rusted_iron_block", (properties) -> new Block(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0f, 5.0f).requiresCorrectToolForDrops()));
	public static final Supplier<Block> CHAMPION_BRICKS = BLOCKS.registerBlock("champion_bricks", (properties) -> new Block(properties.mapColor(MapColor.COLOR_GREEN).strength(6.0f, 99.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops()));
	@TextureMetadataMarker(frameTime = 8, interpolate = true)
	public static final Supplier<Block> CHAMPION_BASE = BLOCKS.registerBlock("champion_base", (properties) -> new Block(properties.mapColor(MapColor.COLOR_GRAY).strength(4.0f, 99.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops()));
	public static final Supplier<ChampionKeycardBlock> CHAMPION_KEYCARD_BRICKS = BLOCKS.registerBlock("champion_keycard_bricks", (properties) -> new ChampionKeycardBlock(properties.mapColor(MapColor.COLOR_GRAY).strength(6.0f, 99.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops()));
	public static final Supplier<CommanderPedestalBlock> COMMANDER_PEDESTAL = BLOCKS.registerBlock("commander_pedestal", (properties) -> new CommanderPedestalBlock(properties.mapColor(MapColor.COLOR_GRAY).strength(2.5f).sound(SoundType.STONE).noOcclusion().requiresCorrectToolForDrops()));
	// Iron tier
	public static final Supplier<BarbedWireFenceBlock> BARBED_WIRE_FENCE = BLOCKS.registerBlock("barbed_wire_fence", (properties) -> new BarbedWireFenceBlock(properties.mapColor(MapColor.METAL).strength(7.0f, 8.0f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<DropExperienceBlock> COBALT_ORE = BLOCKS.registerBlock("cobalt_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> DEEPSLATE_COBALT_ORE = BLOCKS.registerBlock("deepslate_cobalt_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(4.5f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
	@LanguageEntryOverride("Block of Cobalt")
	public static final Supplier<Block> COBALT_BLOCK = BLOCKS.registerBlock("cobalt_block", (properties) -> new Block(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(6.0f).requiresCorrectToolForDrops()));
	@LanguageEntryOverride("Block of Raw Cobalt")
	public static final Supplier<Block> RAW_COBALT_BLOCK = BLOCKS.registerBlock("raw_cobalt_block", (properties) -> new Block(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0f).requiresCorrectToolForDrops()));
	public static final Supplier<BearTrapBlock> BEAR_TRAP = BLOCKS.registerBlock("bear_trap", (properties) -> new BearTrapBlock(properties.mapColor(MapColor.METAL).noOcclusion().strength(2.0f).sound(SoundType.METAL)));
	public static final Supplier<BarbedWireBlock> BARBED_WIRE = BLOCKS.registerBlock("barbed_wire", (properties) -> new BarbedWireBlock(properties.mapColor(MapColor.METAL).strength(2.0f).sound(SoundType.CHAIN).noOcclusion().noCollission()));
	public static final Supplier<SpikeTrapBlock> SPIKE_TRAP = BLOCKS.registerBlock("spike_trap", (properties) -> new SpikeTrapBlock(properties.mapColor(MapColor.METAL).strength(2.0f).sound(SoundType.METAL).noOcclusion().noCollission()));
	public static final Supplier<CelestialLanternBlock> CELESTIAL_LANTERN = BLOCKS.registerBlock("celestial_lantern", (properties) -> new CelestialLanternBlock(properties.mapColor(MapColor.METAL).strength(3.5f).sound(SoundType.LANTERN).requiresCorrectToolForDrops().lightLevel((blockState) -> 15).noOcclusion()));
	public static final Supplier<StarForgeControllerBlock> STAR_FORGE_CONTROLLER = BLOCKS.registerBlock("star_forge_controller", (properties) -> new StarForgeControllerBlock(properties.mapColor(MapColor.COLOR_CYAN).strength(5.0f, 10.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<Block> STAR_FORGE_BRICKS = BLOCKS.registerBlock("star_forge_bricks", (properties) -> new Block(properties.mapColor(MapColor.COLOR_CYAN).strength(5.0f, 10.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops()));
	public static final Supplier<TeleporterBlock> TELEPORTER = BLOCKS.registerBlock("teleporter", (properties) -> new TeleporterBlock(properties.mapColor(MapColor.METAL).strength(3.5f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	// Diamond tier
	@TextureMetadataMarker(frameTime = 12, interpolate = true)
	public static final Supplier<DropExperienceBlock> MOLTEN_ORE = BLOCKS.registerBlock("molten_ore", (properties) -> new DropExperienceBlock(UniformInt.of(5, 10), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(6.0f, 8.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> ELECTRIC_ORE = BLOCKS.registerBlock("electric_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(6.0f, 8.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> VENTUS_ORE = BLOCKS.registerBlock("ventus_ore", (properties) -> new DropExperienceBlock(UniformInt.of(5, 10), properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(3.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	@TextureMetadataMarker(frameTime = 12, interpolate = true)
	public static final Supplier<Block> MOLTEN_BLOCK = BLOCKS.registerBlock("molten_block", (properties) -> new Block(properties.mapColor(MapColor.METAL).strength(45.0f, 1100.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
	@TextureMetadataMarker(frameTime = 2, interpolate = true)
	public static final Supplier<Block> TESLA_BLOCK = BLOCKS.registerBlock("tesla_block", (properties) -> new BasicOrientableBlock(properties.mapColor(MapColor.METAL).strength(35.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
	public static final Supplier<TeslaSynthesizerBlock> TESLA_SYNTHESIZER = BLOCKS.registerBlock("tesla_synthesizer", (properties) -> new TeslaSynthesizerBlock(properties.mapColor(MapColor.METAL).strength(10.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<Block> TILTROS_PORTAL_FRAME = BLOCKS.registerBlock("tiltros_portal_frame", (properties) -> new Block(properties.mapColor(MapColor.COLOR_BLACK).strength(50.0f, 1200f).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion()));
	// Netherite tier
	public static final Supplier<DropExperienceBlock> ASTRAL_ORE = BLOCKS.registerBlock("astral_ore", (properties) -> new DropExperienceBlock(UniformInt.of(7, 12), properties.mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(8.0f, 10.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<Block> ASTRAL_BLOCK = BLOCKS.registerBlock("astral_block", (properties) -> new Block(properties.mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(25.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<AmethystClusterBlock> STARSTORM_CRYSTAL = BLOCKS.registerBlock("starstorm_crystal", (properties) -> new StarstormCrystalBlock(7, 3, properties.mapColor(MapColor.COLOR_ORANGE).randomTicks().strength(0.4f).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().lightLevel((blockState) -> 6)));
	@TextureMetadataMarker(frameTime = 24, interpolate = true, frames = {0, 1, 2, 3, 2, 1})
	public static final Supplier<Block> STARSTORM_BLOCK = BLOCKS.registerBlock("starstorm_block", (properties) -> new Block(properties.mapColor(MapColor.COLOR_ORANGE).strength(30.0f).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
	// Astral / Starstorm tier
	@TextureMetadataMarker(frameTime = 36, interpolate = true)
	public static final Supplier<Block> VOID_ORE = BLOCKS.registerBlock("void_ore", (properties) -> new DropExperienceBlock(ConstantInt.of(0), properties.mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).strength(8.0f, 10.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

	// Breakable via axe
	// Wood tier
	public static final Supplier<SmallPartsTableBlock> SMALL_PARTS_TABLE = BLOCKS.registerBlock("small_parts_table", (properties) -> new SmallPartsTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.5f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));
	public static final Supplier<AmmunitionTableBlock> AMMUNITION_TABLE = BLOCKS.registerBlock("ammunition_table", (properties) -> new AmmunitionTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.5f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<RotatedPillarBlock> STRIPPED_BURNED_OAK_WOOD = BLOCKS.registerBlock("stripped_burned_oak_wood", (properties) -> new RotatedPillarBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> STRIPPED_BURNED_OAK_LOG = BLOCKS.registerBlock("stripped_burned_oak_log", (properties) -> new RotatedPillarBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7F).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> BURNED_OAK_WOOD = BLOCKS.registerBlock("burned_oak_wood", (properties) -> new StrippablePillarBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f).sound(SoundType.WOOD), STRIPPED_BURNED_OAK_WOOD.get().defaultBlockState()));
	public static final Supplier<RotatedPillarBlock> BURNED_OAK_LOG = BLOCKS.registerBlock("burned_oak_log", (properties) -> new StrippablePillarBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f).sound(SoundType.WOOD), STRIPPED_BURNED_OAK_LOG.get().defaultBlockState()));
	public static final Supplier<Block> BURNED_OAK_PLANKS = BLOCKS.registerBlock("burned_oak_planks", (properties) -> new Block(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD)));
	public static final Supplier<StairBlock> BURNED_OAK_STAIRS = BLOCKS.registerBlock("burned_oak_stairs", (properties) -> new StairBlock(BURNED_OAK_PLANKS.get().defaultBlockState(), properties));
	public static final Supplier<SlabBlock> BURNED_OAK_SLAB = BLOCKS.registerBlock("burned_oak_slab", SlabBlock::new);
	public static final Supplier<FenceBlock> BURNED_OAK_FENCE = BLOCKS.registerBlock("burned_oak_fence", (properties) -> new FenceBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD)));
	public static final Supplier<FenceGateBlock> BURNED_OAK_FENCE_GATE = BLOCKS.registerBlock("burned_oak_fence_gate", (properties) -> new FenceGateBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
	public static final Supplier<DoorBlock> BURNED_OAK_DOOR = BLOCKS.registerBlock("burned_oak_door", (properties) -> new DoorBlock(CustomBlockSetTypes.BURNED_OAK, properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<TrapDoorBlock> BURNED_OAK_TRAPDOOR = BLOCKS.registerBlock("burned_oak_trapdoor", (properties) -> new TrapDoorBlock(CustomBlockSetTypes.BURNED_OAK, properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<PressurePlateBlock> BURNED_OAK_PRESSURE_PLATE = BLOCKS.registerBlock("burned_oak_pressure_plate", (properties) -> new PressurePlateBlock(CustomBlockSetTypes.BURNED_OAK, properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(0.4f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final Supplier<CustomStandingSignBlock> BURNED_OAK_SIGN = BLOCKS.registerBlock("burned_oak_sign", (properties) -> new CustomStandingSignBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSignBlock> BURNED_OAK_WALL_SIGN = BLOCKS.registerBlock("burned_oak_wall_sign", (properties) -> new CustomWallSignBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	public static final Supplier<CustomCeilingHangingSignBlock> BURNED_OAK_HANGING_SIGN = BLOCKS.registerBlock("burned_oak_hanging_sign", (properties) -> new CustomCeilingHangingSignBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallHangingSignBlock> BURNED_OAK_WALL_HANGING_SIGN = BLOCKS.registerBlock("burned_oak_wall_hanging_sign", (properties) -> new CustomWallHangingSignBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	public static final Supplier<ButtonBlock> BURNED_OAK_BUTTON = BLOCKS.registerBlock("burned_oak_button", (properties) -> new ButtonBlock(CustomBlockSetTypes.BURNED_OAK, 30, properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.4f).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> STRIPPED_STARDUST_WOOD = BLOCKS.registerBlock("stripped_stardust_wood", (properties) -> new RotatedPillarBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> STRIPPED_STARDUST_LOG = BLOCKS.registerBlock("stripped_stardust_log", (properties) -> new RotatedPillarBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> STARDUST_LOG = BLOCKS.registerBlock("stardust_log", (properties) -> new StrippablePillarBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD), STRIPPED_STARDUST_LOG.get().defaultBlockState()));
	public static final Supplier<RotatedPillarBlock> STARDUST_WOOD = BLOCKS.registerBlock("stardust_wood", (properties) -> new StrippablePillarBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD), STRIPPED_STARDUST_WOOD.get().defaultBlockState()));
	public static final Supplier<Block> STARDUST_PLANKS = BLOCKS.registerBlock("stardust_planks", (properties) -> new Block(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD)));
	public static final Supplier<StairBlock> STARDUST_STAIRS = BLOCKS.registerBlock("stardust_stairs", (properties) -> new StairBlock(STARDUST_PLANKS.get().defaultBlockState(), properties));
	public static final Supplier<SlabBlock> STARDUST_SLAB = BLOCKS.registerBlock("stardust_slab", SlabBlock::new);
	public static final Supplier<FenceBlock> STARDUST_FENCE = BLOCKS.registerBlock("stardust_fence", (properties) -> new FenceBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD)));
	public static final Supplier<FenceGateBlock> STARDUST_FENCE_GATE = BLOCKS.registerBlock("stardust_fence_gate", (properties) -> new FenceGateBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
	public static final Supplier<DoorBlock> STARDUST_DOOR = BLOCKS.registerBlock("stardust_door", (properties) -> new DoorBlock(CustomBlockSetTypes.STARDUST, properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<TrapDoorBlock> STARDUST_TRAPDOOR = BLOCKS.registerBlock("stardust_trapdoor", (properties) -> new TrapDoorBlock(CustomBlockSetTypes.STARDUST, properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<PressurePlateBlock> STARDUST_PRESSURE_PLATE = BLOCKS.registerBlock("stardust_pressure_plate", (properties) -> new PressurePlateBlock(CustomBlockSetTypes.STARDUST, properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(0.7f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final Supplier<ButtonBlock> STARDUST_BUTTON = BLOCKS.registerBlock("stardust_button", (properties) -> new ButtonBlock(CustomBlockSetTypes.STARDUST, 30, properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.7f).sound(SoundType.WOOD)));
	public static final Supplier<CustomStandingSignBlock> STARDUST_SIGN = BLOCKS.registerBlock("stardust_sign", (properties) -> new CustomStandingSignBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSignBlock> STARDUST_WALL_SIGN = BLOCKS.registerBlock("stardust_wall_sign", (properties) -> new CustomWallSignBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	public static final Supplier<CustomCeilingHangingSignBlock> STARDUST_HANGING_SIGN = BLOCKS.registerBlock("stardust_hanging_sign", (properties) -> new CustomCeilingHangingSignBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallHangingSignBlock> STARDUST_WALL_HANGING_SIGN = BLOCKS.registerBlock("stardust_wall_hanging_sign", (properties) -> new CustomWallHangingSignBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	public static final Supplier<WoodenTableBlock> OAK_TABLE = BLOCKS.registerBlock("oak_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> SPRUCE_TABLE = BLOCKS.registerBlock("spruce_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> BIRCH_TABLE = BLOCKS.registerBlock("birch_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> JUNGLE_TABLE = BLOCKS.registerBlock("jungle_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> ACACIA_TABLE = BLOCKS.registerBlock("acacia_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> DARK_OAK_TABLE = BLOCKS.registerBlock("dark_oak_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> CRIMSON_TABLE = BLOCKS.registerBlock("crimson_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> WARPED_TABLE = BLOCKS.registerBlock("warped_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> MANGROVE_TABLE = BLOCKS.registerBlock("mangrove_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> CHERRY_TABLE = BLOCKS.registerBlock("cherry_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> BAMBOO_TABLE = BLOCKS.registerBlock("bamboo_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.BAMBOO).noOcclusion()));
	public static final Supplier<WoodenTableBlock> BURNED_OAK_TABLE = BLOCKS.registerBlock("burned_oak_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> STARDUST_TABLE = BLOCKS.registerBlock("stardust_table", (properties) -> new WoodenTableBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	// Stone tier
	public static final Supplier<WoodenSpikesBlock> WOODEN_SPIKES = BLOCKS.registerBlock("wooden_spikes", (properties) -> new WoodenSpikesBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<CelestialAltarBlock> CELESTIAL_ALTAR = BLOCKS.registerBlock("celestial_altar", (properties) -> new CelestialAltarBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));

	// Breakable via shovel
	// Wood tier
	public static final Supplier<SandbagBlock> SANDBAG = BLOCKS.registerBlock("sandbag", (properties) -> new SandbagBlock(properties.mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(4.0f, 5.0f).sound(SoundType.SAND).noOcclusion()));
	public static final Supplier<Block> MUD = BLOCKS.registerBlock("mud", (properties) -> new IWMudBlock(properties.mapColor(MapColor.DIRT).mapColor(MapColor.DIRT).strength(0.8f, 0.3f).sound(SoundType.WET_GRASS).speedFactor(0.75f).randomTicks()));
	public static final Supplier<Block> DRIED_MUD = BLOCKS.registerBlock("dried_mud", (properties) -> new DriedMudBlock(properties.mapColor(MapColor.DIRT).strength(1.0f, 0.7f).sound(SoundType.ROOTED_DIRT).randomTicks()));
	public static final Supplier<Block> HARDENED_MUD = BLOCKS.registerBlock("hardened_mud", (properties) -> new Block(properties.mapColor(MapColor.DIRT).strength(2.0f, 1.0f).sound(SoundType.ROOTED_DIRT)));
	public static final Supplier<StairBlock> HARDENED_MUD_STAIRS = BLOCKS.registerBlock("hardened_mud_stairs", (properties) -> new StairBlock(HARDENED_MUD.get().defaultBlockState(), properties));
	public static final Supplier<SlabBlock> HARDENED_MUD_SLAB = BLOCKS.registerBlock("hardened_mud_slab", (properties) -> new SlabBlock(properties.mapColor(MapColor.DIRT).strength(2.0f, 1.0f).sound(SoundType.ROOTED_DIRT)));
	public static final Supplier<Block> HARDENED_MUD_WINDOW = BLOCKS.registerBlock("hardened_mud_window", (properties) -> new HardenedMudWindowBlock(properties.mapColor(MapColor.DIRT).strength(2.0f, 1.0f).sound(SoundType.ROOTED_DIRT).noOcclusion()));
	public static final Supplier<ColoredFallingBlock> BLOOD_SAND = BLOCKS.registerBlock("blood_sand", (properties) -> new ColoredFallingBlock(new ColorRGBA(13201254), properties.mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sound(SoundType.SAND)));
	// Stone tier
	public static final Supplier<PunjiSticksBlock> PUNJI_STICKS = BLOCKS.registerBlock("punji_sticks", (properties) -> new PunjiSticksBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(5.0f, 1.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));

	// Breakable via hoe
	// Wood tier
	public static final Supplier<StardustLeavesBlock> STARDUST_LEAVES = BLOCKS.registerBlock("stardust_leaves", (properties) -> new StardustLeavesBlock(properties.mapColor(MapColor.PLANT).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.3f).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((state, getter, pos, type) -> (type == EntityType.OCELOT || type == EntityType.PARROT)).isSuffocating((state, getter, pos) -> false).isViewBlocking((state, getter, pos) -> false)));

	// Breakable without a tool
	public static final Supplier<TransparentBlock> BULLETPROOF_GLASS = BLOCKS.registerBlock("bulletproof_glass", (properties) -> new TransparentBlock(properties.instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> WHITE_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("white_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.WHITE, properties.mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> LIGHT_GRAY_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("light_gray_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.LIGHT_GRAY, properties.mapColor(DyeColor.LIGHT_GRAY).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> GRAY_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("gray_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.GRAY, properties.mapColor(DyeColor.GRAY).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> BLACK_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("black_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.BLACK, properties.mapColor(DyeColor.BLACK).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> ORANGE_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("orange_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.ORANGE, properties.mapColor(DyeColor.ORANGE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> MAGENTA_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("magenta_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.MAGENTA, properties.mapColor(DyeColor.MAGENTA).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> LIGHT_BLUE_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("light_blue_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.LIGHT_BLUE, properties.mapColor(DyeColor.LIGHT_BLUE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> YELLOW_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("yellow_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.YELLOW, properties.mapColor(DyeColor.YELLOW).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> LIME_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("lime_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.LIME, properties.mapColor(DyeColor.LIME).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> PINK_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("pink_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.PINK, properties.mapColor(DyeColor.PINK).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> CYAN_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("cyan_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.CYAN, properties.mapColor(DyeColor.CYAN).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> PURPLE_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("purple_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.PURPLE, properties.mapColor(DyeColor.PURPLE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> BLUE_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("blue_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.BLUE, properties.mapColor(DyeColor.BLUE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> BROWN_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("brown_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.BROWN, properties.mapColor(DyeColor.BROWN).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> GREEN_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("green_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.GREEN, properties.mapColor(DyeColor.GREEN).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> RED_STAINED_BULLETPROOF_GLASS = BLOCKS.registerBlock("red_stained_bulletproof_glass", (properties) -> new StainedGlassBlock(DyeColor.RED, properties.mapColor(DyeColor.RED).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<PitfallBlock> PITFALL = BLOCKS.registerBlock("pitfall", (properties) -> new PitfallBlock(properties.mapColor(MapColor.GRASS).strength(0.2f, 1.0f).sound(SoundType.GRAVEL).randomTicks()));
	public static final Supplier<LandmineBlock> LANDMINE = BLOCKS.registerBlock("landmine", (properties) -> new LandmineBlock(properties.mapColor(MapColor.METAL).strength(1.0F).sound(SoundType.METAL)));
	public static final Supplier<MinutemanStatueBlock> MINUTEMAN_STATUE = BLOCKS.registerBlock("minuteman_statue", (properties) -> new MinutemanStatueBlock(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0f).sound(SoundType.STONE).noOcclusion()));
	public static final Supplier<MedicStatueBlock> MEDIC_STATUE = BLOCKS.registerBlock("medic_statue", (properties) -> new MedicStatueBlock(properties.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0f).sound(SoundType.STONE).noOcclusion()));
	public static final Supplier<ShelfBlock> WALL_SHELF = BLOCKS.registerBlock("wall_shelf", (properties) -> new ShelfBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.0f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final Supplier<PanicAlarmBlock> PANIC_ALARM = BLOCKS.registerBlock("panic_alarm", (properties) -> new PanicAlarmBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.0f).sound(SoundType.WOOD).noOcclusion().randomTicks()));
	public static final Supplier<BiohazardBoxBlock> BIOHAZARD_BOX = BLOCKS.registerBlock("biohazard_box", (properties) -> new BiohazardBoxBlock(properties.pushReaction(PushReaction.DESTROY).pushReaction(PushReaction.DESTROY).strength(0.5f).sound(SoundType.LANTERN).noOcclusion()));
	public static final Supplier<HalfTransparentBlock> CLOUD = BLOCKS.registerBlock("cloud", (properties) -> new HalfTransparentBlock(properties.replaceable().strength(0.7f).sound(SoundType.SNOW).noOcclusion()));
	public static final Supplier<CampChairBlock> CAMP_CHAIR = BLOCKS.registerBlock("camp_chair", (properties) -> new CampChairBlock(properties.mapColor(MapColor.WOOL).ignitedByLava().strength(1.0f).sound(SoundType.WOOL).noOcclusion()));
	public static final Supplier<BranchBlock> BURNED_OAK_BRANCH = BLOCKS.registerBlock("burned_oak_branch", (properties) -> new BranchBlock(properties.mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(0.1f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final Supplier<SkullBlock> MINUTEMAN_HEAD = BLOCKS.registerBlock("minuteman_head", (properties) -> new CustomSkullBlock(CustomSkullTypes.MINUTEMAN, properties.pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> MINUTEMAN_WALL_HEAD = BLOCKS.registerBlock("minuteman_wall_head", (properties) -> new CustomWallSkullBlock(CustomSkullTypes.MINUTEMAN, properties.pushReaction(PushReaction.DESTROY).strength(1.0f).overrideLootTable(MINUTEMAN_HEAD.get().getLootTable())));
	public static final Supplier<SkullBlock> FIELD_MEDIC_HEAD = BLOCKS.registerBlock("field_medic_head", (properties) -> new CustomSkullBlock(CustomSkullTypes.FIELD_MEDIC, properties.pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> FIELD_MEDIC_WALL_HEAD = BLOCKS.registerBlock("field_medic_wall_head", (properties) -> new CustomWallSkullBlock(CustomSkullTypes.FIELD_MEDIC, properties.pushReaction(PushReaction.DESTROY).strength(1.0f).overrideLootTable(FIELD_MEDIC_HEAD.get().getLootTable())));
	public static final Supplier<SkullBlock> DYING_SOLDIER_HEAD = BLOCKS.registerBlock("dying_soldier_head", (properties) -> new CustomSkullBlock(CustomSkullTypes.DYING_SOLDIER, properties.pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> DYING_SOLDIER_WALL_HEAD = BLOCKS.registerBlock("dying_soldier_wall_head", (properties) -> new CustomWallSkullBlock(CustomSkullTypes.DYING_SOLDIER, properties.pushReaction(PushReaction.DESTROY).strength(1.0f).overrideLootTable(DYING_SOLDIER_HEAD.get().getLootTable())));
	public static final Supplier<SkullBlock> THE_COMMANDER_HEAD = BLOCKS.registerBlock("the_commander_head", (properties) -> new CustomSkullBlock(CustomSkullTypes.THE_COMMANDER, properties.pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> THE_COMMANDER_WALL_HEAD = BLOCKS.registerBlock("the_commander_wall_head", (properties) -> new CustomWallSkullBlock(CustomSkullTypes.THE_COMMANDER, properties.pushReaction(PushReaction.DESTROY).strength(1.0f).overrideLootTable(THE_COMMANDER_HEAD.get().getLootTable())));
	public static final Supplier<SkullBlock> WANDERING_WARRIOR_HEAD = BLOCKS.registerBlock("wandering_warrior_head", (properties) -> new CustomSkullBlock(CustomSkullTypes.WANDERING_WARRIOR, properties.pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> WANDERING_WARRIOR_WALL_HEAD = BLOCKS.registerBlock("wandering_warrior_wall_head", (properties) -> new CustomWallSkullBlock(CustomSkullTypes.WANDERING_WARRIOR, properties.pushReaction(PushReaction.DESTROY).strength(1.0f).overrideLootTable(WANDERING_WARRIOR_HEAD.get().getLootTable())));
	public static final Supplier<SkullBlock> HANS_HEAD = BLOCKS.registerBlock("hans_head", (properties) -> new CustomSkullBlock(CustomSkullTypes.HANS, properties.pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> HANS_WALL_HEAD = BLOCKS.registerBlock("hans_wall_head", (properties) -> new CustomWallSkullBlock(CustomSkullTypes.HANS, properties.pushReaction(PushReaction.DESTROY).strength(1.0f).overrideLootTable(HANS_HEAD.get().getLootTable())));
	public static final Supplier<SkullBlock> STORM_CREEPER_HEAD = BLOCKS.registerBlock("storm_creeper_head", (properties) -> new CustomSkullBlock(CustomSkullTypes.STORM_CREEPER, properties.pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> STORM_CREEPER_WALL_HEAD = BLOCKS.registerBlock("storm_creeper_wall_head", (properties) -> new CustomWallSkullBlock(CustomSkullTypes.STORM_CREEPER, properties.pushReaction(PushReaction.DESTROY).strength(1.0f).overrideLootTable(STORM_CREEPER_HEAD.get().getLootTable())));
	public static final Supplier<SkullBlock> SKELETON_MERCHANT_HEAD = BLOCKS.registerBlock("skeleton_merchant_head", (properties) -> new CustomSkullBlock(CustomSkullTypes.SKELETON_MERCHANT, properties.pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> SKELETON_MERCHANT_WALL_HEAD = BLOCKS.registerBlock("skeleton_merchant_wall_head", (properties) -> new CustomWallSkullBlock(CustomSkullTypes.SKELETON_MERCHANT, properties.pushReaction(PushReaction.DESTROY).strength(1.0f).overrideLootTable(SKELETON_MERCHANT_HEAD.get().getLootTable())));
	public static final Supplier<MoonglowBlock> MOONGLOW = BLOCKS.registerBlock("moonglow", (properties) -> new MoonglowBlock(MobEffects.GLOWING, 10, properties.mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).lightLevel((state) -> 12).noCollission().instabreak().offsetType(OffsetType.XZ)));
	public static final Supplier<FlowerPotBlock> POTTED_MOONGLOW = BLOCKS.registerBlock("potted_moonglow", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, MOONGLOW, properties.pushReaction(PushReaction.DESTROY).lightLevel((state) -> 12).instabreak().noOcclusion()));
	public static final Supplier<DeathweedBlock> DEATHWEED = BLOCKS.registerBlock("deathweed", (properties) -> new DeathweedBlock(MobEffects.INSTANT_DAMAGE, 0, properties.mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).lightLevel((state) -> 4).noCollission().instabreak().offsetType(OffsetType.XZ)));
	public static final Supplier<FlowerPotBlock> POTTED_DEATHWEED = BLOCKS.registerBlock("potted_deathweed", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DEATHWEED, properties.pushReaction(PushReaction.DESTROY).lightLevel((state) -> 12).instabreak().noOcclusion()));
	public static final Supplier<SaplingBlock> STARDUST_SAPLING = BLOCKS.registerBlock("stardust_sapling", (properties) -> new SaplingBlock(new TreeGrower("stardust_sapling", Optional.empty(), Optional.of(STARDUST_TREE_CONFIGURATION), Optional.empty()), properties.mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).strength(0.0f).sound(SoundType.GRASS).noCollission().instabreak().offsetType(OffsetType.NONE)));
	public static final Supplier<SolarLensBlock> SOLAR_LENS = BLOCKS.registerBlock("solar_lens", (properties) -> new SolarLensBlock(properties.mapColor(MapColor.METAL).pushReaction(PushReaction.DESTROY).strength(0.5f).sound(SoundType.GLASS).noOcclusion()));
	@TextureMetadataMarker
	public static final Supplier<TiltrosPortalBlock> TILTROS_PORTAL = BLOCKS.registerBlock("tiltros_portal", (properties) -> new TiltrosPortalBlock(properties.mapColor(MapColor.COLOR_BLACK).pushReaction(PushReaction.BLOCK).strength(-1.0f, 3600000f).noCollission().lightLevel(state -> 15).noLootTable()));
}