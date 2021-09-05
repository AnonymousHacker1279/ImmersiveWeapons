package com.anonymoushacker1279.immersiveweapons.item.gauntlet;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

public class GauntletItem extends TieredItem implements Vanishable {
	public static Multimap<Attribute, AttributeModifier> gauntletAttributes;
	private final float bleedChance;

	public GauntletItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, float bleedChance) {
		super(tier, properties);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (float) attackDamageModifier + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedModifier, AttributeModifier.Operation.ADDITION));
		gauntletAttributes = builder.build();
		this.bleedChance = bleedChance;
	}

	@Override
	public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public float getDestroySpeed(@NotNull ItemStack stack, BlockState state) {
		Material material = state.getMaterial();
		return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
	}

	@Override
	public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
		bleedBehavior(target);
		stack.hurtAndBreak(1, attacker, (breakEvent) -> {
			breakEvent.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		return true;
	}

	@Override
	public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity livingEntity) {
		if (state.getDestroySpeed(level, pos) != 0.0F) {
			stack.hurtAndBreak(2, livingEntity, (breakEvent) -> {
				breakEvent.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
		}

		return true;
	}

	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? gauntletAttributes : super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
		return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
	}

	/**
	 * Check if the repair item is valid.
	 * @param toRepair the <code>ItemStack</code> to repair
	 * @param repair the <code>ItemStack</code> to repair the first item
	 * @return boolean
	 */
	@Override
	public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
		return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	/**
	 * Get the repair material.
	 * @return Ingredient
	 */
	Ingredient getRepairMaterial() {
		return Ingredient.of(ItemTags.PLANKS);
	}

	/**
	 * Set the bleeding behavior.
	 * @param target    the <code>LivingEntity</code> being targeted
	 */
	public void bleedBehavior(LivingEntity target) {
		if (GeneralUtilities.getRandomNumber(0.0f, 1.0f) <= bleedChance) {
			target.addEffect(new MobEffectInstance(DeferredRegistryHandler.BLEEDING_EFFECT.get(), 200, 0, false, false));
		}
	}
}