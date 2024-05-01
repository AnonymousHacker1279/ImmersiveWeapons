package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Re-color enchantment names at the max Skygazer cap.
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	// TODO: check for new injection point, in ItemEnchantments.addToTooltip

	/*@ModifyArg(
			method = "lambda$appendEnchantmentNames$16",
			at = @At(
					value = "INVOKE",
					target = "Ljava/util/List;add(Ljava/lang/Object;)Z"
			),
			remap = false
	)
	private static Object recolorEnchantmentNames(Object object, @Local(argsOnly = true) CompoundTag compoundTag) {
		if (object instanceof Component component) {
			ResourceLocation enchantmentLocation = EnchantmentHelper.getEnchantmentId(compoundTag);
			int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(compoundTag);

			if (enchantmentLocation != null) {
				int maxLevel = CommonConfig.skygazerEnchantCaps.getOrDefault(enchantmentLocation.toString(), -1);

				if ((enchantmentLevel >= maxLevel && maxLevel != -1) || (enchantmentLevel >= 255)) {
					return component.copy().withStyle(ChatFormatting.GOLD);
				}
			}
		}

		return object;
	}*/
}