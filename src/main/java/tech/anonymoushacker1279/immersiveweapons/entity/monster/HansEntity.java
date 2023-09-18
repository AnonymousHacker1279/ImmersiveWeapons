package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
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
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class HansEntity extends AbstractWanderingWarriorEntity {

	protected boolean isImmuneToProjectiles = true;

	public HansEntity(EntityType<? extends HansEntity> entityType, Level level) {
		super(entityType, level);
	}

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
	@NotNull
	protected SoundEvent getAmbientSound() {
		return SoundEventRegistry.WANDERING_WARRIOR_AMBIENT.get();
	}

	/**
	 * Get the ambient sound interval.
	 *
	 * @return int
	 */
	@Override
	public int getAmbientSoundInterval() {
		return getRandom().nextIntBetweenInclusive(240, 1600);
	}

	/**
	 * Get the hurt sound.
	 *
	 * @param damageSource the <code>DamageSource</code> instance
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEventRegistry.WANDERING_WARRIOR_HURT.get();
	}

	/**
	 * Get the death sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEventRegistry.WANDERING_WARRIOR_DEATH.get();
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 *
	 * @param difficulty the <code>DifficultyInstance</code> of the world
	 */
	@Override
	public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
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
	public boolean hurt(DamageSource source, float amount) {
		if (amount > 0 && source.getEntity() instanceof Player
				|| source.getEntity() instanceof Mob
				|| source.getEntity() instanceof PathfinderMob) {

			if (source.isCreativePlayer()) {
				super.hurt(source, amount);
				return true;
			}

			if (isImmuneToProjectiles) {
				if (source.is(DamageTypeTags.IS_PROJECTILE) || source.getDirectEntity() instanceof AbstractArrow) {
					setTarget((LivingEntity) source.getEntity());
					setCombatTask();
					heal(amount);
					return false;
				}
			}

			setTarget((LivingEntity) source.getEntity());
			setCombatTask();
			return super.hurt(source, amount);
		}

		if (source == damageSources().genericKill()) {
			super.hurt(source, amount);
			return true;
		}

		return false;
	}
}