package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.client.particle.damage_indicator.DamageIndicatorParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.network.payload.DamageIndicatorPayload;

public class DamageIndicatorPayloadHandler {

	private static final DamageIndicatorPayloadHandler INSTANCE = new DamageIndicatorPayloadHandler();

	public static DamageIndicatorPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final DamageIndicatorPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (IWConfigs.CLIENT.enableDamageIndicatorParticles.getAsBoolean()) {
						Player player = context.player();
						player.level().addAlwaysVisibleParticle(
								new DamageIndicatorParticleOptions(data.damage()),
								true,
								data.pos().x(),
								data.pos().y(),
								data.pos().z(),
								player.getRandom().nextGaussian() * 0.025D,
								player.getRandom().nextGaussian() * 0.025D,
								player.getRandom().nextGaussian() * 0.025D
						);
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}