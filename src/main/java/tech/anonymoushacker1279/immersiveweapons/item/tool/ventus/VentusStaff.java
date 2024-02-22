package tech.anonymoushacker1279.immersiveweapons.item.tool.ventus;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public class VentusStaff extends Item {

	/**
	 * Constructor for VentusStaff.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public VentusStaff(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the player right-clicks.
	 *
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> performing the action
	 * @param hand   the <code>Hand</code> the player is using
	 * @return InteractionResultHolder extending ItemStack
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		List<Entity> entities = level.getEntities(player, player.getBoundingBox().inflate(3, 2, 3));

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

			postPush(player, level, itemInHand);
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

	private void postPush(Player player, Level level, ItemStack itemInHand) {
		level.playLocalSound(player.getX(), player.getY(), player.getZ(),
				SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 0.4f, 1.0f, true);

		if (!player.isCreative()) {
			player.getCooldowns().addCooldown(this, 100);
			itemInHand.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
	}

	/**
	 * Check for a valid repair item.
	 *
	 * @param toRepair the <code>ItemStack</code> being repaired
	 * @param repair   the <code>ItemStack</code> to repair the first one
	 * @return boolean
	 */
	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == ItemRegistry.VENTUS_SHARD.get();
	}
}