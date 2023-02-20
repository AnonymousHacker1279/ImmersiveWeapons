package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class MeteorStaffItem extends Item implements SummoningStaff {

	public MeteorStaffItem(Properties properties) {
		super(properties);
	}

	// Summon a meteor entity at the location the player is looking at on right-click
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		BlockPos lookingAt = getBlockLookingAt(player, level, getMaxRange());

		if (!level.isClientSide) {
			if (lookingAt != null) {
				MeteorEntity.create(level, player, lookingAt);
			} else {
				return InteractionResultHolder.pass(player.getItemInHand(hand));
			}
		}

		handleCooldown(this, lookingAt, player, hand);

		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
	}

	@Override
	public boolean useOnRelease(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return Ingredient.of(ItemRegistry.CELESTIAL_FRAGMENT.get()).test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public int getMaxRange() {
		return CommonConfig.METEOR_STAFF_MAX_USE_RANGE.get();
	}
}