package tech.anonymoushacker1279.immersiveweapons.item.materials;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.function.Supplier;

public enum CustomArmorMaterials implements ArmorMaterial {

	MOLTEN("molten", 39, new int[]{5, 6, 9, 4}, 15,
			3.25F, () -> Ingredient.of(ItemRegistry.MOLTEN_INGOT.get()), 0.12F),
	COPPER("copper", 13, new int[]{1, 4, 5, 1}, 9,
			0.0F, () -> Ingredient.of(Items.COPPER_INGOT), 0.0F),
	TESLA("tesla", 42, new int[]{7, 8, 11, 6}, 20,
			3.5F, () -> Ingredient.of(ItemRegistry.TESLA_INGOT.get()), 0.05F),
	COBALT("cobalt", 19, new int[]{3, 5, 6, 3}, 10,
			0.0F, () -> Ingredient.of(ItemRegistry.COBALT_INGOT.get()), 0.0F),
	VENTUS("ventus", 39, new int[]{5, 6, 9, 5}, 14,
			2.75F, () -> Ingredient.of(ItemRegistry.VENTUS_SHARD.get()), 0.02F),
	ASTRAL("astral", 25, new int[]{4, 5, 6, 4}, 22,
			1.8F, () -> Ingredient.of(ItemRegistry.ASTRAL_INGOT.get()), 0.0F),
	STARSTORM("starstorm", 32, new int[]{5, 5, 6, 4}, 15,
			2.2F, () -> Ingredient.of(ItemRegistry.STARSTORM_INGOT.get()), 0.0F);

	static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
	private final String name;
	private final int durabilityMultiplier;
	private final int[] slotArmorValues;
	private final int enchantability;
	private SoundEvent equipSound;
	private final float toughness;
	private final Supplier<Ingredient> repairMaterial;
	private final float knockbackResist;

	/**
	 * Constructor for CustomArmorMaterials.
	 *
	 * @param name                 the material name
	 * @param durabilityMultiplier the durability multiplier. This is multiplied by the values in the
	 *                             <code>HEALTH_PER_SLOT</code> array.
	 * @param slotArmorValues      an array of defense values for each armor piece. From left to right: helmet,
	 *                             chestplate, leggings, boots.
	 * @param enchantability       the enchantability value
	 * @param toughness            the armor toughness value
	 * @param repairMaterial       the repair material
	 * @param knockbackResist      the knockback resistance value
	 */
	CustomArmorMaterials(String name, int durabilityMultiplier, int[] slotArmorValues, int enchantability,
	                     float toughness, Supplier<Ingredient> repairMaterial, float knockbackResist) {

		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.slotArmorValues = slotArmorValues;
		this.enchantability = enchantability;
		equipSound = SoundEvents.ARMOR_EQUIP_IRON; // This is set to a non-default value in post-init
		this.toughness = toughness;
		this.repairMaterial = repairMaterial;
		this.knockbackResist = knockbackResist;
	}

	/**
	 * Get durability for a slot.
	 *
	 * @param slotIn the <code>EquipmentSlotType</code>
	 * @return int
	 */
	@Override
	public int getDurabilityForSlot(EquipmentSlot slotIn) {
		return HEALTH_PER_SLOT[slotIn.getIndex()] * durabilityMultiplier;
	}

	/**
	 * Get the defense for a slot.
	 *
	 * @param slotIn the <code>EquipmentSlotType</code>
	 * @return int
	 */
	@Override
	public int getDefenseForSlot(EquipmentSlot slotIn) {
		return slotArmorValues[slotIn.getIndex()];
	}

	/**
	 * Get the enchantment value.
	 *
	 * @return int
	 */
	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	/**
	 * Get the equipping sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	public SoundEvent getEquipSound() {
		return equipSound;
	}

	/**
	 * Set the equipping sound.
	 */
	public void setEquipSound(SoundEvent sound) {
		equipSound = sound;
	}

	/**
	 * Get the repair ingredient.
	 *
	 * @return Ingredient
	 */
	@Override
	public Ingredient getRepairIngredient() {
		return repairMaterial.get();
	}

	/**
	 * Get the name.
	 *
	 * @return String
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Get the toughness.
	 *
	 * @return float
	 */
	@Override
	public float getToughness() {
		return toughness;
	}

	/**
	 * Get the knockback resistance.
	 *
	 * @return float
	 */
	@Override
	public float getKnockbackResistance() {
		return knockbackResist;
	}
}