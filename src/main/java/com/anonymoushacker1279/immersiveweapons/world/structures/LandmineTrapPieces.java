package com.anonymoushacker1279.immersiveweapons.world.structures;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.Structures;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class LandmineTrapPieces {

	private static final int height = 0;

	private static final ResourceLocation CENTER = new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_trap");
	private static final Map<ResourceLocation, BlockPos> OFFSET = new ImmutableMap.Builder<ResourceLocation, BlockPos>()
			.put(CENTER, new BlockPos(0, height, 0))
			.build();

	/**
	 * Start building structure pieces.
	 * @param structureManager the <code>TemplateManager</code> instance
	 * @param pos the <code>BlockPos</code> position
	 * @param rotation the <code>Rotation</code>
	 * @param pieceList the <code>List</code> of pieces; must extend StructurePiece
	 */
	public static void start(StructureManager structureManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList) {
		int x = pos.getX();
		int z = pos.getZ();

		BlockPos rotationOffset = new BlockPos(0, 0, 0).rotate(rotation);
		BlockPos blockPos = rotationOffset.offset(x, pos.getY(), z);
		pieceList.add(new LandmineTrapPieces.Piece(structureManager, blockPos, rotation));
	}

	public static class Piece extends TemplateStructurePiece {
		private static ResourceLocation resourceLocation;
		private static Rotation rotation;

		/**
		 * Constructor for Piece.
		 * @param structureManager the <code>StructureManager</code> instance
		 * @param pos the <code>BlockPos</code> position
		 * @param rotationIn the <code>Rotation</code>
		 */
		Piece(StructureManager structureManager, BlockPos pos, Rotation rotationIn) {
			super(Structures.LT, 0, structureManager, LandmineTrapPieces.CENTER, LandmineTrapPieces.CENTER.toString(), makeSettings(rotationIn, LandmineTrapPieces.CENTER), makePosition(pos));
		}

		/**
		 * Constructor for Piece.
		 * @param level the <code>ServerLevel</code> the structure is in
		 * @param tag the <code>CompoundTag</code> data
		 */
		public Piece(ServerLevel level, CompoundTag tag) {
			super(Structures.LT, tag, level, (resourceLocation) -> makeSettings(Rotation.valueOf(tag.getString("Rot")), resourceLocation));
		}

		/**
		 * Make structure settings
		 * @param rotationIn the <code>Rotation</code> of the structure
		 * @param resourceLocationIn the <code>ResourceLocation</code> of the structure
		 * @return StructurePlaceSettings
		 */
		private static StructurePlaceSettings makeSettings(Rotation rotationIn, ResourceLocation resourceLocationIn) {
			resourceLocation = resourceLocationIn;
			rotation = rotationIn;
			return (new StructurePlaceSettings()).setRotation(rotation).setMirror(Mirror.NONE).setRotationPivot(LandmineTrapPieces.OFFSET.get(resourceLocation)).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
		}

		/**
		 * Make a structure position.
		 * @param blockPos the <code>BlockPos</code> the piece is at
		 * @return BlockPos
		 */
		private static BlockPos makePosition(BlockPos blockPos) {
			return blockPos.offset(LandmineTrapPieces.OFFSET.get(LandmineTrapPieces.CENTER));
		}


		/**
		 * Add additional save data to NBT.
		 * @param level the <code>ServerLevel</code> instance
		 * @param tag the <code>CompoundNBT</code> data
		 */
		@Override
		protected void addAdditionalSaveData(@NotNull ServerLevel level, @NotNull CompoundTag tag) {
			super.addAdditionalSaveData(level, tag);
			tag.putString("Template", resourceLocation.toString());
			tag.putString("Rot", rotation.name());
		}

		/**
		 * Handle data markers.
		 * @param function the <code>String</code> function
		 * @param pos the <code>BlockPos</code> position
		 * @param serverLevelAccessor the <code>ServerLevelAccessor</code>
		 * @param rand the <code>Random</code> instance
		 * @param sbb the <code>MutableBoundingBox</code>
		 */
		@Override
		protected void handleDataMarker(@NotNull String function, @NotNull BlockPos pos, @NotNull ServerLevelAccessor serverLevelAccessor, @NotNull Random rand, @NotNull BoundingBox sbb) {
		}
	}
}