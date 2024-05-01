package tech.anonymoushacker1279.immersiveweapons.item.gauntlet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EnchantmentRegistry;

public class GauntletItem extends TieredItem {

	public final Ingredient repairIngredient;
	public final double damage;
	public final double attackSpeed;
	private final float bleedChance;
	private final int bleedLevel;

	public GauntletItem(Tier tier, int damageBonus, float attackSpeed, Properties properties, float bleedChance, int bleedLevel, Ingredient repairIngredient) {
		super(tier, properties);

		this.repairIngredient = repairIngredient;
		damage = damageBonus + tier.getAttackDamageBonus();
		this.attackSpeed = attackSpeed;
		this.bleedChance = bleedChance;
		this.bleedLevel = bleedLevel;
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target.getRandom().nextFloat() <= bleedChance) {
			int enchantmentLevel = stack.getEnchantmentLevel(EnchantmentRegistry.CRIMSON_CLAW.get());
			int duration = 200 + (enchantmentLevel * 100);

			target.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT, duration,
					bleedLevel + enchantmentLevel, true, false));

			enchantmentLevel = stack.getEnchantmentLevel(EnchantmentRegistry.EXCESSIVE_FORCE.get());
			if (enchantmentLevel > 0) {
				duration = 2 + (enchantmentLevel * 20);
				target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, 0, true, false));
				target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, 0, true, false));

				// Knock back the target
				target.knockback(0.5f, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
			}
		}

		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
		if (state.getDestroySpeed(level, pos) != 0.0F) {
			stack.hurtAndBreak(2, livingEntity, EquipmentSlot.MAINHAND);
		}

		return true;
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
		return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
	}

	/**
	 * Check if the repair item is valid.
	 *
	 * @param toRepair the <code>ItemStack</code> to repair
	 * @param repair   the <code>ItemStack</code> to repair the first item
	 * @return boolean
	 */
	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repairIngredient.test(repair) || super.isValidRepairItem(toRepair, repair);
	}
}