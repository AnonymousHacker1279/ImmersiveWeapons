package tech.anonymoushacker1279.immersiveweapons.item.tool.ventus;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public class VentusStaff extends Item {

	public VentusStaff(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		if (player.isShiftKeyDown()) {
			launchWindCharge(level, player, itemInHand);
		} else {
			pushEntities(level, player, itemInHand);
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}

	private void spawnParticles(Entity entity, Level level) {
		level.addParticle(ParticleTypes.CLOUD,
				entity.getX(),
				entity.getY() + 0.3d,
				entity.getZ(),
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
				GeneralUtilities.getRandomNumber(0.0d, 0.03d),
				GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
	}

	/**
	 * Get nearby entities and push them away from the player.
	 */
	private void pushEntities(Level level, Player player, ItemStack itemInHand) {
		List<Entity> entities = level.getEntities(player, player.getBoundingBox().inflate(IWConfigs.SERVER.ventusStaffRadius.getAsInt()));

		if (!entities.isEmpty()) {
			for (Entity entity : entities) {
				if (entity instanceof LivingEntity livingEntity) {
					spawnParticles(livingEntity, level);

					livingEntity.knockback(1.5f, player.getLookAngle().reverse().x(), player.getLookAngle().reverse().z());
					livingEntity.hurtMarked = true;
				} else if (entity instanceof Projectile projectile) {
					spawnParticles(projectile, level);

					float kbStrength = 1.5f;
					Vec3 deltaMovement = projectile.getDeltaMovement();
					double x = player.getLookAngle().reverse().x();
					double y = player.getLookAngle().reverse().y();
					double z = player.getLookAngle().reverse().z();
					Vec3 newMovement = new Vec3(x, y, z).normalize().scale(kbStrength);
					projectile.setDeltaMovement(
							deltaMovement.x / 2.0 - newMovement.x,
							deltaMovement.y / 2.0 - newMovement.y,
							deltaMovement.z / 2.0 - newMovement.z);
				}
			}

			postUse(player, level, itemInHand, 60);
		}
	}

	/**
	 * Summon a {@link WindCharge} entity in the direction the player is looking.
	 */
	private void launchWindCharge(Level level, Player player, ItemStack itemInHand) {
		if (!level.isClientSide()) {
			WindCharge windCharge = new WindCharge(player, level, player.position().x(), player.getEyePosition().y(), player.position().z());
			windCharge.setOwner(player);
			windCharge.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5f, 0.0f);
			level.addFreshEntity(windCharge);

			postUse(player, level, itemInHand, 20);
		}
	}

	private void postUse(Player player, Level level, ItemStack itemInHand, int cooldown) {
		level.playLocalSound(player.getX(), player.getY(), player.getZ(),
				SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 0.4f, 1.0f, true);

		if (!player.isCreative()) {
			player.getCooldowns().addCooldown(this, cooldown);
			itemInHand.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
		}
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == ItemRegistry.VENTUS_SHARD.get();
	}

	@Override
	public int getEnchantmentValue(ItemStack stack) {
		return 1;
	}
}