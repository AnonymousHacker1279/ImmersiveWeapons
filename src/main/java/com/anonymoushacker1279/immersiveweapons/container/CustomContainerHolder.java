package com.anonymoushacker1279.immersiveweapons.container;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ImmersiveWeapons.MOD_ID)
public class CustomContainerHolder {

	@ObjectHolder("small_parts_table")
	public static ContainerType<SmallPartsContainer> SMALL_PARTS_CONTAINER = null;

	@ObjectHolder("tesla_synthesizer")
	public static ContainerType<TeslaSynthesizerContainer> TESLA_SYNTHESIZER_CONTAINER = null;
}