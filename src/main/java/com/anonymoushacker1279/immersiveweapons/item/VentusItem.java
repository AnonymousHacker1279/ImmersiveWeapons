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

public class VentusItem {

	public static class VentusSword extends SwordItem {
		public VentusSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties tab) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP));
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusAxe extends AxeItem {

		public VentusAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusPickaxe extends PickaxeItem {

		public VentusPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusShovel extends ShovelItem {

		public VentusShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusHoe extends HoeItem {

		public VentusHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class VentusStaff extends Item {

		private boolean pushedEntity = false;

		public VentusStaff(Properties properties) {
			super(properties);
		}

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

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return repair.getItem() == DeferredRegistryHandler.VENTUS_SHARD.get();
		}
	}
}