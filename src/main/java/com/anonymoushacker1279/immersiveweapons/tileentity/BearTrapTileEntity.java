package com.anonymoushacker1279.immersiveweapons.tileentity;

import java.util.EnumSet;
import java.util.UUID;

import javax.annotation.Nullable;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class BearTrapTileEntity extends TileEntity implements ITickableTileEntity {

	private final DamageSource damageSource = new DamageSource("immersiveweapons.bear_trap");
    @Nullable
    private MobEntity entityliving;
    private PlayerEntity entitylivingPlayer;
    private Goal doNothingGoal;
    private UUID id;
    private boolean currentlyUnpressingKeys = false;
    private Minecraft mc = Minecraft.getInstance();
    
    private BlockPos previousPlayerPos;

    public BearTrapTileEntity() {
        super(DeferredRegistryHandler.BEAR_TRAP_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        final MobEntity trapped = this.getTrappedEntity();
        final PlayerEntity trappedPlayer = this.getTrappedPlayerEntity();

        if (!this.world.isRemote) {
            if (trapped != null) {
            	if (trapped instanceof MobEntity) {
            		// Entity has escaped
	                if (!trapped.getBoundingBox().intersects(new AxisAlignedBB(this.pos)) || !trapped.isAlive()) {
	                    this.setTrappedEntity(null);
	                }
            	}
                
            }
            
            if (trappedPlayer != null) {
            	if (trappedPlayer instanceof PlayerEntity) {
            		// Player has escaped
            		if (!trappedPlayer.getBoundingBox().intersects(new AxisAlignedBB(this.pos)) || !trappedPlayer.isAlive()) {
            			this.setTrappedPlayerEntity(null);
	                    currentlyUnpressingKeys = false;
	
	                } else  {
	                	currentlyUnpressingKeys = true;
	                	if (trappedPlayer.getPosition() != previousPlayerPos) {
	                		trappedPlayer.attackEntityFrom(damageSource, 2);
	                	}
	                }
            	}
            }
            
            if (currentlyUnpressingKeys) {
            	//KeyBinding.unPressAllKeys();
            	previousPlayerPos = trappedPlayer.getPosition();
            	mc.gameSettings.keyBindJump.setPressed(false);
            	mc.gameSettings.keyBindForward.setPressed(false);
            	mc.gameSettings.keyBindLeft.setPressed(false);
            	mc.gameSettings.keyBindRight.setPressed(false);
            	mc.gameSettings.keyBindBack.setPressed(false);
            }
        }
    }

    class DoNothingGoal extends Goal {
        private MobEntity trappedEntity;
        private BearTrapTileEntity trap;
        public DoNothingGoal(MobEntity trappedEntity, BearTrapTileEntity trap) {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
            this.trappedEntity = trappedEntity;
            this.trap = trap;
        }

        @Override
        public boolean shouldExecute() {
            return this.trap.isEntityTrapped(this.trappedEntity);
        }
     }



    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.hasUniqueId("trapped_entity")) {
            this.id = nbt.getUniqueId("trapped_entity");
        }
    }


    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (this.entityliving != null && this.entityliving.isAlive()) {
            compound.putUniqueId("trapped_entity", this.entityliving.getUniqueID());
        }
        if (this.entitylivingPlayer != null && this.entitylivingPlayer.isAlive()) {
            compound.putUniqueId("trapped_entity", this.entitylivingPlayer.getUniqueID());
        }
        return compound;
    }

    public boolean setTrappedEntity(@Nullable MobEntity livingEntity) {
        if (this.hasTrappedEntity() && livingEntity != null) {
            return false;
        } else {

            if (livingEntity == null) {
                if (this.entityliving != null) {
                    this.entityliving.goalSelector.removeGoal(this.doNothingGoal);
                }
                this.id = null;
                this.doNothingGoal = null;
            } else {
            	livingEntity.attackEntityFrom(damageSource, 2);
                livingEntity.goalSelector.getRunningGoals().filter(PrioritizedGoal::isRunning).forEach(PrioritizedGoal::resetTask);
                livingEntity.goalSelector.addGoal(0, this.doNothingGoal = new DoNothingGoal(livingEntity, this));
            }

            this.entityliving = livingEntity;
            return true;
        }
    }
    
    public boolean setTrappedPlayerEntity(@Nullable PlayerEntity livingEntity) {
        if (this.hasTrappedPlayerEntity() && livingEntity != null) {
            return false;
        } else {

            if (livingEntity == null) {
                this.id = null;
                currentlyUnpressingKeys = false;
            }

            this.entitylivingPlayer = livingEntity;
            return true;
        }
    }

    public MobEntity getTrappedEntity() {
        if (this.id != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.world).getEntityByUuid(this.id);
            this.id = null;
            if (entity instanceof MobEntity)
                this.setTrappedEntity((MobEntity)entity);
        }
        return this.entityliving;
    }
    
    public PlayerEntity getTrappedPlayerEntity() {
        if (this.id != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.world).getEntityByUuid(this.id);
            this.id = null;
            if (entity instanceof PlayerEntity)
                this.setTrappedPlayerEntity((PlayerEntity)entity);
        }
        return this.entitylivingPlayer;
    }

    public boolean hasTrappedEntity() {
        return this.getTrappedEntity() != null;
    }
    
    public boolean hasTrappedPlayerEntity() {
        return this.getTrappedPlayerEntity() != null;
    }

    public boolean isEntityTrapped(final MobEntity trappedEntity) {
        return this.getTrappedEntity() == trappedEntity;
    }
    
    public boolean isPlayerEntityTrapped(final PlayerEntity trappedEntity) {
        return this.getTrappedPlayerEntity() == trappedEntity;
    }
}
