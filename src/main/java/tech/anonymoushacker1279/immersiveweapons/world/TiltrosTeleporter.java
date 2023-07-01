package tech.anonymoushacker1279.immersiveweapons.world;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.common.util.ITeleporter;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.AzulStainedOrchidBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AzulStainedOrchidBlockEntity;

import java.util.function.Function;

public class TiltrosTeleporter implements ITeleporter {

	final BlockPos targetPos;
	final BlockPos currentPos;
	static final ResourceLocation TILTROS_PORTAL_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros_portal");

	public TiltrosTeleporter(BlockPos targetPos, BlockPos currentPos) {
		this.targetPos = targetPos;
		this.currentPos = currentPos;
	}

	@Override
	public Entity placeEntity(Entity entity, ServerLevel currentLevel, ServerLevel destinationLevel, float yaw, Function<Boolean, Entity> repositionEntity) {
		repositionEntity.apply(false);

		BlockPos teleportPos = targetPos.above().relative(entity.getDirection().getOpposite());
		// Teleport the player to the target position
		entity.teleportTo(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());

		BlockPos offsetTarget = targetPos.offset(-3, 0, -3);

		if (!(destinationLevel.getBlockState(targetPos).getBlock() instanceof AzulStainedOrchidBlock)) {
			// Create a Tiltros Portal structure
			StructureTemplate template = destinationLevel.getStructureManager().getOrCreate(TILTROS_PORTAL_LOCATION);
			template.placeInWorld(destinationLevel, offsetTarget, offsetTarget, new StructurePlaceSettings(), destinationLevel.random, 3);

			// The Azul Stained Orchid is always in the center of the structure
			if (destinationLevel.getBlockEntity(targetPos.above()) instanceof AzulStainedOrchidBlockEntity blockEntity) {
				blockEntity.setTargetPos(currentPos.above());
			}
		}

		return entity;
	}

	@Override
	public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destinationWorld) {
		return false;
	}
}