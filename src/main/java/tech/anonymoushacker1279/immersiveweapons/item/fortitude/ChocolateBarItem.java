package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class ChocolateBarItem extends Item {

	private final boolean isExplosive;

	/**
	 * Constructor for ChocolateBarItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public ChocolateBarItem(Properties properties, boolean isExplosive) {
		super(properties);

		this.isExplosive = isExplosive;
	}

	/**
	 * Runs when the item is no longer being used.
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param level  the <code>Level</code> the entity is in
	 * @param entity the <code>LivingEntity</code> using the item
	 * @return ItemStack
	 */
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (isExplosive) {
			level.explode(null, IWDamageSources.explosiveChocolateBar(level.registryAccess()), null, entity.position().x, entity.position().y,
					entity.position().z, 2.0F, false, ExplosionInteraction.NONE);
		}

		return super.finishUsingItem(stack, level, entity);
	}
}