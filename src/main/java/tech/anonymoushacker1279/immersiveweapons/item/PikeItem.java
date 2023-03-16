package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PikeItem extends TieredItem implements Vanishable {

	public final Ingredient repairIngredient;
	public final double damage;
	public final double attackSpeed;

	public PikeItem(Tier tier, Properties properties, double damageBonus, double attackSpeed, Ingredient repairIngredient) {
		super(tier, properties);

		this.repairIngredient = repairIngredient;
		damage = damageBonus + tier.getAttackDamageBonus();
		this.attackSpeed = attackSpeed;
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.SPEAR;
	}

	/**
	 * Runs when an enemy is hurt.
	 *
	 * @param stack    the <code>ItemStack</code> instance
	 * @param target   the <code>LivingEntity</code> target
	 * @param attacker the <code>LivingEntity</code> attacker
	 * @return boolean
	 */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	/**
	 * Runs when a block is mined.
	 *
	 * @param stack        the <code>ItemStack</code> instance
	 * @param level        the <code>Level</code> the block is in
	 * @param state        the <code>BlockState</code> of the block
	 * @param pos          the <code>BlockPos</code> the block is at
	 * @param livingEntity the <code>LivingEntity</code> destroying the block
	 * @return boolean
	 */
	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
		if (state.getDestroySpeed(level, pos) != 0.0D) {
			stack.hurtAndBreak(2, livingEntity, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
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