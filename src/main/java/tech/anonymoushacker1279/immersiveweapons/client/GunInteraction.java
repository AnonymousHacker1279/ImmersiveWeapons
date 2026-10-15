package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/// Decides whether the use key should interact with what the player is looking at, instead of aiming, while a firearm
/// is held. Firearms suppress the vanilla use key, which would otherwise block doors, chests, villagers, and so on.
public final class GunInteraction {

	private static final Map<Class<?>, Boolean> BLOCK_INTERACTIVITY = new ConcurrentHashMap<>();

	private GunInteraction() {
	}

	/// Whether the use key should interact with the target under the crosshair rather than aim.
	public static boolean shouldInteract(Minecraft minecraft) {
		LocalPlayer player = minecraft.player;
		HitResult hit = minecraft.hitResult;
		if (player == null || hit == null) {
			return false;
		}

		// Optionally, crouching always aims
		if (player.isCrouching() && IWConfigs.CLIENT.gunSuppressInteractionWhenCrouching.get()) {
			return false;
		}

		if (hit instanceof BlockHitResult blockHit && hit.getType() == HitResult.Type.BLOCK) {
			Block block = player.level().getBlockState(blockHit.getBlockPos()).getBlock();
			return BLOCK_INTERACTIVITY.computeIfAbsent(block.getClass(), GunInteraction::overridesUse);
		}

		if (hit instanceof EntityHitResult entityHit) {
			Entity entity = entityHit.getEntity();
			return entity instanceof AbstractVillager
					|| entity instanceof VehicleEntity
					|| entity instanceof ItemFrame
					|| entity instanceof ArmorStand
					|| entity instanceof AbstractHorse
					|| (entity instanceof TamableAnimal tamable && tamable.isOwnedBy(player));
		}

		return false;
	}

	/// Whether a block class does something on use, which is when it overrides one of the use methods.
	private static boolean overridesUse(Class<?> blockClass) {
		for (Class<?> clazz = blockClass; clazz != null && clazz != BlockBehaviour.class; clazz = clazz.getSuperclass()) {
			for (var method : clazz.getDeclaredMethods()) {
				String name = method.getName();
				if (name.equals("useWithoutItem") || name.equals("useItemOn")) {
					return true;
				}
			}
		}

		return false;
	}
}
