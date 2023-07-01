package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.FieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.function.Predicate;

public abstract class AbstractDyingSoldierEntity extends Monster implements RangedAttackMob, GrantAdvancementOnDiscovery {

	private final RangedGunAttackGoal<AbstractDyingSoldierEntity> aiPistolAttack =
			new RangedGunAttackGoal<>(this, 1.0D, 20, 15.0F, ItemRegistry.FLINTLOCK_PISTOL.get());
	private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		@Override
		public void stop() {
			super.stop();
			setAggressive(false);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			super.start();
			setAggressive(true);
		}
	};

	/**
	 * Constructor for AbstractDyingSoldierEntity.
	 *
	 * @param type  the <code>EntityType</code> instance
	 * @param level the <code>Level</code> the entity is in
	 */
	AbstractDyingSoldierEntity(EntityType<? extends AbstractDyingSoldierEntity> type, Level level) {
		super(type, level);
		setCombatTask();
	}

	/**
	 * Register this entity's attributes.
	 *
	 * @return AttributeSupplier.Builder
	 */
	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 5.0D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(100, new RandomLookAroundGoal(this));
		goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, false,
				6, () -> true));
		goalSelector.addGoal(6, new OpenDoorGoal(this, false));
		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Villager.class, false));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MinutemanEntity.class, true));
		targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, FieldMedicEntity.class, true));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	/**
	 * Get the mob type.
	 *
	 * @return MobType
	 */
	@Override
	public MobType getMobType() {
		return MobType.ILLAGER;
	}

	/**
	 * Handles updating while riding another entity
	 */
	@Override
	public void rideTick() {
		super.rideTick();
		if (getVehicle() instanceof PathfinderMob creatureEntity) {
			yBodyRot = creatureEntity.yBodyRot;
		}
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 *
	 * @param difficulty the <code>DifficultyInstance</code> of the world
	 */
	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(randomSource, difficulty);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.FLINTLOCK_PISTOL.get()));
	}

	/**
	 * Finalize spawn information.
	 *
	 * @param level      the <code>ServerLevelAccessor</code> the entity is in
	 * @param difficulty the <code>DifficultyInstance</code> of the world
	 * @param spawnType  the <code>MobSpawnType</code> for the entity
	 * @param groupData  the <code>SpawnGroupData</code> for the entity
	 * @param tag        the <code>CompoundTag</code> data tag for the entity
	 * @return SpawnGroupData
	 */
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
	                                    MobSpawnType spawnType, @Nullable SpawnGroupData groupData,
	                                    @Nullable CompoundTag tag) {

		groupData = super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
		populateDefaultEquipmentSlots(random, difficulty);
		populateDefaultEquipmentEnchantments(random, difficulty);
		setCombatTask();
		setCanPickUpLoot(random.nextFloat() < 0.55F * difficulty.getSpecialMultiplier());

		if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate date = LocalDate.now();
			int day = date.get(ChronoField.DAY_OF_MONTH);
			int month = date.get(ChronoField.MONTH_OF_YEAR);
			if (month == 10 && day == 31 && random.nextFloat() < 0.25F) {
				setItemSlot(EquipmentSlot.HEAD,
						new ItemStack(random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));

				armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		return groupData;
	}

	/**
	 * Set the entity's combat AI.
	 */
	private void setCombatTask() {
		if (!level().isClientSide) {
			goalSelector.removeGoal(aiAttackOnCollide);
			goalSelector.removeGoal(aiPistolAttack);
			ItemStack itemInHand = getItemInHand(ProjectileUtil.getWeaponHoldingHand(this,
					Predicate.isEqual(ItemRegistry.FLINTLOCK_PISTOL.get())));

			if (itemInHand.getItem() == ItemRegistry.FLINTLOCK_PISTOL.get()) {
				int cooldown = 20;
				if (level().getDifficulty() != Difficulty.HARD) {
					cooldown = 40;
				}

				aiPistolAttack.setAttackCooldown(cooldown);
				goalSelector.addGoal(1, aiPistolAttack);
			} else {
				goalSelector.addGoal(4, aiAttackOnCollide);
			}

		}
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 *
	 * @param target         the <code>LivingEntity</code> being targeted
	 * @param distanceFactor the distance factor
	 */
	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		BulletEntity bulletEntity = fireBullet(new ItemStack(ItemRegistry.IRON_MUSKET_BALL.get()),
				distanceFactor);

		double deltaX = target.getX() - getX();
		double deltaY = target.getY(0.1D) - bulletEntity.getY();
		double deltaZ = target.getZ() - getZ();
		double sqrtXZ = Mth.sqrt((float) (deltaX * deltaX + deltaZ * deltaZ));

		bulletEntity.setKnockback(3);
		bulletEntity.setOwner(this);

		bulletEntity.shoot(deltaX, deltaY + sqrtXZ * 0.2D, deltaZ, 1.6F,
				(float) (14 - level().getDifficulty().getId() * 4));
		playSound(SoundEventRegistry.FLINTLOCK_PISTOL_FIRE.get(), 1.0F,
				1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
		level().addFreshEntity(bulletEntity);
	}

	/**
	 * Fires a bullet.
	 *
	 * @param arrowStack     the <code>ItemStack</code> of the arrow
	 * @param distanceFactor the distance factor for firing
	 * @return BulletEntity
	 */
	private BulletEntity fireBullet(ItemStack arrowStack, float distanceFactor) {
		AbstractBulletItem bulletItem = (AbstractBulletItem) (arrowStack.getItem() instanceof AbstractArrowItem
				? arrowStack.getItem() : ItemRegistry.IRON_MUSKET_BALL.get());

		BulletEntity bulletEntity = bulletItem.createBullet(level(), this);
		bulletEntity.setEnchantmentEffectsFromEntity(this, distanceFactor);

		return bulletEntity;
	}

	/**
	 * Check if the entity can fire a projectile.
	 *
	 * @param projectileWeaponItem the <code>ProjectileWeaponItem</code> instance
	 * @return boolean
	 */
	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem == ItemRegistry.FLINTLOCK_PISTOL.get().asItem();
	}

	/**
	 * Read entity NBT data.
	 *
	 * @param compound the <code>CompoundNBT</code> to read from
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setCombatTask();
	}

	/**
	 * Set item slots.
	 *
	 * @param slot  the <code>EquipmentSlot</code> to set
	 * @param stack the <code>ItemStack</code> to set in the slot
	 */
	@Override
	public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
		super.setItemSlot(slot, stack);
		if (!level().isClientSide) {
			setCombatTask();
		}

	}

	/**
	 * Get the standing eye height of the entity.
	 *
	 * @param pose       the <code>Pose</code> instance
	 * @param dimensions the <code>EntityDimensions</code> of the entity
	 * @return float
	 */
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return 1.74F;
	}

	/**
	 * Get the Y offset of the entity.
	 *
	 * @return double
	 */
	@Override
	public double getMyRidingOffset() {
		return -0.6D;
	}

	@Override
	public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
		boolean walkTargetAboveZero = super.checkSpawnRules(pLevel, pSpawnReason);
		boolean isValidSpawn = pLevel.getBlockState(blockPosition().below()).isValidSpawn(pLevel, blockPosition(), getType());
		boolean isDarkEnough = isDarkEnoughToSpawn((ServerLevelAccessor) pLevel, blockPosition(), pLevel.getRandom());

		if (pSpawnReason == MobSpawnType.SPAWN_EGG) {
			return true;
		}

		if (pSpawnReason == MobSpawnType.NATURAL) {
			return walkTargetAboveZero && isValidSpawn && isDarkEnough;
		} else {
			return walkTargetAboveZero && isValidSpawn;
		}
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader pLevel) {
		return super.checkSpawnObstruction(pLevel) && pLevel.canSeeSky(blockPosition());
	}
}