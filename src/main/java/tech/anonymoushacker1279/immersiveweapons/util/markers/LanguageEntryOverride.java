package tech.anonymoushacker1279.immersiveweapons.util.markers;

import tech.anonymoushacker1279.immersiveweapons.init.PotionRegistry;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LanguageEntryOverride {

	/**
	 * The value of the language entry.
	 * <p>
	 * Note specifically for {@link PotionRegistry} entries: Overrides will still have some modifications
	 * made due to potion variations.
	 */
	String value();
}