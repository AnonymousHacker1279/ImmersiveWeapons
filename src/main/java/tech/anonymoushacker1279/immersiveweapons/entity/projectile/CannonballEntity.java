package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class CannonballEntity extends BulletEntity implements ItemSupplier {

	public CannonballEntity(EntityType<? extends Arrow> entityType, Level level) {
		super(entityType, level);
	}

	public CannonballEntity(LivingEntity shooter, Level level, @Nullable ItemStack firedFromWeapon) {
		super(EntityRegistry.CANNONBALL_ENTITY.get(), shooter, level, firedFromWeapon);
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(isExplosive ? ItemRegistry.EXPLOSIVE_CANNONBALL.get() : ItemRegistry.CANNONBALL.get());
	}

	@Override
	public DamageSource getDamageSource(@Nullable Entity owner) {
		if (owner == null) {
			owner = this;
		}

		return isExplosive ? IWDamageSources.explosiveCannonball(this, owner) : IWDamageSources.cannonball(this, owner);
	}
}