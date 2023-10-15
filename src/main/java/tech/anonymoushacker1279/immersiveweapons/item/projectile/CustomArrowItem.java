package tech.anonymoushacker1279.immersiveweapons.item.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils.HitEffect;

import java.util.ArrayList;
import java.util.List;

public class CustomArrowItem<T extends CustomArrowEntity> extends ArrowItem {

	public final RegistryObject<EntityType<T>> entityRegistryObject;
	private final int pierceLevel;
	private final boolean canBeInfinite;
	final double gravityModifier;
	private final List<Double> shootingVectorInputs;
	private final int knockbackStrength;
	public double damage;
	private final HitEffect hitEffect;
	public final int color;

	protected CustomArrowItem(Properties properties, double damage, RegistryObject<EntityType<T>> arrowEntity,
	                          int pierceLevel, boolean canBeInfinite, double gravityModifier,
	                          List<Double> shootingVectorInputs, int knockbackStrength, HitEffect hitEffect, int color) {
		super(properties);
		this.damage = damage;
		this.entityRegistryObject = arrowEntity;
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
	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		CustomArrowEntity arrowEntity = new CustomArrowEntity(entityRegistryObject.get(), shooter, level);
		setCommonArrowCharacteristics(arrowEntity);

		return arrowEntity;
	}

	public AbstractArrow createArrow(Level level) {
		CustomArrowEntity arrowEntity = new CustomArrowEntity(entityRegistryObject.get(), level);
		setCommonArrowCharacteristics(arrowEntity);

		return arrowEntity;
	}

	private void setCommonArrowCharacteristics(CustomArrowEntity arrowEntity) {
		arrowEntity.setPierceLevel((byte) pierceLevel);
		arrowEntity.setBaseDamage(damage);
		arrowEntity.setKnockback(knockbackStrength);
		arrowEntity.gravityModifier = gravityModifier;
		arrowEntity.shootingVectorInputs = shootingVectorInputs;
		arrowEntity.hitEffect = hitEffect;
		arrowEntity.color = color;

		if (color != -1) {
			arrowEntity.pickup = Pickup.DISALLOWED;
		}

		// Add data to entity accessors so the client is aware
		arrowEntity.getEntityData().set(BulletEntity.GRAVITY_MODIFIER_ACCESSOR, (float) gravityModifier);
	}

	/**
	 * Check if the arrow is infinite. A more flexible check than Vanilla provides.
	 * Restricts the ability to lower level arrows, for balance.
	 *
	 * @param arrow  the arrow being checked
	 * @param bow    the bow firing the arrow
	 * @param player the player firing the bow
	 * @return boolean
	 */
	@Override
	public boolean isInfinite(ItemStack arrow, ItemStack bow, Player player) {
		int enchant = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
		return canBeInfinite() && enchant > 0;
	}

	public static class ArrowBuilder<T extends CustomArrowEntity> {

		private final Properties properties;
		private final double damage;
		private final RegistryObject<EntityType<T>> arrowEntity;
		private int pierceLevel = 0;
		private boolean canBeInfinite = true;
		private double gravityModifier = 0.05d;
		private List<Double> shootingVectorInputs = List.of(0.0075d, -0.0095d, 0.0075d);
		private int knockbackStrength = 0;
		private HitEffect hitEffect = HitEffect.NONE;
		private int color = -1;

		public ArrowBuilder(Properties properties, double damage, RegistryObject<EntityType<T>> arrowEntity) {
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