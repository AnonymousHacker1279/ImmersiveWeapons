package tech.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.data.GunData;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.function.Predicate;

public abstract class AbstractGunItem extends Item implements Vanishable {

	protected static final Predicate<ItemStack> MUSKET_BALLS = (stack) -> stack.is(ImmersiveWeaponsItemTagGroups.MUSKET_BALLS);
	protected static final Predicate<ItemStack> FLARES = (stack) -> stack.is(ImmersiveWeaponsItemTagGroups.FLARES);

	/**
	 * Constructor for AbstractGunItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	protected AbstractGunItem(Properties properties) {
		super(properties);
	}

	@Override
	public void releaseUsing(ItemStack gun, Level level, LivingEntity livingEntity,
	                         int timeLeft) {

		if (livingEntity instanceof Player player) {
			if (level.isClientSide) {
				GunData.changingPlayerFOV = -1;
				GunData.scopeScale = 0.5f;
			}

			boolean isCreative = player.isCreative();
			boolean misfire = false;
			ItemStack ammo = findAmmo(gun, livingEntity);

			// Determine number of bullets to fire
			int bulletsToFire = isCreative ? getMaxBulletsToFire() : getBulletsToFire(ammo);

			// Roll for misfire
			if (ammo.getItem() instanceof AbstractBulletItem bullet) {
				if (GeneralUtilities.getRandomNumber(0.0f, 1.0001f) <= bullet.misfireChance()) {
					misfire = true;
				}

				if (misfire) {
					level.playSound(null, player.getX(), player.getY(), player.getZ(),
							getMisfireSound(), SoundSource.PLAYERS, 1.0F,
							1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);

					handleAmmoStack(gun, ammo, bulletsToFire, player);
					if (!isCreative) {
						gun.hurtAndBreak(5, player, (entity) ->
								entity.broadcastBreakEvent(entity.getUsedItemHand()));
					}
				}
			}

			if (getUseDuration(gun) < 0) {
				return;
			}

			// Check if the ammunition stack is not empty, if the player is in creative mode,
			// or that a misfire hasn't occurred
			if (!misfire && (!ammo.isEmpty() || isCreative)) {
				// If the ammunition stack is empty, set it to the default.
				// This happens when the player is in creative mode but has no ammunition.
				if (ammo.isEmpty()) {
					ammo = new ItemStack(defaultAmmo());
				}

				player.lerpTo(player.getX(), player.getY(), player.getZ(),
						player.getYRot() + GeneralUtilities.getRandomNumber(getMaxYRecoil(), 0.5f),
						player.getXRot() + GeneralUtilities.getRandomNumber(getMaxXRecoil(), -3.0f),
						1,
						false);

				if (!level.isClientSide) {
					AbstractBulletItem bulletItem = (AbstractBulletItem) (ammo.getItem() instanceof AbstractBulletItem
							? ammo.getItem() : defaultAmmo());

					for (int i = 0; i < bulletsToFire; ++i) {
						BulletEntity bulletEntity = bulletItem.createBullet(level, player);

						bulletEntity.setFiringItem(bulletItem);
						setupFire(gun, bulletEntity, player);

						// Roll for random crits
						if (GeneralUtilities.getRandomNumber(0f, 1f) <= CommonConfig.GUN_CRIT_CHANCE.get()) {
							bulletEntity.setCritArrow(true);
						}

						bulletEntity.setOwner(player);
						bulletEntity.pickup = Pickup.DISALLOWED;

						// Handle enchants
						int enchantmentLevel = gun.getEnchantmentLevel(EnchantmentRegistry.FIREPOWER.get());
						if (enchantmentLevel > 0) {
							bulletEntity.setBaseDamage(bulletEntity.getBaseDamage() + (double) enchantmentLevel * 0.5D + 0.5D);
						}

						enchantmentLevel = gun.getEnchantmentLevel(EnchantmentRegistry.IMPACT.get());
						int kb = getKnockbackLevel();
						if (enchantmentLevel > 0) {
							kb += enchantmentLevel;
						}
						bulletEntity.setKnockback(kb);

						enchantmentLevel = gun.getEnchantmentLevel(EnchantmentRegistry.SCORCH_SHOT.get());
						if (enchantmentLevel > 0) {
							bulletEntity.setSecondsOnFire(enchantmentLevel * 100);
						}

						gun.hurtAndBreak(1, player, (entity) ->
								entity.broadcastBreakEvent(player.getUsedItemHand()));

						level.addFreshEntity(bulletEntity);
					}
				}

				double forwards = 0.67 - (GunData.playerFOV * 0.001);
				float left = -0.35f;

				Vec2 rotationVector = player.getRotationVector();
				Vec3 eyePosition = player.getEyePosition().subtract(0, 0.1f, 0);
				float f = Mth.cos((rotationVector.y + 90.0F) * ((float) Math.PI / 180F));
				float f1 = Mth.sin((rotationVector.y + 90.0F) * ((float) Math.PI / 180F));
				float f2 = Mth.cos(-rotationVector.x * ((float) Math.PI / 180F));
				float f3 = Mth.sin(-rotationVector.x * ((float) Math.PI / 180F));
				float f4 = Mth.cos((-rotationVector.x + 90.0F) * ((float) Math.PI / 180F));
				float f5 = Mth.sin((-rotationVector.x + 90.0F) * ((float) Math.PI / 180F));
				Vec3 vec31 = new Vec3(f * f2, f3, f1 * f2);
				Vec3 vec32 = new Vec3(f * f4, f5, f1 * f4);
				Vec3 vec33 = vec31.cross(vec32).scale(-1.0D);
				double d0 = vec31.x * forwards + vec33.x * left;
				double d1 = vec31.y * forwards + vec33.y * left;
				double d2 = vec31.z * forwards + vec33.z * left;
				Vec3 particlePosition = new Vec3(eyePosition.x + d0, eyePosition.y + d1, eyePosition.z + d2);

				for (int i = 0; i < bulletsToFire; ++i) {
					level.addParticle(ParticleTypesRegistry.MUZZLE_FLASH_PARTICLE.get(),
							particlePosition.x, particlePosition.y, particlePosition.z,
							GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
							GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
							GeneralUtilities.getRandomNumber(-0.01d, 0.01d));

					level.addParticle(ParticleTypes.SMOKE,
							particlePosition.x, particlePosition.y, particlePosition.z,
							GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
							GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
							GeneralUtilities.getRandomNumber(-0.01d, 0.01d));
				}

				level.playSound(null, player.getX(), player.getY(), player.getZ(),
						getFireSound(), SoundSource.PLAYERS, 1.0F,
						1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);

				handleAmmoStack(gun, ammo, bulletsToFire, player);

				if (!player.isCreative()) {
					int rapidFireLevel = gun.getEnchantmentLevel(EnchantmentRegistry.RAPID_FIRE.get());
					// Each level reduces the cooldown by 5%
					int cooldown = getCooldown() - (int) (getCooldown() * (0.05f * rapidFireLevel));
					player.getCooldowns().addCooldown(this, cooldown);
				}
			}
		}
	}

	public ItemStack findAmmo(ItemStack gun, LivingEntity livingEntity) {
		if (gun.getItem() instanceof AbstractGunItem gunItem) {
			if (livingEntity instanceof Player player) {
				Predicate<ItemStack> ammoPredicate = (gunItem).getInventoryAmmoPredicate();
				ItemStack heldAmmo = AbstractGunItem.getHeldAmmo(player, ammoPredicate);
				if (!heldAmmo.isEmpty()) {
					return heldAmmo;
				} else {
					ammoPredicate = gunItem.getInventoryAmmoPredicate();
					for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
						ItemStack ammoItem = player.getInventory().getItem(i);
						if (ammoPredicate.test(ammoItem)) {
							return ammoItem;
						}
					}

					return player.isCreative() ? new ItemStack(defaultAmmo()) : ItemStack.EMPTY;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	protected static ItemStack getHeldAmmo(LivingEntity livingEntity, Predicate<ItemStack> isAmmo) {
		if (isAmmo.test(livingEntity.getItemInHand(InteractionHand.OFF_HAND))) {
			return livingEntity.getItemInHand(InteractionHand.OFF_HAND);
		} else {
			return isAmmo.test(livingEntity.getItemInHand(InteractionHand.MAIN_HAND)) ?
					livingEntity.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		ItemStack itemInHand = player.getItemInHand(hand);
		boolean hasAmmo = !findAmmo(itemInHand, player).isEmpty();

		InteractionResultHolder<ItemStack> resultHolder = ForgeEventFactory.onArrowNock(itemInHand, level, player,
				hand, hasAmmo);
		if (resultHolder != null) {
			return resultHolder;
		}

		if (!player.isCreative() && !hasAmmo) {
			return InteractionResultHolder.fail(itemInHand);
		} else {
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(itemInHand);
		}
	}

	@Override
	public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
		if (!player.level.isClientSide && canScope()) {
			PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
					new GunScopePacketHandler(GunData.playerFOV, 15.0d, GunData.scopeScale));
		}
	}

	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
		if (pEntity instanceof Player player) {
			if (pLevel.isClientSide && !player.getUseItem().is(ItemRegistry.MUSKET_SCOPE.get())) {
				GunData.changingPlayerFOV = -1;
				GunData.scopeScale = 0.5f;
			}
		}
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, Player player) {

		PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
				new GunScopePacketHandler(GunData.playerFOV, -1, 0.5f));

		return super.onDroppedByPlayer(item, player);
	}

	/**
	 * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
	 *
	 * @return Predicate extending ItemStack
	 */
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return MUSKET_BALLS;
	}

	/**
	 * Return the enchantability factor of the item, most of the time it is based on material.
	 *
	 * @return int
	 */
	@Override
	public int getEnchantmentValue(ItemStack stack) {
		return 1;
	}

	/**
	 * Get the repair material.
	 *
	 * @return Ingredient
	 */
	protected Ingredient getRepairMaterial() {
		return Ingredient.of(Items.IRON_INGOT);
	}

	/**
	 * Get the default ammunition.
	 *
	 * @return Item
	 */
	public Item defaultAmmo() {
		return ItemRegistry.IRON_MUSKET_BALL.get();
	}


	/**
	 * Get the use duration.
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return int
	 */
	@Override
	public int getUseDuration(ItemStack stack) {
		return 100;
	}

	/**
	 * Get the misfire sound.
	 *
	 * @return SoundEvent
	 */
	SoundEvent getMisfireSound() {
		return SoundEventRegistry.FLINTLOCK_PISTOL_MISFIRE.get();
	}

	/**
	 * Get the fire sound.
	 *
	 * @return SoundEvent
	 */
	public SoundEvent getFireSound() {
		return SoundEventRegistry.FLINTLOCK_PISTOL_FIRE.get();
	}

	/**
	 * Get the maximum number of bullets that can be fired at once.
	 *
	 * @return int
	 */
	public int getMaxBulletsToFire() {
		return 1;
	}

	/**
	 * Get the number of bullets to fire.
	 *
	 * @param itemStack the ammunition <code>ItemStack</code>
	 * @return int
	 */
	public int getBulletsToFire(ItemStack itemStack) {
		return Math.min(itemStack.getCount(), getMaxBulletsToFire());
	}

	public int getCooldown() {
		return 60;
	}

	public float getMaxYRecoil() {
		return -0.5f;
	}

	public float getMaxXRecoil() {
		return -7.0f;
	}

	public boolean canScope() {
		return false;
	}

	public float getFireVelocity(ItemStack gun) {
		int velocityLevel = gun.getEnchantmentLevel(EnchantmentRegistry.VELOCITY.get());
		// Each level increases velocity by 10%
		return getBaseFireVelocity() * (1.0f + (0.1f * velocityLevel));
	}

	public float getBaseFireVelocity() {
		return CommonConfig.FLINTLOCK_PISTOL_FIRE_VELOCITY.get().floatValue();
	}

	public int getKnockbackLevel() {
		return 0;
	}

	protected void setupFire(ItemStack gun, BulletEntity bulletEntity, Player player) {
		bulletEntity.shootFromRotation(player, player.xRot, player.yRot,
				0.0F,
				getFireVelocity(gun),
				CommonConfig.FLINTLOCK_PISTOL_FIRE_INACCURACY.get().floatValue());
	}

	protected void handleAmmoStack(ItemStack gun, ItemStack ammo, int bulletsToFire, Player player) {
		if (!player.isCreative() && ammo.getItem() instanceof AbstractBulletItem bulletItem) {
			if (!bulletItem.isInfinite(ammo, gun, player)) {
				ammo.shrink(bulletsToFire);
				if (ammo.isEmpty()) {
					player.getInventory().removeItem(ammo);
				}
			}
		}
	}
}