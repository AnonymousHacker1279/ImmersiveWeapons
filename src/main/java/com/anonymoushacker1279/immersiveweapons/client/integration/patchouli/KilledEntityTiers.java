package com.anonymoushacker1279.immersiveweapons.client.integration.patchouli;

import net.minecraft.network.chat.TranslatableComponent;

public class KilledEntityTiers {

	public static final TranslatableComponent PASSIVE = new TranslatableComponent("immersiveweapons.lorebook.killed_entity_tiers.passive");

	public static final TranslatableComponent MURDERER = new TranslatableComponent("immersiveweapons.lorebook.killed_entity_tiers.murderer");
	public static final TranslatableComponent SERIAL_KILLER = new TranslatableComponent("immersiveweapons.lorebook.killed_entity_tiers.serial_killer");
	public static final TranslatableComponent CULT_LEADER = new TranslatableComponent("immersiveweapons.lorebook.killed_entity_tiers.cult_leader");
	public static final TranslatableComponent SLAYER = new TranslatableComponent("immersiveweapons.lorebook.killed_entity_tiers.slayer");
	public static final TranslatableComponent KILL_EM_ALL = new TranslatableComponent("immersiveweapons.lorebook.killed_entity_tiers.kill_em_all");

	public static TranslatableComponent getTier(int kills) {
		int killRange = 0;
		
		if (kills < 20 && kills > 0) {
			killRange = 1;
		} else if (kills < 40 && kills > 20) {
			killRange = 2;
		} else if (kills < 80 && kills > 40) {
			killRange = 3;
		} else if (kills < 150 && kills > 80) {
			killRange = 4;
		} else if (kills > 300) {
			killRange = 5;
		}

		switch (killRange) {
			case 1 -> {
				return MURDERER;
			}
			case 2 -> {
				return SERIAL_KILLER;
			}
			case 3 -> {
				return CULT_LEADER;
			}
			case 4 -> {
				return SLAYER;
			}
			case 5 -> {
				return KILL_EM_ALL;
			}
			default -> {
				return PASSIVE;
			}
		}
	}
}