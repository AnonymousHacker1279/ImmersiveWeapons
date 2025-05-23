package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface TickableArmor {

	void playerTick(Level level, Player player);
}