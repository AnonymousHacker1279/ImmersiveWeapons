package tech.anonymoushacker1279.immersiveweapons.entity.npc;

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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades.*;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.*;
import java.util.Map.Entry;

public class SkygazerEntity extends AbstractMerchantEntity {

	public SkygazerEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
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
		// Remove any other offers
		getOffers().removeIf(offer -> (offer instanceof IdentifiableMerchantOffer identifiableMerchantOffer
				&& identifiableMerchantOffer.getId().equals("enchant_item_for_items")));

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
			EnchantItemForItems trade = new EnchantItemForItems(enchantableItem, ItemRegistry.CELESTIAL_FRAGMENT.get(), 1);
			MerchantOffer offer = trade.getOffer(this, player.getRandom());

			if (trade.getTotalEnchantmentLevels() > GeneralUtilities.getTotalEnchantmentLevels(enchantableItem)) {
				getOffers().add(offer);
			}
		}
	}

	private void setupAddItemEnchantsTrade(Player player) {
		// Remove any other offers
		getOffers().removeIf(offer -> (offer instanceof IdentifiableMerchantOffer identifiableMerchantOffer
				&& identifiableMerchantOffer.getId().equals("enchant_item_with_enchanting_books")));

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

		// Check the enchants on the two items. If the book has the same level or lower, remove the offer
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
			EnchantItemWithEnchantingBooks trade = new EnchantItemWithEnchantingBooks(enchantableItem, enchantableBook, 1);
			MerchantOffer offer = trade.getOffer(this, player.getRandom());

			if (trade.getTotalEnchantmentLevels() > GeneralUtilities.getTotalEnchantmentLevels(enchantableItem)) {
				getOffers().add(offer);
			}
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