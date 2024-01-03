package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public abstract class AbstractStatueBlockEntity extends BlockEntity implements EntityBlock {

	protected int cooldown;
	protected int scannedEntities;

	protected static final ResourceKey<Biome> BATTLEFIELD = ResourceKey.create(Registries.BIOME, new ResourceLocation(ImmersiveWeapons.MOD_ID, "battlefield"));

	public AbstractStatueBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	protected void attemptSpawnEntity(LivingEntity entity) {
		int i;
		for (i = 0; i < 5; i++) {
			BlockPos randomPositionInArea = getRandomPositionInArea();
			if (level != null && level.getBlockState(randomPositionInArea) == Blocks.AIR.defaultBlockState()) {
				entity.moveTo(randomPositionInArea, 0.0F, 0.0F);
				level.addFreshEntity(entity);
				spawnParticles();
				break;
			}
		}

		cooldown = 400;
	}

	/**
	 * Spawn particles.
	 */
	protected void spawnParticles() {
		if (getLevel() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
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
	protected BlockPos getRandomPositionInArea() {
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
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putInt("scanCooldown", cooldown);
		pTag.putInt("scannedEntities", scannedEntities);
	}

	/**
	 * Load NBT data.
	 *
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		cooldown = nbt.getInt("scanCooldown");
		scannedEntities = nbt.getInt("scannedEntities");
	}
}