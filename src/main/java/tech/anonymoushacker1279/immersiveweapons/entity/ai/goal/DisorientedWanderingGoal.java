package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.EnumSet;

public class DisorientedWanderingGoal extends Goal {
	private final Mob mob;
	private double targetX;
	private double targetY;
	private double targetZ;
	private int disorientedTicks;

	public DisorientedWanderingGoal(Mob mob, int disorientedTicks) {
		this.mob = mob;
		this.disorientedTicks = disorientedTicks;
		setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		return disorientedTicks > 0;
	}

	@Override
	public void start() {
		findRandomTargetPos();
	}

	@Override
	public void tick() {
		if (mob.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
					mob.getX() + (mob.getRandom().nextDouble() - 0.5D) * (double) mob.getBbWidth(),
					mob.getY() + mob.getRandom().nextDouble() * (double) mob.getBbHeight(),
					mob.getZ() + (mob.getRandom().nextDouble() - 0.5D) * (double) mob.getBbWidth(),
					3, 0.0D, 0.0D, 0.0D, 0.0D
			);
		}

		if (mob.getNavigation().isDone()) {
			mob.getNavigation().moveTo(targetX, targetY, targetZ, 1.0);
			findRandomTargetPos();
		}
		disorientedTicks--;
	}

	private void findRandomTargetPos() {
		for (int i = 0; i < 100; i++) {
			int dx = mob.getRandom().nextInt(33) - 16;
			int dz = mob.getRandom().nextInt(33) - 16;
			int x = (int) mob.getX() + dx;
			int z = (int) mob.getZ() + dz;
			int y = mob.level().getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

			if (mob.level().isEmptyBlock(new BlockPos(x, y, z)) && !mob.level().isEmptyBlock(new BlockPos(x, y - 1, z))) {
				targetX = x;
				targetY = y;
				targetZ = z;
				return;
			}
		}

		// If no valid location is found after 100 tries, fall back to the entity's current location
		this.targetX = this.mob.getX();
		this.targetY = this.mob.getY();
		this.targetZ = this.mob.getZ();
	}
}