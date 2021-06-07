package com.anonymoushacker1279.immersiveweapons.util;

import net.minecraftforge.fml.client.gui.widget.Slider;

public abstract class Option<T> {

	private final String name;
	private final T minimum;
	private final T maximum;
	protected T value;

	public Option(String optionName, T value, T min, T max) {
		this.name = optionName;
		this.value = value;
		this.minimum = min;
		this.maximum = max;
	}

	public final String getName() {
		return name;
	}

	public T get() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getMin() {
		return minimum;
	}

	public T getMax() {
		return maximum;
	}

	public static class IntOption extends Option<Integer> implements Slider.ISlider {

		public IntOption(String optionName, Integer value, Integer min, Integer max) {
			super(optionName, value, min, max);
		}

		@Override
		public String toString() {
			return (value) + "";
		}

		@Override
		public void onChangeSliderValue(Slider slider) {

		}
	}
}