package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public class CelestialTowerSummonMeteorGoal extends Goal {

	private final CelestialTowerEntity tower;
	private int summonCooldown = 400;

	public CelestialTowerSummonMeteorGoal(CelestialTowerEntity pMob) {
		tower = pMob;
	}

	@Override
	public boolean canUse() {
		return tower.getWaveSizeModifier() > 1;
	}

	@Override
	public void tick() {
		// The tower will summon a meteor at a random player's position in the near vicinity
		// If it is done, they spawn twice as often
		if (summonCooldown <= 0) {
			summonCooldown = tower.isDoneSpawningWaves() ? 200 / tower.getWaveSizeModifier() : 400 / tower.getWaveSizeModifier();

			AABB searchBox = new AABB(tower.getX() - 32,
					tower.getY() - 16,
					tower.getZ() - 32,
					tower.getX() + 16,
					tower.getY() + 16,
					tower.getZ() + 32);

			// Find a random nearby player (within 32 blocks)
			List<Player> nearbyPlayers = tower.getLevel().getNearbyPlayers(
					TargetingConditions.forCombat().range(32),
					tower,
					searchBox
			);
			// Select a random player
			if (!nearbyPlayers.isEmpty()) {
				Player target = nearbyPlayers.get(GeneralUtilities.getRandomNumber(0, nearbyPlayers.size() - 1));

				// Summon a meteor at the player's position
				MeteorEntity.create(tower.getLevel(), tower, null, target.blockPosition(), null);
			}
		} else {
			summonCooldown--;
		}
	}
}