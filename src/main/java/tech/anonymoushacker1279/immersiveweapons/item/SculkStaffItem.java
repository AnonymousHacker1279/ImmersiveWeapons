package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;

public class SculkStaffItem extends Item implements SummoningStaff {

	public SculkStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		if (hand != InteractionHand.MAIN_HAND) {
			return InteractionResultHolder.pass(player.getItemInHand(hand));
		}

		Vec3 eyePos = player.getEyePosition();
		Vec3 lookVec = player.getViewVector(1.0F);
		Vec3 normalized = lookVec.normalize();

		// Check if there is an entity in the path of the sonic boom
		boolean canBlastThroughWalls = IWConfigs.SERVER.sculkStaffSonicBlastThroughBlocks.getAsBoolean();
		EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(level, player,
				eyePos,
				eyePos.add(lookVec.scale(getMaxRange())),
				player.getBoundingBox()
						.expandTowards(lookVec.scale(getMaxRange()))
						.inflate(1.0D),
				(entity) -> {
					if (canBlastThroughWalls) {
						return !entity.isSpectator() && entity.isPickable();
					} else {
						return !entity.isSpectator() && entity.isPickable() && player.hasLineOfSight(entity);
					}
				});

		BlockPos lookingAt = getBlockLookingAt(player, level, getMaxRange());
		double particleDistance = (lookingAt != null && !canBlastThroughWalls) ? eyePos.distanceTo(lookingAt.getCenter()) : getMaxRange();

		if (hitResult != null && hitResult.getEntity() instanceof LivingEntity nearestEntity) {
			nearestEntity.hurt(nearestEntity.damageSources().sonicBoom(player), 10.0F);
			double verticalKB = 0.5 * (1.0 - nearestEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
			double horizontalKB = 2.5 * (1.0 - nearestEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
			nearestEntity.push(normalized.x() * horizontalKB, normalized.y() * verticalKB, normalized.z() * horizontalKB);

			particleDistance = eyePos.distanceTo(nearestEntity.position());
		}

		if (level instanceof ServerLevel serverLevel) {
			for (int i = 1; i < particleDistance; ++i) {
				Vec3 particlePos = eyePos.add(normalized.scale(i));
				serverLevel.sendParticles(ParticleTypes.SONIC_BOOM,
						particlePos.x, particlePos.y, particlePos.z,
						1, 0.0, 0.0, 0.0, 0.0);
			}
		}

		player.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);

		handleCooldown(this, player, hand);

		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
	}

	@Override
	public int getMaxRange() {
		return IWConfigs.SERVER.sculkStaffMaxUseRange.getAsInt();
	}

	@Override
	public int getStaffCooldown() {
		return 150;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return Ingredient.of(Items.ECHO_SHARD).test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public int getEnchantmentValue(ItemStack stack) {
		return 1;
	}
}