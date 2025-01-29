package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SmokeGrenadePayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.util.List;

public class CustomArrowEntity extends Arrow implements HitEffectUtils {

	protected static final byte VANILLA_IMPACT_STATUS_ID = 3;
	public Item referenceItem = Items.AIR;
	protected boolean shouldStopMoving = false;
	public List<Double> shootingVectorInputs = List.of(0.0075d, -0.0095d, 0.0075d);
	public HitEffect hitEffect = HitEffect.NONE;
	public int color = -1;
	public boolean isExplosive = false;
	private boolean hasExploded = false;

	public CustomArrowEntity(EntityType<? extends Arrow> type, Level level) {
		super(type, level);
	}

	public CustomArrowEntity(EntityType<? extends Arrow> type, LivingEntity shooter, Level level, @Nullable ItemStack weapon) {
		this(type, level);
		setPos(shooter.getX(), shooter.getY() + shooter.getEyeHeight() - 0.1f, shooter.getZ());
		setOwner(shooter);
		firedFromWeapon = weapon;

		pickupItemStack = pickupItemStack.copy();
		setCustomName(pickupItemStack.get(DataComponents.CUSTOM_NAME));
		Unit unit = pickupItemStack.remove(DataComponents.INTANGIBLE_PROJECTILE);
		if (unit != null) {
			this.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
		}

		if (firedFromWeapon != null && level instanceof ServerLevel serverlevel) {
			if (firedFromWeapon.isEmpty()) {
				throw new IllegalArgumentException("Invalid weapon firing an arrow");
			}

			firedFromWeapon = firedFromWeapon.copy();
			int i = EnchantmentHelper.getPiercingCount(serverlevel, firedFromWeapon, pickupItemStack);
			if (i > 0) {
				this.setPierceLevel((byte) i);
			}

			EnchantmentHelper.onProjectileSpawned(serverlevel, firedFromWeapon, this, item -> this.firedFromWeapon = null);
		}
	}

	public static class ArrowEntityBuilder implements HitEffectUtils {

		private final EntityType<? extends Arrow> entityType;
		private final Item referenceItem;

		public ArrowEntityBuilder(EntityType<? extends Arrow> entityType, Item referenceItem) {
			this.entityType = entityType;
			this.referenceItem = referenceItem;
		}

		public CustomArrowEntity build(Level level) {
			CustomArrowEntity arrowEntity = new CustomArrowEntity(entityType, level);
			arrowEntity.referenceItem = referenceItem;

			return arrowEntity;
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(referenceItem);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
		return super.getAddEntityPacket(entity);
	}

	@Override
	public void tick() {
		super.tick();

		if (color != -1 && inGroundTime > 0) {
			if (level() instanceof ServerLevel serverLevel && tickCount % 2 == 0) {
				serverLevel.getEntities(this, getBoundingBox().inflate(IWConfigs.SERVER.smokeGrenadeEffectRange.getAsDouble()))
						.stream()
						.filter(entity -> !entity.isSpectator())
						.forEach(entity -> {
							if (entity instanceof Mob mob && !mob.getType().is(EntityTypes.BOSSES)) {
								if (AdvancedThrowableItemProjectile.canSee(mob, this, false)) {
									mob.setTarget(null);
								}
							}
						});
			}
		}

		// Extra stuff to do while ticking, for child classes
		doWhileTicking();
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		if (color != -1 && level() instanceof ServerLevel serverLevel) {
			PacketDistributor.sendToPlayersTrackingChunk(serverLevel, chunkPosition(), new SmokeGrenadePayload(getX(), getY(), getZ(), color, IWConfigs.SERVER.forceSmokeGrenadeParticles.getAsInt()));
		}

		if (isExplosive && !hasExploded) {
			explode(1.5f);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);

		// Extra code to run when an entity is hit
		doWhenHitEntity(result.getEntity());

		if (result.getEntity() instanceof LivingEntity livingEntity) {
			// Apply any hit effects from the arrow
			switch (hitEffect) {
				case MOLTEN -> {
					if (getOwner() instanceof LivingEntity owner) {
						addMoltenEffects(livingEntity, owner);
					}
				}
				case TESLA -> addTeslaEffects(livingEntity);
				case VENTUS -> addVentusEffects(livingEntity);
			}
		}
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		Vec3 shootingVector = new Vec3(x, y, z)
				.normalize()
				.add(random.triangle(0d,
								shootingVectorInputs.get(0) * inaccuracy
										* GeneralUtilities.getRandomNumber(shootingVectorInputs.get(1), shootingVectorInputs.get(2))),
						random.triangle(0d,
								shootingVectorInputs.get(0) * inaccuracy
										* GeneralUtilities.getRandomNumber(shootingVectorInputs.get(1), shootingVectorInputs.get(2))),
						random.triangle(0d,
								shootingVectorInputs.get(0) * inaccuracy
										* GeneralUtilities.getRandomNumber(shootingVectorInputs.get(1), shootingVectorInputs.get(2))))
				.scale(velocity);

		setDeltaMovement(shootingVector);
		double horizontalDistanceSqr = shootingVector.horizontalDistanceSqr();
		setYRot((float) (Mth.atan2(shootingVector.x, shootingVector.z) * (180F / (float) Math.PI)));
		setXRot((float) (Mth.atan2(shootingVector.y, horizontalDistanceSqr) * (180F / (float) Math.PI)));
		yRotO = getYRot();
		xRotO = getXRot();
	}

	/**
	 * Additional stuff to do while ticking. Runs early in the tick method.
	 */
	protected void doWhileTicking() {
	}

	/**
	 * Additional stuff to do when an entity is hit.
	 *
	 * @param entity the <code>Entity</code> being hit
	 */
	protected void doWhenHitEntity(Entity entity) {
		level().broadcastEntityEvent(this, VANILLA_IMPACT_STATUS_ID);
	}

	protected void explode(float radius) {
		if (!level().isClientSide && getOwner() != null) {
			level().explode(this,
					getDamageSource(getOwner()),
					null,
					position(),
					radius,
					false,
					ExplosionInteraction.NONE);

			// Get nearby entities in the radius and apply hit effects
			if (hitEffect != HitEffect.NONE) {
				level().getEntities(this, getBoundingBox().inflate(radius))
						.stream()
						.filter(entity -> {
							if (entity instanceof LivingEntity livingEntity) {
								return livingEntity.hasLineOfSight(this);
							} else {
								return false;
							}
						})
						.forEach(entity -> {
							LivingEntity livingEntity = (LivingEntity) entity;
							switch (hitEffect) {
								case MOLTEN -> addMoltenEffects(livingEntity, (LivingEntity) getOwner());
								case TESLA -> addTeslaEffects(livingEntity);
								case VENTUS -> addVentusEffects(livingEntity);
							}
						});
			}

			hasExploded = true;
		}
	}

	public DamageSource getDamageSource(@Nullable Entity owner) {
		if (owner == null) {
			owner = this;
		}

		return isExplosive ? IWDamageSources.explosiveArrow(this, owner) : damageSources().arrow(this, owner);
	}
}