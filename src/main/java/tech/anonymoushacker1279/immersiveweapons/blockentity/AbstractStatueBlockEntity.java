package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public abstract class AbstractStatueBlockEntity<T extends SoldierEntity> extends BlockEntity implements EntityBlock {

	protected int cooldown;
	protected final int maxNearbyEntities;
	protected int additionalEntities;
	protected float armorSpawnChance = 0.0f;
	protected float gearEnchantChance = 0.0f;

	protected static final ResourceKey<Biome> BATTLEFIELD = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "battlefield"));

	public AbstractStatueBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int maxNearbyEntities) {
		super(type, pos, blockState);
		this.maxNearbyEntities = maxNearbyEntities;
	}

	public void tick(Level level, BlockPos blockPos) {
		if (cooldown == 0) {
			cooldown = 400;

			if (level.getBlockEntity(blockPos.below()) instanceof CommanderPedestalBlockEntity blockEntity) {
				for (ItemStack augment : blockEntity.getInventory()) {
					if (augment.is(ItemRegistry.PEDESTAL_AUGMENT_SPEED.get())) {
						cooldown = Mth.floor(cooldown * 0.8f);
					} else if (augment.is(ItemRegistry.PEDESTAL_AUGMENT_ARMOR.get())) {
						armorSpawnChance += 0.25f;
					} else if (augment.is(ItemRegistry.PEDESTAL_AUGMENT_ENCHANTMENT.get())) {
						gearEnchantChance += 0.25f;
					} else if (augment.is(ItemRegistry.PEDESTAL_AUGMENT_CAPACITY.get())) {
						additionalEntities += 2;
					}
				}

				prepareEntitySpawn(level);
			} else if (level.getBiome(blockPos).is(BATTLEFIELD)) {
				prepareEntitySpawn(level);
			}

			armorSpawnChance = 0.0f;
			gearEnchantChance = 0.0f;
			additionalEntities = 0;
		} else if (cooldown > 0) {
			cooldown--;
		}
	}

	protected void prepareEntitySpawn(Level level) {
		T entity = createEntity(level);
		List<? extends LivingEntity> entitiesInArea = getEntitiesInArea(entity);

		if (entitiesInArea != null && entitiesInArea.size() <= (maxNearbyEntities + additionalEntities)) {
			if (entity != null) {
				if (entity.getRandom().nextFloat() <= armorSpawnChance) {
					for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
						if (equipmentslot.getType() == Type.HUMANOID_ARMOR) {
							ItemStack itemstack = entity.getItemBySlot(equipmentslot);

							int armorTier = 0;
							for (int i = 0; i < 5; i++) {
								if (entity.getRandom().nextFloat() <= 0.25f) {
									armorTier++;
								}
							}

							if (itemstack.isEmpty()) {
								Item item = Mob.getEquipmentForSlot(equipmentslot, armorTier);
								if (item != null) {
									entity.setItemSlot(equipmentslot, new ItemStack(item));
								}
							}
						}
					}
				}

				if (entity.getRandom().nextFloat() <= gearEnchantChance) {
					GeneralUtilities.enchantGear(entity, true, true);
				}

				attemptSpawnEntity(entity);
			}
		}
	}

	protected void attemptSpawnEntity(T entity) {
		int i;
		for (i = 0; i < 5; i++) {
			BlockPos randomPositionInArea = getRandomPositionInArea();
			if (level != null && level.getBlockState(randomPositionInArea) == Blocks.AIR.defaultBlockState()) {
				entity.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(randomPositionInArea), MobSpawnType.SPAWNER, null);
				entity.moveTo(randomPositionInArea, 0.0F, 0.0F);
				level.addFreshEntity(entity);
				spawnParticles();
				break;
			}
		}
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
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
		tag.putInt("scanCooldown", cooldown);
	}

	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);
		cooldown = tag.getInt("scanCooldown");
	}
}