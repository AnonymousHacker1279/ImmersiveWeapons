package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.*;

public class DispenserBehaviorRegistry implements DispenseItemBehavior {

	/**
	 * Initialize the dispenser behavior registry.
	 */
	public static void init() {
		ImmersiveWeapons.LOGGER.info("Initializing dispenser behavior registry");

		DispenserBlock.registerBehavior(ItemRegistry.WOODEN_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				WoodenArrowEntity arrowEntity = new WoodenArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(1.65d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.STONE_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				StoneArrowEntity arrowEntity = new StoneArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(1.85d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.GOLDEN_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				GoldenArrowEntity arrowEntity = new GoldenArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(2.10d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.COPPER_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				CopperArrowEntity arrowEntity = new CopperArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(2.15d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.IRON_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				IronArrowEntity arrowEntity = new IronArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(2.35d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.COBALT_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				CobaltArrowEntity arrowEntity = new CobaltArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(2.55d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.DIAMOND_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				DiamondArrowEntity arrowEntity = new DiamondArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(3.0d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.NETHERITE_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				NetheriteArrowEntity arrowEntity = new NetheriteArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(5.75d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(0);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_RED.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(1);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_GREEN.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(2);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_BLUE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(3);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_PURPLE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(4);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_YELLOW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeEntity.push(5, 5, 5);
				smokeGrenadeEntity.setColor(5);
				return smokeGrenadeEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeArrowEntity smokeGrenadeArrowEntity = new SmokeGrenadeArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeGrenadeArrowEntity.push(5, 5, 5);
				smokeGrenadeArrowEntity.setColor(0);
				return smokeGrenadeArrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_ARROW_RED.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeArrowEntity smokeGrenadeArrowEntity = new SmokeGrenadeArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeGrenadeArrowEntity.push(5, 5, 5);
				smokeGrenadeArrowEntity.setColor(1);
				return smokeGrenadeArrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_ARROW_GREEN.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeArrowEntity smokeGrenadeArrowEntity = new SmokeGrenadeArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeGrenadeArrowEntity.push(5, 5, 5);
				smokeGrenadeArrowEntity.setColor(2);
				return smokeGrenadeArrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_ARROW_BLUE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeArrowEntity smokeGrenadeArrowEntity = new SmokeGrenadeArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeGrenadeArrowEntity.push(5, 5, 5);
				smokeGrenadeArrowEntity.setColor(3);
				return smokeGrenadeArrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeArrowEntity smokeGrenadeArrowEntity = new SmokeGrenadeArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeGrenadeArrowEntity.push(5, 5, 5);
				smokeGrenadeArrowEntity.setColor(4);
				return smokeGrenadeArrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				SmokeGrenadeArrowEntity smokeGrenadeArrowEntity = new SmokeGrenadeArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeGrenadeArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeGrenadeArrowEntity.push(5, 5, 5);
				smokeGrenadeArrowEntity.setColor(5);
				return smokeGrenadeArrowEntity;
			}
		});
		DispenserBlock.registerBehavior(ItemRegistry.MOLOTOV_COCKTAIL.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				MolotovEntity molotovEntity = new MolotovEntity(worldIn, position.x(), position.y(), position.z());
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