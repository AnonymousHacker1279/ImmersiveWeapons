package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class ChocolateBarItem extends Item {

	public ChocolateBarItem(Properties properties) {
		super(properties.component(DataComponentTypeRegistry.IS_EXPLOSIVE, false));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (stack.getOrDefault(DataComponentTypeRegistry.IS_EXPLOSIVE, false)) {
			level.explode(null,
					IWDamageSources.explosiveChocolateBar(level.registryAccess()),
					null,
					entity.position().x,
					entity.position().y,
					entity.position().z,
					2.0F,
					false,
					ExplosionInteraction.NONE);
		}

		return super.finishUsingItem(stack, level, entity);
	}
}