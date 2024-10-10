package tech.anonymoushacker1279.immersiveweapons.mixin.loot;

import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tech.anonymoushacker1279.immersiveweapons.api.events.ComputeEnchantedLootBonusEvent;

/**
 * Loot-related mixins in this package are based on Puzzles Lib's implementation.
 * Provided as a solution to NeoForge
 * <a href="https://github.com/neoforged/NeoForge/issues/1112">issue #1112</a>.
 * <br>
 * <a href="https://github.com/Fuzss/puzzleslib/blob/main/1.21.1/NeoForge/src/main/java/fuzs/puzzleslib/neoforge/mixin/LootItemRandomChanceWithEnchantedBonusConditionNeoForgeMixin.java">Source</a>
 */
@Mixin(LootItemRandomChanceWithEnchantedBonusCondition.class)
public class LootItemRandomChanceWithEnchantedBonusConditionMixin {

	@Shadow
	@Final
	private Holder<Enchantment> enchantment;

	@ModifyVariable(method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z", at = @At("STORE"), ordinal = 0)
	public int test(int enchantmentLevel, LootContext lootContext) {
		return ComputeEnchantedLootBonusEvent.onComputeEnchantedLootBonus(this.enchantment, enchantmentLevel, lootContext);
	}
}