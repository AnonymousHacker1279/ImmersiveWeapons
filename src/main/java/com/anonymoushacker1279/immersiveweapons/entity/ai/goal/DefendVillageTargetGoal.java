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

	public DefendVillageTargetGoal(MobEntity mobEntity) {
		super(mobEntity, false, true);
		this.mobEntity = mobEntity;
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		AxisAlignedBB axisalignedbb = this.mobEntity.getBoundingBox().inflate(10.0D, 8.0D, 10.0D);
		List<LivingEntity> list = this.mobEntity.level.getNearbyEntities(VillagerEntity.class, this.distancePredicate, this.mobEntity, axisalignedbb);
		List<PlayerEntity> list1 = this.mobEntity.level.getNearbyPlayers(this.distancePredicate, this.mobEntity, axisalignedbb);

		for (LivingEntity livingentity : list) {
			VillagerEntity villagerentity = (VillagerEntity) livingentity;

			for (PlayerEntity playerentity : list1) {
				int i = villagerentity.getPlayerReputation(playerentity);
				if (i <= -100) {
					this.villageAgressorTarget = playerentity;
				}
			}
		}

		if (this.villageAgressorTarget == null) {
			return false;
		} else {
			return !(this.villageAgressorTarget instanceof PlayerEntity) || !this.villageAgressorTarget.isSpectator() && !((PlayerEntity) this.villageAgressorTarget).isCreative();
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.mobEntity.setTarget(this.villageAgressorTarget);
		super.start();
	}
}