package tech.anonymoushacker1279.immersiveweapons.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.init.CriterionTriggerRegistry;

public interface GrantAdvancementOnDiscovery {

	default void checkForDiscovery(LivingEntity entity) {
		Level level = entity.level();
		BlockPos entityPos = entity.blockPosition();

		if (level instanceof ServerLevel serverLevel && entity.tickCount % 20 == 0) {
			int scanningRange = IWConfigs.SERVER.discoveryAdvancementRange.getAsInt();
			AABB scanningBox = new AABB(
					entityPos.offset(-scanningRange, -scanningRange, -scanningRange).getCenter(),
					entityPos.offset(scanningRange, scanningRange, scanningRange).getCenter()
			);

			for (Player player : serverLevel.getNearbyPlayers(TargetingConditions.forNonCombat(), entity, scanningBox)) {
				if (isLookingAtMe(entity, player)) {
					CriterionTriggerRegistry.ENTITY_DISCOVERED_TRIGGER.get().trigger((ServerPlayer) player, entity);
				}
			}
		}
	}

	default boolean isLookingAtMe(Entity entity, Player player) {
		Vec3 viewVector = player.getViewVector(1.0F).normalize();
		Vec3 viewDelta = new Vec3(entity.getX() - player.getX(), entity.getEyeY() - player.getEyeY(), entity.getZ() - player.getZ());
		double deltaLength = viewDelta.length();
		viewDelta = viewDelta.normalize();
		double deltaDot = viewVector.dot(viewDelta);
		return deltaDot > 1.0D - 0.025D / deltaLength && player.hasLineOfSight(entity);
	}
}