package tech.anonymoushacker1279.immersiveweapons.data.trades;

import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.SimpleItemListing;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeGroup;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.List;
import java.util.Map;

public class TradeDataGenerator extends TradeDataProvider {

	public TradeDataGenerator(PackOutput output) {
		super(output);
	}

	@Override
	protected Map<EntityType<?>, List<TradeGroup>> getTrades() {
		return Map.of(
				EntityRegistry.SKYGAZER_ENTITY.get(), List.of(
						new TradeGroup(3, List.of(
								new SimpleItemListing(1, new ItemStack(ItemRegistry.BANDAGE.get()), 8),
								new SimpleItemListing(3, new ItemStack(ItemRegistry.FIRST_AID_KIT.get()), 8),
								new SimpleItemListing(2, new ItemStack(ItemRegistry.PAINKILLERS.get()), 8),
								new SimpleItemListing(1, new ItemStack(ItemRegistry.COBALT_MUSKET_BALL.get(), 8), 12),
								new SimpleItemListing(2, new ItemStack(ItemRegistry.DIAMOND_MUSKET_BALL.get(), 8), 12),
								new SimpleItemListing(1, new ItemStack(ItemRegistry.COBALT_ARROW.get(), 8), 12),
								new SimpleItemListing(2, new ItemStack(ItemRegistry.DIAMOND_ARROW.get(), 8), 12),
								new SimpleItemListing(4, new ItemStack(ItemRegistry.AZUL_LOCATOR.get()), 2),
								new SimpleItemListing(3, new ItemStack(Items.SPYGLASS, 1), 2)
						)),
						new TradeGroup(1, List.of(
								new SimpleItemListing(12, new ItemStack(BlockItemRegistry.STARSTORM_CRYSTAL_ITEM.get()), 4),
								new SimpleItemListing(12, new ItemStack(BlockItemRegistry.ASTRAL_CRYSTAL_ITEM.get()), 4),
								new SimpleItemListing(6, new ItemStack(ItemRegistry.FLINTLOCK_PISTOL.get()), 3),
								new SimpleItemListing(7, new ItemStack(ItemRegistry.BLUNDERBUSS.get()), 3),
								new SimpleItemListing(8, new ItemStack(ItemRegistry.MUSKET.get()), 3),
								new SimpleItemListing(28, new ItemStack(ItemRegistry.METEOR_STAFF.get()), 1, 5),
								new SimpleItemListing(28, new ItemStack(ItemRegistry.CURSED_SIGHT_STAFF.get()), 1, 5),
								new SimpleItemListing(32, new ItemStack(ItemRegistry.NIGHT_VISION_GOGGLES.get()), 1, 6)
						)),
						new TradeGroup(1, List.of(
								new SimpleItemListing(30, new ItemStack(ItemRegistry.TESLA_SWORD.get()), 1, 12),
								new SimpleItemListing(30, new ItemStack(ItemRegistry.MOLTEN_SWORD.get()), 1, 12),
								new SimpleItemListing(30, new ItemStack(ItemRegistry.VENTUS_SWORD.get()), 1, 12),
								new SimpleItemListing(30, new ItemStack(ItemRegistry.ASTRAL_SWORD.get()), 1, 12),
								new SimpleItemListing(30, new ItemStack(ItemRegistry.STARSTORM_SWORD.get()), 1, 12)
						)),
						new TradeGroup(1, List.of(
								new SimpleItemListing(new ItemStack(Items.LECTERN), new ItemStack(ItemRegistry.CELESTIAL_FRAGMENT.get(), 16), new ItemStack(BlockItemRegistry.CELESTIAL_ALTAR_ITEM.get()), 1, 24, 1)
						))
				),
				EntityRegistry.SKELETON_MERCHANT_ENTITY.get(), List.of(
						new TradeGroup(3, List.of(
								new SimpleItemListing(1, new ItemStack(ItemRegistry.BANDAGE.get()), 8),
								new SimpleItemListing(2, new ItemStack(ItemRegistry.PAINKILLERS.get()), 8),
								new SimpleItemListing(2, new ItemStack(ItemRegistry.IRON_RING.get()), 6),
								new SimpleItemListing(2, new ItemStack(ItemRegistry.IRON_MUSKET_BALL.get(), 8), 12),
								new SimpleItemListing(1, new ItemStack(ItemRegistry.IRON_ARROW.get(), 8), 12),
								new SimpleItemListing(16, new ItemStack(ItemRegistry.SATCHEL.get()), 3),
								new SimpleItemListing(16, new ItemStack(ItemRegistry.POWDER_HORN.get()), 3),
								new SimpleItemListing(5, new ItemStack(ItemRegistry.BLOODY_SACRIFICE.get()), 1),
								new SimpleItemListing(2, new ItemStack(ItemRegistry.MORTAR_SHELL.get(), 3), 6)
						)),
						new TradeGroup(1, List.of(
								new SimpleItemListing(6, new ItemStack(ItemRegistry.BLOATED_HEART.get()), 3),
								new SimpleItemListing(5, new ItemStack(ItemRegistry.COBALT_RING.get()), 3),
								new SimpleItemListing(7, new ItemStack(ItemRegistry.COBALT_PIKE.get()), 3),
								new SimpleItemListing(6, new ItemStack(ItemRegistry.MOLOTOV_COCKTAIL.get(), 2), 4),
								new SimpleItemListing(3, new ItemStack(ItemRegistry.SMOKE_GRENADE.get(), 2), 6),
								new SimpleItemListing(3, new ItemStack(ItemRegistry.SMOKE_GRENADE_ARROW.get(), 2), 6),
								new SimpleItemListing(16, new ItemStack(Items.RECOVERY_COMPASS, 1), 2)
						)),
						new TradeGroup(1, List.of(
								new SimpleItemListing(32, new ItemStack(ItemRegistry.DEADEYE_PENDANT.get()), 1, 7),
								new SimpleItemListing(32, new ItemStack(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get()), 1, 7),
								new SimpleItemListing(24, new ItemStack(ItemRegistry.NETHERITE_SHIELD.get()), 1, 5),
								new SimpleItemListing(28, new ItemStack(ItemRegistry.BERSERKERS_AMULET.get()), 1, 5),
								new SimpleItemListing(28, new ItemStack(ItemRegistry.AMETHYST_RING.get()), 1, 3),
								new SimpleItemListing(28, new ItemStack(ItemRegistry.EMERALD_RING.get()), 1, 3),
								new SimpleItemListing(30, new ItemStack(Items.TOTEM_OF_UNDYING, 1), 1, 5)
						))
				)
		);
	}
}