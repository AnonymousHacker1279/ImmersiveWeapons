package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.EvilEyeEntity;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class FlyRandomlyGoal extends Goal {

	private final EvilEyeEntity evilEyeEntity;
	@Nullable
	private BlockPos targetPosition;

	public FlyRandomlyGoal(EvilEyeEntity pMob) {
		evilEyeEntity = pMob;
	}

	@Override
	public boolean canUse() {
		return true;
	}

	@Override
	public void tick() {
		// If the entity has no target position, or the target position is too close, pick a new one

		Vec3i immutablePosition = new Vec3i(evilEyeEntity.getX(), evilEyeEntity.getY(), evilEyeEntity.getZ());

		if (targetPosition == null) {
			// Pick random coordinates within 64 blocks of the entity
			// They must be at least 10 blocks above the ground and no more than 20 blocks above the ground

			// Pick a random x coordinate
			int x = immutablePosition.getX() + evilEyeEntity.getRandom().nextInt(64) - 32;

			// Pick a random z coordinate
			int z = immutablePosition.getZ() + evilEyeEntity.getRandom().nextInt(64) - 32;

			// Pick a random y coordinate
			// Start by getting the ground level and adding 10 to it
			int minY = evilEyeEntity.getLevel().getHeightmapPos(Types.MOTION_BLOCKING, new BlockPos(x, 0, z)).getY() + 10;
			int maxY = minY + 11;
			// Pick a random number between the two bounds
			int y = GeneralUtilities.getRandomNumber(minY, maxY);

			// Set the target position
			targetPosition = new BlockPos(x, y, z);

			// Check if there is a direct path to the coordinates without hitting any blocks
			if (evilEyeEntity.getLevel().clip(new ClipContext(evilEyeEntity.position(), targetPosition.getCenter(),
					ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, evilEyeEntity)).getType() != HitResult.Type.MISS) {
				// If there is no direct path, set the target position to null
				targetPosition = null;
				return;
			}

			// Ensure the target position is in the Deadman's Desert biome
			if (!evilEyeEntity.getLevel().getBiome(targetPosition).is(IWBiomes.DEADMANS_DESERT)) {
				targetPosition = null;
				return;
			}
		}

		// Set the delta movement to move towards the coordinates
		evilEyeEntity.setDeltaMovement(
				(targetPosition.getX() - immutablePosition.getX()) * 0.01,
				(targetPosition.getY() - immutablePosition.getY()) * 0.01,
				(targetPosition.getZ() - immutablePosition.getZ()) * 0.01);

		// Set the look position to the target position
		evilEyeEntity.lookAt(Anchor.EYES, targetPosition.getCenter());

		// If the entity is within 3 blocks of the target position, pick a new one
		if (targetPosition.closerThan(immutablePosition, 3)) {
			targetPosition = null;
		}
	}
}