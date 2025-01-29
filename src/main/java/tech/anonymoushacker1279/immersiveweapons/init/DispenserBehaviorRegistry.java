package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.ProjectileItem.DispenseConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomBoatEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomBoatType;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomChestBoatEntity;
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
		DispenserBlock.registerBehavior(ItemRegistry.BURNED_OAK_BOAT.get(), new CustomBoatDispenseBehavior(CustomBoatType.BURNED_OAK));
		DispenserBlock.registerBehavior(ItemRegistry.BURNED_OAK_CHEST_BOAT.get(), new CustomBoatDispenseBehavior(CustomBoatType.BURNED_OAK_CHEST, true));
		DispenserBlock.registerBehavior(ItemRegistry.STARDUST_BOAT.get(), new CustomBoatDispenseBehavior(CustomBoatType.STARDUST));
		DispenserBlock.registerBehavior(ItemRegistry.STARDUST_CHEST_BOAT.get(), new CustomBoatDispenseBehavior(CustomBoatType.STARDUST_CHEST, true));
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

	public static class CustomBoatDispenseBehavior extends DefaultDispenseItemBehavior {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
		private final CustomBoatType type;
		private final boolean isChestBoat;

		public CustomBoatDispenseBehavior(CustomBoatType type) {
			this(type, false);
		}

		public CustomBoatDispenseBehavior(CustomBoatType type, boolean hasChest) {
			this.type = type;
			this.isChestBoat = hasChest;
		}

		@Override
		public ItemStack execute(BlockSource blockSource, ItemStack stack) {
			Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
			Level level = blockSource.level();
			Vec3 center = blockSource.center();
			double widthModifier = 0.5625 + (double) EntityType.BOAT.getWidth() / 2.0;
			double x = center.x() + (double) direction.getStepX() * widthModifier;
			double y = center.y() + (double) ((float) direction.getStepY() * 1.125F);
			double z = center.z() + (double) direction.getStepZ() * widthModifier;
			BlockPos relativePos = blockSource.pos().relative(direction);
			CustomBoatEntity boat = this.isChestBoat ? new CustomChestBoatEntity(type.getEntityType(), level, type.getDropItem()) : new CustomBoatEntity(type.getEntityType(), level, type.getDropItem());
			boat.setBoatType(this.type);
			boat.setYRot(direction.toYRot());
			double yModifier;
			if (boat.canBoatInFluid(level.getFluidState(relativePos))) {
				yModifier = 1.0;
			} else {
				if (!level.getBlockState(relativePos).isAir() || !boat.canBoatInFluid(level.getFluidState(relativePos.below()))) {
					return this.defaultDispenseItemBehavior.dispense(blockSource, stack);
				}

				yModifier = 0.0;
			}

			boat.setPos(x, y + yModifier, z);
			level.addFreshEntity(boat);
			stack.shrink(1);
			return stack;
		}
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