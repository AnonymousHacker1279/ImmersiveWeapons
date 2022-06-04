package com.anonymoushacker1279.immersiveweapons.client;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class IWKeyBinds {

	private static final String CATEGORY = "key.categories." + ImmersiveWeapons.MOD_ID;

	public static final KeyMapping TOGGLE_ARMOR_EFFECT = new KeyMapping(ImmersiveWeapons.MOD_ID + ".key.toggleArmorEffect",
			KeyConflictContext.IN_GAME,
			Type.KEYSYM,
			InputConstants.KEY_N,
			CATEGORY);
}