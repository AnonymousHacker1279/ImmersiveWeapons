package com.anonymoushacker1279.immersiveweapons.world.structures;
// TODO: Rework when Forge API updates
/*
public class CampsitePieces {

	private static final ResourceLocation CENTER = new ResourceLocation(ImmersiveWeapons.MOD_ID, "campsite");
	private static final Map<ResourceLocation, BlockPos> OFFSET = new ImmutableMap.Builder<ResourceLocation, BlockPos>()
			.put(CENTER, new BlockPos(0, 0, 0))
			.build();

	*/
/**
	 * Start building structure pieces.
	 *
	 * @param structureManager the <code>TemplateManager</code> instance
	 * @param pos              the <code>BlockPos</code> position
	 * @param rotation         the <code>Rotation</code>
	 * @param pieceList        the <code>List</code> of pieces; must extend StructurePiece
	 *//*

	public static void start(StructureManager structureManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList) {
		int x = pos.getX();
		int z = pos.getZ();

		BlockPos rotationOffset = new BlockPos(0, 0, 0).rotate(rotation);
		BlockPos blockPos = rotationOffset.offset(x, pos.getY(), z);
		pieceList.add(new CampsitePieces.Piece(structureManager, blockPos, rotation));
	}

	public static class Piece extends TemplateStructurePiece {
		private static ResourceLocation resourceLocation;
		private static Rotation rotation;

		*/
/**
		 * Constructor for Piece.
		 *
		 * @param structureManager the <code>StructureManager</code> instance
		 * @param pos              the <code>BlockPos</code> position
		 * @param rotationIn       the <code>Rotation</code>
		 *//*

		Piece(StructureManager structureManager, BlockPos pos, Rotation rotationIn) {
			super(Structures.CS, 0, structureManager, CampsitePieces.CENTER, CampsitePieces.CENTER.toString(), makeSettings(rotationIn, CampsitePieces.CENTER), makePosition(pos));
		}

		*/
/**
		 * Constructor for Piece.
		 *
		 * @param level the <code>ServerLevel</code> the structure is in
		 * @param tag   the <code>CompoundTag</code> data
		 *//*

		public Piece(ServerLevel level, CompoundTag tag) {
			super(Structures.CS, tag, level, (resourceLocation) -> makeSettings(Rotation.valueOf(tag.getString("Rot")), resourceLocation));
		}

		*/
/**
		 * Make structure settings
		 *
		 * @param rotationIn         the <code>Rotation</code> of the structure
		 * @param resourceLocationIn the <code>ResourceLocation</code> of the structure
		 * @return StructurePlaceSettings
		 *//*

		private static StructurePlaceSettings makeSettings(Rotation rotationIn, ResourceLocation resourceLocationIn) {
			resourceLocation = resourceLocationIn;
			rotation = rotationIn;
			return (new StructurePlaceSettings()).setRotation(rotation).setMirror(Mirror.NONE).setRotationPivot(CampsitePieces.OFFSET.get(resourceLocation)).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
		}

		*/
/**
		 * Make a structure position.
		 *
		 * @param blockPos the <code>BlockPos</code> the piece is at
		 * @return BlockPos
		 *//*

		private static BlockPos makePosition(BlockPos blockPos) {
			return blockPos.offset(CampsitePieces.OFFSET.get(CampsitePieces.CENTER));
		}


		*/
/**
		 * Add additional save data to NBT.
		 *
		 * @param level the <code>ServerLevel</code> instance
		 * @param tag   the <code>CompoundNBT</code> data
		 *//*

		@Override
		protected void addAdditionalSaveData(@NotNull ServerLevel level, @NotNull CompoundTag tag) {
			super.addAdditionalSaveData(level, tag);
			tag.putString("Template", resourceLocation.toString());
			tag.putString("Rot", rotation.name());
		}

		*/
/**
		 * Handle data markers.
		 *
		 * @param function            the <code>String</code> function
		 * @param pos                 the <code>BlockPos</code> position
		 * @param serverLevelAccessor the <code>ServerLevelAccessor</code>
		 * @param rand                the <code>Random</code> instance
		 * @param sbb                 the <code>MutableBoundingBox</code>
		 *//*

		@Override
		protected void handleDataMarker(@NotNull String function, @NotNull BlockPos pos, @NotNull ServerLevelAccessor serverLevelAccessor, @NotNull Random rand, @NotNull BoundingBox sbb) {
		}
	}
}*/