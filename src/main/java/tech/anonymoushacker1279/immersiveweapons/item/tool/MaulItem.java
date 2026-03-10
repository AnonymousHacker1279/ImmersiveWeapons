package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

public class MaulItem extends Item implements HitEffectUtils {

	public static final Identifier ARMOR_BREACH_ID = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "armor_breach");
	private final HitEffect hitEffect;

	public MaulItem(ToolMaterial material, float attackSpeedModifier, float armorBreach, Properties properties) {
		super(material.applyCommonProperties(properties).attributes(createAttributes(material, attackSpeedModifier, armorBreach)));

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

	public static ItemAttributeModifiers createAttributes(ToolMaterial material, float attackSpeedModifier, float armorBreach) {
		return ItemAttributeModifiers.builder()
				.add(
						Attributes.ATTACK_DAMAGE,
						new AttributeModifier(
								BASE_ATTACK_DAMAGE_ID,
								(float) 5 + material.attackDamageBonus(),
								AttributeModifier.Operation.ADD_VALUE
						),
						EquipmentSlotGroup.MAINHAND)
				.add(
						Attributes.ATTACK_SPEED,
						new AttributeModifier(
								BASE_ATTACK_SPEED_ID,
								attackSpeedModifier,
								AttributeModifier.Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND)
				.add(
						AttributeRegistry.ARMOR_BREACH,
						new AttributeModifier(
								ARMOR_BREACH_ID,
								armorBreach,
								AttributeModifier.Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND
				)
				.build();
	}

	@Override
	public boolean canDestroyBlock(ItemStack stack, BlockState state, Level level, BlockPos pos, LivingEntity entity) {
		return entity instanceof Player player && !player.isCreative();
	}

	@Override
	public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		switch (hitEffect) {
			case MOLTEN -> addMoltenEffects(target, attacker);
			case TESLA -> addTeslaEffects(target);
			case VENTUS -> addVentusEffects(target);
			default -> {
				// No hit effect
			}
		}

		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
	}
}