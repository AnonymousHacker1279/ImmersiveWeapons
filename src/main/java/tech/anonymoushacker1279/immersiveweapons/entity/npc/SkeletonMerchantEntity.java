package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class SkeletonMerchantEntity extends AbstractMerchantEntity {

	public static final ResourceKey<TradeSet> TRADE_SET_1 = ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "skeleton_merchant/level_1"));
	public static final ResourceKey<TradeSet> TRADE_SET_2 = ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "skeleton_merchant/level_2"));

	public SkeletonMerchantEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected Item getSpawnEgg() {
		return ItemRegistry.SKELETON_MERCHANT_SPAWN_EGG.get();
	}

	@Override
	protected SoundEvent getTradeUpdatedSound(boolean isYesSound) {
		return SoundEvents.SKELETON_AMBIENT;
	}

	@Override
	protected void updateTrades(ServerLevel level) {
		addOffersFromTradeSet(level, getOffers(), TRADE_SET_1);
		addOffersFromTradeSet(level, getOffers(), TRADE_SET_2);
	}
}