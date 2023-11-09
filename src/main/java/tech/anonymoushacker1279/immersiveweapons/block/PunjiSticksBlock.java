package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class PunjiSticksBlock extends Block implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

	/**
	 * Constructor for PunjiSticksBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public PunjiSticksBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			if (livingEntity.fallDistance >= 2.5f) {
				int featherFallingLevel = getFeatherFallingLevel(livingEntity);
				float damage = (livingEntity.fallDistance + 10f) *
						(1.25f - (featherFallingLevel <= 4 ? featherFallingLevel * 0.25f : 1.0f));
				livingEntity.hurt(IWDamageSources.PUNJI_STICKS_FALL, damage);
			} else {
				float damage = (float) (livingEntity.getDeltaMovement().dot(new Vec3(1, 1, 1)) / 1.5f) + 2.0f;
				livingEntity.hurt(IWDamageSources.PUNJI_STICKS, damage);
			}

			livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0, false, true));
			livingEntity.makeStuckInBlock(state, new Vec3(0.85F, 0.80D, 0.85F));
		}
	}

	protected int getFeatherFallingLevel(LivingEntity entity) {
		ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);

		if (EnchantmentHelper.getEnchantments(boots).containsKey(Enchantments.FALL_PROTECTION)) {
			return EnchantmentHelper.getEnchantments(boots).getOrDefault(Enchantments.FALL_PROTECTION, 0);
		}

		return 0;
	}
}