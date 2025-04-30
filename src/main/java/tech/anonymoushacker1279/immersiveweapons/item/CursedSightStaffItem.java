package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.EvilEyeEntity;

public class CursedSightStaffItem extends Item implements SummoningStaff {

	public CursedSightStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {

		BlockPos lookingAt = getBlockLookingAt(player, level, getMaxRange());

		if (lookingAt != null && level instanceof ServerLevel serverLevel) {
			// Build targeting conditions for combat, and exclude other summoned evil eyes
			TargetingConditions conditions = TargetingConditions.forCombat().selector(
					(entity, serverLevel1) -> {
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

			LivingEntity nearestEntity = serverLevel.getNearestEntity(LivingEntity.class, conditions, player,
					lookingAt.getX(), lookingAt.getY(), lookingAt.getZ(),
					new AABB(lookingAt).inflate(3));

			if (nearestEntity != null) {
				EvilEyeEntity evilEyeEntity = EvilEyeEntity.create(level, player.position(), true);

				// Handle enchantments
				HolderGetter<Enchantment> enchantmentGetter = evilEyeEntity.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();

				int enchantmentLevel = player.getItemInHand(hand).getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.NIGHTMARISH_STARE));
				if (enchantmentLevel > 0) {
					evilEyeEntity.setEffectChance(0.05f + (0.05f * enchantmentLevel));
				}
				enchantmentLevel = player.getItemInHand(hand).getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.MALEVOLENT_GAZE));
				if (enchantmentLevel > 0) {
					int duration = Math.min(100 + (40 * enchantmentLevel), 300);
					int effectLevel = Math.min(1 + enchantmentLevel, 5);
					evilEyeEntity.setEffectDuration(duration);
					evilEyeEntity.setEffectLevel(effectLevel);
				}

				evilEyeEntity.setTargetedEntity(nearestEntity);
			} else {
				return InteractionResult.PASS;
			}
		} else {
			return InteractionResult.PASS;
		}


		handleCooldown(lookingAt, player, hand);

		return InteractionResult.SUCCESS;
	}

	@Override
	public int getMaxRange() {
		return IWConfigs.SERVER.cursedSightStaffMaxUseRange.getAsInt();
	}

	@Override
	public int getStaffCooldown() {
		return 250;
	}
}