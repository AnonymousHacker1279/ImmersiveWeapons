package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.EvilEyeEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EnchantmentRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

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
						} else if (entity instanceof TamableAnimal animal) {
							// Do not target if the entity is tamed to the player
							return !animal.isOwnedBy(player);
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

				// Handle enchantments
				int enchantmentLevel = player.getItemInHand(hand).getEnchantmentLevel(EnchantmentRegistry.NIGHTMARISH_STARE.get());
				if (enchantmentLevel > 0) {
					evilEyeEntity.setEffectChance(0.05f + (0.05f * enchantmentLevel));
				}
				enchantmentLevel = player.getItemInHand(hand).getEnchantmentLevel(EnchantmentRegistry.MALEVOLENT_GAZE.get());
				if (enchantmentLevel > 0) {
					int duration = Math.min(100 + (40 * enchantmentLevel), 300);
					int effectLevel = Math.min(1 + enchantmentLevel, 5);
					evilEyeEntity.setEffectDuration(duration);
					evilEyeEntity.setEffectLevel(effectLevel);
				}

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
		return CommonConfig.cursedSightStaffMaxUseRange;
	}

	@Override
	public int getStaffCooldown() {
		return 250;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return Ingredient.of(ItemRegistry.BROKEN_LENS.get()).test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public int getEnchantmentValue(ItemStack stack) {
		return 1;
	}
}