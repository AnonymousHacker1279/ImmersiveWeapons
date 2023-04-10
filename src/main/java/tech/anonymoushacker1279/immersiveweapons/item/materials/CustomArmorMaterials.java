package tech.anonymoushacker1279.immersiveweapons.item.materials;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum CustomArmorMaterials implements ArmorMaterial {

	MOLTEN("molten", 39,
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.LEGGINGS, 6);
				map.put(ArmorItem.Type.CHESTPLATE, 9);
				map.put(ArmorItem.Type.HELMET, 4);
			}), 15, 3.25F, () -> Ingredient.of(ItemRegistry.MOLTEN_INGOT.get()), 0.12F),

	COPPER("copper", 13,
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 1);
				map.put(ArmorItem.Type.LEGGINGS, 4);
				map.put(ArmorItem.Type.CHESTPLATE, 5);
				map.put(ArmorItem.Type.HELMET, 1);
			}), 9, 0.0F, () -> Ingredient.of(Items.COPPER_INGOT), 0.0F),

	TESLA("tesla", 42,
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 7);
				map.put(ArmorItem.Type.LEGGINGS, 8);
				map.put(ArmorItem.Type.CHESTPLATE, 11);
				map.put(ArmorItem.Type.HELMET, 6);
			}), 20, 3.5F, () -> Ingredient.of(ItemRegistry.TESLA_INGOT.get()), 0.05F),

	COBALT("cobalt", 19,
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 3);
				map.put(ArmorItem.Type.LEGGINGS, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 6);
				map.put(ArmorItem.Type.HELMET, 3);
			}), 10, 0.0F, () -> Ingredient.of(ItemRegistry.COBALT_INGOT.get()), 0.0F),

	VENTUS("ventus", 39,
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.LEGGINGS, 6);
				map.put(ArmorItem.Type.CHESTPLATE, 9);
				map.put(ArmorItem.Type.HELMET, 5);
			}), 14, 2.75F, () -> Ingredient.of(ItemRegistry.VENTUS_SHARD.get()), 0.02F),

	ASTRAL("astral", 25,
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 4);
				map.put(ArmorItem.Type.LEGGINGS, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 6);
				map.put(ArmorItem.Type.HELMET, 4);
			}), 22, 1.8F, () -> Ingredient.of(ItemRegistry.ASTRAL_INGOT.get()), 0.0F),

	STARSTORM("starstorm", 32,
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.LEGGINGS, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 6);
				map.put(ArmorItem.Type.HELMET, 4);
			}), 15, 2.2F, () -> Ingredient.of(ItemRegistry.STARSTORM_INGOT.get()), 0.0F);

	private final String name;
	private final int durabilityMultiplier;
	private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
	private final int enchantability;
	private SoundEvent equipSound;
	private final float toughness;
	private final Supplier<Ingredient> repairMaterial;
	private final float knockbackResist;

	CustomArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionType, int enchantability,
	                     float toughness, Supplier<Ingredient> repairMaterial, float knockbackResist) {

		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionFunctionForType = protectionType;
		this.enchantability = enchantability;
		equipSound = SoundEvents.ARMOR_EQUIP_IRON; // This is set to a non-default value in post-init
		this.toughness = toughness;
		this.repairMaterial = repairMaterial;
		this.knockbackResist = knockbackResist;
	}

	private static final EnumMap<Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
		map.put(ArmorItem.Type.BOOTS, 13);
		map.put(ArmorItem.Type.LEGGINGS, 15);
		map.put(ArmorItem.Type.CHESTPLATE, 16);
		map.put(ArmorItem.Type.HELMET, 11);
	});

	@Override
	public int getDurabilityForType(ArmorItem.Type type) {
		return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
	}

	@Override
	public int getDefenseForType(ArmorItem.Type type) {
		return protectionFunctionForType.get(type);
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