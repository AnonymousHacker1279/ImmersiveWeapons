package com.anonymoushacker1279.immersiveweapons.entity.vehicle;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class BurnedOakBoatEntity extends Boat {

	public BurnedOakBoatEntity(EntityType<? extends BurnedOakBoatEntity> type, Level level) {
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
		return getCustomBoatType().getBoatItem().asItem();
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

	public void setBoatType(@NotNull CustomBoatType boatType) {
		entityData.set(DATA_ID_TYPE, boatType.getId());
	}

	public CustomBoatType getCustomBoatType() {
		return CustomBoatType.byId(entityData.get(DATA_ID_TYPE));
	}

	@Override
	public @NotNull Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}