package tech.anonymoushacker1279.immersiveweapons.item.projectile.throwable;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class SmokeGrenadeItem extends Item {

	private final int color;

	/**
	 * Constructor for SmokeGrenadeItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public SmokeGrenadeItem(Properties properties, int color) {
		super(properties);
		this.color = color;
	}

	/**
	 * Runs when the player right-clicks.
	 *
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> performing the action
	 * @param hand   the <code>InteractionHand</code> the player is using
	 * @return InteractionResultHolder extending ItemStack
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(),
				DeferredRegistryHandler.GENERIC_WHOOSH.get(), SoundSource.NEUTRAL,
				0.5F, 0.4F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 0.8F));

		if (!level.isClientSide) {
			SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, player);
			smokeGrenadeEntity.setColor(color);
			smokeGrenadeEntity.setItem(itemInHand);
			smokeGrenadeEntity.shootFromRotation(player, player.xRot, player.yRot, -20.0F, 0.5F, 1.0F);
			level.addFreshEntity(smokeGrenadeEntity);
		}

		if (!player.isCreative()) {
			itemInHand.shrink(1);
			player.getCooldowns().addCooldown(this, 100);
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}
}