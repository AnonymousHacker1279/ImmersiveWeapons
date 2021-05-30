package com.anonymoushacker1279.immersiveweapons.tileentity;

import com.anonymoushacker1279.immersiveweapons.entity.passive.MinutemanEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Objects;

public class MinutemanStatueTileEntity extends TileEntity implements ITickableTileEntity {

	private int cooldown;
	private int scannedMinutemen;

	public MinutemanStatueTileEntity() {
		super(DeferredRegistryHandler.MINUTEMAN_STATUE_TILE_ENTITY.get());
	}

	@Override
	public void tick() {
		if (this.world != null && !this.world.isRemote && Objects.equals(this.world.getBiome(this.getPos()).getRegistryName(), DeferredRegistryHandler.BATTLEFIELD.get().getRegistryName()) && cooldown == 0) {
			List<MinutemanEntity> listOfMinutmenInArea = this.world.getEntitiesWithinAABB(MinutemanEntity.class, new AxisAlignedBB(this.getPos().getX() - 48, this.getPos().getY() - 16, this.getPos().getZ() - 48, this.getPos().getX() + 48, this.getPos().getY() + 16, this.getPos().getZ() + 48));
			scannedMinutemen = listOfMinutmenInArea.size();

			if (scannedMinutemen <= 16) {
				MinutemanEntity minutemanEntity = DeferredRegistryHandler.MINUTEMAN_ENTITY.get().create(this.world);
				if (minutemanEntity != null) {
					while (true) {
						BlockPos blockPos = this.getRandomPositionInArea();
						if (this.world.getBlockState(blockPos) == Blocks.AIR.getDefaultState()) {
							minutemanEntity.moveToBlockPosAndAngles(blockPos, 0.0F, 0.0F);
							this.world.addEntity(minutemanEntity);
							spawnParticles();
							cooldown = 400;
							break;
						}
					}
				}
			}
		} else if (cooldown > 0) {
			cooldown--;
		}
	}

	private void spawnParticles() {
		ServerWorld serverWorld = (ServerWorld) this.getWorld();
		if (serverWorld != null) {
			serverWorld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, this.getPos().getX() + 0.5d, this.getPos().getY(), this.getPos().getZ() + 0.75d, 5, GeneralUtilities.getRandomNumber(-0.05d, 0.05d), GeneralUtilities.getRandomNumber(-0.25d, 0.25d), GeneralUtilities.getRandomNumber(-0.05d, 0.05d), GeneralUtilities.getRandomNumber(-0.15d, 0.15d));
		}
	}

	private BlockPos getRandomPositionInArea() {
		return new BlockPos(this.getPos().getX() + GeneralUtilities.getRandomNumber(-15, 15), this.getPos().getY(), this.getPos().getZ() + GeneralUtilities.getRandomNumber(-15, 15));
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		super.write(tag);
		tag.putInt("scanCooldown", cooldown);
		tag.putInt("scannedMinutemen", scannedMinutemen);
		return tag;
	}

	@Override
	public void read(BlockState state, CompoundNBT tag) {
		super.read(state, tag);
		cooldown = tag.getInt("scanCooldown");
		scannedMinutemen = tag.getInt("scannedMinutemen");
	}
}