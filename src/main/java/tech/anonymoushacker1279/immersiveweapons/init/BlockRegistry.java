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
import net.minecraft.world.level.block.state.BlockBehaviour;
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
import tech.anonymoushacker1279.immersiveweapons.block.core.CustomSandBlock;
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
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, ImmersiveWeapons.MOD_ID);

	public static final ResourceKey<ConfiguredFeature<?, ?>> STARDUST_TREE_CONFIGURATION = ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "stardust_tree"));

	// Blocks
	// Breakable via pickaxe
	// Wooden tier
	public static final Supplier<BarrelTapBlock> BARREL_TAP = BLOCKS.register("barrel_tap", () -> new BarrelTapBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).mapColor(MapColor.METAL).strength(1.0f).sound(SoundType.METAL)));
	public static final Supplier<MortarBlock> MORTAR = BLOCKS.register("mortar", () -> new MortarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0f).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<Block> CLOUD_MARBLE = BLOCKS.register("cloud_marble", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<Block> CLOUD_MARBLE_BRICKS = BLOCKS.register("cloud_marble_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<RotatedPillarBlock> CLOUD_MARBLE_PILLAR = BLOCKS.register("cloud_marble_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<StairBlock> CLOUD_MARBLE_BRICK_STAIRS = BLOCKS.register("cloud_marble_brick_stairs", () -> new StairBlock(CLOUD_MARBLE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(CLOUD_MARBLE_BRICKS.get())));
	public static final Supplier<SlabBlock> CLOUD_MARBLE_BRICK_SLAB = BLOCKS.register("cloud_marble_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<WallBlock> CLOUD_MARBLE_BRICK_WALL = BLOCKS.register("cloud_marble_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CLOUD_MARBLE_BRICKS.get())));
	public static final Supplier<FlagPoleBlock> FLAG_POLE = BLOCKS.register("flag_pole", () -> new FlagPoleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> AMERICAN_FLAG = BLOCKS.register("american_flag", () -> new FlagBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> GADSDEN_FLAG = BLOCKS.register("gadsden_flag", () -> new FlagBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> CANADIAN_FLAG = BLOCKS.register("canadian_flag", () -> new FlagBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> MEXICAN_FLAG = BLOCKS.register("mexican_flag", () -> new FlagBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> BRITISH_FLAG = BLOCKS.register("british_flag", () -> new FlagBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> TROLL_FLAG = BLOCKS.register("troll_flag", () -> new FlagBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<FlagBlock> IMMERSIVE_WEAPONS_FLAG = BLOCKS.register("immersive_weapons_flag", () -> new FlagBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<Block> BLOOD_SANDSTONE = BLOCKS.register("blood_sandstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<SlabBlock> BLOOD_SANDSTONE_SLAB = BLOCKS.register("blood_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BLOOD_SANDSTONE.get())));
	public static final Supplier<StairBlock> BLOOD_SANDSTONE_STAIRS = BLOCKS.register("blood_sandstone_stairs", () -> new StairBlock(BLOOD_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BLOOD_SANDSTONE.get())));
	public static final Supplier<WallBlock> BLOOD_SANDSTONE_WALL = BLOCKS.register("blood_sandstone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(BLOOD_SANDSTONE.get())));
	public static final Supplier<BasicOrientableBlock> CHISELED_BLOOD_SANDSTONE = BLOCKS.register("chiseled_blood_sandstone", () -> new BasicOrientableBlock(BlockBehaviour.Properties.ofFullCopy(BLOOD_SANDSTONE.get())));
	public static final Supplier<BasicOrientableBlock> CUT_BLOOD_SANDSTONE = BLOCKS.register("cut_blood_sandstone", () -> new BasicOrientableBlock(BlockBehaviour.Properties.ofFullCopy(BLOOD_SANDSTONE.get())));
	public static final Supplier<SlabBlock> CUT_BLOOD_SANDSTONE_SLAB = BLOCKS.register("cut_blood_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BLOOD_SANDSTONE.get())));
	public static final Supplier<Block> SMOOTH_BLOOD_SANDSTONE = BLOCKS.register("smooth_blood_sandstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(BLOOD_SANDSTONE.get())));
	public static final Supplier<SlabBlock> SMOOTH_BLOOD_SANDSTONE_SLAB = BLOCKS.register("smooth_blood_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BLOOD_SANDSTONE.get())));
	public static final Supplier<StairBlock> SMOOTH_BLOOD_SANDSTONE_STAIRS = BLOCKS.register("smooth_blood_sandstone_stairs", () -> new StairBlock(BLOOD_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BLOOD_SANDSTONE.get())));
	// Stone tier
	public static final Supplier<SpotlightBlock> SPOTLIGHT = BLOCKS.register("spotlight", () -> new SpotlightBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion().lightLevel((state) -> 0)));
	public static final Supplier<PanelBlock> IRON_PANEL = BLOCKS.register("iron_panel", () -> new PanelBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	@LanguageEntryOverride("Iron Panel (Bars)")
	public static final Supplier<PanelBlock> IRON_PANEL_BARS = BLOCKS.register("iron_panel_bars", () -> new PanelBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<DropExperienceBlock> SULFUR_ORE = BLOCKS.register("sulfur_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> DEEPSLATE_SULFUR_ORE = BLOCKS.register("deepslate_sulfur_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(4.5f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> NETHER_SULFUR_ORE = BLOCKS.register("nether_sulfur_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.4f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	@LanguageEntryOverride("Block of Raw Sulfur")
	public static final Supplier<Block> RAW_SULFUR_BLOCK = BLOCKS.register("raw_sulfur_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.4f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<Block> POTASSIUM_NITRATE_ORE = BLOCKS.register("potassium_nitrate_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<AstralCrystalBlock> ASTRAL_CRYSTAL = BLOCKS.register("astral_crystal", () -> new AstralCrystalBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).randomTicks().strength(0.4f).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().lightLevel((blockState) -> 6)));
	public static final Supplier<BiodomeLifeSupportUnitBlock> BIODOME_LIFE_SUPPORT_UNIT = BLOCKS.register("biodome_life_support_unit", () -> new BiodomeLifeSupportUnitBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(4.0f).sound(SoundType.METAL).noOcclusion().requiresCorrectToolForDrops()));
	public static final Supplier<Block> RUSTED_IRON_BLOCK = BLOCKS.register("rusted_iron_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0f, 5.0f).requiresCorrectToolForDrops()));
	public static final Supplier<Block> CHAMPION_BRICKS = BLOCKS.register("champion_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(6.0f, 99.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops()));
	@TextureMetadataMarker(frameTime = 8, interpolate = true)
	public static final Supplier<Block> CHAMPION_BASE = BLOCKS.register("champion_base", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(4.0f, 99.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops()));
	public static final Supplier<ChampionKeycardBlock> CHAMPION_KEYCARD_BRICKS = BLOCKS.register("champion_keycard_bricks", () -> new ChampionKeycardBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(6.0f, 99.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops()));
	public static final Supplier<CommanderPedestalBlock> COMMANDER_PEDESTAL = BLOCKS.register("commander_pedestal", () -> new CommanderPedestalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(2.5f).sound(SoundType.STONE).noOcclusion().requiresCorrectToolForDrops()));
	// Iron tier
	public static final Supplier<BarbedWireFenceBlock> BARBED_WIRE_FENCE = BLOCKS.register("barbed_wire_fence", () -> new BarbedWireFenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(7.0f, 8.0f).sound(SoundType.METAL).noOcclusion()));
	public static final Supplier<DropExperienceBlock> COBALT_ORE = BLOCKS.register("cobalt_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(4.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> DEEPSLATE_COBALT_ORE = BLOCKS.register("deepslate_cobalt_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(4.5f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
	@LanguageEntryOverride("Block of Cobalt")
	public static final Supplier<Block> COBALT_BLOCK = BLOCKS.register("cobalt_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(6.0f).requiresCorrectToolForDrops()));
	@LanguageEntryOverride("Block of Raw Cobalt")
	public static final Supplier<Block> RAW_COBALT_BLOCK = BLOCKS.register("raw_cobalt_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(4.0f).requiresCorrectToolForDrops()));
	public static final Supplier<BearTrapBlock> BEAR_TRAP = BLOCKS.register("bear_trap", () -> new BearTrapBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noOcclusion().strength(2.0f).sound(SoundType.METAL)));
	public static final Supplier<BarbedWireBlock> BARBED_WIRE = BLOCKS.register("barbed_wire", () -> new BarbedWireBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.0f).sound(SoundType.CHAIN).noOcclusion().noCollission()));
	public static final Supplier<SpikeTrapBlock> SPIKE_TRAP = BLOCKS.register("spike_trap", () -> new SpikeTrapBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.0f).sound(SoundType.METAL).noOcclusion().noCollission()));
	public static final Supplier<CelestialLanternBlock> CELESTIAL_LANTERN = BLOCKS.register("celestial_lantern", () -> new CelestialLanternBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5f).sound(SoundType.LANTERN).requiresCorrectToolForDrops().lightLevel((blockState) -> 15).noOcclusion()));
	public static final Supplier<StarForgeControllerBlock> STAR_FORGE_CONTROLLER = BLOCKS.register("star_forge_controller", () -> new StarForgeControllerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(5.0f, 10.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<Block> STAR_FORGE_BRICKS = BLOCKS.register("star_forge_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(5.0f, 10.0f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops()));
	public static final Supplier<TeleporterBlock> TELEPORTER = BLOCKS.register("teleporter", () -> new TeleporterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.5f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	// Diamond tier
	@TextureMetadataMarker(frameTime = 12, interpolate = true)
	public static final Supplier<DropExperienceBlock> MOLTEN_ORE = BLOCKS.register("molten_ore", () -> new DropExperienceBlock(UniformInt.of(5, 10), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(6.0f, 8.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> ELECTRIC_ORE = BLOCKS.register("electric_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(6.0f, 8.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<DropExperienceBlock> VENTUS_ORE = BLOCKS.register("ventus_ore", () -> new DropExperienceBlock(UniformInt.of(5, 10), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(3.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	@TextureMetadataMarker(frameTime = 12, interpolate = true)
	public static final Supplier<Block> MOLTEN_BLOCK = BLOCKS.register("molten_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(45.0f, 1100.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
	@TextureMetadataMarker(frameTime = 2, interpolate = true)
	public static final Supplier<Block> TESLA_BLOCK = BLOCKS.register("tesla_block", () -> new BasicOrientableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(35.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
	public static final Supplier<TeslaSynthesizerBlock> TESLA_SYNTHESIZER = BLOCKS.register("tesla_synthesizer", () -> new TeslaSynthesizerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(10.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<Block> TILTROS_PORTAL_FRAME = BLOCKS.register("tiltros_portal_frame", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(50.0f, 1200f).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion()));
	// Netherite tier
	public static final Supplier<DropExperienceBlock> ASTRAL_ORE = BLOCKS.register("astral_ore", () -> new DropExperienceBlock(UniformInt.of(7, 12), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(8.0f, 10.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<Block> ASTRAL_BLOCK = BLOCKS.register("astral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).strength(25.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final Supplier<AmethystClusterBlock> STARSTORM_CRYSTAL = BLOCKS.register("starstorm_crystal", () -> new StarstormCrystalBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).randomTicks().strength(0.4f).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().lightLevel((blockState) -> 6)));
	@TextureMetadataMarker(frameTime = 24, interpolate = true, frames = {0, 1, 2, 3, 2, 1})
	public static final Supplier<Block> STARSTORM_BLOCK = BLOCKS.register("starstorm_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(30.0f).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
	// Astral / Starstorm tier
	@TextureMetadataMarker(frameTime = 36, interpolate = true)
	public static final Supplier<Block> VOID_ORE = BLOCKS.register("void_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).strength(8.0f, 10.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

	// Breakable via axe
	// Wood tier
	public static final Supplier<SmallPartsTableBlock> SMALL_PARTS_TABLE = BLOCKS.register("small_parts_table", () -> new SmallPartsTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.5f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));
	public static final Supplier<AmmunitionTableBlock> AMMUNITION_TABLE = BLOCKS.register("ammunition_table", () -> new AmmunitionTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.5f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<RotatedPillarBlock> STRIPPED_BURNED_OAK_WOOD = BLOCKS.register("stripped_burned_oak_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> STRIPPED_BURNED_OAK_LOG = BLOCKS.register("stripped_burned_oak_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7F).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> BURNED_OAK_WOOD = BLOCKS.register("burned_oak_wood", () -> new StrippablePillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f).sound(SoundType.WOOD), STRIPPED_BURNED_OAK_WOOD.get().defaultBlockState()));
	public static final Supplier<RotatedPillarBlock> BURNED_OAK_LOG = BLOCKS.register("burned_oak_log", () -> new StrippablePillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f).sound(SoundType.WOOD), STRIPPED_BURNED_OAK_LOG.get().defaultBlockState()));
	public static final Supplier<Block> BURNED_OAK_PLANKS = BLOCKS.register("burned_oak_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD)));
	public static final Supplier<StairBlock> BURNED_OAK_STAIRS = BLOCKS.register("burned_oak_stairs", () -> new StairBlock(BURNED_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BURNED_OAK_PLANKS.get())));
	public static final Supplier<SlabBlock> BURNED_OAK_SLAB = BLOCKS.register("burned_oak_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD)));
	public static final Supplier<FenceBlock> BURNED_OAK_FENCE = BLOCKS.register("burned_oak_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD)));
	public static final Supplier<FenceGateBlock> BURNED_OAK_FENCE_GATE = BLOCKS.register("burned_oak_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
	public static final Supplier<DoorBlock> BURNED_OAK_DOOR = BLOCKS.register("burned_oak_door", () -> new DoorBlock(CustomBlockSetTypes.BURNED_OAK, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<TrapDoorBlock> BURNED_OAK_TRAPDOOR = BLOCKS.register("burned_oak_trapdoor", () -> new TrapDoorBlock(CustomBlockSetTypes.BURNED_OAK, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.7f, 2.7f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<PressurePlateBlock> BURNED_OAK_PRESSURE_PLATE = BLOCKS.register("burned_oak_pressure_plate", () -> new PressurePlateBlock(CustomBlockSetTypes.BURNED_OAK, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(0.4f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final Supplier<CustomStandingSignBlock> BURNED_OAK_SIGN = BLOCKS.register("burned_oak_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSignBlock> BURNED_OAK_WALL_SIGN = BLOCKS.register("burned_oak_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	public static final Supplier<CustomCeilingHangingSignBlock> BURNED_OAK_HANGING_SIGN = BLOCKS.register("burned_oak_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallHangingSignBlock> BURNED_OAK_WALL_HANGING_SIGN = BLOCKS.register("burned_oak_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	public static final Supplier<ButtonBlock> BURNED_OAK_BUTTON = BLOCKS.register("burned_oak_button", () -> new ButtonBlock(CustomBlockSetTypes.BURNED_OAK, 30, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.4f).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> STRIPPED_STARDUST_WOOD = BLOCKS.register("stripped_stardust_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> STRIPPED_STARDUST_LOG = BLOCKS.register("stripped_stardust_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD)));
	public static final Supplier<RotatedPillarBlock> STARDUST_LOG = BLOCKS.register("stardust_log", () -> new StrippablePillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD), STRIPPED_STARDUST_LOG.get().defaultBlockState()));
	public static final Supplier<RotatedPillarBlock> STARDUST_WOOD = BLOCKS.register("stardust_wood", () -> new StrippablePillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD), STRIPPED_STARDUST_WOOD.get().defaultBlockState()));
	public static final Supplier<Block> STARDUST_PLANKS = BLOCKS.register("stardust_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD)));
	public static final Supplier<StairBlock> STARDUST_STAIRS = BLOCKS.register("stardust_stairs", () -> new StairBlock(STARDUST_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(STARDUST_PLANKS.get())));
	public static final Supplier<SlabBlock> STARDUST_SLAB = BLOCKS.register("stardust_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD)));
	public static final Supplier<FenceBlock> STARDUST_FENCE = BLOCKS.register("stardust_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD)));
	public static final Supplier<FenceGateBlock> STARDUST_FENCE_GATE = BLOCKS.register("stardust_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
	public static final Supplier<DoorBlock> STARDUST_DOOR = BLOCKS.register("stardust_door", () -> new DoorBlock(CustomBlockSetTypes.STARDUST, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<TrapDoorBlock> STARDUST_TRAPDOOR = BLOCKS.register("stardust_trapdoor", () -> new TrapDoorBlock(CustomBlockSetTypes.STARDUST, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.3f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<PressurePlateBlock> STARDUST_PRESSURE_PLATE = BLOCKS.register("stardust_pressure_plate", () -> new PressurePlateBlock(CustomBlockSetTypes.STARDUST, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(0.7f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final Supplier<ButtonBlock> STARDUST_BUTTON = BLOCKS.register("stardust_button", () -> new ButtonBlock(CustomBlockSetTypes.STARDUST, 30, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.7f).sound(SoundType.WOOD)));
	public static final Supplier<CustomStandingSignBlock> STARDUST_SIGN = BLOCKS.register("stardust_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSignBlock> STARDUST_WALL_SIGN = BLOCKS.register("stardust_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	public static final Supplier<CustomCeilingHangingSignBlock> STARDUST_HANGING_SIGN = BLOCKS.register("stardust_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallHangingSignBlock> STARDUST_WALL_HANGING_SIGN = BLOCKS.register("stardust_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	public static final Supplier<WoodenTableBlock> OAK_TABLE = BLOCKS.register("oak_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> SPRUCE_TABLE = BLOCKS.register("spruce_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> BIRCH_TABLE = BLOCKS.register("birch_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> JUNGLE_TABLE = BLOCKS.register("jungle_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> ACACIA_TABLE = BLOCKS.register("acacia_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> DARK_OAK_TABLE = BLOCKS.register("dark_oak_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> CRIMSON_TABLE = BLOCKS.register("crimson_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> WARPED_TABLE = BLOCKS.register("warped_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> MANGROVE_TABLE = BLOCKS.register("mangrove_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> CHERRY_TABLE = BLOCKS.register("cherry_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> BAMBOO_TABLE = BLOCKS.register("bamboo_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.BAMBOO).noOcclusion()));
	public static final Supplier<WoodenTableBlock> BURNED_OAK_TABLE = BLOCKS.register("burned_oak_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final Supplier<WoodenTableBlock> STARDUST_TABLE = BLOCKS.register("stardust_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	// Stone tier
	public static final Supplier<WoodenSpikesBlock> WOODEN_SPIKES = BLOCKS.register("wooden_spikes", () -> new WoodenSpikesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));
	public static final Supplier<CelestialAltarBlock> CELESTIAL_ALTAR = BLOCKS.register("celestial_altar", () -> new CelestialAltarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(3.0f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));

	// Breakable via shovel
	// Wood tier
	public static final Supplier<SandbagBlock> SANDBAG = BLOCKS.register("sandbag", () -> new SandbagBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(4.0f, 5.0f).sound(SoundType.SAND).noOcclusion()));
	public static final Supplier<Block> MUD = BLOCKS.register("mud", () -> new IWMudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).mapColor(MapColor.DIRT).strength(0.8f, 0.3f).sound(SoundType.WET_GRASS).speedFactor(0.75f).randomTicks()));
	public static final Supplier<Block> DRIED_MUD = BLOCKS.register("dried_mud", () -> new DriedMudBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(1.0f, 0.7f).sound(SoundType.ROOTED_DIRT).randomTicks()));
	public static final Supplier<Block> HARDENED_MUD = BLOCKS.register("hardened_mud", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(2.0f, 1.0f).sound(SoundType.ROOTED_DIRT)));
	public static final Supplier<StairBlock> HARDENED_MUD_STAIRS = BLOCKS.register("hardened_mud_stairs", () -> new StairBlock(HARDENED_MUD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BURNED_OAK_PLANKS.get())));
	public static final Supplier<SlabBlock> HARDENED_MUD_SLAB = BLOCKS.register("hardened_mud_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(2.0f, 1.0f).sound(SoundType.ROOTED_DIRT)));
	public static final Supplier<Block> HARDENED_MUD_WINDOW = BLOCKS.register("hardened_mud_window", () -> new HardenedMudWindowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(2.0f, 1.0f).sound(SoundType.ROOTED_DIRT).noOcclusion()));
	public static final Supplier<CustomSandBlock> BLOOD_SAND = BLOCKS.register("blood_sand", () -> new CustomSandBlock(new ColorRGBA(13201254), BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sound(SoundType.SAND)));
	// Stone tier
	public static final Supplier<PunjiSticksBlock> PUNJI_STICKS = BLOCKS.register("punji_sticks", () -> new PunjiSticksBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(5.0f, 1.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));

	// Breakable via hoe
	// Wood tier
	public static final Supplier<StardustLeavesBlock> STARDUST_LEAVES = BLOCKS.register("stardust_leaves", () -> new StardustLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.3f).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((state, getter, pos, type) -> (type == EntityType.OCELOT || type == EntityType.PARROT)).isSuffocating((state, getter, pos) -> false).isViewBlocking((state, getter, pos) -> false)));

	// Breakable without tool
	public static final Supplier<TransparentBlock> BULLETPROOF_GLASS = BLOCKS.register("bulletproof_glass", () -> new TransparentBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> WHITE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("white_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.WHITE, BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> LIGHT_GRAY_STAINED_BULLETPROOF_GLASS = BLOCKS.register("light_gray_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.LIGHT_GRAY, BlockBehaviour.Properties.of().mapColor(DyeColor.LIGHT_GRAY).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> GRAY_STAINED_BULLETPROOF_GLASS = BLOCKS.register("gray_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.GRAY, BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> BLACK_STAINED_BULLETPROOF_GLASS = BLOCKS.register("black_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.BLACK, BlockBehaviour.Properties.of().mapColor(DyeColor.BLACK).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> ORANGE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("orange_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.ORANGE, BlockBehaviour.Properties.of().mapColor(DyeColor.ORANGE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> MAGENTA_STAINED_BULLETPROOF_GLASS = BLOCKS.register("magenta_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.MAGENTA, BlockBehaviour.Properties.of().mapColor(DyeColor.MAGENTA).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> LIGHT_BLUE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("light_blue_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.LIGHT_BLUE, BlockBehaviour.Properties.of().mapColor(DyeColor.LIGHT_BLUE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> YELLOW_STAINED_BULLETPROOF_GLASS = BLOCKS.register("yellow_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.YELLOW, BlockBehaviour.Properties.of().mapColor(DyeColor.YELLOW).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> LIME_STAINED_BULLETPROOF_GLASS = BLOCKS.register("lime_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.LIME, BlockBehaviour.Properties.of().mapColor(DyeColor.LIME).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> PINK_STAINED_BULLETPROOF_GLASS = BLOCKS.register("pink_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.PINK, BlockBehaviour.Properties.of().mapColor(DyeColor.PINK).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> CYAN_STAINED_BULLETPROOF_GLASS = BLOCKS.register("cyan_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.CYAN, BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> PURPLE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("purple_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.PURPLE, BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> BLUE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("blue_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.BLUE, BlockBehaviour.Properties.of().mapColor(DyeColor.BLUE).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> BROWN_STAINED_BULLETPROOF_GLASS = BLOCKS.register("brown_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> GREEN_STAINED_BULLETPROOF_GLASS = BLOCKS.register("green_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.GREEN, BlockBehaviour.Properties.of().mapColor(DyeColor.GREEN).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<StainedGlassBlock> RED_STAINED_BULLETPROOF_GLASS = BLOCKS.register("red_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.RED, BlockBehaviour.Properties.of().mapColor(DyeColor.RED).instrument(NoteBlockInstrument.HAT).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final Supplier<PitfallBlock> PITFALL = BLOCKS.register("pitfall", () -> new PitfallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.2f, 1.0f).sound(SoundType.GRAVEL).randomTicks()));
	public static final Supplier<LandmineBlock> LANDMINE = BLOCKS.register("landmine", () -> new LandmineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(1.0F).sound(SoundType.METAL)));
	public static final Supplier<MinutemanStatueBlock> MINUTEMAN_STATUE = BLOCKS.register("minuteman_statue", () -> new MinutemanStatueBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0f).sound(SoundType.STONE).noOcclusion()));
	public static final Supplier<MedicStatueBlock> MEDIC_STATUE = BLOCKS.register("medic_statue", () -> new MedicStatueBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(5.0f).sound(SoundType.STONE).noOcclusion()));
	public static final Supplier<ShelfBlock> WALL_SHELF = BLOCKS.register("wall_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.0f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final Supplier<PanicAlarmBlock> PANIC_ALARM = BLOCKS.register("panic_alarm", () -> new PanicAlarmBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(1.0f).sound(SoundType.WOOD).noOcclusion().randomTicks()));
	public static final Supplier<BiohazardBoxBlock> BIOHAZARD_BOX = BLOCKS.register("biohazard_box", () -> new BiohazardBoxBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).pushReaction(PushReaction.DESTROY).strength(0.5f).sound(SoundType.LANTERN).noOcclusion()));
	public static final Supplier<HalfTransparentBlock> CLOUD = BLOCKS.register("cloud", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().replaceable().strength(0.7f).sound(SoundType.SNOW).noOcclusion()));
	public static final Supplier<CampChairBlock> CAMP_CHAIR = BLOCKS.register("camp_chair", () -> new CampChairBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).ignitedByLava().strength(1.0f).sound(SoundType.WOOL).noOcclusion()));
	public static final Supplier<BranchBlock> BURNED_OAK_BRANCH = BLOCKS.register("burned_oak_branch", () -> new BranchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(0.1f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final Supplier<SkullBlock> MINUTEMAN_HEAD = BLOCKS.register("minuteman_head", () -> new CustomSkullBlock(CustomSkullTypes.MINUTEMAN, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> MINUTEMAN_WALL_HEAD = BLOCKS.register("minuteman_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.MINUTEMAN, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f).lootFrom(MINUTEMAN_HEAD)));
	public static final Supplier<SkullBlock> FIELD_MEDIC_HEAD = BLOCKS.register("field_medic_head", () -> new CustomSkullBlock(CustomSkullTypes.FIELD_MEDIC, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> FIELD_MEDIC_WALL_HEAD = BLOCKS.register("field_medic_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.FIELD_MEDIC, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f).lootFrom(FIELD_MEDIC_HEAD)));
	public static final Supplier<SkullBlock> DYING_SOLDIER_HEAD = BLOCKS.register("dying_soldier_head", () -> new CustomSkullBlock(CustomSkullTypes.DYING_SOLDIER, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> DYING_SOLDIER_WALL_HEAD = BLOCKS.register("dying_soldier_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.DYING_SOLDIER, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f).lootFrom(DYING_SOLDIER_HEAD)));
	public static final Supplier<SkullBlock> THE_COMMANDER_HEAD = BLOCKS.register("the_commander_head", () -> new CustomSkullBlock(CustomSkullTypes.THE_COMMANDER, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> THE_COMMANDER_WALL_HEAD = BLOCKS.register("the_commander_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.THE_COMMANDER, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f).lootFrom(THE_COMMANDER_HEAD)));
	public static final Supplier<SkullBlock> WANDERING_WARRIOR_HEAD = BLOCKS.register("wandering_warrior_head", () -> new CustomSkullBlock(CustomSkullTypes.WANDERING_WARRIOR, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> WANDERING_WARRIOR_WALL_HEAD = BLOCKS.register("wandering_warrior_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.WANDERING_WARRIOR, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f).lootFrom(WANDERING_WARRIOR_HEAD)));
	public static final Supplier<SkullBlock> HANS_HEAD = BLOCKS.register("hans_head", () -> new CustomSkullBlock(CustomSkullTypes.HANS, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> HANS_WALL_HEAD = BLOCKS.register("hans_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.HANS, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f).lootFrom(HANS_HEAD)));
	public static final Supplier<SkullBlock> STORM_CREEPER_HEAD = BLOCKS.register("storm_creeper_head", () -> new CustomSkullBlock(CustomSkullTypes.STORM_CREEPER, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> STORM_CREEPER_WALL_HEAD = BLOCKS.register("storm_creeper_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.STORM_CREEPER, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f).lootFrom(STORM_CREEPER_HEAD)));
	public static final Supplier<SkullBlock> SKELETON_MERCHANT_HEAD = BLOCKS.register("skeleton_merchant_head", () -> new CustomSkullBlock(CustomSkullTypes.SKELETON_MERCHANT, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f)));
	@DatagenExclusionMarker(Type.LANGUAGE_GENERATOR)
	public static final Supplier<CustomWallSkullBlock> SKELETON_MERCHANT_WALL_HEAD = BLOCKS.register("skeleton_merchant_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.SKELETON_MERCHANT, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).strength(1.0f).lootFrom(SKELETON_MERCHANT_HEAD)));
	public static final Supplier<MoonglowBlock> MOONGLOW = BLOCKS.register("moonglow", () -> new MoonglowBlock(MobEffects.GLOWING, 10, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).lightLevel((state) -> 12).noCollission().instabreak().offsetType(OffsetType.XZ)));
	public static final Supplier<FlowerPotBlock> POTTED_MOONGLOW = BLOCKS.register("potted_moonglow", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, MOONGLOW, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).lightLevel((state) -> 12).instabreak().noOcclusion()));
	public static final Supplier<DeathweedBlock> DEATHWEED = BLOCKS.register("deathweed", () -> new DeathweedBlock(MobEffects.HARM, 0, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).lightLevel((state) -> 4).noCollission().instabreak().offsetType(OffsetType.XZ)));
	public static final Supplier<FlowerPotBlock> POTTED_DEATHWEED = BLOCKS.register("potted_deathweed", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DEATHWEED, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).lightLevel((state) -> 12).instabreak().noOcclusion()));
	public static final Supplier<SaplingBlock> STARDUST_SAPLING = BLOCKS.register("stardust_sapling", () -> new SaplingBlock(new TreeGrower("stardust_sapling", Optional.empty(), Optional.of(STARDUST_TREE_CONFIGURATION), Optional.empty()), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).strength(0.0f).sound(SoundType.GRASS).noCollission().instabreak().offsetType(OffsetType.NONE)));
	public static final Supplier<SolarLensBlock> SOLAR_LENS = BLOCKS.register("solar_lens", () -> new SolarLensBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).pushReaction(PushReaction.DESTROY).strength(0.5f).sound(SoundType.GLASS).noOcclusion()));
	@TextureMetadataMarker
	public static final Supplier<TiltrosPortalBlock> TILTROS_PORTAL = BLOCKS.register("tiltros_portal", () -> new TiltrosPortalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).pushReaction(PushReaction.BLOCK).strength(-1.0f, 3600000f).noCollission().lightLevel(state -> 15).noLootTable()));
}