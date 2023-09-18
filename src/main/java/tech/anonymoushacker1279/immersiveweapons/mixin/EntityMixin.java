package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.nbt.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.armor.PaddedLeatherArmorItem;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.spongepowered.asm.mixin.injection.callback.LocalCapture.CAPTURE_FAILSOFT;

/**
 * See individual methods for notes.
 */
@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow
	public abstract Iterable<ItemStack> getArmorSlots();

	@Shadow
	public abstract Level level();

	@Shadow
	public abstract Vec3 position();

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

	/**
	 * Loading entities from tags does not respect any "passenger" tags, so we need to inject into the load method to
	 * manually set the passengers.
	 *
	 * @param pCompound the <code>CompoundTag</code> to load
	 * @param ci        callback info
	 */
	@Inject(method = "load", at = @At("RETURN"))
	public void load(CompoundTag pCompound, CallbackInfo ci) {
		if (pCompound.contains("Passengers")) {
			try {
				// Get a list of all passengers
				ListTag tags = pCompound.getList("Passengers", 10);

				// Iterate through the list of passengers
				for (Tag tag : tags) {
					// Get the passenger's entity data
					CompoundTag passengerData = (CompoundTag) tag;

					// Get the passenger's entity
					Optional<Entity> optional = EntityType.create(passengerData, level());

					// If the passenger is not null, load the passenger's data
					if (optional.isPresent()) {
						Entity entity = optional.get();
						entity.setPos(position());

						entity.startRiding((Entity) (Object) this, true);
					}
				}
			} catch (Exception e) {
				// If there is an error, print it to the console
				ImmersiveWeapons.LOGGER.error("Failed to load passengers for entity: " + pCompound.getString("id"));
			}
		}
	}
}