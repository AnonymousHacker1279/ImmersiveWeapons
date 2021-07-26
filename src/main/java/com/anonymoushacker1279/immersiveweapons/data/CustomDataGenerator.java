package com.anonymoushacker1279.immersiveweapons.data;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class CustomDataGenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();

		if (event.includeClient()) {
			generator.addProvider(new Lang(generator));
		}
		if (event.includeServer()) {
			generator.addProvider(new AdvancementProvider(generator));
		}
	}

	public static class Lang extends LanguageProvider {
		Lang(DataGenerator gen) {
			super(gen, ImmersiveWeapons.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			// add(Blocks.STONE, "Stone");
		}
	}
}