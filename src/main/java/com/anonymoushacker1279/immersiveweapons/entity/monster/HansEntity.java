package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class HansEntity extends AbstractWanderingWarriorEntity {

	/**
	 * Constructor for HansEntity.
	 * @param entityType the <code>EntityType</code> instance
	 * @param world the <code>World</code> the entity is in
	 */
	public HansEntity(EntityType<? extends HansEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * Get the ambient sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_AMBIENT.get();
	}

	/**
	 * Get the ambient sound interval.
	 * @return int
	 */
	@Override
	public int getAmbientSoundInterval() {
		return GeneralUtilities.getRandomNumber(240, 1600);
	}

	/**
	 * Get the hurt sound.
	 * @param damageSourceIn the <code>DamageSource</code> instance
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DeferredRegistryHandler.WANDERING_WARRIOR_HURT.get();
	}

	/**
	 * Get the death sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_DEATH.get();
	}

	/**
	 * Get the step sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getStepSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_STEP.get();
	}

	/**
	 * Register this entity's attributes.
	 * @return AttributeModifierMap.MutableAttribute
	 */
	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.35D)
				.add(Attributes.ARMOR, 20.0D);
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 * @param difficulty the <code>DifficultyInstance</code> of the world
	 */
	@Override
	public void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET));
		setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
	}

	/**
	 * Runs when the entity is hurt.
	 * @param source the <code>DamageSource</code> instance
	 * @param amount the damage amount
	 * @return boolean
	 */
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (amount > 0 && source.getEntity() instanceof PlayerEntity || source.getEntity() instanceof MobEntity || source.getEntity() instanceof CreatureEntity) {
			if (source.isCreativePlayer()) {
				super.hurt(source, amount);
				return false;
			}
			if (source.isProjectile() || source.getDirectEntity() instanceof AbstractArrowEntity) {
				setTarget((LivingEntity) source.getEntity());
				setCombatTask();
				heal(amount);
				return false;
			}
			setTarget((LivingEntity) source.getEntity());
			setCombatTask();
			super.hurt(source, amount);
		}
		return false;
	}
}