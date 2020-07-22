package com.anonymoushacker1279.anonymoushacker1279s_mods;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AnonymousHacker1279s_Mods.MODID)
public final class AnonymousHacker1279s_Mods {

	public static final String MODID = "anonymoushacker1279s_mods";
	private static final Logger LOGGER = LogManager.getLogger();
	
	public AnonymousHacker1279s_Mods() {
		LOGGER.debug("Hello world from AnonymousHacker1279's Mods!");
	}

}