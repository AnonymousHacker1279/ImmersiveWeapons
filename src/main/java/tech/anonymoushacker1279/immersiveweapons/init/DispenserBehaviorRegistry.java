package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.*;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.ProjectileItem.DispenseConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.CustomArrowItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem;

public class DispenserBehaviorRegistry implements DispenseItemBehavior {

	/**
	 * Initialize the dispenser behavior registry.
	 */
	public static void init() {
		ImmersiveWeapons.LOGGER.info("Initializing dispenser behavior registry");

		// Iterate through all registry items of the CustomArrowItem type and register a dispenser behavior for each
		ItemRegistry.ITEMS.getEntries().stream()
				.filter(item -> item.get() instanceof CustomArrowItem<?>)
				.forEach(arrow -> DispenserBlock.registerBehavior(arrow.get(), new ProjectileDispenseBehavior(arrow.get())));

		ItemRegistry.ITEMS.getEntries().stream()
				.filter(item -> item.get() instanceof ThrowableItem)
				.forEach(throwable -> DispenserBlock.registerBehavior(throwable.get(), new ThrowableItemDispenseBehavior((ThrowableItem) throwable.get())));

		DispenserBlock.registerProjectileBehavior(ItemRegistry.DRAGON_FIREBALL.get());

		// Register behavior for boats
		DispenserBlock.registerBehavior(ItemRegistry.BURNED_OAK_BOAT.get(), new BoatDispenseItemBehavior(EntityRegistry.BURNED_OAK_BOAT_ENTITY.get()));
		DispenserBlock.registerBehavior(ItemRegistry.BURNED_OAK_CHEST_BOAT.get(), new BoatDispenseItemBehavior(EntityRegistry.BURNED_OAK_CHEST_BOAT_ENTITY.get()));
		DispenserBlock.registerBehavior(ItemRegistry.STARDUST_BOAT.get(), new BoatDispenseItemBehavior(EntityRegistry.STARDUST_BOAT_ENTITY.get()));
		DispenserBlock.registerBehavior(ItemRegistry.STARDUST_CHEST_BOAT.get(), new BoatDispenseItemBehavior(EntityRegistry.STARDUST_CHEST_BOAT_ENTITY.get()));
	}

	/**
	 * Custom dispense behavior.
	 *
	 * @param source    the <code>BlockSource</code> instance
	 * @param itemStack the <code>ItemStack</code> being dispensed
	 * @return ItemStack
	 */
	@Override
	public ItemStack dispense(BlockSource source, ItemStack itemStack) {
		return null;
	}

	public static class ThrowableItemDispenseBehavior extends DefaultDispenseItemBehavior {

		private final ThrowableItem throwableItem;
		private final ProjectileItem.DispenseConfig dispenseConfig;

		public ThrowableItemDispenseBehavior(ThrowableItem throwable) {
			this.throwableItem = throwable;
			this.dispenseConfig = DispenseConfig.DEFAULT;
		}

		@Override
		public ItemStack execute(BlockSource blockSource, ItemStack stack) {
			Level level = blockSource.level();
			Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
			Position position = this.dispenseConfig.positionFunction().getDispensePosition(blockSource, direction);

			ThrowableItemProjectile throwable;
			switch (throwableItem.type) {
				case SMOKE_GRENADE ->
						throwable = throwableItem.createSmokeGrenade(stack, level, position, direction, 0.5F);
				case FLASHBANG -> throwable = throwableItem.createFlashbang(stack, level, position, direction, 0.5F);
				case MOLOTOV -> throwable = throwableItem.createMolotov(stack, level, position, direction);
				default -> throwable = throwableItem.createMudBall(stack, level, position, direction);
			}

			level.addFreshEntity(throwable);
			stack.shrink(1);
			return stack;
		}
	}
}