package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.FieldMedicHealEntitiesGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.HurtByTargetWithPredicateGoal;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.util.List;

public class FieldMedicEntity extends SoldierEntity {

	private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, false);
	private int selfHealCooldown = 0;

	public FieldMedicEntity(EntityType<? extends PathfinderMob> type, Level level) {
		super(type, level, List.of(MinutemanEntity.class));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 2.5D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		goalSelector.addGoal(2, new FieldMedicHealEntitiesGoal(this));
		goalSelector.addGoal(4, new MoveBackToVillageGoal(this, 0.6D, false));
		goalSelector.addGoal(4, new MoveThroughVillageGoal(this, 1.0D, false, 6, () -> true));
		goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new HurtByTargetWithPredicateGoal(this, this::canTargetEntityWhenHurt, MinutemanEntity.class, IronGolem.class)
				.setAlertOthers());
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (selfHealCooldown > 0) {
			selfHealCooldown--;
		}

		if (getHealth() < getMaxHealth() && selfHealCooldown == 0) {
			healSelf();
		}
	}

	@Override
	protected void prepareForCombat() {
		if (!level().isClientSide) {
			goalSelector.removeGoal(meleeAttackGoal);
			setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.USED_SYRINGE.get()));
			goalSelector.addGoal(3, meleeAttackGoal);
		}
	}

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
	public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
		boolean didHurt = super.hurtServer(serverLevel, source, amount);
		if (didHurt && source.getEntity() instanceof LivingEntity livingEntity) {

			double followDistance = getAttributeValue(Attributes.FOLLOW_RANGE);
			List<MinutemanEntity> nearbyMinutemen = level().getEntitiesOfClass(MinutemanEntity.class,
					AABB.unitCubeFromLowerCorner(position()).inflate(followDistance, 10.0, followDistance));

			for (MinutemanEntity minutemanEntity : nearbyMinutemen) {
				if (minutemanEntity.canTargetEntityWhenHurt(livingEntity)) {
					minutemanEntity.setTarget(livingEntity);
					minutemanEntity.setPersistentAngerTarget(livingEntity.getUUID());
				}
			}
		}

		return didHurt;
	}

	@Override
	public boolean doHurtTarget(ServerLevel serverLevel, Entity entity) {
		boolean didHurtTarget = super.doHurtTarget(serverLevel, entity);
		if (didHurtTarget && entity instanceof LivingEntity livingEntity) {
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
					entity.hurt(IWDamageSources.usedSyringe(level().registryAccess()), 8.0F);
				}
			}
		}

		return didHurtTarget;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEventRegistry.FIELD_MEDIC_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEventRegistry.FIELD_MEDIC_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEventRegistry.FIELD_MEDIC_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		playSound(SoundEventRegistry.FIELD_MEDIC_STEP.get(), 0.15F, 1.0F);
	}

	@Override
	protected Item getPeaceAccessory() {
		return ItemRegistry.MEDAL_OF_HONOR.get();
	}

	@Override
	protected Item getAggroAccessory() {
		return ItemRegistry.MEDAL_OF_DISHONOR.get();
	}

	@Override
	protected boolean canTargetPlayer(LivingEntity entity) {
		if (entity instanceof Player player) {
			if (Accessory.isAccessoryActive(player, getPeaceAccessory())) {
				return false;
			}

			return !player.isCreative() && Accessory.isAccessoryActive(player, getAggroAccessory());
		}

		return false;
	}
}