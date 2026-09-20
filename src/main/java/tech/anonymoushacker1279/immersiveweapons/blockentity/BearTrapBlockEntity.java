package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
				// Only restore the entity once. Otherwise, it would be trapped again after it escapes.
				trappedEntityUUID = null;
			}
		}

		if (trappedEntity != null) {
			trappedEntity.makeStuckInBlock(getBlockState(), new Vec3(0.0F, 0.0D, 0.0F));
			trappedEntity.setDeltaMovement(0, 0, 0);

			if (trappedEntity instanceof Mob mob) {
				mob.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 1, 9, true, true));
			}

			if (!trappedEntity.getBoundingBox().intersects(new AABB(blockPos)) || !trappedEntity.isAlive()) {
				trappedEntity = null;
			}
		}
	}

	public void trapEntity(LivingEntity entity) {
		trappedEntity = entity;
		trappedEntityUUID = null;
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
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);

		// Keep a not yet restored UUID, so the entity is not lost if the block saves before it loads
		if (trappedEntity != null) {
			valueOutput.store("UUID", UUIDUtil.CODEC, trappedEntity.getUUID());
		} else if (trappedEntityUUID != null) {
			valueOutput.store("UUID", UUIDUtil.CODEC, trappedEntityUUID);
		}
	}

	@Override
	protected void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);

		valueInput.read("UUID", UUIDUtil.CODEC).ifPresent(uuid -> trappedEntityUUID = uuid);
	}
}