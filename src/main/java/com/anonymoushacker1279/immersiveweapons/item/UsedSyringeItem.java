package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class UsedSyringeItem extends Item {

	public static final DamageSource damageSource = new DamageSource("immersiveweapons.used_syringe");

	/**
	 * Constructor for UsedSyringeItem.
	 * @param properties the <code>Properties</code> for the item
	 */
	public UsedSyringeItem(Properties properties) {
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
		RayTraceResult rayTraceResult = Minecraft.getInstance().hitResult;
		if (rayTraceResult != null && rayTraceResult.getType() != Type.ENTITY) {
			int randomNumber = GeneralUtilities.getRandomNumber(0, 100);
			if (randomNumber <= 80) {
				playerIn.addEffect(new EffectInstance(Effects.POISON, 500, 0, false, true));
				if (randomNumber <= 30) {
					playerIn.hurt(damageSource, 8.0F);
				}
			}
			if (!playerIn.isCreative()) {
				itemstack.shrink(1);
			}
		}

		return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}