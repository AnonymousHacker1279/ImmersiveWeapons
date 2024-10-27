package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tech.anonymoushacker1279.immersiveweapons.config.ServerConfig;

/**
 * Re-color enchantment names at the max Skygazer cap.
 */
@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

	@ModifyReturnValue(method = "getFullname", at = @At(value = "RETURN"))
	private static Component recolorEnchantmentNames(Component component, @Local(argsOnly = true) Holder<Enchantment> holder, @Local(argsOnly = true) int level) {
		ResourceKey<Enchantment> enchantmentLocation = holder.getKey();

		if (enchantmentLocation != null) {
			int maxLevel = ServerConfig.getEnchantCap(enchantmentLocation.location().toString());

			if ((level >= maxLevel && maxLevel != -1) || (level >= 255)) {
				return component.copy().withStyle(ChatFormatting.GOLD);
			}
		}

		return component;
	}
}