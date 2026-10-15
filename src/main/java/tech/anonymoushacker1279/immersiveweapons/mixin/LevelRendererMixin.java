package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.anonymoushacker1279.immersiveweapons.client.renderer.FlareLightPass;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

	@Shadow
	@Final
	private LevelTargetBundle targets;

	/// Adds post-processing passes that need the finished main pass (its color and depth). Passes added from the
	/// frame graph setup event would run before the main pass, so this has to happen right after it is added.
	@Inject(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/LevelRenderer;addMainPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/feature/FeatureRenderDispatcher$PreparedFrame;Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;Z)V",
					shift = At.Shift.AFTER
			)
	)
	private void immersiveweapons$addFlareLightPass(CallbackInfo callbackInfo, @Local FrameGraphBuilder frame, @Local(argsOnly = true) CameraRenderState cameraState) {
		FlareLightPass.addToFrame(frame, targets, cameraState);
	}
}
