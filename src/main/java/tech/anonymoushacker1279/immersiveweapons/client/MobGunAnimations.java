package tech.anonymoushacker1279.immersiveweapons.client;

import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.HumanoidAnimationController;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.MirrorModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.SpeedModifier;
import com.zigythebird.playeranimcore.enums.PlayState;
import com.zigythebird.playeranimcore.molang.MolangLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.WeakHashMap;

/// PlayerAnimator animations for mobs. PlayerAnimator only attaches itself to avatars (players and mannequins), so for
/// other entities the controller is created and driven here, and its bones are applied by `GunAnimatedHumanoidModel`.
/// The animations are the same files used for players.
///
/// A controller is only created for a mob the first time it is told to play something.
public final class MobGunAnimations {

	private static final Map<Entity, State> STATES = new WeakHashMap<>();

	private static final class State {
		private final HumanoidAnimationController controller;
		private final SpeedModifier speed = new SpeedModifier(1.0f);
		private final MirrorModifier mirror = new MirrorModifier();
		private int lastTick = Integer.MIN_VALUE;

		private State() {
			// The default Molang engine casts the controller to a player controller, so use the base one
			controller = new HumanoidAnimationController((animController, data, setter) -> PlayState.STOP, MolangLoader::createBaseEngine);
			controller.addModifierLast(speed);
			controller.addModifierLast(mirror);
		}
	}

	private MobGunAnimations() {
	}

	/// Play an animation on a mob.
	///
	/// @param animation the animation ID
	/// @param speed     the playback speed multiplier
	/// @param mirrored  true to swap the arms (animations are authored for the gun in the left arm)
	public static void play(Entity mob, Identifier animation, float speed, boolean mirrored) {
		Animation resolved = PlayerAnimResources.getAnimation(animation);
		if (resolved == null) {
			return;
		}

		State state = STATES.computeIfAbsent(mob, key -> new State());
		if (Float.isFinite(speed) && speed > 0) {
			state.speed.speed = speed;
		}
		state.mirror.enabled = mirrored;

		state.controller.triggerAnimation(resolved);
	}

	/// Stop whatever is playing on a mob.
	public static void stop(Entity mob) {
		State state = STATES.get(mob);
		if (state != null) {
			state.controller.stopTriggeredAnimation();
		}
	}

	/// Advance the animation for this frame, and return the controller if it is currently animating the mob.
	@Nullable
	public static HumanoidAnimationController update(Entity mob, float partialTick) {
		State state = STATES.get(mob);
		if (state == null) {
			return null;
		}

		Vec3 velocity = mob.getDeltaMovement();
		AnimationData data = new AnimationData((float) ((Math.abs(velocity.x) + Math.abs(velocity.z)) / 2.0d), partialTick, false);

		// Tick once per game tick, set up once per rendered frame
		if (state.lastTick != mob.tickCount) {
			state.lastTick = mob.tickCount;
			state.controller.tick(data.copy());
		}
		state.controller.setupAnim(data.copy());

		return state.controller.isActive() ? state.controller : null;
	}
}
