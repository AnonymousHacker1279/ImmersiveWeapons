package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class DefendVillageTargetGoal extends TargetGoal {

	private final TargetingConditions distancePredicate = TargetingConditions.forCombat().range(64.0D);

	public DefendVillageTargetGoal(Mob mobEntity) {
		super(mobEntity, false, true);
		setFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	@Override
	public boolean canUse() {
		if (mob.level() instanceof ServerLevel serverLevel) {
			AABB aabb = mob.getBoundingBox().inflate(10.0D, 8.0D, 10.0D);
			List<Villager> nearbyVillagers = serverLevel.getNearbyEntities(Villager.class, distancePredicate, mob, aabb);
			List<Player> nearbyPlayers = serverLevel.getNearbyPlayers(distancePredicate, mob, aabb);

			for (Villager villagerEntity : nearbyVillagers) {
				for (Player playerEntity : nearbyPlayers) {
					int i = villagerEntity.getPlayerReputation(playerEntity);
					if (i <= -100) {
						targetMob = playerEntity;
					}
				}
			}

			if (targetMob == null) {
				return false;
			} else {
				return !(targetMob instanceof Player player) || !player.isSpectator() && !player.isCreative();
			}
		}

		return false;
	}

	@Override
	public void start() {
		mob.setTarget(targetMob);
		super.start();
	}
}