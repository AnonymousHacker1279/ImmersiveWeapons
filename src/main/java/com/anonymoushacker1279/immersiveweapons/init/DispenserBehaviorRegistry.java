package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.*;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

public class DispenserBehaviorRegistry implements DispenseItemBehavior {

	/**
	 * Initialize the dispenser behavior registry.
	 */
	public static void init() {
		DispenserBlock.registerBehavior(DeferredRegistryHandler.COPPER_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				CopperArrowEntity arrowEntity = new CopperArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(2.15d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.IRON_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				IronArrowEntity arrowEntity = new IronArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(2.35d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.DIAMOND_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				DiamondArrowEntity arrowEntity = new DiamondArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(3.0d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.GOLD_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				GoldArrowEntity arrowEntity = new GoldArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(2.10d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.STONE_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				StoneArrowEntity arrowEntity = new StoneArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(1.85d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.WOOD_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				WoodArrowEntity arrowEntity = new WoodArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(1.65d);
				return arrowEntity;
			}
		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.NETHERITE_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				NetheriteArrowEntity arrowEntity = new NetheriteArrowEntity(worldIn, position.x(), position.y(), position.z());
				arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
				arrowEntity.setBaseDamage(5.75d);
				return arrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(0);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_RED.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(1);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_GREEN.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(2);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_BLUE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(3);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_PURPLE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(4);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_YELLOW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombEntity.push(5, 5, 5);
				SmokeBombEntity.setColor(5);
				return smokeBombEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(1);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(2);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(3);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(4);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
				SmokeBombArrowEntity smokeBombArrowEntity = new SmokeBombArrowEntity(worldIn, position.x(), position.y(), position.z());
				smokeBombArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
				smokeBombArrowEntity.push(5, 5, 5);
				SmokeBombArrowEntity.setColor(5);
				return smokeBombArrowEntity;
			}

		});
		DispenserBlock.registerBehavior(DeferredRegistryHandler.MOLOTOV_COCKTAIL.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected @NotNull Projectile getProjectile(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
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
	public @NotNull ItemStack dispense(@NotNull BlockSource iBlockSource, @NotNull ItemStack itemStack) {
		return null;
	}
}