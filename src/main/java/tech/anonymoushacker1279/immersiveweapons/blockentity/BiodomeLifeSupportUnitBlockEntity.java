package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;


public class BiodomeLifeSupportUnitBlockEntity extends BlockEntity implements EntityBlock {

	int cooldown = 0;
	boolean isPowered = false;

	public BiodomeLifeSupportUnitBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.BIODOME_LIFE_SUPPORT_UNIT_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BiodomeLifeSupportUnitBlockEntity(pos, state);
	}

	public void tick(Level level, BlockPos pos) {
		if (isPowered && cooldown == 0) {
			cooldown = 300;

			ServerLevel serverLevel = (ServerLevel) level;

			// Get all players in a radius of 5 blocks
			serverLevel.getEntitiesOfClass(Player.class, new AABB(pos).inflate(5), player -> true).forEach(player -> {
				// Grant the Celestial Protection effect
				player.addEffect(new MobEffectInstance(EffectRegistry.CELESTIAL_PROTECTION_EFFECT.get(), 360, 0, true, true));
			});

			// Spawn particles in a circle around the block
			for (int i = 0; i < 360; i += 10) {
				double x = pos.getX() + 0.5d + Math.cos(i);
				double z = pos.getZ() + 0.5d + Math.sin(i);
				serverLevel.sendParticles(ParticleTypes.INSTANT_EFFECT, x, pos.getY() + 0.5, z, 1, 0, 0, 0, 0);
			}
		} else {
			cooldown--;
		}
	}

	public void setPowered(boolean state) {
		isPowered = state;
		setChanged();
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
		setChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);

		pTag.putInt("cooldown", cooldown);
		pTag.putBoolean("isPowered", isPowered);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);

		cooldown = nbt.getInt("cooldown");
		isPowered = nbt.getBoolean("isPowered");
	}
}