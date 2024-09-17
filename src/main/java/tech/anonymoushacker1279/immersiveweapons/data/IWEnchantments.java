package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWEnchantments {

	public static final ResourceKey<Enchantment> FIREPOWER = create("firepower");
	public static final ResourceKey<Enchantment> IMPACT = create("impact");
	public static final ResourceKey<Enchantment> ENDLESS_MUSKET_POUCH = create("endless_musket_pouch");
	public static final ResourceKey<Enchantment> SCORCH_SHOT = create("scorch_shot");
	public static final ResourceKey<Enchantment> RAPID_FIRE = create("rapid_fire");
	public static final ResourceKey<Enchantment> VELOCITY = create("velocity");
	public static final ResourceKey<Enchantment> EXTENDED_REACH = create("extended_reach");
	public static final ResourceKey<Enchantment> SHARPENED_HEAD = create("sharpened_head");
	public static final ResourceKey<Enchantment> CRIMSON_CLAW = create("crimson_claw");
	public static final ResourceKey<Enchantment> EXCESSIVE_FORCE = create("excessive_force");
	public static final ResourceKey<Enchantment> REGENERATIVE_ASSAULT = create("regenerative_assault");
	public static final ResourceKey<Enchantment> HEAVY_COMET = create("heavy_comet");
	public static final ResourceKey<Enchantment> BURNING_HEAT = create("burning_heat");
	public static final ResourceKey<Enchantment> CELESTIAL_FURY = create("celestial_fury");
	public static final ResourceKey<Enchantment> NIGHTMARISH_STARE = create("nightmarish_stare");
	public static final ResourceKey<Enchantment> MALEVOLENT_GAZE = create("malevolent_gaze");

	private static ResourceKey<Enchantment> create(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}
}