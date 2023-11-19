package tech.anonymoushacker1279.immersiveweapons.init;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.*;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class LootModifierRegistry {

	// Global Loot Modifier Register
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZER = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ImmersiveWeapons.MOD_ID);

	// Loot Table Modifiers
	public static final Supplier<Codec<LogShardsLootModifierHandler>> WOOD_LOGS_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("log_shards", LogShardsLootModifierHandler.CODEC);
	public static final Supplier<Codec<SimpleChestModifierHandler>> SIMPLE_CHEST_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("simple_chest", SimpleChestModifierHandler.CODEC);
	public static final Supplier<Codec<MoltenToolSmeltingModifierHandler>> MOLTEN_TOOL_SMELTING_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("molten_tool_smelting", MoltenToolSmeltingModifierHandler.CODEC);
	public static final Supplier<Codec<UndeadMobDropModifierHandler>> UNDEAD_MOB_DROP_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("undead_drop", UndeadMobDropModifierHandler.CODEC);
	public static final Supplier<Codec<SimpleDropModifierHandler>> SIMPLE_DROP_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZER.register("simple_drop", SimpleDropModifierHandler.CODEC);
}