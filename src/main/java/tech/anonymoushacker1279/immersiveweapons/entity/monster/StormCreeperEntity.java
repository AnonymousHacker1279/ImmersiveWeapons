package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.StormCreeperSwellGoal;

import java.util.Collection;

public class StormCreeperEntity extends Monster implements PowerableMob {
	private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(StormCreeperEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(StormCreeperEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(StormCreeperEntity.class, EntityDataSerializers.BOOLEAN);
	private int oldSwell;
	private int swell;
	private int maxSwell = 30;
	private int explosionRadius = 5;
	private int droppedSkulls;

	public StormCreeperEntity(EntityType<? extends StormCreeperEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(2, new StormCreeperSwellGoal(this));
		goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Ocelot.class, 6.0F, 1.0D, 1.2D));
		goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Cat.class, 6.0F, 1.0D, 1.2D));
		goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new HurtByTargetGoal(this));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.15D);
	}

	/**
	 * The maximum height from where the entity is allowed to jump (used in pathfinder)
	 */
	@Override
	public int getMaxFallDistance() {
		return getTarget() == null ? 3 : 3 + (int) (getHealth() - 1.0F);
	}

	@Override
	public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
		boolean causeFallDamage = super.causeFallDamage(pFallDistance, pMultiplier, pSource);
		swell += (int) (pFallDistance * 1.5F);
		if (swell > maxSwell - 5) {
			swell = maxSwell - 5;
		}

		return causeFallDamage;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_SWELL_DIR, -1);
		entityData.define(DATA_IS_POWERED, false);
		entityData.define(DATA_IS_IGNITED, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		if (entityData.get(DATA_IS_POWERED)) {
			pCompound.putBoolean("powered", true);
		}

		pCompound.putShort("fuse", (short) maxSwell);
		pCompound.putByte("explosionRadius", (byte) explosionRadius);
		pCompound.putBoolean("ignited", isIgnited());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		entityData.set(DATA_IS_POWERED, pCompound.getBoolean("powered"));
		if (pCompound.contains("fuse", 99)) {
			maxSwell = pCompound.getShort("fuse");
		}

		if (pCompound.contains("explosionRadius", 99)) {
			explosionRadius = pCompound.getByte("explosionRadius");
		}

		if (pCompound.getBoolean("ignited")) {
			ignite();
		}

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		if (isAlive()) {
			oldSwell = swell;
			if (isIgnited()) {
				setSwellState(1);
			}

			int swellState = getSwellState();
			if (swellState > 0 && swell == 0) {
				playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
				gameEvent(GameEvent.PRIME_FUSE);
			}

			swell += swellState;
			if (swell < 0) {
				swell = 0;
			}

			if (swell >= maxSwell) {
				swell = maxSwell;
				explodeCreeper();
			}
		}

		super.tick();

		// Spawn a circle of smoke particles around the creeper every 8 ticks
		if (tickCount % 8 == 0) {
			for (int i = 0; i < 360; i += 30) {
				double x = getX() + 0.5 * Math.cos(i);
				double z = getZ() + 0.5 * Math.sin(i);
				level.addParticle(ParticleTypes.CLOUD, x, getY() + 0.5, z, 0, 0, 0);
			}
		}
	}

	/**
	 * Sets the active target the Goal system uses for tracking
	 */
	@Override
	public void setTarget(@Nullable LivingEntity pTarget) {
		if (!(pTarget instanceof Goat)) {
			super.setTarget(pTarget);
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.CREEPER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.CREEPER_DEATH;
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
		super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
		Entity entity = pSource.getEntity();
		if (entity instanceof StormCreeperEntity creeper) {
			if (creeper.canDropMobsSkull()) {
				creeper.increaseDroppedSkulls();
				spawnAtLocation(Items.CREEPER_HEAD);
			}
		}

	}

	@Override
	public boolean doHurtTarget(Entity pEntity) {
		return true;
	}

	@Override
	public boolean isPowered() {
		return entityData.get(DATA_IS_POWERED);
	}

	public float getSwelling(float pPartialTicks) {
		return Mth.lerp(pPartialTicks, (float) oldSwell, (float) swell) / (float) (maxSwell - 2);
	}

	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public int getSwellState() {
		return entityData.get(DATA_SWELL_DIR);
	}

	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setSwellState(int pState) {
		entityData.set(DATA_SWELL_DIR, pState);
	}

	@Override
	public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
		super.thunderHit(pLevel, pLightning);
		entityData.set(DATA_IS_POWERED, true);
	}

	@Override
	protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
		ItemStack itemInHand = pPlayer.getItemInHand(pHand);
		if (itemInHand.is(ItemTags.CREEPER_IGNITERS)) {
			SoundEvent soundEvent = itemInHand.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
			level.playSound(pPlayer, getX(), getY(), getZ(), soundEvent, getSoundSource(), 1.0F, random.nextFloat() * 0.4F + 0.8F);
			if (!level.isClientSide) {
				ignite();
				itemInHand.hurtAndBreak(1, pPlayer, (player) -> {
					player.broadcastBreakEvent(pHand);
				});
			}

			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.mobInteract(pPlayer, pHand);
		}
	}

	/**
	 * Creates an explosion as determined by this creeper's power and explosion radius.
	 */
	private void explodeCreeper() {
		if (!level.isClientSide) {
			float powerModifier = isPowered() ? 2.0F : 1.0F;
			dead = true;
			level.explode(this, getX(), getY(), getZ(), (float) explosionRadius * powerModifier, Level.ExplosionInteraction.MOB);
			discard();
			spawnLingeringCloud();
		}

	}

	private void spawnLingeringCloud() {
		Collection<MobEffectInstance> collection = getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud effectCloud = new AreaEffectCloud(level, getX(), getY(), getZ());
			effectCloud.setRadius(2.5F);
			effectCloud.setRadiusOnUse(-0.5F);
			effectCloud.setWaitTime(10);
			effectCloud.setDuration(effectCloud.getDuration() / 2);
			effectCloud.setRadiusPerTick(-effectCloud.getRadius() / (float) effectCloud.getDuration());

			for (MobEffectInstance instance : collection) {
				effectCloud.addEffect(new MobEffectInstance(instance));
			}

			level.addFreshEntity(effectCloud);
		}

	}

	public boolean isIgnited() {
		return entityData.get(DATA_IS_IGNITED);
	}

	public void ignite() {
		entityData.set(DATA_IS_IGNITED, true);
	}

	/**
	 * Returns {@code true} if an entity is able to drop its skull due to being blown up by this creeper.
	 * <p>
	 * Does not test if this creeper is charged, the caller must do that. However, does test the doMobLoot gamerule.
	 */
	public boolean canDropMobsSkull() {
		return isPowered() && droppedSkulls < 1;
	}

	public void increaseDroppedSkulls() {
		++droppedSkulls;
	}

	@Override
	public int getExperienceReward() {
		xpReward = 5 + level.random.nextInt(5);
		return super.getExperienceReward();
	}
}