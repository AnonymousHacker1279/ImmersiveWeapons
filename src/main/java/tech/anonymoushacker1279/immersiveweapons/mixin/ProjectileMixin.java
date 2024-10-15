package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.anonymoushacker1279.immersiveweapons.network.payload.ArrowGravityPayload;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {

	@Shadow
	private boolean hasBeenShot;

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo ci) {
		Entity self = (Entity) (Object) this;

		if (hasBeenShot && !self.level().isClientSide) {
			PacketDistributor.sendToPlayersTrackingEntity(self, new ArrowGravityPayload(self.getGravity(), self.getId()));
		}

	}
}