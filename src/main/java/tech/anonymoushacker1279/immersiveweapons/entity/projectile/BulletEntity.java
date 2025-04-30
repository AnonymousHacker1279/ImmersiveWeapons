package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact.BulletImpactParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;
import tech.anonymoushacker1279.immersiveweapons.network.payload.BulletEntityDebugPayload;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunShotBloodParticlePayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.util.List;

public class BulletEntity extends CustomArrowEntity implements HitEffectUtils {

	private final SoundEvent hitSound = getDefaultHitGroundSoundEvent();
	private boolean hasAlreadyBrokeGlass = false;
	private Vec3 initialPos = Vec3.ZERO;
	public List<Double> shootingVectorInputs = List.of(0.0025d, 0.2d, 1.1d);
	public HitEffect hitEffect = HitEffect.NONE;
	public boolean flameTrail = false;
	private static final TagKey<Block> BULLETPROOF_GLASS = BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", "bulletproof_glass"));

	public BulletEntity(EntityType<? extends Arrow> entityType, Level level) {
		super(entityType, level);
	}

	public BulletEntity(EntityType<? extends BulletEntity> entityType, LivingEntity shooter, Level level, @Nullable ItemStack firedFromWeapon) {
		super(entityType, shooter, level, firedFromWeapon);
		initialPos = shooter.position();
	}

	public static class BulletEntityBuilder implements HitEffectUtils {

		private final EntityType<? extends Arrow> entityType;
		private final Item referenceItem;

		public BulletEntityBuilder(EntityType<? extends Arrow> entityType, Item referenceItem) {
			this.entityType = entityType;
			this.referenceItem = referenceItem;
		}

		public BulletEntity build(Level level) {
			BulletEntity bulletEntity = new BulletEntity(entityType, level);
			bulletEntity.referenceItem = referenceItem;

			return bulletEntity;
		}
	}

	public float calculateDamage() {
		float velocityModifier = (float) getDeltaMovement().length();
		// Determine the damage to be dealt, which is calculated by multiplying the velocity modifier
		// and the base damage. It's clamped if the velocity is extremely high.
		int damage = Mth.ceil(Mth.clamp(velocityModifier * getBaseDamage(), 0.0D, 2.147483647E9D));

		// Add crit modifier if the bullet is critical
		if (isCritArrow()) {
			int randomCritModifier = random.nextInt(damage / 2 + 2);
			damage = (int) Math.min(randomCritModifier + damage, 2.147483647E9D);
		}

		// If the entity is a player and has an active Deadeye Pendant, add a damage modifier which increases with distance to the target
		// This modifier increases damage up to a maximum of +20% at 100 meters range
		if (getOwner() instanceof Player player) {
			if (Accessory.isAccessoryActive(player, ItemRegistry.DEADEYE_PENDANT.get())) {
				double distance = player.distanceToSqr(getX(), getY(), getZ());
				double modifier = Math.min(distance / 100d, 1);
				damage = (int) Math.round(damage * (1 + modifier * 0.2f));
			}
		}

		return damage;
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		lastState = this.level().getBlockState(hitResult.getBlockPos());
		boolean didPassThroughBlock = false;

		// Check if the bullet hit a permeable block like leaves, if so keep moving and decrease velocity
		if (lastState.is(BlockTags.LEAVES)) {
			push(0, -0.1, 0);
			shakeTime = 4;
			didPassThroughBlock = true;
		} else {
			// This is similar to a super call but is more flexible with the sounds
			Vec3 delta = hitResult.getLocation().subtract(getX(), getY(), getZ());
			setDeltaMovement(delta);
			Vec3 scaledPosition = delta.normalize().scale(0.0025F);
			setPosRaw(getX() - scaledPosition.x, getY() - scaledPosition.y, getZ() - scaledPosition.z);
			playSound(hitSound, 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			setInGround(true);
			shakeTime = 7;
			setCritArrow(false);
			setPierceLevel((byte) 0);
			setSoundEvent(hitSound);
			resetPiercedEntities();
		}

		// Check if glass can be broken, and if it hasn't already broken glass
		if (IWConfigs.SERVER.bulletsBreakGlass.getAsBoolean() && !hasAlreadyBrokeGlass
				&& !lastState.is(BULLETPROOF_GLASS)
				&& (lastState.is(Blocks.GLASS_BLOCKS) || lastState.is(Tags.Blocks.GLASS_PANES))) {

			level().destroyBlock(hitResult.getBlockPos(), false);
			hasAlreadyBrokeGlass = true;
		}

		lastState.onProjectileHit(level(), lastState, hitResult, this);

		// Check if a solid block was hit
		if (!didPassThroughBlock) {
			level().addParticle(new BulletImpactParticleOptions(1.0F, Block.getId(lastState)),
					hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z,
					GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
					GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
					GeneralUtilities.getRandomNumber(-0.01d, 0.01d));
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		if (!level().isClientSide && getOwner() instanceof ServerPlayer player && initialPos != Vec3.ZERO) {
			Vec3 location = result.getLocation();
			double distance = Math.sqrt(Math.pow(location.x - initialPos.x, 2) + Math.pow(location.y - initialPos.y, 2) + Math.pow(location.z - initialPos.z, 2));

			if (distance > 200 && player.getServer() != null) {
				AdvancementHolder advancement = player.getServer().getAdvancements().get(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "firearm_long_range"));

				if (advancement != null) {
					player.getAdvancements().award(advancement, "");
				}
			}
		}
	}

	@Override
	protected void doWhenHitEntity(Entity entity) {
		super.doWhenHitEntity(entity);

		if (level() instanceof ServerLevel serverLevel) {
			PacketDistributor.sendToPlayersTrackingChunk(serverLevel, chunkPosition(),
					new GunShotBloodParticlePayload(
							ParticleTypesRegistry.BLOOD_PARTICLE.get(),
							position().x, position().y, position().z
					));
		}
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEventRegistry.BULLET_WHIZZ.get();
	}

	@Override
	public DamageSource getDamageSource(@Nullable Entity owner) {
		if (owner == null) {
			owner = this;
		}

		return IWDamageSources.bullet(this, owner);
	}

	@Override
	public void remove(RemovalReason removalReason) {
		super.remove(removalReason);
		DebugTracingData.liveBulletDamage = 0;
		DebugTracingData.isBulletCritical = false;
	}

	@Override
	protected void doWhileTicking() {
		if (tickCount % 10 == 0 && !isInGround() && getOwner() instanceof ServerPlayer player) {
			if (!level().isClientSide) {
				PacketDistributor.sendToPlayer(player, new BulletEntityDebugPayload(calculateDamage(), isCritArrow()));
			}
		}

		if (!isInGround() && flameTrail && level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
					ParticleTypes.SMALL_FLAME,
					position().x, position().y, position().z,
					8,
					random.nextGaussian() * 0.05,
					random.nextGaussian() * 0.05,
					random.nextGaussian() * 0.05,
					random.nextGaussian() * 0.025);
		}

		if (!isInGround() && tickCount % 2 == 0 && level().isClientSide) {
			Player player = level().getNearestPlayer(this, 5.0D);
			if (player != null) {
				float pitch = (float) (0.8F + (getDeltaMovement().length() * 0.2) + (distanceTo(player) * 0.05));
				player.playSound(SoundEventRegistry.BULLET_WHIZZ.get(), 1.0F, pitch);
			}
		}
	}


	@Override
	protected void tickDespawn() {
		for (int i = 0; i < IWConfigs.SERVER.bulletDespawnTimeModifier.get(); i++) {
			super.tickDespawn();
		}
	}
}