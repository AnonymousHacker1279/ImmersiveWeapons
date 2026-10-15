package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.mob;

import com.zigythebird.playeranimcore.animation.HumanoidAnimationController;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.jetbrains.annotations.Nullable;

/// Humanoid render state carrying the firearm animation that is currently playing on the mob, if any.
public class GunAnimatedRenderState extends HumanoidRenderState {

	@Nullable
	public HumanoidAnimationController gunAnimation;
}
