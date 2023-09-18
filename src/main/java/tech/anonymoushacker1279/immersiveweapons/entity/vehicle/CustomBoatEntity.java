package tech.anonymoushacker1279.immersiveweapons.entity.vehicle;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CustomBoatEntity extends Boat {

	private final Item dropItem;

	public CustomBoatEntity(EntityType<? extends Boat> type, Level level, Item dropItem) {
		super(type, level);
		this.dropItem = dropItem;
	}

	@Override
	public Item getDropItem() {
		return dropItem;
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putString("Type", getCustomBoatType().getName());
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		if (compound.contains("Type", 8)) {
			setBoatType(CustomBoatType.getTypeFromString(compound.getString("Type")));
		}
	}

	@Override
	protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
		lastYd = getDeltaMovement().y;

		if (!isPassenger()) {
			if (pOnGround) {
				if (fallDistance > 3.0F) {
					if (status != Boat.Status.ON_LAND) {
						resetFallDistance();
						return;
					}

					causeFallDamage(fallDistance, 1.0F, damageSources().fall());
					if (!level().isClientSide && !isRemoved()) {
						kill();
						if (level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
							for (int i = 0; i < 3; ++i) {
								spawnAtLocation(getCustomBoatType().getBlock());
							}

							for (int j = 0; j < 2; ++j) {
								spawnAtLocation(Items.STICK);
							}
						}
					}
				}

				resetFallDistance();
			} else if (!canBoatInFluid(level().getFluidState(blockPosition().below())) && pY < 0.0D) {
				fallDistance -= (float) pY;
			}

		}
	}

	public void setBoatType(CustomBoatType boatType) {
		entityData.set(DATA_ID_TYPE, boatType.ordinal());
	}

	public CustomBoatType getCustomBoatType() {
		return CustomBoatType.byId(entityData.get(DATA_ID_TYPE));
	}

}