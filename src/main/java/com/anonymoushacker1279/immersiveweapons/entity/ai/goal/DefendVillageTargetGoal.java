package com.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
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

	private final Mob mobEntity;
	private final TargetingConditions distancePredicate = (TargetingConditions.forCombat()).range(64.0D);
	private LivingEntity villageAgressorTarget;

	/**
	 * Constructor for DefendVillageTargetGoal.
	 *
	 * @param mobEntity the <code>MobEntity</code> instance
	 */
	public DefendVillageTargetGoal(Mob mobEntity) {
		super(mobEntity, false, true);
		this.mobEntity = mobEntity;
		setFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 *
	 * @return boolean
	 */
	@Override
	public boolean canUse() {
		AABB aabb = mobEntity.getBoundingBox().inflate(10.0D, 8.0D, 10.0D);
		List<Villager> list = mobEntity.level.getNearbyEntities(Villager.class, distancePredicate, mobEntity, aabb);
		List<Player> list1 = mobEntity.level.getNearbyPlayers(distancePredicate, mobEntity, aabb);

		for (Villager villagerEntity : list) {

			for (Player playerEntity : list1) {
				int i = villagerEntity.getPlayerReputation(playerEntity);
				if (i <= -100) {
					villageAgressorTarget = playerEntity;
				}
			}
		}

		if (villageAgressorTarget == null) {
			return false;
		} else {
			return !(villageAgressorTarget instanceof Player) || !villageAgressorTarget.isSpectator() && !((Player) villageAgressorTarget).isCreative();
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		mobEntity.setTarget(villageAgressorTarget);
		super.start();
	}
}