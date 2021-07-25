package com.anonymoushacker1279.immersiveweapons.world.structures;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
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

public class LandmineTrapPieces {

	private static final int height = 0;

	private static final ResourceLocation CENTER = new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_trap");
	private static final Map<ResourceLocation, BlockPos> OFFSET = new ImmutableMap.Builder<ResourceLocation, BlockPos>()
			.put(CENTER, new BlockPos(0, height, 0))
			.build();

	/**
	 * Start building structure pieces.
	 * @param templateManager the <code>TemplateManager</code> instance
	 * @param pos the <code>BlockPos</code> position
	 * @param rotation the <code>Rotation</code>
	 * @param pieceList the <code>List</code> of pieces; must extend StructurePiece
	 */
	public static void start(StructureManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		int x = pos.getX();
		int z = pos.getZ();

		BlockPos rotationOffset = new BlockPos(0, 0, 0).rotate(rotation);
		BlockPos blockPos = rotationOffset.offset(x, pos.getY(), z);
		pieceList.add(new LandmineTrapPieces.Piece(templateManager, CENTER, blockPos, rotation, GeneralUtilities.getRandomNumber(1, 3)));
	}

	public static class Piece extends TemplateStructurePiece {
		private static ResourceLocation resourceLocation;
		private static Rotation rotation;

		/**
		 * Constructor for Piece.
		 * @param templateManagerIn the <code>TemplateManager</code> instance
		 * @param resourceLocationIn the <code>ResourceLocation</code> for the piece
		 * @param pos the <code>BlockPos</code> position
		 * @param rotationIn the <code>Rotation</code>
		 */
		Piece(StructureManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn, int i) {
			super(Structures.LT, 0, templateManagerIn, resourceLocationIn, resourceLocationIn.toString(), makeSettings(rotationIn, resourceLocationIn), makePosition(resourceLocationIn, pos, i));
		}

		// TODO: javadocs
		public Piece(ServerLevel level, CompoundTag nbt) {
			super(Structures.LT, nbt, level, (resourceLocation) -> makeSettings(Rotation.valueOf(nbt.getString("Rot")), resourceLocation));
		}

		// TODO: javadocs
		private static StructurePlaceSettings makeSettings(Rotation rotationIn, ResourceLocation resourceLocationIn) {
			resourceLocation = resourceLocationIn;
			rotation = rotationIn;
			return (new StructurePlaceSettings()).setRotation(rotation).setMirror(Mirror.NONE).setRotationPivot(LandmineTrapPieces.OFFSET.get(resourceLocation)).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
		}

		// TODO: javadocs
		private static BlockPos makePosition(ResourceLocation resourceLocation, BlockPos blockPos, int i) {
			return blockPos.offset(LandmineTrapPieces.OFFSET.get(resourceLocation)).below(i);
		}


		/**
		 * Add additional save data to NBT.
		 * @param nbt the <code>CompoundNBT</code> data
		 */
		// TODO: update javadocs
		@Override
		protected void addAdditionalSaveData(ServerLevel level, CompoundTag nbt) {
			super.addAdditionalSaveData(level, nbt);
			nbt.putString("Template", resourceLocation.toString());
			nbt.putString("Rot", rotation.name());
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