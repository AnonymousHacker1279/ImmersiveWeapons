package tech.anonymoushacker1279.immersiveweapons.item.gauntlet;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.PostSetupHandler;

public class GoldenGauntletItem extends GauntletItem {
	public static Multimap<Attribute, AttributeModifier> gauntletAttributes;

	public GoldenGauntletItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, float bleedChance) {
		super(tier, attackDamageModifier, attackSpeedModifier, properties, bleedChance);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (float) attackDamageModifier + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedModifier, AttributeModifier.Operation.ADDITION));
		gauntletAttributes = builder.build();
	}

	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> returnValue;
		if (PostSetupHandler.hasCompletedClientSetup) {
			returnValue = equipmentSlot == EquipmentSlot.MAINHAND ? GoldenGauntletItem.gauntletAttributes : super.getAttributeModifiers(equipmentSlot, stack);
		} else {
			returnValue = GoldenGauntletItem.gauntletAttributes;
		}
		return returnValue;
	}

	@Override
	Ingredient getRepairMaterial() {
		return Ingredient.of(Items.GOLD_INGOT);
	}
}