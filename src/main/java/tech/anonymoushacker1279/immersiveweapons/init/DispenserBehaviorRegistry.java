package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.*;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.CustomArrowItem;

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

		// TODO: find a better way to do this
		/*DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE.get(), new ProjectileDispenseBehavior() {
			@Override
			private Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(0);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_RED.get(), new ProjectileDispenseBehavior() {
			@Override
			private Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(1);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_GREEN.get(), new ProjectileDispenseBehavior() {
			@Override
			private Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(2);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_BLUE.get(), new ProjectileDispenseBehavior() {
			@Override
			private Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(3);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_PURPLE.get(), new ProjectileDispenseBehavior() {
			@Override
			private Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(4);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_YELLOW.get(), new ProjectileDispenseBehavior() {
			@Override
			private Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(5);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.FLASHBANG.get(), new ProjectileDispenseBehavior() {
			@Override
			private Projectile getProjectile(Level level, Position position, ItemStack stack) {
				FlashbangEntity flashbangEntity = new FlashbangEntity(level, position.x(), position.y(), position.z());
				flashbangEntity.push(5, 5, 5);
				return flashbangEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.MOLOTOV_COCKTAIL.get(), new ProjectileDispenseBehavior() {
			@Override
			private Projectile getProjectile(Level level, Position position, ItemStack stack) {
				MolotovEntity molotovEntity = new MolotovEntity(level, position.x(), position.y(), position.z());
				molotovEntity.push(5, 5, 5);
				return molotovEntity;
			}
		});*/

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
}