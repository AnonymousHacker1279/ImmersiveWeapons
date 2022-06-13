package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class HansEntity extends AbstractWanderingWarriorEntity {

	/**
	 * Constructor for HansEntity.
	 *
	 * @param entityType the <code>EntityType</code> instance
	 * @param world      the <code>World</code> the entity is in
	 */
	public HansEntity(EntityType<? extends HansEntity> entityType, Level world) {
		super(entityType, world);
	}

	/**
	 * Register this entity's attributes.
	 *
	 * @return AttributeModifierMap.MutableAttribute
	 */
	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.35D)
				.add(Attributes.ARMOR, 20.0D);
	}

	/**
	 * Get the ambient sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_AMBIENT.get();
	}

	/**
	 * Get the ambient sound interval.
	 *
	 * @return int
	 */
	@Override
	public int getAmbientSoundInterval() {
		return GeneralUtilities.getRandomNumber(240, 1600);
	}

	/**
	 * Get the hurt sound.
	 *
	 * @param damageSourceIn the <code>DamageSource</code> instance
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
		return DeferredRegistryHandler.WANDERING_WARRIOR_HURT.get();
	}

	/**
	 * Get the death sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_DEATH.get();
	}

	/**
	 * Get the step sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getStepSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_STEP.get();
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 *
	 * @param difficulty the <code>DifficultyInstance</code> of the world
	 */
	@Override
	public void populateDefaultEquipmentSlots(@NotNull DifficultyInstance difficulty) {
		setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
		setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
	}

	/**
	 * Runs when the entity is hurt.
	 *
	 * @param source the <code>DamageSource</code> instance
	 * @param amount the damage amount
	 * @return boolean
	 */
	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		if (amount > 0 && source.getEntity() instanceof Player || source.getEntity() instanceof Mob
				|| source.getEntity() instanceof PathfinderMob) {

			if (source.isCreativePlayer()) {
				super.hurt(source, amount);
				return false;
			}
			if (source.isProjectile() || source.getDirectEntity() instanceof AbstractArrow) {
				setTarget((LivingEntity) source.getEntity());
				setCombatTask();
				heal(amount);
				return false;
			}
			setTarget((LivingEntity) source.getEntity());
			setCombatTask();
			super.hurt(source, amount);
		}
		if (source == DamageSource.OUT_OF_WORLD) {
			super.hurt(source, amount);
			return true;
		}
		return false;
	}
}