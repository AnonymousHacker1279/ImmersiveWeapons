package com.anonymoushacker1279.immersiveweapons.world.structures;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.Structures;
import com.google.common.collect.ImmutableMap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class PitfallTrapPieces {

	private static final int height = 0;

	private static final ResourceLocation CENTER = new ResourceLocation(ImmersiveWeapons.MOD_ID, "pitfall_trap");
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
	public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		int x = pos.getX();
		int z = pos.getZ();

		BlockPos rotationOffset = new BlockPos(0, 0, 0).rotate(rotation);
		BlockPos blockPos = rotationOffset.offset(x, pos.getY(), z);
		pieceList.add(new PitfallTrapPieces.Piece(templateManager, CENTER, blockPos, rotation));
	}

	public static class Piece extends TemplateStructurePiece {
		private final ResourceLocation resourceLocation;
		private final Rotation rotation;

		/**
		 * Constructor for Piece.
		 * @param templateManagerIn the <code>TemplateManager</code> instance
		 * @param resourceLocationIn the <code>ResourceLocation</code> for the piece
		 * @param pos the <code>BlockPos</code> position
		 * @param rotationIn the <code>Rotation</code>
		 */
		Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
			super(Structures.PT, 0);
			resourceLocation = resourceLocationIn;
			BlockPos blockpos = PitfallTrapPieces.OFFSET.get(resourceLocation);
			templatePosition = pos.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
			rotation = rotationIn;
			setupPiece(templateManagerIn);
		}

		/**
		 * Constructor for Piece.
		 * @param templateManagerIn the <code>TemplateManager</code> instance
		 * @param nbt the <code>CompoundNBT</code> data
		 */
		public Piece(TemplateManager templateManagerIn, CompoundNBT nbt) {
			super(Structures.PT, nbt);
			resourceLocation = new ResourceLocation(nbt.getString("Template"));
			rotation = Rotation.valueOf(nbt.getString("Rot"));
			setupPiece(templateManagerIn);
		}

		/**
		 * Setup a piece.
		 * @param templateManager the <code>TemplateManger</code> instance
		 */
		private void setupPiece(TemplateManager templateManager) {
			Template template = templateManager.getOrCreate(resourceLocation);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setMirror(Mirror.NONE);
			setup(template, templatePosition, placementsettings);
		}


		/**
		 * Add additional save data to NBT.
		 * @param nbt the <code>CompoundNBT</code> data
		 */
		@Override
		protected void addAdditionalSaveData(CompoundNBT nbt) {
			super.addAdditionalSaveData(nbt);
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
		protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
		}
	}
}