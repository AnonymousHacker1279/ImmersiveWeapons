package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Re-color enchantment names at the max Skygazer cap.
 */
@Mixin(ItemEnchantments.class)
public abstract class ItemEnchantmentsMixin {

	// TODO: rework enchantment recoloring
	/*@ModifyArg(method = "addToTooltip", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
	private Object recolorEnchantmentNames(Object object, @Local Holder<Enchantment> holder) {
		if (object instanceof Component component) {
			ResourceLocation enchantmentLocation = BuiltInRegistries.ENCHANTMENT.getKey(holder.value());

			if (enchantmentLocation != null) {
				int maxLevel = CommonConfig.skygazerEnchantCaps.getOrDefault(enchantmentLocation.toString(), -1);

				if ((holder.value().getMaxLevel() >= maxLevel && maxLevel != -1) || (holder.value().getMaxLevel() >= 255)) {
					return component.copy().withStyle(ChatFormatting.GOLD);
				}
			}
		}

		return object;
	}*/
}