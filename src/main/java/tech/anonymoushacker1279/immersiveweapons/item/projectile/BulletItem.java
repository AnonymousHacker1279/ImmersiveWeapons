package tech.anonymoushacker1279.immersiveweapons.item.projectile;

import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CannonballEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.DragonFireballBulletEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.FlareEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.CustomArrowItem.InaccuracySettings;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils.HitEffect;
import tech.anonymoushacker1279.immersiveweapons.util.ArrowAttributeAccessor;

import java.util.Optional;
import java.util.function.Supplier;

public class BulletItem<T extends BulletEntity> extends ArrowItem {

	public final Supplier<EntityType<T>> entitySupplier;
	public final int pierceLevel;
	public final boolean canBeInfinite;
	public final float misfireChance;
	public final double gravityModifier;
	public final InaccuracySettings inaccuracySettings;
	public final int knockbackStrength;
	public final double damage;
	public final HitEffect hitEffect;
	public final boolean isExplosive;

	public BulletItem(Properties properties, double damage, Supplier<EntityType<T>> bulletEntity, int pierceLevel,
	                  boolean canBeInfinite, float misfireChance, double gravityModifier, InaccuracySettings inaccuracySettings,
	                  int knockbackStrength, HitEffect hitEffect, boolean isExplosive) {

		super(properties);
		this.damage = damage;
		this.entitySupplier = bulletEntity;
		this.pierceLevel = pierceLevel;
		this.canBeInfinite = canBeInfinite;
		this.misfireChance = misfireChance;
		this.gravityModifier = gravityModifier;
		this.inaccuracySettings = inaccuracySettings;
		this.knockbackStrength = knockbackStrength;
		this.hitEffect = hitEffect;
		this.isExplosive = isExplosive;
	}

	public BulletEntity createBullet(Level level, LivingEntity shooter, ItemStack gun) {
		BulletEntity bulletEntity = new BulletEntity(entitySupplier.get(), shooter, level, gun);
		setCommonBulletCharacteristics(bulletEntity);

		return bulletEntity;
	}

	public FlareEntity createFlare(Level level, LivingEntity shooter, ItemStack gun) {
		FlareEntity flareEntity = new FlareEntity(entitySupplier.get(), shooter, level, gun);
		setCommonBulletCharacteristics(flareEntity);

		return flareEntity;
	}

	public CannonballEntity createCannonball(Level level, LivingEntity shooter, ItemStack gun) {
		CannonballEntity cannonballEntity = new CannonballEntity(shooter, level, gun);
		setCommonBulletCharacteristics(cannonballEntity);
		cannonballEntity.isExplosive = isExplosive;

		return cannonballEntity;
	}

	public DragonFireballBulletEntity createDragonFireball(Level level, LivingEntity shooter, ItemStack gun) {
		DragonFireballBulletEntity dragonFireballBulletEntity = new DragonFireballBulletEntity(shooter, level, gun);
		setCommonBulletCharacteristics(dragonFireballBulletEntity);

		return dragonFireballBulletEntity;
	}

	private void setCommonBulletCharacteristics(BulletEntity bulletEntity) {
		bulletEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(SoundEventRegistry.BULLET_WHIZZ.get());
		bulletEntity.setPierceLevel((byte) pierceLevel);
		bulletEntity.setBaseDamage(damage);
		((ArrowAttributeAccessor) bulletEntity).immersiveWeapons$setBaseKnockback(knockbackStrength);
		((ArrowAttributeAccessor) bulletEntity).immersiveWeapons$setGravity(gravityModifier);
		bulletEntity.inaccuracySettings = inaccuracySettings;
		bulletEntity.hitEffect = hitEffect;
	}

	/**
	 * Check if the bullet is infinite. Restricts the ability to lower level bullets for balance.
	 *
	 * @param bullet  the bullet being checked
	 * @param gun     the gun firing the bullet
	 * @param shooter the entity firing the gun
	 * @return boolean
	 */
	@Override
	public boolean isInfinite(ItemStack bullet, ItemStack gun, LivingEntity shooter) {
		HolderGetter<Enchantment> enchantmentGetter = shooter.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
		Optional<Reference<Enchantment>> endlessMusketPouch = enchantmentGetter.get(IWEnchantments.ENDLESS_MUSKET_POUCH);

		int enchantmentLevel = 0;
		if (endlessMusketPouch.isPresent()) {
			enchantmentLevel = gun.getEnchantmentLevel(endlessMusketPouch.get());
		}

		return (IWConfigs.SERVER.infiniteAmmoOnAllTiers.getAsBoolean() || canBeInfinite) && enchantmentLevel > 0;
	}

	public static class BulletBuilder<T extends BulletEntity> {

		final Properties properties;
		final double damage;
		final Supplier<EntityType<T>> bulletEntity;
		int pierceLevel = 0;
		boolean canBeInfinite = true;
		float misfireChance = 0.0f;
		double gravityModifier = 0.035d;
		InaccuracySettings inaccuracySettings = new InaccuracySettings(1.0d);
		int knockbackStrength = 0;
		HitEffect hitEffect = HitEffect.NONE;
		boolean isExplosive = false;

		public BulletBuilder(Properties properties, double damage, Supplier<EntityType<T>> bulletEntity) {
			this.properties = properties;
			this.damage = damage;
			this.bulletEntity = bulletEntity;
		}

		public BulletBuilder<T> pierceLevel(int pierceLevel) {
			this.pierceLevel = pierceLevel;
			return this;
		}

		public BulletBuilder<T> canBeInfinite(boolean canBeInfinite) {
			this.canBeInfinite = canBeInfinite;
			return this;
		}

		public BulletBuilder<T> misfireChance(float misfireChance) {
			this.misfireChance = misfireChance;
			return this;
		}

		public BulletBuilder<T> gravityModifier(double gravityModifier) {
			this.gravityModifier = gravityModifier;
			return this;
		}

		public BulletBuilder<T> inaccuracySettings(double modifier) {
			this.inaccuracySettings = new InaccuracySettings(modifier);
			return this;
		}

		public BulletBuilder<T> inaccuracySettings(double xModifier, double yModifier, double zModifier) {
			this.inaccuracySettings = new InaccuracySettings(xModifier, yModifier, zModifier);
			return this;
		}

		public BulletBuilder<T> knockbackStrength(int knockbackStrength) {
			this.knockbackStrength = knockbackStrength;
			return this;
		}

		public BulletBuilder<T> hitEffect(HitEffect hitEffect) {
			this.hitEffect = hitEffect;
			return this;
		}

		public BulletBuilder<T> isExplosive(boolean isExplosive) {
			this.isExplosive = isExplosive;
			return this;
		}

		public BulletItem<T> build() {
			return new BulletItem<>(properties, damage, bulletEntity, pierceLevel, canBeInfinite, misfireChance, gravityModifier, inaccuracySettings, knockbackStrength, hitEffect, isExplosive);
		}
	}
}