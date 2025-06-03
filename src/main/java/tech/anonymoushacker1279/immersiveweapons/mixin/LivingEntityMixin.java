package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tech.anonymoushacker1279.immersiveweapons.item.armor.ArmorUtils;

/**
 * Increase swimming speed in lava when wearing Molten Armor.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@ModifyArg(method = "travelInFluid(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/material/FluidState;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;scale(D)Lnet/minecraft/world/phys/Vec3;"))
	private double modifySwimmingSpeed(double speed) {
		LivingEntity self = (LivingEntity) (Object) this;
		if (self.isInLava() && ArmorUtils.isWearingMoltenArmor(self)) {
			return 0.9f * self.getAttributeValue(NeoForgeMod.SWIM_SPEED);
		}

		return speed;
	}
}