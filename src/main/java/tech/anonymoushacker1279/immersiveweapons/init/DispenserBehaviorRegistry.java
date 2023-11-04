package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.Position;
import net.minecraft.core.dispenser.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity;
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
				.forEach(arrow -> DispenserBlock.registerBehavior(arrow.get(), new AbstractProjectileDispenseBehavior() {
					@Override
					protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
						CustomArrowItem<?> customArrowItem = (CustomArrowItem<?>) stack.getItem();
						AbstractArrow arrowEntity = customArrowItem.createArrow(level);
						arrowEntity.setPos(position.x(), position.y(), position.z());

						return arrowEntity;
					}
				}));

		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(0);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_RED.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(1);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_GREEN.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(2);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_BLUE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(3);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_PURPLE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(4);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_YELLOW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(level, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(5);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.MOLOTOV_COCKTAIL.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				MolotovEntity molotovEntity = new MolotovEntity(level, position.x(), position.y(), position.z());
				molotovEntity.push(5, 5, 5);
				return molotovEntity;
			}
		});
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
}