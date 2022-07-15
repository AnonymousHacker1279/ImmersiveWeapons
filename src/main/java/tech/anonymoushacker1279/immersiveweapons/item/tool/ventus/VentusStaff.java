package tech.anonymoushacker1279.immersiveweapons.item.tool.ventus;

import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public class VentusStaff extends Item {

	private boolean pushedEntity = false;

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
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		List<Entity> entities = player.level.getEntities(player,
				player.getBoundingBox().move(-2, 0, -2).expandTowards(4, 2, 4));

		if (!entities.isEmpty()) {
			for (Entity entity : entities) {
				if (entity.isAlive()) {
					level.addParticle(ParticleTypes.CLOUD,
							entity.getX(),
							entity.getY() + 0.3d,
							entity.getZ(),
							GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
							GeneralUtilities.getRandomNumber(0.0d, 0.03d),
							GeneralUtilities.getRandomNumber(-0.03d, 0.03d));

					entity.push(player.getLookAngle().get(Axis.X), 1f, player.getLookAngle().get(Axis.Z));
					pushedEntity = true;
				}
			}
		}

		if (pushedEntity) {
			level.playLocalSound(player.getX(), player.getY(), player.getZ(),
					SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 0.4f, 1.0f, true);

			pushedEntity = false;

			if (!player.isCreative()) {
				player.getCooldowns().addCooldown(this, 100);
				itemInHand.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}

	/**
	 * Check for a valid repair item.
	 *
	 * @param toRepair the <code>ItemStack</code> being repaired
	 * @param repair   the <code>ItemStack</code> to repair the first one
	 * @return boolean
	 */
	@Override
	public boolean isValidRepairItem(@NotNull ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == DeferredRegistryHandler.VENTUS_SHARD.get();
	}
}