package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class CannonballEntity extends BulletEntity implements ItemSupplier {

	private boolean isExplosive = false;
	private boolean hasExploded = false;

	public CannonballEntity(EntityType<? extends AbstractArrow> entityType, Level level, int knockbackStrength) {
		super(entityType, level);
		this.knockbackStrength = knockbackStrength;
	}

	public CannonballEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.CANNONBALL_ENTITY.get(), shooter, level);
	}

	public void setExplosive(boolean isExplosive) {
		this.isExplosive = isExplosive;
	}

	@Override
	public @NotNull Item getReferenceItem() {
		return isExplosive ? ItemRegistry.EXPLOSIVE_CANNONBALL.get() : ItemRegistry.CANNONBALL.get();
	}

	@Override
	public double getGravityModifier() {
		return 0.055d;
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(getReferenceItem());
	}

	@Override
	protected void doWhenHitEntity(Entity entity) {
		super.doWhenHitEntity(entity);

		if (isExplosive && !hasExploded) {
			explode();
		}
	}

	@Override
	protected void doWhenHitBlock() {
		super.doWhenHitBlock();

		if (isExplosive && !hasExploded) {
			explode();
		}
	}

	private void explode() {
		if (!level().isClientSide && getOwner() != null) {
			level().explode(this,
					IWDamageSources.explosiveCannonball(this, getOwner()),
					null,
					position(),
					1.5f,
					false,
					ExplosionInteraction.NONE);

			hasExploded = true;
		}
	}

	@Override
	protected DamageSource getDamageSource(@Nullable Entity owner) {
		if (owner == null) {
			owner = this;
		}

		return isExplosive ? IWDamageSources.explosiveCannonball(this, owner) : IWDamageSources.cannonball(this, owner);
	}
}