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

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BearTrapPieces {

	private static final int height = 0;

	private static final ResourceLocation CENTER = new ResourceLocation(ImmersiveWeapons.MOD_ID, "bear_trap");
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
	public static void start(StructureManager structureManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		int x = pos.getX();
		int z = pos.getZ();

		BlockPos rotationOffset = new BlockPos(0, 0, 0).rotate(rotation);
		BlockPos blockPos = rotationOffset.offset(x, pos.getY(), z);
		pieceList.add(new BearTrapPieces.Piece(structureManager, CENTER, blockPos, rotation));
	}

	public static class Piece extends TemplateStructurePiece {
		private static ResourceLocation resourceLocation;
		private static Rotation rotation;

		/**
		 * Constructor for Piece.
		 * @param structureManager the <code>TemplateManager</code> instance
		 * @param resourceLocationIn the <code>ResourceLocation</code> for the piece
		 * @param pos the <code>BlockPos</code> position
		 * @param rotationIn the <code>Rotation</code>
		 */
		Piece(StructureManager structureManager, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
			super(Structures.BT, 0, structureManager, resourceLocationIn, resourceLocationIn.toString(), makeSettings(rotationIn, resourceLocationIn), makePosition(resourceLocationIn, pos));
		}

		/**
		 * Constructor for Piece.
		 * @param level the <code>ServerLevel</code> the structure is in
		 * @param tag the <code>CompoundTag</code> data
		 */
		public Piece(ServerLevel level, CompoundTag tag) {
			super(Structures.BT, tag, level, (location) -> makeSettings(Rotation.valueOf(tag.getString("Rot")), location));
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
			return (new StructurePlaceSettings()).setRotation(rotation).setMirror(Mirror.NONE).setRotationPivot(BearTrapPieces.OFFSET.get(resourceLocation)).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
		}

		/**
		 * Make a structure position.
		 * @param resourceLocation the <code>ResourceLocation</code> of the piece
		 * @param blockPos the <code>BlockPos</code> the piece is at
		 * @return BlockPos
		 */
		private static BlockPos makePosition(ResourceLocation resourceLocation, BlockPos blockPos) {
			return blockPos.offset(BearTrapPieces.OFFSET.get(resourceLocation));
		}


		/**
		 * Add additional save data to NBT.
		 * @param level the <code>ServerLevel</code> instance
		 * @param tag the <code>CompoundNBT</code> data
		 */
		@Override
		protected void addAdditionalSaveData(ServerLevel level, CompoundTag tag) {
			super.addAdditionalSaveData(level, tag);
			tag.putString("Template", resourceLocation.toString());
			tag.putString("Rot", rotation.name());
		}

		/**
		 * Handle data markers.
		 * @param function the <code>String</code> function
		 * @param pos the <code>BlockPos</code> position
		 * @param worldIn the <code>IServerWorld</code>
		 * @param rand the <code>Random</code> instance
		 * @param sbb the <code>MutableBoundingBox</code>
		 */
		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, Random rand, BoundingBox sbb) {
		}
	}
}