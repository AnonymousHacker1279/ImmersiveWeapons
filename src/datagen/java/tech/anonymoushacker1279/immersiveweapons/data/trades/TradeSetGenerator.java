package tech.anonymoushacker1279.immersiveweapons.data.trades;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.TradeSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class TradeSetGenerator {

	public static final ResourceKey<TradeSet> SKYGAZER_LEVEL_1 = create("skygazer/level_1");
	public static final ResourceKey<TradeSet> SKYGAZER_LEVEL_2 = create("skygazer/level_2");
	public static final ResourceKey<TradeSet> SKYGAZER_LEVEL_3 = create("skygazer/level_3");

	public static final ResourceKey<TradeSet> SKELETON_MERCHANT_LEVEL_1 = create("skeleton_merchant/level_1");
	public static final ResourceKey<TradeSet> SKELETON_MERCHANT_LEVEL_2 = create("skeleton_merchant/level_2");

	private static ResourceKey<TradeSet> create(String name) {
		return ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstrapContext<TradeSet> context) {
		TradeSets.register(context, SKYGAZER_LEVEL_1, TradeTags.SKYGAZER_LEVEL_1, ConstantValue.exactly(3.0f));
		TradeSets.register(context, SKYGAZER_LEVEL_2, TradeTags.SKYGAZER_LEVEL_2, UniformGenerator.between(1.0f, 2.0f));
		TradeSets.register(context, SKYGAZER_LEVEL_3, TradeTags.SKYGAZER_LEVEL_3);

		TradeSets.register(context, SKELETON_MERCHANT_LEVEL_1, TradeTags.SKELETON_MERCHANT_LEVEL_1, ConstantValue.exactly(3.0f));
		TradeSets.register(context, SKELETON_MERCHANT_LEVEL_2, TradeTags.SKELETON_MERCHANT_LEVEL_2, UniformGenerator.between(1.0f, 2.0f));
	}
}