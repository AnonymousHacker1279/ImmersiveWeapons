package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkygazerEntity;

import java.util.List;
import java.util.Map;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	/**
	 * @author AnonymousHacker1279
	 * @reason Re-colors enchantment names in tooltips to be gold if at the maximum Skygazer cap.
	 */
	@Overwrite
	public static void appendEnchantmentNames(List<Component> pTooltipComponents, ListTag pStoredEnchantments) {
		// Check the tooltip components, anything at the Skygazer max level should appear gold
		Map<Enchantment, Integer> enchants = EnchantmentHelper.deserializeEnchantments(pStoredEnchantments);

		enchants.forEach((enchantment, level) -> {
			ResourceLocation enchantmentLocation = ForgeRegistries.ENCHANTMENTS.getKey(enchantment);

			if (enchantmentLocation == null) {
				ImmersiveWeapons.LOGGER.error("Failed to locate enchantment {} in registry", enchantment);
				return;
			}

			int maxLevel = SkygazerEntity.ENCHANT_CAPS.getOrDefault(enchantmentLocation.toString(), -1);

			if (level >= maxLevel && maxLevel != -1) {
				pTooltipComponents.add(enchantment.getFullname(level).copy().withStyle(ChatFormatting.GOLD));
			} else {
				pTooltipComponents.add(enchantment.getFullname(level));
			}
		});
	}
}