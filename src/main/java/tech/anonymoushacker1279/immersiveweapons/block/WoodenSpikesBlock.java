package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.block.core.DamageableBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.DamageableBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class WoodenSpikesBlock extends DamageableBlock {

	protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15D, 14D, 15D);
	public static final IntegerProperty DAMAGE_STAGE = IntegerProperty.create("damage_stage", 0, 3);

	/**
	 * Constructor for WoodenSpikesBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public WoodenSpikesBlock(Properties properties) {
		super(properties, 96, 3, Items.STICK, DAMAGE_STAGE);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(DAMAGE_STAGE, 0));
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING, DAMAGE_STAGE);
	}

	/**
	 * Runs when an entity is inside the block's collision area.
	 * Allows the block to deal damage on contact.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param level  the <code>Level</code> the block is in
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @param entity the <code>Entity</code> passing through the block
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			entity.makeStuckInBlock(state, new Vec3(0.85F, 0.80D, 0.85F));
			if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double deltaX = Math.abs(entity.getX() - entity.xOld);
				double deltaZ = Math.abs(entity.getZ() - entity.zOld);
				if (deltaX >= 0.003F || deltaZ >= 0.003F) {
					if (entity instanceof Player player && player.isCreative()) {
						return;
					}

					if (level.getBlockEntity(pos) instanceof DamageableBlockEntity damageable && level.getGameTime() % 10 == 0) {
						entity.hurt(IWDamageSources.WOODEN_SPIKES, damageable.calculateDamage(1.5f, 0.33f));

						if (livingEntity.getRandom().nextFloat() <= 0.15f) {
							livingEntity.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT.get(),
									200, 0, true, false));
						}

						damageable.takeDamage(state, level, pos, DAMAGE_STAGE);
					}
				}
			}
		}
	}
}