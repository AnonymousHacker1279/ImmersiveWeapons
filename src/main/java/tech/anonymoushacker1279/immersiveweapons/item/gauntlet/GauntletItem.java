package tech.anonymoushacker1279.immersiveweapons.item.gauntlet;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;

public class GauntletItem extends TieredItem {

	public final Ingredient repairIngredient;
	private final float bleedChance;
	private final int bleedLevel;

	public GauntletItem(Tier tier, Properties properties, float bleedChance, int bleedLevel, Ingredient repairIngredient) {
		super(tier, properties);

		this.repairIngredient = repairIngredient;
		this.bleedChance = bleedChance;
		this.bleedLevel = bleedLevel;
	}

	public float getBleedChance() {
		return bleedChance;
	}

	public int getBleedLevel() {
		return bleedLevel;
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target.getRandom().nextFloat() <= bleedChance) {
			HolderGetter<Enchantment> enchantmentGetter = target.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();

			int enchantmentLevel = stack.getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.CRIMSON_CLAW));
			int duration = 200 + (enchantmentLevel * 100);

			target.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT, duration,
					bleedLevel + enchantmentLevel, true, false));

			enchantmentLevel = stack.getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.EXCESSIVE_FORCE));
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
	public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
		return ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(ability);
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repairIngredient.test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackSpeedModifier) {
		return ItemAttributeModifiers.builder()
				.add(
						Attributes.ATTACK_DAMAGE,
						new AttributeModifier(
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "attack_damage"),
								(float) 2 + tier.getAttackDamageBonus(),
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