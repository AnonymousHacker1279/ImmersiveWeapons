package tech.anonymoushacker1279.immersiveweapons.data.lists;

import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class ItemLists {

	public static final List<Item> MODEL_GENERATOR_IGNORED_ITEMS = new ArrayList<>(75);
	public static final List<Item> HEAD_ITEMS = new ArrayList<>(15);
	public static final List<Item> SMOKE_GRENADE_ITEMS = new ArrayList<>(15);
	public static final List<Item> SMOKE_GRENADE_ARROW_ITEMS = new ArrayList<>(15);
	public static final List<Item> ARROW_ITEMS = new ArrayList<>(30);
	public static final List<Item> MUSKET_BALL_ITEMS = new ArrayList<>(15);

	static {
		// Items
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.BLUNDERBUSS.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.FLINTLOCK_PISTOL.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.MUSKET.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.MUSKET_SCOPE.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.FLARE_GUN.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.FLARE.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.HAND_CANNON.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.CANNONBALL.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.EXPLOSIVE_CANNONBALL.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.MORTAR_SHELL.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.MOLOTOV_COCKTAIL.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.SMOKE_GRENADE.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.SMOKE_GRENADE_BLUE.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.SMOKE_GRENADE_RED.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.SMOKE_GRENADE_GREEN.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.SMOKE_GRENADE_PURPLE.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.SMOKE_GRENADE_YELLOW.get());
		MODEL_GENERATOR_IGNORED_ITEMS.add(ItemRegistry.EXPLOSIVE_CHOCOLATE_BAR.get());

		HEAD_ITEMS.add(BlockItemRegistry.MINUTEMAN_HEAD_ITEM.get());
		HEAD_ITEMS.add(BlockItemRegistry.FIELD_MEDIC_HEAD_ITEM.get());
		HEAD_ITEMS.add(BlockItemRegistry.DYING_SOLDIER_HEAD_ITEM.get());
		HEAD_ITEMS.add(BlockItemRegistry.WANDERING_WARRIOR_HEAD_ITEM.get());
		HEAD_ITEMS.add(BlockItemRegistry.HANS_HEAD_ITEM.get());
		HEAD_ITEMS.add(BlockItemRegistry.STORM_CREEPER_HEAD_ITEM.get());
		HEAD_ITEMS.add(BlockItemRegistry.SKELETON_MERCHANT_HEAD_ITEM.get());

		SMOKE_GRENADE_ITEMS.add(ItemRegistry.SMOKE_GRENADE.get());
		SMOKE_GRENADE_ITEMS.add(ItemRegistry.SMOKE_GRENADE_BLUE.get());
		SMOKE_GRENADE_ITEMS.add(ItemRegistry.SMOKE_GRENADE_RED.get());
		SMOKE_GRENADE_ITEMS.add(ItemRegistry.SMOKE_GRENADE_GREEN.get());
		SMOKE_GRENADE_ITEMS.add(ItemRegistry.SMOKE_GRENADE_PURPLE.get());
		SMOKE_GRENADE_ITEMS.add(ItemRegistry.SMOKE_GRENADE_YELLOW.get());

		SMOKE_GRENADE_ARROW_ITEMS.add(ItemRegistry.SMOKE_GRENADE_ARROW.get());
		SMOKE_GRENADE_ARROW_ITEMS.add(ItemRegistry.SMOKE_GRENADE_ARROW_BLUE.get());
		SMOKE_GRENADE_ARROW_ITEMS.add(ItemRegistry.SMOKE_GRENADE_ARROW_RED.get());
		SMOKE_GRENADE_ARROW_ITEMS.add(ItemRegistry.SMOKE_GRENADE_ARROW_GREEN.get());
		SMOKE_GRENADE_ARROW_ITEMS.add(ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE.get());
		SMOKE_GRENADE_ARROW_ITEMS.add(ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW.get());

		ARROW_ITEMS.add(ItemRegistry.WOODEN_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.STONE_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.COPPER_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.IRON_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.COBALT_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.GOLDEN_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.DIAMOND_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.NETHERITE_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.MOLTEN_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.TESLA_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.VENTUS_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.ASTRAL_ARROW.get());
		ARROW_ITEMS.add(ItemRegistry.STARSTORM_ARROW.get());
		ARROW_ITEMS.addAll(SMOKE_GRENADE_ARROW_ITEMS);

		MUSKET_BALL_ITEMS.add(ItemRegistry.WOODEN_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.STONE_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.GOLDEN_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.COPPER_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.IRON_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.COBALT_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.DIAMOND_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.NETHERITE_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.MOLTEN_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.TESLA_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.VENTUS_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.ASTRAL_MUSKET_BALL.get());
		MUSKET_BALL_ITEMS.add(ItemRegistry.STARSTORM_MUSKET_BALL.get());
	}
}