package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFortitudeItem extends Item {

	protected boolean usedOnEntity = false;
	protected final boolean requireHalfHealth;

	public AbstractFortitudeItem(Properties properties, boolean requireHalfHealth) {
		super(properties);

		this.requireHalfHealth = requireHalfHealth;
	}

	public int getCooldown() {
		return 300;
	}

	@Nullable
	public ItemStack getContainerItem() {
		return null;
	}

	public List<MobEffectInstance> effects() {
		return new ArrayList<>(0);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		if (usedOnEntity) {
			usedOnEntity = false;
			return InteractionResultHolder.pass(player.getItemInHand(hand));
		}

		ItemStack itemInHand = player.getItemInHand(hand);

		if (requireHalfHealth) {
			if (player.getMaxHealth() - player.getHealth() <= player.getMaxHealth() / 2) {
				if (level.isClientSide) {
					player.displayClientMessage(Component.translatable("immersiveweapons.item.first_aid_kit.player")
							.withStyle(ChatFormatting.RED), true);
				}
				return InteractionResultHolder.pass(itemInHand);
			}
		}

		setEffects(player);

		if (!player.isCreative()) {
			itemInHand.shrink(1);
			if (getContainerItem() != null) {
				player.getInventory().add(getContainerItem());
			}
			player.getCooldowns().addCooldown(this, getCooldown());
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player,
	                                              LivingEntity entity, InteractionHand hand) {

		usedOnEntity = true;

		if (requireHalfHealth) {
			if (entity.getMaxHealth() - entity.getHealth() <= entity.getMaxHealth() / 2) {
				if (player.level().isClientSide) {
					player.displayClientMessage(Component.translatable("immersiveweapons.item.first_aid_kit.entity")
							.withStyle(ChatFormatting.RED), true);
				}
				return InteractionResult.PASS;
			}
		}

		if (entity.level().isClientSide) {
			return InteractionResult.PASS;
		}

		setEffects(entity);

		if (!player.isCreative()) {
			if (getContainerItem() != null) {
				player.getInventory().add(getContainerItem());
			}
			stack.shrink(1);
		}

		return InteractionResult.PASS;
	}

	private void setEffects(LivingEntity entity) {
		if (entity.hasEffect(EffectRegistry.BLEEDING_EFFECT.get())) {
			entity.removeEffect(EffectRegistry.BLEEDING_EFFECT.get());
		}

		for (MobEffectInstance effect : effects()) {
			entity.addEffect(effect);
		}
	}
}