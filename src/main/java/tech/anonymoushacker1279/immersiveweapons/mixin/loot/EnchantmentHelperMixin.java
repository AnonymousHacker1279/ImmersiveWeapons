package tech.anonymoushacker1279.immersiveweapons.mixin.loot;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.*;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tech.anonymoushacker1279.immersiveweapons.api.events.ComputeEnchantedLootBonusEvent;

/**
 * Loot-related mixins in this package are based on Puzzles Lib's implementation.
 * Provided as a solution to NeoForge
 * <a href="https://github.com/neoforged/NeoForge/issues/1112">issue #1112</a>.
 * <br>
 * <a href="https://github.com/Fuzss/puzzleslib/blob/main/1.21.1/NeoForge/src/main/java/fuzs/puzzleslib/neoforge/mixin/EnchantmentHelperNeoForgeMixin.java">Source</a>
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

	@ModifyVariable(method = "lambda$processEquipmentDropChance$24", at = @At("HEAD"), argsOnly = true)
	private static int processEquipmentDropChance$0(int enchantmentLevel, ServerLevel level, LivingEntity entity, DamageSource damageSource, MutableFloat mutableFloat, RandomSource randomSource, Holder<Enchantment> enchantment, int enchantmentLevelX, EnchantedItemInUse enchantedItemInUse) {
		return ComputeEnchantedLootBonusEvent.onComputeEnchantedLootBonus(enchantment, enchantmentLevel, entity, damageSource);
	}

	@ModifyVariable(method = "lambda$processEquipmentDropChance$26", at = @At("HEAD"), argsOnly = true)
	private static int processEquipmentDropChance$1(int enchantmentLevel, ServerLevel level, LivingEntity entity, DamageSource damageSource, MutableFloat mutableFloat, RandomSource randomSource, Holder<Enchantment> enchantment, int enchantmentLevelX, EnchantedItemInUse enchantedItemInUse) {
		return ComputeEnchantedLootBonusEvent.onComputeEnchantedLootBonus(enchantment, enchantmentLevel, entity, damageSource);
	}
}