package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.EvilEyeEntity;

public class EvilEyeFlyRandomlyGoal extends Goal {

	private final EvilEyeEntity entity;
	@Nullable
	private BlockPos targetPosition;
	private int targetingCooldown;

	private static final ResourceKey<Biome> DEADMANS_DESERT = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "deadmans_desert"));
	private static final ResourceKey<Level> TILTROS = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "tiltros"));

	public EvilEyeFlyRandomlyGoal(EvilEyeEntity eye) {
		entity = eye;
	}

	@Override
	public boolean canUse() {
		return true;
	}

	@Override
	public void tick() {
		// If the entity has no target position, or the target position is too close, pick a new one

		if (targetingCooldown > 0) {
			targetingCooldown--;
			return;
		}

		Vec3i immutablePosition = new Vec3i(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ());

		if (targetPosition == null) {
			// Pick random coordinates within 64 blocks of the entity
			// They must be at least 20 blocks above the ground and no more than 30 blocks above the ground

			// Pick a random x coordinate
			int x = immutablePosition.getX() + entity.getRandom().nextInt(64) - 32;

			// Pick a random z coordinate
			int z = immutablePosition.getZ() + entity.getRandom().nextInt(64) - 32;

			// Pick a random y coordinate
			// Start by getting the ground level and adding 10 to it
			int minY = entity.level().getHeightmapPos(Types.WORLD_SURFACE, new BlockPos(x, 0, z)).getY() + 20;
			int maxY = minY + 11;
			// Pick a random number between the two bounds
			int y = entity.getRandom().nextIntBetweenInclusive(minY, maxY);

			// Set the target position
			targetPosition = new BlockPos(x, y, z);

			// Check if there is a direct path to the coordinates without hitting any blocks
			if (entity.level().clip(new ClipContext(entity.position(), targetPosition.getCenter(),
					ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() != HitResult.Type.MISS) {
				// If there is no direct path, set the target position to null
				targetPosition = null;
				targetingCooldown = 40;
				return;
			}

			// Check if the entity is in Tiltros
			// It should stay confined to the desert, but if it's not in Tiltros, it will always remain idle
			if (entity.level().dimension() == TILTROS) {
				// Ensure the target position is in the Deadman's Desert biome
				if (!entity.level().getBiome(targetPosition).is(DEADMANS_DESERT) && entity.getSpawnType() == EntitySpawnReason.NATURAL) {
					targetPosition = null;
					targetingCooldown = 40;
					return;
				}
			}
		}

		// Move towards the target position
		double deltaX = targetPosition.getX() + 0.5D - entity.getX();
		double deltaY = targetPosition.getY() + 0.5D - entity.getY();
		double deltaZ = targetPosition.getZ() + 0.5D - entity.getZ();
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
		double speed = entity.getAttributeValue(Attributes.FLYING_SPEED);
		entity.setDeltaMovement(entity.getDeltaMovement().add(
				(deltaX / distance) * speed,
				(deltaY / distance) * speed,
				(deltaZ / distance) * speed));

		// Set the look position to the target position
		entity.lookAt(Anchor.EYES, targetPosition.getCenter());

		// If the entity is within 3 blocks of the target position, pick a new one
		if (targetPosition.closerThan(immutablePosition, 3)) {
			targetPosition = null;
		}
	}
}