package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.util.ArrowAttributeAccessor;


/**
 * See individual methods for notes.
 */
@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin implements ArrowAttributeAccessor {

	@Unique
	private double immersiveWeapons$baseKnockback = 0.0d;

	@Unique
	private double immersiveWeapons$gravity = 0.05d;

	@Override
	public void immersiveWeapons$setBaseKnockback(double baseKnockback) {
		this.immersiveWeapons$baseKnockback = baseKnockback;
	}

	@Override
	public void immersiveWeapons$setGravity(double gravity) {
		this.immersiveWeapons$gravity = gravity;
	}

	/**
	 * Allows bullet entities to have custom damage sources and calculations.
	 */
	@Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
	private boolean hurtOrSimulate(Entity instance, DamageSource pSource, float pAmount) {
		AbstractArrow self = (AbstractArrow) (Object) this;
		Entity owner = self.getOwner();
		if (self instanceof BulletEntity bulletEntity) {
			boolean didHurt = instance.hurtOrSimulate(bulletEntity.getDamageSource(owner), bulletEntity.calculateDamage());

			// Bullets disable invulnerability. Otherwise, items like the blunderbuss would be useless.
			instance.invulnerableTime = 0;
			instance.setInvulnerable(false);

			return didHurt;
		} else {
			return instance.hurtOrSimulate(pSource, pAmount);
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
		return originalKnockback + immersiveWeapons$baseKnockback;
	}

	/**
	 * Modify the gravity of arrows.
	 */
	@ModifyReturnValue(method = "getDefaultGravity", at = @At("RETURN"))
	private double modifyGravity(double originalGravity) {
		if (immersiveWeapons$gravity != 0.05d) {
			return immersiveWeapons$gravity;
		} else {
			return originalGravity;
		}
	}

	/**
	 * Allow bullets to pass through leaves.
	 *
	 * @param original the original value
	 * @return the modified value
	 */
	@ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;isNoPhysics()Z"))
	private boolean modifyIsNoPhysics(boolean original) {
		AbstractArrow self = (AbstractArrow) (Object) this;
		if (self instanceof BulletEntity bulletEntity) {
			if (bulletEntity.level().getBlockState(bulletEntity.blockPosition()).is(BlockTags.LEAVES)) {
				return true;
			}
		}

		return original;
	}
}