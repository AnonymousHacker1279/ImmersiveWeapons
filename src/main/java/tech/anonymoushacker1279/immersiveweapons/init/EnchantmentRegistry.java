package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.enchantment.*;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.AbstractGunItem;

@SuppressWarnings({"unused"})
public class EnchantmentRegistry {

	// Enchantment Register
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ImmersiveWeapons.MOD_ID);

	// Equipment slots
	public static final EquipmentSlot[] HANDS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

	// Enchantment categories
	public static final EnchantmentCategory GUN = EnchantmentCategory.create("gun", AbstractGunItem.class::isInstance);

	// Enchantments
	public static final RegistryObject<FirepowerEnchantment> FIREPOWER = ENCHANTMENTS.register("firepower", () -> new FirepowerEnchantment(Rarity.COMMON, GUN, HANDS));
	public static final RegistryObject<ImpactEnchantment> IMPACT = ENCHANTMENTS.register("impact", () -> new ImpactEnchantment(Rarity.RARE, GUN, HANDS));
	public static final RegistryObject<EndlessMusketPouchEnchantment> ENDLESS_MUSKET_POUCH = ENCHANTMENTS.register("endless_musket_pouch", () -> new EndlessMusketPouchEnchantment(Rarity.VERY_RARE, GUN, HANDS));
}