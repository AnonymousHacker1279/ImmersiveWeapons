package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;

import java.util.List;

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
			List<ItemStack> armorSlots = List.of(
					livingEntity.getItemBySlot(EquipmentSlot.HEAD),
					livingEntity.getItemBySlot(EquipmentSlot.CHEST),
					livingEntity.getItemBySlot(EquipmentSlot.LEGS),
					livingEntity.getItemBySlot(EquipmentSlot.FEET));

			int paddedArmorCount = 0;
			for (ItemStack itemStack : armorSlots) {
				if (itemStack.is(IWItemTagGroups.PADDED_LEATHER)) {
					paddedArmorCount++;
				}
			}

			if (paddedArmorCount == 4) {
				ci.setReturnValue(true);
			}
		}
	}
}