package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class IWJukeboxSongs {

	public static final ResourceKey<JukeboxSong> STARLIGHT_PLAINS_THEME_1 = createKey("starlight_plains_theme_1");
	public static final ResourceKey<JukeboxSong> STARLIGHT_PLAINS_THEME_2 = createKey("starlight_plains_theme_2");
	public static final ResourceKey<JukeboxSong> TILTROS_WASTES_THEME = createKey("tiltros_wastes_theme");
	public static final ResourceKey<JukeboxSong> DEADMANS_DESERT_THEME_1 = createKey("deadmans_desert_theme_1");
	public static final ResourceKey<JukeboxSong> DEADMANS_DESERT_THEME_2 = createKey("deadmans_desert_theme_2");

	private static ResourceKey<JukeboxSong> createKey(String name) {
		return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstrapContext<JukeboxSong> context) {
		register(context, STARLIGHT_PLAINS_THEME_1, SoundEventRegistry.STARLIGHT_PLAINS_THEME_1, 2580f, 0);
		register(context, STARLIGHT_PLAINS_THEME_2, SoundEventRegistry.STARLIGHT_PLAINS_THEME_2, 2540f, 0);
		register(context, TILTROS_WASTES_THEME, SoundEventRegistry.TILTROS_WASTES_MUSIC, 3700f, 0);
		register(context, DEADMANS_DESERT_THEME_1, SoundEventRegistry.DEADMANS_DESERT_THEME_1, 2900f, 0);
		register(context, DEADMANS_DESERT_THEME_2, SoundEventRegistry.DEADMANS_DESERT_THEME_2, 2860f, 0);
	}

	private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder<SoundEvent> soundEvent, float duration, int comparatorOutput) {
		context.register(
				key,
				new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), duration, comparatorOutput)
		);
	}
}