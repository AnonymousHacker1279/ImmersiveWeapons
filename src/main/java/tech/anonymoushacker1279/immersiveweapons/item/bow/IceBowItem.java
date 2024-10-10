package tech.anonymoushacker1279.immersiveweapons.item.bow;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class IceBowItem extends BowItem {

	public IceBowItem(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractArrow customArrow(AbstractArrow abstractArrow, ItemStack projectileStack, ItemStack weaponStack) {
		if (abstractArrow instanceof Arrow arrow) {
			arrow.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));

			return arrow;
		}

		return abstractArrow;
	}
}