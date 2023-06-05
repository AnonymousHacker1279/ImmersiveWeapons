package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.AccessorySlot;

import static tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.getAccessory;

public class AccessoryEffects {

	public static void berserkersAmuletEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		// Take 20% more damage if the player has the amulet
		if (damagedEntity instanceof Player player) {
			AccessoryItem amulet = getAccessory(player, AccessorySlot.SPIRIT);
			if (amulet == ItemRegistry.BERSERKERS_AMULET.get()) {
				event.setAmount(event.getAmount() * 1.2f);
			}
		}

		// Deal 20% more damage if the player has the amulet
		if (event.getSource().getEntity() instanceof Player player) {
			AccessoryItem amulet = getAccessory(player, AccessorySlot.SPIRIT);
			if (amulet == ItemRegistry.BERSERKERS_AMULET.get()) {
				// Check if a projectile was used, and if so, only deal 10% more damage
				if (event.getSource().getDirectEntity() instanceof Projectile) {
					event.setAmount(event.getAmount() * 1.1f);
				} else {
					event.setAmount(event.getAmount() * 1.2f);
				}
			}
		}
	}

	public static void hansBlessingEffect(LivingHurtEvent event, LivingEntity damagedEntity) {
		// Take 15% less damage if the player has the blessing
		if (damagedEntity instanceof Player player) {
			AccessoryItem blessing = getAccessory(player, AccessorySlot.SPIRIT);

			if (blessing == ItemRegistry.HANS_BLESSING.get()) {
				event.setAmount(event.getAmount() * 0.85f);
			}
		}
	}
}