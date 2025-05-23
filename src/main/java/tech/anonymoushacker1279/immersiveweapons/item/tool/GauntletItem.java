package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;

public class GauntletItem extends Item implements HitEffectUtils {

	private final float bleedChance;
	private final int bleedLevel;
	private final HitEffect hitEffect;

	public GauntletItem(ToolMaterial material, float attackSpeedModifier, float bleedChance, int bleedLevel, Properties properties) {
		super(material.applyCommonProperties(properties).attributes(createAttributes(material, attackSpeedModifier)));

		this.bleedChance = bleedChance;
		this.bleedLevel = bleedLevel;

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

	public float getBleedChance() {
		return bleedChance;
	}

	public int getBleedLevel() {
		return bleedLevel;
	}

	@Override
	public boolean canDestroyBlock(ItemStack stack, BlockState state, Level level, BlockPos pos, LivingEntity entity) {
		return entity instanceof Player player && !player.isCreative();
	}

	@Override
	public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target.getRandom().nextFloat() <= bleedChance) {
			HolderGetter<Enchantment> enchantmentGetter = target.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();

			int enchantmentLevel = stack.getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.CRIMSON_CLAW));
			int duration = 200 + (enchantmentLevel * 100);

			target.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT, duration,
					bleedLevel + enchantmentLevel, true, false));

			enchantmentLevel = stack.getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.EXCESSIVE_FORCE));
			if (enchantmentLevel > 0) {
				duration = 2 + (enchantmentLevel * 20);
				target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, duration, 0, true, false));
				target.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, duration, 0, true, false));

				// Knock back the target
				target.knockback(0.5f, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
			}
		}

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

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
		if (state.getDestroySpeed(level, pos) != 0.0F) {
			stack.hurtAndBreak(2, livingEntity, EquipmentSlot.MAINHAND);
		}

		return true;
	}

	public static ItemAttributeModifiers createAttributes(ToolMaterial material, float attackSpeedModifier) {
		return ItemAttributeModifiers.builder()
				.add(
						Attributes.ATTACK_DAMAGE,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "attack_damage"),
								(float) 2 + material.attackDamageBonus(),
								AttributeModifier.Operation.ADD_VALUE
						),
						EquipmentSlotGroup.MAINHAND)
				.add(
						Attributes.ATTACK_SPEED,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "attack_speed"),
								4 + attackSpeedModifier,
								AttributeModifier.Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND)
				.add(
						Attributes.ENTITY_INTERACTION_RANGE,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "attack_range"),
								-2.0D,
								AttributeModifier.Operation.ADD_VALUE),
						EquipmentSlotGroup.MAINHAND)
				.build();
	}
}