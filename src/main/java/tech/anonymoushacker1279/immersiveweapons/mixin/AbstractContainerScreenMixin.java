package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.anonymoushacker1279.immersiveweapons.client.tooltip.PlayerStatTooltips;

import java.util.List;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

	@Shadow
	protected abstract boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY);

	@Inject(method = "renderTooltip", at = @At("TAIL"))
	private void renderTooltip(GuiGraphics guiGraphics, int x, int y, CallbackInfo ci) {
		AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
		Player player = screen.getMinecraft().player;

		if (player == null) {
			return;
		}

		if (screen instanceof InventoryScreen) {
			if (isHovering(26, 8, 49, 70, x, y) && Screen.hasShiftDown()) {
				immersiveWeapons$renderPlayerStats(guiGraphics, screen, x, y, PlayerStatTooltips.getStatTooltips(player));
			}
		}

		if (screen instanceof CreativeModeInventoryScreen creativeScreen && creativeScreen.isInventoryOpen() && Screen.hasShiftDown()) {
			if (isHovering(73, 6, 32, 42, x, y)) {
				immersiveWeapons$renderPlayerStats(guiGraphics, screen, x, y, PlayerStatTooltips.getStatTooltips(player));
			}
		}
	}

	@Unique
	private void immersiveWeapons$renderPlayerStats(GuiGraphics guiGraphics, AbstractContainerScreen<?> screen, int x, int y, List<ClientTooltipComponent> tooltips) {
		guiGraphics.renderTooltip(
				screen.getFont(),
				tooltips,
				x,
				y,
				DefaultTooltipPositioner.INSTANCE,
				null
		);
	}
}