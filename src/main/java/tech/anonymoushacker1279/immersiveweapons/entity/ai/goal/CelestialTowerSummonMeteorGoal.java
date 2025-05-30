package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;

import java.util.List;

public class CelestialTowerSummonMeteorGoal extends Goal {

	private final CelestialTowerEntity tower;
	private int summonCooldown = 400;

	public CelestialTowerSummonMeteorGoal(CelestialTowerEntity pMob) {
		tower = pMob;
	}

	@Override
	public boolean canUse() {
		return tower.level().getDifficulty() != Difficulty.EASY;
	}

	@Override
	public void tick() {
		if (summonCooldown <= 0) {
			summonCooldown = tower.isDoneSpawningWaves() ? 200 / tower.getDifficultyWaveSizeModifier() : 400 / tower.getDifficultyWaveSizeModifier();

			AABB searchBox = new AABB(tower.getX() - 32,
					tower.getY() - 16,
					tower.getZ() - 32,
					tower.getX() + 16,
					tower.getY() + 16,
					tower.getZ() + 32);

			// Find a random nearby player (within 32 blocks)
			if (tower.level() instanceof ServerLevel serverLevel) {
				List<Player> nearbyPlayers = serverLevel.getNearbyPlayers(
						TargetingConditions.forCombat().range(32),
						tower,
						searchBox
				);

				// Select a random player
				if (!nearbyPlayers.isEmpty()) {
					Player target = nearbyPlayers.get(tower.getRandom().nextIntBetweenInclusive(0, nearbyPlayers.size() - 1));

					// Summon a meteor at the player's position
					MeteorEntity.create(tower.level(), tower, null, target.blockPosition(), null);
				}
			}
		} else {
			summonCooldown--;
		}
	}
}