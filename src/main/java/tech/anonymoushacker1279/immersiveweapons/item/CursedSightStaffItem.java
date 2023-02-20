package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.EvilEyeEntity;

public class CursedSightStaffItem extends Item implements SummoningStaff {

	public CursedSightStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		BlockPos lookingAt = getBlockLookingAt(player, level, getMaxRange());

		if (lookingAt != null) {
			// Build targeting conditions for combat, and exclude other summoned evil eyes
			TargetingConditions conditions = TargetingConditions.forCombat().selector(
					(entity) -> {
						if (entity instanceof EvilEyeEntity eye) {
							return !eye.summonedByStaff();
						} else {
							return true;
						}
					}
			);

			LivingEntity nearestEntity = level.getNearestEntity(LivingEntity.class, conditions, player,
					lookingAt.getX(), lookingAt.getY(), lookingAt.getZ(),
					new AABB(lookingAt).inflate(3));

			if (nearestEntity != null) {
				EvilEyeEntity evilEyeEntity = EvilEyeEntity.create(level, player.position(), true);

				evilEyeEntity.setTargetedEntity(nearestEntity);
			} else {
				return InteractionResultHolder.pass(player.getItemInHand(hand));
			}
		} else {
			return InteractionResultHolder.pass(player.getItemInHand(hand));
		}


		handleCooldown(this, lookingAt, player, hand);

		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
	}

	@Override
	public int getMaxRange() {
		return CommonConfig.CURSED_SIGHT_STAFF_MAX_USE_RANGE.get();
	}

	@Override
	public int getStaffCooldown() {
		return 250;
	}
}