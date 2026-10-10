package tech.anonymoushacker1279.immersiveweapons.client;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranim.api.PlayerAnimationFactory;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.MirrorModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.SpeedModifier;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonMode;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

/// Client-side PlayerAnimator support for firearm animations on the player model (arms loading/holding the gun).
///
/// Animations live in `assets/immersiveweapons/player_animations/*.json`; an animation's ID is the namespace plus
/// its key inside the file. This is the only class that touches PlayerAnimator, so the mod still loads without it
/// on a dedicated server.
public final class GunPlayerAnimations {

	public static final Identifier LAYER_ID = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "gun_actions");

	/// Priority of the layer. Gameplay animations are suggested at 1500 or above.
	private static final int LAYER_PRIORITY = 1500;

	private GunPlayerAnimations() {
	}

	/// Register the animation layer. Call from client setup, inside `enqueueWork`.
	public static void register() {
		PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(LAYER_ID, LAYER_PRIORITY, avatar -> {
			// Trigger-only layer, nothing plays until an animation is triggered
			PlayerAnimationController controller = new PlayerAnimationController(avatar, (animController, data, setter) -> PlayState.STOP);

			// Show the animated third-person arms and the held item in first person
			controller.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
			controller.setFirstPersonConfiguration(new FirstPersonConfiguration()
					.setShowLeftArm(true)
					.setShowRightArm(true)
					.setShowLeftItem(true)
					.setShowRightItem(true));

			// Per-trigger playback speed and left/right mirroring, updated in `play`
			controller.addModifierLast(new SpeedModifier(1.0f));
			controller.addModifierLast(new MirrorModifier());

			return controller;
		});
	}

	/// Play an animation on a player.
	///
	/// @param avatar    the player
	/// @param animation the animation ID
	/// @param speed     the playback speed multiplier
	/// @param mirrored  true to swap the left and right arms (animations are authored for the left arm)
	public static void play(Avatar avatar, Identifier animation, float speed, boolean mirrored) {
		if (PlayerAnimationAccess.getPlayerAnimationLayer(avatar, LAYER_ID) instanceof PlayerAnimationController controller) {
			for (AbstractModifier modifier : controller.getModifiers()) {
				if (modifier instanceof SpeedModifier speedModifier && Float.isFinite(speed) && speed > 0) {
					speedModifier.speed = speed;
				} else if (modifier instanceof MirrorModifier mirrorModifier) {
					mirrorModifier.enabled = mirrored;
				}
			}

			controller.triggerAnimation(animation);
		}
	}

	/// Stop whatever the layer is playing on a player (e.g., a reload that was cancelled).
	public static void stop(Avatar avatar) {
		if (PlayerAnimationAccess.getPlayerAnimationLayer(avatar, LAYER_ID) instanceof PlayerAnimationController controller) {
			controller.stopTriggeredAnimation();
		}
	}
}
