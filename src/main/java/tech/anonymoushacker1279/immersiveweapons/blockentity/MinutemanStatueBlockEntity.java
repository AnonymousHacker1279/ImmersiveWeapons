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
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.BiomesAndDimensions;

import javax.annotation.Nullable;
import java.util.List;

public class MinutemanStatueBlockEntity extends BlockEntity implements EntityBlock {

	private int cooldown;
	private int scannedMinutemen;

	/**
	 * Constructor for MinutemanStatueBlockEntity.
	 */
	public MinutemanStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(DeferredRegistryHandler.MINUTEMAN_STATUE_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Runs once each tick. Handle scanning and spawning entities.
	 */
	public static void serverTick(Level level, BlockPos blockPos, MinutemanStatueBlockEntity minutemanStatueBlockEntity) {
		if (level.getBiome(blockPos).is(BiomesAndDimensions.BATTLEFIELD) && minutemanStatueBlockEntity.cooldown == 0) {
			List<MinutemanEntity> listOfMinutemenInArea = level.getEntitiesOfClass(MinutemanEntity.class,
					new AABB(minutemanStatueBlockEntity.getBlockPos().getX() - 48,
							minutemanStatueBlockEntity.getBlockPos().getY() - 16,
							minutemanStatueBlockEntity.getBlockPos().getZ() - 48,
							minutemanStatueBlockEntity.getBlockPos().getX() + 48,
							minutemanStatueBlockEntity.getBlockPos().getY() + 16,
							minutemanStatueBlockEntity.getBlockPos().getZ() + 48));

			minutemanStatueBlockEntity.scannedMinutemen = listOfMinutemenInArea.size();

			if (minutemanStatueBlockEntity.scannedMinutemen <= 16) {
				MinutemanEntity minutemanEntity = DeferredRegistryHandler.MINUTEMAN_ENTITY.get().create(level);
				if (minutemanEntity != null) {
					while (true) {
						BlockPos randomPositionInArea = minutemanStatueBlockEntity.getRandomPositionInArea();
						if (level.getBlockState(randomPositionInArea) == Blocks.AIR.defaultBlockState()) {
							minutemanEntity.moveTo(randomPositionInArea, 0.0F, 0.0F);
							level.addFreshEntity(minutemanEntity);
							minutemanStatueBlockEntity.spawnParticles();
							minutemanStatueBlockEntity.cooldown = 400;
							break;
						}
					}
				}
			}
		} else if (minutemanStatueBlockEntity.cooldown > 0) {
			minutemanStatueBlockEntity.cooldown--;
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
		return new MinutemanStatueBlockEntity(blockPos, blockState);
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
		return new BlockPos(getBlockPos().getX() + GeneralUtilities.getRandomNumber(-15, 15),
				getBlockPos().getY(),
				getBlockPos().getZ() + GeneralUtilities.getRandomNumber(-15, 15));
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
		pTag.putInt("scannedMinutemen", scannedMinutemen);
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
		scannedMinutemen = nbt.getInt("scannedMinutemen");
	}
}