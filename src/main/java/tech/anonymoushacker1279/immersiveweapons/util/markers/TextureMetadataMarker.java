package tech.anonymoushacker1279.immersiveweapons.util.markers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TextureMetadataMarker {
	/// The frame time of an animation.
	int frameTime() default 1;

	/// Whether the animation frames should be interpolated.
	boolean interpolate() default false;

	/// A list of frames, to be animated in the given order.
	int[] frames() default {};
}