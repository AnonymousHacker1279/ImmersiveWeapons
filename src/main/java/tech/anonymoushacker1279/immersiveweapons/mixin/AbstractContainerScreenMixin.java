package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
	protected abstract boolean isHovering(int left, int top, int w, int h, double xm, double ym);

	@Inject(method = "extractTooltip", at = @At("TAIL"))
	private void renderTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, CallbackInfo ci) {
		AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
		Player player = screen.getMinecraft().player;

		if (player == null) {
			return;
		}

		if (screen instanceof InventoryScreen) {
			if (isHovering(26, 8, 49, 70, mouseX, mouseY) && Minecraft.getInstance().hasShiftDown()) {
				immersiveWeapons$renderPlayerStats(graphics, screen, mouseX, mouseY, PlayerStatTooltips.getStatTooltips(player));
			}
		}

		if (screen instanceof CreativeModeInventoryScreen creativeScreen && creativeScreen.isInventoryOpen() && Minecraft.getInstance().hasShiftDown()) {
			if (isHovering(73, 6, 32, 42, mouseX, mouseY)) {
				immersiveWeapons$renderPlayerStats(graphics, screen, mouseX, mouseY, PlayerStatTooltips.getStatTooltips(player));
			}
		}
	}

	@Unique
	private void immersiveWeapons$renderPlayerStats(GuiGraphicsExtractor graphics, AbstractContainerScreen<?> screen, int x, int y, List<ClientTooltipComponent> tooltips) {
		graphics.tooltip(
				screen.getFont(),
				tooltips,
				x,
				y,
				DefaultTooltipPositioner.INSTANCE,
				null
		);
	}
}