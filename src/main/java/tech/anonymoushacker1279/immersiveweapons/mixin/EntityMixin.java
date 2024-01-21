package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.anonymoushacker1279.immersiveweapons.item.armor.PaddedLeatherArmorItem;

import java.util.concurrent.atomic.AtomicInteger;

import static org.spongepowered.asm.mixin.injection.callback.LocalCapture.CAPTURE_FAILSOFT;

/**
 * See individual methods for notes.
 */
@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow
	public abstract Iterable<ItemStack> getArmorSlots();

	/**
	 * Allow an entity to dampen vibrations if wearing padded leather armor.
	 */
	@Inject(method = "dampensVibrations", at = @At("RETURN"), cancellable = true, locals = CAPTURE_FAILSOFT)
	public void dampensVibrations(CallbackInfoReturnable<Boolean> ci) {
		// Check if the entity is wearing padded leather armor
		AtomicInteger paddedArmorCount = new AtomicInteger();
		getArmorSlots().forEach(itemStack -> {
			if (itemStack.getItem() instanceof PaddedLeatherArmorItem) {
				paddedArmorCount.getAndIncrement();
			}
		});

		if (paddedArmorCount.get() == 4) {
			ci.setReturnValue(true);
		}
	}
}