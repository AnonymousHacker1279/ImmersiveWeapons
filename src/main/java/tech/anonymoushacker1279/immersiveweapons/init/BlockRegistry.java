package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.*;
import tech.anonymoushacker1279.immersiveweapons.block.barbed_wire.BarbedWireBlock;
import tech.anonymoushacker1279.immersiveweapons.block.barbed_wire.BarbedWireFenceBlock;
import tech.anonymoushacker1279.immersiveweapons.block.core.*;
import tech.anonymoushacker1279.immersiveweapons.block.corrugated.CorrugatedBlock;
import tech.anonymoushacker1279.immersiveweapons.block.corrugated.CorrugatedBlockFlat;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.BarrelTapBlock;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.TeslaSynthesizerBlock;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.small_parts.SmallPartsTable;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.*;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomSkullBlocks.CustomSkullBlock;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomSkullBlocks.CustomWallSkullBlock;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomSkullTypes;
import tech.anonymoushacker1279.immersiveweapons.block.grower.StardustTreeGrower;
import tech.anonymoushacker1279.immersiveweapons.block.misc.MedicStatueBlock;
import tech.anonymoushacker1279.immersiveweapons.block.misc.MinutemanStatueBlock;
import tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.*;
import tech.anonymoushacker1279.immersiveweapons.block.mud.*;
import tech.anonymoushacker1279.immersiveweapons.block.properties.WoodTypes;
import tech.anonymoushacker1279.immersiveweapons.block.sign.*;
import tech.anonymoushacker1279.immersiveweapons.util.BlockPredicates;

@SuppressWarnings({"unused"})
public class BlockRegistry {

	// Block Register
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ImmersiveWeapons.MOD_ID);

	// Blocks
	// Breakable via pickaxe
	// Wooden tier
	public static final RegistryObject<BarrelTapBlock> BARREL_TAP = BLOCKS.register("barrel_tap", () -> new BarrelTapBlock(BlockBehaviour.Properties.of(Material.METAL).strength(1.0f).sound(SoundType.METAL)));
	public static final RegistryObject<MortarBlock> MORTAR = BLOCKS.register("mortar", () -> new MortarBlock(BlockBehaviour.Properties.of(Material.STONE).strength(5.0f).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion()));
	public static final RegistryObject<Block> CLOUD_MARBLE = BLOCKS.register("cloud_marble", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> CLOUD_MARBLE_BRICKS = BLOCKS.register("cloud_marble_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<RotatedPillarBlock> CLOUD_MARBLE_PILLAR = BLOCKS.register("cloud_marble_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<StairBlock> CLOUD_MARBLE_BRICK_STAIRS = BLOCKS.register("cloud_marble_brick_stairs", () -> new StairBlock(() -> CLOUD_MARBLE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CLOUD_MARBLE_BRICKS.get())));
	public static final RegistryObject<SlabBlock> CLOUD_MARBLE_BRICK_SLAB = BLOCKS.register("cloud_marble_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<WallBlock> CLOUD_MARBLE_BRICK_WALL = BLOCKS.register("cloud_marble_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(CLOUD_MARBLE_BRICKS.get())));
	public static final RegistryObject<FlagPoleBlock> FLAG_POLE = BLOCKS.register("flag_pole", () -> new FlagPoleBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<FlagBlock> AMERICAN_FLAG = BLOCKS.register("american_flag", () -> new FlagBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<FlagBlock> GADSDEN_FLAG = BLOCKS.register("gadsden_flag", () -> new FlagBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<FlagBlock> CANADIAN_FLAG = BLOCKS.register("canadian_flag", () -> new FlagBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<FlagBlock> MEXICAN_FLAG = BLOCKS.register("mexican_flag", () -> new FlagBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<FlagBlock> BRITISH_FLAG = BLOCKS.register("british_flag", () -> new FlagBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<FlagBlock> TROLL_FLAG = BLOCKS.register("troll_flag", () -> new FlagBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<FlagBlock> IMMERSIVE_WEAPONS_FLAG = BLOCKS.register("immersive_weapons_flag", () -> new FlagBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5f).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block> BLOOD_SANDSTONE = BLOCKS.register("blood_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).strength(2.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<SlabBlock> BLOOD_SANDSTONE_SLAB = BLOCKS.register("blood_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BLOOD_SANDSTONE.get())));
	public static final RegistryObject<StairBlock> BLOOD_SANDSTONE_STAIRS = BLOCKS.register("blood_sandstone_stairs", () -> new StairBlock(() -> BLOOD_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(BLOOD_SANDSTONE.get())));
	public static final RegistryObject<WallBlock> BLOOD_SANDSTONE_WALL = BLOCKS.register("blood_sandstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(BLOOD_SANDSTONE.get())));
	public static final RegistryObject<BasicOrientableBlock> CHISELED_BLOOD_SANDSTONE = BLOCKS.register("chiseled_blood_sandstone", () -> new BasicOrientableBlock(BlockBehaviour.Properties.copy(BLOOD_SANDSTONE.get())));
	public static final RegistryObject<BasicOrientableBlock> CUT_BLOOD_SANDSTONE = BLOCKS.register("cut_blood_sandstone", () -> new BasicOrientableBlock(BlockBehaviour.Properties.copy(BLOOD_SANDSTONE.get())));
	public static final RegistryObject<SlabBlock> CUT_BLOOD_SANDSTONE_SLAB = BLOCKS.register("cut_blood_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BLOOD_SANDSTONE.get())));
	public static final RegistryObject<Block> SMOOTH_BLOOD_SANDSTONE = BLOCKS.register("smooth_blood_sandstone", () -> new Block(BlockBehaviour.Properties.copy(BLOOD_SANDSTONE.get())));
	public static final RegistryObject<SlabBlock> SMOOTH_BLOOD_SANDSTONE_SLAB = BLOCKS.register("smooth_blood_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BLOOD_SANDSTONE.get())));
	public static final RegistryObject<StairBlock> SMOOTH_BLOOD_SANDSTONE_STAIRS = BLOCKS.register("smooth_blood_sandstone_stairs", () -> new StairBlock(() -> BLOOD_SANDSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(BLOOD_SANDSTONE.get())));
	// Stone tier
	public static final RegistryObject<SpotlightBlock> SPOTLIGHT = BLOCKS.register("spotlight", () -> new SpotlightBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion().lightLevel((state) -> 0)));
	public static final RegistryObject<CorrugatedBlock> CORRUGATED_IRON_PANEL = BLOCKS.register("corrugated_iron_panel", () -> new CorrugatedBlock(BlockBehaviour.Properties.of(Material.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	public static final RegistryObject<CorrugatedBlock> CORRUGATED_IRON_PANEL_BARS = BLOCKS.register("corrugated_iron_panel_bars", () -> new CorrugatedBlock(BlockBehaviour.Properties.of(Material.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	public static final RegistryObject<CorrugatedBlockFlat> CORRUGATED_IRON_PANEL_FLAT = BLOCKS.register("corrugated_iron_panel_flat", () -> new CorrugatedBlockFlat(BlockBehaviour.Properties.of(Material.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	public static final RegistryObject<CorrugatedBlockFlat> CORRUGATED_IRON_PANEL_FLAT_BARS = BLOCKS.register("corrugated_iron_panel_flat_bars", () -> new CorrugatedBlockFlat(BlockBehaviour.Properties.of(Material.METAL).strength(5.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	public static final RegistryObject<WarriorStatueBase> WARRIOR_STATUE_BASE = BLOCKS.register("warrior_statue_base", () -> new WarriorStatueBase(BlockBehaviour.Properties.of(Material.STONE).strength(4.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<WarriorStatueTorso> WARRIOR_STATUE_TORSO = BLOCKS.register("warrior_statue_torso", () -> new WarriorStatueTorso(BlockBehaviour.Properties.of(Material.STONE).strength(4.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<WarriorStatueHead> WARRIOR_STATUE_HEAD = BLOCKS.register("warrior_statue_head", () -> new WarriorStatueHead(BlockBehaviour.Properties.of(Material.STONE).strength(4.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<DropExperienceBlock> SULFUR_ORE = BLOCKS.register("sulfur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<DropExperienceBlock> DEEPSLATE_SULFUR_ORE = BLOCKS.register("deepslate_sulfur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4.5f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
	public static final RegistryObject<DropExperienceBlock> NETHER_SULFUR_ORE = BLOCKS.register("nether_sulfur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(0.4f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> RAW_SULFUR_BLOCK = BLOCKS.register("raw_sulfur_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.4f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<AstralCrystalBlock> ASTRAL_CRYSTAL = BLOCKS.register("astral_crystal", () -> new AstralCrystalBlock(7, 3, BlockBehaviour.Properties.of(Material.STONE).randomTicks().strength(0.4f).sound(SoundType.STONE).requiresCorrectToolForDrops().lightLevel((blockState) -> 6)));
	public static final RegistryObject<BiodomeLifeSupportUnitBlock> BIODOME_LIFE_SUPPORT_UNIT = BLOCKS.register("biodome_life_support_unit", () -> new BiodomeLifeSupportUnitBlock(BlockBehaviour.Properties.of(Material.METAL).strength(4.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
	// Iron tier
	public static final RegistryObject<BarbedWireFenceBlock> BARBED_WIRE_FENCE = BLOCKS.register("barbed_wire_fence", () -> new BarbedWireFenceBlock(BlockBehaviour.Properties.of(Material.METAL).strength(7.0f, 8.0f).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<DropExperienceBlock> COBALT_ORE = BLOCKS.register("cobalt_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<DropExperienceBlock> DEEPSLATE_COBALT_ORE = BLOCKS.register("deepslate_cobalt_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4.5f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> COBALT_BLOCK = BLOCKS.register("cobalt_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(6.0f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> RAW_COBALT_BLOCK = BLOCKS.register("raw_cobalt_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(4.0f).requiresCorrectToolForDrops()));
	public static final RegistryObject<BearTrapBlock> BEAR_TRAP = BLOCKS.register("bear_trap", () -> new BearTrapBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion().strength(2.0f).sound(SoundType.METAL)));
	public static final RegistryObject<BarbedWireBlock> BARBED_WIRE = BLOCKS.register("barbed_wire", () -> new BarbedWireBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.0f).sound(SoundType.CHAIN).noOcclusion().noCollission()));
	public static final RegistryObject<SpikeTrapBlock> SPIKE_TRAP = BLOCKS.register("spike_trap", () -> new SpikeTrapBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.0f).sound(SoundType.METAL).noOcclusion().noCollission()));
	public static final RegistryObject<CelestialLanternBlock> CELESTIAL_LANTERN = BLOCKS.register("celestial_lantern", () -> new CelestialLanternBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3.5f).sound(SoundType.LANTERN).requiresCorrectToolForDrops().lightLevel((blockState) -> 15).noOcclusion()));
	// Diamond tier
	public static final RegistryObject<DropExperienceBlock> MOLTEN_ORE = BLOCKS.register("molten_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6.0f, 8.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<DropExperienceBlock> ELECTRIC_ORE = BLOCKS.register("electric_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6.0f, 8.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<DropExperienceBlock> VENTUS_ORE = BLOCKS.register("ventus_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3.5f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> MOLTEN_BLOCK = BLOCKS.register("molten_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(45.0f, 1100.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> TESLA_BLOCK = BLOCKS.register("tesla_block", () -> new BasicOrientableBlock(BlockBehaviour.Properties.of(Material.METAL).strength(35.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
	public static final RegistryObject<TeslaSynthesizerBlock> TESLA_SYNTHESIZER = BLOCKS.register("tesla_synthesizer", () -> new TeslaSynthesizerBlock(BlockBehaviour.Properties.of(Material.METAL).strength(10.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
	// Netherite tier
	public static final RegistryObject<DropExperienceBlock> ASTRAL_ORE = BLOCKS.register("astral_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(8.0f, 10.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> ASTRAL_BLOCK = BLOCKS.register("astral_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(25.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<AmethystClusterBlock> STARSTORM_CRYSTAL = BLOCKS.register("starstorm_crystal", () -> new StarstormCrystalBlock(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST).randomTicks().strength(0.4f).sound(SoundType.AMETHYST_CLUSTER).requiresCorrectToolForDrops().lightLevel((blockState) -> 6)));
	public static final RegistryObject<Block> STARSTORM_BLOCK = BLOCKS.register("starstorm_block", () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST).strength(30.0f).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));

	// Breakable via axe
	// Wood tier
	public static final RegistryObject<SmallPartsTable> SMALL_PARTS_TABLE = BLOCKS.register("small_parts_table", () -> new SmallPartsTable(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_BURNED_OAK_WOOD = BLOCKS.register("stripped_burned_oak_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7f).sound(SoundType.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_BURNED_OAK_LOG = BLOCKS.register("stripped_burned_oak_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7F).sound(SoundType.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> BURNED_OAK_WOOD = BLOCKS.register("burned_oak_wood", () -> new StrippablePillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7f).sound(SoundType.WOOD), STRIPPED_BURNED_OAK_WOOD.get().defaultBlockState()));
	public static final RegistryObject<RotatedPillarBlock> BURNED_OAK_LOG = BLOCKS.register("burned_oak_log", () -> new StrippablePillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7f).sound(SoundType.WOOD), STRIPPED_BURNED_OAK_LOG.get().defaultBlockState()));
	public static final RegistryObject<Block> BURNED_OAK_PLANKS = BLOCKS.register("burned_oak_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7f, 2.7f).sound(SoundType.WOOD)));
	public static final RegistryObject<StairBlock> BURNED_OAK_STAIRS = BLOCKS.register("burned_oak_stairs", () -> new StairBlock(() -> BURNED_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(BURNED_OAK_PLANKS.get())));
	public static final RegistryObject<SlabBlock> BURNED_OAK_SLAB = BLOCKS.register("burned_oak_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7f, 2.7f).sound(SoundType.WOOD)));
	public static final RegistryObject<FenceBlock> BURNED_OAK_FENCE = BLOCKS.register("burned_oak_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7f, 2.7f).sound(SoundType.WOOD)));
	public static final RegistryObject<FenceGateBlock> BURNED_OAK_FENCE_GATE = BLOCKS.register("burned_oak_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7f, 2.7f).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
	public static final RegistryObject<DoorBlock> BURNED_OAK_DOOR = BLOCKS.register("burned_oak_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7f, 2.7f).sound(SoundType.WOOD), SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN));
	public static final RegistryObject<TrapDoorBlock> BURNED_OAK_TRAPDOOR = BLOCKS.register("burned_oak_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.7f, 2.7f).sound(SoundType.WOOD).noOcclusion(), SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN));
	public static final RegistryObject<PressurePlateBlock> BURNED_OAK_PRESSURE_PLATE = BLOCKS.register("burned_oak_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD).strength(0.4f).sound(SoundType.WOOD).noOcclusion().noCollission(), SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON));
	public static final RegistryObject<BurnedOakStandingSignBlock> BURNED_OAK_SIGN = BLOCKS.register("burned_oak_sign", () -> new BurnedOakStandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	public static final RegistryObject<BurnedOakWallSignBlock> BURNED_OAK_WALL_SIGN = BLOCKS.register("burned_oak_wall_sign", () -> new BurnedOakWallSignBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodTypes.BURNED_OAK));
	public static final RegistryObject<ButtonBlock> BURNED_OAK_BUTTON = BLOCKS.register("burned_oak_button", () -> new ButtonBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(0.4f).sound(SoundType.WOOD), 30, true, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_STARDUST_WOOD = BLOCKS.register("stripped_stardust_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_STARDUST_LOG = BLOCKS.register("stripped_stardust_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> STARDUST_LOG = BLOCKS.register("stardust_log", () -> new StrippablePillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD), STRIPPED_STARDUST_LOG.get().defaultBlockState()));
	public static final RegistryObject<RotatedPillarBlock> STARDUST_WOOD = BLOCKS.register("stardust_wood", () -> new StrippablePillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD), STRIPPED_STARDUST_WOOD.get().defaultBlockState()));
	public static final RegistryObject<Block> STARDUST_PLANKS = BLOCKS.register("stardust_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD)));
	public static final RegistryObject<StairBlock> STARDUST_STAIRS = BLOCKS.register("stardust_stairs", () -> new StairBlock(() -> STARDUST_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(STARDUST_PLANKS.get())));
	public static final RegistryObject<SlabBlock> STARDUST_SLAB = BLOCKS.register("stardust_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD)));
	public static final RegistryObject<FenceBlock> STARDUST_FENCE = BLOCKS.register("stardust_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD)));
	public static final RegistryObject<FenceGateBlock> STARDUST_FENCE_GATE = BLOCKS.register("stardust_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
	public static final RegistryObject<DoorBlock> STARDUST_DOOR = BLOCKS.register("stardust_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD), SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN));
	public static final RegistryObject<TrapDoorBlock> STARDUST_TRAPDOOR = BLOCKS.register("stardust_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.3f).sound(SoundType.WOOD).noOcclusion(), SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN));
	public static final RegistryObject<PressurePlateBlock> STARDUST_PRESSURE_PLATE = BLOCKS.register("stardust_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD).strength(0.7f).sound(SoundType.WOOD).noOcclusion().noCollission(), SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON));
	public static final RegistryObject<ButtonBlock> STARDUST_BUTTON = BLOCKS.register("stardust_button", () -> new ButtonBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(0.7f).sound(SoundType.WOOD), 30, true, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON));
	public static final RegistryObject<StardustStandingSignBlock> STARDUST_SIGN = BLOCKS.register("stardust_sign", () -> new StardustStandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	public static final RegistryObject<StardustWallSignBlock> STARDUST_WALL_SIGN = BLOCKS.register("stardust_wall_sign", () -> new StardustWallSignBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.7F).sound(SoundType.WOOD), WoodTypes.STARDUST));
	// Stone tier
	public static final RegistryObject<WoodenSpikesBlock> WOODEN_SPIKES = BLOCKS.register("wooden_spikes", () -> new WoodenSpikesBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));

	// Breakable via shovel
	// Wood tier
	public static final RegistryObject<SandbagBlock> SANDBAG = BLOCKS.register("sandbag", () -> new SandbagBlock(BlockBehaviour.Properties.of(Material.SAND).strength(4.0f, 5.0f).sound(SoundType.SAND).noOcclusion()));
	public static final RegistryObject<Block> MUD = BLOCKS.register("mud", () -> new IWMudBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(0.8f, 0.3f).sound(SoundType.WET_GRASS).speedFactor(0.75f).randomTicks()));
	public static final RegistryObject<Block> DRIED_MUD = BLOCKS.register("dried_mud", () -> new DriedMudBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(1.0f, 0.7f).sound(SoundType.ROOTED_DIRT).randomTicks()));
	public static final RegistryObject<Block> HARDENED_MUD = BLOCKS.register("hardened_mud", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(2.0f, 1.0f).sound(SoundType.ROOTED_DIRT)));
	public static final RegistryObject<StairBlock> HARDENED_MUD_STAIRS = BLOCKS.register("hardened_mud_stairs", () -> new StairBlock(() -> HARDENED_MUD.get().defaultBlockState(), BlockBehaviour.Properties.copy(BURNED_OAK_PLANKS.get())));
	public static final RegistryObject<SlabBlock> HARDENED_MUD_SLAB = BLOCKS.register("hardened_mud_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(2.0f, 1.0f).sound(SoundType.ROOTED_DIRT)));
	public static final RegistryObject<Block> HARDENED_MUD_WINDOW = BLOCKS.register("hardened_mud_window", () -> new HardenedMudWindowBlock(BlockBehaviour.Properties.of(Material.DIRT).strength(2.0f, 1.0f).sound(SoundType.ROOTED_DIRT).noOcclusion()));
	public static final RegistryObject<CustomSandBlock> BLOOD_SAND = BLOCKS.register("blood_sand", () -> new CustomSandBlock(13201254, BlockBehaviour.Properties.of(Material.SAND).strength(0.5f).sound(SoundType.SAND)));
	// Stone tier
	public static final RegistryObject<PunjiSticksBlock> PUNJI_STICKS = BLOCKS.register("punji_sticks", () -> new PunjiSticksBlock(BlockBehaviour.Properties.of(Material.BAMBOO).strength(5.0f, 1.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));

	// Breakable via hoe
	// Wood tier
	public static final RegistryObject<StardustLeavesBlock> STARDUST_LEAVES = BLOCKS.register("stardust_leaves", () -> new StardustLeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.3f).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((state, getter, pos, type) -> BlockPredicates.ocelotOrParrot(type)).isSuffocating((state, getter, pos) -> BlockPredicates.never()).isViewBlocking((state, getter, pos) -> BlockPredicates.never())));

	// Breakable without tool
	public static final RegistryObject<GlassBlock> BULLETPROOF_GLASS = BLOCKS.register("bulletproof_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> WHITE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("white_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.WHITE, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.WHITE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> LIGHT_GRAY_STAINED_BULLETPROOF_GLASS = BLOCKS.register("light_gray_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.LIGHT_GRAY, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.LIGHT_GRAY).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> GRAY_STAINED_BULLETPROOF_GLASS = BLOCKS.register("gray_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.GRAY, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.GRAY).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> BLACK_STAINED_BULLETPROOF_GLASS = BLOCKS.register("black_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.BLACK, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.BLACK).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> ORANGE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("orange_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.ORANGE, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.ORANGE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> MAGENTA_STAINED_BULLETPROOF_GLASS = BLOCKS.register("magenta_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.MAGENTA, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.MAGENTA).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> LIGHT_BLUE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("light_blue_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.LIGHT_BLUE, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.LIGHT_BLUE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> YELLOW_STAINED_BULLETPROOF_GLASS = BLOCKS.register("yellow_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.YELLOW, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.YELLOW).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> LIME_STAINED_BULLETPROOF_GLASS = BLOCKS.register("lime_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.LIME, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.LIME).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> PINK_STAINED_BULLETPROOF_GLASS = BLOCKS.register("pink_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.PINK, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.PINK).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> CYAN_STAINED_BULLETPROOF_GLASS = BLOCKS.register("cyan_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.CYAN, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.CYAN).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> PURPLE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("purple_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.PURPLE, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.PURPLE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> BLUE_STAINED_BULLETPROOF_GLASS = BLOCKS.register("blue_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.BLUE, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.BLUE).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> BROWN_STAINED_BULLETPROOF_GLASS = BLOCKS.register("brown_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.BROWN, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.BROWN).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> GREEN_STAINED_BULLETPROOF_GLASS = BLOCKS.register("green_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.GREEN, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.GREEN).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<StainedGlassBlock> RED_STAINED_BULLETPROOF_GLASS = BLOCKS.register("red_stained_bulletproof_glass", () -> new StainedGlassBlock(DyeColor.RED, BlockBehaviour.Properties.of(Material.GLASS, DyeColor.RED).sound(SoundType.GLASS).noOcclusion().strength(0.5f)));
	public static final RegistryObject<PitfallBlock> PITFALL = BLOCKS.register("pitfall", () -> new PitfallBlock(BlockBehaviour.Properties.of(Material.GRASS).strength(0.2f, 1.0f).sound(SoundType.GRAVEL).randomTicks()));
	public static final RegistryObject<LandmineBlock> LANDMINE = BLOCKS.register("landmine", () -> new LandmineBlock(BlockBehaviour.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL)));
	public static final RegistryObject<MinutemanStatueBlock> MINUTEMAN_STATUE = BLOCKS.register("minuteman_statue", () -> new MinutemanStatueBlock(BlockBehaviour.Properties.of(Material.STONE).strength(5.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<MedicStatueBlock> MEDIC_STATUE = BLOCKS.register("medic_statue", () -> new MedicStatueBlock(BlockBehaviour.Properties.of(Material.STONE).strength(5.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<ShelfBlock> WALL_SHELF = BLOCKS.register("wall_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.0f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final RegistryObject<PanicAlarmBlock> PANIC_ALARM = BLOCKS.register("panic_alarm", () -> new PanicAlarmBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.0f).sound(SoundType.WOOD).noOcclusion().randomTicks()));
	public static final RegistryObject<WoodenTableBlock> OAK_TABLE = BLOCKS.register("oak_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<WoodenTableBlock> SPRUCE_TABLE = BLOCKS.register("spruce_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<WoodenTableBlock> BIRCH_TABLE = BLOCKS.register("birch_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<WoodenTableBlock> JUNGLE_TABLE = BLOCKS.register("jungle_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<WoodenTableBlock> ACACIA_TABLE = BLOCKS.register("acacia_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<WoodenTableBlock> DARK_OAK_TABLE = BLOCKS.register("dark_oak_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<WoodenTableBlock> CRIMSON_TABLE = BLOCKS.register("crimson_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<WoodenTableBlock> WARPED_TABLE = BLOCKS.register("warped_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<WoodenTableBlock> BURNED_OAK_TABLE = BLOCKS.register("burned_oak_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<WoodenTableBlock> STARDUST_TABLE = BLOCKS.register("stardust_table", () -> new WoodenTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<BiohazardBoxBlock> BIOHAZARD_BOX = BLOCKS.register("biohazard_box", () -> new BiohazardBoxBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.5f).sound(SoundType.LANTERN).noOcclusion()));
	public static final RegistryObject<HalfTransparentBlock> CLOUD = BLOCKS.register("cloud", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of(Material.STRUCTURAL_AIR).strength(0.7f).sound(SoundType.SNOW).noOcclusion()));
	public static final RegistryObject<CampChairBlock> CAMP_CHAIR = BLOCKS.register("camp_chair", () -> new CampChairBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION).strength(1.0f).sound(SoundType.WOOL).noOcclusion()));
	public static final RegistryObject<BranchBlock> BURNED_OAK_BRANCH = BLOCKS.register("burned_oak_branch", () -> new BranchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.1f).sound(SoundType.WOOD).noOcclusion().noCollission()));
	public static final RegistryObject<AzulStainedOrchidBlock> AZUL_STAINED_ORCHID = BLOCKS.register("azul_stained_orchid", () -> new AzulStainedOrchidBlock(() -> MobEffects.LUCK, 30, BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.GRASS).noCollission().instabreak().offsetType(OffsetType.NONE)));
	public static final RegistryObject<SkullBlock> MINUTEMAN_HEAD = BLOCKS.register("minuteman_head", () -> new CustomSkullBlock(CustomSkullTypes.MINUTEMAN, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f)));
	public static final RegistryObject<CustomWallSkullBlock> MINUTEMAN_WALL_HEAD = BLOCKS.register("minuteman_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.MINUTEMAN, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f).lootFrom(MINUTEMAN_HEAD)));
	public static final RegistryObject<SkullBlock> FIELD_MEDIC_HEAD = BLOCKS.register("field_medic_head", () -> new CustomSkullBlock(CustomSkullTypes.FIELD_MEDIC, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f)));
	public static final RegistryObject<CustomWallSkullBlock> FIELD_MEDIC_WALL_HEAD = BLOCKS.register("field_medic_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.FIELD_MEDIC, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f).lootFrom(FIELD_MEDIC_HEAD)));
	public static final RegistryObject<SkullBlock> DYING_SOLDIER_HEAD = BLOCKS.register("dying_soldier_head", () -> new CustomSkullBlock(CustomSkullTypes.DYING_SOLDIER, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f)));
	public static final RegistryObject<CustomWallSkullBlock> DYING_SOLDIER_WALL_HEAD = BLOCKS.register("dying_soldier_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.DYING_SOLDIER, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f).lootFrom(DYING_SOLDIER_HEAD)));
	public static final RegistryObject<SkullBlock> WANDERING_WARRIOR_HEAD = BLOCKS.register("wandering_warrior_head", () -> new CustomSkullBlock(CustomSkullTypes.WANDERING_WARRIOR, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f)));
	public static final RegistryObject<CustomWallSkullBlock> WANDERING_WARRIOR_WALL_HEAD = BLOCKS.register("wandering_warrior_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.WANDERING_WARRIOR, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f).lootFrom(WANDERING_WARRIOR_HEAD)));
	public static final RegistryObject<SkullBlock> HANS_HEAD = BLOCKS.register("hans_head", () -> new CustomSkullBlock(CustomSkullTypes.HANS, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f)));
	public static final RegistryObject<CustomWallSkullBlock> HANS_WALL_HEAD = BLOCKS.register("hans_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.HANS, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f).lootFrom(HANS_HEAD)));
	public static final RegistryObject<SkullBlock> STORM_CREEPER_HEAD = BLOCKS.register("storm_creeper_head", () -> new CustomSkullBlock(CustomSkullTypes.STORM_CREEPER, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f)));
	public static final RegistryObject<CustomWallSkullBlock> STORM_CREEPER_WALL_HEAD = BLOCKS.register("storm_creeper_wall_head", () -> new CustomWallSkullBlock(CustomSkullTypes.STORM_CREEPER, BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0f).lootFrom(STORM_CREEPER_HEAD)));
	public static final RegistryObject<MoonglowBlock> MOONGLOW = BLOCKS.register("moonglow", () -> new MoonglowBlock(() -> MobEffects.GLOWING, 10, BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.GRASS).lightLevel((state) -> 12).noCollission().instabreak().offsetType(OffsetType.XZ)));
	public static final RegistryObject<FlowerPotBlock> POTTED_MOONGLOW = BLOCKS.register("potted_moonglow", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, MOONGLOW, BlockBehaviour.Properties.of(Material.DECORATION).lightLevel((state) -> 12).instabreak().noOcclusion()));
	public static final RegistryObject<DeathweedBlock> DEATHWEED = BLOCKS.register("deathweed", () -> new DeathweedBlock(() -> MobEffects.HARM, 0, BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.GRASS).lightLevel((state) -> 4).noCollission().instabreak().offsetType(OffsetType.XZ)));
	public static final RegistryObject<FlowerPotBlock> POTTED_DEATHWEED = BLOCKS.register("potted_deathweed", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DEATHWEED, BlockBehaviour.Properties.of(Material.DECORATION).lightLevel((state) -> 12).instabreak().noOcclusion()));
	public static final RegistryObject<SaplingBlock> STARDUST_SAPLING = BLOCKS.register("stardust_sapling", () -> new SaplingBlock(new StardustTreeGrower(), BlockBehaviour.Properties.of(Material.PLANT).strength(0.0f).sound(SoundType.GRASS).noCollission().instabreak().offsetType(OffsetType.NONE)));
}