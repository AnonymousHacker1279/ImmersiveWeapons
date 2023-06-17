package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trades.ItemsForEmeralds;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import javax.annotation.Nullable;

public class SkeletonMerchantEntity extends AbstractVillager implements GrantAdvancementOnDiscovery {

	private int timeUntilRefreshTrades = 24000; // One day

	public static final Int2ObjectMap<ItemListing[]> TRADES = new Int2ObjectOpenHashMap<>(ImmutableMap.of(
			1, new VillagerTrades.ItemListing[]{
					new ItemsForEmeralds(ItemRegistry.BANDAGE.get(), 1, 1, 8),
					new ItemsForEmeralds(ItemRegistry.IRON_RING.get(), 3, 1, 6),
					new ItemsForEmeralds(ItemRegistry.IRON_MUSKET_BALL.get(), 2, 8, 12),
					new ItemsForEmeralds(ItemRegistry.IRON_ARROW.get(), 1, 8, 12),
					new ItemsForEmeralds(ItemRegistry.SATCHEL.get(), 16, 1, 3),
					new ItemsForEmeralds(ItemRegistry.POWDER_HORN.get(), 16, 1, 3),
					new ItemsForEmeralds(ItemRegistry.BLOODY_SACRIFICE.get(), 5, 1, 1)},
			2, new VillagerTrades.ItemListing[]{
					new ItemsForEmeralds(ItemRegistry.BLOATED_HEART.get(), 6, 1, 3),
					new ItemsForEmeralds(ItemRegistry.COBALT_RING.get(), 5, 1, 3),
					new ItemsForEmeralds(ItemRegistry.COBALT_PIKE.get(), 10, 1, 3),
					new ItemsForEmeralds(ItemRegistry.MOLOTOV_COCKTAIL.get(), 6, 2, 4),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_GREEN.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_RED.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_BLUE.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_PURPLE.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_YELLOW.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_ARROW.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_ARROW_GREEN.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_ARROW_RED.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_ARROW_BLUE.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW.get(), 3, 2, 6),},
			3, new VillagerTrades.ItemListing[]{
					new ItemsForEmeralds(ItemRegistry.DEADEYE_PENDANT.get(), 32, 1, 1),
					new ItemsForEmeralds(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get(), 32, 1, 1),
					new ItemsForEmeralds(ItemRegistry.NETHERITE_SHIELD.get(), 24, 1, 1),
					new ItemsForEmeralds(ItemRegistry.BERSERKERS_AMULET.get(), 28, 1, 1),
					new ItemsForEmeralds(ItemRegistry.AMETHYST_RING.get(), 28, 1, 1),
					new ItemsForEmeralds(ItemRegistry.EMERALD_RING.get(), 28, 1, 1),
			}));

	public SkeletonMerchantEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
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
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		if (!itemInHand.is(ItemRegistry.SKELETON_MERCHANT_SPAWN_EGG.get()) && isAlive() && !isTrading() && !isBaby()) {
			if (hand == InteractionHand.MAIN_HAND) {
				player.awardStat(Stats.TALKED_TO_VILLAGER);
			}

			if (!getOffers().isEmpty()) {
				if (!level().isClientSide) {
					setTradingPlayer(player);
					openTradingScreen(player, getDisplayName(), 1);
				}
			}
			return InteractionResult.sidedSuccess(level().isClientSide);
		} else {
			return super.mobInteract(player, hand);
		}
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
		VillagerTrades.ItemListing[] commonItemListings = TRADES.get(1);
		VillagerTrades.ItemListing[] rareItemListings = TRADES.get(2);
		VillagerTrades.ItemListing[] epicItemListings = TRADES.get(3);

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