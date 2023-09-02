package tech.anonymoushacker1279.immersiveweapons.api;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import org.jetbrains.annotations.ApiStatus.Internal;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.*;

@SuppressWarnings("unused")
public class PluginHandler {

	private static boolean havePluginsInitialized = false;
	private static final List<IWPlugin> PLUGINS = new ArrayList<>(0);
	private static final LinkedHashSet<String> PLUGIN_NAMES = new LinkedHashSet<>(0);

	/**
	 * Initialize plugins. This should NOT be called in your own code, IW will automatically initialize plugins
	 * during its common setup stage.
	 *
	 * @param event the {@link FMLCommonSetupEvent} instance
	 */
	@Internal
	public static void initializePlugins(FMLCommonSetupEvent event) {
		ImmersiveWeapons.LOGGER.info("Initializing plugins");

		for (IWPlugin plugin : PLUGINS) {
			if (plugin.areLoadingRequirementsMet()) {
				ImmersiveWeapons.LOGGER.info("Initialized plugin {}", plugin.getPluginName());
				plugin.init(event);
			} else {
				ImmersiveWeapons.LOGGER.warn("Did not initialize plugin {}, because it reported loading requirements were not met", plugin.getPluginName());
			}
		}

		havePluginsInitialized = true;
	}

	/**
	 * Register your plugin here from your mod's constructor.
	 * <br><br>
	 * Registration will fail in either of the two conditions:
	 * <br>- a plugin already exists with the name of your plugin
	 * <br>- the initialization stage has already passed
	 *
	 * @param plugin the {@link IWPlugin} instance you are registering
	 */
	@AvailableSince("1.17.0")
	public static void registerPlugin(IWPlugin plugin) {
		String name = plugin.getPluginName();

		if (!havePluginsInitialized) {
			if (name.isEmpty()) {
				ImmersiveWeapons.LOGGER.error("A plugin failed to register, no plugin name was provided");
			}
			if (!PLUGIN_NAMES.add(name)) {
				ImmersiveWeapons.LOGGER.error("Plugin registration failed for {}, a plugin with this name already exists", name);
			} else {
				PLUGINS.add(plugin);
			}
		} else {
			ImmersiveWeapons.LOGGER.error("Plugin registration failed for {}, initialization has already completed", name);
		}
	}

	/**
	 * Check if a plugin has been registered. Note, a plugin may be registered but not active. If you are checking
	 * if a plugin is active, use {@link #isPluginActive(String)} instead.
	 *
	 * @param name the name of the plugin
	 * @return true if the plugin has been registered, false otherwise
	 */
	@AvailableSince("1.20.0")
	public static boolean isPluginRegistered(String name) {
		return PLUGIN_NAMES.contains(name);
	}

	/**
	 * Check if a plugin is active.
	 *
	 * @param name the name of the plugin
	 * @return true if the plugin is active, false otherwise
	 */
	@AvailableSince("1.23.0")
	public static boolean isPluginActive(String name) {
		for (IWPlugin plugin : PLUGINS) {
			if (plugin.getPluginName().equals(name) && plugin.areLoadingRequirementsMet()) {
				return true;
			}
		}
		return false;
	}
}