package tech.anonymoushacker1279.immersiveweapons.item.projectile.throwable;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class MolotovItem extends Item {

	public MolotovItem(Properties properties) {
		super(properties);
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		level.playLocalSound(player.getX(), player.getY(), player.getZ(),
				SoundEventRegistry.GENERIC_ITEM_THROW.get(), SoundSource.NEUTRAL,
				0.5F, 0.4F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 0.8F), false);

		if (!level.isClientSide) {
			MolotovEntity molotovEntity = new MolotovEntity(level, player);
			molotovEntity.setItem(itemInHand);
			molotovEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
			level.addFreshEntity(molotovEntity);
		}

		if (!player.isCreative()) {
			itemInHand.shrink(1);
			player.getCooldowns().addCooldown(this, 100);
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}
}