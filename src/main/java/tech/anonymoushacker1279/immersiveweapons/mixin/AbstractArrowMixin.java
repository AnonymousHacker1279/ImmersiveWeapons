package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.util.ArrowKnockbackAccessor;


/**
 * See individual methods for notes.
 */
@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin implements ArrowKnockbackAccessor {

	@Unique
	private double baseKnockback = 0.0;

	@Override
	public void setBaseKnockback(double baseKnockback) {
		this.baseKnockback = baseKnockback;
	}

	/**
	 * Allows bullet entities to have custom damage sources and calculations.
	 */
	@Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
	private boolean hurtEntity(Entity instance, DamageSource pSource, float pAmount) {
		AbstractArrow self = (AbstractArrow) (Object) this;
		Entity owner = self.getOwner();
		if (self instanceof BulletEntity bulletEntity) {
			boolean didHurt = instance.hurt(bulletEntity.getDamageSource(owner), bulletEntity.calculateDamage());

			// Bullets disable invulnerability. Otherwise, items like the blunderbuss would be useless.
			instance.invulnerableTime = 0;
			instance.setInvulnerable(false);

			return didHurt;
		} else {
			return instance.hurt(pSource, pAmount);
		}
	}

	/**
	 * Prevent bullets from appearing as arrows on the player.
	 */
	@ModifyArg(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setArrowCount(I)V"))
	private int setArrowCount(int arrowCount) {
		AbstractArrow self = (AbstractArrow) (Object) this;
		if (self instanceof BulletEntity) {
			// This should be equal to the previous number of arrows that were in the player
			return arrowCount - 1;
		} else {
			return arrowCount;
		}
	}

	/**
	 * Modify the knockback of arrows.
	 */
	@ModifyVariable(method = "doKnockback", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
	private double modifyKnockback(double originalKnockback) {
		return originalKnockback + baseKnockback;
	}
}