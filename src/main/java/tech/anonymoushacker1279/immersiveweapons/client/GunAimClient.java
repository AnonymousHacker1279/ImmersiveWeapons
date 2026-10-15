package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.util.Mth;

/// Client-side aiming state for firearms. `progress` eases between hip-fire (0) and fully aimed (1).
public final class GunAimClient {

	private static final float AIM_IN_SPEED = 0.2f;
	private static final float AIM_OUT_SPEED = 0.25f;

	private static boolean aiming;
	private static float progress;
	private static float previousProgress;

	private GunAimClient() {
	}

	public static boolean isAiming() {
		return aiming;
	}

	public static void setAiming(boolean value) {
		aiming = value;
	}

	/// Advance the animation. Call once per client tick.
	public static void tick() {
		previousProgress = progress;
		progress = Mth.clamp(progress + (aiming ? AIM_IN_SPEED : -AIM_OUT_SPEED), 0.0f, 1.0f);
	}

	/// The eased aim progress, from 0 (hip-fire) to 1 (fully aimed).
	public static float getProgress(float partialTick) {
		float linear = Mth.lerp(partialTick, previousProgress, progress);
		return linear * linear * (3.0f - 2.0f * linear);
	}
}
