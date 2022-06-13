package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.FieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.BiomesAndDimensions;

import javax.annotation.Nullable;
import java.util.List;

public class MedicStatueBlockEntity extends BlockEntity implements EntityBlock {

	private int cooldown;
	private int scannedMedics;

	/**
	 * Constructor for MedicStatueBlockEntity.
	 */
	public MedicStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(DeferredRegistryHandler.MEDIC_STATUE_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Runs once each tick. Handle scanning and spawning entities.
	 */
	public static void serverTick(Level level, BlockPos blockPos, MedicStatueBlockEntity medicStatueBlockEntity) {
		if (level.getBiome(blockPos).is(BiomesAndDimensions.BATTLEFIELD) && medicStatueBlockEntity.cooldown == 0) {
			List<FieldMedicEntity> listOfMedicsInArea = level.getEntitiesOfClass(FieldMedicEntity.class,
					new AABB(medicStatueBlockEntity.getBlockPos().getX() - 48,
							medicStatueBlockEntity.getBlockPos().getY() - 16,
							medicStatueBlockEntity.getBlockPos().getZ() - 48,
							medicStatueBlockEntity.getBlockPos().getX() + 48,
							medicStatueBlockEntity.getBlockPos().getY() + 16,
							medicStatueBlockEntity.getBlockPos().getZ() + 48));

			medicStatueBlockEntity.scannedMedics = listOfMedicsInArea.size();

			if (medicStatueBlockEntity.scannedMedics <= 1) {
				FieldMedicEntity fieldMedicEntity = DeferredRegistryHandler.FIELD_MEDIC_ENTITY.get().create(level);
				if (fieldMedicEntity != null) {
					while (true) {
						BlockPos randomPositionInArea = medicStatueBlockEntity.getRandomPositionInArea();
						if (level.getBlockState(randomPositionInArea) == Blocks.AIR.defaultBlockState()) {
							fieldMedicEntity.moveTo(randomPositionInArea, 0.0F, 0.0F);
							level.addFreshEntity(fieldMedicEntity);
							medicStatueBlockEntity.spawnParticles();
							medicStatueBlockEntity.cooldown = 400;
							break;
						}
					}
				}
			}
		} else if (medicStatueBlockEntity.cooldown > 0) {
			medicStatueBlockEntity.cooldown--;
		}
	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new MedicStatueBlockEntity(blockPos, blockState);
	}

	/**
	 * Spawn particles.
	 */
	private void spawnParticles() {
		ServerLevel serverWorld = (ServerLevel) getLevel();
		if (serverWorld != null) {
			serverWorld.sendParticles(ParticleTypes.HAPPY_VILLAGER,
					getBlockPos().getX() + 0.5d,
					getBlockPos().getY(),
					getBlockPos().getZ() + 0.75d,
					5,
					GeneralUtilities.getRandomNumber(-0.05d, 0.05d),
					GeneralUtilities.getRandomNumber(-0.25d, 0.25d),
					GeneralUtilities.getRandomNumber(-0.05d, 0.05d),
					GeneralUtilities.getRandomNumber(-0.15d, 0.15d));
		}
	}

	/**
	 * Get a random position in the nearby area.
	 *
	 * @return BlockPos
	 */
	private BlockPos getRandomPositionInArea() {
		return new BlockPos(getBlockPos().getX() + GeneralUtilities.getRandomNumber(-8, 8),
				getBlockPos().getY(),
				getBlockPos().getZ() + GeneralUtilities.getRandomNumber(-8, 8));
	}

	/**
	 * Save NBT data.
	 *
	 * @param pTag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(@NotNull CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putInt("scanCooldown", cooldown);
		pTag.putInt("scannedMedics", scannedMedics);
	}

	/**
	 * Load NBT data.
	 *
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(@NotNull CompoundTag nbt) {
		super.load(nbt);
		cooldown = nbt.getInt("scanCooldown");
		scannedMedics = nbt.getInt("scannedMedics");
	}
}