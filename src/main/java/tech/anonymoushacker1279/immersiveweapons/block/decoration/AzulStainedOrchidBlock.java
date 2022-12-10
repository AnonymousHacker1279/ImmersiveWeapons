package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AzulStainedOrchidBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;

public class AzulStainedOrchidBlock extends FlowerBlock implements EntityBlock {

	public AzulStainedOrchidBlock(MobEffect mobEffect, int effectDuration, Properties properties) {
		super(mobEffect, effectDuration, properties);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (CommonConfig.TILTROS_ENABLED.get()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof AzulStainedOrchidBlockEntity) {
				((AzulStainedOrchidBlockEntity) blockEntity).entityInside(pos, entity);
			}
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AzulStainedOrchidBlockEntity(pos, state);
	}
}