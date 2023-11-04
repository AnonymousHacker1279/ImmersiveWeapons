package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

import java.time.LocalDate;
import java.util.function.Predicate;

public abstract class AbstractDyingSoldierEntity extends Monster implements RangedAttackMob, GrantAdvancementOnDiscovery {

	private final RangedGunAttackGoal<AbstractDyingSoldierEntity> rangedGunAttackGoal =
			new RangedGunAttackGoal<>(this, 1.0D, 20, 18.0F);

	/**
	 * Constructor for AbstractDyingSoldierEntity.
	 *
	 * @param type  the <code>EntityType</code> instance
	 * @param level the <code>Level</code> the entity is in
	 */
	AbstractDyingSoldierEntity(EntityType<? extends AbstractDyingSoldierEntity> type, Level level) {
		super(type, level);
		prepareForCombat();
	}

	/**
	 * Register this entity's attributes.
	 *
	 * @return AttributeSupplier.Builder
	 */
	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.27D)
				.add(Attributes.ARMOR, 5.0D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D, 0.35f));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, false,
				6, () -> true));
		goalSelector.addGoal(6, new OpenDoorGoal(this, false));

		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MinutemanEntity.class, true));
		targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, FieldMedicEntity.class, true));
		targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Villager.class, false));
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(randomSource, difficulty);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.FLINTLOCK_PISTOL.get()));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
	                                    MobSpawnType spawnType, @Nullable SpawnGroupData groupData,
	                                    @Nullable CompoundTag tag) {

		populateDefaultEquipmentSlots(random, difficulty);
		populateDefaultEquipmentEnchantments(random, difficulty);
		prepareForCombat();
		setCanPickUpLoot(random.nextFloat() < 0.55F * difficulty.getSpecialMultiplier());

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

		return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
	}

	private void prepareForCombat() {
		if (!level().isClientSide) {
			goalSelector.removeGoal(rangedGunAttackGoal);

			if (getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
				populateDefaultEquipmentSlots(random, level().getCurrentDifficultyAt(blockPosition()));
			}

			ItemStack itemInHand = getItemInHand(ProjectileUtil.getWeaponHoldingHand(this,
					Predicate.isEqual(ItemRegistry.FLINTLOCK_PISTOL.get())));

			if (itemInHand.is(ItemRegistry.FLINTLOCK_PISTOL.get())) {
				int attackInterval = switch (level().getDifficulty()) {
					case NORMAL -> 40;
					case HARD -> 20;
					default -> 50;
				};

				rangedGunAttackGoal.setMinAttackInterval(attackInterval);
				goalSelector.addGoal(1, rangedGunAttackGoal);
			}
		}
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		BulletEntity bulletEntity = ItemRegistry.IRON_MUSKET_BALL.get().createBullet(level(), this);

		double deltaX = target.getX() - getX();
		double deltaY = target.getY(0.1D) - bulletEntity.getY();
		double deltaZ = target.getZ() - getZ();
		double sqrtXZ = Mth.sqrt((float) (deltaX * deltaX + deltaZ * deltaZ));

		bulletEntity.setOwner(this);

		bulletEntity.shoot(
				deltaX,
				deltaY + sqrtXZ * 0.2D,
				deltaZ, 1.6F,
				(float) (14 - level().getDifficulty().getId() * 4));

		playSound(SoundEventRegistry.FLINTLOCK_PISTOL_FIRE.get(), 1.0F,
				1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));

		level().addFreshEntity(bulletEntity);
	}

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

		prepareForCombat();
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
			prepareForCombat();
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