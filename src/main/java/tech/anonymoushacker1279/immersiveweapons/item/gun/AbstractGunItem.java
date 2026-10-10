package tech.anonymoushacker1279.immersiveweapons.item.gun;

import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.renderer.GeoItemRenderer;
import com.geckolib.util.GeckoLibUtil;
import com.google.common.base.Suppliers;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryManager;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunAnimationPayload;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractGunItem extends Item implements GeoItem {

	/// Name of the controller (and triggerable animation) used for firing/reload animations.
	public static final String ACTIONS_CONTROLLER = "actions";
	/// Playback speeds available to the reload animation, in ascending order. See `registerControllers`.
	/// Registry paths of firearms that have a player arm animation at `player_animations/<path>_reload`.
	private static final Set<String> PLAYER_RELOAD_ANIMATIONS = Set.of("flintlock_pistol", "blunderbuss", "musket", "flare_gun", "hand_cannon", "dragons_breath_cannon");
	private static final double[] RELOAD_SPEED_STEPS = {0.25, 0.35, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0, 2.5, 3.0, 4.0};
	public static final String RELOAD_ANIMATION = "reload";
	public static final String FIRE_ANIMATION = "fire";
	/// Index in `RELOAD_SPEED_STEPS` of normal (1x) speed, used for the `fire` animation.
	private static final int NORMAL_SPEED_STEP = 4;
	/// Camera recoil is scaled down so it doesn't hide the firing animations.
	private static final float CAMERA_RECOIL_SCALE = 0.35f;
	/// Shot spread is multiplied by this while aiming.
	private static final float AIM_INACCURACY_SCALE = 0.35f;
	/// How long creative players must hold to reload, at most.
	private static final int CREATIVE_RELOAD_TICKS = 10;

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	public static final DataMapType<Item, FlammablePowder> POWDER_TYPE = DataMapType.builder(
					Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "flammable_powder"),
					Registries.ITEM, FlammablePowder.CODEC)
			.synced(FlammablePowder.CODEC, true)
			.build();

	protected static final Predicate<ItemStack> MUSKET_BALLS = (stack) -> stack.is(IWItemTagGroups.MUSKET_BALLS);
	protected static final Predicate<ItemStack> FLARES = (stack) -> stack.is(IWItemTagGroups.FLARES);
	protected static final Predicate<ItemStack> CANNONBALLS = (stack) -> stack.is(IWItemTagGroups.CANNONBALLS);
	protected static final Predicate<ItemStack> DRAGON_FIREBALLS = (stack) -> stack.is(IWItemTagGroups.DRAGON_FIREBALLS);
	protected static final Predicate<ItemStack> FLAMMABLE_POWDERS = (stack) -> stack.typeHolder().getData(POWDER_TYPE) != null;

	protected AbstractGunItem(Properties properties) {
		super(properties);
		// Required for triggered animations to be synced to clients
		GeoItem.registerSyncedAnimatable(this);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		// Trigger-only controller; the item model decides whether it is ever rendered via GeckoLib.
		// The animation name must match an entry in the item's `.animation.json` file.
		// A triggered animation cannot carry a speed, and cooldown reductions (accessories, Rapid Fire) are only known
		// to the shooter, so one controller per speed step is registered and the server picks the right one.
		for (int i = 0; i < RELOAD_SPEED_STEPS.length; i++) {
			controllers.add(new AnimationController<AbstractGunItem>(ACTIONS_CONTROLLER + "_" + i, state -> PlayState.STOP)
					.setAnimationSpeed(RELOAD_SPEED_STEPS[i])
					.triggerableAnim(RELOAD_ANIMATION, RawAnimation.begin().thenPlay(RELOAD_ANIMATION))
					.triggerableAnim(FIRE_ANIMATION, RawAnimation.begin().thenPlay(FIRE_ANIMATION)));
		}
	}

	/// Create the GeckoLib renderer for this item. Override to customize rendering (e.g., hiding optional bones).
	protected GeoItemRenderer<?> createGeoItemRenderer() {
		return new GeoItemRenderer<>(this);
	}

	/// The length of the `reload` animation in ticks, as authored in the item's `.animation.json` file. Animations
	/// are authored to the cooldown, capped at 10 seconds; the playback speed is scaled to the actual cooldown.
	public int getReloadAnimationTicks() {
		return Math.min(getCooldown(), 200);
	}

	/// Get the cooldown in ticks after firing, with accessory and Rapid Fire reductions applied.
	public int getEffectiveCooldown(ItemStack gun, Player player) {
		HolderGetter<Enchantment> enchantmentGetter = player.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
		Optional<Reference<Enchantment>> rapidFire = enchantmentGetter.get(IWEnchantments.RAPID_FIRE);

		// Reduce cooldown in certain conditions
		float reductionFactor = (float) AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.FIREARM_RELOAD_SPEED.get(), player);

		if (rapidFire.isPresent()) {
			int rapidFireLevel = gun.getEnchantmentLevel(rapidFire.get());
			reductionFactor += (0.05f * rapidFireLevel);
		}

		return (int) (getCooldown() * (1f - reductionFactor));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return geoCache;
	}

	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoRenderProvider() {
			private final Supplier<GeoItemRenderer<?>> renderer =
					Suppliers.memoize(AbstractGunItem.this::createGeoItemRenderer);

			@Override
			public GeoItemRenderer<?> getGeoItemRenderer() {
				return renderer.get();
			}
		});
	}

	/// Whether the firearm has ammunition loaded into it.
	public boolean isLoaded(ItemStack gun) {
		return gun.has(DataComponentTypeRegistry.LOADED_AMMUNITION.get());
	}

	/// How long in ticks the player must hold to reload, with accessory and Rapid Fire reductions applied.
	public int getReloadTicks(ItemStack gun, Player player) {
		int ticks = Math.max(1, getEffectiveCooldown(gun, player));
		return player.isCreative() ? Math.min(ticks, CREATIVE_RELOAD_TICKS) : ticks;
	}

	/// The index of the slowest speed step that still finishes the reload animation within the reload time.
	private int getReloadSpeedStep(double neededSpeed) {
		for (int i = 0; i < RELOAD_SPEED_STEPS.length; i++) {
			if (RELOAD_SPEED_STEPS[i] >= neededSpeed) {
				return i;
			}
		}

		return RELOAD_SPEED_STEPS.length - 1;
	}

	/// Play the reload animation for everyone tracking the player. Server-side only.
	protected void triggerReloadAnimation(ServerLevel level, Player player, InteractionHand hand, ItemStack gun) {
		double neededSpeed = (double) getReloadAnimationTicks() / getReloadTicks(gun, player);
		int step = getReloadSpeedStep(neededSpeed);

		triggerAnim(player, GeoItem.getOrAssignId(gun, level), ACTIONS_CONTROLLER + "_" + step, RELOAD_ANIMATION);
		sendPlayerAnimation(player, hand, "_reload", (float) Math.clamp(neededSpeed, 0.1d, 4.0d), false);
	}

	/// Play the fire (recoil) animation for everyone tracking the player. Server-side only.
	protected void triggerFireAnimation(ServerLevel level, Player player, InteractionHand hand, ItemStack gun) {
		triggerAnim(player, GeoItem.getOrAssignId(gun, level), ACTIONS_CONTROLLER + "_" + NORMAL_SPEED_STEP, FIRE_ANIMATION);
		sendPlayerAnimation(player, hand, "_fire", 1.0f, false);
	}

	/// Stop a reload animation that did not complete (the item was switched or the reload released early).
	protected void cancelReloadAnimation(ServerPlayer player, ItemStack gun) {
		long id = GeoItem.getId(gun);
		double neededSpeed = (double) getReloadAnimationTicks() / getReloadTicks(gun, player);
		stopTriggeredAnim(player, id, ACTIONS_CONTROLLER + "_" + getReloadSpeedStep(neededSpeed), RELOAD_ANIMATION);

		sendPlayerAnimation(player, player.getUsedItemHand(), "_reload", 1.0f, true);
	}

	/// Send a PlayerAnimator animation (the arms) to everyone tracking the player, if this firearm has one.
	private void sendPlayerAnimation(Player player, InteractionHand hand, String suffix, float speed, boolean stop) {
		Identifier itemId = BuiltInRegistries.ITEM.getKey(this);
		if (!PLAYER_RELOAD_ANIMATIONS.contains(itemId.getPath())) {
			return;
		}

		Identifier animation = Identifier.fromNamespaceAndPath(itemId.getNamespace(), itemId.getPath() + suffix);

		// Animations are authored for the gun in the left arm, so mirror them when it's in the right
		HumanoidArm holdingArm = hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
		boolean mirrored = holdingArm == HumanoidArm.RIGHT;

		PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, new GunAnimationPayload(player.getId(), animation, speed, mirrored, stop));
	}

	protected static ItemStack getHeldPredicate(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
		if (predicate.test(livingEntity.getItemInHand(InteractionHand.OFF_HAND))) {
			return livingEntity.getItemInHand(InteractionHand.OFF_HAND);
		} else {
			return predicate.test(livingEntity.getItemInHand(InteractionHand.MAIN_HAND)) ?
					livingEntity.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
		}
	}

	/// Releasing a reload early cancels it (see `onStopUsing`). Releasing after the full reload time loads the firearm.
	@Override
	public boolean releaseUsing(ItemStack gun, Level level, LivingEntity livingEntity, int timeLeft) {
		// Mobs handle their own firing
		if (!(livingEntity instanceof Player player)) {
			return true;
		}

		int heldTicks = getUseDuration(gun, player) - timeLeft;
		if (heldTicks < getReloadTicks(gun, player)) {
			return false;
		}

		if (!level.isClientSide()) {
			return completeReload(player, gun);
		}

		return true;
	}

	/// Stopping use for any reason (switching items, releasing, etc.) without a loaded firearm cancels the reload.
	@Override
	public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
		if (entity instanceof ServerPlayer player && !isLoaded(stack)) {
			cancelReloadAnimation(player, stack);
		}
	}

	/// Consume ammunition and powder from the player's inventory and load them into the firearm. Server-side only.
	///
	/// @return true if the firearm was loaded
	protected boolean completeReload(Player player, ItemStack gun) {
		boolean isCreative = player.isCreative();
		ItemStack ammo = findAmmo(gun, player);
		PowderType powder = findPowder(gun, player);

		if (!isCreative && (ammo.isEmpty() || powder == null)) {
			return false;
		}

		// If the ammunition is empty, set it to the default. This happens in creative mode with no ammunition.
		if (ammo.isEmpty()) {
			ammo = new ItemStack(defaultAmmo());
		}

		if (powder == null) {
			powder = new PowderType(new ItemStack(defaultPowder()));
		}

		int bulletsToLoad = isCreative ? getMaxBulletsToFire() : getBulletsToFire(ammo);

		// Copy before consuming, the inventory stack is shrunk below
		gun.set(DataComponentTypeRegistry.LOADED_AMMUNITION.get(),
				new LoadedAmmunition(ammo.copyWithCount(bulletsToLoad), powder.powder().copyWithCount(1)));

		handleAmmoStack(gun, ammo, bulletsToLoad, player);
		handlePowderStack(powder, player);

		return true;
	}

	/// Fire the loaded ammunition.
	public void fire(Level level, Player player, InteractionHand hand, ItemStack gun) {
		LoadedAmmunition loaded = gun.get(DataComponentTypeRegistry.LOADED_AMMUNITION.get());
		if (loaded == null) {
			return;
		}

		gun.remove(DataComponentTypeRegistry.LOADED_AMMUNITION.get());

		if (level.isClientSide()) {
			// Handle recoil
			player.setYRot(player.getYRot() + (getMaxYRecoil() + level.getRandom().nextFloat() * 10f) * (level.getRandom().nextBoolean() ? 1 : -1) * CAMERA_RECOIL_SCALE);
			player.setXRot(player.getXRot() + (getMaxXRecoil() + level.getRandom().nextFloat() * 0.5f) * CAMERA_RECOIL_SCALE);
			return;
		}

		ItemStack ammo = loaded.ammo().copy();
		PowderType powder = loaded.powder().typeHolder().getData(POWDER_TYPE) != null
				? new PowderType(loaded.powder().copy())
				: new PowderType(new ItemStack(defaultPowder()));
		int bulletsToFire = Math.max(1, ammo.getCount());

		// Roll for misfire
		boolean misfire = false;
		if (ammo.getItem() instanceof BulletItem<?> bullet) {
			if (player.getRandom().nextFloat() <= bullet.misfireChance) {
				misfire = true;
			}

			// High misfire chance when underwater
			if (player.isUnderWater() && player.getRandom().nextFloat() <= 0.9f) {
				misfire = true;
			}
		}

		if (misfire) {
			level.playSound(null,
					player.getX(),
					player.getY(),
					player.getZ(),
					getMisfireSound(),
					SoundSource.PLAYERS,
					1.0F,
					1.0F + (-0.6f + player.getRandom().nextFloat() * 0.2f));

			if (!player.isCreative()) {
				gun.hurtAndBreak(5, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
			}

			return;
		}

		BulletItem<?> bulletItem = (BulletItem<?>) (ammo.getItem() instanceof BulletItem<?> ? ammo.getItem() : defaultAmmo());

		for (int i = 0; i < bulletsToFire; ++i) {
			BulletEntity bulletEntity;

			if (MUSKET_BALLS.test(ammo)) {
				bulletEntity = bulletItem.createBullet(level, player, gun);
			} else if (FLARES.test(ammo)) {
				bulletEntity = bulletItem.createFlare(level, player, gun);
			} else if (CANNONBALLS.test(ammo)) {
				bulletEntity = bulletItem.createCannonball(level, player, gun);
			} else {
				bulletEntity = bulletItem.createDragonFireball(level, player, gun);
			}

			setupFire(player, bulletEntity, gun, ammo, powder);
			level.addFreshEntity(bulletEntity);
		}

		level.playSound(null,
				player.getX(),
				player.getY(),
				player.getZ(),
				getFireSound(),
				SoundSource.PLAYERS,
				1.0F,
				1.0F + (-0.3f + player.getRandom().nextFloat() * 0.5f));

		if (level instanceof ServerLevel serverLevel) {
			triggerFireAnimation(serverLevel, player, hand, gun);
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

	/// Fallback if the item is used through vanilla means. Normally the fire key calls `triggerPressed` instead.
	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		return triggerPressed(level, player, hand);
	}

	/// The hand holding the firearm the player is operating, if any. The main hand takes priority; the offhand only
	/// counts when the main hand is empty.
	@Nullable
	public static InteractionHand getGunHand(Player player) {
		if (player.getMainHandItem().getItem() instanceof AbstractGunItem) {
			return InteractionHand.MAIN_HAND;
		}

		if (player.getMainHandItem().isEmpty() && player.getOffhandItem().getItem() instanceof AbstractGunItem) {
			return InteractionHand.OFF_HAND;
		}

		return null;
	}

	/// Fires if loaded. Otherwise, starts a reload which must be held until it completes.
	public InteractionResult triggerPressed(Level level, Player player, InteractionHand hand) {
		ItemStack gun = player.getItemInHand(hand);

		if (isLoaded(gun)) {
			fire(level, player, hand, gun);
			return InteractionResult.CONSUME;
		}

		boolean canReload = player.isCreative() || (!findAmmo(gun, player).isEmpty() && findPowder(gun, player) != null);
		if (!canReload) {
			return InteractionResult.FAIL;
		}

		player.startUsingItem(hand);

		if (level instanceof ServerLevel serverLevel) {
			triggerReloadAnimation(serverLevel, player, hand, gun);
		}

		return InteractionResult.CONSUME;
	}

	/// Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
	///
	/// @return Predicate extending ItemStack
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return MUSKET_BALLS;
	}

	/// Get the default ammunition.
	///
	/// @return Item
	public Item defaultAmmo() {
		return ItemRegistry.IRON_MUSKET_BALL.get();
	}

	/// Get the default powder.
	///
	/// @return Item
	public Item defaultPowder() {
		return Items.GUNPOWDER;
	}

	@Override
	public int getUseDuration(ItemStack pStack, LivingEntity livingEntity) {
		return Integer.MAX_VALUE;
	}

	/// Get the misfire sound.
	///
	/// @return SoundEvent
	SoundEvent getMisfireSound() {
		return SoundEventRegistry.FLINTLOCK_PISTOL_MISFIRE.get();
	}

	/// Get the fire sound.
	///
	/// @return SoundEvent
	public SoundEvent getFireSound() {
		return SoundEventRegistry.FLINTLOCK_PISTOL_FIRE.get();
	}

	/// Get the maximum number of bullets that can be fired at once.
	///
	/// @return int
	public int getMaxBulletsToFire() {
		return 1;
	}

	/// Get the number of bullets to fire.
	///
	/// @param itemStack the ammunition `ItemStack`
	/// @return int
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

		// Handle enchantments
		HolderGetter<Enchantment> enchantmentGetter = shooter.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
		Optional<Reference<Enchantment>> firepower = enchantmentGetter.get(IWEnchantments.FIREPOWER);
		Optional<Reference<Enchantment>> scorchShot = enchantmentGetter.get(IWEnchantments.SCORCH_SHOT);

		int enchantmentLevel;
		if (firepower.isPresent()) {
			enchantmentLevel = gun.getEnchantmentLevel(firepower.get());
			if (enchantmentLevel > 0) {
				bullet.setBaseDamage(bullet.baseDamage + (double) enchantmentLevel * 0.5D + 0.5D);
			}
		}

		if (scorchShot.isPresent()) {
			enchantmentLevel = gun.getEnchantmentLevel(scorchShot.get());
			if (enchantmentLevel > 0) {
				// 100 ticks (5 seconds) per level
				bullet.igniteForSeconds(enchantmentLevel * 5);
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
				getInaccuracy() * (shooter instanceof Player player && GunAimTracker.isAiming(player) ? AIM_INACCURACY_SCALE : 1.0f));
	}

	protected void handleMuzzleFlash(Level level, LivingEntity shooter, PowderType powderType) {
		if (level instanceof ServerLevel serverLevel) {
			Vec3 particlePosition = shooter.getEyePosition();

			// Shift to the side (taking into consideration which hand the gun is in)
			Vec3 lookVector = shooter.getLookAngle();
			Vec3 sideVector = lookVector.cross(new Vec3(0, 1, 0.2));
			double sideOffset = shooter.getUsedItemHand() == InteractionHand.MAIN_HAND ? 0.5d : -0.5d;
			particlePosition = particlePosition.add(sideVector.scale(sideOffset));

			double fov = 70d;

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
				if (!player.level().isClientSide()) {
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
			if (!player.level().isClientSide()) {
				// Roll to see if the powder is consumed. If not, skip consuming it.
				if (player.getRandom().nextFloat() > consumeChance) {
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
			this(powder, Objects.requireNonNull(powder.typeHolder().getData(POWDER_TYPE)));
		}
	}
}