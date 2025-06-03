package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.anonymoushacker1279.immersiveweapons.item.armor.ArmorUtils;

/**
 * Allow swimming in lava when wearing Molten Armor.
 */
@Mixin(ILivingEntityExtension.class)
public interface ILivingEntityExtensionMixin {

	@Inject(method = "canSwimInFluidType", at = @At(value = "RETURN"), cancellable = true)
	private void allowSwimming(FluidType type, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity self = (LivingEntity) this;
		if (type == NeoForgeMod.LAVA_TYPE.value() && ArmorUtils.isWearingMoltenArmor(self)) {
			cir.setReturnValue(true);
		}
	}
}