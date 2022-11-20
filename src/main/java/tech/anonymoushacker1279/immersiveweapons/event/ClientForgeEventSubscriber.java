package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.ViewportEvent.ComputeFov;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.small_parts.SmallPartsCraftables;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.data.GunData;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.BiomesAndDimensions;

import java.util.Objects;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	public static final DamageSource DEADMANS_DESERT_DAMAGE_SOURCE = new DamageSource("immersiveweapons.deadmans_desert").bypassArmor();
	private static final Minecraft minecraft = Minecraft.getInstance();

	/**
	 * Event handler for the RenderBlockScreenEffectEvent.
	 *
	 * @param event the <code>RenderBlockScreenEffectEvent</code> instance
	 */
	@SubscribeEvent
	public static void renderBlockScreenEffectEvent(RenderBlockScreenEffectEvent event) {
		// Remove fire overlay from players wearing a full set of molten armor
		if (event.getPlayer().getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			if (event.getPlayer().isInLava()) {
				if (event.getBlockState() == Blocks.FIRE.defaultBlockState()) {
					event.setCanceled(true);
				}
			}
		}
	}

	/**
	 * Event handler for the RenderFogEvent event.
	 *
	 * @param event the <code>RenderFogEvent</code> instance
	 */
	@SubscribeEvent
	public static void renderFogEvent(RenderFog event) {
		// Reduce lava fog from players wearing a full set of molten armor
		Player player = minecraft.player;
		if (player != null && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			if (player.isInLava()) {
				if (minecraft.level != null) {
					BlockState state = minecraft.level.getBlockState(new BlockPos(player.blockPosition().above(1)));
					if (state.is(Blocks.LAVA)) {
						event.setNearPlaneDistance(16.0f);
						event.setFarPlaneDistance(32.0f);
						event.setCanceled(true);
					}
				}
			}
		}
	}

	/**
	 * Event handler for the RecipesUpdatedEvent event.
	 *
	 * @param event the <code>RecipesUpdatedEvent</code> instance
	 */
	@SubscribeEvent
	public static void recipesUpdatedEvent(RecipesUpdatedEvent event) {
		ImmersiveWeapons.LOGGER.info("Recipes have updated, re-initializing custom crafting systems");
		SmallPartsCraftables.init(event.getRecipeManager());
	}

	@SubscribeEvent
	public static void computeFovEvent(ComputeFov event) {
		if (event.getFOV() != 70) {
			GunData.playerFOV = event.getFOV();
		}
		if (GunData.changingPlayerFOV != -1 && minecraft.options.getCameraType().isFirstPerson()) {
			event.setFOV(GunData.changingPlayerFOV);
		}
	}

	@SubscribeEvent
	public static void RenderGuiOverlayPostEvent(RenderGuiOverlayEvent.Post event) {
		if (GunData.changingPlayerFOV != -1) {
			if (minecraft.options.getCameraType().isFirstPerson()) {
				int screenHeight = event.getWindow().getGuiScaledHeight();
				int screenWidth = event.getWindow().getGuiScaledWidth();

				float deltaFrame = minecraft.getDeltaFrameTime() / 8;
				GunData.scopeScale = Mth.lerp(0.25F * deltaFrame, GunData.scopeScale, 1.125F);

				IWOverlays.SCOPE_ELEMENT.render((ForgeGui) minecraft.gui,
						event.getPoseStack(),
						event.getPartialTick(),
						screenWidth,
						screenHeight);
			}
		}
	}

	@SubscribeEvent
	public static void playerTickEvent(PlayerTickEvent event) {
		// Passive damage from the Deadman's Desert biome
		Player player = event.player;
		// Only check every 8 ticks, and make sure the player is not in creative mode
		if (player.tickCount % 8 == 0 && !player.isCreative()) {
			if (player.level.getBiome(player.blockPosition()).is(BiomesAndDimensions.DEADMANS_DESERT)) {
				// If the player is under the effects of Celestial Protection, they are immune to damage
				if (!player.hasEffect(DeferredRegistryHandler.CELESTIAL_PROTECTION_EFFECT.get())) {
					player.hurt(DEADMANS_DESERT_DAMAGE_SOURCE, 1);
				}
			}
		}
	}

	private static float celestialProtectionChanceForNoDamage = 0.0f;

	@SubscribeEvent
	public static void livingHurtEvent(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();

		// Handle stuff for the celestial protection effect
		if (entity.hasEffect(DeferredRegistryHandler.CELESTIAL_PROTECTION_EFFECT.get())) {
			float damage = event.getAmount();

			// Check if the damage should be neutralized.
			if (celestialProtectionChanceForNoDamage >= 1.0f) {
				event.setCanceled(true);
				celestialProtectionChanceForNoDamage = 0.0f;
				return;
			} else if (celestialProtectionChanceForNoDamage > 0.0f) {
				if (GeneralUtilities.getRandomNumber(0, 1.0f) <= celestialProtectionChanceForNoDamage) {
					event.setCanceled(true);
					celestialProtectionChanceForNoDamage = 0.0f;
				}
			}
			// Increase the chance that the next damage taken will be neutralized.
			if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.ASTRAL_HELMET.get() &&
					entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.ASTRAL_CHESTPLATE.get() &&
					entity.getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.ASTRAL_LEGGINGS.get() &&
					entity.getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.ASTRAL_BOOTS.get()) {

				celestialProtectionChanceForNoDamage += damage * 0.03f;
			} else {
				celestialProtectionChanceForNoDamage += damage * 0.01f;
			}

			// This effect grants a 5% damage reduction to all damage taken, unless they rolled for no damage.
			damage = damage * 0.95f;
			event.setAmount(damage);
		}

		// Handle stuff for the damage vulnerability effect
		if (entity.hasEffect(DeferredRegistryHandler.DAMAGE_VULNERABILITY_EFFECT.get())) {
			int level = Objects.requireNonNull(entity.getEffect(DeferredRegistryHandler.DAMAGE_VULNERABILITY_EFFECT.get()))
					.getAmplifier();
			float damage = event.getAmount();

			// Each level of the effect results in a 10% increase in damage taken.
			damage *= (level + 1) * 1.1f;

			event.setAmount(damage);
		}
	}
}