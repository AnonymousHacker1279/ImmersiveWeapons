package tech.anonymoushacker1279.immersiveweapons.api;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;

public interface IWPlugin {

	/**
	 * Initialize your plugin here. This occurs during the {@link FMLCommonSetupEvent}, and the event
	 * instance is passed here.
	 * <br><br>
	 * It is not required for you to have anything here. If your plugin does not need to initialize, there is
	 * no need to override.
	 */
	@OverrideOnly
	@AvailableSince("1.17.0")
	default void init(FMLCommonSetupEvent event) {
	}

	/**
	 * Set your plugin name. This should ideally be in the format of {@code modid:pluginname}
	 * to avoid naming clashes.
	 *
	 * @return a string containing the plugin name
	 */
	@AvailableSince("1.17.0")
	default String getPluginName() {
		return "";
	}

	/**
	 * Determine if your loading requirements are met. For example, checking if a dependency is installed first.
	 * Do any of your requirement checks here. Returning {@code false} will prevent the plugin from initializing.
	 *
	 * @return a boolean determining if the plugin can initialize
	 */
	@AvailableSince("1.17.0")
	default boolean areLoadingRequirementsMet() {
		return true;
	}
}