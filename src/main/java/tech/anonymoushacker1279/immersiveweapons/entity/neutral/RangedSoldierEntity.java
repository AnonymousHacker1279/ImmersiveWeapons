package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

import java.util.List;
import java.util.function.Predicate;

public abstract class RangedSoldierEntity extends SoldierEntity implements RangedAttackMob {

	protected RangedSoldierEntity(EntityType<? extends PathfinderMob> entityType, Level level, List<Class<? extends Entity>> ignoresDamageFrom) {
		super(entityType, level, ignoresDamageFrom);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(randomSource, difficulty);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(getGunItem()));
	}

	public void prepareForCombat() {
		if (!level().isClientSide) {
			goalSelector.removeGoal(getRangedGunAttackGoal());

			if (getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
				populateDefaultEquipmentSlots(random, level().getCurrentDifficultyAt(blockPosition()));
			}

			ItemStack itemInHand = getItemInHand(ProjectileUtil.getWeaponHoldingHand(this,
					Predicate.isEqual(getGunItem())));

			if (itemInHand.is(getGunItem())) {
				getRangedGunAttackGoal().setMinAttackInterval(getAttackInterval(level().getDifficulty()));
				goalSelector.addGoal(1, getRangedGunAttackGoal());
			}
		}
	}

	protected ItemStack getGun() {
		return getItemBySlot(EquipmentSlot.MAINHAND);
	}

	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
		return projectileWeaponItem == getGunItem().asItem();
	}

	protected abstract int getAttackInterval(Difficulty difficulty);

	protected abstract AbstractGunItem getGunItem();

	protected abstract RangedGunAttackGoal<? extends SoldierEntity> getRangedGunAttackGoal();
}