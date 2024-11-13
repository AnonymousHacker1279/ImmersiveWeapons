package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryManager;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.gun.data.GunData;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunScopePayload;
import tech.anonymoushacker1279.immersiveweapons.util.ArrowAttributeAccessor;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractGunItem extends Item {

	public static final DataMapType<Item, FlammablePowder> POWDER_TYPE = DataMapType.builder(
					ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "flammable_powder"),
					Registries.ITEM, FlammablePowder.CODEC)
			.synced(FlammablePowder.CODEC, true)
			.build();

	protected static final Predicate<ItemStack> MUSKET_BALLS = (stack) -> stack.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "projectiles/musket_balls")));
	protected static final Predicate<ItemStack> FLARES = (stack) -> stack.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "projectiles/flares")));
	protected static final Predicate<ItemStack> CANNONBALLS = (stack) -> stack.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "projectiles/cannonballs")));
	protected static final Predicate<ItemStack> FLAMMABLE_POWDERS = (stack) -> stack.getItemHolder().getData(POWDER_TYPE) != null;

	final DataComponentType<Float> DENSITY_MODIFIER = DataComponentTypeRegistry.DENSITY_MODIFIER.get();

	/**
	 * Constructor for AbstractGunItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	protected AbstractGunItem(Properties properties) {
		super(properties);
	}

	@Override
	public void releaseUsing(ItemStack gun, Level level, LivingEntity livingEntity, int timeLeft) {

		if (livingEntity instanceof Player player) {
			if (level.isClientSide) {
				GunData.changingPlayerFOV = -1;
				GunData.scopeScale = 0.5f;
			}

			boolean isCreative = player.isCreative();
			boolean misfire = false;
			ItemStack ammo = findAmmo(gun, livingEntity);
			PowderType powder = findPowder(gun, livingEntity);

			// Determine number of bullets to fire
			int bulletsToFire = isCreative ? getMaxBulletsToFire() : getBulletsToFire(ammo);

			// Roll for misfire
			if (ammo.getItem() instanceof BulletItem<?> bullet) {
				if (livingEntity.getRandom().nextFloat() <= bullet.misfireChance) {
					misfire = true;
				}

				// High misfire chance when underwater
				if (livingEntity.isUnderWater()) {
					if (livingEntity.getRandom().nextFloat() <= 0.9f) {
						misfire = true;
					}
				}

				if (misfire) {
					level.playSound(null, player.getX(), player.getY(), player.getZ(),
							getMisfireSound(), SoundSource.PLAYERS, 1.0F,
							1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);

					handleAmmoStack(gun, ammo, bulletsToFire, player);
					handlePowderStack(powder, player);

					if (!isCreative) {
						gun.hurtAndBreak(5, player, EquipmentSlot.MAINHAND);
					}
				}
			}

			if (getUseDuration(gun, player) < 0) {
				return;
			}

			// Check if the gun can be fired
			if (!misfire && ((!ammo.isEmpty() && powder != null) || isCreative)) {
				// If the ammunition stack is empty, set it to the default.
				// This happens when the player is in creative mode but has no ammunition.
				if (ammo.isEmpty()) {
					ammo = new ItemStack(defaultAmmo());
				}

				if (powder == null) {
					powder = new PowderType(new ItemStack(defaultPowder()));
				}

				if (!level.isClientSide) {
					BulletItem<?> bulletItem = (BulletItem<?>) (ammo.getItem() instanceof BulletItem<?> ? ammo.getItem() : defaultAmmo());

					for (int i = 0; i < bulletsToFire; ++i) {
						BulletEntity bulletEntity;

						if (MUSKET_BALLS.test(ammo)) {
							bulletEntity = bulletItem.createBullet(level, player, gun);
						} else if (FLARES.test(ammo)) {
							bulletEntity = bulletItem.createFlare(level, player, gun);
						} else {
							bulletEntity = bulletItem.createCannonball(level, player, gun);
						}

						setupFire(player, bulletEntity, gun, ammo, powder);
						level.addFreshEntity(bulletEntity);
					}
				} else {
					// Handle recoil
					player.yHeadRot = player.yHeadRot + GeneralUtilities.getRandomNumber(getMaxYRecoil(), 0.5f);
					player.setXRot(player.getXRot() + GeneralUtilities.getRandomNumber(getMaxXRecoil(), -3.0f));
				}

				level.playSound(null, player.getX(), player.getY(), player.getZ(),
						getFireSound(), SoundSource.PLAYERS, 1.0F,
						1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);

				handleAmmoStack(gun, ammo, bulletsToFire, player);
				handlePowderStack(powder, player);

				if (!player.isCreative()) {
					HolderGetter<Enchantment> enchantmentGetter = player.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
					Optional<Reference<Enchantment>> rapidFire = enchantmentGetter.get(IWEnchantments.RAPID_FIRE);

					// Reduce cooldown in certain conditions
					float reductionFactor = (float) AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.FIREARM_RELOAD_SPEED.get(), player);

					int rapidFireLevel;
					if (rapidFire.isPresent()) {
						rapidFireLevel = gun.getEnchantmentLevel(rapidFire.get());
						reductionFactor += (0.05f * rapidFireLevel);
					}

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
				Predicate<ItemStack> ammoPredicate = gunItem.getInventoryAmmoPredicate();
				ItemStack heldAmmo = AbstractGunItem.getHeldPredicate(player, ammoPredicate);
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

	@Nullable
	public PowderType findPowder(ItemStack gun, LivingEntity livingEntity) {
		if (gun.getItem() instanceof AbstractGunItem) {
			if (livingEntity instanceof Player player) {
				ItemStack heldPowder = AbstractGunItem.getHeldPredicate(player, FLAMMABLE_POWDERS);
				if (!heldPowder.isEmpty()) {
					return new PowderType(heldPowder);
				} else {
					for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
						ItemStack powder = player.getInventory().getItem(i);
						if (FLAMMABLE_POWDERS.test(powder)) {
							return new PowderType(powder);
						}
					}

					return player.isCreative() ? new PowderType(defaultPowder().getDefaultInstance()) : null;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	protected static ItemStack getHeldPredicate(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
		if (predicate.test(livingEntity.getItemInHand(InteractionHand.OFF_HAND))) {
			return livingEntity.getItemInHand(InteractionHand.OFF_HAND);
		} else {
			return predicate.test(livingEntity.getItemInHand(InteractionHand.MAIN_HAND)) ?
					livingEntity.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

		ItemStack itemInHand = player.getItemInHand(hand);
		boolean hasAmmo = !findAmmo(itemInHand, player).isEmpty();

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

		if (!level.isClientSide && canScope() && livingEntity instanceof ServerPlayer serverPlayer) {
			PacketDistributor.sendToPlayer(serverPlayer, new GunScopePayload(GunData.playerFOV, 15.0d, GunData.scopeScale));
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
		PacketDistributor.sendToPlayer((ServerPlayer) player, new GunScopePayload(GunData.playerFOV, -1, 0.5f));

		return super.onDroppedByPlayer(item, player);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.CUSTOM;
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
	 * Get the default powder.
	 *
	 * @return Item
	 */
	public Item defaultPowder() {
		return Items.GUNPOWDER;
	}

	@Override
	public int getUseDuration(ItemStack pStack, LivingEntity livingEntity) {
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

	public float getFireVelocity(ItemStack gun, float powderModifier, LivingEntity shooter) {
		HolderGetter<Enchantment> enchantmentGetter = shooter.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
		Optional<Reference<Enchantment>> velocity = enchantmentGetter.get(IWEnchantments.VELOCITY);

		int velocityLevel = 0;
		if (velocity.isPresent()) {
			velocityLevel = gun.getEnchantmentLevel(velocity.get());
		}
		// Each level increases velocity by 10%
		return getBaseFireVelocity() * (1.0f + (0.1f * velocityLevel)) * (1.0f + powderModifier);
	}

	public float getBaseFireVelocity() {
		return (float) IWConfigs.SERVER.flintlockPistolFireVelocity.getAsDouble();
	}

	public float getInaccuracy() {
		return (float) IWConfigs.SERVER.flintlockPistolFireInaccuracy.getAsDouble();
	}

	public int getKnockbackLevel() {
		return 0;
	}

	public void setupFire(LivingEntity shooter, BulletEntity bullet, ItemStack gun, @Nullable ItemStack ammo, PowderType powderType) {
		bullet.setFiringItem(this);

		float powderVelocityModifier = powderType.data.velocityModifier();
		if (shooter instanceof Player player) {
			// Check for any scenarios where the powder would become wet
			// Having the Powder Horn accessory reduces the effects slightly
			boolean hasPowderHorn = Accessory.isAccessoryActive(player, ItemRegistry.POWDER_HORN.get());
			if (player.level().isRainingAt(player.blockPosition())) {
				powderVelocityModifier -= hasPowderHorn ? 0.15f : 0.3f;
			}
			if (player.isUnderWater()) {
				powderVelocityModifier -= hasPowderHorn ? 0.25f : 0.5f;
			}
			if (player.isInPowderSnow) {
				powderVelocityModifier -= hasPowderHorn ? 0.1f : 0.2f;
			}
		}

		// Roll for random crits
		if (shooter.getRandom().nextFloat() <= IWConfigs.SERVER.gunCritChance.getAsDouble()) {
			bullet.setCritArrow(true);
		}

		// Handle enchants
		HolderGetter<Enchantment> enchantmentGetter = shooter.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
		Optional<Reference<Enchantment>> firepower = enchantmentGetter.get(IWEnchantments.FIREPOWER);
		Optional<Reference<Enchantment>> scorchShot = enchantmentGetter.get(IWEnchantments.SCORCH_SHOT);

		int enchantmentLevel;
		if (firepower.isPresent()) {
			enchantmentLevel = gun.getEnchantmentLevel(firepower.get());
			if (enchantmentLevel > 0) {
				bullet.setBaseDamage(bullet.getBaseDamage() + (double) enchantmentLevel * 0.5D + 0.5D);
			}
		}

		if (scorchShot.isPresent()) {
			enchantmentLevel = gun.getEnchantmentLevel(scorchShot.get());
			if (enchantmentLevel > 0) {
				bullet.igniteForSeconds(enchantmentLevel * 100);
			}
		}

		// Handle bullet density modifiers
		if (ammo != null) {
			float densityModifier = ammo.getOrDefault(DENSITY_MODIFIER, 0f);
			if (densityModifier > 0) {
				// A full 100% value is +20% damage
				bullet.setBaseDamage(bullet.getBaseDamage() + (bullet.getBaseDamage() * (densityModifier * 0.2f)));

				// Higher density slightly increases the gravity modifier
				((ArrowAttributeAccessor) bullet).immersiveWeapons$setGravity(bullet.getGravity() + (densityModifier * 0.015f));
			}
		}

		bullet.flameTrail = powderType.data.hasFlameTrail();

		int weaponDamage = powderType.data.weaponDamageAmount();
		gun.hurtAndBreak(weaponDamage, shooter, EquipmentSlot.MAINHAND);

		prepareBulletForFire(gun, bullet, shooter, powderVelocityModifier);
		handleMuzzleFlash(shooter.level(), shooter, powderType);
	}

	public void prepareBulletForFire(ItemStack gun, BulletEntity bulletEntity, LivingEntity shooter, float powderModifier) {
		bulletEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(),
				0.0F,
				getFireVelocity(gun, powderModifier, shooter),
				getInaccuracy());
	}

	protected void handleMuzzleFlash(Level level, LivingEntity shooter, PowderType powderType) {
		if (level instanceof ServerLevel serverLevel) {
			Vec3 particlePosition = shooter.getEyePosition();

			// Shift to the side (taking into consideration which hand the gun is in)
			Vec3 lookVector = shooter.getLookAngle();
			Vec3 sideVector = lookVector.cross(new Vec3(0, 1, 0.2));
			double sideOffset = shooter.getUsedItemHand() == InteractionHand.MAIN_HAND ? 0.5d : -0.5d;
			particlePosition = particlePosition.add(sideVector.scale(sideOffset));

			// Adjust forward position based on FOV
			double fov = 70d;
			if (shooter instanceof Player) {
				fov = GunData.playerFOV;
			}

			double fovOffset = 1.75d - (fov / 150.0d);
			particlePosition = particlePosition.add(lookVector.x * fovOffset * 0.5d, lookVector.y * fovOffset * 0.5d, lookVector.z * fovOffset * 0.5d);

			serverLevel.sendParticles(ParticleTypesRegistry.MUZZLE_FLASH_PARTICLE.get(),
					particlePosition.x, particlePosition.y, particlePosition.z,
					3,
					serverLevel.getRandom().nextGaussian() * 0.05d,
					serverLevel.getRandom().nextGaussian() * 0.025d,
					serverLevel.getRandom().nextGaussian() * 0.05d,
					0.01d);

			int smokeParticles = 3 * powderType.data.dirtiness();
			for (int j = 0; j < smokeParticles; ++j) {
				serverLevel.sendParticles(ParticleTypes.SMOKE,
						particlePosition.x, particlePosition.y, particlePosition.z,
						3,
						serverLevel.getRandom().nextGaussian() * 0.05d,
						serverLevel.getRandom().nextGaussian() * 0.025d,
						serverLevel.getRandom().nextGaussian() * 0.05d,
						0.01d);
			}
		}
	}

	protected void handleAmmoStack(ItemStack gun, ItemStack ammo, int bulletsToFire, Player player) {
		if (!player.isCreative() && ammo.getItem() instanceof BulletItem<?> bulletItem) {
			if (!bulletItem.isInfinite(ammo, gun, player)) {
				float ammoConservationChance = (float) AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.FIREARM_AMMO_CONSERVATION_CHANCE.get(), player);
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

	protected void handlePowderStack(@Nullable PowderType powderType, Player player) {
		if (!player.isCreative()) {

			if (powderType == null) {
				return;
			}

			float consumeChance = powderType.data.consumeChance();
			if (!player.level().isClientSide) {
				if (player.getRandom().nextFloat() <= consumeChance) {
					player.getInventory().setChanged(); // Resync the inventory because the client may not roll the same number
					return;
				}
			}

			powderType.powder.shrink(1);
			if (powderType.powder.isEmpty()) {
				player.getInventory().removeItem(powderType.powder);
			}
		}
	}

	public record PowderType(ItemStack powder, FlammablePowder data) {

		public PowderType(ItemStack powder) {
			this(powder, Objects.requireNonNull(powder.getItemHolder().getData(POWDER_TYPE)));
		}
	}
}