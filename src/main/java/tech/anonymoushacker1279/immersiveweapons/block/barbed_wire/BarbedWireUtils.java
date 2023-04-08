package tech.anonymoushacker1279.immersiveweapons.block.barbed_wire;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public interface BarbedWireUtils {

	default void handleEntityContact(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity) {
			entity.makeStuckInBlock(state, new Vec3(0.45F, 0.40D, 0.45F));
			if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double xDelta = Math.abs(entity.getX() - entity.xOld);
				double zDelta = Math.abs(entity.getZ() - entity.zOld);
				if (xDelta >= (double) 0.003F || zDelta >= (double) 0.003F) {
					entity.hurt(IWDamageSources.BARBED_WIRE, 2.0F);
				}
			}
			if (entity instanceof Player && BarbedWireBlock.soundCooldown <= 0) {
				level.playSound((Player) entity, pos, SoundEventRegistry.BARBED_WIRE_RATTLE.get(), SoundSource.BLOCKS, 1f, 1f);
				BarbedWireBlock.soundCooldown = 40;
			} else {
				BarbedWireBlock.soundCooldown--;
			}
		}
	}
}