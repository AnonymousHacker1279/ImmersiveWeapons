package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.ambient.WispEntity;

public class WispSpawnEggItem extends SpawnEggItem {

	private final int type;

	public WispSpawnEggItem(Properties properties, int type) {
		super(properties);
		this.type = type;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		if (!(level instanceof ServerLevel serverLevel)) {
			return InteractionResult.SUCCESS;
		} else {
			ItemStack itemStack = context.getItemInHand();
			BlockPos pos = context.getClickedPos();
			Direction clickedFace = context.getClickedFace();
			BlockState blockState = level.getBlockState(pos);
			if (level.getBlockEntity(pos) instanceof Spawner spawnerHolder) {
				EntityType<?> type = getType(itemStack);
				if (type == null) {
					return InteractionResult.FAIL;
				} else if (!serverLevel.isSpawnerBlockEnabled()) {
					if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
						serverPlayer.sendSystemMessage(Component.translatable("advMode.notEnabled.spawner"));
					}

					return InteractionResult.FAIL;
				} else {
					spawnerHolder.setEntityId(type, level.getRandom());
					level.sendBlockUpdated(pos, blockState, blockState, 3);
					level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, pos);
					itemStack.shrink(1);
					return InteractionResult.SUCCESS;
				}
			} else {
				BlockPos spawnPos;
				if (blockState.getCollisionShape(level, pos).isEmpty()) {
					spawnPos = pos;
				} else {
					spawnPos = pos.relative(clickedFace);
				}

				return spawn(context.getPlayer(), itemStack, level, spawnPos);
			}
		}
	}

	private InteractionResult spawn(@Nullable LivingEntity source, ItemStack stack, Level level, BlockPos pos) {
		WispEntity.create(level, pos, type);
		stack.consume(1, source);

		if (source instanceof Player player && !player.isCreative()) {
			player.addItem(Items.GLASS_BOTTLE.getDefaultInstance());
		}

		level.gameEvent(source, GameEvent.ENTITY_PLACE, pos);

		return InteractionResult.SUCCESS;
	}
}