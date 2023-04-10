package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class StarstormArmorItem extends ArmorItem {

	private final boolean isLeggings;

	public StarstormArmorItem(ArmorMaterial material, ArmorItem.Type armorType, Properties properties, boolean isLeggings) {
		super(material, armorType, properties);
		this.isLeggings = isLeggings;
	}

	/**
	 * Get the armor texture.
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param entity the <code>Entity</code> wearing the armor
	 * @param slot   the <code>EquipmentSlot</code>
	 * @param type   type ID
	 * @return String
	 */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return (!isLeggings
				? ImmersiveWeapons.MOD_ID + ":textures/armor/starstorm_layer_1.png"
				: ImmersiveWeapons.MOD_ID + ":textures/armor/starstorm_layer_2.png");
	}
}