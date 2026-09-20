package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.anonymoushacker1279.immersiveweapons.network.payload.ArrowGravityPayload;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {

	/// The default gravity of an arrow. The client already uses this value, so there is no need to sync it.
	@Unique
	private static final double immersiveWeapons$DEFAULT_GRAVITY = 0.05d;

	/// How often (in ticks) gravity is re-sent after the initial sync, so clients that start tracking a projectile
	/// mid-flight still receive it.
	@Unique
	private static final int immersiveWeapons$GRAVITY_RESYNC_INTERVAL = 20;

	@Shadow
	private boolean hasBeenShot;

	@Unique
	private boolean immersiveWeapons$gravitySynced;

	/// Sync custom arrow gravity to tracking clients. Only arrows with non-default gravity are synced, once at the
	/// start of flight and periodically after.
	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo ci) {
		Entity self = (Entity) (Object) this;

		if (hasBeenShot && self instanceof AbstractArrow && !self.level().isClientSide()
				&& (!immersiveWeapons$gravitySynced || self.tickCount % immersiveWeapons$GRAVITY_RESYNC_INTERVAL == 0)) {
			immersiveWeapons$gravitySynced = true;

			double gravity = self.getGravity();
			if (gravity != immersiveWeapons$DEFAULT_GRAVITY) {
				PacketDistributor.sendToPlayersTrackingEntity(self, new ArrowGravityPayload(gravity, self.getId()));
			}
		}
	}
}
