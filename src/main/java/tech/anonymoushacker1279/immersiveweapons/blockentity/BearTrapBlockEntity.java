package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

import java.util.UUID;


public class BearTrapBlockEntity extends BlockEntity implements EntityBlock {

	@Nullable
	private LivingEntity trappedEntity;
	@Nullable
	private UUID trappedEntityUUID;

	public BearTrapBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.BEAR_TRAP_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	public void tick(BlockPos blockPos) {
		if (trappedEntityUUID != null && level instanceof ServerLevel serverLevel) {
			if (serverLevel.getEntity(trappedEntityUUID) instanceof LivingEntity livingEntity) {
				trappedEntity = livingEntity;
			}
		}

		if (trappedEntity != null) {
			trappedEntity.makeStuckInBlock(getBlockState(), new Vec3(0.0F, 0.0D, 0.0F));
			trappedEntity.setDeltaMovement(0, 0, 0);

			if (trappedEntity instanceof Mob mob) {
				mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 9, true, true));
			}

			if (!trappedEntity.getBoundingBox().intersects(new AABB(blockPos)) || !trappedEntity.isAlive()) {
				trappedEntity = null;
			}
		}
	}

	public void trapEntity(LivingEntity entity) {
		trappedEntity = entity;
	}

	@Nullable
	public LivingEntity getTrappedEntity() {
		return trappedEntity;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BearTrapBlockEntity(pos, state);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, Provider registries) {
		super.saveAdditional(tag, registries);

		if (trappedEntity != null) {
			tag.putUUID("trappedEntity", trappedEntity.getUUID());
		}
	}

	@Override
	protected void loadAdditional(CompoundTag tag, Provider registries) {
		super.loadAdditional(tag, registries);

		if (tag.hasUUID("trappedEntity")) {
			trappedEntityUUID = tag.getUUID("trappedEntity");
		}
	}
}