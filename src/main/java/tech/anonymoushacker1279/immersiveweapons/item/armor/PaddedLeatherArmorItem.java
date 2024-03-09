package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class PaddedLeatherArmorItem extends DyeableArmorItem {

	private final String baseTextureLocation;
	private final String overlayTextureLocation;

	public PaddedLeatherArmorItem(ArmorMaterial material, Type type, Properties properties) {
		super(material, type, properties);

		baseTextureLocation = ImmersiveWeapons.MOD_ID + ":textures/armor/padded_leather_layer_" + (type == Type.LEGGINGS ? "2" : "1") + ".png";
		overlayTextureLocation = ImmersiveWeapons.MOD_ID + ":textures/armor/padded_leather_layer_" + (type == Type.LEGGINGS ? "2" : "1") + "_overlay.png";
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, @Nullable String type) {
		return type == null ? baseTextureLocation : overlayTextureLocation;
	}

	@Override
	public int getColor(ItemStack stack) {
		CompoundTag tag = stack.getTagElement(TAG_DISPLAY);
		return tag != null && tag.contains(TAG_COLOR, 99) ? tag.getInt(TAG_COLOR) : 10511680;
	}
}