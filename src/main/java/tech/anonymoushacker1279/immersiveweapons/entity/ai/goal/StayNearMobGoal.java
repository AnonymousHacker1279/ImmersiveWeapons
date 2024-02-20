package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class StayNearMobGoal extends Goal {

	private final PathfinderMob mob;
	private final Mob mobToStayNear;
	private final double maxDistance;
	private final PathNavigation navigation;

	public StayNearMobGoal(PathfinderMob mob, Mob mobToStayNear, double maxDistance) {
		this.mob = mob;
		this.mobToStayNear = mobToStayNear;
		this.maxDistance = maxDistance;
		this.navigation = mob.getNavigation();
	}

	@Override
	public boolean canUse() {
		return mob.distanceToSqr(mobToStayNear) > maxDistance * maxDistance;
	}

	@Override
	public void start() {
		Vec3 posTowards = DefaultRandomPos.getPosTowards(mob, 16, 7, mobToStayNear.position(), ((float) Math.PI / 2F));
		if (posTowards != null) {
			navigation.moveTo(navigation.createPath(posTowards.x, posTowards.y, posTowards.z, 0), 1.0D);
		}
	}
}