package tech.anonymoushacker1279.immersiveweapons.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWKeyBinds {

	private static final String CATEGORY = "key.categories." + ImmersiveWeapons.MOD_ID;

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
	public static final KeyMapping DEBUG_TRACING = new KeyMapping("key." + ImmersiveWeapons.MOD_ID + ".debugTracing",
			KeyConflictContext.IN_GAME,
			Type.KEYSYM,
			InputConstants.KEY_F9,
			CATEGORY);
}