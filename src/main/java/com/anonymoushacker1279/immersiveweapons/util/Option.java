package com.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.gui.widget.Slider;

import java.util.function.Supplier;

public abstract class Option<T> {

	private String name;
	protected T value;
	private T defaultValue;
	private T increment;
	private T minimum;
	private T maximum;

	public Option(String optionName, T value) {
		this.name = optionName;
		this.value = value;
		this.defaultValue = value;
	}

	public Option(String optionName, T value, T min, T max, T increment) {
		this.name = optionName;
		this.value = value;
		this.defaultValue = value;
		this.increment = increment;
		this.minimum = min;
		this.maximum = max;
	}

	public abstract void toggle();

	public abstract void readFromNBT(CompoundNBT tag);

	public abstract void writeToNBT(CompoundNBT tag);

	@SuppressWarnings("unchecked")
	public void copy(Option<?> option) {
		value = (T) option.get();
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

	public T getDefaultValue() {
		return defaultValue;
	}

	public T getIncrement() {
		return increment;
	}

	public T getMin() {
		return minimum;
	}

	public T getMax() {
		return maximum;
	}

	public boolean isSlider() {
		return false;
	}

	public static class IntOption extends Option<Integer> implements Slider.ISlider {
		private boolean slider;
		private Supplier<BlockPos> pos;

		public IntOption(String optionName, Integer value) {
			super(optionName, value);
		}

		public IntOption(String optionName, Integer value, Integer min, Integer max, Integer increment) {
			super(optionName, value, min, max, increment);
		}

		public IntOption(Supplier<BlockPos> pos, String optionName, Integer value, Integer min, Integer max, Integer increment, boolean s) {
			super(optionName, value, min, max, increment);
			slider = s;
			this.pos = pos;
		}

		@Override
		public void toggle() {
			if (isSlider())
				return;

			if (get() >= getMax()) {
				setValue(getMin());
				return;
			}

			if ((get() + getIncrement()) >= getMax()) {
				setValue(getMax());
				return;
			}

			setValue(get() + getIncrement());
		}

		@Override
		public void readFromNBT(CompoundNBT tag) {
			if (tag.contains(getName()))
				value = tag.getInt(getName());
			else
				value = getDefaultValue();
		}

		@Override
		public void writeToNBT(CompoundNBT tag) {
			tag.putInt(getName(), value);
		}

		@Override
		public String toString() {
			return (value) + "";
		}

		@Override
		public boolean isSlider() {
			return slider;
		}

		@Override
		public void onChangeSliderValue(Slider slider) {

		}
	}
}