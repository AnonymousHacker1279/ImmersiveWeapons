package tech.anonymoushacker1279.immersiveweapons.item.projectile;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.*;
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

		if (type.canCharge) {
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(itemInHand);
		}

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
			}

			if (throwable != null) {
				level.addFreshEntity(throwable);
			}
		}

		handleCooldown(player, itemInHand);

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}

	@Override
	public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
		// Allow smoke grenade throws to be charged
		if (type.canCharge && pLivingEntity instanceof Player player) {
			int i = this.getUseDuration(pStack, pLivingEntity) - pTimeCharged;
			if (i < 0) {
				return;
			}

			float charge = BowItem.getPowerForTime(i);
			if (charge > 0.1f && !pLevel.isClientSide) {
				ThrowableItemProjectile throwable = null;

				switch (type) {
					case SMOKE_GRENADE -> throwable = createSmokeGrenade(pStack, pLevel, player, charge);
					case FLASHBANG -> throwable = createFlashbang(pStack, pLevel, player, charge);
				}

				if (throwable != null) {
					pLevel.addFreshEntity(throwable);

					pLevel.playLocalSound(pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(),
							SoundEventRegistry.GENERIC_ITEM_THROW.get(),
							SoundSource.NEUTRAL,
							0.5F,
							0.4F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 0.8F),
							false);

					handleCooldown(player, pStack);
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack pStack, LivingEntity livingEntity) {
		return type.canCharge ? 72000 : super.getUseDuration(pStack, livingEntity);
	}

	private void handleCooldown(Player player, ItemStack itemInHand) {
		if (!player.isCreative()) {
			itemInHand.shrink(1);

			int cooldown = type == ThrowableType.MUD_BALL ? 0 : 100;
			if (cooldown > 0) {
				player.getCooldowns().addCooldown(this, cooldown);
			}
		}
	}

	public ThrowableItemProjectile createMolotov(ItemStack stack, Level level, Player player) {
		MolotovEntity molotovEntity = new MolotovEntity(level, player);
		molotovEntity.setItem(stack);
		molotovEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);

		return molotovEntity;
	}

	public ThrowableItemProjectile createMolotov(ItemStack stack, Level level, Position position, Direction direction) {
		MolotovEntity molotovEntity = new MolotovEntity(level, position.x(), position.y(), position.z());
		molotovEntity.setItem(stack);
		molotovEntity.shootFromDirection(direction, 0.75F, 0.5F);

		return molotovEntity;
	}

	public ThrowableItemProjectile createMudBall(ItemStack stack, Level level, Player player) {
		MudBallEntity mudBall = new MudBallEntity(level, player);
		mudBall.setItem(stack);
		mudBall.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);

		return mudBall;
	}

	public ThrowableItemProjectile createMudBall(ItemStack stack, Level level, Position position, Direction direction) {
		MudBallEntity mudBall = new MudBallEntity(level, position.x(), position.y(), position.z());
		mudBall.setItem(stack);
		mudBall.shootFromDirection(direction, 1.5F, 1.0F);

		return mudBall;
	}

	public ThrowableItemProjectile createSmokeGrenade(ItemStack stack, Level level, Player player, float charge) {
		SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, player);
		smokeGrenadeEntity.setColor(color);
		smokeGrenadeEntity.setItem(stack);
		smokeGrenadeEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, charge * 1.5F, 0.5F);

		return smokeGrenadeEntity;
	}

	public ThrowableItemProjectile createSmokeGrenade(ItemStack stack, Level level, Position position, Direction direction, float charge) {
		SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
		smokeGrenadeEntity.setColor(color);
		smokeGrenadeEntity.setItem(stack);
		smokeGrenadeEntity.shootFromDirection(direction, charge * 1.5F, 0.5F);

		return smokeGrenadeEntity;
	}

	public ThrowableItemProjectile createFlashbang(ItemStack stack, Level level, Player player, float charge) {
		FlashbangEntity flashbangEntity = new FlashbangEntity(level, player);
		flashbangEntity.setItem(stack);
		flashbangEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, charge * 1.5F, 0.5F);

		return flashbangEntity;
	}

	public ThrowableItemProjectile createFlashbang(ItemStack stack, Level level, Position position, Direction direction, float charge) {
		FlashbangEntity flashbangEntity = new FlashbangEntity(level, position.x(), position.y(), position.z());
		flashbangEntity.setItem(stack);
		flashbangEntity.shootFromDirection(direction, charge * 1.5F, 0.5F);

		return flashbangEntity;
	}

	public enum ThrowableType {
		MOLOTOV(false),
		MUD_BALL(false),
		SMOKE_GRENADE(true),
		FLASHBANG(true);

		public final boolean canCharge;

		ThrowableType(boolean canCharge) {
			this.canCharge = canCharge;
		}
	}
}