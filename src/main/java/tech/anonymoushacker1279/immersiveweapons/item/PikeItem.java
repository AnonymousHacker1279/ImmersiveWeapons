package tech.anonymoushacker1279.immersiveweapons.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class PikeItem extends Item {

	public Multimap<Attribute, AttributeModifier> pikeAttributes;
	public final Ingredient repairIngredient;

	/**
	 * Constructor for PikeItem.
	 *
	 * @param properties    the <code>Properties</code> for the item
	 * @param damageIn      the damage
	 * @param attackSpeedIn the attack speed
	 */
	public PikeItem(Properties properties, double damageIn, double attackSpeedIn, Ingredient repairIngredient) {
		super(properties);

		this.repairIngredient = repairIngredient;

		// Add damage and attack speed to the pike attributes
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
		pikeAttributes = builder.build();
	}

	public void addReachDistanceAttributes() {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

		pikeAttributes = builder.put(ForgeMod.REACH_DISTANCE.get(),
						new AttributeModifier(GeneralUtilities.ATTACK_REACH_MODIFIER,
								"Weapon modifier",
								0.5D,
								AttributeModifier.Operation.ADDITION))
				.putAll(pikeAttributes)
				.build();
	}

	/**
	 * Check if the block can be damaged.
	 *
	 * @param state   the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos     the <code>BlockPos</code> the block is at
	 * @param player  the <code>PlayerEntity</code> damaging the block
	 * @return boolean
	 */
	@Override
	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	/**
	 * Get the use animation.
	 *
	 * @param stack the <code>ItemStack</code> instance
	 * @return UseAction
	 */
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

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? pikeAttributes : super.getAttributeModifiers(equipmentSlot, stack);
	}
}