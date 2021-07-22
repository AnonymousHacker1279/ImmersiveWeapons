package com.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.EnumSet;
import java.util.List;

public class DefendVillageTargetGoal extends TargetGoal {

	private final MobEntity mobEntity;
	private final EntityPredicate distancePredicate = (new EntityPredicate()).range(64.0D);
	private LivingEntity villageAgressorTarget;

	/**
	 * Constructor for DefendVillageTargetGoal.
	 * @param mobEntity the <code>MobEntity</code> instance
	 */
	public DefendVillageTargetGoal(MobEntity mobEntity) {
		super(mobEntity, false, true);
		this.mobEntity = mobEntity;
		setFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 * @return boolean
	 */
	@Override
	public boolean canUse() {
		AxisAlignedBB axisalignedbb = mobEntity.getBoundingBox().inflate(10.0D, 8.0D, 10.0D);
		List<LivingEntity> list = mobEntity.level.getNearbyEntities(VillagerEntity.class, distancePredicate, mobEntity, axisalignedbb);
		List<PlayerEntity> list1 = mobEntity.level.getNearbyPlayers(distancePredicate, mobEntity, axisalignedbb);

		for (LivingEntity livingentity : list) {
			VillagerEntity villagerentity = (VillagerEntity) livingentity;

			for (PlayerEntity playerentity : list1) {
				int i = villagerentity.getPlayerReputation(playerentity);
				if (i <= -100) {
					villageAgressorTarget = playerentity;
				}
			}
		}

		if (villageAgressorTarget == null) {
			return false;
		} else {
			return !(villageAgressorTarget instanceof PlayerEntity) || !villageAgressorTarget.isSpectator() && !((PlayerEntity) villageAgressorTarget).isCreative();
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