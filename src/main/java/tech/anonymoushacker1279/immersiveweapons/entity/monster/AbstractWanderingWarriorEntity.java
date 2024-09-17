package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.FieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

import java.time.LocalDate;

public abstract class AbstractWanderingWarriorEntity extends Monster implements GrantAdvancementOnDiscovery {

	protected MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, false);

	/**
	 * Constructor for AbstractWanderingWarriorEntity.
	 *
	 * @param type  the <code>EntityType</code> instance
	 * @param level the <code>Level</code> the entity is in
	 */
	AbstractWanderingWarriorEntity(EntityType<? extends Monster> type, Level level) {
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
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ARMOR, 4.0D);
	}

	/**
	 * Register entity goals and targets.
	 */
	@Override
	protected void registerGoals() {
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(100, new RandomLookAroundGoal(this));
		goalSelector.addGoal(4, new OpenDoorGoal(this, false));
		goalSelector.addGoal(1, new FloatGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, 1,
				true, true, (targetPredicate) -> !(targetPredicate instanceof Creeper)));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MinutemanEntity.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, FieldMedicEntity.class, true));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new HurtByTargetGoal(this));
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	/**
	 * Play the step sound.
	 *
	 * @param pos   the <code>BlockPos</code> the entity is at
	 * @param state the <code>BlockState</code> of the block being stepped on
	 */
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		playSound(SoundEventRegistry.WANDERING_WARRIOR_STEP.get(), 1.0F, 1.0F);
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

		// Populate weapons if empty
		if (getItemBySlot(EquipmentSlot.MAINHAND).getItem() == Items.AIR) {
			float random = randomSource.nextFloat();
			if (random <= 0.3f) {
				setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.COBALT_SWORD.get()));
			} else if (random <= 0.5f) {
				setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
			} else {
				setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.COPPER_SWORD.get()));
			}
		}
	}

	/**
	 * Finalize spawn information.
	 *
	 * @param level      the <code>ServerLevelAccessor</code> the entity is in
	 * @param difficulty the <code>DifficultyInstance</code> of the world
	 * @param spawnType  the <code>MobSpawnType</code> for the entity
	 * @param groupData  the <code>SpawnGroupData</code> for the entity
	 * @return SpawnGroupData
	 */
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
	                                    MobSpawnType spawnType, @Nullable SpawnGroupData groupData) {

		groupData = super.finalizeSpawn(level, difficulty, spawnType, groupData);
		populateDefaultEquipmentSlots(random, difficulty);
		populateDefaultEquipmentEnchantments(level, random, difficulty);
		setCombatTask();
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

		return groupData;
	}

	/**
	 * Set the entity's combat AI.
	 */
	void setCombatTask() {
		if (!level().isClientSide) {
			goalSelector.removeGoal(meleeAttackGoal);
			if (getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.AIR) {
				populateDefaultEquipmentSlots(random, level().getCurrentDifficultyAt(blockPosition()));
			}
			goalSelector.addGoal(1, meleeAttackGoal);
		}
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