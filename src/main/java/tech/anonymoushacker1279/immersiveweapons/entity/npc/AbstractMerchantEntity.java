package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trades.ItemsForEmeralds;

import javax.annotation.Nullable;

public abstract class AbstractMerchantEntity extends AbstractVillager implements GrantAdvancementOnDiscovery {

	private int timeUntilRefreshTrades = 24000;

	public AbstractMerchantEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
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
		return new Int2ObjectOpenHashMap<>(ImmutableMap.of(
				1, new VillagerTrades.ItemListing[]{
						new ItemsForEmeralds(Items.AIR, 1, 1, 1)
				},
				2, new VillagerTrades.ItemListing[]{
						new ItemsForEmeralds(Items.AIR, 1, 1, 1)
				},
				3, new VillagerTrades.ItemListing[]{
						new ItemsForEmeralds(Items.AIR, 1, 1, 1)
				}
		));
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
			timeUntilRefreshTrades = 24000;
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

		VillagerTrades.ItemListing[] commonItemListings = getTrades().get(1);
		VillagerTrades.ItemListing[] rareItemListings = getTrades().get(2);
		VillagerTrades.ItemListing[] epicItemListings = getTrades().get(3);

		if (commonItemListings != null && rareItemListings != null && epicItemListings != null) {
			MerchantOffers offers = getOffers();
			addOffersFromItemListings(offers, commonItemListings, 3);

			int i = random.nextInt(rareItemListings.length);
			VillagerTrades.ItemListing rareItemListing = rareItemListings[i];
			MerchantOffer rareOffer = rareItemListing.getOffer(this, random);
			if (rareOffer != null) {
				offers.add(rareOffer);
			}

			int i2 = random.nextInt(epicItemListings.length);
			VillagerTrades.ItemListing epicItemListing = epicItemListings[i2];
			MerchantOffer epicOffer = epicItemListing.getOffer(this, random);
			if (epicOffer != null) {
				offers.add(epicOffer);
			}
		}
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return null;
	}
}