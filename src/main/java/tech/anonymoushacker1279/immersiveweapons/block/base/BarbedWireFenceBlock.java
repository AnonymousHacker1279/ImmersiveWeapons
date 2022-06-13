package tech.anonymoushacker1279.immersiveweapons.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class BarbedWireFenceBlock extends FenceBlock {

	private final DamageSource damageSource = new DamageSource("immersiveweapons.barbed_wire");
	private int soundCooldown = 0;

	/**
	 * Constructor for BarbedWireFenceBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public BarbedWireFenceBlock(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when an entity is inside the block's collision area.
	 * Allows the block to deal damage on contact.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param level  the <code>Level</code> the block is in
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @param entity the <code>Entity</code> passing through the block
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (entity instanceof LivingEntity) {
			entity.makeStuckInBlock(state, new Vec3(0.45F, 0.40D, 0.45F));
			if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double d0 = Math.abs(entity.getX() - entity.xOld);
				double d1 = Math.abs(entity.getZ() - entity.zOld);
				if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
					entity.hurt(damageSource, 2.0F);
				}
			}
			if (entity instanceof Player && soundCooldown <= 0) {
				level.playSound((Player) entity, pos, DeferredRegistryHandler.BARBED_WIRE_RATTLE.get(), SoundSource.BLOCKS, 1f, 1f);
				soundCooldown = 40;
			} else {
				soundCooldown--;
			}
		}
	}
}