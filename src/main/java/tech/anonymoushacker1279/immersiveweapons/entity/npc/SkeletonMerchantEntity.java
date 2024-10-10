package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class SkeletonMerchantEntity extends AbstractMerchantEntity {

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

			if (!level().isClientSide) {
				if (!getOffers().isEmpty()) {
					setTradingPlayer(player);
					openTradingScreen(player, getDisplayName(), 1);
				}
			}
			return InteractionResult.sidedSuccess(level().isClientSide);
		} else {
			return super.mobInteract(player, hand);
		}
	}
}