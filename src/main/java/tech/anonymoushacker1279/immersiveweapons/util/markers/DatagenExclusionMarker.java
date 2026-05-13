package tech.anonymoushacker1279.immersiveweapons.util.markers;

import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatagenExclusionMarker {

	/// The type(s) of exclusion this registry entry should have.
	Type[] value();

	enum Type {
		/// Marks an [ItemRegistry] entry as being excluded from item model generation.
		MODEL_GENERATOR_ITEM,
		/// Marks a registry entry as being excluded from language file generation.
		LANGUAGE_GENERATOR
	}
}