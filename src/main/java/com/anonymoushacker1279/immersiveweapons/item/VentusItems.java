package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

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
		public VentusSword(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 2, false, false));
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
		public VentusAxe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 2, false, false));
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
		public VentusPickaxe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 2, false, false));
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
		public VentusShovel(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 2, false, false));
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
		public VentusHoe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 2, false, false));
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
		public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
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
				worldIn.playLocalSound(playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 0.4f, 1.0f, true);
				pushedEntity = false;

				if (!playerIn.isCreative()) {
					playerIn.getCooldowns().addCooldown(this, 100);
					itemstack.hurtAndBreak(1, playerIn, (player) -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
				}
			}

			return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
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