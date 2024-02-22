package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.EvilEyeEntity;

public class FlyRandomlyGoal extends Goal {

	private final EvilEyeEntity evilEyeEntity;
	@Nullable
	private BlockPos targetPosition;
	private int targetingCooldown;

	private static final ResourceKey<Biome> DEADMANS_DESERT = ResourceKey.create(Registries.BIOME, new ResourceLocation(ImmersiveWeapons.MOD_ID, "deadmans_desert"));
	private static final ResourceKey<Level> TILTROS = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros"));

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

		if (targetingCooldown > 0) {
			--targetingCooldown;
			return;
		}

		Vec3i immutablePosition = new Vec3i(evilEyeEntity.getBlockX(), evilEyeEntity.getBlockY(), evilEyeEntity.getBlockZ());

		if (targetPosition == null) {
			// Pick random coordinates within 64 blocks of the entity
			// They must be at least 20 blocks above the ground and no more than 30 blocks above the ground

			// Pick a random x coordinate
			int x = immutablePosition.getX() + evilEyeEntity.getRandom().nextInt(64) - 32;

			// Pick a random z coordinate
			int z = immutablePosition.getZ() + evilEyeEntity.getRandom().nextInt(64) - 32;

			// Pick a random y coordinate
			// Start by getting the ground level and adding 10 to it
			int minY = evilEyeEntity.level().getHeightmapPos(Types.MOTION_BLOCKING, new BlockPos(x, 0, z)).getY() + 20;
			int maxY = minY + 11;
			// Pick a random number between the two bounds
			int y = evilEyeEntity.getRandom().nextIntBetweenInclusive(minY, maxY);

			// Set the target position
			targetPosition = new BlockPos(x, y, z);

			// Check if there is a direct path to the coordinates without hitting any blocks
			if (evilEyeEntity.level().clip(new ClipContext(evilEyeEntity.position(), targetPosition.getCenter(),
					ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, evilEyeEntity)).getType() != HitResult.Type.MISS) {
				// If there is no direct path, set the target position to null
				targetPosition = null;
				targetingCooldown = 40;
				return;
			}

			// Check if the entity is in Tiltros
			// It should stay confined to the desert but if it's not in Tiltros, it will always remain idle
			if (evilEyeEntity.level().dimension() == TILTROS) {
				// Ensure the target position is in the Deadman's Desert biome
				if (!evilEyeEntity.level().getBiome(targetPosition).is(DEADMANS_DESERT)) {
					targetPosition = null;
					targetingCooldown = 40;
					return;
				}
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