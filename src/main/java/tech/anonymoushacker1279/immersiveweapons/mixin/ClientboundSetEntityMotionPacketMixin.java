package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Remove clamping on entity motion packets and use integers over shorts, to prevent rubber-banding
 * in projectiles with high speed.
 */
@Mixin(ClientboundSetEntityMotionPacket.class)
public class ClientboundSetEntityMotionPacketMixin {
	@Shadow
	private int id;

	@Shadow
	private int xa;

	@Shadow
	private int ya;

	@Shadow
	private int za;

	@Inject(method = "<init>(ILnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
	public void ClientboundSetEntityMotionPacket(int pId, Vec3 pDeltaMovement, CallbackInfo ci) {
		this.xa = (int) (pDeltaMovement.x * 8000.0D);
		this.ya = (int) (pDeltaMovement.y * 8000.0D);
		this.za = (int) (pDeltaMovement.z * 8000.0D);
	}

	@Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"))
	private void ClientboundSetEntityMotionPacket(FriendlyByteBuf pBuffer, CallbackInfo ci) {
		this.id = pBuffer.readVarInt();
		this.xa = pBuffer.readVarInt();
		this.ya = pBuffer.readVarInt();
		this.za = pBuffer.readVarInt();
	}

	@Inject(method = "write", at = @At("RETURN"))
	private void write(FriendlyByteBuf pBuffer, CallbackInfo ci) {
		pBuffer.writeVarInt(this.id);
		pBuffer.writeVarInt(this.xa);
		pBuffer.writeVarInt(this.ya);
		pBuffer.writeVarInt(this.za);
	}
}