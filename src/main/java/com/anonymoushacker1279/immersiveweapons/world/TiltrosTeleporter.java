package com.anonymoushacker1279.immersiveweapons.world;

import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

public class TiltrosTeleporter implements ITeleporter {

	protected final ServerLevel level;

	public TiltrosTeleporter(ServerLevel worldIn) {
		level = worldIn;
	}

	public Optional<BlockUtil.FoundRectangle> makePortal(BlockPos pos) {
		return Optional.of(new BlockUtil.FoundRectangle(pos.immutable(), 1, 1));
	}

	@Nullable
	@Override
	public PortalInfo getPortalInfo(Entity entity, ServerLevel level, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
		boolean destinationIsUG = level.dimension() == GeneralUtilities.TILTROS;
		if (entity.level.dimension() != GeneralUtilities.TILTROS && !destinationIsUG) {
			return null;
		}
		else {
			WorldBorder border = level.getWorldBorder();
			double minX = Math.max(-2.9999872E7D, border.getMinX() + 16.0D);
			double minZ = Math.max(-2.9999872E7D, border.getMinZ() + 16.0D);
			double maxX = Math.min(2.9999872E7D, border.getMaxX() - 16.0D);
			double maxZ = Math.min(2.9999872E7D, border.getMaxZ() - 16.0D);
			double coordinateDifference = DimensionType.getTeleportationScale(entity.level.dimensionType(), level.dimensionType());
			BlockPos blockpos = new BlockPos(Mth.clamp(entity.getX() * coordinateDifference, minX, maxX), entity.getY(), Mth.clamp(entity.getZ() * coordinateDifference, minZ, maxZ));
			return getOrMakePortal(blockpos).map((result) -> {
				BlockState blockstate = entity.level.getBlockState(entity.portalEntrancePos);
				Direction.Axis axis;
				Vec3 vector3d;
				if (blockstate.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
					axis = blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS);
					BlockUtil.FoundRectangle rectangle = BlockUtil.getLargestRectangleAround(entity.portalEntrancePos, axis, 21, Direction.Axis.Y, 21, (pos) -> entity.level.getBlockState(pos) == blockstate);
					vector3d = PortalShape.getRelativePosition(rectangle, axis, entity.position(), entity.getDimensions(entity.getPose()));
				} else {
					axis = Direction.Axis.X;
					vector3d = new Vec3(0.5D, 0.0D, 0.0D);
				}

				return PortalShape.createPortalInfo(level, result, axis, vector3d, entity.getDimensions(entity.getPose()), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
			}).orElse(null);
		}
	}

	protected Optional<BlockUtil.FoundRectangle> getOrMakePortal(BlockPos pos) {
		return makePortal(pos);
	}

	@Override
	public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destinationWorld) {
		return false;
	}
}