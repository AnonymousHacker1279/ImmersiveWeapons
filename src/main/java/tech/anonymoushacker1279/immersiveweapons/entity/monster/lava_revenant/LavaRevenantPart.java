package tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.entity.PartEntity;

import javax.annotation.Nullable;

public class LavaRevenantPart extends PartEntity<LavaRevenantEntity> {
	public final LavaRevenantEntity parentMob;
	public final String name;
	private EntityDimensions size;
	public float baseWidth;
	public float baseHeight;

	public LavaRevenantPart(LavaRevenantEntity entity, String name, float width, float height) {
		super(entity);
		size = EntityDimensions.scalable(width, height);
		refreshDimensions();
		parentMob = entity;
		this.name = name;
		baseWidth = width;
		baseHeight = height;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag pCompound) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag pCompound) {
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Nullable
	@Override
	public ItemStack getPickResult() {
		return parentMob.getPickResult();
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		return !isInvulnerableToBase(damageSource) && parentMob.hurtFromPart(level, this, damageSource, amount);
	}

	@Override
	public boolean is(Entity entity) {
		return this == entity || parentMob == entity;
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return size;
	}

	public void setNewDimensions(float width, float height) {
		size = EntityDimensions.scalable(width, height);
		refreshDimensions();
	}

	@Override
	public boolean shouldBeSaved() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
}