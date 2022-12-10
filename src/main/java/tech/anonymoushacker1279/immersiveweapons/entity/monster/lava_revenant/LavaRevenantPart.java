package tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraftforge.entity.PartEntity;

public class LavaRevenantPart extends PartEntity<LavaRevenantEntity> {
	public final LavaRevenantEntity parentMob;
	public final String name;
	private final EntityDimensions size;

	public LavaRevenantPart(LavaRevenantEntity entity, String name, float width, float height) {
		super(entity);
		size = EntityDimensions.scalable(width, height);
		refreshDimensions();
		parentMob = entity;
		this.name = name;
	}

	@Override
	protected void defineSynchedData() {
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readAdditionalSaveData(CompoundTag pCompound) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag pCompound) {
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean isPickable() {
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		return !isInvulnerableTo(pSource) && parentMob.hurt(this, pSource, pAmount);
	}

	/**
	 * Returns true if Entity argument is equal to this Entity
	 */
	@Override
	public boolean is(Entity pEntity) {
		return this == pEntity || parentMob == pEntity;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EntityDimensions getDimensions(Pose pPose) {
		return size;
	}

	@Override
	public boolean shouldBeSaved() {
		return false;
	}
}