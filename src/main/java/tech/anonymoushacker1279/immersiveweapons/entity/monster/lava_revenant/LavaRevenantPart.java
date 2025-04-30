package tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.neoforged.neoforge.entity.PartEntity;

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
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
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

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		return parentMob.hurtServer(level, damageSource, amount);
	}

	/**
	 * Returns true if Entity argument is equal to this Entity
	 */
	@Override
	public boolean is(Entity pEntity) {
		return this == pEntity || parentMob == pEntity;
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