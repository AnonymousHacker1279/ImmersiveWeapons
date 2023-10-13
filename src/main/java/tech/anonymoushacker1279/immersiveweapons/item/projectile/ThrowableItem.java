package tech.anonymoushacker1279.immersiveweapons.item.projectile;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.*;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class ThrowableItem extends Item {

	public final ThrowableType type;
	public final int color;

	/**
	 * Creates a throwable item for a specific {@link ThrowableType}
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param type       the <code>ThrowableType</code> to create
	 */
	public ThrowableItem(Properties properties, ThrowableType type) {
		super(properties);
		this.type = type;
		this.color = -1;
	}

	/**
	 * Creates a throwable item, specifically for a smoke grenade.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param color      the color of the smoke grenade
	 */
	public ThrowableItem(Properties properties, int color) {
		super(properties);
		this.type = ThrowableType.SMOKE_GRENADE;
		this.color = color;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		level.playLocalSound(player.getX(), player.getY(), player.getZ(),
				SoundEventRegistry.GENERIC_ITEM_THROW.get(),
				SoundSource.NEUTRAL,
				0.5F,
				0.4F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 0.8F),
				false);

		if (!level.isClientSide) {
			ThrowableItemProjectile throwable = null;

			switch (type) {
				case MOLOTOV -> throwable = createMolotov(itemInHand, level, player);
				case MUD_BALL -> throwable = createMudBall(itemInHand, level, player);
				case SMOKE_GRENADE -> throwable = createSmokeGrenade(itemInHand, level, player);
			}

			if (throwable != null) {
				level.addFreshEntity(throwable);
			}
		}

		if (!player.isCreative()) {
			itemInHand.shrink(1);

			int cooldown;
			switch (type) {
				case MOLOTOV, SMOKE_GRENADE -> cooldown = 100;
				default -> cooldown = 0;
			}

			if (cooldown > 0) {
				player.getCooldowns().addCooldown(this, cooldown);
			}
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}

	private ThrowableItemProjectile createMolotov(ItemStack stack, Level level, Player player) {
		MolotovEntity molotovEntity = new MolotovEntity(level, player);
		molotovEntity.setItem(stack);
		molotovEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);

		return molotovEntity;
	}

	private ThrowableItemProjectile createMudBall(ItemStack stack, Level level, Player player) {
		MudBallEntity mudBall = new MudBallEntity(level, player);
		mudBall.setItem(stack);
		mudBall.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);

		return mudBall;
	}

	private ThrowableItemProjectile createSmokeGrenade(ItemStack stack, Level level, Player player) {
		SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, player);
		smokeGrenadeEntity.setColor(color);
		smokeGrenadeEntity.setItem(stack);
		smokeGrenadeEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);

		return smokeGrenadeEntity;
	}

	public enum ThrowableType {
		MOLOTOV,
		MUD_BALL,
		SMOKE_GRENADE
	}
}