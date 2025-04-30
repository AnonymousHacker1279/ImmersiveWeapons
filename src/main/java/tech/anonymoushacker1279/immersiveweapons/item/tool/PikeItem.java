package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.AttributeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;

public class PikeItem extends Item implements HitEffectUtils {

	private final HitEffect hitEffect;

	public PikeItem(ToolMaterial material, float attackSpeedModifier, Properties properties) {
		super(material.applyCommonProperties(properties).attributes(createAttributes(material, attackSpeedModifier)));

		if (material == IWToolMaterials.MOLTEN) {
			hitEffect = HitEffect.MOLTEN;
		} else if (material == IWToolMaterials.TESLA) {
			hitEffect = HitEffect.TESLA;
		} else if (material == IWToolMaterials.VENTUS) {
			hitEffect = HitEffect.VENTUS;
		} else {
			hitEffect = HitEffect.NONE;
		}
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		switch (hitEffect) {
			case MOLTEN -> addMoltenEffects(target, attacker);
			case TESLA -> addTeslaEffects(target);
			case VENTUS -> addVentusEffects(target);
			default -> {
				// No hit effect
			}
		}

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

	// TODO: investigate enum extension
	/*@Override
	public ItemUseAnimation getUseAnimation(ItemStack pStack) {
		return ItemUseAnimation.CUSTOM;
	}*/

	public static ItemAttributeModifiers createAttributes(ToolMaterial material, float attackSpeedModifier) {
		return ItemAttributeModifiers.builder()
				.add(
						Attributes.ATTACK_DAMAGE,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "attack_damage"),
								(float) 3 + material.attackDamageBonus(),
								Operation.ADD_VALUE
						),
						EquipmentSlotGroup.MAINHAND)
				.add(
						Attributes.ATTACK_SPEED,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "attack_speed"),
								4 + attackSpeedModifier,
								Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ENTITY_INTERACTION_RANGE,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "entity_interaction_range"),
								0.5d,
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