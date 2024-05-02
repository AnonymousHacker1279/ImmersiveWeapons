package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;

/**
 * Re-color enchantment names at the max Skygazer cap.
 */
@Mixin(ItemEnchantments.class)
public abstract class ItemEnchantmentsMixin {

	@ModifyArg(
			method = "addToTooltip",
			at = @At(
					value = "INVOKE",
					target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"
			)
	)
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
	}
}