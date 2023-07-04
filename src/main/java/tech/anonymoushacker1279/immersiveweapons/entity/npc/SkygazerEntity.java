package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trades.*;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.*;
import java.util.Map.Entry;

public class SkygazerEntity extends AbstractMerchantEntity {

	public static final Int2ObjectMap<ItemListing[]> TRADES = new Int2ObjectOpenHashMap<>(ImmutableMap.of(
			1, new VillagerTrades.ItemListing[]{
					new ItemsForEmeralds(ItemRegistry.BANDAGE.get(), 1, 1, 8),
					new ItemsForEmeralds(ItemRegistry.FIRST_AID_KIT.get(), 3, 1, 8),
					new ItemsForEmeralds(ItemRegistry.PAINKILLERS.get(), 2, 1, 8),
					new ItemsForEmeralds(ItemRegistry.COBALT_MUSKET_BALL.get(), 1, 8, 12),
					new ItemsForEmeralds(ItemRegistry.DIAMOND_MUSKET_BALL.get(), 2, 8, 12),
					new ItemsForEmeralds(ItemRegistry.COBALT_ARROW.get(), 1, 8, 12),
					new ItemsForEmeralds(ItemRegistry.DIAMOND_ARROW.get(), 2, 8, 12),
					new ItemsForEmeralds(ItemRegistry.AZUL_LOCATOR.get(), 4, 1, 2),
					new ItemsForEmeralds(Items.SPYGLASS, 3, 1, 2)},
			2, new VillagerTrades.ItemListing[]{
					new ItemsForEmeralds(BlockRegistry.STARSTORM_CRYSTAL.get(), 12, 1, 4),
					new ItemsForEmeralds(BlockRegistry.ASTRAL_CRYSTAL.get(), 12, 1, 4),
					new ItemsForEmeralds(ItemRegistry.FLINTLOCK_PISTOL.get(), 6, 1, 3),
					new ItemsForEmeralds(ItemRegistry.BLUNDERBUSS.get(), 7, 1, 3),
					new ItemsForEmeralds(ItemRegistry.MUSKET.get(), 8, 1, 3),
					new ItemsForEmeralds(ItemRegistry.METEOR_STAFF.get(), 28, 1, 1),
					new ItemsForEmeralds(ItemRegistry.CURSED_SIGHT_STAFF.get(), 28, 1, 1)},
			3, new VillagerTrades.ItemListing[]{
					new ItemsForEmeralds(ItemRegistry.TESLA_SWORD.get(), 30, 1, 1),
					new ItemsForEmeralds(ItemRegistry.MOLTEN_SWORD.get(), 30, 1, 1),
					new ItemsForEmeralds(ItemRegistry.VENTUS_SWORD.get(), 30, 1, 1),
					new ItemsForEmeralds(ItemRegistry.ASTRAL_SWORD.get(), 30, 1, 1),
					new ItemsForEmeralds(ItemRegistry.STARSTORM_SWORD.get(), 30, 1, 1),
			}));

	public SkygazerEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public Int2ObjectMap<ItemListing[]> getTrades() {
		return TRADES;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		if (!itemInHand.is(ItemRegistry.SKYGAZER_SPAWN_EGG.get()) && isAlive() && !isTrading() && !isBaby()) {
			if (hand == InteractionHand.MAIN_HAND) {
				player.awardStat(Stats.TALKED_TO_VILLAGER);
			}

			if (!getOffers().isEmpty()) {
				if (!level().isClientSide) {
					// An offer is always added to add enchanting levels from a book to an item in the player's inventory
					setupAddItemEnchantsTrade(player);

					// An offer is always added to increase the enchantment levels on an item in the player's inventory
					setupRaiseItemEnchantsTrade(player);

					setTradingPlayer(player);
					openTradingScreen(player, getDisplayName(), 1);
				}
			}
			return InteractionResult.sidedSuccess(level().isClientSide);
		} else {
			return super.mobInteract(player, hand);
		}
	}

	private void setupRaiseItemEnchantsTrade(Player player) {
		// First, remove any other EnchantItemsForItems offers (check the result item)
		getOffers().removeIf(offer -> offer.getCostB().is(ItemRegistry.CELESTIAL_FRAGMENT.get()));

		// Get the first enchanted item from the player's inventory
		ItemStack enchantableItem = ItemStack.EMPTY;
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack = player.getInventory().getItem(i);
			if (stack.isEnchanted()) {
				enchantableItem = stack;
				break;
			}
		}

		// If the player has an enchanted item, add an offer to increase the enchantment level
		if (!enchantableItem.isEmpty()) {
			getOffers().add(new EnchantItemForItems(enchantableItem, ItemRegistry.CELESTIAL_FRAGMENT.get(), 1)
					.getOffer(this, player.getRandom()));
		}
	}

	private void setupAddItemEnchantsTrade(Player player) {
		// First, remove any other EnchantItemWithEnchantingBooks offers (check the result item)
		getOffers().removeIf(offer -> offer.getCostB().is(Items.ENCHANTED_BOOK));

		// Get the first enchanted item from the player's inventory
		ItemStack enchantableItem = ItemStack.EMPTY;
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack = player.getInventory().getItem(i);
			if (stack.isEnchanted()) {
				enchantableItem = stack;
				break;
			}
		}

		// Get the first enchanted book from the player's inventory
		ItemStack enchantableBook = ItemStack.EMPTY;
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack = player.getInventory().getItem(i);
			if (stack.is(Items.ENCHANTED_BOOK)) {
				enchantableBook = stack;
				break;
			}
		}

		// Check the enchants on the two items. If the book has the same level or higher, remove the offer
		if (!enchantableItem.isEmpty() && !enchantableBook.isEmpty()) {
			Map<Enchantment, Integer> existingEnchantments = enchantableItem.getAllEnchantments();
			Map<Enchantment, Integer> enchantmentsFromBook = new HashMap<>(EnchantmentHelper.getEnchantments(enchantableBook));
			for (Entry<Enchantment, Integer> entry : enchantmentsFromBook.entrySet()) {
				Enchantment enchantment = entry.getKey();
				if (existingEnchantments.containsKey(enchantment) && existingEnchantments.get(enchantment) >= entry.getValue()) {
					return;
				}
			}
		}

		// If the player has an enchanted book, add an offer to add the enchantments to an item
		if (!enchantableBook.isEmpty()) {
			getOffers().add(new EnchantItemWithEnchantingBooks(enchantableItem, enchantableBook, 1)
					.getOffer(this, player.getRandom()));
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		// Give the attacker blindness and summon 2-3 Starmite entities on the attacker
		if (source.getDirectEntity() instanceof LivingEntity attacker) {
			if (TargetingConditions.forCombat().test(this, attacker)) {
				attacker.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 250, 0));

				for (int i = 0; i < getRandom().nextIntBetweenInclusive(2, 3); i++) {
					StarmiteEntity starmite = new StarmiteEntity(EntityRegistry.STARMITE_ENTITY.get(), level());
					starmite.moveTo(attacker.getX(), attacker.getY(), attacker.getZ(), attacker.getXRot(), attacker.getYRot());

					// Increase the attack damage
					Objects.requireNonNull(starmite.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(4.0D);

					level().addFreshEntity(starmite);
				}
			}
		}

		return super.hurt(source, amount);
	}
}