package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeGroup;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeLoader;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades.ItemsForEmeralds;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractMerchantEntity extends AbstractVillager implements GrantAdvancementOnDiscovery {

	private int timeUntilRefreshTrades;

	public AbstractMerchantEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);

		timeUntilRefreshTrades = TradeLoader.TRADES.get(getType()).tradeRefreshTime();
	}

	/**
	 * Get the time until trades refresh. Used by IWCB for a plugin, hence the
	 * unused warning suppression.
	 */
	@SuppressWarnings("unused")
	public int getTradeRefreshTime() {
		return timeUntilRefreshTrades;
	}

	public Int2ObjectMap<ItemListing[]> getTrades() {
		return TradeLoader.getTrades(getType());
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
		goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
		goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35D));
		goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35D));
		goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	@Override
	public boolean showProgressBar() {
		return false;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	public void tick() {
		super.tick();

		// Lower the trade refresh cooldown
		if (timeUntilRefreshTrades > 0) {
			timeUntilRefreshTrades--;
		} else {
			updateTrades();
			timeUntilRefreshTrades = TradeLoader.TRADES.get(getType()).tradeRefreshTime();
		}
	}

	@Override
	protected void rewardTradeXp(MerchantOffer offer) {
		if (offer.shouldRewardExp()) {
			int xp = 8 + random.nextInt(6);
			level().addFreshEntity(new ExperienceOrb(level(), getX(), getY() + 0.5D, getZ(), xp));
		}
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public boolean isPersistenceRequired() {
		return true;
	}

	@Override
	protected void updateTrades() {
		// Clear existing trades
		getOffers().clear();

		List<TradeGroup> tradeGroups = TradeLoader.TRADES.get(getType()).tradeGroups();
		for (TradeGroup group : tradeGroups) {
			int entries = group.entries();
			ItemListing[] trades = group.trades().stream()
					.map(trade -> (ItemListing) new ItemsForEmeralds(
							trade.itemStack(),
							trade.emeraldCost(),
							trade.maxUses(),
							trade.xpReward(),
							trade.priceMultiplier()))
					.toArray(ItemListing[]::new);

			addOffersFromItemListings(getOffers(), trades, entries);
		}
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return null;
	}
}