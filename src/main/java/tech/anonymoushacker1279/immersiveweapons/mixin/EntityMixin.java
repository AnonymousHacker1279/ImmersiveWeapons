package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * See individual methods for notes.
 */
@Mixin(Entity.class)
public abstract class EntityMixin {

	/**
	 * Allow an entity to dampen vibrations if wearing padded leather armor.
	 */
	@Inject(method = "dampensVibrations", at = @At("RETURN"), cancellable = true)
	public void dampensVibrations(CallbackInfoReturnable<Boolean> ci) {
		// Check if the entity is wearing padded leather armor
		Entity self = (Entity) (Object) this;
		if (self instanceof LivingEntity livingEntity) {
			AtomicInteger paddedArmorCount = new AtomicInteger();
			livingEntity.getArmorSlots().forEach(itemStack -> {
				// TODO: should probably use a tag for this
			/*if (itemStack.getItem() instanceof PaddedLeatherArmorItem) {
				paddedArmorCount.getAndIncrement();
			}*/
			});

			if (paddedArmorCount.get() == 4) {
				ci.setReturnValue(true);
			}
		}
	}
}