package com.anonymoushacker1279.immersiveweapons.item.projectile.throwable;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.MudBallEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MudBallItem extends Item {

	public MudBallItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), DeferredRegistryHandler.GENERIC_WHOOSH.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!pLevel.isClientSide) {
			MudBallEntity mudBall = new MudBallEntity(pLevel, pPlayer);
			mudBall.setItem(itemstack);
			mudBall.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
			pLevel.addFreshEntity(mudBall);
		}

		if (!pPlayer.isCreative()) {
			itemstack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
	}
}