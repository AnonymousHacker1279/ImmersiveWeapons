package com.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class PunjiSticksBlock extends Block implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	private DamageSource damageSource = new DamageSource("immersiveweapons.punji_sticks");
    
	public PunjiSticksBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.FALSE));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d vector3d = state.getOffset(worldIn, pos);
		return SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	 public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		 if (stateIn.get(WATERLOGGED)) {
			 worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		 }

		 return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	 }

	 @Override
	 public BlockState getStateForPlacement(BlockItemUseContext context) {
		 FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
	        
		 return this.getDefaultState().with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	 }

	 @SuppressWarnings("deprecation")
	 @Override
	 public FluidState getFluidState(BlockState state) {
		 return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	 }
	 
	 @Override
	 protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		 builder.add(WATERLOGGED);
	 }

	 @Override
	 public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		 return Block.hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
	 }

	 @Override
	 public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		 if (entity instanceof ItemEntity) {
			 return;
		 } else if (entity instanceof ExperienceOrbEntity) {
			 return;
		 }

		 if (entity.fallDistance >= 5F) {
			 entity.attackEntityFrom(damageSource, entity.fallDistance >= 10F ? entity.fallDistance + 20F * 0.5f : 20F);
			 ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.POISON, 60, 0, false, false));
			 return;
		 }
		 else {
			 final float damageTodo = (float)entity.getMotion().dotProduct(new Vector3d(1, 1, 1)) / 1.5F;
			 entity.attackEntityFrom(damageSource, 2F + damageTodo);
			 ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.POISON, 60, 0, false, false));
			 return;
		 }
	 }
}
