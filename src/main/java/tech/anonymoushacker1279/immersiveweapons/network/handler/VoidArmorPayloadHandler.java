package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.network.payload.VoidArmorPayload;

public class VoidArmorPayloadHandler {

	private static final VoidArmorPayloadHandler INSTANCE = new VoidArmorPayloadHandler();

	public static VoidArmorPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final VoidArmorPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() instanceof ServerPlayer serverPlayer) {
						serverPlayer.getPersistentData().putBoolean("VoidArmorEffectEnabled", data.state());
						if (data.summonDragonBreath()) {
							summonDragonBreath(serverPlayer.level(), serverPlayer);
						}
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}

	private void summonDragonBreath(Level level, Player player) {
		int yPos = (int) player.getY();
		BlockHitResult result = level.clip(
				new ClipContext(
						player.position(),
						player.position()
								.add(0, -50, 0),
						ClipContext.Block.COLLIDER,
						ClipContext.Fluid.ANY,
						player
				)
		);

		if (result.getType() == BlockHitResult.Type.BLOCK) {
			yPos = result.getBlockPos().getY() + 1;
		}

		AreaEffectCloud cloud = new AreaEffectCloud(level, player.getX(), yPos, player.getZ());
		cloud.setOwner(player);
		cloud.setParticle(ParticleTypes.DRAGON_BREATH);
		cloud.setRadius(2.0f);
		cloud.setDuration(100);
		cloud.setRadiusPerTick((2.0f - cloud.getRadius()) / (float) cloud.getDuration());
		cloud.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 1, 1));
		level.addFreshEntity(cloud);

		level.playSound(null, player.blockPosition(), SoundEvents.DRAGON_FIREBALL_EXPLODE, player.getSoundSource(), 1.0f, Mth.randomBetween(level.random, 0.8f, 1.1f));
	}
}