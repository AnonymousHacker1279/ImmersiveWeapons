package tech.anonymoushacker1279.immersiveweapons.init;

import com.google.common.collect.Sets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.skull.CustomWallSkullBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.*;

@SuppressWarnings({"unused"})
public class BlockEntityRegistry {

	// Block Entity Register
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ImmersiveWeapons.MOD_ID);

	// Block Entities
	public static final RegistryObject<BlockEntityType<BearTrapBlockEntity>> BEAR_TRAP_BLOCK_ENTITY = BLOCK_ENTITIES.register("bear_trap", () -> new BlockEntityType<>(BearTrapBlockEntity::new, Sets.newHashSet(BlockRegistry.BEAR_TRAP.get()), null));
	public static final RegistryObject<BlockEntityType<ShelfBlockEntity>> SHELF_BLOCK_ENTITY = BLOCK_ENTITIES.register("wall_shelf", () -> new BlockEntityType<>(ShelfBlockEntity::new, Sets.newHashSet(BlockRegistry.WALL_SHELF.get()), null));
	public static final RegistryObject<BlockEntityType<PanicAlarmBlockEntity>> PANIC_ALARM_BLOCK_ENTITY = BLOCK_ENTITIES.register("panic_alarm", () -> new BlockEntityType<>(PanicAlarmBlockEntity::new, Sets.newHashSet(BlockRegistry.PANIC_ALARM.get()), null));
	public static final RegistryObject<BlockEntityType<MinutemanStatueBlockEntity>> MINUTEMAN_STATUE_BLOCK_ENTITY = BLOCK_ENTITIES.register("minuteman_statue", () -> new BlockEntityType<>(MinutemanStatueBlockEntity::new, Sets.newHashSet(BlockRegistry.MINUTEMAN_STATUE.get()), null));
	public static final RegistryObject<BlockEntityType<MedicStatueBlockEntity>> MEDIC_STATUE_BLOCK_ENTITY = BLOCK_ENTITIES.register("medic_statue", () -> new BlockEntityType<>(MedicStatueBlockEntity::new, Sets.newHashSet(BlockRegistry.MEDIC_STATUE.get()), null));
	public static final RegistryObject<BlockEntityType<TeslaSynthesizerBlockEntity>> TESLA_SYNTHESIZER_BLOCK_ENTITY = BLOCK_ENTITIES.register("tesla_synthesizer", () -> new BlockEntityType<>(TeslaSynthesizerBlockEntity::new, Sets.newHashSet(BlockRegistry.TESLA_SYNTHESIZER.get()), null));
	public static final RegistryObject<BlockEntityType<BurnedOakSignEntity>> BURNED_OAK_SIGN_ENTITY = BLOCK_ENTITIES.register("burned_oak_sign", () -> BlockEntityType.Builder.of(BurnedOakSignEntity::new, BlockRegistry.BURNED_OAK_SIGN.get(), BlockRegistry.BURNED_OAK_WALL_SIGN.get()).build(null));
	public static final RegistryObject<BlockEntityType<CelestialLanternBlockEntity>> CELESTIAL_LANTERN_BLOCK_ENTITY = BLOCK_ENTITIES.register("celestial_lantern", () -> new BlockEntityType<>(CelestialLanternBlockEntity::new, Sets.newHashSet(BlockRegistry.CELESTIAL_LANTERN.get()), null));
	public static final RegistryObject<BlockEntityType<CustomSkullBlockEntity>> CUSTOM_SKULL_BLOCK_ENTITY = BLOCK_ENTITIES.register("custom_skull", () -> BlockEntityType.Builder.of(CustomSkullBlockEntity::new, getSkullBlocks()).build(null));
	public static final RegistryObject<BlockEntityType<StardustSignEntity>> STARDUST_SIGN_ENTITY = BLOCK_ENTITIES.register("stardust_sign", () -> BlockEntityType.Builder.of(StardustSignEntity::new, BlockRegistry.STARDUST_SIGN.get(), BlockRegistry.STARDUST_WALL_SIGN.get()).build(null));
	public static final RegistryObject<BlockEntityType<AstralCrystalBlockEntity>> ASTRAL_CRYSTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("astral_crystal", () -> new BlockEntityType<>(AstralCrystalBlockEntity::new, Sets.newHashSet(BlockRegistry.ASTRAL_CRYSTAL.get()), null));
	public static final RegistryObject<BlockEntityType<AzulStainedOrchidBlockEntity>> AZUL_STAINED_ORCHID_BLOCK_ENTITY = BLOCK_ENTITIES.register("azul_stained_orchid", () -> new BlockEntityType<>(AzulStainedOrchidBlockEntity::new, Sets.newHashSet(BlockRegistry.AZUL_STAINED_ORCHID.get()), null));
	public static final RegistryObject<BlockEntityType<BiodomeLifeSupportUnitBlockEntity>> BIODOME_LIFE_SUPPORT_UNIT_BLOCK_ENTITY = BLOCK_ENTITIES.register("biodome_life_support_unit", () -> new BlockEntityType<>(BiodomeLifeSupportUnitBlockEntity::new, Sets.newHashSet(BlockRegistry.BIODOME_LIFE_SUPPORT_UNIT.get()), null));
	public static final RegistryObject<BlockEntityType<DamageableBlockEntity>> DAMAGEABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("damageable_block", () -> new BlockEntityType<>(DamageableBlockEntity::new, Sets.newHashSet(BlockRegistry.WOODEN_SPIKES.get(), BlockRegistry.BARBED_WIRE.get()), null));

	private static Block[] getSkullBlocks() {
		return BlockRegistry.BLOCKS.getEntries().stream()
				.map(RegistryObject::get)
				.filter(block -> block instanceof SkullBlock || block instanceof CustomWallSkullBlock)
				.toArray(Block[]::new);
	}
}