package tech.anonymoushacker1279.immersiveweapons.entity.vehicle;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class BurnedOakBoatEntity extends Boat {

	public BurnedOakBoatEntity(EntityType<? extends Boat> type, Level level) {
		super(type, level);
	}

	public BurnedOakBoatEntity(Level level, double x, double y, double z) {
		this(DeferredRegistryHandler.BURNED_OAK_BOAT_ENTITY.get(), level);
		setPos(x, y, z);
		setDeltaMovement(Vec3.ZERO);
		xOld = x;
		yOld = y;
		zOld = z;
	}

	@Override
	public @NotNull Item getDropItem() {
		return DeferredRegistryHandler.BURNED_OAK_BOAT.get();
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
	protected void checkFallDamage(double pY, boolean pOnGround, @NotNull BlockState pState, @NotNull BlockPos pPos) {
		lastYd = getDeltaMovement().y;
		if (!isPassenger()) {
			if (pOnGround) {
				if (fallDistance > 3.0F) {
					if (status != Boat.Status.ON_LAND) {
						resetFallDistance();
						return;
					}

					causeFallDamage(fallDistance, 1.0F, DamageSource.FALL);
					if (!level.isClientSide && !isRemoved()) {
						kill();
						if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
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
			} else if (!canBoatInFluid(level.getFluidState(blockPosition().below())) && pY < 0.0D) {
				fallDistance -= (float) pY;
			}

		}
	}

	public void setBoatType(@NotNull CustomBoatType boatType) {
		entityData.set(DATA_ID_TYPE, boatType.ordinal());
	}

	public CustomBoatType getCustomBoatType() {
		return CustomBoatType.byId(entityData.get(DATA_ID_TYPE));
	}

	@Override
	public @NotNull Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}