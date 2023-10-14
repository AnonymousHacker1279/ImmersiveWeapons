package tech.anonymoushacker1279.immersiveweapons.item.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.*;
import tech.anonymoushacker1279.immersiveweapons.init.EnchantmentRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils.HitEffect;

import java.util.ArrayList;
import java.util.List;

public class BulletItem<T extends BulletEntity> extends ArrowItem {

	private final RegistryObject<EntityType<T>> entityRegistryObject;
	private final int pierceLevel;
	private final boolean canBeInfinite;
	private final float misfireChance;
	final double gravityModifier;
	private final List<Double> shootingVectorInputs;
	private final int knockbackStrength;
	public final double damage;
	private final HitEffect hitEffect;
	private final boolean isExplosive;

	public BulletItem(Properties properties, double damage, RegistryObject<EntityType<T>> bulletEntity, int pierceLevel,
	                  boolean canBeInfinite, float misfireChance, double gravityModifier, List<Double> shootingVectorInputs,
	                  int knockbackStrength, HitEffect hitEffect, boolean isExplosive) {

		super(properties);
		this.damage = damage;
		this.entityRegistryObject = bulletEntity;
		this.pierceLevel = pierceLevel;
		this.canBeInfinite = canBeInfinite;
		this.misfireChance = misfireChance;
		this.gravityModifier = gravityModifier;
		this.shootingVectorInputs = shootingVectorInputs;
		this.knockbackStrength = knockbackStrength;
		this.hitEffect = hitEffect;
		this.isExplosive = isExplosive;
	}

	public BulletEntity createBullet(Level level, LivingEntity shooter) {
		BulletEntity bulletEntity = new BulletEntity(entityRegistryObject.get(), shooter, level);
		setCommonBulletCharacteristics(bulletEntity);

		return bulletEntity;
	}

	public FlareEntity createFlare(Level level, LivingEntity shooter) {
		FlareEntity flareEntity = new FlareEntity(entityRegistryObject.get(), shooter, level);
		setCommonBulletCharacteristics(flareEntity);

		return flareEntity;
	}

	public CannonballEntity createCannonball(Level level, LivingEntity shooter) {
		CannonballEntity cannonballEntity = new CannonballEntity(shooter, level);
		setCommonBulletCharacteristics(cannonballEntity);
		cannonballEntity.setExplosive(isExplosive);

		return cannonballEntity;
	}

	private void setCommonBulletCharacteristics(BulletEntity bulletEntity) {
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(SoundEventRegistry.BULLET_WHIZZ.get());
		bulletEntity.setPierceLevel((byte) pierceLevel);
		bulletEntity.setBaseDamage(damage);
		bulletEntity.setKnockback(knockbackStrength);
		bulletEntity.gravityModifier = gravityModifier;
		bulletEntity.shootingVectorInputs = shootingVectorInputs;
		bulletEntity.hitEffect = hitEffect;

		// Add data to entity accessors so the client is aware
		bulletEntity.getEntityData().set(BulletEntity.GRAVITY_MODIFIER_ACCESSOR, (float) gravityModifier);
	}

	public boolean canBeInfinite() {
		return canBeInfinite;
	}

	public float misfireChance() {
		return misfireChance;
	}

	/**
	 * Check if the bullet is infinite. A more flexible check than Vanilla provides.
	 * Restricts the ability to lower level bullets, for balance.
	 *
	 * @param bullet the bullet being checked
	 * @param gun    the gun firing the bullet
	 * @param player the player firing the gun
	 * @return boolean
	 */
	@Override
	public boolean isInfinite(ItemStack bullet, ItemStack gun, Player player) {
		int enchant = EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegistry.ENDLESS_MUSKET_POUCH.get(), gun);
		boolean canBeInfinite = ImmersiveWeapons.COMMON_CONFIG.allowInfiniteAmmoOnAllTiers().get() || canBeInfinite();
		return canBeInfinite && enchant > 0;
	}

	public static class BulletBuilder<T extends BulletEntity> {

		private final Properties properties;
		private final double damage;
		private final RegistryObject<EntityType<T>> bulletEntity;
		private int pierceLevel = 0;
		private boolean canBeInfinite = true;
		private float misfireChance = 0.0f;
		private double gravityModifier = 0.035d;
		private List<Double> shootingVectorInputs = List.of(0.0025d, 0.2d, 1.1d);
		private int knockbackStrength = 0;
		private HitEffect hitEffect = HitEffect.NONE;
		private boolean isExplosive = false;

		public BulletBuilder(Properties properties, double damage, RegistryObject<EntityType<T>> bulletEntity) {
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

		public BulletBuilder<T> shootingVector(double inaccuracyBaseMultiplier, double inaccuracyRandomModifierLow, double inaccuracyRandomModifierHigh) {
			shootingVectorInputs = new ArrayList<>(5);
			shootingVectorInputs.add(inaccuracyBaseMultiplier);
			shootingVectorInputs.add(inaccuracyRandomModifierLow);
			shootingVectorInputs.add(inaccuracyRandomModifierHigh);
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
			return new BulletItem<>(properties, damage, bulletEntity, pierceLevel, canBeInfinite, misfireChance, gravityModifier, shootingVectorInputs, knockbackStrength, hitEffect, isExplosive);
		}
	}
}