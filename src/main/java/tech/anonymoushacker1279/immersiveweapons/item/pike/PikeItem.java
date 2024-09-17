package tech.anonymoushacker1279.immersiveweapons.item.pike;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.AttributeRegistry;

public class PikeItem extends TieredItem {

	public final Ingredient repairIngredient;

	public PikeItem(Tier tier, Properties properties, Ingredient repairIngredient) {
		super(tier, properties);

		this.repairIngredient = repairIngredient;
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
		if (state.getDestroySpeed(level, pos) != 0.0D) {
			stack.hurtAndBreak(2, livingEntity, EquipmentSlot.MAINHAND);
		}

		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repairIngredient.test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.CUSTOM;
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float pAttackSpeed) {
		return ItemAttributeModifiers.builder()
				.add(
						Attributes.ATTACK_DAMAGE,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "attack_damage"),
								(float) 3 + tier.getAttackDamageBonus(),
								Operation.ADD_VALUE
						),
						EquipmentSlotGroup.MAINHAND)
				.add(
						Attributes.ATTACK_SPEED,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "attack_speed"),
								pAttackSpeed,
								Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND)
				.add(AttributeRegistry.ARMOR_BREACH,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "armor_breach"),
								0.25d,
								Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND)
				.build();
	}
}