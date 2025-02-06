package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.TeleporterBlockEntity;

public class PliersItem extends Item {

	@Nullable
	private BlockPos linkedTeleporterPos;
	@Nullable
	private ResourceLocation linkedTeleporterDimension;

	public PliersItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getLevel().isClientSide) {
			return InteractionResult.SUCCESS;
		}

		Player player = context.getPlayer();

		if (player == null) {
			return InteractionResult.PASS;
		}

		if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof TeleporterBlockEntity blockEntity) {
			if (linkedTeleporterPos != null) {
				blockEntity.setLinkedTeleporter(linkedTeleporterPos, linkedTeleporterDimension);
				player.displayClientMessage(Component.translatable("immersiveweapons.item.pliers.set_linked_teleporter", linkedTeleporterPos.getX(), linkedTeleporterPos.getY(), linkedTeleporterPos.getZ())
						.withStyle(ChatFormatting.GREEN), true);

				linkedTeleporterPos = null;
				linkedTeleporterDimension = null;
			} else {
				linkedTeleporterPos = context.getClickedPos();
				linkedTeleporterDimension = context.getLevel().dimension().location();
				player.displayClientMessage(Component.translatable("immersiveweapons.item.pliers.store_linked_teleporter", linkedTeleporterPos.getX(), linkedTeleporterPos.getY(), linkedTeleporterPos.getZ())
						.withStyle(ChatFormatting.GREEN), true);
			}

			return InteractionResult.SUCCESS;
		} else {
			if (player.isCrouching() && linkedTeleporterPos != null && linkedTeleporterDimension != null) {
				linkedTeleporterPos = null;
				linkedTeleporterDimension = null;
				player.displayClientMessage(Component.translatable("immersiveweapons.item.pliers.clear_linked_teleporter")
						.withStyle(ChatFormatting.RED), true);
			}
		}

		return InteractionResult.PASS;
	}
}