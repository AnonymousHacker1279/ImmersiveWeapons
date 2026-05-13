package tech.anonymoushacker1279.immersiveweapons.api.events;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/// Called just before a [LivingEntity] drops all its loot for determining the level of a loot bonus enchantment such as
/// [net.minecraft.world.item.enchantment.Enchantments#LOOTING] that should be applied to the drops.
///
/// Specifically the event allows for controlling the enchantment level when applying the:
///
///   - loot item function `minecraft:enchanted\_count\_increase`
///   - loot item condition `minecraft:random\_chance\_with\_enchanted\_bonus`
///   - enchantment effect component `minecraft:equipment\_drops`
///
/// This event is fired on the [NeoForge#EVENT_BUS].
///
/// Based on Puzzles Lib's implementation. Provided as a solution to NeoForge [issue
/// #1112](https://github.com/neoforged/NeoForge/issues/1112).
///
/// [Source](https://github.com/Fuzss/puzzleslib/blob/main/1.21.1/NeoForge/src/main/java/fuzs/puzzleslib/neoforge/mixin/LootItemRandomChanceWithEnchantedBonusConditionNeoForgeMixin.java)
public class ComputeEnchantedLootBonusEvent extends LivingEvent {
	@Nullable
	private final DamageSource damageSource;
	private final Holder<Enchantment> enchantment;
	private int enchantmentLevel;

	@ApiStatus.Internal
	public ComputeEnchantedLootBonusEvent(LivingEntity entity, @Nullable DamageSource damageSource, Holder<Enchantment> enchantment, int enchantmentLevel) {
		super(entity);
		this.damageSource = damageSource;
		this.enchantment = enchantment;
		this.enchantmentLevel = enchantmentLevel;
	}

	@ApiStatus.Internal
	public static int onComputeEnchantedLootBonus(Holder<Enchantment> enchantment, int enchantmentLevel, LootContext lootContext) {
		Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
		if (!(entity instanceof LivingEntity livingEntity)) {
			return enchantmentLevel;
		}
		DamageSource damageSource = lootContext.getOptionalParameter(LootContextParams.DAMAGE_SOURCE);
		return onComputeEnchantedLootBonus(enchantment, enchantmentLevel, livingEntity, damageSource);
	}

	@ApiStatus.Internal
	public static int onComputeEnchantedLootBonus(Holder<Enchantment> enchantment, int enchantmentLevel, LivingEntity livingEntity, @Nullable DamageSource damageSource) {
		return NeoForge.EVENT_BUS.post(
						new ComputeEnchantedLootBonusEvent(livingEntity, damageSource, enchantment, enchantmentLevel))
				.getEnchantmentLevel();
	}

	@Nullable
	public DamageSource getDamageSource() {
		return this.damageSource;
	}

	public Holder<Enchantment> getEnchantment() {
		return this.enchantment;
	}

	public int getEnchantmentLevel() {
		return this.enchantmentLevel;
	}

	public void setEnchantmentLevel(int enchantmentLevel) {
		this.enchantmentLevel = enchantmentLevel;
	}
}