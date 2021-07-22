package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public class VentusItems {

	public static class VentusSword extends SwordItem {
		/**
		 * Constructor for VentusSword.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public VentusSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusAxe extends AxeItem {
		/**
		 * Constructor for VentusAxe.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public VentusAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusPickaxe extends PickaxeItem {
		/**
		 * Constructor for VentusPickaxe.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public VentusPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusShovel extends ShovelItem {
		/**
		 * Constructor for VentusShovel.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public VentusShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusHoe extends HoeItem {
		/**
		 * Constructor for VentusHoe.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public VentusHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusStaff extends Item {

		private boolean pushedEntity = false;

		/**
		 * Constructor for VentusStaff.
		 * @param properties the <code>Properties</code> for the item
		 */
		public VentusStaff(Properties properties) {
			super(properties);
		}

		/**
		 * Runs when the player right-clicks.
		 * @param worldIn the <code>World</code> the player is in
		 * @param playerIn the <code>PlayerEntity</code> performing the action
		 * @param handIn the <code>Hand</code> the player is using
		 * @return ActionResult extending ItemStack
		 */
		@Override
		public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
			ItemStack itemstack = playerIn.getItemInHand(handIn);
			List<Entity> entity = playerIn.level.getEntities(playerIn, playerIn.getBoundingBox().move(-2, 0, -2).expandTowards(4, 2, 4));

			if (!entity.isEmpty()) {
				for (Entity element : entity) {
					if (element.isAlive()) {
						worldIn.addParticle(ParticleTypes.CLOUD, element.getX(), element.getY() + 0.3d, element.getZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(0.0d, 0.03d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
						element.push(playerIn.getLookAngle().get(Axis.X), 1f, playerIn.getLookAngle().get(Axis.Z));

						if (!pushedEntity) {
							pushedEntity = true;
						}
					}
				}
			}

			if (pushedEntity) {
				worldIn.playLocalSound(playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 0.4f, 1.0f, true);
				pushedEntity = false;

				if (!playerIn.isCreative()) {
					playerIn.getCooldowns().addCooldown(this, 100);
					itemstack.hurtAndBreak(1, playerIn, (player) -> player.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
				}
			}

			return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
		}

		/**
		 * Check for a valid repair item.
		 * @param toRepair the <code>ItemStack</code> being repaired
		 * @param repair the <code>ItemStack</code> to repair the first one
		 * @return boolean
		 */
		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return repair.getItem() == DeferredRegistryHandler.VENTUS_SHARD.get();
		}
	}
}