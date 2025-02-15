package tech.anonymoushacker1279.immersiveweapons.util.markers;

import net.minecraft.ChatFormatting;
import tech.anonymoushacker1279.immersiveweapons.client.tooltip.DynamicTooltip;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TooltipMarker {

	/**
	 * An array of {@link ChatFormatting} styles to apply to the tooltip.
	 */
	ChatFormatting[] style() default {};

	/**
	 * The number of components the tooltip contains. Each component will be represented with an integer appended to the
	 * item name, if more than one exist. Used for simple tooltips.
	 */
	int components() default 1;

	/**
	 * The key to use for the tooltip. If not provided, the item name will be used.
	 */
	String key() default "";

	/**
	 * A class to handle dynamic tooltips. See {@link DynamicTooltip} for more information.
	 */
	Class<? extends DynamicTooltip> dynamicTooltip() default DynamicTooltip.class;
}