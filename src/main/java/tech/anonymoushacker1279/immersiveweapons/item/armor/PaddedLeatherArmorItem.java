package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class PaddedLeatherArmorItem extends DyeableArmorItem {

	private final boolean isLeggings;

	public PaddedLeatherArmorItem(ArmorMaterial material, Type type, Properties properties, boolean isLeggings) {
		super(material, type, properties);
		this.isLeggings = isLeggings;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, @Nullable String type) {
		if (type == null) {
			return (!isLeggings
					? ImmersiveWeapons.MOD_ID + ":textures/armor/padded_leather_layer_1.png"
					: ImmersiveWeapons.MOD_ID + ":textures/armor/padded_leather_layer_2.png");
		} else {
			return (!isLeggings
					? ImmersiveWeapons.MOD_ID + ":textures/armor/padded_leather_layer_1_%s.png".formatted(type)
					: ImmersiveWeapons.MOD_ID + ":textures/armor/padded_leather_layer_2_%s.png".formatted(type));
		}
	}

	@Override
	public int getColor(ItemStack stack) {
		CompoundTag tag = stack.getTagElement(TAG_DISPLAY);
		return tag != null && tag.contains(TAG_COLOR, 99) ? tag.getInt(TAG_COLOR) : 10511680;
	}
}