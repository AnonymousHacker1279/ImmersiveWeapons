package tech.anonymoushacker1279.immersiveweapons.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.advancement.IWCriteriaTriggers;

public interface GrantAdvancementOnDiscovery {

	default void checkForDiscovery(LivingEntity entity) {
		Level level = entity.level();
		BlockPos entityPos = entity.blockPosition();

		if (!level.isClientSide && entity.tickCount % 20 == 0) {
			int scanningRange = ImmersiveWeapons.COMMON_CONFIG.discoveryAdvancementRange().get();
			AABB scanningBox = new AABB(entityPos.offset(-scanningRange, -scanningRange, -scanningRange),
					entityPos.offset(scanningRange, scanningRange, scanningRange));

			for (Player player : level.getNearbyPlayers(TargetingConditions.forNonCombat(), entity, scanningBox)) {
				if (isLookingAtMe(entity, player)) {
					if (IWCriteriaTriggers.ENTITY_DISCOVERED_TRIGGER != null) {
						IWCriteriaTriggers.ENTITY_DISCOVERED_TRIGGER.trigger((ServerPlayer) player, entity);
					}
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