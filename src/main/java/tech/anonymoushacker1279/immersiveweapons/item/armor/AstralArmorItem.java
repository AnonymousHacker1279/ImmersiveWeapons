package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.AstralArmorPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class AstralArmorItem extends ArmorItem {

	private final boolean isLeggings;
	private int dashCooldown = 0;

	public AstralArmorItem(ArmorMaterial material, ArmorItem.Type armorType, Properties properties, boolean isLeggings) {
		super(material, armorType, properties);
		this.isLeggings = isLeggings;
	}

	/**
	 * Get the armor texture.
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param entity the <code>Entity</code> wearing the armor
	 * @param slot   the <code>EquipmentSlot</code>
	 * @param type   type ID
	 * @return String
	 */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return (!isLeggings
				? ImmersiveWeapons.MOD_ID + ":textures/armor/astral_layer_1.png"
				: ImmersiveWeapons.MOD_ID + ":textures/armor/astral_layer_2.png");
	}

	/**
	 * Runs once per tick while armor is equipped
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> wearing the armor
	 */
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.ASTRAL_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.ASTRAL_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.ASTRAL_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.ASTRAL_BOOTS.get()) {

			boolean effectEnabled = player.getPersistentData().getBoolean("AstralArmorEffectEnabled");

			if (level.isClientSide) {
				if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
					// Store the toggle variable in the player's NBT
					player.getPersistentData().putBoolean("AstralArmorEffectEnabled", !effectEnabled);

					// Send packet to server
					PacketDistributor.SERVER.noArg().send(new AstralArmorPayload(!effectEnabled));

					if (effectEnabled) {
						player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.disabled")
								.withStyle(ChatFormatting.RED), true);
					} else {
						player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.enabled")
								.withStyle(ChatFormatting.GREEN), true);
					}
				}
				if (effectEnabled) {
					if (Minecraft.getInstance().options.keyJump.consumeClick()) {
						level.addParticle(ParticleTypes.CLOUD,
								player.getX(),
								player.getY() + 0.1d,
								player.getZ(),
								GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
								GeneralUtilities.getRandomNumber(0.0d, 0.03d),
								GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
					}
					if (IWKeyBinds.ARMOR_ACTION.consumeClick()) {
						if (dashCooldown == 0) {
							Vec3 viewVector = player.getViewVector(1);
							Vec3 dashVector = new Vec3(viewVector.x(), player.getDeltaMovement().y(), viewVector.z());
							player.setNoGravity(true);
							player.setDeltaMovement(dashVector);
							dashCooldown = 60;
						}
					}
				}

				if (dashCooldown > 0) {
					if (dashCooldown < 50 && player.isNoGravity()) {
						player.setNoGravity(false);
					}

					level.addParticle(ParticleTypes.ELECTRIC_SPARK,
							player.getX(), player.getY(), player.getZ(),
							GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
							GeneralUtilities.getRandomNumber(0.0d, 0.03d),
							GeneralUtilities.getRandomNumber(-0.03d, 0.03d));

					dashCooldown--;

					if (dashCooldown == 0) {
						player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
					}
				}
			}

			if (effectEnabled) {
				player.addEffect(new MobEffectInstance(MobEffects.JUMP,
						0, 1, false, false));
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
						0, 0, false, false));
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,
						0, 0, false, false));
			}
		}
	}
}