package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.event.level.PistonEvent.PistonMoveType;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.events.ComputeEnchantedLootBonusEvent;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.StarstormCrystalBlock;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.MerchantTrades;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeLoader;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MudBallEntity;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryEffects;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryManager;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.EnvironmentEffects;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.CursedItem;
import tech.anonymoushacker1279.immersiveweapons.item.KillCountWeapon;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.armor.TickableArmor;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.PistonCrushingRecipe;
import tech.anonymoushacker1279.immersiveweapons.network.payload.DebugDataPayload;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncMerchantTradesPayload;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncPlayerDataPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.GAME)
public class ForgeEventSubscriber {

	public static final AttributeModifier JONNYS_CURSE_SPEED_MODIFIER = new AttributeModifier(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "jonnys_curse_speed_modifier"), -0.25d, Operation.ADD_MULTIPLIED_BASE);

	private static final ResourceKey<Biome> DEADMANS_DESERT = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "deadmans_desert"));

	@SubscribeEvent
	public static void playerTickEvent(PlayerTickEvent.Post event) {
		// Passive damage from the Deadman's Desert biome
		Player player = event.getEntity();
		// Only check every 8 ticks, and make sure the player is not in creative mode
		if (player.tickCount % 8 == 0 && !player.isCreative()) {
			if (player.level().getBiome(player.blockPosition()).is(DEADMANS_DESERT)) {
				// If the player is under the effects of Celestial Protection, they are immune to damage
				if (!player.hasEffect(EffectRegistry.CELESTIAL_PROTECTION_EFFECT)) {
					player.hurt(IWDamageSources.deadmansDesertAtmosphere(player.level().registryAccess()), 1);
				}
			}
		}

		if (player.tickCount % 20 == 0) {
			// Make KB resist a syncable attribute for debug tracing
			AttributeInstance kbResistAttribute = player.getAttributes().getInstance(Attributes.KNOCKBACK_RESISTANCE);
			if (kbResistAttribute != null) {
				if (!kbResistAttribute.getAttribute().value().isClientSyncable()) {
					kbResistAttribute.getAttribute().value().setSyncable(true);
				}
			}

			// Handle permanent hunger effect of Bloody Sacrifice and Jonny's Curse
			if (player.getPersistentData().getBooleanOr("used_curse_accessory_bloody_sacrifice", false)) {
				if (!player.hasEffect(MobEffects.HUNGER)) {
					player.addEffect(new MobEffectInstance(MobEffects.HUNGER, -1, 0, true, true));
				}
			}
			if (player.getPersistentData().getBooleanOr("used_curse_accessory_jonnys_curse", false)) {
				if (GeneralUtilities.notJonny(player.getUUID())) {
					if (!player.hasEffect(MobEffects.HUNGER)) {
						player.addEffect(new MobEffectInstance(MobEffects.HUNGER, -1, 2, true, true));
					}
				} else {
					if (!player.hasEffect(MobEffects.SATURATION)) {
						player.addEffect(new MobEffectInstance(MobEffects.SATURATION, -1, 0, true, true));
					}
				}

				AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
				if (attributeInstance != null) {
					if (!attributeInstance.hasModifier(JONNYS_CURSE_SPEED_MODIFIER.id())) {
						if (GeneralUtilities.notJonny(player.getUUID())) {
							attributeInstance.addTransientModifier(JONNYS_CURSE_SPEED_MODIFIER);
						} else {
							AttributeModifier newModifier = new AttributeModifier(JONNYS_CURSE_SPEED_MODIFIER.id(),
									0.25d,
									JONNYS_CURSE_SPEED_MODIFIER.operation());

							attributeInstance.addTransientModifier(newModifier);
						}
					}
				}
			}

			AccessoryManager.handleAccessoryMobEffects(player);
			AccessoryManager.handleAccessoryAttributes(player);
		}

		// Handle random debuffs with the Insomnia Amulet after 7 days of no sleep
		if (player.tickCount % 600 == 0 && player instanceof ServerPlayer serverPlayer) {
			if (Accessory.isAccessoryActive(player, ItemRegistry.INSOMNIA_AMULET.get())) {
				int timeSinceRest = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
				if (timeSinceRest > 168000) {
					float chance = player.getRandom().nextFloat();
					if (chance <= 0.1f) {
						player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0, true, true));
					}
					if (chance <= 0.2f) {
						player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0, true, true));
					}
					if (chance <= 0.3f) {
						player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200, 0, true, true));
					}
				}
			}
		}

		// Handle the cooldown on the Venstral Jar accessory
		ItemStack venstralJar = ItemRegistry.VENSTRAL_JAR.get().getDefaultInstance();
		ItemCooldowns cooldowns = player.getCooldowns();
		if (cooldowns.isOnCooldown(venstralJar) && player.onGround()) {
			cooldowns.removeCooldown(cooldowns.getCooldownGroup(venstralJar));
		}

		// Handle the temporary fire resistance effect on the Super Blanket Cape accessory
		if (Accessory.isAccessoryActive(player, ItemRegistry.SUPER_BLANKET_CAPE.get())) {
			if (!player.isInLava() && !player.isOnFire()) {
				if (!player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
					player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, true, true));
				}
			}
		}

		// Armor ticking
		player.getInventory().forEach(stack -> {
			if (stack.getItem() instanceof TickableArmor tickable) {
				tickable.playerTick(player.level(), player);
			}
		});

		// Debug tracing
		if (DebugTracingData.isDebugTracingEnabled) {
			if (player.isLocalPlayer()) {
				DebugTracingData.handleTracing(player);
			}
		}
		if (player.tickCount % 100 == 0 && player instanceof ServerPlayer serverPlayer) {
			int ticksSinceRest = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
			PacketDistributor.sendToPlayer(serverPlayer, new DebugDataPayload(player.getUUID(), -1f, -1f, ticksSinceRest));
		}
	}

	@SubscribeEvent
	public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
		LivingEntity damagedEntity = event.getEntity();
		LivingEntity sourceEntity = null;

		if (event.getSource().getEntity() instanceof LivingEntity source) {
			sourceEntity = source;
		}

		if (event.getSource().is(IWDamageSources.METEOR_KEY) && event.getSource().getDirectEntity() instanceof MeteorEntity meteor && !meteor.getPersistentData().isEmpty()) {
			// Check for a "target" tag, and check if it matches the damaged entity's UUID
			meteor.getPersistentData().read("targetEntityUUID", UUIDUtil.CODEC).ifPresent(uuid -> {
				if (!uuid.equals(damagedEntity.getUUID())) {
					event.setCanceled(true);
				}
			});
		}

		// Handle environmental effects
		EnvironmentEffects.celestialProtectionEffect(event, damagedEntity);
		EnvironmentEffects.damageVulnerabilityEffect(event, damagedEntity);
		EnvironmentEffects.starstormArmorSetBonus(event, sourceEntity);
		EnvironmentEffects.moltenArmorSetBonus(event, sourceEntity, damagedEntity);
		EnvironmentEffects.voidArmorSetBonus(event, sourceEntity, damagedEntity);

		// Handle accessory effects
		if (damagedEntity instanceof ServerPlayer player) {
			if (event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD) && Accessory.isAccessoryActive(player, ItemRegistry.VOID_BLESSING.get())) {
				// Warp back to spawn
				if (player.getServer() != null) {
					if (player.getRespawnConfig() != null) {
						ServerLevel targetLevel = player.getServer().getLevel(player.getRespawnConfig().dimension());
						if (targetLevel != null) {
							player.resetFallDistance();
							BlockPos respawnPos = player.getRespawnConfig().pos();
							player.teleportTo(targetLevel, respawnPos.getX(), respawnPos.getY(), respawnPos.getZ(), Set.of(), player.getYRot(), player.getXRot(), false);
						}
					} else {
						BlockPos spawnPos = player.level().getSharedSpawnPos();
						ServerLevel targetLevel = player.getServer().getLevel(player.level().dimension());
						if (targetLevel != null) {
							player.teleportTo(targetLevel, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), Set.of(), player.getYRot(), player.getXRot(), false);
						}
					}
				}
			}

			AccessoryEffects.holyMantleEffect(event, player);
			AccessoryEffects.damageResistanceEffects(event, player);
			AccessoryEffects.bleedResistanceEffects(event, player);
			AccessoryEffects.bleedCancelEffects(event, player);
			AccessoryEffects.sonicBoomResistanceEffects(event, player);

			if (sourceEntity != null) {
				AccessoryEffects.celestialSpiritEffect(player, sourceEntity);
			}
		}

		if (sourceEntity instanceof Player player) {
			AccessoryEffects.meleeDamageEffects(event, player);
			AccessoryEffects.projectileDamageEffects(event, player);
			AccessoryEffects.generalDamageEffects(event, player);
			AccessoryEffects.meleeBleedChanceEffects(event, player, damagedEntity);
			AccessoryEffects.generalWitherChanceEffects(player, damagedEntity);
		}

		AccessoryEffects.bloodySacrificeEffect(event, damagedEntity);
		AccessoryEffects.jonnysCurseEffect(event, damagedEntity);


		if (sourceEntity instanceof ServerPlayer serverPlayer && serverPlayer.getServer() != null) {
			// If a Mud Ball was thrown, add an advancement
			if (event.getSource().getDirectEntity() instanceof MudBallEntity) {
				AdvancementHolder advancement = serverPlayer.getServer().getAdvancements().get(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "mud_ball"));

				if (advancement != null) {
					serverPlayer.getAdvancements().award(advancement, "");
				}
			}
		}
	}

	@SubscribeEvent
	public static void preLivingDamageEvent(LivingDamageEvent.Pre event) {
		LivingEntity damagedEntity = event.getEntity();
		LivingEntity source = null;

		if (event.getSource().getEntity() instanceof LivingEntity sourceEntity) {
			source = sourceEntity;
		}

		if (source instanceof Player player) {
			// Handle Regenerative Assault enchantment
			ItemStack weapon = player.getItemInHand(player.getUsedItemHand());

			// Check if it was a thrown trident
			if (weapon.isEmpty() && event.getSource().getDirectEntity() instanceof ThrownTrident tridentEntity) {
				weapon = tridentEntity.getPickupItem();
			}

			HolderGetter<Enchantment> enchantmentGetter = damagedEntity.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
			int enchantmentLevel = weapon.getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.REGENERATIVE_ASSAULT));

			// Heal the player for 10% of all damage dealt per level
			if (enchantmentLevel > 0) {
				player.heal(event.getNewDamage() * 0.1f * enchantmentLevel);
				player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
			}

			if (player instanceof ServerPlayer serverPlayer && serverPlayer.getServer() != null) {
				// If over 175 damage was dealt, add an advancement
				if (event.getNewDamage() >= 175.0f) {
					AdvancementHolder advancement = serverPlayer.getServer().getAdvancements().get(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "overkill"));

					if (advancement != null) {
						serverPlayer.getAdvancements().award(advancement, "");
					}
				}

				// Handle debug tracing
				PacketDistributor.sendToPlayer(serverPlayer, new DebugDataPayload(player.getUUID(), event.getNewDamage(), -1f, -1));
			}
		}

		if (damagedEntity instanceof ServerPlayer serverPlayer) {
			// Handle debug tracing
			PacketDistributor.sendToPlayer(serverPlayer, new DebugDataPayload(serverPlayer.getUUID(), -1f, event.getNewDamage(), -1));
		}
	}

	@SubscribeEvent
	public static void livingDeathEvent(LivingDeathEvent event) {
		LivingEntity dyingEntity = event.getEntity();
		DataComponentType<Boolean> AT_MAX_CHARGE = DataComponentTypeRegistry.AT_MAX_CHARGE.get();

		if (event.getSource().getEntity() instanceof Player player) {
			// Handle charging of cursed accessories on kill
			List<ItemStack> curses = CursedItem.getCurses(player);
			for (ItemStack curse : curses) {
				Boolean maxCharge = curse.get(AT_MAX_CHARGE);
				if (maxCharge != null) {
					if (!curse.isDamaged() && !maxCharge) {
						curse.setDamageValue(100);
					}

					if (curse.getDamageValue() <= 100 && curse.getDamageValue() > 0) {
						curse.setDamageValue(curse.getDamageValue() - 1);

						// If at max charge, set a tag, so it will no longer charge
						if (curse.getDamageValue() == 0) {
							curse.set(AT_MAX_CHARGE, true);
						}
					}
				}
			}

			// Handle kill count weapons
			ItemStack weapon = player.getItemInHand(player.getUsedItemHand());
			if (KillCountWeapon.hasKillCount(weapon)) {
				KillCountWeapon.increment(weapon);
			}
		}

		if (dyingEntity.getTags().contains("ChampionTowerMinibossSpider")) {
			Level level = dyingEntity.level();
			for (int i = 0; i < 10; i++) {
				CaveSpider spider = EntityType.CAVE_SPIDER.create(level, EntitySpawnReason.EVENT);
				if (spider != null) {
					spider.snapTo(dyingEntity.getX(), dyingEntity.getY(), dyingEntity.getZ(), dyingEntity.getYRot(), dyingEntity.getXRot());
					spider.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 20, 4, true, false));
					level.addFreshEntity(spider);
				}
			}
		}
	}

	@SubscribeEvent
	public static void entityJoinLevelEvent(EntityJoinLevelEvent event) {
		// Sync player data from server to client
		if (event.getEntity() instanceof ServerPlayer player) {
			// Send update packet
			PacketDistributor.sendToPlayer(player, new SyncPlayerDataPayload(player.getPersistentData(), player.getUUID()));
		}

		// Handle the Velocity enchantment on bows (guns are handled in the gun code)
		if (event.getEntity() instanceof AbstractArrow arrow) {
			if (arrow.getOwner() instanceof Player player
					&& (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof BowItem
					|| player.getItemInHand(player.getUsedItemHand()).getItem() instanceof CrossbowItem)) {

				ItemStack bow = player.getItemInHand(player.getUsedItemHand());
				HolderGetter<Enchantment> enchantmentGetter = player.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
				// If the bow is not empty, and has the enchantment, apply its effect
				int enchantLevel = bow.getEnchantmentLevel(enchantmentGetter.get(IWEnchantments.VELOCITY).orElseThrow());
				if (!bow.isEmpty() && enchantLevel > 0) {
					// Each level increases velocity by 10%
					arrow.setDeltaMovement(arrow.getDeltaMovement().scale(1 + (0.1f * enchantLevel)));
				}
			}
		}

		// Handle Big Slime in the Champion Tower, because size is overridden during finalizeSpawn...
		if (event.getEntity() instanceof Slime slime && slime.getTags().contains("ChampionTowerMiniboss")) {
			slime.setSize(4, true);
			slime.getAttribute(Attributes.MAX_HEALTH).setBaseValue(150.0d);
			slime.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(50.0d);
			slime.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(10.0d);
			slime.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(5.0d);
			slime.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.7d);
			slime.heal(150);
		}
	}

	@SubscribeEvent
	public static void playerCloneEvent(PlayerEvent.Clone event) {
		// Copy old persistent data to the new player
		event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
	}

	@SubscribeEvent
	public static void prePistonEvent(PistonEvent.Pre event) {
		LevelAccessor levelAccessor = event.getLevel();

		// Handle piston crushing recipes
		if (event.getDirection() == Direction.DOWN && event.getPistonMoveType() == PistonMoveType.EXTEND && levelAccessor instanceof ServerLevel level) {
			BlockPos belowPos = event.getPos().below();
			BlockState belowState = event.getLevel().getBlockState(belowPos);

			RecipeHolder<PistonCrushingRecipe> recipe = level.recipeAccess()
					.getRecipeFor(RecipeTypeRegistry.PISTON_CRUSHING_RECIPE_TYPE.get(),
							new SingleRecipeInput(belowState.getBlock().asItem().getDefaultInstance()),
							level)
					.orElse(null);

			// If a recipe was found, drop the output of the recipe
			if (recipe != null) {
				ItemStack drop = recipe.value().result().copy();
				drop.setCount(recipe.value().getRandomDropAmount());
				Block.popResource(level, belowPos, drop);

				// Destroy the block, and do not drop loot
				level.destroyBlock(belowPos, false);
			}

			if (belowState.getBlock() instanceof StarstormCrystalBlock) {
				// There is a 15% chance to spawn a Starmite when a Starstorm Crystal is crushed
				if (level.getRandom().nextFloat() <= 0.15) {
					StarmiteEntity entity = EntityRegistry.STARMITE_ENTITY.get().create(level, EntitySpawnReason.EVENT);
					if (entity != null) {
						entity.snapTo(belowPos.getX(), belowPos.getY(), belowPos.getZ(), 0, 0);
						level.addFreshEntity(entity);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void computeEnchantedLootBonusEvent(ComputeEnchantedLootBonusEvent event) {
		if (event.getDamageSource() == null) {
			return;
		}

		if (event.getDamageSource().getEntity() instanceof Player player) {
			// Increase the looting level by 3 with the Bloody Sacrifice curse
			if (player.getPersistentData().getBooleanOr("used_curse_accessory_bloody_sacrifice", false)) {
				event.setEnchantmentLevel(event.getEnchantmentLevel() + 3);
			}

			// Increase the looting level from accessories
			AccessoryEffects.lootingEffects(event, player);
		}
	}

	@SubscribeEvent
	public static void livingDropsEvent(LivingDropsEvent event) {
		if (event.getSource().getEntity() instanceof Player player) {
			// 25% chance to drop items a second time with the Bloody Sacrifice curse
			if (player.getPersistentData().getBooleanOr("used_curse_accessory_bloody_sacrifice", false)) {
				if (player.getRandom().nextFloat() <= 0.25f) {
					ResourceKey<LootTable> lootTable = event.getEntity().getLootTable().orElseThrow();
					MinecraftServer server = event.getEntity().level().getServer();

					if (server != null) {
						LootTable table = server.reloadableRegistries().getLootTable(lootTable);

						table.getRandomItems(new LootParams.Builder((ServerLevel) event.getEntity().level())
										.withParameter(LootContextParams.THIS_ENTITY, event.getEntity())
										.withParameter(LootContextParams.ORIGIN, event.getEntity().position())
										.withParameter(LootContextParams.DAMAGE_SOURCE, event.getSource())
										.withParameter(LootContextParams.ATTACKING_ENTITY, player)
										.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
										.withParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, player)
										.withParameter(LootContextParams.BLOCK_STATE, Blocks.AIR.defaultBlockState())
										.withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
										.withParameter(LootContextParams.EXPLOSION_RADIUS, 0.0f)
										.create(LootContextParamSets.ENTITY))
								.forEach(stack -> {
									ItemEntity itemEntity = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack);
									itemEntity.setPickUpDelay(10);
									event.getDrops().add(itemEntity);
								});

						// Summon a cloud of particles around the entity
						ServerLevel level = server.getLevel(event.getEntity().level().dimension());
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
			if (player.getPersistentData().getBooleanOr("used_curse_accessory_jonnys_curse", false)) {
				if (GeneralUtilities.notJonny(player.getUUID()) && player.getRandom().nextFloat() <= 0.5f) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void livingKnockBackEvent(LivingKnockBackEvent event) {
		LivingEntity entity = event.getEntity();

		if (entity.getLastAttacker() instanceof Player player) {
			AccessoryEffects.meleeKnockbackEffects(event, player);
		}
	}

	@SubscribeEvent
	public static void criticalHitEvent(CriticalHitEvent event) {
		Player player = event.getEntity();

		AccessoryEffects.meleeCritChanceEffects(event, player);
		AccessoryEffects.meleeCritDamageBonusEffects(event, player);
	}

	@SubscribeEvent
	public static void livingExperienceDropEvent(LivingExperienceDropEvent event) {
		Player player = event.getAttackingPlayer();

		if (player != null) {
			AccessoryEffects.experienceEffects(event, player);
		}
	}

	@SubscribeEvent
	public static void anvilUpdateEvent(AnvilUpdateEvent event) {
		// Kill Counter recipe
		if (event.getRight().is(ItemRegistry.KILL_COUNTER.get()) && !KillCountWeapon.hasKillCount(event.getLeft())) {
			ItemStack output = event.getLeft().copy();
			KillCountWeapon.initialize(output);
			event.setOutput(output);
			event.setCost(5);
		}
	}

	@SubscribeEvent
	public static void addReloadListenerEvent(AddServerReloadListenersEvent event) {
		event.addListener(TradeLoader.ID, new TradeLoader());
	}

	@SubscribeEvent
	public static void registerBrewingRecipesEvent(RegisterBrewingRecipesEvent event) {
		ImmersiveWeapons.LOGGER.info("Registering brewing recipes");

		PotionBrewing.Builder builder = event.getBuilder();

		// Celestial potions
		builder.addMix(
				Potions.AWKWARD,
				BlockItemRegistry.MOONGLOW_ITEM.get(),
				PotionRegistry.CELESTIAL_BREW_POTION);
		builder.addMix(
				PotionRegistry.CELESTIAL_BREW_POTION,
				Items.REDSTONE,
				PotionRegistry.LONG_CELESTIAL_BREW_POTION);

		// Death potions
		builder.addMix(
				Potions.AWKWARD,
				BlockItemRegistry.DEATHWEED_ITEM.get(),
				PotionRegistry.DEATH_POTION);
		builder.addMix(
				PotionRegistry.DEATH_POTION,
				Items.GLOWSTONE_DUST,
				PotionRegistry.STRONG_DEATH_POTION);
		builder.addMix(
				PotionRegistry.DEATH_POTION,
				Items.REDSTONE,
				PotionRegistry.LONG_DEATH_POTION);
		builder.addMix(
				Potions.STRENGTH,
				Items.FERMENTED_SPIDER_EYE,
				PotionRegistry.DEATH_POTION);
		builder.addMix(
				Potions.STRONG_STRENGTH,
				Items.FERMENTED_SPIDER_EYE,
				PotionRegistry.STRONG_DEATH_POTION);
		builder.addMix(
				Potions.LONG_STRENGTH,
				Items.FERMENTED_SPIDER_EYE,
				PotionRegistry.LONG_DEATH_POTION);

		// Broken Armor potions
		builder.addMix(
				Potions.AWKWARD,
				Items.PRISMARINE_SHARD,
				PotionRegistry.BROKEN_ARMOR_POTION);
		builder.addMix(
				PotionRegistry.BROKEN_ARMOR_POTION,
				Items.GLOWSTONE_DUST,
				PotionRegistry.STRONG_BROKEN_ARMOR_POTION);
		builder.addMix(
				PotionRegistry.BROKEN_ARMOR_POTION,
				Items.REDSTONE,
				PotionRegistry.LONG_BROKEN_ARMOR_POTION);
	}


	@SubscribeEvent
	public static void onDatapackSyncEvent(OnDatapackSyncEvent event) {
		ImmersiveWeapons.LOGGER.info("Syncing datapack objects");

		for (Entry<EntityType<?>, MerchantTrades> entry : TradeLoader.TRADES.entrySet()) {
			SyncMerchantTradesPayload payload = new SyncMerchantTradesPayload(entry.getKey(), entry.getValue());
			PacketDistributor.sendToAllPlayers(payload);
		}

		event.sendRecipes(RecipeTypeRegistry.STAR_FORGE_RECIPE_TYPE.get(), RecipeTypeRegistry.SMALL_PARTS_RECIPE_TYPE.get());
	}
}