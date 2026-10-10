package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.RangedSoldierEntity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

/// Walks to ammunition or powder lying on the ground when the soldier is running low. The soldier picks the items up
/// by itself once it is close enough, the same way any mob collects items.
public class PickUpAmmunitionGoal extends Goal {

	private final RangedSoldierEntity mob;
	private final double speedModifier;
	private final int searchRange;
	private ItemEntity wanted;

	public PickUpAmmunitionGoal(RangedSoldierEntity mob, double speedModifier, int searchRange) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.searchRange = searchRange;
		setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		// Not while fighting, and the search is spread out over time
		if (mob.getTarget() != null || !mob.wantsMoreSupply() || mob.getRandom().nextInt(reducedTickDelay(10)) != 0) {
			return false;
		}

		if (!(mob.level() instanceof ServerLevel serverLevel)) {
			return false;
		}

		List<ItemEntity> items = mob.level().getEntitiesOfClass(ItemEntity.class,
				mob.getBoundingBox().inflate(searchRange, 4.0D, searchRange),
				item -> item.isAlive() && !item.hasPickUpDelay() && mob.wantsToPickUp(serverLevel, item.getItem()));

		wanted = items.stream().min(Comparator.comparingDouble(mob::distanceToSqr)).orElse(null);
		return wanted != null;
	}

	@Override
	public boolean canContinueToUse() {
		return wanted != null && wanted.isAlive() && mob.getTarget() == null && !mob.getNavigation().isDone();
	}

	@Override
	public void start() {
		mob.getNavigation().moveTo(wanted, speedModifier);
	}

	@Override
	public void stop() {
		wanted = null;
		mob.getNavigation().stop();
	}
}
