package com.anonymoushacker1279.immersiveweapons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ElectricOre extends Block {
	
	public ElectricOre() {
		super(Block.Properties.create(Material.ROCK)
				.hardnessAndResistance(4.0f, 5.0f)
				.sound(SoundType.STONE)
				.harvestLevel(3)
				.harvestTool(ToolType.PICKAXE)
			);
	}
	
}
