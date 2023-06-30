package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.level.PistonEvent;
import net.minecraftforge.event.level.PistonEvent.PistonMoveType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.registries.MissingMappingsEvent.Mapping;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.StarstormCrystalBlock;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData.LastDamageDealtPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryEffects;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.EnvironmentEffects;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.CursedItem;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.util.LegacyMappingsHandler;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	public static final UUID BLOATED_HEART_HEALTH_MODIFIER_UUID = UUID.fromString("e5485685-cfbe-422b-b7dd-eda29049a508");
	public static final AttributeModifier BLOATED_HEART_HEALTH_MODIFIER = new AttributeModifier(BLOATED_HEART_HEALTH_MODIFIER_UUID,
			"Bloated Heart boost", 4.0d, Operation.ADDITION);
	public static final UUID JONNYS_CURSE_SPEED_MODIFIER_UUID = UUID.fromString("c74619c0-b953-4ee7-a3d7-adf974c22d7d");
	public static final AttributeModifier JONNYS_CURSE_SPEED_MODIFIER = new AttributeModifier(JONNYS_CURSE_SPEED_MODIFIER_UUID,
			"Jonny's Curse reduction", -0.25d, Operation.MULTIPLY_BASE);

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
			LegacyMappingsHandler.remapItems(mappings);
		}
	}

	/**
	 * Event handler for the MissingMappingsEvent.
	 * Migrates old block registry names to newer ones.
	 *
	 * @param event the <code>MissingMappingsEvent</code> instance
	 */
	@SubscribeEvent
	public static void missingBlockMappings(MissingMappingsEvent event) {
		List<Mapping<Block>> mappings = event.getMappings(ForgeRegistries.BLOCKS.getRegistryKey(), ImmersiveWeapons.MOD_ID);

		if (!mappings.isEmpty()) {
			LegacyMappingsHandler.remapBlocks(mappings);
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
			LegacyMappingsHandler.remapEntities(mappings);
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
			LegacyMappingsHandler.remapSoundEvents(mappings);
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
					player.hurt(IWDamageSources.DEADMANS_DESERT_ATMOSPHERE, 1);
				}
			}
		}

		if (player.tickCount % 20 == 0) {
			// Handle permanent hunger effect of Bloody Sacrifice and Jonny's Curse
			if (player.getPersistentData().getBoolean("used_curse_accessory_bloody_sacrifice")) {
				if (!player.hasEffect(MobEffects.HUNGER)) {
					player.addEffect(new MobEffectInstance(MobEffects.HUNGER, -1, 0, true, true));
				}
			}
			if (player.getPersistentData().getBoolean("used_curse_accessory_jonnys_curse")) {
				if (!player.hasEffect(MobEffects.HUNGER)) {
					player.addEffect(new MobEffectInstance(MobEffects.HUNGER, -1, 2, true, true));
				}
			}

			// Handle reduced movement speed of Jonny's Curse
			if (player.getPersistentData().getBoolean("used_curse_accessory_jonnys_curse")) {
				AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
				if (attributeInstance != null) {
					if (!attributeInstance.hasModifier(JONNYS_CURSE_SPEED_MODIFIER)) {
						attributeInstance.addTransientModifier(JONNYS_CURSE_SPEED_MODIFIER);
					}
				}
			}

			// Handle increased health effect of the Bloated Heart
			AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
			if (attributeInstance != null) {
				if (AccessoryItem.isAccessoryActive(player, ItemRegistry.BLOATED_HEART.get())) {
					if (!attributeInstance.hasModifier(BLOATED_HEART_HEALTH_MODIFIER)) {
						attributeInstance.addTransientModifier(BLOATED_HEART_HEALTH_MODIFIER);
					}
				} else if (attributeInstance.hasModifier(BLOATED_HEART_HEALTH_MODIFIER)) {
					attributeInstance.removeModifier(BLOATED_HEART_HEALTH_MODIFIER);
				}
			}

			// Handle constant Hero of the Village effect of the Emerald Ring
			if (AccessoryItem.isAccessoryActive(player, ItemRegistry.EMERALD_RING.get())) {
				if (!player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
					player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 60, 0, true, true));
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
		LivingEntity damagedEntity = event.getEntity();
		LivingEntity source = null;

		if (event.getSource().getEntity() instanceof LivingEntity sourceEntity) {
			source = sourceEntity;
		}

		if (event.getSource().is(IWDamageTypes.METEOR_KEY) && event.getSource().getDirectEntity() instanceof MeteorEntity meteor && !meteor.getPersistentData().isEmpty()) {
			// Check for a "target" tag, and check if it matches the damaged entity's UUID
			if (!meteor.getPersistentData().getUUID("target").equals(damagedEntity.getUUID())) {
				event.setCanceled(true);
			}
		}

		// Handle environmental effects
		EnvironmentEffects.celestialProtectionEffect(event, damagedEntity);
		EnvironmentEffects.damageVulnerabilityEffect(event, damagedEntity);
		EnvironmentEffects.starstormArmorSetBonus(event, source);
		EnvironmentEffects.moltenArmorSetBonus(event, source);

		// Handle accessory effects
		if (damagedEntity instanceof Player player) {
			AccessoryEffects.damageResistanceEffects(event, player);

			if (source != null) {
				AccessoryEffects.celestialSpiritEffect(player, source);
			}
		}

		if (source instanceof Player player) {
			AccessoryEffects.meleeDamageEffects(event, player);
			AccessoryEffects.projectileDamageEffects(event, player);
			AccessoryEffects.generalDamageEffects(event, player);
			AccessoryEffects.meleeBleedChanceEffects(event, player, damagedEntity);

			// Handle Regenerative Assault enchantment
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


			if (player instanceof ServerPlayer serverPlayer) {
				// If over 175 damage was dealt, add an advancement
				if (event.getAmount() >= 175.0f && serverPlayer.getServer() != null) {
					Advancement advancement = serverPlayer.getServer().getAdvancements()
							.getAdvancement(new ResourceLocation(ImmersiveWeapons.MOD_ID, "overkill"));

					if (advancement != null) {
						serverPlayer.getAdvancements().award(advancement, "");
					}
				}

				// Handle debug tracing
				PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new LastDamageDealtPacketHandler(player.getUUID(), event.getAmount()));
			}
		}

		AccessoryEffects.bloodySacrificeEffect(event, damagedEntity);
		AccessoryEffects.jonnysCurseEffect(event, damagedEntity);
	}

	@SubscribeEvent
	public static void livingDeathEvent(LivingDeathEvent event) {
		// Handle charging of cursed accessories on kill

		if (event.getSource().getEntity() instanceof Player player) {
			List<ItemStack> curses = CursedItem.getCurses(player);
			for (ItemStack curse : curses) {
				if (!curse.getOrCreateTag().getBoolean("max_charge")) {
					if (!curse.isDamaged()) {
						curse.setDamageValue(100);
					}

					if (curse.getDamageValue() <= 100 && curse.getDamageValue() >= 0) {
						curse.setDamageValue(curse.getDamageValue() - 1);

						// If at max charge, set a tag, so it will no longer charge
						if (curse.getDamageValue() == 0) {
							curse.getOrCreateTag().putBoolean("max_charge", true);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void entityJoinLevelEvent(EntityJoinLevelEvent event) {
		// Sync player data from server to client
		if (event.getEntity() instanceof ServerPlayer player) {
			// Send update packet
			PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerDataPacketHandler(player.getPersistentData(), player.getUUID()));
		}

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
	public static void playerCloneEvent(PlayerEvent.Clone event) {
		// Copy old persistent data to the new player
		event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
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

			event.addModifier(ForgeMod.ENTITY_REACH.get(),
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

			event.addModifier(ForgeMod.ENTITY_REACH.get(),
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

	@SubscribeEvent
	public static void levelLoadEvent(LevelEvent.Load event) {
		// Initialize custom damage sources
		IWDamageSources.init(event.getLevel().registryAccess());
	}

	@SubscribeEvent
	public static void lootingLevelEvent(LootingLevelEvent event) {
		if (event.getDamageSource() == null) {
			return;
		}

		if (event.getDamageSource().getEntity() instanceof Player player) {
			// Increase the looting level by 3 with the Bloody Sacrifice curse
			if (player.getPersistentData().getBoolean("used_curse_accessory_bloody_sacrifice")) {
				event.setLootingLevel(event.getLootingLevel() + 3);
			}

			// Increase the looting level by 2 with the Amethyst Ring
			if (AccessoryItem.isAccessoryActive(player, ItemRegistry.AMETHYST_RING.get())) {
				event.setLootingLevel(event.getLootingLevel() + 2);
			}
		}
	}

	@SubscribeEvent
	public static void livingDropsEvent(LivingDropsEvent event) {
		if (event.getSource().getEntity() instanceof Player player) {
			// 25% chance to drop items a second time with the Bloody Sacrifice curse
			if (player.getPersistentData().getBoolean("used_curse_accessory_bloody_sacrifice")) {
				if (player.getRandom().nextFloat() <= 0.25f) {
					ResourceLocation lootTable = event.getEntity().getLootTable();
					MinecraftServer server = event.getEntity().level.getServer();

					if (server != null) {
						LootTable table = server.getLootTables().get(lootTable);

						table.getRandomItems(new LootContext.Builder((ServerLevel) event.getEntity().level)
										.withParameter(LootContextParams.THIS_ENTITY, event.getEntity())
										.withParameter(LootContextParams.ORIGIN, event.getEntity().position())
										.withParameter(LootContextParams.DAMAGE_SOURCE, event.getSource())
										.withParameter(LootContextParams.KILLER_ENTITY, player)
										.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
										.withParameter(LootContextParams.DIRECT_KILLER_ENTITY, player)
										.withParameter(LootContextParams.BLOCK_STATE, Blocks.AIR.defaultBlockState())
										.withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
										.withParameter(LootContextParams.EXPLOSION_RADIUS, 0.0f)
										.create(LootContextParamSets.ENTITY))
								.forEach(stack -> {
									ItemEntity itemEntity = new ItemEntity(event.getEntity().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack);
									itemEntity.setPickUpDelay(10);
									event.getDrops().add(itemEntity);
								});

						// Summon a cloud of particles around the entity
						ServerLevel level = server.getLevel(event.getEntity().level.dimension());
						if (level != null) {
							level.sendParticles(
									ParticleTypes.SOUL,
									event.getEntity().getX(),
									event.getEntity().getY() + event.getEntity().getBbHeight() / 2.0d,
									event.getEntity().getZ(),
									10,
									0.5d,
									0.5d,
									0.5d,
									0.0d
							);
						}
					}
				}
			}

			// 50% chance to not get any drops with Jonny's Curse
			if (player.getPersistentData().getBoolean("used_curse_accessory_jonnys_curse")) {
				if (player.getRandom().nextFloat() <= 0.5f) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void livingKnockBackEvent(LivingKnockBackEvent event) {
		LivingEntity entity = event.getEntity();

		if (entity instanceof Player player) {
			AccessoryEffects.knockbackResistanceEffects(event, player);
		}

		if (entity.getLastAttacker() instanceof Player player) {
			AccessoryEffects.meleeKnockbackEffects(event, player);
		}
	}
}