package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;

/**
 * Re-color enchantment names at the max Skygazer cap.
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@ModifyArg(
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

				if (enchantmentLevel >= maxLevel && maxLevel != -1) {
					return component.copy().withStyle(ChatFormatting.GOLD);
				}
			}
		}

		return object;
	}
}