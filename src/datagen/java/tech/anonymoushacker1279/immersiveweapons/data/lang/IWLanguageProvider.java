package tech.anonymoushacker1279.immersiveweapons.data.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class IWLanguageProvider extends LanguageProvider {

	public IWLanguageProvider(PackOutput output, String id) {
		super(output, id, "en_us");
	}

	public void addPotion(String name, String type, String translation) {
		add("item.immersiveweapons." + type + "." + name, translation);
	}

	public void addContainer(String name, String translation) {
		add("container.immersiveweapons." + name, translation);
	}

	public void addSubtitle(String name, String translation) {
		add("subtitles.immersiveweapons." + name, translation);
	}

	public void addTooltip(String name, String translation) {
		add("tooltip.immersiveweapons." + name, translation);
	}

	public void addKey(String name, String translation) {
		add("key.immersiveweapons." + name, translation);
	}

	public void addMessage(String name, String translation) {
		add("immersiveweapons." + name, translation);
	}

	public void addDeathMessage(String name, String translation) {
		add("death.attack.immersiveweapons." + name, translation);
	}

	public void addBiome(String name, String translation) {
		add("biome.immersiveweapons." + name, translation);
	}

	public void addAdvancement(String name, String translation) {
		add("advancements.immersiveweapons." + name, translation);
	}

	public void addConfigDescription(String name, String translation) {
		add("config.immersiveweapons." + name, translation);
	}

	public void addNetworkingFailure(String name, String translation) {
		add("immersiveweapons.networking.failure." + name, translation);
	}
}