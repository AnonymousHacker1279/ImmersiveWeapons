package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class CannonballEntity extends BulletEntity implements ItemSupplier {

	private boolean isExplosive = false;
	private boolean hasExploded = false;

	public CannonballEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
		super(entityType, level);
		gravityModifier = 0.055d;
	}

	public CannonballEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.CANNONBALL_ENTITY.get(), shooter, level);
	}

	public void setExplosive(boolean isExplosive) {
		this.isExplosive = isExplosive;
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(isExplosive ? ItemRegistry.EXPLOSIVE_CANNONBALL.get() : ItemRegistry.CANNONBALL.get());
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
	public DamageSource getDamageSource(@Nullable Entity owner) {
		if (owner == null) {
			owner = this;
		}

		return isExplosive ? IWDamageSources.explosiveCannonball(this, owner) : IWDamageSources.cannonball(this, owner);
	}
}