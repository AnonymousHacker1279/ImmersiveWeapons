package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public abstract class BasicArmorItem extends ArmorItem {

	private final String textureLocation;

	public BasicArmorItem(ArmorMaterial material, Type type, Properties properties) {
		super(material, type, properties);

		textureLocation = ImmersiveWeapons.MOD_ID + ":textures/armor/" + material.getName() + "_layer_" + (type == Type.LEGGINGS ? "2" : "1") + ".png";
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return textureLocation;
	}
}