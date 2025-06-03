package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class BarbedWireFenceBlock extends FenceBlock {

	public BarbedWireFenceBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
		if (entity instanceof LivingEntity livingEntity) {
			entity.makeStuckInBlock(state, new Vec3(0.45F, 0.40D, 0.45F));
			if (level instanceof ServerLevel serverLevel) {
				Vec3 movement = entity.getKnownMovement();
				if (movement.x >= 0.001F || movement.z >= 0.001F) {
					if (entity instanceof Player player && player.isCreative()) {
						return;
					}

					if (level.getGameTime() % 10 == 0) {
						entity.hurtServer(serverLevel,
								IWDamageSources.barbedWire(level.registryAccess()),
								2.0f);

						if (livingEntity.getRandom().nextFloat() <= 0.35f) {
							livingEntity.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT,
									200, 0, true, true));
						}
					}
				}
			}

			if (entity instanceof Player player && player.getRandom().nextFloat() <= 0.2f) {
				level.playSound(player, pos, SoundEventRegistry.BARBED_WIRE_RATTLE.get(), SoundSource.BLOCKS, 1f, player.getRandom().nextFloat() * 0.2f + 0.9f);
			}
		}
	}
}