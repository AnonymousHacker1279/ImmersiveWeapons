package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.world.item.trading.VillagerTrade;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.trades.TradeGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.trades.TradeTags;

import java.util.concurrent.CompletableFuture;

public class TradeTagsGenerator extends KeyTagProvider<VillagerTrade> {

	public TradeTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.VILLAGER_TRADE, lookupProvider, ImmersiveWeapons.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		tag(TradeTags.SKYGAZER_LEVEL_1)
				.add(TradeGenerator.SKYGAZER_1_EMERALD_BANDAGE)
				.add(TradeGenerator.SKYGAZER_1_EMERALD_FIRST_AID_KIT)
				.add(TradeGenerator.SKYGAZER_1_EMERALD_PAINKILLERS)
				.add(TradeGenerator.SKYGAZER_1_EMERALD_DIAMOND_MUSKET_BALL)
				.add(TradeGenerator.SKYGAZER_1_EMERALD_DIAMOND_ARROW)
				.add(TradeGenerator.SKYGAZER_1_EMERALD_AZUL_LOCATOR)
				.add(TradeGenerator.SKYGAZER_1_EMERALD_SPYGLASS);

		tag(TradeTags.SKYGAZER_LEVEL_2)
				.add(TradeGenerator.SKYGAZER_2_EMERALD_STARSTORM_CRYSTAL)
				.add(TradeGenerator.SKYGAZER_2_EMERALD_ASTRAL_CRYSTAL)
				.add(TradeGenerator.SKYGAZER_2_EMERALD_METEOR_STAFF)
				.add(TradeGenerator.SKYGAZER_2_EMERALD_CURSED_SIGHT_STAFF)
				.add(TradeGenerator.SKYGAZER_2_EMERALD_NIGHT_VISION_GOGGLES);

		tag(TradeTags.SKYGAZER_LEVEL_3)
				.add(TradeGenerator.SKYGAZER_3_LECTERN_CELESTIAL_ALTAR);

		tag(TradeTags.SKELETON_MERCHANT_LEVEL_1)
				.add(TradeGenerator.SKELETON_MERCHANT_1_EMERALD_IRON_RING)
				.add(TradeGenerator.SKELETON_MERCHANT_1_EMERALD_SATCHEL)
				.add(TradeGenerator.SKELETON_MERCHANT_1_EMERALD_POWDER_HORN)
				.add(TradeGenerator.SKELETON_MERCHANT_1_EMERALD_AGILITY_BRACELET)
				.add(TradeGenerator.SKELETON_MERCHANT_1_EMERALD_BLOODY_SACRIFICE);

		tag(TradeTags.SKELETON_MERCHANT_LEVEL_2)
				.add(TradeGenerator.SKELETON_MERCHANT_2_EMERALD_BLOATED_HEART)
				.add(TradeGenerator.SKELETON_MERCHANT_2_EMERALD_DEADEYE_PENDANT)
				.add(TradeGenerator.SKELETON_MERCHANT_2_EMERALD_MELEE_MASTERS_MOLTEN_GLOVE)
				.add(TradeGenerator.SKELETON_MERCHANT_2_EMERALD_NETHERITE_SHIELD)
				.add(TradeGenerator.SKELETON_MERCHANT_2_EMERALD_BERSERKERS_AMULET)
				.add(TradeGenerator.SKELETON_MERCHANT_2_EMERALD_AMETHYST_RING)
				.add(TradeGenerator.SKELETON_MERCHANT_2_EMERALD_EMERALD_RING)
				.add(TradeGenerator.SKELETON_MERCHANT_2_EMERALD_TOTEM_OF_UNDYING);
	}
}