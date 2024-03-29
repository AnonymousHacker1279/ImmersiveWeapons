package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeHolder;
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
import net.neoforged.bus.api.Event.Result;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.event.TickEvent.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.event.level.PistonEvent.PistonMoveType;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.StarstormCrystalBlock;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeLoader;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MudBallEntity;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.*;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.*;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.PistonCrushingRecipe;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.network.payload.DebugDataPayload;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncPlayerDataPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.util.*;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("9f470b49-0445-4341-ae85-55b9e5ec2a1c");

	public static final AttributeModifier JONNYS_CURSE_SPEED_MODIFIER = new AttributeModifier(
			UUID.fromString("c74619c0-b953-4ee7-a3d7-adf974c22d7d"),
			"Jonny's Curse speed modifier", -0.25d, Operation.MULTIPLY_BASE);

	private static final ResourceKey<Biome> DEADMANS_DESERT = ResourceKey.create(Registries.BIOME, new ResourceLocation(ImmersiveWeapons.MOD_ID, "deadmans_desert"));

	@SubscribeEvent
	public static void playerTickEvent(PlayerTickEvent event) {
		// Passive damage from the Deadman's Desert biome
		Player player = event.player;
		// Only check every 8 ticks, and make sure the player is not in creative mode
		if (player.tickCount % 8 == 0 && !player.isCreative()) {
			if (player.level().getBiome(player.blockPosition()).is(DEADMANS_DESERT)) {
				// If the player is under the effects of Celestial Protection, they are immune to damage
				if (!player.hasEffect(EffectRegistry.CELESTIAL_PROTECTION_EFFECT.get())) {
					player.hurt(IWDamageSources.DEADMANS_DESERT_ATMOSPHERE, 1);
				}
			}
		}

		if (player.tickCount % 20 == 0) {
			// Make KB resist a syncable attribute for debug tracing
			AttributeInstance kbResistAttribute = player.getAttributes().getInstance(Attributes.KNOCKBACK_RESISTANCE);
			if (kbResistAttribute != null) {
				if (!kbResistAttribute.getAttribute().isClientSyncable()) {
					kbResistAttribute.getAttribute().setSyncable(true);
				}
			}

			// Handle permanent hunger effect of Bloody Sacrifice and Jonny's Curse
			if (player.getPersistentData().getBoolean("used_curse_accessory_bloody_sacrifice")) {
				if (!player.hasEffect(MobEffects.HUNGER)) {
					player.addEffect(new MobEffectInstance(MobEffects.HUNGER, -1, 0, true, true));
				}
			}
			if (player.getPersistentData().getBoolean("used_curse_accessory_jonnys_curse")) {
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
					if (!attributeInstance.hasModifier(JONNYS_CURSE_SPEED_MODIFIER)) {
						if (GeneralUtilities.notJonny(player.getUUID())) {
							attributeInstance.addTransientModifier(JONNYS_CURSE_SPEED_MODIFIER);
						} else {
							AttributeModifier newModifier = new AttributeModifier(JONNYS_CURSE_SPEED_MODIFIER.getId(),
									JONNYS_CURSE_SPEED_MODIFIER.name,
									0.25d,
									JONNYS_CURSE_SPEED_MODIFIER.getOperation());

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
			if (AccessoryItem.isAccessoryActive(player, ItemRegistry.INSOMNIA_AMULET.get())) {
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
						player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0, true, true));
					}
				}
			}
		}

		// Handle the cooldown on the Venstral Jar accessory
		if (player.getCooldowns().isOnCooldown(ItemRegistry.VENSTRAL_JAR.get()) && player.onGround()) {
			player.getCooldowns().removeCooldown(ItemRegistry.VENSTRAL_JAR.get());
		}

		// Handle the temporary fire resistance effect on the Super Blanket Cape accessory
		if (AccessoryItem.isAccessoryActive(player, ItemRegistry.SUPER_BLANKET_CAPE.get())) {
			if (!player.isInLava() && !player.isOnFire()) {
				if (!player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
					player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, true, true));
				}
			}
		}

		// Debug tracing
		if (DebugTracingData.isDebugTracingEnabled) {
			if (player.isLocalPlayer()) {
				DebugTracingData.handleTracing(player);
			}
		}
		if (player.tickCount % 100 == 0 && player instanceof ServerPlayer serverPlayer) {
			int ticksSinceRest = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
			PacketDistributor.PLAYER.with(serverPlayer)
					.send(new DebugDataPayload(player.getUUID(), -1f, -1f, ticksSinceRest));
		}
	}

	@SubscribeEvent
	public static void livingHurtEvent(LivingHurtEvent event) {
		LivingEntity damagedEntity = event.getEntity();
		LivingEntity source = null;

		if (event.getSource().getEntity() instanceof LivingEntity sourceEntity) {
			source = sourceEntity;
		}

		if (event.getSource().is(IWDamageSources.METEOR_KEY) && event.getSource().getDirectEntity() instanceof MeteorEntity meteor && !meteor.getPersistentData().isEmpty()) {
			// Check for a "target" tag, and check if it matches the damaged entity's UUID
			if (!meteor.getPersistentData().getUUID("target").equals(damagedEntity.getUUID())) {
				event.setCanceled(true);
			}
		}

		// Handle environmental effects
		EnvironmentEffects.celestialProtectionEffect(event, damagedEntity);
		EnvironmentEffects.damageVulnerabilityEffect(event, damagedEntity);
		EnvironmentEffects.starstormArmorSetBonus(event, source);
		EnvironmentEffects.moltenArmorSetBonus(event, source, damagedEntity);

		// Handle accessory effects
		if (damagedEntity instanceof Player player) {
			AccessoryEffects.holyMantleEffect(event, player);
			AccessoryEffects.damageResistanceEffects(event, player);
			AccessoryEffects.bleedResistanceEffects(event, player);
			AccessoryEffects.bleedCancelEffects(event, player);
			AccessoryEffects.sonicBoomResistanceEffects(event, player);

			if (source != null) {
				AccessoryEffects.celestialSpiritEffect(player, source);
			}
		}

		if (source instanceof Player player) {
			AccessoryEffects.meleeDamageEffects(event, player);
			AccessoryEffects.projectileDamageEffects(event, player);
			AccessoryEffects.generalDamageEffects(event, player);
			AccessoryEffects.meleeBleedChanceEffects(event, player, damagedEntity);
			AccessoryEffects.generalWitherChanceEffects(player, damagedEntity);
		}

		AccessoryEffects.bloodySacrificeEffect(event, damagedEntity);
		AccessoryEffects.jonnysCurseEffect(event, damagedEntity);


		if (source instanceof ServerPlayer serverPlayer && serverPlayer.getServer() != null) {
			// If a Mud Ball was thrown, add an advancement
			if (event.getSource().getDirectEntity() instanceof MudBallEntity) {
				AdvancementHolder advancement = serverPlayer.getServer().getAdvancements().get(new ResourceLocation(ImmersiveWeapons.MOD_ID, "mud_ball"));

				if (advancement != null) {
					serverPlayer.getAdvancements().award(advancement, "");
				}
			}
		}
	}

	@SubscribeEvent
	public static void livingDamageEvent(LivingDamageEvent event) {
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

			int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentRegistry.REGENERATIVE_ASSAULT.get());

			// Heal the player for 10% of all damage dealt per level
			if (enchantmentLevel > 0) {
				player.heal(event.getAmount() * 0.1f * enchantmentLevel);
				player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
			}


			if (player instanceof ServerPlayer serverPlayer && serverPlayer.getServer() != null) {
				// If over 175 damage was dealt, add an advancement
				if (event.getAmount() >= 175.0f) {
					AdvancementHolder advancement = serverPlayer.getServer().getAdvancements().get(new ResourceLocation(ImmersiveWeapons.MOD_ID, "overkill"));

					if (advancement != null) {
						serverPlayer.getAdvancements().award(advancement, "");
					}
				}

				// Handle debug tracing
				PacketDistributor.PLAYER.with(serverPlayer)
						.send(new DebugDataPayload(player.getUUID(), event.getAmount(), -1f, -1));
			}
		}

		if (damagedEntity instanceof ServerPlayer serverPlayer) {
			// Handle debug tracing
			PacketDistributor.PLAYER.with(serverPlayer)
					.send(new DebugDataPayload(serverPlayer.getUUID(), -1f, event.getAmount(), -1));
		}
	}

	@SubscribeEvent
	public static void livingDeathEvent(LivingDeathEvent event) {
		LivingEntity dyingEntity = event.getEntity();

		if (event.getSource().getEntity() instanceof Player player) {
			// Handle charging of cursed accessories on kill
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

			// Handle kill count weapons
			ItemStack weapon = player.getItemInHand(player.getUsedItemHand());
			if (KillCountWeapon.hasKillCount(weapon)) {
				KillCountWeapon.incrementKillCount(weapon);
			}
		}

		if (dyingEntity.getTags().contains("ChampionTowerMinibossSpider")) {
			Level level = dyingEntity.level();
			for (int i = 0; i < 10; i++) {
				CaveSpider spider = EntityType.CAVE_SPIDER.create(level);
				if (spider != null) {
					spider.moveTo(dyingEntity.getX(), dyingEntity.getY(), dyingEntity.getZ(), dyingEntity.getYRot(), dyingEntity.getXRot());
					spider.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 4, true, false));
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
			PacketDistributor.PLAYER.with(player)
					.send(new SyncPlayerDataPayload(player.getPersistentData(), player.getUUID()));
		}

		// Handle the Velocity enchantment on bows (guns are handled in the gun code)
		if (event.getEntity() instanceof AbstractArrow arrow) {
			if (arrow.getOwner() instanceof Player player
					&& (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof BowItem
					|| player.getItemInHand(player.getUsedItemHand()).getItem() instanceof CrossbowItem)) {

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

			event.addModifier(NeoForgeMod.ENTITY_REACH.value(),
					new AttributeModifier(ATTACK_REACH_MODIFIER,
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

			event.addModifier(NeoForgeMod.ENTITY_REACH.value(),
					new AttributeModifier(ATTACK_REACH_MODIFIER,
							"Weapon modifier",
							-2.0d,
							Operation.ADDITION));
		}
	}

	@SubscribeEvent
	public static void prePistonEvent(PistonEvent.Pre event) {
		LevelAccessor levelAccessor = event.getLevel();

		// Handle piston crushing recipes
		if (event.getDirection() == Direction.DOWN && event.getPistonMoveType() == PistonMoveType.EXTEND && levelAccessor instanceof Level level) {
			BlockPos belowPos = event.getPos().below();
			BlockState belowState = event.getLevel().getBlockState(belowPos);

			List<RecipeHolder<PistonCrushingRecipe>> recipes = level.getRecipeManager()
					.getAllRecipesFor(RecipeTypeRegistry.PISTON_CRUSHING_RECIPE_TYPE.get());

			// Select a recipe that matches the blockstate of the block below the piston
			Optional<RecipeHolder<PistonCrushingRecipe>> recipe = recipes.stream()
					.filter(r -> r.value().matches(belowState.getBlock()))
					.findFirst();

			// If a recipe was found, drop the output of the recipe
			if (recipe.isPresent()) {
				ItemStack drop = recipe.get().value().getResultItem(level.registryAccess()).copy();
				drop.setCount(recipe.get().value().getRandomDropAmount());
				Block.popResource(level, belowPos, drop);

				// Destroy the block, and do not drop loot
				level.destroyBlock(belowPos, false);
			}

			if (belowState.getBlock() instanceof StarstormCrystalBlock) {
				// There is a 15% chance to spawn a Starmite when a Starstorm Crystal is crushed
				if (level.getRandom().nextFloat() <= 0.15) {
					StarmiteEntity entity = EntityRegistry.STARMITE_ENTITY.get().create(level);
					if (entity != null) {
						entity.moveTo(belowPos.getX(), belowPos.getY(), belowPos.getZ(), 0, 0);
						level.addFreshEntity(entity);
					}
				}
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

			// Increase the looting level from accessories
			AccessoryEffects.lootingEffects(event, player);
		}
	}

	@SubscribeEvent
	public static void livingDropsEvent(LivingDropsEvent event) {
		if (event.getSource().getEntity() instanceof Player player) {
			// 25% chance to drop items a second time with the Bloody Sacrifice curse
			if (player.getPersistentData().getBoolean("used_curse_accessory_bloody_sacrifice")) {
				if (player.getRandom().nextFloat() <= 0.25f) {
					ResourceLocation lootTable = event.getEntity().getLootTable();
					MinecraftServer server = event.getEntity().level().getServer();

					if (server != null) {
						LootTable table = server.getLootData().getLootTable(lootTable);

						table.getRandomItems(new LootParams.Builder((ServerLevel) event.getEntity().level())
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
			if (player.getPersistentData().getBoolean("used_curse_accessory_jonnys_curse")) {
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
		LivingEntity entity = event.getEntity();

		if (entity instanceof Player player) {
			AccessoryEffects.meleeCritChanceEffects(event, player);

			if (event.getResult() == Result.DEFAULT || event.getResult() == Result.ALLOW) {
				AccessoryEffects.meleeCritDamageBonusEffects(event, player);
			}
		}
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
			event.setOutput(KillCountWeapon.initialize(event.getLeft().copy(), 0));
			event.setCost(5);
		}
	}

	@SubscribeEvent
	public static void addReloadListenerEvent(AddReloadListenerEvent event) {
		event.addListener(new TradeLoader());
	}
}