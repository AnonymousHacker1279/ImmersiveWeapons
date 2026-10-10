package tech.anonymoushacker1279.immersiveweapons.client.renderer;

import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoItemRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.item.ItemDisplayContext;
import tech.anonymoushacker1279.immersiveweapons.client.GunAimClient;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gun.MusketItem;

/// Renders the musket, hiding the scope and its rings unless the stack has a scope attached.
public class MusketGeoItemRenderer extends GeoItemRenderer<MusketItem> {

	/// Aim progress past which the gun is hidden while scoped.
	private static final float SCOPED_HIDE_THRESHOLD = 0.85f;

	private static final DataTicket<Boolean> HAS_SCOPE = DataTicket.create("immersiveweapons_has_scope", Boolean.class);

	public MusketGeoItemRenderer(MusketItem item) {
		super(item);
	}

	@Override
	public void captureDefaultRenderState(MusketItem animatable, RenderData renderData, GeoRenderState renderState, float partialTick) {
		super.captureDefaultRenderState(animatable, renderData, renderState, partialTick);

		renderState.addGeckolibData(HAS_SCOPE, renderData.itemStack().has(DataComponentTypeRegistry.SCOPE.get()));
	}

	/// Hide the whole gun in first person once scoped in. The scope overlay replaces the view, and the gun (and the scope
	/// itself) would otherwise show through the middle of it.
	@Override
	public void submit(GeoRenderState renderState, PoseStack poseStack, SubmitNodeCollector renderTasks, int packedLight) {
		ItemDisplayContext perspective = renderState.getGeckolibData(DataTickets.ITEM_RENDER_PERSPECTIVE);

		if (perspective != null && perspective.firstPerson()
				&& renderState.getOrDefaultGeckolibData(HAS_SCOPE, false)
				&& GunAimClient.isAiming()
				&& GunAimClient.getProgress(renderState.getPartialTick()) > SCOPED_HIDE_THRESHOLD) {
			return;
		}

		super.submit(renderState, poseStack, renderTasks, packedLight);
	}

	@Override
	public void preRenderPass(RenderPassInfo<GeoRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
		super.preRenderPass(renderPassInfo, renderTasks);

		if (!renderPassInfo.getOrDefaultGeckolibData(HAS_SCOPE, false)) {
			renderPassInfo.addBoneUpdater((info, snapshots) -> {
				snapshots.ifPresent("scope", snapshot -> snapshot.skipRender(true));
				snapshots.ifPresent("rings", snapshot -> snapshot.skipRender(true));
			});
		}
	}
}
