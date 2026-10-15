package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.RangedSoldierEntity;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

import java.util.EnumSet;

/// Reloads a soldier's firearm whenever it is empty and there is ammunition and powder to load, in or out of combat, so
/// it is ready at a moment's notice. It uses no movement or look flags, so the soldier keeps doing what it was doing.
public class ReloadFirearmGoal extends Goal {

	private final RangedSoldierEntity mob;
	private int reloadTicks;
	private boolean completed;

	public ReloadFirearmGoal(RangedSoldierEntity mob) {
		this.mob = mob;
		setFlags(EnumSet.noneOf(Flag.class));
	}

	@Override
	public boolean canUse() {
		return !mob.isUsingItem() && !mob.isFirearmLoaded() && mob.canReload();
	}

	@Override
	public boolean canContinueToUse() {
		return mob.isUsingItem() && !completed && !mob.getFirearm().isEmpty() && !mob.isFirearmLoaded();
	}

	@Override
	public void start() {
		completed = false;

		ItemStack gun = mob.getFirearm();
		if (gun.getItem() instanceof AbstractGunItem gunItem) {
			reloadTicks = Math.max(1, gunItem.getReloadAnimationTicks());
			mob.startUsingItem(InteractionHand.MAIN_HAND);

			if (mob.level() instanceof ServerLevel serverLevel) {
				gunItem.triggerMobReloadAnimation(serverLevel, mob, InteractionHand.MAIN_HAND, gun, reloadTicks);
			}
		}
	}

	@Override
	public void tick() {
		if (mob.getTicksUsingItem() >= reloadTicks) {
			completed = true;
			mob.stopUsingItem();
			mob.finishReload();
		}
	}

	@Override
	public void stop() {
		// Interrupted before the reload finished, so the animation shouldn't carry on
		if (!completed) {
			ItemStack gun = mob.getFirearm();
			if (gun.getItem() instanceof AbstractGunItem gunItem) {
				gunItem.cancelMobReloadAnimation(mob, InteractionHand.MAIN_HAND, gun);
			}

			if (mob.isUsingItem()) {
				mob.stopUsingItem();
			}
		}
	}
}
