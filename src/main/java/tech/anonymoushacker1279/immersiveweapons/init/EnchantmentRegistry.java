package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class EnchantmentRegistry {

	// Enchantment Register
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, ImmersiveWeapons.MOD_ID);

	// Equipment slots
	public static final EquipmentSlot[] HANDS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

	public static final TagKey<Item> FIREARMS = ItemTags.create(new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"weapons/firearms"));
	public static final TagKey<Item> RANGED_WEAPONS = ItemTags.create(new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"weapons/ranged_weapons"));
	public static final TagKey<Item> PIKES = ItemTags.create(new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"weapons/pikes"));
	public static final TagKey<Item> GAUNTLETS = ItemTags.create(new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"weapons/gauntlets"));
	public static final TagKey<Item> WEAPONS_AND_TOOLS = ItemTags.create(new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"weapons_and_tools"));
	public static final TagKey<Item> METEOR_STAFFS = ItemTags.create(new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"weapons/meteor_staffs"));
	public static final TagKey<Item> CURSED_SIGHT_STAFFS = ItemTags.create(new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"weapons/cursed_sight_staffs"));

	// Enchantments
	public static final Supplier<Enchantment> FIREPOWER = ENCHANTMENTS.register("firepower", () -> new Enchantment(Enchantment.definition(
			FIREARMS,
			10,
			5,
			Enchantment.dynamicCost(1, 10),
			Enchantment.dynamicCost(16, 10),
			1,
			FeatureFlagSet.of(),
			EquipmentSlot.MAINHAND
	)));

	public static final Supplier<Enchantment> IMPACT = ENCHANTMENTS.register("impact", () -> new Enchantment(Enchantment.definition(
			FIREARMS,
			2,
			2,
			Enchantment.dynamicCost(12, 20),
			Enchantment.dynamicCost(37, 20),
			4,
			FeatureFlagSet.of(),
			EquipmentSlot.MAINHAND
	)));

	public static final Supplier<Enchantment> ENDLESS_MUSKET_POUCH = ENCHANTMENTS.register("endless_musket_pouch", () -> new Enchantment(Enchantment.definition(
			FIREARMS,
			1,
			1,
			Enchantment.constantCost(20),
			Enchantment.constantCost(50),
			8,
			EquipmentSlot.MAINHAND
	)) {
		@Override
		protected boolean checkCompatibility(Enchantment otherEnchant) {
			return !(otherEnchant instanceof MendingEnchantment) && super.checkCompatibility(otherEnchant);
		}
	});

	public static final Supplier<Enchantment> SCORCH_SHOT = ENCHANTMENTS.register("scorch_shot", () -> new Enchantment(Enchantment.definition(
			FIREARMS,
			2,
			1,
			Enchantment.constantCost(20),
			Enchantment.constantCost(50),
			4,
			EquipmentSlot.MAINHAND
	)));

	public static final Supplier<Enchantment> RAPID_FIRE = ENCHANTMENTS.register("rapid_fire", () -> new Enchantment(Enchantment.definition(
			FIREARMS,
			5,
			3,
			Enchantment.dynamicCost(10, 15),
			Enchantment.dynamicCost(16, 15),
			5,
			EquipmentSlot.MAINHAND
	)));

	public static final Supplier<Enchantment> VELOCITY = ENCHANTMENTS.register("velocity", () -> new Enchantment(Enchantment.definition(
			RANGED_WEAPONS,
			5,
			3,
			Enchantment.dynamicCost(15, 20),
			Enchantment.dynamicCost(25, 20),
			5,
			EquipmentSlot.MAINHAND
	)));

	public static final Supplier<Enchantment> EXTENDED_REACH = ENCHANTMENTS.register("extended_reach", () -> new Enchantment(Enchantment.definition(
			PIKES,
			2,
			1,
			Enchantment.constantCost(15),
			Enchantment.constantCost(30),
			3,
			EquipmentSlot.MAINHAND
	)));

	public static final Supplier<DamageEnchantment> SHARPENED_HEAD = ENCHANTMENTS.register("sharpened_head", () -> new DamageEnchantment(Enchantment.definition(
			PIKES,
			10,
			5,
			Enchantment.dynamicCost(1, 11),
			Enchantment.dynamicCost(21, 11),
			1,
			EquipmentSlot.MAINHAND
	),
			Optional.empty()
	));

	public static final Supplier<Enchantment> CRIMSON_CLAW = ENCHANTMENTS.register("crimson_claw", () -> new Enchantment(Enchantment.definition(
			GAUNTLETS,
			2,
			3,
			Enchantment.dynamicCost(10, 15),
			Enchantment.dynamicCost(16, 15),
			3,
			FeatureFlagSet.of(),
			HANDS
	)));

	public static final Supplier<Enchantment> EXCESSIVE_FORCE = ENCHANTMENTS.register("excessive_force", () -> new Enchantment(Enchantment.definition(
			GAUNTLETS,
			2,
			2,
			Enchantment.dynamicCost(20, 20),
			Enchantment.dynamicCost(21, 20),
			4,
			FeatureFlagSet.of(),
			HANDS
	)));

	public static final Supplier<Enchantment> REGENERATIVE_ASSAULT = ENCHANTMENTS.register("regenerative_assault", () -> new Enchantment(Enchantment.definition(
			WEAPONS_AND_TOOLS,
			1,
			3,
			Enchantment.dynamicCost(20, 20),
			Enchantment.dynamicCost(25, 20),
			6,
			FeatureFlagSet.of(),
			HANDS
	)));

	public static final Supplier<Enchantment> HEAVY_COMET = ENCHANTMENTS.register("heavy_comet", () -> new Enchantment(Enchantment.definition(
			METEOR_STAFFS,
			2,
			2,
			Enchantment.dynamicCost(20, 20),
			Enchantment.dynamicCost(21, 20),
			4,
			FeatureFlagSet.of(),
			HANDS
	)));

	public static final Supplier<Enchantment> BURNING_HEAT = ENCHANTMENTS.register("burning_heat", () -> new Enchantment(Enchantment.definition(
			METEOR_STAFFS,
			2,
			1,
			Enchantment.constantCost(25),
			Enchantment.constantCost(50),
			3,
			FeatureFlagSet.of(),
			HANDS
	)));

	public static final Supplier<Enchantment> CELESTIAL_FURY = ENCHANTMENTS.register("celestial_fury", () -> new Enchantment(Enchantment.definition(
			METEOR_STAFFS,
			1,
			1,
			Enchantment.constantCost(35),
			Enchantment.constantCost(50),
			5,
			FeatureFlagSet.of(),
			HANDS
	)));

	public static final Supplier<Enchantment> NIGHTMARISH_STARE = ENCHANTMENTS.register("nightmarish_stare", () -> new Enchantment(Enchantment.definition(
			CURSED_SIGHT_STAFFS,
			5,
			7,
			Enchantment.dynamicCost(10, 10),
			Enchantment.dynamicCost(15, 15),
			3,
			FeatureFlagSet.of(),
			HANDS
	)));

	public static final Supplier<Enchantment> MALEVOLENT_GAZE = ENCHANTMENTS.register("malevolent_gaze", () -> new Enchantment(Enchantment.definition(
			CURSED_SIGHT_STAFFS,
			2,
			3,
			Enchantment.dynamicCost(20, 20),
			Enchantment.dynamicCost(21, 25),
			5,
			FeatureFlagSet.of(),
			HANDS
	)));
}