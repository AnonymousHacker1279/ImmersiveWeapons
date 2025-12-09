package tech.anonymoushacker1279.immersiveweapons.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWKeyBinds {

	private static final KeyMapping.Category CATEGORY = new KeyMapping.Category(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "main"));

	public static final KeyMapping TOGGLE_ARMOR_EFFECT = new KeyMapping("key." + ImmersiveWeapons.MOD_ID + ".toggleArmorEffect",
			KeyConflictContext.IN_GAME,
			Type.KEYSYM,
			InputConstants.KEY_N,
			CATEGORY);
	public static final KeyMapping ARMOR_ACTION = new KeyMapping("key." + ImmersiveWeapons.MOD_ID + ".armorAction",
			KeyConflictContext.IN_GAME,
			Type.MOUSE,
			4, // Mouse button 5
			CATEGORY);
}