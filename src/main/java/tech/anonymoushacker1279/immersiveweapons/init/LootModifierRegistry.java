package tech.anonymoushacker1279.immersiveweapons.init;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.*;

@SuppressWarnings({"unused"})
public class LootModifierRegistry {

	// Global Loot Modifier Register
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ImmersiveWeapons.MOD_ID);

	// Loot Table Modifiers
	public static final RegistryObject<Codec<LogShardsLootModifierHandler>> WOOD_LOGS_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("log_shards", LogShardsLootModifierHandler.CODEC);
	public static final RegistryObject<Codec<AzulKeystoneFragmentInChestsLootModifierHandler>> AZUL_KEYSTONE_FRAGMENT_IN_CHESTS_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("azul_keystone_fragment_in_chests", AzulKeystoneFragmentInChestsLootModifierHandler.CODEC);
	public static final RegistryObject<Codec<MoltenToolSmeltingModifierHandler>> MOLTEN_TOOL_SMELTING_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("molten_tool_smelting", MoltenToolSmeltingModifierHandler.CODEC);
	public static final RegistryObject<Codec<CopperRingDropLootModifierHandler>> COPPER_RING_DROP_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("copper_ring", CopperRingDropLootModifierHandler.CODEC);
	public static final RegistryObject<Codec<MOADropModifierHandler>> MOA_DROP_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("medal_of_adequacy", MOADropModifierHandler.CODEC);
}