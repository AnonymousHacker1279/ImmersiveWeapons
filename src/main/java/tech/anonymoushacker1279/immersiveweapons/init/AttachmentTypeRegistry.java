package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class AttachmentTypeRegistry {

	// Attachment Type Register
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ImmersiveWeapons.MOD_ID);

	/// Whether a player is aiming a firearm. Not saved, but synced to everyone tracking the player so other clients can
	/// show the aimed pose.
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> GUN_AIMING = ATTACHMENT_TYPES.register("gun_aiming",
			() -> AttachmentType.builder(() -> false)
					.sync(ByteBufCodecs.BOOL)
					.build());
}
