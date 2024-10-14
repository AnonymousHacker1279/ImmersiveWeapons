package tech.anonymoushacker1279.immersiveweapons.item.bow;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.util.ArrowAttributeAccessor;

public class AuroraBow extends BowItem {

	public AuroraBow(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractArrow customArrow(AbstractArrow abstractArrow, ItemStack projectileStack, ItemStack weaponStack) {
		((ArrowAttributeAccessor) abstractArrow).immersiveWeapons$setGravity(abstractArrow.getGravity() / 4);

		return abstractArrow;
	}
}