package tech.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryManager;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.data.GunData;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractGunItem extends Item implements Vanishable {

	protected static final Predicate<ItemStack> MUSKET_BALLS = (stack) -> stack.is(IWItemTagGroups.MUSKET_BALLS);
	protected static final Predicate<ItemStack> FLARES = (stack) -> stack.is(IWItemTagGroups.FLARES);
	protected static final Predicate<ItemStack> CANNONBALLS = (stack) -> stack.is(IWItemTagGroups.CANNONBALLS);

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
			if (ammo.getItem() instanceof BulletItem<?> bullet) {
				if (livingEntity.getRandom().nextFloat() <= bullet.misfireChance()) {
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

				if (!level.isClientSide) {
					BulletItem<?> bulletItem = (BulletItem<?>) (ammo.getItem() instanceof BulletItem<?>
							? ammo.getItem() : defaultAmmo());

					for (int i = 0; i < bulletsToFire; ++i) {
						BulletEntity bulletEntity;

						if (MUSKET_BALLS.test(ammo)) {
							bulletEntity = bulletItem.createBullet(level, player);
						} else if (FLARES.test(ammo)) {
							bulletEntity = bulletItem.createFlare(level, player);
						} else {
							bulletEntity = bulletItem.createCannonball(level, player);
						}

						bulletEntity.setFiringItem(this);
						setupFire(gun, bulletEntity, player);

						// Roll for random crits
						if (livingEntity.getRandom().nextFloat() <= ImmersiveWeapons.COMMON_CONFIG.gunCritChance().get()) {
							bulletEntity.setCritArrow(true);
						}

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

						// Handle bullet density modifiers
						if (ammo.getTag() != null && ammo.getTag().contains("densityModifier")) {
							float densityModifier = ammo.getTag().getFloat("densityModifier");

							// A full 100% value is +20% damage
							bulletEntity.setBaseDamage(bulletEntity.getBaseDamage() + (bulletEntity.getBaseDamage() * (densityModifier * 0.2f)));

							// Higher density slightly increases the gravity modifier
							bulletEntity.gravityModifier += (densityModifier * 0.015f);
						}

						gun.hurtAndBreak(1, player, (entity) ->
								entity.broadcastBreakEvent(player.getUsedItemHand()));

						level.addFreshEntity(bulletEntity);
					}
				} else {
					// Handle recoil
					player.yHeadRot = player.yHeadRot + GeneralUtilities.getRandomNumber(getMaxYRecoil(), 0.5f);
					player.setXRot(player.getXRot() + GeneralUtilities.getRandomNumber(getMaxXRecoil(), -3.0f));
				}

				// Handle muzzle flash
				// Calculate a particle position based on the player's position and rotation
				Vec3 particlePosition = player.getEyePosition(1.0f);
				particlePosition = particlePosition.add(player.getLookAngle().scale(0.5d));

				// Shift to the side (taking into consideration which hand the gun is in)
				Vec3 sideVector = player.getLookAngle().cross(new Vec3(0, 1, 0.2));
				double sideOffset = player.getUsedItemHand() == InteractionHand.MAIN_HAND ? 0.5d : -0.5d;
				particlePosition = particlePosition.add(sideVector.scale(sideOffset));

				// Adjust forward position based on player FOV
				double fov = GunData.playerFOV;
				double fovOffset = 1.75d - (fov / 150.0d);
				Vec3 lookVector = player.getLookAngle();
				particlePosition = particlePosition.add(lookVector.x * fovOffset * 0.5d, lookVector.y * fovOffset * 0.5d, lookVector.z * fovOffset * 0.5d);

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
					// Reduce cooldown in certain conditions
					float reductionFactor = (float) AccessoryManager.collectEffects(EffectType.FIREARM_RELOAD_SPEED, player);

					int rapidFireLevel = gun.getEnchantmentLevel(EnchantmentRegistry.RAPID_FIRE.get());
					reductionFactor += (0.05f * rapidFireLevel);

					// Calculate the cooldown
					int cooldown = (int) (getCooldown() * (1f - reductionFactor));

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
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
		super.onUseTick(level, livingEntity, stack, remainingUseDuration);

		if (!level.isClientSide && canScope() && livingEntity instanceof Player player) {
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

	@Override
	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.CUSTOM;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		// Handle arm posing for holding the weapons
		consumer.accept(new IClientItemExtensions() {

			private static final ArmPose AIM_PISTOL_POSE = ArmPose.create("AIM_PISTOL", false, (model, entity, arm) -> {
				// Hold the gun up as if it's being aimed
				if (arm == HumanoidArm.RIGHT) {
					model.rightArm.xRot = -1.5f;
					model.rightArm.yRot = -0.25f;

					// Adjust to move with the head (looking up/down moves the gun)
					model.rightArm.xRot += model.head.xRot * 0.5f;
					model.rightArm.yRot += model.head.yRot * 0.5f;
				} else {
					model.leftArm.xRot = -1.5f;
					model.leftArm.yRot = 0.25f;

					// Adjust to move with the head
					model.leftArm.xRot += model.head.xRot * 0.5f;
					model.leftArm.yRot += model.head.yRot * 0.5f;
				}
			});

			private static final ArmPose AIM_MUSKET_POSE = ArmPose.create("AIM_MUSKET", true, (model, entity, arm) -> {
				// Hold the gun up as if it's being aimed. This one uses two hands, one needs to be supporting the gun at the end and the other midway
				if (arm == HumanoidArm.RIGHT) {
					model.rightArm.xRot = -1.5f;
					model.rightArm.yRot = -0.25f;
					// The left arm needs to be moved over more to support the gun
					model.leftArm.xRot = -1.5f;
					model.leftArm.yRot = 1.0f;

					// Adjust to move with the head
					model.rightArm.xRot += model.head.xRot * 0.5f;
					model.rightArm.yRot += model.head.yRot * 0.5f;
					model.leftArm.xRot += model.head.xRot * 0.25f;
					model.leftArm.yRot += model.head.yRot * 0.25f;
				} else {
					model.leftArm.xRot = -1.5f;
					model.leftArm.yRot = 0.25f;
					// The right arm needs to be moved over more to support the gun
					model.rightArm.xRot = -1.5f;
					model.rightArm.yRot = -1.0f;

					// Adjust to move with the head
					model.leftArm.xRot += model.head.xRot * 0.5f;
					model.leftArm.yRot += model.head.yRot * 0.5f;
					model.rightArm.xRot += model.head.xRot * 0.25f;
					model.rightArm.yRot += model.head.yRot * 0.25f;
				}
			});

			@Override
			public ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack itemStack) {
				if (!itemStack.isEmpty()) {
					if (entity.getUsedItemHand() == hand && entity.getUseItemRemainingTicks() > 0) {
						if (itemStack.getItem() instanceof MusketItem || itemStack.getItem() instanceof SimpleShotgunItem) {
							return AIM_MUSKET_POSE;
						} else {
							return AIM_PISTOL_POSE;
						}
					}
				}
				return ArmPose.EMPTY;
			}

			@Override
			public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
			                                       ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {

				// Don't use custom transform until it is fully equipped
				if (equipProcess < 1.0f && !player.isUsingItem()) {
					return false;
				}

				applyItemArmTransform(poseStack, arm);
				return true;
			}

			private void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm) {
				int i = arm == HumanoidArm.RIGHT ? 1 : -1;
				poseStack.translate(i * 0.56F, -0.52F, -0.72F);
			}
		});
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
		return Integer.MAX_VALUE;
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
		return ImmersiveWeapons.COMMON_CONFIG.flintlockPistolFireVelocity().get().floatValue();
	}

	public int getKnockbackLevel() {
		return 0;
	}

	protected void setupFire(ItemStack gun, BulletEntity bulletEntity, Player player) {
		bulletEntity.shootFromRotation(player, player.getXRot(), player.getYRot(),
				0.0F,
				getFireVelocity(gun),
				ImmersiveWeapons.COMMON_CONFIG.flintlockPistolFireInaccuracy().get().floatValue());
	}

	protected void handleAmmoStack(ItemStack gun, ItemStack ammo, int bulletsToFire, Player player) {
		if (!player.isCreative() && ammo.getItem() instanceof BulletItem<?> bulletItem) {
			if (!bulletItem.isInfinite(ammo, gun, player)) {
				float ammoConservationChance = (float) AccessoryManager.collectEffects(EffectType.FIREARM_AMMO_CONSERVATION_CHANCE, player);
				if (!player.level().isClientSide) {
					if (player.getRandom().nextFloat() <= ammoConservationChance) {
						player.getInventory().setChanged(); // Resync the inventory because the client may not roll the same number
						return;
					}
				}

				ammo.shrink(bulletsToFire);
				if (ammo.isEmpty()) {
					player.getInventory().removeItem(ammo);
				}
			}
		}
	}
}