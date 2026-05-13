package tech.anonymoushacker1279.immersiveweapons.util.markers;

import tech.anonymoushacker1279.immersiveweapons.init.PotionRegistry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LanguageEntryOverride {

	/// The value of the language entry.
	///
	/// Note specifically for [PotionRegistry] entries: Overrides will still have some modifications made due to potion
	/// variations.
	String value();
}