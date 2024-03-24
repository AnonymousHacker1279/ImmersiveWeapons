package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gun.*;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem.PowderType;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;

import java.util.List;

public abstract class RangedSoldierEntity extends SoldierEntity implements RangedAttackMob {

	private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.1D, false);

	protected RangedSoldierEntity(EntityType<? extends PathfinderMob> entityType, Level level, List<Class<? extends Entity>> ignoresDamageFrom) {
		super(entityType, level, ignoresDamageFrom);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(randomSource, difficulty);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(getDefaultGunItem()));
	}

	public void prepareForCombat() {
		if (!level().isClientSide) {
			goalSelector.removeGoal(getRangedGunAttackGoal());
			goalSelector.removeGoal(meleeAttackGoal);

			if (getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
				populateDefaultEquipmentSlots(random, level().getCurrentDifficultyAt(blockPosition()));
			}

			ItemStack itemInHand = getItemInHand(InteractionHand.MAIN_HAND);

			if (itemInHand.is(getGunItem())) {
				getRangedGunAttackGoal().setMinAttackInterval(getAttackInterval(level().getDifficulty()));
				goalSelector.addGoal(1, getRangedGunAttackGoal());
			} else {
				goalSelector.addGoal(1, meleeAttackGoal);
			}
		}
	}

	protected ItemStack getGun() {
		ItemStack mainHandItem = getItemBySlot(EquipmentSlot.MAINHAND);
		if (mainHandItem.getItem() instanceof AbstractGunItem) {
			return mainHandItem;
		} else {
			return new ItemStack(getDefaultGunItem());
		}
	}

	protected AbstractGunItem getGunItem() {
		return (AbstractGunItem) getGun().getItem();
	}

	protected BulletEntity createBullet(Level level, LivingEntity livingEntity) {
		if (getGunItem() instanceof FlareGunItem) {
			return ItemRegistry.FLARE.get().createFlare(level, livingEntity);
		} else if (getGunItem() instanceof HandCannonItem) {
			return ItemRegistry.CANNONBALL.get().createCannonball(level, livingEntity);
		} else {
			return getDefaultBulletItem().createBullet(level, livingEntity);
		}
	}

	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem == getGunItem().asItem();
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		lookAt(target, 30.0F, 30.0F);

		for (int i = 0; i <= getGunItem().getMaxBulletsToFire(); i++) {
			BulletEntity bulletEntity = createBullet(level(), this);
			getGunItem().setupFire(this, bulletEntity, getGun(), null, getPowderType());
			level().addFreshEntity(bulletEntity);
		}

		playSound(getGunItem().getFireSound(), 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
	}

	protected int getAttackInterval(Difficulty difficulty) {
		return getGunItem().getCooldown() + getAttackIntervalModifier(difficulty);
	}

	protected abstract int getAttackIntervalModifier(Difficulty difficulty);

	protected abstract AbstractGunItem getDefaultGunItem();

	protected abstract BulletItem<?> getDefaultBulletItem();

	protected PowderType getPowderType() {
		return AbstractGunItem.getPowderFromItem(ItemRegistry.BLACKPOWDER.get());
	}

	protected abstract RangedGunAttackGoal<? extends SoldierEntity> getRangedGunAttackGoal();
}