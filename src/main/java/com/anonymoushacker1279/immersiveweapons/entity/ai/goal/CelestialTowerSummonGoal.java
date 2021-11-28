package com.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import com.anonymoushacker1279.immersiveweapons.entity.monster.CelestialTowerEntity;
import com.anonymoushacker1279.immersiveweapons.entity.monster.RockSpiderEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.Objects;
import java.util.function.Supplier;

public class CelestialTowerSummonGoal extends Goal {

	private final CelestialTowerEntity mob;
	private int waveSpawnCooldown = 100;

	public CelestialTowerSummonGoal(CelestialTowerEntity pMob) {
		mob = pMob;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return !mob.isDoneSpawningWaves() && getEligibleMobsInArea() == 0;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (mob.getWavesSpawned() < mob.getTotalWavesToSpawn() && waveSpawnCooldown <= 0) {
			int mobsToSpawn = (GeneralUtilities.getRandomNumber(8, 12 + mob.getWavesSpawned())) * mob.getWaveSizeModifier(); // Get the total mobs to spawn
			int fodderMobsToSpawn = (int) (mobsToSpawn * 0.3f); // Get the number of "fodder" mobs to spawn
			mobsToSpawn = mobsToSpawn - fodderMobsToSpawn; // Reduce the total number left to spawn
			int powerMobsToSpawn = isWavesPastHalf() ? (int) (mobsToSpawn * 0.2f) : 0; // Get the number of "power" mobs to spawn, if over halfway through the waves
			mobsToSpawn = mobsToSpawn - powerMobsToSpawn; //Reduce the total number left to spawn

			for (int i = fodderMobsToSpawn; i > 0; i--) {
				BlockPos summonPos = new BlockPos(mob.getX() + GeneralUtilities.getRandomNumber(-16, 17), mob.getY(), mob.getZ() + GeneralUtilities.getRandomNumber(-16, 17));
				RockSpiderEntity entity = new RockSpiderEntity(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), mob.level);
				entity.setPersistenceRequired();
				entity.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
				mob.level.addFreshEntity(entity);
				PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> mob.level.getChunkAt(summonPos)), new CelestialTowerSummonGoalPacketHandler(summonPos, 0));
			}
			for (int i = powerMobsToSpawn; i > 0; i--) {
				BlockPos summonPos = new BlockPos(mob.getX() + GeneralUtilities.getRandomNumber(-16, 17), mob.getY(), mob.getZ() + GeneralUtilities.getRandomNumber(-16, 17));
				Zombie entity = new Zombie(EntityType.ZOMBIE, mob.level);
				entity.setPersistenceRequired();
				ItemStack sword = new ItemStack(Items.IRON_SWORD);
				sword.enchant(Enchantments.SHARPNESS, GeneralUtilities.getRandomNumber(2, 4 + mob.getWavesSpawned()));
				sword.enchant(Enchantments.KNOCKBACK, GeneralUtilities.getRandomNumber(1, 3 + mob.getWavesSpawned()));
				sword.enchant(Enchantments.FIRE_ASPECT, GeneralUtilities.getRandomNumber(1, 2 + mob.getWavesSpawned()));
				entity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				entity.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				entity.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
				entity.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
				Objects.requireNonNull(entity.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20 + (GeneralUtilities.getRandomNumber(5, 11) * mob.getWaveSizeModifier()));
				entity.heal(entity.getMaxHealth());
				entity.setItemInHand(InteractionHand.MAIN_HAND, sword);
				entity.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
				mob.level.addFreshEntity(entity);
				PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> mob.level.getChunkAt(summonPos)), new CelestialTowerSummonGoalPacketHandler(summonPos, 0));
			}
			for (int i = mobsToSpawn; i > 0; i--) {
				BlockPos summonPos = new BlockPos(mob.getX() + GeneralUtilities.getRandomNumber(-16, 17), mob.getY(), mob.getZ() + GeneralUtilities.getRandomNumber(-16, 17));
				Skeleton entity = new Skeleton(EntityType.SKELETON, mob.level);
				entity.setPersistenceRequired();
				ItemStack bow = new ItemStack(Items.BOW);
				bow.enchant(Enchantments.POWER_ARROWS, GeneralUtilities.getRandomNumber(1, 3 + mob.getWavesSpawned()));
				bow.enchant(Enchantments.PUNCH_ARROWS, GeneralUtilities.getRandomNumber(1, 2 + mob.getWavesSpawned()));
				entity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				Objects.requireNonNull(entity.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20 + (GeneralUtilities.getRandomNumber(0, 6) * mob.getWaveSizeModifier()));
				entity.heal(entity.getMaxHealth());
				entity.setItemInHand(InteractionHand.MAIN_HAND, bow);
				entity.teleportTo(summonPos.getX(), summonPos.getY(), summonPos.getZ());
				mob.level.addFreshEntity(entity);
				PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> mob.level.getChunkAt(summonPos)), new CelestialTowerSummonGoalPacketHandler(summonPos, 0));
			}

			// Spawn some particles
			for (int i = 96; i > 0; i--) {
				PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> mob.level.getChunkAt(mob.blockPosition())), new CelestialTowerSummonGoalPacketHandler(mob.blockPosition(), 1));
			}

			mob.setWavesSpawned(mob.getWavesSpawned() + 1); // Increment the total spawned waves
			mob.bossEvent.setProgress((float) mob.getWavesSpawned() / mob.getTotalWavesToSpawn());
			mob.bossEvent.setName(new TranslatableComponent("immersiveweapons.boss.celestial_tower.waves", mob.getWavesSpawned(), mob.getTotalWavesToSpawn()));
			mob.setNoActionTime(0);
			mob.playSound(DeferredRegistryHandler.CELESTIAL_TOWER_SUMMON.get(), 1.0f, 1.0f + GeneralUtilities.getRandomNumber(-0.3f, 0.2f));
			waveSpawnCooldown = 100; // Set the wave spawn cooldown
		} else if (waveSpawnCooldown > 0) {
			waveSpawnCooldown--;
		} else {
			mob.setDoneSpawningWaves(true);
			mob.bossEvent.setColor(BossBarColor.GREEN);
			mob.bossEvent.setName(mob.getDisplayName());
			mob.bossEvent.setProgress(1f);
			mob.setNoActionTime(0);
			mob.playSound(DeferredRegistryHandler.CELESTIAL_TOWER_VULNERABLE.get(), 1.0f, 1.0f + GeneralUtilities.getRandomNumber(-0.3f, 0.2f));
		}
	}

	// Only return major mobs in the area; "fodder" entities are ignored
	private int getEligibleMobsInArea() {
		AABB searchBox = new AABB(mob.getX() - 32, mob.getY() - 32, mob.getZ() - 32, mob.getX() + 32, mob.getY() + 32, mob.getZ() + 32);
		int mobs;
		mobs = mob.level.getNearbyEntities(Skeleton.class, TargetingConditions.forNonCombat(), mob, searchBox).size();
		mobs = mobs + mob.level.getNearbyEntities(Zombie.class, TargetingConditions.forNonCombat(), mob, searchBox).size();
		return mobs;
	}

	private boolean isWavesPastHalf() {
		return mob.getTotalWavesToSpawn() / 2 <= mob.getWavesSpawned();
	}

	public record CelestialTowerSummonGoalPacketHandler(BlockPos blockPos, int type) {

		/**
		 * Constructor for CelestialTowerSummonGoalPacketHandler.
		 *
		 * @param blockPos the <code>BlockPos</code> the packet came from
		 * @param type     the particles to summon
		 */
		public CelestialTowerSummonGoalPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>CelestialTowerSummonGoalPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(CelestialTowerSummonGoalPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBlockPos(msg.blockPos).writeInt(msg.type);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return CelestialTowerSummonGoalPacketHandler
		 */
		public static CelestialTowerSummonGoalPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new CelestialTowerSummonGoalPacketHandler(packetBuffer.readBlockPos(), packetBuffer.readInt());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>CelestialTowerSummonGoalPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(CelestialTowerSummonGoalPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>CelestialTowerSummonGoalPacketHandler</code> message being sent
		 */
		@OnlyIn(Dist.CLIENT)
		private static void handleOnClient(CelestialTowerSummonGoalPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				if (msg.type == 0) {
					minecraft.level.addParticle(ParticleTypes.POOF, msg.blockPos.getX(), msg.blockPos.getY(), msg.blockPos.getZ(), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(-0.1d, -0.08d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
				} else if (msg.type == 1) {
					minecraft.level.addParticle(ParticleTypes.FLAME, msg.blockPos.getX(), msg.blockPos.getY() + 9, msg.blockPos.getZ(), GeneralUtilities.getRandomNumber(-0.6d, 0.81d), GeneralUtilities.getRandomNumber(0.3d, 0.4d), GeneralUtilities.getRandomNumber(-0.6d, 0.81d));
				}
			}
		}
	}
}