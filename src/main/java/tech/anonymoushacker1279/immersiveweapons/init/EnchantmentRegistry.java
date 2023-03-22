package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.enchantment.*;
import tech.anonymoushacker1279.immersiveweapons.item.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.AbstractGunItem;

@SuppressWarnings({"unused"})
public class EnchantmentRegistry {

	// Enchantment Register
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ImmersiveWeapons.MOD_ID);

	// Equipment slots
	public static final EquipmentSlot[] HANDS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

	// Enchantment categories
	public static final EnchantmentCategory GUN = EnchantmentCategory.create("gun", AbstractGunItem.class::isInstance);
	public static final EnchantmentCategory GUN_OR_BOW = EnchantmentCategory.create("gun_or_bow", (item) -> item instanceof AbstractGunItem || item instanceof BowItem);
	public static final EnchantmentCategory PIKE = EnchantmentCategory.create("pike", PikeItem.class::isInstance);
	public static final EnchantmentCategory GAUNTLET = EnchantmentCategory.create("gauntlet", GauntletItem.class::isInstance);
	public static final EnchantmentCategory WEAPONS_OR_TOOLS = EnchantmentCategory.create("weapons_or_tools", (item) ->
			GUN.canEnchant(item) || PIKE.canEnchant(item) || GAUNTLET.canEnchant(item) ||
					EnchantmentCategory.DIGGER.canEnchant(item) || EnchantmentCategory.WEAPON.canEnchant(item) ||
					EnchantmentCategory.BOW.canEnchant(item) || EnchantmentCategory.CROSSBOW.canEnchant(item) ||
					EnchantmentCategory.TRIDENT.canEnchant(item));

	// Enchantments
	public static final RegistryObject<FirepowerEnchantment> FIREPOWER = ENCHANTMENTS.register("firepower", () -> new FirepowerEnchantment(Rarity.COMMON, GUN, HANDS));
	public static final RegistryObject<ImpactEnchantment> IMPACT = ENCHANTMENTS.register("impact", () -> new ImpactEnchantment(Rarity.RARE, GUN, HANDS));
	public static final RegistryObject<EndlessMusketPouchEnchantment> ENDLESS_MUSKET_POUCH = ENCHANTMENTS.register("endless_musket_pouch", () -> new EndlessMusketPouchEnchantment(Rarity.VERY_RARE, GUN, HANDS));
	public static final RegistryObject<ScorchShotEnchantment> SCORCH_SHOT = ENCHANTMENTS.register("scorch_shot", () -> new ScorchShotEnchantment(Rarity.RARE, GUN, HANDS));
	public static final RegistryObject<RapidFireEnchantment> RAPID_FIRE = ENCHANTMENTS.register("rapid_fire", () -> new RapidFireEnchantment(Rarity.UNCOMMON, GUN, HANDS));
	public static final RegistryObject<VelocityEnchantment> VELOCITY = ENCHANTMENTS.register("velocity", () -> new VelocityEnchantment(Rarity.UNCOMMON, GUN_OR_BOW, HANDS));
	public static final RegistryObject<ExtendedReachEnchantment> EXTENDED_REACH = ENCHANTMENTS.register("extended_reach", () -> new ExtendedReachEnchantment(Rarity.RARE, PIKE, HANDS));
	public static final RegistryObject<SharpenedHeadEnchantment> SHARPENED_HEAD = ENCHANTMENTS.register("sharpened_head", () -> new SharpenedHeadEnchantment(Rarity.COMMON, PIKE, HANDS));
	public static final RegistryObject<CrimsonClawEnchantment> CRIMSON_CLAW = ENCHANTMENTS.register("crimson_claw", () -> new CrimsonClawEnchantment(Rarity.RARE, GAUNTLET, HANDS));
	public static final RegistryObject<ExcessiveForceEnchantment> EXCESSIVE_FORCE = ENCHANTMENTS.register("excessive_force", () -> new ExcessiveForceEnchantment(Rarity.RARE, GAUNTLET, HANDS));
	public static final RegistryObject<RegenerativeAssaultEnchantment> REGENERATIVE_ASSAULT = ENCHANTMENTS.register("regenerative_assault", () -> new RegenerativeAssaultEnchantment(Rarity.VERY_RARE, WEAPONS_OR_TOOLS, HANDS));
}