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

	public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		int x = pos.getX();
		int z = pos.getZ();

		BlockPos rotationOffset = new BlockPos(0, 0, 0).rotate(rotation);
		BlockPos blockPos = rotationOffset.add(x, pos.getY(), z);
		pieceList.add(new PitfallTrapPieces.Piece(templateManager, CENTER, blockPos, rotation));
	}

	public static class Piece extends TemplateStructurePiece {
		private final ResourceLocation resourceLocation;
		private final Rotation rotation;

		public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
			super(Structures.PT, 0);
			this.resourceLocation = resourceLocationIn;
			BlockPos blockpos = PitfallTrapPieces.OFFSET.get(resourceLocation);
			this.templatePosition = pos.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
			this.rotation = rotationIn;
			this.setupPiece(templateManagerIn);
		}

		public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
			super(Structures.PT, tagCompound);
			this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
			this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
			this.setupPiece(templateManagerIn);
		}

		private void setupPiece(TemplateManager templateManager) {
			Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
			this.setup(template, this.templatePosition, placementsettings);
		}

		@Override
		protected void readAdditional(CompoundNBT tagCompound) {
			super.readAdditional(tagCompound);
			tagCompound.putString("Template", this.resourceLocation.toString());
			tagCompound.putString("Rot", this.rotation.name());
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {

		}
	}
}