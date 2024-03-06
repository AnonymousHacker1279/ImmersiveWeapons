package tech.anonymoushacker1279.immersiveweapons.util.markers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TextureMetadataMarker {
	/**
	 * The frame time of an animation.
	 */
	int frameTime() default 1;

	/**
	 * Whether the animation frames should be interpolated.
	 */
	boolean interpolate() default false;

	/**
	 * A list of frames, to be animated in the given order.
	 */
	int[] frames() default {};

	/**
	 * For specific groups that all share the same properties, an {@link PredefinedGroups}
	 * enum can be provided instead of manually defining all the properties multiple times.
	 */
	PredefinedGroups predefinedGroup() default PredefinedGroups.NONE;

	enum PredefinedGroups {
		NONE,
		VENTUS_TOOLS,
		STARSTORM_ITEMS
	}
}