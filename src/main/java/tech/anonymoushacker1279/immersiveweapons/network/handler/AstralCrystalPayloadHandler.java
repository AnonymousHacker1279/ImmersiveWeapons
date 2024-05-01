package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AstralCrystalBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.network.payload.AstralCrystalPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class AstralCrystalPayloadHandler {

	private static final AstralCrystalPayloadHandler INSTANCE = new AstralCrystalPayloadHandler();

	public static AstralCrystalPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final AstralCrystalPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					Level level = context.player().level();

					int x = data.pos().getX();
					int y = data.pos().getY();
					int z = data.pos().getZ();

					level.addParticle(ParticleTypes.EXPLOSION_EMITTER,
							x + 0.5D + (GeneralUtilities.getRandomNumber(-0.2D, 0.2D)),
							y + 0.4D + (GeneralUtilities.getRandomNumber(0.2D, 0.5D)),
							z + 0.5D + (GeneralUtilities.getRandomNumber(-0.2D, 0.2D)),
							(GeneralUtilities.getRandomNumber(-0.16D, 0.16D)),
							(GeneralUtilities.getRandomNumber(0.13D, 0.16D)),
							(GeneralUtilities.getRandomNumber(-0.16D, 0.16D)));

					level.playLocalSound(x, y, z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0f, 1.3f, false);

					if (level.getBlockEntity(data.pos()) instanceof AstralCrystalBlockEntity crystalBlockEntity) {
						crystalBlockEntity.getInventory().clear();
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}