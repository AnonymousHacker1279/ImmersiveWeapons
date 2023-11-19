package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

public class ModelLayerLocations {

	public static final ModelLayerLocation MINUTEMAN_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.MINUTEMAN_HEAD.get()), "main");
	public static final ModelLayerLocation FIELD_MEDIC_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.FIELD_MEDIC_HEAD.get()), "main");
	public static final ModelLayerLocation DYING_SOLDIER_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.DYING_SOLDIER_HEAD.get()), "main");
	public static final ModelLayerLocation WANDERING_WARRIOR_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.WANDERING_WARRIOR_HEAD.get()), "main");
	public static final ModelLayerLocation HANS_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.HANS_HEAD.get()), "main");
	public static final ModelLayerLocation STORM_CREEPER_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.STORM_CREEPER_HEAD.get()), "main");
	public static final ModelLayerLocation SKELETON_MERCHANT_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.SKELETON_MERCHANT_HEAD.get()), "main");
}