package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.SimpleItemListing;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeGroup;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeLoader;

import java.util.List;

public abstract class AbstractMerchantEntity extends AbstractVillager implements GrantAdvancementOnDiscovery {

	private static final EntityDataAccessor<Integer> TRADE_REFRESH_TIME = SynchedEntityData.defineId(AbstractMerchantEntity.class,
			EntityDataSerializers.INT);

	public AbstractMerchantEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
	}

	/**
	 * Get the time until trades refresh. Used by IWCB for a plugin, hence the unused warning suppression.
	 */
	@SuppressWarnings("unused")
	public int getTradeRefreshTime() {
		return entityData.get(TRADE_REFRESH_TIME);
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
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(TRADE_REFRESH_TIME, 24000);
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
		if (entityData.get(TRADE_REFRESH_TIME) > 0) {
			entityData.set(TRADE_REFRESH_TIME, entityData.get(TRADE_REFRESH_TIME) - 1);
		} else {
			if (!level().isClientSide) {
				updateTrades();
				entityData.set(TRADE_REFRESH_TIME, TradeLoader.TRADES.get(getType()).tradeRefreshTime());
			}
		}
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnGroupData) {
		entityData.set(TRADE_REFRESH_TIME, TradeLoader.TRADES.get(getType()).tradeRefreshTime());
		return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
	}

	@Override
	protected void rewardTradeXp(MerchantOffer offer) {
		if (offer.shouldRewardExp()) {
			int xp = 8 + random.nextInt(6);
			level().addFreshEntity(new ExperienceOrb(level(), getX(), getY() + 0.5D, getZ(), xp));
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		if (player.isSecondaryUseActive()) {
			return InteractionResult.PASS;
		}

		ItemStack itemInHand = player.getItemInHand(hand);
		if (!itemInHand.is(getSpawnEgg()) && isAlive() && !isTrading() && !isBaby()) {
			if (hand == InteractionHand.MAIN_HAND) {
				player.awardStat(Stats.TALKED_TO_VILLAGER);
			}

			if (!level().isClientSide) {
				if (!getOffers().isEmpty()) {
					setTradingPlayer(player);
					openTradingScreen(player, getDisplayName(), 1);
				}
			}
			return InteractionResult.SUCCESS;
		} else {
			return super.mobInteract(player, hand);
		}
	}

	abstract protected Item getSpawnEgg();

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
					.map(trade -> (ItemListing) new SimpleItemListing(
							trade.item1(),
							trade.item2(),
							trade.result(),
							trade.maxUses(),
							trade.xpReward(),
							trade.priceMultiplier()
					))
					.toArray(ItemListing[]::new);

			addOffersFromItemListings(getOffers(), trades, entries);
		}
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return null;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		// Before saving, remove any IdentifiableMerchantOffers

		super.addAdditionalSaveData(compound);

		compound.putInt("tradeRefreshTime", entityData.get(TRADE_REFRESH_TIME));
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);

		entityData.set(TRADE_REFRESH_TIME, compound.getInt("tradeRefreshTime"));
	}
}