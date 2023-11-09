package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.MissingMappingsEvent.Mapping;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles remapping old item IDs to new ones.
 */
public class LegacyMappingsHandler {

	public static void remapItems(List<Mapping<Item>> mappings) {
		ImmersiveWeapons.LOGGER.warn("Missing item mappings were found. This probably means an item was renamed or deleted. Attempting to remap...");

		List<String> remappedItems = new ArrayList<>(0);

		ResourceLocation SMALL_PARTS_METAL_THROWABLE_BOMB = new ResourceLocation(ImmersiveWeapons.MOD_ID, "small_parts_metal_throwable_bomb");
		ResourceLocation SMALL_PARTS_METAL_TOOL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "small_parts_metal_tool");
		ResourceLocation SMOKE_BOMB = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb");
		ResourceLocation SMOKE_BOMB_RED = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_red");
		ResourceLocation SMOKE_BOMB_GREEN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_green");
		ResourceLocation SMOKE_BOMB_BLUE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_blue");
		ResourceLocation SMOKE_BOMB_PURPLE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_purple");
		ResourceLocation SMOKE_BOMB_YELLOW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_yellow");
		ResourceLocation SMOKE_BOMB_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow");
		ResourceLocation SMOKE_BOMB_ARROW_RED = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_red");
		ResourceLocation SMOKE_BOMB_ARROW_GREEN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_green");
		ResourceLocation SMOKE_BOMB_ARROW_BLUE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_blue");
		ResourceLocation SMOKE_BOMB_ARROW_PURPLE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_purple");
		ResourceLocation SMOKE_BOMB_ARROW_YELLOW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_yellow");
		ResourceLocation GOLD_PIKE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_pike");
		ResourceLocation GOLD_GAUNTLET = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_gauntlet");
		ResourceLocation GOLD_PIKE_HEAD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_pike_head");
		ResourceLocation GOLD_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_arrow");
		ResourceLocation GOLD_MUSKET_BALL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_musket_ball");
		ResourceLocation WOOD_PIKE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_pike");
		ResourceLocation WOOD_GAUNTLET = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_gauntlet");
		ResourceLocation WOOD_SHARD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_shard");
		ResourceLocation WOOD_TOOL_ROD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_tool_rod");
		ResourceLocation WOOD_PIKE_HEAD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_pike_head");
		ResourceLocation WOOD_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_arrow");
		ResourceLocation WOOD_MUSKET_BALL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_musket_ball");
		ResourceLocation CORRUGATED_IRON_PANEL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel");
		ResourceLocation CORRUGATED_IRON_PANEL_BARS = new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_bars");
		ResourceLocation CORRUGATED_IRON_PANEL_FLAT = new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_flat");
		ResourceLocation CORRUGATED_IRON_PANEL_FLAT_BARS = new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_flat_bars");

		for (Mapping<Item> itemMapping : mappings) {
			if (itemMapping.getKey().equals(SMALL_PARTS_METAL_THROWABLE_BOMB)) {
				itemMapping.remap(ItemRegistry.GRENADE_ASSEMBLY.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMALL_PARTS_METAL_TOOL)) {
				itemMapping.remap(ItemRegistry.TOOL_JOINT.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_RED)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_RED.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_GREEN)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_GREEN.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_BLUE)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_BLUE.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_PURPLE)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_PURPLE.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_YELLOW)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_YELLOW.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_ARROW)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_ARROW.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_ARROW_RED)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_ARROW_RED.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_ARROW_GREEN)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_ARROW_GREEN.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_ARROW_BLUE)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_ARROW_BLUE.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_ARROW_PURPLE)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(SMOKE_BOMB_ARROW_YELLOW)) {
				itemMapping.remap(ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(GOLD_PIKE)) {
				itemMapping.remap(ItemRegistry.GOLDEN_PIKE.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(GOLD_GAUNTLET)) {
				itemMapping.remap(ItemRegistry.GOLDEN_GAUNTLET.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(GOLD_PIKE_HEAD)) {
				itemMapping.remap(ItemRegistry.GOLDEN_PIKE_HEAD.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(GOLD_ARROW)) {
				itemMapping.remap(ItemRegistry.GOLDEN_ARROW.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(GOLD_MUSKET_BALL)) {
				itemMapping.remap(ItemRegistry.GOLDEN_MUSKET_BALL.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(WOOD_PIKE)) {
				itemMapping.remap(ItemRegistry.WOODEN_PIKE.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(WOOD_GAUNTLET)) {
				itemMapping.remap(ItemRegistry.WOODEN_GAUNTLET.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(WOOD_SHARD)) {
				itemMapping.remap(ItemRegistry.WOODEN_SHARD.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(WOOD_TOOL_ROD)) {
				itemMapping.remap(ItemRegistry.WOODEN_TOOL_ROD.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(WOOD_PIKE_HEAD)) {
				itemMapping.remap(ItemRegistry.WOODEN_PIKE_HEAD.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(WOOD_ARROW)) {
				itemMapping.remap(ItemRegistry.WOODEN_ARROW.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(WOOD_MUSKET_BALL)) {
				itemMapping.remap(ItemRegistry.WOODEN_MUSKET_BALL.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(CORRUGATED_IRON_PANEL) || itemMapping.getKey().equals(CORRUGATED_IRON_PANEL_FLAT)) {
				itemMapping.remap(BlockItemRegistry.IRON_PANEL_ITEM.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
			if (itemMapping.getKey().equals(CORRUGATED_IRON_PANEL_BARS) || itemMapping.getKey().equals(CORRUGATED_IRON_PANEL_FLAT_BARS)) {
				itemMapping.remap(BlockItemRegistry.IRON_PANEL_BARS_ITEM.get());
				remappedItems.add(itemMapping.getKey().getPath());
			}
		}

		ImmersiveWeapons.LOGGER.warn("Item remapping complete. Remapped entries: {}", remappedItems);
		ImmersiveWeapons.LOGGER.warn("{}/{} items remapped.", remappedItems.size(), mappings.size());
	}

	public static void remapBlocks(List<Mapping<Block>> mappings) {
		ImmersiveWeapons.LOGGER.warn("Missing block mappings were found. This probably means a block was renamed or deleted. Attempting to remap...");

		List<String> remappedBlocks = new ArrayList<>(0);

		ResourceLocation CORRUGATED_IRON_PANEL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel");
		ResourceLocation CORRUGATED_IRON_PANEL_BARS = new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_bars");
		ResourceLocation CORRUGATED_IRON_PANEL_FLAT = new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_flat");
		ResourceLocation CORRUGATED_IRON_PANEL_FLAT_BARS = new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_flat_bars");

		for (Mapping<Block> blockMapping : mappings) {
			if (blockMapping.getKey().equals(CORRUGATED_IRON_PANEL) || blockMapping.getKey().equals(CORRUGATED_IRON_PANEL_FLAT)) {
				blockMapping.remap(BlockRegistry.IRON_PANEL.get());
				remappedBlocks.add(blockMapping.getKey().getPath());
			}
			if (blockMapping.getKey().equals(CORRUGATED_IRON_PANEL_BARS) || blockMapping.getKey().equals(CORRUGATED_IRON_PANEL_FLAT_BARS)) {
				blockMapping.remap(BlockRegistry.IRON_PANEL_BARS.get());
				remappedBlocks.add(blockMapping.getKey().getPath());
			}
		}

		ImmersiveWeapons.LOGGER.warn("Block remapping complete. Remapped entries: {}", remappedBlocks);
		ImmersiveWeapons.LOGGER.warn("{}/{} blocks remapped.", remappedBlocks.size(), mappings.size());
	}

	public static void remapEntities(List<Mapping<EntityType<?>>> mappings) {
		ImmersiveWeapons.LOGGER.warn("Missing entity mappings were found. This probably means an entity was renamed or deleted. Attempting to remap...");

		List<String> remappedEntities = new ArrayList<>(0);

		ResourceLocation SMOKE_BOMB = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb");
		ResourceLocation SMOKE_BOMB_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow");
		ResourceLocation GOLD_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_arrow");
		ResourceLocation GOLD_MUSKET_BALL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_musket_ball");
		ResourceLocation WOOD_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_arrow");
		ResourceLocation WOOD_MUSKET_BALL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_musket_ball");

		for (Mapping<EntityType<?>> entityTypeMapping : mappings) {
			if (entityTypeMapping.getKey().equals(SMOKE_BOMB)) {
				entityTypeMapping.remap(EntityRegistry.SMOKE_GRENADE_ENTITY.get());
				remappedEntities.add(entityTypeMapping.getKey().getPath());
			}
			if (entityTypeMapping.getKey().equals(SMOKE_BOMB_ARROW)) {
				entityTypeMapping.remap(EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY.get());
				remappedEntities.add(entityTypeMapping.getKey().getPath());
			}
			if (entityTypeMapping.getKey().equals(GOLD_ARROW)) {
				entityTypeMapping.remap(EntityRegistry.GOLDEN_ARROW_ENTITY.get());
				remappedEntities.add(entityTypeMapping.getKey().getPath());
			}
			if (entityTypeMapping.getKey().equals(GOLD_MUSKET_BALL)) {
				entityTypeMapping.remap(EntityRegistry.GOLDEN_MUSKET_BALL_ENTITY.get());
				remappedEntities.add(entityTypeMapping.getKey().getPath());
			}
			if (entityTypeMapping.getKey().equals(WOOD_ARROW)) {
				entityTypeMapping.remap(EntityRegistry.WOODEN_ARROW_ENTITY.get());
				remappedEntities.add(entityTypeMapping.getKey().getPath());
			}
			if (entityTypeMapping.getKey().equals(WOOD_MUSKET_BALL)) {
				entityTypeMapping.remap(EntityRegistry.WOODEN_MUSKET_BALL_ENTITY.get());
				remappedEntities.add(entityTypeMapping.getKey().getPath());
			}
		}

		ImmersiveWeapons.LOGGER.warn("Entity remapping complete. Remapped entries: {}", remappedEntities);
		ImmersiveWeapons.LOGGER.warn("{}/{} entities remapped.", remappedEntities.size(), mappings.size());
	}

	public static void remapSoundEvents(List<Mapping<SoundEvent>> mappings) {
		ImmersiveWeapons.LOGGER.warn("Missing sound event mappings were found. This probably means a sound was renamed or deleted. Attempting to remap...");

		List<String> remappedSoundEvents = new ArrayList<>(0);

		ResourceLocation SMOKE_BOMB_HISS = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_hiss");

		for (Mapping<SoundEvent> soundEventMapping : mappings) {
			if (soundEventMapping.getKey().equals(SMOKE_BOMB_HISS)) {
				soundEventMapping.remap(SoundEventRegistry.SMOKE_GRENADE_HISS.get());
				remappedSoundEvents.add(soundEventMapping.getKey().getPath());
			}
		}

		ImmersiveWeapons.LOGGER.warn("Sound event remapping complete. Remapped entries: {}", remappedSoundEvents);
		ImmersiveWeapons.LOGGER.warn("{}/{} sounds remapped.", remappedSoundEvents.size(), mappings.size());
	}
}