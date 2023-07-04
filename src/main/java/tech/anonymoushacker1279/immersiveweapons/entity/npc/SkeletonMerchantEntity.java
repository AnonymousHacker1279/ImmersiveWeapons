package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trades.ItemsForEmeralds;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class SkeletonMerchantEntity extends AbstractMerchantEntity {

	public static final Int2ObjectMap<ItemListing[]> TRADES = new Int2ObjectOpenHashMap<>(ImmutableMap.of(
			1, new VillagerTrades.ItemListing[]{
					new ItemsForEmeralds(ItemRegistry.BANDAGE.get(), 1, 1, 8),
					new ItemsForEmeralds(ItemRegistry.PAINKILLERS.get(), 2, 1, 8),
					new ItemsForEmeralds(ItemRegistry.IRON_RING.get(), 3, 1, 6),
					new ItemsForEmeralds(ItemRegistry.IRON_MUSKET_BALL.get(), 2, 8, 12),
					new ItemsForEmeralds(ItemRegistry.IRON_ARROW.get(), 1, 8, 12),
					new ItemsForEmeralds(ItemRegistry.SATCHEL.get(), 16, 1, 3),
					new ItemsForEmeralds(ItemRegistry.POWDER_HORN.get(), 16, 1, 3),
					new ItemsForEmeralds(ItemRegistry.BLOODY_SACRIFICE.get(), 5, 1, 1),
					new ItemsForEmeralds(ItemRegistry.MORTAR_SHELL.get(), 2, 3, 6)},
			2, new VillagerTrades.ItemListing[]{
					new ItemsForEmeralds(ItemRegistry.BLOATED_HEART.get(), 6, 1, 3),
					new ItemsForEmeralds(ItemRegistry.COBALT_RING.get(), 5, 1, 3),
					new ItemsForEmeralds(ItemRegistry.COBALT_PIKE.get(), 10, 1, 3),
					new ItemsForEmeralds(ItemRegistry.MOLOTOV_COCKTAIL.get(), 6, 2, 4),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE.get(), 3, 2, 6),
					new ItemsForEmeralds(ItemRegistry.SMOKE_GRENADE_ARROW.get(), 3, 2, 6),
					new ItemsForEmeralds(Items.RECOVERY_COMPASS, 16, 1, 2)},
			3, new VillagerTrades.ItemListing[]{
					new ItemsForEmeralds(ItemRegistry.DEADEYE_PENDANT.get(), 32, 1, 1),
					new ItemsForEmeralds(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get(), 32, 1, 1),
					new ItemsForEmeralds(ItemRegistry.NETHERITE_SHIELD.get(), 24, 1, 1),
					new ItemsForEmeralds(ItemRegistry.BERSERKERS_AMULET.get(), 28, 1, 1),
					new ItemsForEmeralds(ItemRegistry.AMETHYST_RING.get(), 28, 1, 1),
					new ItemsForEmeralds(ItemRegistry.EMERALD_RING.get(), 28, 1, 1),
					new ItemsForEmeralds(Items.TOTEM_OF_UNDYING, 30, 1, 1),
			}));

	public SkeletonMerchantEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
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
	public Int2ObjectMap<ItemListing[]> getTrades() {
		return TRADES;
	}
}