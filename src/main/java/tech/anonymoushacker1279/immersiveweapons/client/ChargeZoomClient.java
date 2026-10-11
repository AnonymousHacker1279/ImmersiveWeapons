package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem;

/// Client-side zoom for items that are charged by holding the use key, like bows and grenades. `progress` follows the
/// charge while it builds, and eases back out on release instead of snapping.
public final class ChargeZoomClient {

	/// Ticks of holding to reach a full charge.
	private static final float CHARGE_TICKS = 20.0f;
	private static final float RELEASE_SPEED = 0.15f;

	private static float progress;
	private static float previousProgress;

	private ChargeZoomClient() {
	}

	/// Advance the animation. Call once per client tick.
	public static void tick() {
		previousProgress = progress;

		Player player = Minecraft.getInstance().player;
		if (player != null && player.isUsingItem() && isChargeItem(player.getUseItem().getItem())) {
			progress = Mth.clamp(player.getTicksUsingItem() / CHARGE_TICKS, 0.0f, 1.0f);
		} else {
			progress = Math.max(progress - RELEASE_SPEED, 0.0f);
		}
	}

	/// The eased charge progress, from 0 (none) to 1 (full).
	public static float getProgress(float partialTick) {
		float linear = Mth.lerp(partialTick, previousProgress, progress);
		return linear * linear * (3.0f - 2.0f * linear);
	}

	private static boolean isChargeItem(Item item) {
		return item == ItemRegistry.ICE_BOW.get()
				|| item == ItemRegistry.DRAGONS_BREATH_BOW.get()
				|| item == ItemRegistry.AURORA_BOW.get()
				|| (item instanceof ThrowableItem throwableItem && throwableItem.type.canCharge);
	}
}
