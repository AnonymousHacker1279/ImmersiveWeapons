package tech.anonymoushacker1279.immersiveweapons.init;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.TradeSet;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class VillagerRegistry {

	// Registers
	public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, ImmersiveWeapons.MOD_ID);
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(Registries.VILLAGER_PROFESSION, ImmersiveWeapons.MOD_ID);

	// Trade sets
	public static final ResourceKey<TradeSet> GUNSMITH_LEVEL_1 = createTradeSet("gunsmith/level_1");
	public static final ResourceKey<TradeSet> GUNSMITH_LEVEL_2 = createTradeSet("gunsmith/level_2");
	public static final ResourceKey<TradeSet> GUNSMITH_LEVEL_3 = createTradeSet("gunsmith/level_3");
	public static final ResourceKey<TradeSet> GUNSMITH_LEVEL_4 = createTradeSet("gunsmith/level_4");

	// Points of interest
	// TODO: placeholder job site, swap for the Gunsmith's dedicated workstation block
	public static final DeferredHolder<PoiType, PoiType> GUNSMITH_POI = POI_TYPES.register("gunsmith", () ->
			new PoiType(ImmutableSet.copyOf(BlockRegistry.SMALL_PARTS_TABLE.get().getStateDefinition().getPossibleStates()), 1, 1));

	// Professions
	public static final DeferredHolder<VillagerProfession, VillagerProfession> GUNSMITH = PROFESSIONS.register("gunsmith", () ->
			new VillagerProfession(
					Component.translatable("entity." + ImmersiveWeapons.MOD_ID + ".villager.gunsmith"),
					poi -> poi.is(GUNSMITH_POI.key()),
					poi -> poi.is(GUNSMITH_POI.key()),
					ImmutableSet.of(),
					ImmutableSet.of(),
					SoundEvents.VILLAGER_WORK_WEAPONSMITH,
					Int2ObjectMap.ofEntries(
							Int2ObjectMap.entry(1, GUNSMITH_LEVEL_1),
							Int2ObjectMap.entry(2, GUNSMITH_LEVEL_2),
							Int2ObjectMap.entry(3, GUNSMITH_LEVEL_3),
							Int2ObjectMap.entry(4, GUNSMITH_LEVEL_4))));

	private static ResourceKey<TradeSet> createTradeSet(String name) {
		return ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}
}
