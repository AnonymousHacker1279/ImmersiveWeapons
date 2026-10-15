package tech.anonymoushacker1279.immersiveweapons.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

public class IWKeyBinds {

	private static final KeyMapping.Category CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "main"));

	/// Only active while a firearm is held.
	///
	/// This has to count as conflicting with `IN_GAME`. NeoForge deactivates a binding with no key modifier while any
	/// modifier key is held (so Shift+key doesn't also trigger plain-key bindings), except for contexts that conflict with
	/// `IN_GAME`, which are exempt. Without that, firing and aiming stop working while crouching, or with Ctrl or Alt
	/// held. The cost is that the controls screen lists these as conflicting with the vanilla attack and use keys they
	/// share a default with, which is accurate.
	private static final IKeyConflictContext FIREARM_HELD = new IKeyConflictContext() {
		@Override
		public boolean isActive() {
			Minecraft minecraft = Minecraft.getInstance();
			return KeyConflictContext.IN_GAME.isActive()
					&& minecraft.player != null
					&& AbstractGunItem.getGunHand(minecraft.player) != null;
		}

		@Override
		public boolean conflicts(IKeyConflictContext other) {
			return this == other || other == KeyConflictContext.IN_GAME;
		}
	};

	public static final KeyMapping FIRE = new KeyMapping("key." + ImmersiveWeapons.MOD_ID + ".fire",
			FIREARM_HELD,
			Type.MOUSE,
			InputConstants.MOUSE_BUTTON_LEFT,
			CATEGORY);
	public static final KeyMapping AIM = new KeyMapping("key." + ImmersiveWeapons.MOD_ID + ".aim",
			FIREARM_HELD,
			Type.MOUSE,
			InputConstants.MOUSE_BUTTON_RIGHT,
			CATEGORY);
	public static final KeyMapping TOGGLE_ARMOR_EFFECT = new KeyMapping("key." + ImmersiveWeapons.MOD_ID + ".toggleArmorEffect",
			KeyConflictContext.IN_GAME,
			Type.KEYBOARD,
			InputConstants.KEY_N,
			CATEGORY);
	public static final KeyMapping ARMOR_ACTION = new KeyMapping("key." + ImmersiveWeapons.MOD_ID + ".armorAction",
			KeyConflictContext.IN_GAME,
			Type.MOUSE,
			InputConstants.MOUSE_BUTTON_5,
			CATEGORY);
}