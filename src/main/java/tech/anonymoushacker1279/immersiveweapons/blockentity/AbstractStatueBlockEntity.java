package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.SoldierEntity;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public abstract class AbstractStatueBlockEntity<T extends SoldierEntity> extends BlockEntity implements EntityBlock {

	protected int cooldown;
	protected int scannedEntities;
	protected final int maxNearbyEntities;

	protected static final ResourceKey<Biome> BATTLEFIELD = ResourceKey.create(Registries.BIOME, new ResourceLocation(ImmersiveWeapons.MOD_ID, "battlefield"));

	public AbstractStatueBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int maxNearbyEntities) {
		super(type, pos, blockState);
		this.maxNearbyEntities = maxNearbyEntities;
	}

	public void tick(Level level, BlockPos blockPos) {
		if (cooldown == 0) {
			if (level.getBiome(blockPos).is(BATTLEFIELD)) {
				T entity = createEntity(level);
				List<? extends LivingEntity> entitiesInArea = getEntitiesInArea(entity);

				if (entitiesInArea != null && entitiesInArea.size() <= maxNearbyEntities) {
					if (entity != null) {
						attemptSpawnEntity(entity);
					}
				}
			} else {
				cooldown = 400;
			}
		} else if (cooldown > 0) {
			cooldown--;
		}
	}

	protected void attemptSpawnEntity(T entity) {
		int i;
		for (i = 0; i < 5; i++) {
			BlockPos randomPositionInArea = getRandomPositionInArea();
			if (level != null && level.getBlockState(randomPositionInArea) == Blocks.AIR.defaultBlockState()) {
				entity.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(randomPositionInArea), MobSpawnType.SPAWNER, null, null);
				entity.moveTo(randomPositionInArea, 0.0F, 0.0F);
				level.addFreshEntity(entity);
				spawnParticles();
				break;
			}
		}

		cooldown = 400;
	}

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

	@Nullable
	protected List<? extends LivingEntity> getEntitiesInArea(@Nullable T entityClass) {
		if (level == null || entityClass == null) {
			return null;
		}

		return level.getEntitiesOfClass(entityClass.getClass(),
				new AABB(getBlockPos().getX() - 48,
						getBlockPos().getY() - 16,
						getBlockPos().getZ() - 48,
						getBlockPos().getX() + 48,
						getBlockPos().getY() + 16,
						getBlockPos().getZ() + 48));
	}

	@Nullable
	protected abstract T createEntity(Level level);

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putInt("scanCooldown", cooldown);
		pTag.putInt("scannedEntities", scannedEntities);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		cooldown = nbt.getInt("scanCooldown");
		scannedEntities = nbt.getInt("scannedEntities");
	}
}