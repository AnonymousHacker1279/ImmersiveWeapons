package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;

public class RockSpiderEntity extends Spider implements GrantAdvancementOnDiscovery {

	private static final EntityDataAccessor<Boolean> DEEPSLATE_VARIANT = SynchedEntityData.defineId(RockSpiderEntity.class, EntityDataSerializers.BOOLEAN);

	public RockSpiderEntity(EntityType<? extends RockSpiderEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 4.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.35F)
				.add(Attributes.ATTACK_DAMAGE, 2.0D)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.STEP_HEIGHT, -0.3d);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		EntityDimensions dimensions = super.getDefaultDimensions(pose);
		return dimensions.withEyeHeight(0.15f);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnData) {
		if (blockPosition().getY() <= 0) {
			entityData.set(DEEPSLATE_VARIANT, true);
			getAttribute(Attributes.ARMOR).setBaseValue(4.0D);
		}

		return super.finalizeSpawn(levelAccessor, difficultyInstance, spawnReason, spawnData);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DEEPSLATE_VARIANT, false);
	}

	public boolean isDeepslateVariant() {
		return entityData.get(DEEPSLATE_VARIANT);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		valueOutput.putBoolean("DeepslateVariant", isDeepslateVariant());
	}

	@Override
	protected void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		entityData.set(DEEPSLATE_VARIANT, valueInput.getBooleanOr("DeepslateVariant", false));
	}
}