package com.anonymoushacker1279.immersiveweapons.item.tool.ventus;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VentusStaff extends Item {

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
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
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
	public boolean isValidRepairItem(@NotNull ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == DeferredRegistryHandler.VENTUS_SHARD.get();
	}
}