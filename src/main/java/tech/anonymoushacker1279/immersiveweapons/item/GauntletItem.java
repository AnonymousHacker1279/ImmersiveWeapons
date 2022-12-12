package tech.anonymoushacker1279.immersiveweapons.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolActions;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class GauntletItem extends TieredItem implements Vanishable {

	public Multimap<Attribute, AttributeModifier> gauntletAttributes;
	public final Ingredient repairIngredient;
	private final float bleedChance;
	private final int bleedLevel;

	public GauntletItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, float bleedChance, int bleedLevel, Ingredient repairIngredient) {
		super(tier, properties);

		this.repairIngredient = repairIngredient;
		this.bleedChance = bleedChance;
		this.bleedLevel = bleedLevel;

		// Add damage and attack speed to the pike attributes
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (float) attackDamageModifier + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedModifier, AttributeModifier.Operation.ADDITION));
		gauntletAttributes = builder.build();
	}

	public void addReachDistanceAttributes() {
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

		gauntletAttributes = builder.put(ForgeMod.REACH_DISTANCE.get(),
						new AttributeModifier(GeneralUtilities.ATTACK_REACH_MODIFIER,
								"Weapon modifier",
								-2.0D,
								AttributeModifier.Operation.ADDITION))
				.putAll(gauntletAttributes)
				.build();
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		Material material = state.getMaterial();
		return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		bleedBehavior(target);
		stack.hurtAndBreak(1, attacker, (breakEvent) -> breakEvent.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
		if (state.getDestroySpeed(level, pos) != 0.0F) {
			stack.hurtAndBreak(2, livingEntity, (breakEvent) -> breakEvent.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}

		return true;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? gauntletAttributes : super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
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

	/**
	 * Set the bleeding behavior.
	 *
	 * @param target the <code>LivingEntity</code> being targeted
	 */
	public void bleedBehavior(LivingEntity target) {
		if (GeneralUtilities.getRandomNumber(0.0f, 1.0f) <= bleedChance) {
			target.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT.get(), 200,
					bleedLevel, true, false));
		}
	}
}