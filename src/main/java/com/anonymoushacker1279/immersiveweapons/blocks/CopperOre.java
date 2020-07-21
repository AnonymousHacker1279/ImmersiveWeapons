package com.anonymoushacker1279.immersiveweapons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class CopperOre extends Block {

	public CopperOre() {
		super(Block.Properties.create(Material.ROCK)
				.hardnessAndResistance(2.5f, 2.5f)
				.sound(SoundType.STONE)
				.harvestLevel(1)
				.harvestTool(ToolType.PICKAXE)
			);
	}
}
