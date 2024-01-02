package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.FieldMedicHealEntitiesGoal;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.time.LocalDate;
import java.util.List;

public abstract class AbstractFieldMedicEntity extends PathfinderMob implements GrantAdvancementOnDiscovery {

	private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, false);

	private int selfHealCooldown = 0;

	AbstractFieldMedicEntity(EntityType<? extends PathfinderMob> type, Level level) {
		super(type, level);
		prepareForCombat();
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 2.5D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(2, new FieldMedicHealEntitiesGoal(this));
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(4, new MoveBackToVillageGoal(this, 0.6D, false));
		goalSelector.addGoal(4, new MoveThroughVillageGoal(this, 1.0D, false, 6, () -> true));
		goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		goalSelector.addGoal(4, new OpenDoorGoal(this, true));

		targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractMinutemanEntity.class, IronGolem.class));
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		playSound(getStepSound(), 0.15F, 1.0F);
	}

	protected abstract SoundEvent getStepSound();

	@Override
	public void aiStep() {
		super.aiStep();

		if (selfHealCooldown > 0) {
			selfHealCooldown--;
		}

		if (!level().isClientSide) {
			if (getHealth() < getMaxHealth() && selfHealCooldown == 0) {
				healSelf();
			}
		}

		checkForDiscovery(this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyInstance,
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

		return super.finalizeSpawn(level, difficultyInstance, mobSpawnType, groupData, compoundTag);
	}

	private void prepareForCombat() {
		if (!level().isClientSide) {
			goalSelector.removeGoal(meleeAttackGoal);
			setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.USED_SYRINGE.get()));
			goalSelector.addGoal(3, meleeAttackGoal);
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (amount > 0 && !(source.getEntity() instanceof MinutemanEntity) && !(source.getEntity() instanceof IronGolem)) {

			super.hurt(source, amount);

			if (source.getEntity() instanceof Player player) {
				if (player.isCreative()) {
					return true;
				}
			}

			if (source.getEntity() instanceof LivingEntity livingEntity) {
				setTarget(livingEntity);
				prepareForCombat();

				// Aggro all other minutemen in the area
				List<MinutemanEntity> nearbyMinutemen = level().getEntitiesOfClass(MinutemanEntity.class, getBoundingBox()
						.inflate(48.0D, 8.0D, 48.0D));

				for (MinutemanEntity minutemanEntity : nearbyMinutemen) {
					minutemanEntity.setTarget(livingEntity);
					minutemanEntity.setPersistentAngerTarget(livingEntity.getUUID());
				}

				// If there are no minutemen, aggro any nearby medics
				if (nearbyMinutemen.isEmpty()) {
					List<AbstractFieldMedicEntity> nearbyMedics = level().getEntitiesOfClass(AbstractFieldMedicEntity.class, getBoundingBox()
							.inflate(48.0D, 8.0D, 48.0D));

					for (AbstractFieldMedicEntity medicEntity : nearbyMedics) {
						medicEntity.setTarget(livingEntity);
					}
				}

				return true;
			}
		}

		return false;
	}

	/**
	 * Perform the healing process on the entity.
	 */
	private void healSelf() {
		selfHealCooldown = 300;
		if (getHealth() <= getMaxHealth() / 2) {
			setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.FIRST_AID_KIT.get()));
			ItemRegistry.FIRST_AID_KIT.get().setEffects(this);
		} else {
			setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.BANDAGE.get()));
			ItemRegistry.BANDAGE.get().setEffects(this);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean canHurtTarget = super.doHurtTarget(entity);
		if (canHurtTarget && entity instanceof LivingEntity livingEntity) {
			if (!getMainHandItem().is(ItemRegistry.USED_SYRINGE.get())) {
				prepareForCombat();
			}

			float randomNumber = livingEntity.getRandom().nextFloat();
			// Poison chance
			if (randomNumber <= 0.8f) {
				livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 500,
						0, false, true));

				// Hepatitis chance
				if (randomNumber <= 0.3f) {
					entity.hurt(IWDamageSources.USED_SYRINGE, 8.0F);
				}
			}
		}

		return canHurtTarget;
	}
}