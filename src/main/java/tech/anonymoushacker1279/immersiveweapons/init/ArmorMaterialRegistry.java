package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ArmorMaterialRegistry {

	// Armor Material Register
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, ImmersiveWeapons.MOD_ID);

	// Armor Materials
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> MOLTEN = register("molten",
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.LEGGINGS, 6);
				map.put(ArmorItem.Type.CHESTPLATE, 9);
				map.put(ArmorItem.Type.HELMET, 4);
			}),
			15,
			SoundEventRegistry.MOLTEN_ARMOR_EQUIP,
			() -> Ingredient.of(ItemRegistry.MOLTEN_INGOT.get()),
			3.25F,
			0.12F
	);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> COPPER = register("copper",
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 1);
				map.put(ArmorItem.Type.LEGGINGS, 4);
				map.put(ArmorItem.Type.CHESTPLATE, 5);
				map.put(ArmorItem.Type.HELMET, 1);
			}),
			9,
			SoundEventRegistry.COPPER_ARMOR_EQUIP,
			() -> Ingredient.of(Items.COPPER_INGOT),
			0.0F,
			0.0F
	);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TESLA = register("tesla",
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 7);
				map.put(ArmorItem.Type.LEGGINGS, 8);
				map.put(ArmorItem.Type.CHESTPLATE, 11);
				map.put(ArmorItem.Type.HELMET, 6);
			}),
			20,
			SoundEventRegistry.TESLA_ARMOR_EQUIP,
			() -> Ingredient.of(ItemRegistry.TESLA_INGOT.get()),
			3.5F,
			0.05F
	);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> COBALT = register("cobalt",
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 3);
				map.put(ArmorItem.Type.LEGGINGS, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 6);
				map.put(ArmorItem.Type.HELMET, 3);
			}),
			10,
			SoundEventRegistry.COBALT_ARMOR_EQUIP,
			() -> Ingredient.of(ItemRegistry.COBALT_INGOT.get()),
			0.0F,
			0.0F
	);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> VENTUS = register("ventus",
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.LEGGINGS, 6);
				map.put(ArmorItem.Type.CHESTPLATE, 9);
				map.put(ArmorItem.Type.HELMET, 5);
			}),
			14,
			SoundEventRegistry.VENTUS_ARMOR_EQUIP,
			() -> Ingredient.of(ItemRegistry.VENTUS_SHARD.get()),
			2.75F,
			0.02F
	);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ASTRAL = register("astral",
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 4);
				map.put(ArmorItem.Type.LEGGINGS, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 6);
				map.put(ArmorItem.Type.HELMET, 4);
			}),
			22,
			SoundEventRegistry.ASTRAL_ARMOR_EQUIP,
			() -> Ingredient.of(ItemRegistry.ASTRAL_INGOT.get()),
			1.8F,
			0.0F
	);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STARSTORM = register("starstorm",
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.LEGGINGS, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 6);
				map.put(ArmorItem.Type.HELMET, 4);
			}),
			15,
			SoundEventRegistry.STARSTORM_ARMOR_EQUIP,
			() -> Ingredient.of(ItemRegistry.STARSTORM_INGOT.get()),
			2.2F,
			0.0F
	);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PADDED_LEATHER = ARMOR_MATERIALS.register("padded_leather", () -> new ArmorMaterial(
			Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
				map.put(ArmorItem.Type.BOOTS, 2);
				map.put(ArmorItem.Type.LEGGINGS, 2);
				map.put(ArmorItem.Type.CHESTPLATE, 3);
				map.put(ArmorItem.Type.HELMET, 2);
			}),
			15,
			SoundEvents.ARMOR_EQUIP_LEATHER,
			() -> Ingredient.of(Items.LEATHER),
			List.of(new ArmorMaterial.Layer(new ResourceLocation(ImmersiveWeapons.MOD_ID, "padded_leather"), "", true), new ArmorMaterial.Layer(new ResourceLocation(ImmersiveWeapons.MOD_ID, "padded_leather"), "_overlay", false)),
			0.0F,
			0.0F
	));

	private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, Supplier<Ingredient> repairIngredients, float toughness, float knockbackResistance) {
		List<Layer> armorLayers = List.of(new Layer(new ResourceLocation(ImmersiveWeapons.MOD_ID, name)));
		return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(defense, enchantmentValue, equipSound, repairIngredients, armorLayers, toughness, knockbackResistance));
	}
}