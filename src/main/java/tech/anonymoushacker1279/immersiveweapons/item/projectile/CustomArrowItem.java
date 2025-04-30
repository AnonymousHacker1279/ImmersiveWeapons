package tech.anonymoushacker1279.immersiveweapons.item.projectile;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Position;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils.HitEffect;
import tech.anonymoushacker1279.immersiveweapons.util.ArrowAttributeAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CustomArrowItem<T extends CustomArrowEntity> extends ArrowItem {

	public final Supplier<EntityType<T>> entitySupplier;
	private final int pierceLevel;
	private final boolean canBeInfinite;
	final double gravityModifier;
	private final List<Double> shootingVectorInputs;
	private final int knockbackStrength;
	public final double damage;
	private final HitEffect hitEffect;
	public final int color;

	protected CustomArrowItem(Properties properties, double damage, Supplier<EntityType<T>> arrowEntity,
	                          int pierceLevel, boolean canBeInfinite, double gravityModifier,
	                          List<Double> shootingVectorInputs, int knockbackStrength, HitEffect hitEffect, int color) {
		super(properties);
		this.damage = damage;
		this.entitySupplier = arrowEntity;
		this.pierceLevel = pierceLevel;
		this.canBeInfinite = canBeInfinite;
		this.gravityModifier = gravityModifier;
		this.shootingVectorInputs = shootingVectorInputs;
		this.knockbackStrength = knockbackStrength;
		this.hitEffect = hitEffect;
		this.color = color;
	}

	public boolean canBeInfinite() {
		return canBeInfinite;
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
		CustomArrowEntity arrowEntity = new CustomArrowEntity(entitySupplier.get(), shooter, level, weapon);
		setCommonArrowCharacteristics(arrowEntity);

		return arrowEntity;
	}

	@Override
	public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		CustomArrowEntity arrowEntity = new CustomArrowEntity(entitySupplier.get(), level);
		arrowEntity.pickup = Pickup.ALLOWED;
		setCommonArrowCharacteristics(arrowEntity);
		arrowEntity.setPos(pos.x(), pos.y(), pos.z());

		return arrowEntity;
	}

	private void setCommonArrowCharacteristics(CustomArrowEntity arrowEntity) {
		arrowEntity.setPierceLevel((byte) pierceLevel);
		arrowEntity.setBaseDamage(damage);
		((ArrowAttributeAccessor) arrowEntity).immersiveWeapons$setBaseKnockback(knockbackStrength);
		((ArrowAttributeAccessor) arrowEntity).immersiveWeapons$setGravity(gravityModifier);
		arrowEntity.shootingVectorInputs = shootingVectorInputs;
		arrowEntity.hitEffect = hitEffect;
		arrowEntity.color = color;
		arrowEntity.referenceItem = this;

		if (color != -1) {
			arrowEntity.pickup = Pickup.DISALLOWED;
		}
	}

	/**
	 * Check if the arrow is infinite. A more flexible check than Vanilla provides. Restricts the ability to lower level
	 * arrows for balance.
	 *
	 * @param arrow   the arrow being checked
	 * @param bow     the bow firing the arrow
	 * @param shooter the entity firing the bow
	 * @return boolean
	 */
	@Override
	public boolean isInfinite(ItemStack arrow, ItemStack bow, LivingEntity shooter) {
		HolderGetter<Enchantment> enchantmentGetter = shooter.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
		Optional<Reference<Enchantment>> infinity = enchantmentGetter.get(Enchantments.INFINITY);

		int enchant = 0;
		if (infinity.isPresent()) {
			enchant = bow.getEnchantmentLevel(infinity.get());
		}

		return canBeInfinite() && enchant > 0;
	}

	public static class ArrowBuilder<T extends CustomArrowEntity> {

		private final Properties properties;
		private final double damage;
		private final Supplier<EntityType<T>> arrowEntity;
		private int pierceLevel = 0;
		private boolean canBeInfinite = true;
		private double gravityModifier = 0.05d;
		private List<Double> shootingVectorInputs = List.of(0.0075d, -0.0095d, 0.0075d);
		private int knockbackStrength = 0;
		private HitEffect hitEffect = HitEffect.NONE;
		private int color = -1;

		public ArrowBuilder(Properties properties, double damage, Supplier<EntityType<T>> arrowEntity) {
			this.properties = properties;
			this.damage = damage;
			this.arrowEntity = arrowEntity;
		}

		public ArrowBuilder<T> pierceLevel(int pierceLevel) {
			this.pierceLevel = pierceLevel;
			return this;
		}

		public ArrowBuilder<T> canBeInfinite(boolean canBeInfinite) {
			this.canBeInfinite = canBeInfinite;
			return this;
		}

		public ArrowBuilder<T> gravityModifier(double gravityModifier) {
			this.gravityModifier = gravityModifier;
			return this;
		}

		public ArrowBuilder<T> shootingVector(double inaccuracyBaseMultiplier, double inaccuracyRandomModifierLow, double inaccuracyRandomModifierHigh) {
			shootingVectorInputs = new ArrayList<>(5);
			shootingVectorInputs.add(inaccuracyBaseMultiplier);
			shootingVectorInputs.add(inaccuracyRandomModifierLow);
			shootingVectorInputs.add(inaccuracyRandomModifierHigh);
			return this;
		}

		public ArrowBuilder<T> knockbackStrength(int knockbackStrength) {
			this.knockbackStrength = knockbackStrength;
			return this;
		}

		public ArrowBuilder<T> hitEffect(HitEffect hitEffect) {
			this.hitEffect = hitEffect;
			return this;
		}

		public ArrowBuilder<T> color(int color) {
			this.color = color;
			return this;
		}

		public CustomArrowItem<T> build() {
			return new CustomArrowItem<>(properties, damage, arrowEntity, pierceLevel, canBeInfinite, gravityModifier, shootingVectorInputs, knockbackStrength, hitEffect, color);
		}
	}
}