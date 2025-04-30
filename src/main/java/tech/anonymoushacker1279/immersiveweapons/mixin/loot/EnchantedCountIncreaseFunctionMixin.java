package tech.anonymoushacker1279.immersiveweapons.mixin.loot;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.anonymoushacker1279.immersiveweapons.api.events.ComputeEnchantedLootBonusEvent;

/**
 * Loot-related mixins in this package are based on Puzzles Lib's implementation. Provided as a solution to NeoForge
 * <a href="https://github.com/neoforged/NeoForge/issues/1112">issue #1112</a>.
 * <br>
 * <a
 * href="https://github.com/Fuzss/puzzleslib/blob/main/1.21.1/NeoForge/src/main/java/fuzs/puzzleslib/neoforge/mixin/EnchantedCountIncreaseFunctionNeoForgeMixin.java">Source</a>
 */
@Mixin(EnchantedCountIncreaseFunction.class)
public abstract class EnchantedCountIncreaseFunctionMixin {

	@Shadow
	@Final
	private Holder<Enchantment> enchantment;
	@Shadow
	@Final
	private NumberProvider value;
	@Shadow
	@Final
	private int limit;

	@Shadow
	private boolean hasLimit() {
		throw new RuntimeException();
	}

	@ModifyVariable(method = "run", at = @At("STORE"), ordinal = 0)
	public int run(int enchantmentLevel, ItemStack itemStack, LootContext lootContext) {
		return ComputeEnchantedLootBonusEvent.onComputeEnchantedLootBonus(this.enchantment, enchantmentLevel, lootContext);
	}

	@Inject(method = "run", at = @At("HEAD"))
	public void run(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> callback) {
		if (!(context.getOptionalParameter(LootContextParams.ATTACKING_ENTITY) instanceof LivingEntity)) {
			int enchantmentLevel = ComputeEnchantedLootBonusEvent.onComputeEnchantedLootBonus(this.enchantment, 0, context);
			if (enchantmentLevel != 0) {
				float modifier = (float) enchantmentLevel * this.value.getFloat(context);
				stack.grow(Math.round(modifier));
				if (this.hasLimit()) {
					stack.limitSize(this.limit);
				}
			}
		}
	}
}