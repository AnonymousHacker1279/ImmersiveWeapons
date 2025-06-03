package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
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

	public WoodenSpikesBlock(Properties properties) {
		super(properties, 96, 3, Items.STICK, DAMAGE_STAGE);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(DAMAGE_STAGE, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING, DAMAGE_STAGE);
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
		if (entity instanceof LivingEntity livingEntity) {
			entity.makeStuckInBlock(state, new Vec3(0.85F, 0.80D, 0.85F));
			if (level instanceof ServerLevel serverLevel) {
				Vec3 movement = entity.getKnownMovement();
				if (movement.x >= 0.001F || movement.z >= 0.001F) {
					if (entity instanceof Player player && player.isCreative()) {
						return;
					}

					if (level.getBlockEntity(pos) instanceof DamageableBlockEntity damageable) {
						if (level.getGameTime() % 10 == 0) {
							damageable.takeDamage(state, level, pos, DAMAGE_STAGE);
							entity.hurtServer(serverLevel,
									IWDamageSources.woodenSpikes(level.registryAccess()),
									damageable.calculateDamage(1.5f, 0.33f));

							if (livingEntity.getRandom().nextFloat() <= 0.15f) {
								livingEntity.addEffect(new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT,
										200, 0, true, true));
							}
						}
					}
				}
			}

			if (entity instanceof Player player && player.getRandom().nextFloat() <= 0.2f) {
				level.playSound(player, pos, SoundEvents.WOOD_HIT, SoundSource.BLOCKS, 1f, player.getRandom().nextFloat() * 0.2f + 0.9f);
			}
		}
	}
}