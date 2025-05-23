package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

public class ModelLayerLocations {

	public static final ModelLayerLocation MINUTEMAN_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.MINUTEMAN_HEAD.get()), "main");
	public static final ModelLayerLocation FIELD_MEDIC_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.FIELD_MEDIC_HEAD.get()), "main");
	public static final ModelLayerLocation DYING_SOLDIER_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.DYING_SOLDIER_HEAD.get()), "main");
	public static final ModelLayerLocation THE_COMMANDER_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.THE_COMMANDER_HEAD.get()), "main");
	public static final ModelLayerLocation WANDERING_WARRIOR_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.WANDERING_WARRIOR_HEAD.get()), "main");
	public static final ModelLayerLocation HANS_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.HANS_HEAD.get()), "main");
	public static final ModelLayerLocation STORM_CREEPER_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.STORM_CREEPER_HEAD.get()), "main");
	public static final ModelLayerLocation SKELETON_MERCHANT_HEAD_LAYER = new ModelLayerLocation(
			BuiltInRegistries.BLOCK.getKey(BlockRegistry.SKELETON_MERCHANT_HEAD.get()), "main");
	public static final ModelLayerLocation BURNED_OAK_BOAT_LAYER = new ModelLayerLocation(
			BuiltInRegistries.ENTITY_TYPE.getKey(EntityRegistry.BURNED_OAK_BOAT_ENTITY.get()), "boat/burned_oak");
	public static final ModelLayerLocation BURNED_OAK_CHEST_BOAT_LAYER = new ModelLayerLocation(
			BuiltInRegistries.ENTITY_TYPE.getKey(EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY.get()), "boat/burned_oak_chest");
	public static final ModelLayerLocation STARDUST_BOAT_LAYER = new ModelLayerLocation(
			BuiltInRegistries.ENTITY_TYPE.getKey(EntityRegistry.STARDUST_BOAT_ENTITY.get()), "boat/stardust");
	public static final ModelLayerLocation STARDUST_CHEST_BOAT_LAYER = new ModelLayerLocation(
			BuiltInRegistries.ENTITY_TYPE.getKey(EntityRegistry.STARDUST_CHEST_BOAT_ENTITY.get()), "boat/stardust_chest");
}