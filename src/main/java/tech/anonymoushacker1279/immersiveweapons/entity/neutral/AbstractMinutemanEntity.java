package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.DefendVillageTargetGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class AbstractMinutemanEntity extends PathfinderMob implements RangedAttackMob, NeutralMob, GrantAdvancementOnDiscovery {

	private static final UniformInt tickRange = TimeUtil.rangeOfSeconds(20, 39);
	private final RangedGunAttackGoal<AbstractMinutemanEntity> rangedGunAttackGoal =
			new RangedGunAttackGoal<>(this, 1.0D, 30, 12.0F);

	private int angerTime;
	@Nullable
	private UUID targetUUID;

	AbstractMinutemanEntity(EntityType<? extends AbstractMinutemanEntity> type, Level level) {
		super(type, level);
		prepareForCombat();
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.27D)
				.add(Attributes.ARMOR, 4.0D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D, 0.35f));
		goalSelector.addGoal(4, new MoveBackToVillageGoal(this, 0.65D, false));
		goalSelector.addGoal(4, new MoveThroughVillageGoal(this, 1.0D, false, 6, () -> true));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		goalSelector.addGoal(4, new OpenDoorGoal(this, true));

		targetSelector.addGoal(1, new HurtByTargetGoal(this, MinutemanEntity.class, IronGolem.class));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, this::isAngryAt));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, true, (targetPredicate) -> !(targetPredicate instanceof Creeper)));
		targetSelector.addGoal(4, new DefendVillageTargetGoal(this));
		targetSelector.addGoal(6, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		playSound(getStepSound(), 0.15F, 1.0F);
	}

	protected abstract SoundEvent getStepSound();

	@Override
	public void aiStep() {
		super.aiStep();

		checkForDiscovery(this);
		if (!level().isClientSide) {
			updatePersistentAnger((ServerLevel) level(), true);
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(randomSource, difficulty);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.BLUNDERBUSS.get()));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance,
	                                    MobSpawnType mobSpawnType, @Nullable SpawnGroupData groupData,
	                                    @Nullable CompoundTag compoundTag) {

		populateDefaultEquipmentSlots(random, difficultyInstance);
		populateDefaultEquipmentEnchantments(random, difficultyInstance);
		prepareForCombat();
		setCanPickUpLoot(random.nextFloat() < 0.55F * difficultyInstance.getSpecialMultiplier());

		if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate date = LocalDate.now();
			int day = date.getDayOfMonth();
			int month = date.getMonth().getValue();
			if (month == 10 && day == 31 && random.nextFloat() < 0.25F) {
				setItemSlot(EquipmentSlot.HEAD,
						new ItemStack(random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));

				armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		return super.finalizeSpawn(levelAccessor, difficultyInstance, mobSpawnType, groupData, compoundTag);
	}

	/**
	 * Set the entity's combat AI.
	 */
	private void prepareForCombat() {
		if (!level().isClientSide) {
			goalSelector.removeGoal(rangedGunAttackGoal);

			if (getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
				populateDefaultEquipmentSlots(random, level().getCurrentDifficultyAt(blockPosition()));
			}

			ItemStack itemInHand = getItemInHand(ProjectileUtil.getWeaponHoldingHand(this,
					Predicate.isEqual(ItemRegistry.BLUNDERBUSS.get())));

			if (itemInHand.is(ItemRegistry.BLUNDERBUSS.get())) {
				int attackInterval = switch (level().getDifficulty()) {
					case NORMAL -> 45;
					case HARD -> 30;
					default -> 60;
				};

				rangedGunAttackGoal.setMinAttackInterval(attackInterval);
				goalSelector.addGoal(3, rangedGunAttackGoal);
			}
		}
	}

	@Override
	public boolean canAttackType(EntityType<?> type) {
		return type != EntityRegistry.MINUTEMAN_ENTITY.get() && type != EntityType.CREEPER;
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		// Fire four bullets for the blunderbuss
		for (int i = 0; i <= 4; i++) {
			BulletEntity bulletEntity = ItemRegistry.COPPER_MUSKET_BALL.get().createBullet(level(), this);
			bulletEntity.setEnchantmentEffectsFromEntity(this, velocity);

			double deltaX = target.getX() - getX();
			double deltaY = target.getY(0.1D) - bulletEntity.getY();
			double deltaZ = target.getZ() - getZ();
			double sqrtXZ = Mth.sqrt((float) (deltaX * deltaX + deltaZ * deltaZ));

			bulletEntity.setKnockback(3);
			bulletEntity.setOwner(this);

			bulletEntity.shoot(
					deltaX + GeneralUtilities.getRandomNumber(-1.0f, 1.0f),
					deltaY + sqrtXZ * 0.2D + GeneralUtilities.getRandomNumber(-0.375f, 0.375f),
					deltaZ,
					1.6F,
					18 - level().getDifficulty().getId() * 4 + GeneralUtilities.getRandomNumber(0.2f, 0.8f));
			level().addFreshEntity(bulletEntity);
		}

		playSound(SoundEventRegistry.BLUNDERBUSS_FIRE.get(), 1.0F,
				1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
	}

	/**
	 * Runs when the entity is hurt.
	 *
	 * @param source the <code>DamageSource</code> instance
	 * @param amount the damage amount
	 * @return boolean
	 */
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (amount > 0 && !(source.getEntity() instanceof MinutemanEntity) && !(source.getEntity() instanceof IronGolem)) {

			super.hurt(source, amount);

			if (source.getEntity() instanceof LivingEntity livingEntity) {
				if (source.getEntity() instanceof Player player && player.isCreative()) {
					return false;
				}

				prepareForCombat();
				setTarget(livingEntity);
				setPersistentAngerTarget(source.getEntity().getUUID());

				// Aggro all other minutemen in the area
				List<MinutemanEntity> list = level().getEntitiesOfClass(MinutemanEntity.class,
						(new AABB(blockPosition())).inflate(24.0D, 8.0D, 24.0D));

				for (MinutemanEntity minutemanEntity : list) {
					minutemanEntity.setTarget(livingEntity);
					minutemanEntity.setPersistentAngerTarget(livingEntity.getUUID());
				}

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem == ItemRegistry.BLUNDERBUSS.get().asItem();
	}

	/**
	 * Read entity NBT data.
	 *
	 * @param compound the <code>CompoundNBT</code> to read from
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

		readPersistentAngerSaveData(level(), compound);
		prepareForCombat();
	}

	/**
	 * Write entity NBT data.
	 *
	 * @param compound the <code>CompoundNBT</code> to write to
	 */
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);

		addPersistentAngerSaveData(compound);
	}

	@Override
	public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
		super.setItemSlot(slot, stack);

		if (!level().isClientSide) {
			prepareForCombat();
		}
	}

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

	/**
	 * Start the anger timer
	 */
	@Override
	public void startPersistentAngerTimer() {
		setRemainingPersistentAngerTime(tickRange.sample(random));
	}

	/**
	 * Get the remaining anger time
	 *
	 * @return int
	 */
	@Override
	public int getRemainingPersistentAngerTime() {
		return angerTime;
	}

	/**
	 * Set the remaining anger time
	 *
	 * @param time the new remaining anger time
	 */
	@Override
	public void setRemainingPersistentAngerTime(int time) {
		angerTime = time;
	}

	/**
	 * Get anger target
	 *
	 * @return UUID
	 */
	@Override
	public UUID getPersistentAngerTarget() {
		return targetUUID;
	}

	/**
	 * Set the anger target
	 *
	 * @param target the <code>UUID</code> of the target
	 */
	@Override
	public void setPersistentAngerTarget(@Nullable UUID target) {
		targetUUID = target;
	}
}