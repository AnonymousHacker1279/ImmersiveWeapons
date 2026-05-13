package tech.anonymoushacker1279.immersiveweapons.data.trades;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.VillagerTrade;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class TradeTags {

	public static final TagKey<VillagerTrade> SKYGAZER_LEVEL_1 = create("skygazer/level_1");
	public static final TagKey<VillagerTrade> SKYGAZER_LEVEL_2 = create("skygazer/level_2");
	public static final TagKey<VillagerTrade> SKYGAZER_LEVEL_3 = create("skygazer/level_3");
	public static final TagKey<VillagerTrade> SKELETON_MERCHANT_LEVEL_1 = create("skeleton_merchant/level_1");
	public static final TagKey<VillagerTrade> SKELETON_MERCHANT_LEVEL_2 = create("skeleton_merchant/level_2");

	private static TagKey<VillagerTrade> create(String name) {
		return TagKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}
}