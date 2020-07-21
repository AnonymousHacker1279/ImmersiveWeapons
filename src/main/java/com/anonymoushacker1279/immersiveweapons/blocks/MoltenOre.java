package com.anonymoushacker1279.immersiveweapons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MoltenOre extends Block {

	public MoltenOre() {
		super(Block.Properties.create(Material.ROCK)
				.hardnessAndResistance(5.0f, 7.0f)
				.sound(SoundType.STONE)
				.harvestLevel(3)
				.harvestTool(ToolType.PICKAXE)
			);
	}
	
}
