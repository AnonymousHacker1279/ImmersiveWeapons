package tech.anonymoushacker1279.immersiveweapons.data.trades;

import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeGroup;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades.ItemsForEmeralds;
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
								new ItemsForEmeralds(new ItemStack(ItemRegistry.BANDAGE.get()), 1, 8),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.FIRST_AID_KIT.get()), 3, 8),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.PAINKILLERS.get()), 2, 8),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.COBALT_MUSKET_BALL.get(), 8), 1, 12),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.DIAMOND_MUSKET_BALL.get(), 8), 2, 12),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.COBALT_ARROW.get(), 8), 1, 12),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.DIAMOND_ARROW.get(), 8), 2, 12),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.AZUL_LOCATOR.get()), 4, 2),
								new ItemsForEmeralds(new ItemStack(Items.SPYGLASS, 1), 3, 2)
						)),
						new TradeGroup(1, List.of(
								new ItemsForEmeralds(new ItemStack(BlockItemRegistry.STARSTORM_CRYSTAL_ITEM.get()), 12, 4),
								new ItemsForEmeralds(new ItemStack(BlockItemRegistry.ASTRAL_CRYSTAL_ITEM.get()), 12, 4),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.FLINTLOCK_PISTOL.get()), 6, 3),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.BLUNDERBUSS.get()), 7, 3),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.MUSKET.get()), 8, 3),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.METEOR_STAFF.get()), 28, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.CURSED_SIGHT_STAFF.get()), 28, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.NIGHT_VISION_GOGGLES.get()), 32, 1)
						)),
						new TradeGroup(1, List.of(
								new ItemsForEmeralds(new ItemStack(ItemRegistry.TESLA_SWORD.get()), 30, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.MOLTEN_SWORD.get()), 30, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.VENTUS_SWORD.get()), 30, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.ASTRAL_SWORD.get()), 30, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.STARSTORM_SWORD.get()), 30, 1)
						))
				),
				EntityRegistry.SKELETON_MERCHANT_ENTITY.get(), List.of(
						new TradeGroup(3, List.of(
								new ItemsForEmeralds(new ItemStack(ItemRegistry.BANDAGE.get()), 1, 8),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.PAINKILLERS.get()), 2, 8),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.IRON_RING.get()), 2, 6),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.IRON_MUSKET_BALL.get(), 8), 2, 12),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.IRON_ARROW.get(), 8), 1, 12),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.SATCHEL.get()), 16, 3),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.POWDER_HORN.get()), 16, 3),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.BLOODY_SACRIFICE.get()), 5, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.MORTAR_SHELL.get(), 3), 2, 6)
						)),
						new TradeGroup(1, List.of(
								new ItemsForEmeralds(new ItemStack(ItemRegistry.BLOATED_HEART.get()), 6, 3),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.COBALT_RING.get()), 5, 3),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.COBALT_PIKE.get()), 0, 3),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.MOLOTOV_COCKTAIL.get(), 2), 6, 4),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.SMOKE_GRENADE.get(), 2), 3, 6),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.SMOKE_GRENADE_ARROW.get(), 2), 3, 6),
								new ItemsForEmeralds(new ItemStack(Items.RECOVERY_COMPASS, 1), 16, 2)
						)),
						new TradeGroup(1, List.of(
								new ItemsForEmeralds(new ItemStack(ItemRegistry.DEADEYE_PENDANT.get()), 32, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get()), 32, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.NETHERITE_SHIELD.get()), 24, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.BERSERKERS_AMULET.get()), 28, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.AMETHYST_RING.get()), 28, 1),
								new ItemsForEmeralds(new ItemStack(ItemRegistry.EMERALD_RING.get()), 28, 1),
								new ItemsForEmeralds(new ItemStack(Items.TOTEM_OF_UNDYING, 1), 30, 1)
						))
				)
		);
	}
}