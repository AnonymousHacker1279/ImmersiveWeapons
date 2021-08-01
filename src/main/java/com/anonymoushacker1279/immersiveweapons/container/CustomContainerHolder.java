package com.anonymoushacker1279.immersiveweapons.container;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ImmersiveWeapons.MOD_ID)
public class CustomContainerHolder {

	@ObjectHolder("small_parts_table")
	public static final MenuType<SmallPartsContainer> SMALL_PARTS_CONTAINER = null;

	@ObjectHolder("tesla_synthesizer")
	public static final MenuType<TeslaSynthesizerContainer> TESLA_SYNTHESIZER_CONTAINER = null;
}