package com.anonymoushacker1279.immersiveweapons.structures.pieces;

import java.util.Random;

import com.anonymoushacker1279.immersiveweapons.init.StructurePieceTypes;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class AbandonedFactoryPiece {

public static class Piece extends TemplateStructurePiece {
		
		public Piece(TemplateManager templateMgr, CompoundNBT nbt) {
			super(StructurePieceTypes.ABANDONED_FACTORY_PIECE, nbt);
			this.setupTemplate(templateMgr);
		}

		public Piece(TemplateManager templateMgr, ResourceLocation resLoc, BlockPos blockPos, Rotation rot, int offsetY) {
			super(StructurePieceTypes.ABANDONED_FACTORY_PIECE, 0);
			this.templatePosition = new BlockPos(blockPos.getX(), blockPos.getY() - offsetY, blockPos.getZ());
			this.setupTemplate(templateMgr);
		}

		private void setupTemplate(TemplateManager templateMgr) {
	         Template template = templateMgr.getTemplateDefaulted(StructurePieceTypes.ABANDONED_FACTORY_LOC);
	         PlacementSettings placementsettings = (new PlacementSettings())
	        		 	.setRotation(Rotation.NONE)
	        		 	.setMirror(Mirror.NONE)
	        		 	.setCenterOffset(BlockPos.ZERO)
	        		 	.addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
	         this.setup(template, this.templatePosition, placementsettings);
		}
		
		@Override
		public boolean create(IWorld worldIn, ChunkGenerator<?> chunkGenIn, Random rand, MutableBoundingBox mutableBB, ChunkPos chunkPos) {
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(Rotation.NONE).setMirror(Mirror.NONE).setCenterOffset(BlockPos.ZERO).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
			BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3, 0, 0)));
			int strucHeight = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
			this.templatePosition = this.templatePosition.add(0, strucHeight, 0);
			boolean superReturn = super.create(worldIn, chunkGenIn, rand, mutableBB, chunkPos);
			return superReturn;
		}

		
		
		@Override
		protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand,
				MutableBoundingBox sbb) {
			
		}
	}
}
