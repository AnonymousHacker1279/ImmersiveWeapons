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

	public static void init() {
		DispenserBlock.registerBehavior(DeferredRegistryHandler.COPPER_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				CopperArrowEntity arrowentity = new CopperArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowentity.setBaseDamage(2.15d);
				return arrowentity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.IRON_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				IronArrowEntity arrowentity = new IronArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowentity.setBaseDamage(2.35d);
				return arrowentity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.DIAMOND_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				DiamondArrowEntity arrowentity = new DiamondArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowentity.setBaseDamage(3.0d);
				return arrowentity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.GOLD_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				GoldArrowEntity arrowentity = new GoldArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowentity.setBaseDamage(2.10d);
				return arrowentity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.STONE_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				StoneArrowEntity arrowentity = new StoneArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowentity.setBaseDamage(1.85d);
				return arrowentity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.WOOD_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				WoodArrowEntity arrowentity = new WoodArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowentity.setBaseDamage(1.65d);
				return arrowentity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.NETHERITE_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				NetheriteArrowEntity arrowentity = new NetheriteArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowentity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
				arrowentity.setBaseDamage(5.75d);
				return arrowentity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor("none");
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_RED.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor("red");
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_GREEN.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor("green");
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_BLUE.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor("blue");
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_PURPLE.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor("purple");
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_YELLOW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor("yellow");
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
				SmokeBombArrowEntity.setColor("red");
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor("green");
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor("blue");
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor("purple");
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor("yellow");
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

	@Override
	public ItemStack dispense(IBlockSource p_dispense_1_, ItemStack p_dispense_2_) {
		return null;
	}
}