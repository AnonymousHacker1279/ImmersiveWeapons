package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.*;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DispenserBehaviorRegistry implements IDispenseItemBehavior {

	/**
	 * Initialize the dispenser behavior registry.
	 */
	public static void init() {
		DispenserBlock.registerBehavior(DeferredRegistryHandler.COPPER_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				CopperArrowEntity arrowEntity = new CopperArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowEntity.setBaseDamage(2.15d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.IRON_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				IronArrowEntity arrowEntity = new IronArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowEntity.setBaseDamage(2.35d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.DIAMOND_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				DiamondArrowEntity arrowEntity = new DiamondArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowEntity.setBaseDamage(3.0d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.GOLD_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				GoldArrowEntity arrowEntity = new GoldArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowEntity.setBaseDamage(2.10d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.STONE_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				StoneArrowEntity arrowEntity = new StoneArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowEntity.setBaseDamage(1.85d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.WOOD_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				WoodArrowEntity arrowEntity = new WoodArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowEntity.setBaseDamage(1.65d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.NETHERITE_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				NetheriteArrowEntity arrowEntity = new NetheriteArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowEntity.setBaseDamage(5.75d);
				return arrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(0);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_RED.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(1);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_GREEN.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(2);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_BLUE.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(3);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_PURPLE.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(4);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_YELLOW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(5);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(1);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(2);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(3);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(4);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(5);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.MOLOTOV_COCKTAIL.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				MolotovEntity molotovEntity = new MolotovEntity(worldIn, position.x(), position.y(), position.z());
				molotovEntity.push(5, 5, 5);
				return molotovEntity;
			}

		});
	}

	/**
	 * Custom dispense behavior.
	 * @param iBlockSource the <code>IBlockSource</code> instance
	 * @param itemStack the <code>ItemStack</code> being dispensed
	 * @return ItemStack
	 */
	@Override
	public ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack) {
		return null;
	}
}