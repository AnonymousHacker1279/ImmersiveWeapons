package com.anonymoushacker1279.immersiveweapons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MoltenBlock extends Block {

	public MoltenBlock() {
		super(Block.Properties.create(Material.IRON)
				.hardnessAndResistance(3.0f, 9.0f)
				.sound(SoundType.METAL)
				.harvestLevel(0)
				.harvestTool(ToolType.PICKAXE));
	}
}
