package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.PistonEvent;
import net.minecraftforge.event.level.PistonEvent.PistonMoveType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.registries.MissingMappingsEvent.Mapping;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.StarstormCrystalBlock;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.event.environment_effects.EnvironmentEffects;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	public static final DamageSource DEADMANS_DESERT_DAMAGE_SOURCE = new DamageSource("immersiveweapons.deadmans_desert").bypassArmor();

	@SubscribeEvent
	public static void registerGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {
		assert IWOverlays.SCOPE_ELEMENT != null;
		event.registerAbove(new ResourceLocation("vignette"),
				ImmersiveWeapons.MOD_ID + ":scope",
				IWOverlays.SCOPE_ELEMENT);

		assert IWOverlays.DEBUG_TRACING_ELEMENT != null;
		event.registerAbove(new ResourceLocation(ImmersiveWeapons.MOD_ID + ":scope"),
				ImmersiveWeapons.MOD_ID + ":debug_tracing",
				IWOverlays.DEBUG_TRACING_ELEMENT);
	}

	/**
	 * Event handler for the MissingMappingsEvent.
	 * Migrates old item registry names to newer ones.
	 *
	 * @param event the <code>MissingMappingsEvent</code> instance
	 */
	@SubscribeEvent
	public static void missingItemMappings(MissingMappingsEvent event) {
		List<Mapping<Item>> mappings = event.getMappings(ForgeRegistries.ITEMS.getRegistryKey(), ImmersiveWeapons.MOD_ID);

		if (!mappings.isEmpty()) {

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
			}

			ImmersiveWeapons.LOGGER.warn("Item remapping complete. Remapped entries: {}", remappedItems);
			ImmersiveWeapons.LOGGER.warn("{}/{} items remapped.", remappedItems.size(), mappings.size());
		}
	}

	/**
	 * Event handler for the MissingMappingsEvent.
	 * Migrates old entity registry names to newer ones.
	 *
	 * @param event the <code>MissingMappingsEvent</code> instance
	 */
	@SubscribeEvent
	public static void missingEntityMappings(MissingMappingsEvent event) {
		List<Mapping<EntityType<?>>> mappings = event.getMappings(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), ImmersiveWeapons.MOD_ID);

		if (!mappings.isEmpty()) {

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
	}

	/**
	 * Event handler for the MissingMappings (Sound) event.
	 * Migrates old registry names to newer ones.
	 *
	 * @param event the <code>MissingMappings</code> instance
	 */
	@SubscribeEvent
	public static void missingSoundEventMappings(MissingMappingsEvent event) {
		List<Mapping<SoundEvent>> mappings = event.getMappings(ForgeRegistries.SOUND_EVENTS.getRegistryKey(), ImmersiveWeapons.MOD_ID);

		if (!mappings.isEmpty()) {

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

	@SubscribeEvent
	public static void playerTickEvent(PlayerTickEvent event) {
		// Passive damage from the Deadman's Desert biome
		Player player = event.player;
		// Only check every 8 ticks, and make sure the player is not in creative mode
		if (player.tickCount % 8 == 0 && !player.isCreative()) {
			if (player.level.getBiome(player.blockPosition()).is(IWBiomes.DEADMANS_DESERT)) {
				// If the player is under the effects of Celestial Protection, they are immune to damage
				if (!player.hasEffect(EffectRegistry.CELESTIAL_PROTECTION_EFFECT.get())) {
					player.hurt(DEADMANS_DESERT_DAMAGE_SOURCE, 1);
				}
			}
		}

		// Debug tracing
		if (DebugTracingData.isDebugTracingEnabled) {
			if (player == Minecraft.getInstance().player) {
				DebugTracingData.handleTracing(player);
			}
		}
	}

	@SubscribeEvent
	public static void livingHurtEvent(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		LivingEntity source = null;
		if (event.getSource().getEntity() instanceof LivingEntity sourceEntity) {
			source = sourceEntity;
		}

		// Handle Regenerative Assault enchantment
		if (source instanceof Player player) {
			ItemStack weapon = player.getItemInHand(player.getUsedItemHand());

			// Check if it was a thrown trident
			if (weapon.isEmpty() && event.getSource().getDirectEntity() instanceof ThrownTrident tridentEntity) {
				weapon = tridentEntity.getPickupItem();
			}

			int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentRegistry.REGENERATIVE_ASSAULT.get());

			// Heal the player for 10% of all damage dealt per level
			if (enchantmentLevel > 0) {
				player.heal(event.getAmount() * 0.1f * enchantmentLevel);
				player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
			}
		}

		EnvironmentEffects effects = new EnvironmentEffects();

		// Celestial Protection effect
		effects.celestialProtectionEffect(event, entity);

		// Damage Vulnerability effect
		effects.damageVulnerabilityEffect(event, entity);

		// Starstorm armor set bonus
		effects.starstormArmorSetBonus(event, source);

		// Molten armor set bonus
		effects.moltenArmorSetBonus(event, source);
	}

	@SubscribeEvent
	public static void entityJoinLevelEvent(EntityJoinLevelEvent event) {
		// Handle the Velocity enchantment on bows (guns are handled in the gun code)
		if (event.getEntity() instanceof AbstractArrow arrow) {
			if (arrow.getOwner() instanceof Player player && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof BowItem) {
				ItemStack bow = player.getItemInHand(player.getUsedItemHand());
				// If the bow is not empty, and has the enchantment, apply its effect
				int enchantLevel = bow.getEnchantmentLevel(EnchantmentRegistry.VELOCITY.get());
				if (!bow.isEmpty() && enchantLevel > 0) {
					// Each level increases velocity by 10%
					arrow.setDeltaMovement(arrow.getDeltaMovement().scale(1 + (0.1f * enchantLevel)));
				}
			}
		}
	}

	@SubscribeEvent
	public static void itemAttributeModifierEvent(ItemAttributeModifierEvent event) {
		// Add reach distance attributes to pikes
		if (event.getItemStack().getItem() instanceof PikeItem pike && event.getSlotType() == EquipmentSlot.MAINHAND) {
			double damage = pike.damage;
			int enchantmentLevel = event.getItemStack().getEnchantmentLevel(EnchantmentRegistry.SHARPENED_HEAD.get());
			if (enchantmentLevel > 0) {
				damage += 1.0d + Math.max(0, enchantmentLevel - 1) * 0.5d;
			}

			event.addModifier(Attributes.ATTACK_DAMAGE,
					new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID,
							"Attack damage",
							damage,
							Operation.ADDITION));

			event.addModifier(Attributes.ATTACK_SPEED,
					new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID,
							"Attack speed",
							pike.attackSpeed,
							Operation.ADDITION));

			double distance = 0.5d;
			enchantmentLevel = event.getItemStack().getEnchantmentLevel(EnchantmentRegistry.EXTENDED_REACH.get());

			if (enchantmentLevel > 0) {
				distance += 0.5d * enchantmentLevel;
			}

			event.addModifier(ForgeMod.REACH_DISTANCE.get(),
					new AttributeModifier(GeneralUtilities.ATTACK_REACH_MODIFIER,
							"Reach distance",
							distance,
							Operation.ADDITION));
		}

		// Add reach distance attributes to gauntlets
		if (event.getItemStack().getItem() instanceof GauntletItem gauntlet && event.getSlotType() == EquipmentSlot.MAINHAND) {
			event.addModifier(Attributes.ATTACK_DAMAGE,
					new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID,
							"Attack damage",
							gauntlet.damage,
							Operation.ADDITION));

			event.addModifier(Attributes.ATTACK_SPEED,
					new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID,
							"Attack speed",
							gauntlet.attackSpeed,
							Operation.ADDITION));

			event.addModifier(ForgeMod.REACH_DISTANCE.get(),
					new AttributeModifier(GeneralUtilities.ATTACK_REACH_MODIFIER,
							"Weapon modifier",
							-2.0d,
							Operation.ADDITION));
		}
	}

	@SubscribeEvent
	public static void prePistonEvent(PistonEvent.Pre event) {
		// Handle dropping of Starstorm Shards by crushing Starstorm Crystals from above
		if (event.getDirection() == Direction.DOWN && event.getPistonMoveType() == PistonMoveType.EXTEND) {
			BlockPos belowPos = event.getPos().below();
			BlockState belowState = event.getLevel().getBlockState(belowPos);

			if (belowState.getBlock() == BlockRegistry.STARSTORM_CRYSTAL.get()) {
				StarstormCrystalBlock.handlePistonCrushing((Level) event.getLevel(), belowPos);
			}
		}
	}
}