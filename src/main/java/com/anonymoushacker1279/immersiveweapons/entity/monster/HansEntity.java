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

	public HansEntity(EntityType<? extends HansEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_AMBIENT.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return GeneralUtilities.getRandomNumber(240, 1600);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DeferredRegistryHandler.WANDERING_WARRIOR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_DEATH.get();
	}

	@Override
	protected SoundEvent getStepSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_STEP.get();
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.35D)
				.add(Attributes.ARMOR, 20.0D);
	}

	@Override
	public void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET));
		this.setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (amount > 0 && source.getEntity() instanceof PlayerEntity || source.getEntity() instanceof MobEntity || source.getEntity() instanceof CreatureEntity) {
			if (source.isCreativePlayer()) {
				super.hurt(source, amount);
				return false;
			}
			if (source.isProjectile() || source.getDirectEntity() instanceof AbstractArrowEntity) {
				this.setTarget((LivingEntity) source.getEntity());
				this.setCombatTask();
				this.heal(amount);
				return false;
			}
			this.setTarget((LivingEntity) source.getEntity());
			this.setCombatTask();
			super.hurt(source, amount);
		}
		return false;
	}
}