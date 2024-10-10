package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class AttributeRegistry {

	// Attribute Register
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ImmersiveWeapons.MOD_ID);

	// Attributes
	public static final DeferredHolder<Attribute, Attribute> ARMOR_BREACH = ATTRIBUTES.register("armor_breach", () -> new RangedAttribute("immersiveweapons.attribute.armor_breach", 0.0d, 0.0d, 1.0d));
}