package tech.anonymoushacker1279.immersiveweapons.data.lang;

import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.lists.BlockLists;
import tech.anonymoushacker1279.immersiveweapons.data.lists.ItemLists;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LanguageGenerator extends IWLanguageProvider {

	public LanguageGenerator(PackOutput output) {
		super(output, ImmersiveWeapons.MOD_ID);
	}

	@Override
	protected void addTranslations() {
		addBlocks();
		addItems();
		addEntityTypes();
		addPotions();
		addEffects();
		addContainers();
		addSubtitles();
		addTooltips();
		addKeys();
		addMessages();
		addDeathMessages();
		addBiomes();
		addAdvancements();
		addConfigDescriptions();
		addEnchantments();
		addMisc();
	}

	private void addBlocks() {
		// Not all blocks are automatically added below; make a list of exceptions here
		List<Block> excludedBlocks = new ArrayList<>(25);
		// These are not added because they're variants of another block which already exists
		excludedBlocks.add(BlockRegistry.BURNED_OAK_WALL_SIGN.get());
		excludedBlocks.add(BlockRegistry.BURNED_OAK_WALL_HANGING_SIGN.get());
		excludedBlocks.add(BlockRegistry.STARDUST_WALL_SIGN.get());
		excludedBlocks.add(BlockRegistry.STARDUST_WALL_HANGING_SIGN.get());
		excludedBlocks.addAll(BlockLists.wallHeadBlocks);

		excludedBlocks.add(BlockRegistry.IRON_PANEL_BARS.get());
		excludedBlocks.add(BlockRegistry.COBALT_BLOCK.get());
		excludedBlocks.add(BlockRegistry.RAW_COBALT_BLOCK.get());
		excludedBlocks.add(BlockRegistry.RAW_SULFUR_BLOCK.get());

		// Filter the excluded blocks from the registry
		Stream<DeferredHolder<Block, ? extends Block>> blocks = BlockRegistry.BLOCKS.getEntries().stream()
				.filter(block -> !excludedBlocks.contains(block.get()));

		// Get a list of all blocks, and convert their registry names to proper names
		// Turn underscores into spaces, and capitalize the first letter of each word

		blocks.forEach(block -> {
			// Get the block name for the block
			String blockName = block.get().toString();
			// Remove the namespace from the block name
			blockName = blockName.substring(blockName.indexOf(":") + 1, blockName.length() - 1);

			// Convert underscores to spaces
			blockName = blockName.replace("_", " ");
			// Capitalize the first letter of all words
			blockName = capitalizeWords(blockName);

			// Add the block to the language file
			addBlock(block, blockName);
		});

		// Manually add the blocks that were excluded above
		addBlock(BlockRegistry.IRON_PANEL_BARS, "Iron Panel (Bars)");
		addBlock(BlockRegistry.COBALT_BLOCK, "Block of Cobalt");
		addBlock(BlockRegistry.RAW_COBALT_BLOCK, "Block of Raw Cobalt");
		addBlock(BlockRegistry.RAW_SULFUR_BLOCK, "Block of Raw Sulfur");
	}

	private void addItems() {
		// Not all items are automatically added below; make a list of exceptions here
		List<Item> excludedItems = new ArrayList<>(25);

		excludedItems.addAll(ItemLists.SMOKE_GRENADE_ITEMS);
		excludedItems.addAll(ItemLists.SMOKE_GRENADE_ARROW_ITEMS);

		excludedItems.add(ItemRegistry.MRE.get());
		excludedItems.add(ItemRegistry.HANS_SPAWN_EGG.get());
		excludedItems.add(ItemRegistry.SUPER_HANS_SPAWN_EGG.get());
		excludedItems.add(ItemRegistry.MUSKET_SCOPE.get());
		excludedItems.add(ItemRegistry.EXPLOSIVE_CHOCOLATE_BAR.get());
		excludedItems.add(ItemRegistry.BERSERKERS_AMULET.get());
		excludedItems.add(ItemRegistry.HANS_BLESSING.get());
		excludedItems.add(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get());
		excludedItems.add(ItemRegistry.JONNYS_CURSE.get());
		excludedItems.add(ItemRegistry.PADDED_LEATHER_HELMET.get());
		excludedItems.add(ItemRegistry.PADDED_LEATHER_CHESTPLATE.get());
		excludedItems.add(ItemRegistry.PADDED_LEATHER_LEGGINGS.get());
		excludedItems.add(ItemRegistry.DRAGONS_BREATH_BOW.get());

		// Filter the excluded items from the registry
		Stream<DeferredHolder<Item, ? extends Item>> items = ItemRegistry.ITEMS.getEntries().stream()
				.filter(item -> !excludedItems.contains(item.get()));
		// This will contain all BlockItems too, so filter those out
		items = items.filter(item -> !(item.get() instanceof BlockItem));

		// Get a list of all items, and convert their registry names to proper names
		// Turn underscores into spaces, and capitalize the first letter of each word
		items.forEach(item -> {
			// Get the item name for the item
			String itemName = item.get().toString();

			// Remove the namespace and prefix
			itemName = itemName.substring(itemName.indexOf(":") + 1);
			// Convert underscores to spaces
			itemName = itemName.replace("_", " ");
			// Capitalize the first letter of all words
			itemName = capitalizeWords(itemName);

			// Add the item to the language file
			addItem(item, itemName);
		});

		// Manually add the items that were excluded above
		addItem(ItemRegistry.SMOKE_GRENADE, "Smoke Grenade");
		addItem(ItemRegistry.SMOKE_GRENADE_BLUE, "Blue Smoke Grenade");
		addItem(ItemRegistry.SMOKE_GRENADE_RED, "Red Smoke Grenade");
		addItem(ItemRegistry.SMOKE_GRENADE_GREEN, "Green Smoke Grenade");
		addItem(ItemRegistry.SMOKE_GRENADE_PURPLE, "Purple Smoke Grenade");
		addItem(ItemRegistry.SMOKE_GRENADE_YELLOW, "Yellow Smoke Grenade");
		addItem(ItemRegistry.SMOKE_GRENADE_ARROW, "Smoke Grenade Arrow");
		addItem(ItemRegistry.SMOKE_GRENADE_ARROW_BLUE, "Blue Smoke Grenade Arrow");
		addItem(ItemRegistry.SMOKE_GRENADE_ARROW_RED, "Red Smoke Grenade Arrow");
		addItem(ItemRegistry.SMOKE_GRENADE_ARROW_GREEN, "Green Smoke Grenade Arrow");
		addItem(ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE, "Purple Smoke Grenade Arrow");
		addItem(ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW, "Yellow Smoke Grenade Arrow");
		addItem(ItemRegistry.MRE, "Meal Ready-to-Eat (MRE)");
		addItem(ItemRegistry.HANS_SPAWN_EGG, "Hans the Almighty Spawn Egg");
		addItem(ItemRegistry.SUPER_HANS_SPAWN_EGG, "Super Hans the Almighty Spawn Egg");
		addItem(ItemRegistry.MUSKET_SCOPE, "Musket (Scope)");
		addItem(ItemRegistry.EXPLOSIVE_CHOCOLATE_BAR, "Chocolate Bar");
		addItem(ItemRegistry.BERSERKERS_AMULET, "Berserker's Amulet");
		addItem(ItemRegistry.HANS_BLESSING, "Hans' Blessing");
		addItem(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE, "Melee Master's Molten Glove");
		addItem(ItemRegistry.JONNYS_CURSE, "Jonny's Curse");
		addItem(ItemRegistry.PADDED_LEATHER_HELMET, "Padded Leather Cap");
		addItem(ItemRegistry.PADDED_LEATHER_CHESTPLATE, "Padded Leather Tunic");
		addItem(ItemRegistry.PADDED_LEATHER_LEGGINGS, "Padded Leather Pants");
		addItem(ItemRegistry.DRAGONS_BREATH_BOW, "Dragon's Breath Bow");
	}

	private void addEntityTypes() {
		// Not all entities are automatically added below; make a list of exceptions here
		List<EntityType<?>> excludedEntities = new ArrayList<>(25);

		excludedEntities.add(EntityRegistry.HANS_ENTITY.get());
		excludedEntities.add(EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY.get());
		excludedEntities.add(EntityRegistry.STARDUST_CHEST_BOAT_ENTITY.get());

		// Filter the excluded entities from the registry
		Stream<DeferredHolder<EntityType<?>, ? extends EntityType<?>>> entities = EntityRegistry.ENTITY_TYPES.getEntries().stream()
				.filter(entity -> !excludedEntities.contains(entity.get()));

		// Get a list of all entities, and convert their registry names to proper names
		// Turn underscores into spaces, and capitalize the first letter of each word
		entities.forEach(entity -> {
			// Get the entity name for the entity
			String entityName = entity.getKey().location().toString();
			// Remove the namespace from the entity name
			entityName = entityName.substring(entityName.indexOf(":") + 1);

			// Convert underscores to spaces
			entityName = entityName.replace("_", " ");
			// Capitalize the first letter of all words
			entityName = capitalizeWords(entityName);

			// Add the entity to the language file
			addEntityType(entity, entityName);
		});

		// Manually add the entities that were excluded above
		addEntityType(EntityRegistry.HANS_ENTITY, "Hans The Almighty");
		addEntityType(EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY, "Burned Oak Boat with Chest");
		addEntityType(EntityRegistry.STARDUST_CHEST_BOAT_ENTITY, "Stardust Boat with Chest");
	}

	private void addPotions() {
		// Celestial Brew
		addPotion("celestial_brew", "potion", "Celestial Brew");
		addPotion("celestial_brew", "splash_potion", "Splash Celestial Brew");
		addPotion("celestial_brew", "lingering_potion", "Lingering Celestial Brew");
		addPotion("celestial_brew", "tipped_arrow", "Arrow of Celestial Brew");
		addPotion("long_celestial_brew", "potion", "Celestial Brew");
		addPotion("long_celestial_brew", "splash_potion", "Splash Celestial Brew");
		addPotion("long_celestial_brew", "lingering_potion", "Lingering Celestial Brew");
		addPotion("long_celestial_brew", "tipped_arrow", "Arrow of Celestial Brew");

		// Potion of Death
		addPotion("death", "potion", "Potion of Death");
		addPotion("death", "splash_potion", "Splash Potion of Death");
		addPotion("death", "lingering_potion", "Lingering Potion of Death");
		addPotion("death", "tipped_arrow", "Arrow of Death");
		addPotion("long_death", "potion", "Potion of Death");
		addPotion("long_death", "splash_potion", "Splash Potion of Death");
		addPotion("long_death", "lingering_potion", "Lingering Potion of Death");
		addPotion("long_death", "tipped_arrow", "Arrow of Death");
		addPotion("strong_death", "potion", "Potion of Death");
		addPotion("strong_death", "splash_potion", "Splash Potion of Death");
		addPotion("strong_death", "lingering_potion", "Lingering Potion of Death");
		addPotion("strong_death", "tipped_arrow", "Arrow of Death");
	}

	private void addEffects() {
		addEffect(EffectRegistry.MORPHINE_EFFECT, "Morphine");
		addEffect(EffectRegistry.BLEEDING_EFFECT, "Bleeding");
		addEffect(EffectRegistry.ALCOHOL_EFFECT, "Alcohol");
		addEffect(EffectRegistry.CELESTIAL_PROTECTION_EFFECT, "Celestial Protection");
		addEffect(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT, "Damage Vulnerability");
	}

	private void addContainers() {
		addContainer("small_parts_table", "Small Parts Crafting");
		addContainer("tesla_synthesizer", "Tesla Synthesizing");
		addContainer("ammunition_table", "Ammunition Crafting");
		addContainer("star_forge", "Star Forging");

		// Container tooltips
		addContainer("star_forge.si_inactive", "Inactive");
		addContainer("star_forge.si_active", "Active");
		addContainer("star_forge.temperature", "Thermal Capacity: %s");
		addContainer("star_forge.craft_progress", "Time Remaining: %s");
	}

	private void addSubtitles() {
		// Armor
		addSubtitle("armor.tesla.equip", "Tesla armor clanks");
		addSubtitle("armor.tesla.effect", "Tesla armor zaps");
		addSubtitle("armor.tesla.power_down", "Tesla armor powers down");
		addSubtitle("armor.tesla.power_up", "Tesla armor powers up");
		addSubtitle("armor.molten.equip", "Molten armor cracks");
		addSubtitle("armor.ventus.equip", "Ventus armor creaks");
		addSubtitle("armor.copper.equip", "Copper armor bends");
		addSubtitle("armor.cobalt.equip", "Cobalt armor clinks");
		addSubtitle("armor.astral.equip", "Astral armor resonates");
		addSubtitle("armor.starstorm.equip", "Starstorm armor shimmers");

		// Guns
		addSubtitle("item.gun.flintlock.fire", "Flintlock pistol fires");
		addSubtitle("item.gun.flintlock.misfire", "Gun misfires");
		addSubtitle("item.gun.blunderbuss.fire", "Blunderbuss fires");
		addSubtitle("item.gun.musket.fire", "Musket fires");
		addSubtitle("item.gun.flare_gun.fire", "Flare gun fires");
		addSubtitle("item.gun.hand_cannon.fire", "Hand cannon fires");
		addSubtitle("item.gun.bullet_whizz", "Bullet whizzes");

		// Misc items
		addSubtitle("item.smoke_grenade_hiss", "Smoke grenade hisses");
		addSubtitle("item.generic_item_throw", "Item flies");

		// Misc blocks
		addSubtitle("block.small_parts_table.used", "Small parts table used");
		addSubtitle("block.barbed_wire.rattle", "Barbed wire rattles");
		addSubtitle("block.bear_trap.close", "Bear trap closes");
		addSubtitle("block.spike_trap.extend", "Spike trap extends");
		addSubtitle("block.spike_trap.retract", "Spike trap retracts");
		addSubtitle("block.panic_alarm.alarm", "Alarm sounds");
		addSubtitle("block.mortar.fire", "Mortar fires");

		// Entity
		addSubtitle("entity.dying_soldier.ambient", "Soldier speaking");
		addSubtitle("entity.dying_soldier.death", "Soldier dies");
		addSubtitle("entity.dying_soldier.hurt", "Soldier hurts");
		addSubtitle("entity.dying_soldier.step", "Soldier walks");
		addSubtitle("entity.field_medic.ambient", "Medic speaking");
		addSubtitle("entity.field_medic.death", "Medic dies");
		addSubtitle("entity.field_medic.hurt", "Medic hurts");
		addSubtitle("entity.field_medic.step", "Medic walks");
		addSubtitle("entity.field_medic.attack", "Medic attacks");
		addSubtitle("entity.wandering_warrior.ambient", "Warrior grunts");
		addSubtitle("entity.wandering_warrior.death", "Warrior dies");
		addSubtitle("entity.wandering_warrior.hurt", "Warrior hurts");
		addSubtitle("entity.wandering_warrior.step", "Warrior walks");
		addSubtitle("entity.lava_revenant.ambient", "Lava Revenant screeches");
		addSubtitle("entity.lava_revenant.death", "Lava Revenant dies");
		addSubtitle("entity.lava_revenant.hurt", "Lava Revenant hurts");
		addSubtitle("entity.lava_revenant.flap", "Lava Revenant flaps");
		addSubtitle("entity.lava_revenant.swoop", "Lava Revenant swoops");
		addSubtitle("entity.lava_revenant.bite", "Lava Revenant bites");
		addSubtitle("entity.celestial_tower.ambient", "Celestial Tower resonates");
		addSubtitle("entity.celestial_tower.death", "Celestial Tower collapses");
		addSubtitle("entity.celestial_tower.hurt", "Celestial Tower hurts");
		addSubtitle("entity.celestial_tower.summon", "Celestial Tower summons");
		addSubtitle("entity.celestial_tower.vulnerable", "Celestial Tower becomes vulnerable");
		addSubtitle("entity.firefly.flying", "Firefly buzzes");
		addSubtitle("entity.starmite.ambient", "Starmite hums");
		addSubtitle("entity.starmite.death", "Starmite dies");
		addSubtitle("entity.starmite.hurt", "Starmite hurts");
		addSubtitle("entity.starmite.step", "Starmite scuttles");
	}

	private void addTooltips() {
		// Swords
		addTooltip("molten_sword", "Kill it with fire");
		addTooltip("tesla_sword", "Provides quite a shocking experience");
		addTooltip("ventus_sword", "Whoosh");
		addTooltip("astral_sword", "Fast Hands");
		addTooltip("starstorm_sword", "Inflict the wrath of the stars");
		addTooltip("the_sword_1", "Truly one of the greatest swords of all time");
		addTooltip("the_sword_2", "Combines the effects of Molten, Tesla, Ventus, Astral, and Starstorm swords");

		// Tier specific
		addTooltip("molten_tool", "Crouching while mining blocks will automatically smelt them");

		// Guns
		addTooltip("flintlock_pistol", "A basic flintlock firearm that excels in medium-range combat.");
		addTooltip("blunderbuss", "Good for hordes a short distance away. A primitive type of shotgun.");
		addTooltip("musket", "A simple firearm designed for long-range combat");
		addTooltip("flare_gun", "Launch bright flares into the sky");
		addTooltip("hand_cannon", "A powerful, but inaccurate, handheld cannon");
		// Gun metadata
		addTooltip("gun.meta.base_velocity", "Base Velocity: %s");
		addTooltip("gun.meta.base_reload_time", "Base Reload Time: %s seconds");
		addTooltip("gun.meta.base_knockback", "Base Knockback Level: %sx");
		addTooltip("gun.meta.selected_powder", "Selected Powder: %s ; (%s velocity modifier)");
		addTooltip("gun.meta.selected_ammo", "Selected Ammunition: %s");

		// Bows
		addTooltip("ice_bow", "Arrows will inflict slowness on enemies");
		addTooltip("dragons_breath_bow", "Arrows will explode on impact");
		addTooltip("aurora_bow", "Arrows will experience 4x less gravity");

		// Projectiles
		addTooltip("wooden_arrow", "35% less powerful than normal arrows. Highly inaccurate. At this point it's just blunt-force trauma.");
		addTooltip("stone_arrow", "15% less powerful than normal arrows. Kind of dull and weighs at least a pound.");
		addTooltip("golden_arrow", "10% more powerful than normal arrows");
		addTooltip("copper_arrow", "15% more powerful than normal arrows");
		addTooltip("iron_arrow", "35% more powerful than normal arrows");
		addTooltip("cobalt_arrow", "55% more powerful than normal arrows");
		addTooltip("diamond_arrow", "100% more powerful than normal arrows");
		addTooltip("netherite_arrow", "375% more powerful than normal arrows. Extremely sharp, powerful, and accurate. How can you even afford to shoot these?");
		addTooltip("molten_arrow", "450% more powerful than normal arrows. Burns enemies on impact.");
		addTooltip("tesla_arrow", "500% more powerful than normal arrows. Weakens enemies on impact.");
		addTooltip("ventus_arrow", "450% more powerful than normal arrows. Causes enemies to levitate on impact.");
		addTooltip("astral_arrow", "350% more powerful than normal arrows. Extremely accurate and has minimal falloff.");
		addTooltip("starstorm_arrow", "565% more powerful than normal arrows");
		addTooltip("smoke_grenade_arrow", "Obscure the vision of your enemies from afar");

		addTooltip("wooden_musket_ball", "Very inaccurate, and likely to cause a misfire. Why would anyone make wooden musket balls anyway?");
		addTooltip("stone_musket_ball", "A very heavy musket ball, and not too accurate.");
		addTooltip("golden_musket_ball", "Fancier than stone musket balls, but not much better. At least it won't misfire. Look at me! I have money to throw away!");
		addTooltip("copper_musket_ball", "A well constructed musket ball, made from copper.");
		addTooltip("iron_musket_ball", "A hard hitting musket ball, made from iron.");
		addTooltip("cobalt_musket_ball", "A strong and dense musket ball, made from cobalt.");
		addTooltip("diamond_musket_ball", "A very sharp and powerful musket ball. You're practically throwing away diamonds.");
		addTooltip("netherite_musket_ball", "An extremely powerful and skillfully crafted musket ball. For when you need to kill a chicken from the other side of a mountain.");
		addTooltip("molten_musket_ball", "A musket ball made from molten metal. Hard hitting and expensive, catches targets on fire.");
		addTooltip("tesla_musket_ball", "Made from a powerful Tesla alloy. Weakens targets.");
		addTooltip("ventus_musket_ball", "A musket ball made from Ventus shards. Causes targets to levitate.");
		addTooltip("astral_musket_ball", "Built using an Astral material. Extremely accurate and has little falloff.");
		addTooltip("starstorm_musket_ball", "A musket ball made from Starstorm. Strongest caliber in terms of raw damage.");
		addTooltip("cannonball", "A heavy metal ball that can be fired from a hand cannon.");
		addTooltip("explosive_cannonball", "Similar to the cannonball but explodes on impact.");
		addTooltip("flare", "Brightly illuminates the area around it.");
		// Projectile metadata
		addTooltip("bullet.meta.base_damage", "Base Damage: %s");
		addTooltip("bullet.meta.gravity_modifier", "Gravity Modifier: %s");
		addTooltip("bullet.meta.base_knockback_level", "Base Knockback Level: %sx");
		addTooltip("bullet.meta.piercing_level", "Piercing Level: %s target(s)");
		addTooltip("bullet.meta.density_modifier", "Density Modifier: +%sx");
		addTooltip("bullet.meta.misfire_chance", "Misfire Chance: %s%%");

		// Pikes
		addTooltip("pike", "Stab them from way over there");

		// Armor
		addTooltip("molten_armor", "Warm and toasty inside. Allows submersion in lava without burning, and improves vision/movement.");
		addTooltip("tesla_armor", "The EMF emitted makes nearby enemies sick (Toggleable with your Armor Effect Toggle key)");
		addTooltip("ventus_armor", "Unlocks a 13.1ft vertical and makes you as light as a feather (Toggleable with your Armor Effect Toggle key)");
		addTooltip("astral_armor", "Improves mobility and allows you to dash forward (Toggleable with your Armor Effect Toggle key)");
		addTooltip("starstorm_armor", "Increases raw damage output by 20%");
		addTooltip("padded_leather_armor", "Fully dampens vibrations caused by walking or running");

		// Throwables
		addTooltip("smoke_grenade", "Creates a thick cloud of smoke upon impact. Good for quickly escaping a situation.");
		addTooltip("smoke_grenade_red", "This one releases red smoke.");
		addTooltip("smoke_grenade_green", "This one releases green smoke.");
		addTooltip("smoke_grenade_blue", "This one releases blue smoke.");
		addTooltip("smoke_grenade_purple", "This one releases purple smoke.");
		addTooltip("smoke_grenade_yellow", "This one releases yellow smoke.");
		addTooltip("molotov_cocktail", "Creates a ring of fire upon impact.");
		addTooltip("mud_ball", "Throw a ball of mud at something");

		// Bottles
		addTooltip("bottle_of_alcohol", "You could drink this, but you'll be nauseous. Highly flammable!");
		addTooltip("bottle_of_wine", "A relaxing combination of sweet berries");

		// Traps
		addTooltip("punji_sticks", "Commit war crimes using Vietnam war tactics");
		addTooltip("pitfall", "Hide your traps with pitfalls. Pairs well with punji sticks.");
		addTooltip("bear_trap", "You do not have permission to move");
		addTooltip("landmine", "Bury their leftovers into a soup can");
		addTooltip("spike_trap", "Toggleable via redstone");
		addTooltip("wooden_spikes", "Poor man's barbed wire");

		// Gauntlets
		addTooltip("gauntlet", "Beat the life out of your opponents.");
		addTooltip("wooden_gauntlet", "Has a 15% chance to inflict Bleeding.");
		addTooltip("stone_gauntlet", "Has a 25% chance to inflict Bleeding.");
		addTooltip("golden_gauntlet", "Has a 35% chance to inflict Bleeding.");
		addTooltip("copper_gauntlet", "Has a 45% chance to inflict Bleeding.");
		addTooltip("iron_gauntlet", "Has a 55% chance to inflict Bleeding.");
		addTooltip("cobalt_gauntlet", "Has a 60% chance to inflict Bleeding.");
		addTooltip("diamond_gauntlet", "Has a 75% chance to inflict Bleeding.");
		addTooltip("netherite_gauntlet", "Has an 85% chance to inflict Bleeding.");
		addTooltip("molten_gauntlet", "Has a 95% chance to inflict Bleeding.");
		addTooltip("tesla_gauntlet", "Has a 95% chance to inflict Bleeding.");
		addTooltip("ventus_gauntlet", "Has a 95% chance to inflict Bleeding.");
		addTooltip("astral_gauntlet", "Has a 95% chance to inflict Bleeding.");
		addTooltip("starstorm_gauntlet", "Has a 95% chance to inflict Bleeding.");

		// Staffs
		addTooltip("ventus_staff", "Teach your enemies the importance of personal space");
		addTooltip("meteor_staff", "Summon meteors from the sky");
		addTooltip("cursed_sight_staff", "Summon Evil Eyes to curse your enemies");
		addTooltip("sculk_staff", "Summon blasts of high-power sonic energy");
		addTooltip("recovery_staff_1", "Heal yourself or targets you aim towards");
		addTooltip("recovery_staff_2", "The amount healed is based on the last amount of incoming damage you received");
		addTooltip("recovery_staff_3", "Current Healing Amount: %s");

		// Player utility
		addTooltip("bandage", "Cover those nasty cuts and scrapes");
		addTooltip("painkillers", "Feel no pain with a copious amount of pills");
		addTooltip("mre", "Tastes disgusting but at least you won't be hungry for a while");
		addTooltip("first_aid_kit", "An effective method of healing, combining the effects of bandages and painkillers");

		// Misc
		addTooltip("shift_for_info", "Hold [SHIFT] for more information");
		addTooltip("alt_for_info", "Hold [ALT] for extended information");
		addTooltip("accessory_note", "This accessory works while sitting in your inventory");
		addTooltip("accessory_inactive", "This accessory is inactive - something else is using its accessory slot");
		addTooltip("accessory_slot", "Accessory Slot: %s");
		addTooltip("creative_only", "Creative Only");
		addTooltip("barrel_tap", "Use this to convert some plants into a fluid form. Connect this to a barrel to collect it.");
		addTooltip("tesla_hoe", "Good thing you aren't in charge of the US Treasury. Because if you were we'd be broke.");
		addTooltip("spotlight", "I see you there");
		addTooltip("panic_alarm", "DON'T PANIC!!!");
		addTooltip("morphine", "I wouldn't recommend using this");
		addTooltip("used_syringe", "If you use this there's a good chance you will die of hepatitis");
		addTooltip("hans_spawn_egg", "Summon Hans the Almighty into this world");
		addTooltip("super_hans_spawn_egg", "What if Hans was even more powerful?");
		addTooltip("mortar", "Capable of sending exploding shells far into the distance");
		addTooltip("celestial_fragment", "The power of the sun in the palm of your hand");
		addTooltip("azul_locator", "Teleport to your spawn point at will");
		addTooltip("curse_cleaning_soap", "Cleanses your soul of all curses");
		addTooltip("champion_keycard", "Unlocks levels within Champion Towers");
		addTooltip("kill_counter", "Apply to an item to track the number of kills achieved with it");

		// Accessories
		addTooltip("accessory.effects", "This accessory provides the following effects:");
		addTooltip("accessory.attribute_modifier", "This accessory provides the following attribute modifiers:");
		addTooltip("accessory.dynamic_attribute_modifier", "These attributes are dynamic and always target the given value:");
		// Add tooltips for each AccessoryItem$EffectType
		addTooltip("accessory.effect_type.firearm_ammo_conservation_chance", "Firearm Ammo Conservation Chance: %s");
		addTooltip("accessory.effect_type.firearm_reload_speed", "Firearm Reload Speed: %s");
		addTooltip("accessory.effect_type.melee_damage", "Melee Damage: %s");
		addTooltip("accessory.effect_type.projectile_damage", "Projectile Damage: %s");
		addTooltip("accessory.effect_type.general_damage", "General Damage: %s");
		addTooltip("accessory.effect_type.damage_resistance", "Damage Resistance: %s");
		addTooltip("accessory.effect_type.melee_knockback", "Melee Knockback: %s");
		addTooltip("accessory.effect_type.melee_bleed_chance", "Melee Bleed Chance: %s");
		addTooltip("accessory.effect_type.melee_crit_damage_bonus", "Melee Crit Damage Bonus: %s");
		addTooltip("accessory.effect_type.melee_crit_chance", "Melee Crit Chance: %s");
		addTooltip("accessory.effect_type.bleed_cancel_chance", "Bleed Cancel Chance: %s");
		addTooltip("accessory.effect_type.bleed_resistance", "Bleed Resistance: %s");
		addTooltip("accessory.effect_type.general_wither_chance", "General Wither Chance: %s");
		addTooltip("accessory.effect_type.experience_modifier", "Experience Modifier: %s");
		addTooltip("accessory.effect_type.sonic_boom_resistance", "Sonic Boom Resistance: %s");
		addTooltip("accessory.effect_type.looting_level", "Looting Level Bonus: %s");
		// Add tooltips for each AccessoryItem$EffectScalingType (excluding NO_SCALING)
		addTooltip("accessory.effect_scaling_type.depth", "(Scales with depth)");
		addTooltip("accessory.effect_scaling_type.insomnia", "(Scales with insomnia)");

		addTooltip("satchel", "Reduces the chance of consuming ammunition in firearms");
		addTooltip("powder_horn_1", "Decreases reload time on firearms");
		addTooltip("powder_horn_2", "Also helps to protect powder in wet environments");
		addTooltip("berserkers_amulet_1", "Increases melee damage and projectile damage");
		addTooltip("berserkers_amulet_2", "However, you will take more damage from all sources");
		addTooltip("hans_blessing_1", "Take less damage from all sources");
		addTooltip("hans_blessing_2", "Tis but a scratch");
		addTooltip("celestial_spirit_1", "When taking damage, there is a 15% chance to summon a meteor");
		addTooltip("celestial_spirit_2", "The meteor will only damage the creature that attacked you");
		addTooltip("blademaster_emblem", "Increases melee damage and adds a chance to inflict Bleeding");
		addTooltip("deadeye_pendant_1", "Increases damage over distance with firearms");
		addTooltip("deadeye_pendant_2", "Maximum damage increase is 20% at 100 meters");
		addTooltip("bloated_heart", "Increases maximum health by two hearts");
		addTooltip("netherite_shield", "Provides total immunity to knockback and minor damage reduction");
		addTooltip("melee_masters_molten_glove", "Increases melee knockback significantly and melee damage");
		addTooltip("copper_ring", "Increases general damage and incoming damage reduction slightly");
		addTooltip("iron_ring", "Increases general damage and incoming damage reduction slightly");
		addTooltip("cobalt_ring", "Increases general damage and incoming damage reduction slightly");
		addTooltip("golden_ring", "Increases general damage and incoming damage reduction slightly");
		addTooltip("amethyst_ring", "Provides a looting bonus");
		addTooltip("emerald_ring", "Provides the Hero of the Village effect");
		addTooltip("diamond_ring", "Increases general damage and incoming damage reduction slightly");
		addTooltip("netherite_ring", "Increases general damage and incoming damage reduction slightly");
		addTooltip("death_gem_ring", "Inflicts Wither on hit");
		addTooltip("medal_of_adequacy", "This medal was once awarded to gamingwarrior0 for his wholly undistinguished assistance");
		addTooltip("depth_charm_1", "Provides damage resistance, a general damage bonus, and increased melee knockback");
		addTooltip("depth_charm_2", "As you descend into the earth, the effects become stronger");
		addTooltip("reinforced_depth_charm", "Provides significant protection against sonic blasts, and includes the effects of the standard Depth Charm");
		addTooltip("insomnia_amulet_1", "Slowly increases damage as you stay awake, up to a maximum of 30%");
		addTooltip("insomnia_amulet_2", "After seven days, you may begin to feel adverse effects");
		addTooltip("goggles", "Increases ranged damage slightly");
		addTooltip("lava_goggles", "Improves vision while under lava");
		addTooltip("night_vision_goggles", "Provides a night vision effect");
		addTooltip("agility_bracelet", "Increases movement speed and step height");
		addTooltip("bloody_cloth", "Reduces bleed damage significantly and provides a small chance to stop bleeding entirely");
		addTooltip("iron_fist_1", "Increases melee crit damage and provides a chance for ALL melee attacks to crit");
		addTooltip("iron_fist_2", "Devil's grip, the Iron Fist");
		addTooltip("ancient_scroll", "Increases experience gained on kill");
		addTooltip("glove_of_rapid_swinging", "Increases attack speed");
		addTooltip("hand_of_doom_1", "Woe, crusade be upon ye, enemies of me");
		addTooltip("hand_of_doom_2", "Combines the effects of the Melee Master's Molten Glove, Iron Fist, and Glove of Rapid Swinging");
		addTooltip("holy_mantle_1", "Fully negates damage for a single hit");
		addTooltip("holy_mantle_2", "Put on the whole armor of God, that ye may be able to stand against the wiles of the devil");
		addTooltip("holy_mantle_3", "This effect has a 30 second cooldown");
		addTooltip("venstral_jar", "Grants a double jump ability");
		addTooltip("super_blanket_cape_1", "Increases movement speed, jump height, slow falling, and temporary immunity to fire and lava");
		addTooltip("super_blanket_cape_2", "Imagination is a very powerful thing");

		addTooltip("bloody_sacrifice", "You feel a dark presence emanating from this item");
		addTooltip("bloody_sacrifice_charge_note", "The destruction of life charges something inside");
		addTooltip("bloody_sacrifice_charge", "Charge: %s / 100");
		addTooltip("bloody_sacrifice_fully_charged", "Using this would be a grave and permanent mistake");
		addTooltip("bloody_sacrifice_effect_1", "- +50% damage taken");
		addTooltip("bloody_sacrifice_effect_2", "- -10% damage dealt");
		addTooltip("bloody_sacrifice_effect_3", "- Permanent hunger effect");
		addTooltip("bloody_sacrifice_effect_4", "- Permanent looting bonus");
		addTooltip("bloody_sacrifice_effect_5", "- 25% chance to roll a second loot drop entirely");

		addTooltip("jonnys_curse_1", "It's like the wrath of gods, Egyptian pharaohs, Chinese monks, and Hans himself.");
		addTooltip("jonnys_curse_2", "Ping me if you want more textures, because I probably wont respond.");
		addTooltip("jonnys_curse_3", "Hrnnnnnggggg soup.");
		addTooltip("jonnys_curse_4", "I don't even think Hans would be able to withstand such pain.");
		addTooltip("jonnys_curse_5", "Imagine stubbing your toe into a lego brick that has a tack stuck through the middle.");
		addTooltip("jonnys_curse_6", "Exist in the life of a furry on 4chan!");
		addTooltip("jonnys_curse_7", "This is what happens when you nuke yourself in Garry’s mod.");
		addTooltip("jonnys_curse_8", "The true for the worthy play through.");
		addTooltip("jonnys_curse_9", "If you ever wanted to be put into a mental hospital, this is how you do it.");
		addTooltip("jonnys_curse_10", "D O N T");
		addTooltip("jonnys_curse_11", "Random crits not included.");
		addTooltip("jonnys_curse_charge_note", "Killing things makes me feel funny");
		addTooltip("jonnys_curse_charge", "Charge: %s / 100");
		addTooltip("jonnys_curse_fully_charged", "This is a really bad idea for obvious reasons");
		addTooltip("jonnys_curse_effect_1", "- +200% damage taken");
		addTooltip("jonnys_curse_effect_2", "- 50% chance not to roll loot drops");
		addTooltip("jonnys_curse_effect_3", "- -25% movement speed");
		addTooltip("jonnys_curse_effect_4", "- Permanent hunger effect");
		addTooltip("jonnys_curse_effect_5", "- Projectiles deal zero damage");

		// Lore
		addTooltip("minuteman_statue", "Something seems to activate inside when it is placed in a Battlefield. It wishes to protect.");
		addTooltip("medic_statue", "Something seems to activate inside when it is placed in a Battlefield. It wishes to heal.");
		addTooltip("azul_keystone", "It resonates with the energy of a lost world");
		addTooltip("azul_keystone_fragment", "It vibrates gently in your hand");
	}

	private void addKeys() {
		add("key.categories.immersiveweapons", "Immersive Weapons");

		addKey("toggleArmorEffect", "Toggle Armor Effect");
		addKey("armorAction", "Armor Action");
		addKey("debugTracing", "Debug Tracing");
	}

	private void addMessages() {
		addMessage("item.first_aid_kit.player", "You must be at or below half health to use this");
		addMessage("item.first_aid_kit.entity", "The target must be at or below half health to use this");
		addMessage("item.azul_locator.teleporting", "Teleporting to spawn point in %s seconds...");
		addMessage("item.azul_locator.teleported", "Teleported to spawn point!");
		addMessage("item.azul_locator.no_spawn", "Unable to teleport: No spawn point set.");
		addMessage("item.meteor_staff.not_enough_clearance", "There isn't enough clearance to summon a meteor");
		addMessage("item.curse_cleaning_soap.cleaned", "All curses have been lifted!");
		addMessage("item.curse_cleaning_soap.not_cursed", "There is nothing to cleanse...");
		addMessage("item.bloody_sacrifice.not_enough_charge", "Not enough blood has been spilled yet...");
		addMessage("item.bloody_sacrifice.using", "This will have permanent consequences...");
		addMessage("item.bloody_sacrifice.canceled", "The curse remains sealed...");
		addMessage("item.bloody_sacrifice.used", "There is no going back now...");
		addMessage("item.jonnys_curse.not_enough_charge", "Nah, go kill more things...");
		addMessage("item.jonnys_curse.using", "Are you sure you wanna become a furry?");
		addMessage("item.jonnys_curse.canceled", "You remain normal for now...");
		addMessage("item.jonnys_curse.used", "Its so over...");
		addMessage("block.wall_shelf.locked", "This shelf has been locked");
		addMessage("block.wall_shelf.unlocked", "This shelf has been unlocked");
		addMessage("armor_effects.disabled", "Armor effects have been disabled");
		addMessage("armor_effects.enabled", "Armor effects have been enabled");
		addMessage("armor_effects.tesla_armor.effect_everything", "Currently effecting ALL creatures (including players)");
		addMessage("armor_effects.tesla_armor.effect_mobs", "Currently effecting only mobs");
		addMessage("entity.super_hans.tower_minibosses_alive", "Super Hans is invulnerable, you must clear the tower bosses first!");
	}

	private void addDeathMessages() {
		addDeathMessage("punji_sticks", "%1$s was victim to Vietnam-era war tactics");
		addDeathMessage("punji_sticks.player", "%1$s was victim to Vietnam-era war tactics by %2$s");
		addDeathMessage("punji_sticks.item", "%1$s was victim to Vietnam-era war tactics by %2$s using %3$s");
		addDeathMessage("bear_trap", "%1$s bled out in a bear trap");
		addDeathMessage("bear_trap.player", "%1$s bled out in a bear trap while %2$s watched");
		addDeathMessage("bear_trap.item", "%1$s bled out in a bear trap while %2$s watched");
		addDeathMessage("barbed_wire", "%1$s thought it was a bright idea to walk into barbed wire");
		addDeathMessage("barbed_wire.player", "%1$s was forced into barbed wire by %2$s");
		addDeathMessage("barbed_wire.item", "%1$s was forced into barbed wire by %2$s using %3$s");
		addDeathMessage("landmine", "%1$s stepped on a landmine");
		addDeathMessage("landmine.player", "%1$s stepped on a landmine because %2$s pushed them");
		addDeathMessage("landmine.item", "%1$s stepped on a landmine because %2$s pushed them using %3$s");
		addDeathMessage("explosive_chocolate_bar", "%1$s learned to not accept candy from strangers");
		addDeathMessage("explosive_chocolate_bar.player", "%1$s learned to not accept candy from strangers while %2$s watched");
		addDeathMessage("explosive_chocolate_bar.item", "%1$s learned to not accept candy from strangers while %2$s watched");
		addDeathMessage("spike_trap", "%1$s was impaled");
		addDeathMessage("spike_trap.player", "%1$s was impaled by %2$s");
		addDeathMessage("spike_trap.item", "%1$s was impaled by %2$s using %3$s");
		addDeathMessage("wooden_spikes", "%1$s was impaled");
		addDeathMessage("wooden_spikes.player", "%1$s was impaled by %2$s");
		addDeathMessage("wooden_spikes.item", "%1$s was impaled by %2$s using %3$s");
		addDeathMessage("used_syringe", "%1$s died of hepatitis");
		addDeathMessage("used_syringe.player", "%1$s died of hepatitis at the hands of %2$s");
		addDeathMessage("used_syringe.item", "%1$s died of hepatitis at the hands of %2$s using %3$s");
		addDeathMessage("mortar", "%1$s was the target of an artillery attack");
		addDeathMessage("mortar.item", "%1$s was the target of an artillery attack");
		addDeathMessage("bleeding", "%1$s bled to death");
		addDeathMessage("bleeding.player", "%1$s bled to death at the hands of %2$s");
		addDeathMessage("deadmans_desert_atmosphere", "%1$s was poisoned by the atmosphere");
		addDeathMessage("deadmans_desert_atmosphere.player", "%1$s was poisoned by the atmosphere at the hands of %2$s");
		addDeathMessage("deadmans_desert_atmosphere.item", "%1$s was poisoned by the atmosphere at the hands of %2$s using %3$s");
		addDeathMessage("deathweed", "%1$s was poisoned by deathweed");
		addDeathMessage("deathweed.player", "%1$s was poisoned by deathweed at the hands of %2$s");
		addDeathMessage("deathweed.item", "%1$s was poisoned by deathweed at the hands of %2$s using %3$s");
		addDeathMessage("meteor", "%1$s was hit by a meteor summoned by %2$s");
		addDeathMessage("meteor.item", "%1$s was hit by a meteor summoned by %2$s using %3$s");
		addDeathMessage("bullet", "%1$s was shot by %2$s");
		addDeathMessage("bullet.item", "%1$s was shot by %2$s using %3$s");
		addDeathMessage("cannonball", "%1$s was hit by a cannonball fired by %2$s");
		addDeathMessage("cannonball.item", "%1$s was hit by a cannonball fired by %2$s using %3$s");
		addDeathMessage("explosive_cannonball", "%1$s was hit by an explosive cannonball fired by %2$s");
		addDeathMessage("explosive_cannonball.item", "%1$s was hit by an explosive cannonball fired by %2$s using %3$s");
		addDeathMessage("explosive_arrow", "%1$s was blown up by explosive arrow fired by %2$s");
		addDeathMessage("explosive_arrow.item", "%1$s was blown up by an explosive arrow fired by %2$s using %3$s");
	}

	private void addBiomes() {
		addBiome("battlefield", "Battlefield");
		addBiome("tiltros_wastes", "Tiltros Wastes");
		addBiome("starlight_plains", "Starlight Plains");
		addBiome("deadmans_desert", "Deadman's Desert");
	}

	private void addAdvancements() {
		// Root
		addAdvancement("root.title", "Immersive Weapons");
		addAdvancement("root.description", "Spice up your combat skills");

		// Molten advancements
		addAdvancement("molten_shard.title", "A Hot Metal");
		addAdvancement("molten_shard.description", "Obtain Molten shards");
		addAdvancement("molten_ingot.title", "Lava Forge");
		addAdvancement("molten_ingot.description", "Smelt a Molten ingot");
		addAdvancement("molten_sword.title", "Kill It With Fire");
		addAdvancement("molten_sword.description", "Craft a Molten sword");
		addAdvancement("molten_pickaxe.title", "Molten Tools: Pickaxe");
		addAdvancement("molten_pickaxe.description", "Craft a Molten pickaxe");
		addAdvancement("molten_axe.title", "Molten Tools: Axe");
		addAdvancement("molten_axe.description", "Craft a Molten axe");
		addAdvancement("molten_shovel.title", "Molten Tools: Shovel");
		addAdvancement("molten_shovel.description", "Craft a Molten shovel");
		addAdvancement("molten_hoe.title", "Molten Tools: Hoe");
		addAdvancement("molten_hoe.description", "Craft a Molten hoe");
		addAdvancement("molten_tools.title", "Play With Fire");
		addAdvancement("molten_tools.description", "Obtain every Molten tool");
		addAdvancement("molten_armor.title", "Warm And Toasty");
		addAdvancement("molten_armor.description", "Wear a full set of Molten armor");
		addAdvancement("swim_in_lava.title", "Lava Bath");
		addAdvancement("swim_in_lava.description", "Swim in lava with a full set of Molten armor");

		// Electric/Tesla advancements
		addAdvancement("conductive_alloy.title", "Electrician");
		addAdvancement("conductive_alloy.description", "Craft a conductive alloy");
		addAdvancement("electric_ingot.title", "Electrician: Part 2");
		addAdvancement("electric_ingot.description", "Obtain an electric ingot, a rare and powerful energy source");
		addAdvancement("tesla_ingot.title", "Electrician: The Final Saga");
		addAdvancement("tesla_ingot.description", "Obtain a Tesla ingot, the answer to your mass energy requirements");
		addAdvancement("tesla_sword.title", "A Shocking Experience");
		addAdvancement("tesla_sword.description", "Obtain a Tesla sword");
		addAdvancement("tesla_pickaxe.title", "Tesla Tools: Pickaxe");
		addAdvancement("tesla_pickaxe.description", "Obtain a Tesla pickaxe");
		addAdvancement("tesla_axe.title", "Tesla Tools: Axe");
		addAdvancement("tesla_axe.description", "Obtain a Tesla axe");
		addAdvancement("tesla_shovel.title", "Tesla Tools: Shovel");
		addAdvancement("tesla_shovel.description", "Obtain a Tesla shovel");
		addAdvancement("tesla_hoe.title", "Tesla Tools: Hoe");
		addAdvancement("tesla_hoe.description", "Destroy your wealth by obtaining a Tesla hoe");
		addAdvancement("tesla_tools.title", "Energized");
		addAdvancement("tesla_tools.description", "Obtain a full set of Tesla tools");
		addAdvancement("tesla_armor.title", "Maybe I Am A Tesla Coil");
		addAdvancement("tesla_armor.description", "Wear a full set of Tesla armor");
		addAdvancement("tesla_synthesizer.title", "I'm A Genius Too");
		addAdvancement("tesla_synthesizer.description", "Craft a Tesla synthesizer");

		// Ventus advancements
		addAdvancement("ventus_shard.title", "Cool Shards");
		addAdvancement("ventus_shard.description", "Obtain Ventus shards");
		addAdvancement("ventus_sword.title", "Whoosh");
		addAdvancement("ventus_sword.description", "Craft a Ventus sword");
		addAdvancement("ventus_pickaxe.title", "Ventus Tools: Pickaxe");
		addAdvancement("ventus_pickaxe.description", "Obtain a Ventus pickaxe");
		addAdvancement("ventus_axe.title", "Ventus Tools: Axe");
		addAdvancement("ventus_axe.description", "Obtain a Ventus axe");
		addAdvancement("ventus_shovel.title", "Ventus Tools: Shovel");
		addAdvancement("ventus_shovel.description", "Obtain a Ventus shovel");
		addAdvancement("ventus_hoe.title", "Ventus Tools: Hoe");
		addAdvancement("ventus_hoe.description", "Obtain a Ventus hoe");
		addAdvancement("ventus_tools.title", "Pretty Windy");
		addAdvancement("ventus_tools.description", "Obtain a full set of Ventus tools");
		addAdvancement("ventus_armor.title", "Almost Flying");
		addAdvancement("ventus_armor.description", "Wear a full set of Ventus armor");
		addAdvancement("ventus_staff_core.title", "Wind Energy");
		addAdvancement("ventus_staff_core.description", "Craft a Ventus staff core");
		addAdvancement("ventus_staff.title", "Gone With The Wind");
		addAdvancement("ventus_staff.description", "Craft a Ventus staff");

		// Astral advancements
		addAdvancement("astral_crystal.title", "Pretty Crystal");
		addAdvancement("astral_crystal.description", "Obtain an Astral crystal");
		addAdvancement("astral_ingot.title", "Celestial Infusion");
		addAdvancement("astral_ingot.description", "Obtain an Astral ingot");
		addAdvancement("astral_sword.title", "Fast Hands");
		addAdvancement("astral_sword.description", "Craft an Astral sword");
		addAdvancement("astral_pickaxe.title", "Astral Tools: Pickaxe");
		addAdvancement("astral_pickaxe.description", "Craft an Astral pickaxe");
		addAdvancement("astral_axe.title", "Astral Tools: Axe");
		addAdvancement("astral_axe.description", "Craft an Astral axe");
		addAdvancement("astral_shovel.title", "Astral Tools: Shovel");
		addAdvancement("astral_shovel.description", "Craft an Astral shovel");
		addAdvancement("astral_hoe.title", "Astral Tools: Hoe");
		addAdvancement("astral_hoe.description", "Craft an Astral hoe");
		addAdvancement("astral_tools.title", "Maximum Efficiency");
		addAdvancement("astral_tools.description", "Obtain every Astral tool");
		addAdvancement("astral_armor.title", "Celestial Protection");
		addAdvancement("astral_armor.description", "Wear a full set of Astral armor");

		// Starstorm advancements
		addAdvancement("starstorm_crystal.title", "Shiny Crystal");
		addAdvancement("starstorm_crystal.description", "Obtain a Starstorm crystal");
		addAdvancement("starstorm_ingot.title", "Crystal Crushing");
		addAdvancement("starstorm_ingot.description", "Obtain a Starstorm ingot");
		addAdvancement("starstorm_sword.title", "The Wrath of the Stars");
		addAdvancement("starstorm_sword.description", "Craft a Starstorm sword");
		addAdvancement("starstorm_pickaxe.title", "Starstorm Tools: Pickaxe");
		addAdvancement("starstorm_pickaxe.description", "Craft a Starstorm pickaxe");
		addAdvancement("starstorm_axe.title", "Starstorm Tools: Axe");
		addAdvancement("starstorm_axe.description", "Craft a Starstorm axe");
		addAdvancement("starstorm_shovel.title", "Starstorm Tools: Shovel");
		addAdvancement("starstorm_shovel.description", "Craft a Starstorm shovel");
		addAdvancement("starstorm_hoe.title", "Starstorm Tools: Hoe");
		addAdvancement("starstorm_hoe.description", "Craft a Starstorm hoe");
		addAdvancement("starstorm_tools.title", "Raw Power");
		addAdvancement("starstorm_tools.description", "Obtain every Starstorm tool");
		addAdvancement("starstorm_armor.title", "Celestial Wrath");
		addAdvancement("starstorm_armor.description", "Wear a full set of Starstorm armor");

		// Copper advancements
		addAdvancement("copper_ingot.title", "Simple Metals: Copper");
		addAdvancement("copper_ingot.description", "Obtain a copper ingot");
		addAdvancement("copper_sword.title", "Copper Tools: Sword");
		addAdvancement("copper_sword.description", "Obtain a copper sword");
		addAdvancement("copper_pickaxe.title", "Copper Tools: Pickaxe");
		addAdvancement("copper_pickaxe.description", "Obtain a copper pickaxe");
		addAdvancement("copper_axe.title", "Copper Tools: Axe");
		addAdvancement("copper_axe.description", "Obtain a copper Axe");
		addAdvancement("copper_shovel.title", "Copper Tools: Shovel");
		addAdvancement("copper_shovel.description", "Obtain a copper shovel");
		addAdvancement("copper_hoe.title", "Copper Tools: Hoe");
		addAdvancement("copper_hoe.description", "Obtain a copper Hoe");
		addAdvancement("copper_tools.title", "Close Enough to Iron Tools");
		addAdvancement("copper_tools.description", "Obtain a full set of copper tools");

		// Cobalt advancements
		addAdvancement("cobalt_ingot.title", "Simple Metals: Cobalt");
		addAdvancement("cobalt_ingot.description", "Obtain a cobalt ingot");
		addAdvancement("cobalt_sword.title", "Cobalt Tools: Sword");
		addAdvancement("cobalt_sword.description", "Obtain a cobalt sword");
		addAdvancement("cobalt_pickaxe.title", "Cobalt Tools: Pickaxe");
		addAdvancement("cobalt_pickaxe.description", "Obtain a cobalt pickaxe");
		addAdvancement("cobalt_axe.title", "Cobalt Tools: Axe");
		addAdvancement("cobalt_axe.description", "Obtain a cobalt Axe");
		addAdvancement("cobalt_shovel.title", "Cobalt Tools: Shovel");
		addAdvancement("cobalt_shovel.description", "Obtain a cobalt shovel");
		addAdvancement("cobalt_hoe.title", "Cobalt Tools: Hoe");
		addAdvancement("cobalt_hoe.description", "Obtain a cobalt Hoe");
		addAdvancement("cobalt_tools.title", "Who Needs Iron Tools");
		addAdvancement("cobalt_tools.description", "Obtain a full set of cobalt tools");

		// Padded Leather advancements
		addAdvancement("padded_leather_armor.title", "Total Silence");
		addAdvancement("padded_leather_armor.description", "Wear a full set of padded leather armor");

		// Guns
		addAdvancement("flintlock_pistol.title", "Firepower");
		addAdvancement("flintlock_pistol.description", "Craft a flintlock pistol");
		addAdvancement("blunderbuss.title", "More Firepower");
		addAdvancement("blunderbuss.description", "Craft a blunderbuss");
		addAdvancement("flare_gun.title", "Light Up the Sky");
		addAdvancement("flare_gun.description", "Craft a flare gun");
		addAdvancement("musket.title", "Snipin's a good job!");
		addAdvancement("musket.description", "Craft a musket");
		addAdvancement("hand_cannon.title", "Big Boom");
		addAdvancement("hand_cannon.description", "Craft a hand cannon");

		// Other weapons
		addAdvancement("pike.title", "Long Distance Stabbing");
		addAdvancement("pike.description", "Craft any pike");
		addAdvancement("gauntlet.title", "Rule With An Iron Fist");
		addAdvancement("gauntlet.description", "Craft any gauntlet");
		addAdvancement("meteor_staff.title", "Meteor Shower");
		addAdvancement("meteor_staff.description", "Craft a meteor staff");
		addAdvancement("cursed_sight_staff.title", "Cursed Sight");
		addAdvancement("cursed_sight_staff.description", "Craft a cursed sight staff");
		addAdvancement("sculk_staff.title", "Sonic Blast");
		addAdvancement("sculk_staff.description", "Craft a sculk staff");

		// Projectiles
		addAdvancement("musket_ball.title", "Pow!");
		addAdvancement("musket_ball.description", "Obtain any musket ball");
		addAdvancement("netherite_projectile.title", "Throwing Away Money");
		addAdvancement("netherite_projectile.description", "Obtain a netherite arrow and musket ball");

		// Throwables
		addAdvancement("smoke_grenade.title", "Sight Privileges Revoked");
		addAdvancement("smoke_grenade.description", "Craft a smoke grenade");
		addAdvancement("molotov_cocktail.title", "Quick-Use Arson");
		addAdvancement("molotov_cocktail.description", "Craft a molotov cocktail");

		// Crafting materials
		addAdvancement("tool_rod.title", "Strong Stick");
		addAdvancement("tool_rod.description", "Craft a wooden tool rod");
		addAdvancement("shards.title", "Sharp Shrapnel");
		addAdvancement("shards.description", "Obtain any shard");
		addAdvancement("wooden_shard.title", "Shards: Wood");
		addAdvancement("wooden_shard.description", "Obtain a wooden shard");
		addAdvancement("stone_shard.title", "Shards: Stone");
		addAdvancement("stone_shard.description", "Obtain a stone shard");
		addAdvancement("diamond_shard.title", "Shards: Diamond");
		addAdvancement("diamond_shard.description", "Obtain a diamond shard");
		addAdvancement("obsidian_shard.title", "Shards: Obsidian");
		addAdvancement("obsidian_shard.description", "Obtain an obsidian shard");
		addAdvancement("gold_ingot.title", "Simple Metals: Gold");
		addAdvancement("gold_ingot.description", "Obtain a gold ingot");
		addAdvancement("netherite_ingot.title", "Diamonds are for Peasants");
		addAdvancement("netherite_ingot.description", "Obtain a netherite ingot");
		addAdvancement("ingots.title", "Ingots!");
		addAdvancement("ingots.description", "Obtain any ingot");
		addAdvancement("nuggets.title", "Nuggets!");
		addAdvancement("nuggets.description", "Obtain any nugget");
		addAdvancement("planks.title", "Planks!");
		addAdvancement("planks.description", "Obtain any plank");
		addAdvancement("bamboo.title", "Thick Grass");
		addAdvancement("bamboo.description", "Obtain bamboo");
		addAdvancement("warden_heart.title", "Heart of Darkness");
		addAdvancement("warden_heart.description", "Obtain a Warden Heart");

		// Super Hans
		addAdvancement("hansium_ingot.title", "Raw Strength in an Ingot");
		addAdvancement("hansium_ingot.description", "Obtain a Hansium Ingot, dropped by the mighty Super Hans");
		addAdvancement("the_sword.title", "The Sword of All Time");
		addAdvancement("the_sword.description", "Obtain The Sword, a powerful combination of several swords");

		// Player utility
		addAdvancement("bottle_of_alcohol.title", "mmmmm alacol");
		addAdvancement("bottle_of_alcohol.description", "obTan A bOTle o AlacOl an dIenk yUor piaN aWae");
		addAdvancement("bandage.title", "Poor Man's Healing Potion");
		addAdvancement("bandage.description", "Craft a bandage");
		addAdvancement("first_aid_kit.title", "Better Band-Aids");
		addAdvancement("first_aid_kit.description", "Craft a first aid kit");
		addAdvancement("used_syringe.title", "Died of Hepatitis");
		addAdvancement("used_syringe.description", "Die to a used syringe");

		// Tiltros
		addAdvancement("tiltros.tiltros_portal.title", "A Portal to a Forgotten Land");
		addAdvancement("tiltros.tiltros_portal.description", "Craft a Tiltros Portal Frame");
		addAdvancement("tiltros.azul_keystone.title", "Dimensional Keystone");
		addAdvancement("tiltros.azul_keystone.description", "Craft an Azul Keystone from fragments scattered across the world");
		addAdvancement("tiltros.celestial_lantern.title", "Magical Lantern");
		addAdvancement("tiltros.celestial_lantern.description", "Obtain a celestial lantern");
		addAdvancement("tiltros.biome.title", "Welcome to Tiltros");
		addAdvancement("tiltros.biome.description", "A ravaged landscape lost in space");

		// Other
		addAdvancement("battlefield.title", "A Destroyed Landscape");
		addAdvancement("battlefield.description", "Visit a battlefield");
		addAdvancement("minuteman_statue.title", "More Minutemen");
		addAdvancement("minuteman_statue.description", "Obtain a Minuteman Statue, a summoning device within battlefields.");
		addAdvancement("medic_statue.title", "More Medics");
		addAdvancement("medic_statue.description", "Obtain a Medic Statue, a summoning device within battlefields.");
		addAdvancement("small_parts_table.title", "The Engineer");
		addAdvancement("small_parts_table.description", "Obtain a small parts table, a gateway for creating advanced weapons.");
		addAdvancement("ammunition_table.title", "Ammo Manufacturer");
		addAdvancement("ammunition_table.description", "Obtain an ammunition table, used to create musket balls.");
		addAdvancement("star_forge.title", "Star Forge");
		addAdvancement("star_forge.description", "Obtain parts for a Star Forge, used to create high-level tools and armor.");
		addAdvancement("traps.title", "Advanced Warfare");
		addAdvancement("traps.description", "Obtain any trap");
		addAdvancement("mud.title", "Mud!");
		addAdvancement("mud.description", "Obtain a mud block");
		addAdvancement("cloud_marble.title", "Looks Better than Diorite");
		addAdvancement("cloud_marble.description", "Obtain a block of cloud marble");
		addAdvancement("biohazard_box.title", "Hazardous?");
		addAdvancement("biohazard_box.description", "Obtain a (harmless) biohazard box from a medic station");
		addAdvancement("cloud.title", "High in the Sky");
		addAdvancement("cloud.description", "Walk across cloud blocks high in the air");
		addAdvancement("overkill.title", "Overkill");
		addAdvancement("overkill.description", "Deal 175 damage in a single hit");
		addAdvancement("firearm_long_range.title", "Long Range");
		addAdvancement("firearm_long_range.description", "Send a musket ball flying over 200 blocks");
		addAdvancement("kill_counter.title", "Kill Counter");
		addAdvancement("kill_counter.description", "Obtain a Kill Counter");
		addAdvancement("mud_ball.title", "Dirty Snowball");
		addAdvancement("mud_ball.description", "Throw a mud ball at an entity");

		// Accessories
		addAdvancement("accessories.title", "Accessories!");
		addAdvancement("accessories.description", "Obtain any accessory");
		addAdvancement("bloody_sacrifice.title", "For the Worthy");
		addAdvancement("bloody_sacrifice.description", "Only the bravest souls may take this path");
		addAdvancement("reinforced_depth_charm.title", "Sonic Boom Protection");
		addAdvancement("reinforced_depth_charm.description", "Obtain a Reinforced Depth Charm");

		// Entity discovery
		addAdvancement("entity_discovery.title", "Entity Discovery");
		addAdvancement("entity_discovery.description", "Encounter every IW entity");
		addAdvancement("discover_minuteman.title", "Ranged Defender");
		addAdvancement("discover_minuteman.description", "Discover a Minuteman, a defender ready to protect at a minute's notice");
		addAdvancement("discover_field_medic.title", "Meet the Medic");
		addAdvancement("discover_field_medic.description", "Discover a Field Medic, a healer that keeps nearby minutemen in good health");
		addAdvancement("discover_dying_soldier.title", "Barely Alive");
		addAdvancement("discover_dying_soldier.description", "Discover a Dying Soldier, a decaying war-torn soldier still fighting the war");
		addAdvancement("discover_wandering_warrior.title", "Rogue Warrior");
		addAdvancement("discover_wandering_warrior.description", "Discover a Wandering Warrior, a creature of the wilderness");
		addAdvancement("discover_hans.title", "O GREAT HANS");
		addAdvancement("discover_hans.description", "WITNESS THE POWER OF THE GREAT HANS, BOW BEFORE HIS GREATNESS");
		addAdvancement("discover_super_hans.title", "O GREATER HANS");
		addAdvancement("discover_super_hans.description", "The mighty being at the top of a great Champion Tower");
		addAdvancement("discover_lava_revenant.title", "Phantom of Tiltros");
		addAdvancement("discover_lava_revenant.description", "Discover a Lava Revenant, a phantom reborn out of the lava pits of Tiltros");
		addAdvancement("discover_rock_spider.title", "Spiders... But Smaller");
		addAdvancement("discover_rock_spider.description", "Discover a Rock Spider, a stone-born spider that's small but just as potent");
		addAdvancement("discover_celestial_tower.title", "Otherworldly Presence");
		addAdvancement("discover_celestial_tower.description", "Discover a Celestial Tower, a strange otherworldly device capable of summoning minions to fight");
		addAdvancement("discover_starmite.title", "Crystal Bug");
		addAdvancement("discover_starmite.description", "Discover a Starmite, a pest that lives within Starstorm Crystals");
		addAdvancement("discover_storm_creeper.title", "They Can Be Worse");
		addAdvancement("discover_storm_creeper.description", "Discover a Storm Creeper, an even more aggressive creeper which packs a bigger boom");
		addAdvancement("discover_evil_eye.title", "Demonic Presence");
		addAdvancement("discover_evil_eye.description", "Discover an Evil Eye, a detached eye which wanders the Deadman's Desert");
		addAdvancement("discover_star_wolf.title", "Bigger Dogs");
		addAdvancement("discover_star_wolf.description", "Discover a Star Wolf, a stronger and larger wolf");
		addAdvancement("discover_skygazer.title", "Finally, Some Decent Trades");
		addAdvancement("discover_skygazer.description", "Discover a Skygazer, a trader which resides in the Starlight Plains");
		addAdvancement("discover_skeleton_merchant.title", "Cavern Trader");
		addAdvancement("discover_skeleton_merchant.description", "Discover a Skeleton Merchant, a trader which resides in lush caves");
	}

	private void addConfigDescriptions() {
		// Client options

		addConfigDescription("tesla_armor_effect_sound", "Enable the Tesla Armor effect sound - Default true");
		addConfigDescription("panic_alarm_range", "Set the range of the Panic Alarm's sound - Default 48");
		addConfigDescription("smoke_grenade_particles", "Set the number of particles produced by the smoke grenade" +
				"\nThe server may choose to override this value to encourage fairness. - Default 96");
		addConfigDescription("fancy_smoke_grenade_particles", "Render smoke grenade particles at 66% of the regular size, spawn 3x more, and add translucency." +
				"\nThis will negatively impact performance, but make smoke grenades appear more realistic. - Default false");

		// Server options

		// General
		addConfigDescription("force_smoke_grenade_particles", """
				Force the number of particles produced by the smoke grenade to be the same on all clients.
				A value of -1 will not force any value, and will allow clients to use their own values.
				Setting this to a high value may cause clients to lag. - Default -1""");
		addConfigDescription("bullets_break_glass", "Enable bullets breaking glass - Default true");
		addConfigDescription("tiltros_enabled", "Enable the Tiltros dimension portal - Default true");

		// Mixins
		addConfigDescription("max_armor_protection", """
				Set the maximum armor protection cap. The vanilla default is 20. Setting this value higher
				allows higher tiers of armor to work properly. - Default 50.0
				""");

		// Entity

		// General
		addConfigDescription("discovery_advancement_range", """
				Set the range for checking criteria of the discovery advancement (value is squared) - Default 50
				Lowering this value may improve server performance,
				""");

		// Celestial Tower
		addConfigDescription("celestial_tower_spawn_check_radius", "Set the spawn checking radius for the Celestial Tower." +
				"\nSetting this higher may slightly negatively impact server ticks in Tiltros, but make Celestial Lanterns more effective - Default 128");
		addConfigDescription("celestial_tower_minions_wave_size_modifier", """
				Multiplier to change the wave size from Celestial Tower summons.
				Set less than 1 to reduce, greater than 1 to increase.
				Increasing the wave size will negatively affect the server ticks in Tiltros. - Default 1.0""");

		// Weapons
		// General
		addConfigDescription("gun_crit_chance", "Set the chance for a fired bullet to be critical - Default 0.1");
		// Flintlock Pistol
		addConfigDescription("flintlock_pistol_fire_velocity", "Set the velocity of bullets fired by the Flintlock Pistol - Default 2.5");
		addConfigDescription("flintlock_pistol_fire_inaccuracy", "Set the inaccuracy of bullets fired by the Flintlock Pistol - Default 1.75");
		// Blunderbuss
		addConfigDescription("blunderbuss_fire_velocity", "Set the velocity of bullets fired by the Blunderbuss - Default 1.7");
		addConfigDescription("blunderbuss_fire_inaccuracy", "Set the inaccuracy of bullets fired by the Blunderbuss - Default 2.0");
		// Musket
		addConfigDescription("musket_fire_velocity", "Set the velocity of bullets fired by the Musket - Default 4.0");
		addConfigDescription("musket_fire_inaccuracy", "Set the inaccuracy of bullets fired by the Musket - Default 0.15");

		// Meteor Staff
		addConfigDescription("meteor_staff_max_use_range", "Set the maximum range in blocks of the Meteor Staff - Default 100");
		addConfigDescription("meteor_staff_explosion_radius", "Set the radius of the explosion created by the Meteor Staff - Default 3.0");
		addConfigDescription("meteor_staff_explosion_break_blocks", "Set whether the Meteor Staff explosion breaks blocks - Default false");

		// Cursed Sight Staff
		addConfigDescription("cursed_sight_staff_max_use_range", "Set the maximum range in blocks of the Cursed Sight Staff - Default 50");

		// Sculk Staff
		addConfigDescription("sculk_staff_max_use_range", "Set the maximum range in blocks of the Sculk Staff - Default 25");
		addConfigDescription("sculk_staff_sonic_blast_through_walls", "Set whether the Sculk Staff sonic blast goes through walls - Default true");
	}

	private void addEnchantments() {
		addEnchantment(EnchantmentRegistry.FIREPOWER, "Firepower");
		addEnchantment(EnchantmentRegistry.IMPACT, "Impact");
		addEnchantment(EnchantmentRegistry.ENDLESS_MUSKET_POUCH, "Endless Musket Pouch");
		addEnchantment(EnchantmentRegistry.SCORCH_SHOT, "Scorch Shot");
		addEnchantment(EnchantmentRegistry.RAPID_FIRE, "Rapid Fire");
		addEnchantment(EnchantmentRegistry.VELOCITY, "Velocity");
		addEnchantment(EnchantmentRegistry.EXTENDED_REACH, "Extended Reach");
		addEnchantment(EnchantmentRegistry.SHARPENED_HEAD, "Sharpened Head");
		addEnchantment(EnchantmentRegistry.CRIMSON_CLAW, "Crimson Claw");
		addEnchantment(EnchantmentRegistry.EXCESSIVE_FORCE, "Excessive Force");
		addEnchantment(EnchantmentRegistry.REGENERATIVE_ASSAULT, "Regenerative Assault");
		addEnchantment(EnchantmentRegistry.HEAVY_COMET, "Heavy Comet");
		addEnchantment(EnchantmentRegistry.BURNING_HEAT, "Burning Heat");
		addEnchantment(EnchantmentRegistry.CELESTIAL_FURY, "Celestial Fury");
		addEnchantment(EnchantmentRegistry.NIGHTMARISH_STARE, "Nightmarish Stare");
		addEnchantment(EnchantmentRegistry.MALEVOLENT_GAZE, "Malevolent Gaze");
	}

	private void addNetworkingFailures() {
		addNetworkingFailure("generic", "A networking error occurred regarding Immersive Weapons: %s");
	}

	private void addMisc() {
		add("loot.immersiveweapons.chest.village.battlefield.medic_station.iron_axe", "The Amputator");
		add("loot.immersiveweapons.entity.hans.iron_sword", "The Grand Blade of Hans");
		add("loot.immersiveweapons.entity.super_hans.super_healing_potion", "Super Potion of Healing");
		add("loot.immersiveweapons.entity.super_hans.super_regeneration_potion", "Super Potion of Regeneration");
		add("immersiveweapons.boss.celestial_tower.waves", "Wave %s of %s");
		add("itemGroup.immersiveweapons.creative_tab", "Immersive Weapons");

		// Vanilla only has enchantment numbers up to 10, so add up to 100 for QoL if the player has high level enchants
		for (int i = 11; i <= 100; i++) {
			// Convert the number to a roman numeral
			add("enchantment.level." + i, GeneralUtilities.convertToRoman(i));
		}

		// Debug tracing strings
		add("immersiveweapons.debug_tracing.melee_item_damage", "Melee Item Damage: %s");
		add("immersiveweapons.debug_tracing.gun_base_velocity", "Gun Base Velocity: %s");
		add("immersiveweapons.debug_tracing.live_bullet_damage", "Live Bullet Damage: %s (is crit: %s)");
		add("immersiveweapons.debug_tracing.damage_bonus", "Damage Bonus: %s general, %s melee, %s projectile");
		add("immersiveweapons.debug_tracing.celestial_protection_chance_for_no_damage", "Celestial Protection Chance: %s");
		add("immersiveweapons.debug_tracing.last_damage_values", "Last Damage Dealt: %s, Last Damage Taken: %s");
		add("immersiveweapons.debug_tracing.armor_values", "Total Armor Value: %s, Armor Toughness: %s");
		add("immersiveweapons.debug_tracing.dr_and_kbr", "General Damage Resistance: %s, Knockback Resistance: %s");

		// Kill count weapon strings
		add("immersiveweapons.kill_count_weapon.total_kills", "Total Kills: %s");
		add("immersiveweapons.kill_count_weapon.tier.special", "Special");
		add("immersiveweapons.kill_count_weapon.tier.novice", "Novice");
		add("immersiveweapons.kill_count_weapon.tier.apprentice", "Apprentice");
		add("immersiveweapons.kill_count_weapon.tier.journeyman", "Journeyman");
		add("immersiveweapons.kill_count_weapon.tier.expert", "Expert");
		add("immersiveweapons.kill_count_weapon.tier.master", "Master");
		add("immersiveweapons.kill_count_weapon.tier.legendary", "Legendary");
		add("immersiveweapons.kill_count_weapon.tier.hans_worthy", "Hans-Worthy");

		// IWCB strings
		add("tooltip.iwcompatbridge.accessory_note", "Equip this in a Curios slot to gain the effect.");
	}

	/**
	 * Capitalizes the first letter of each word in a string.
	 *
	 * @param str the string to capitalize
	 * @return the capitalized string
	 */
	private String capitalizeWords(String str) {
		String[] words = str.split(" ");
		StringBuilder capitalizedString = new StringBuilder(25);
		for (String word : words) {
			String firstLetter = word.substring(0, 1);
			String restOfWord = word.substring(1);
			capitalizedString.append(firstLetter.toUpperCase()).append(restOfWord).append(" ");
		}
		return capitalizedString.toString().trim();
	}
}