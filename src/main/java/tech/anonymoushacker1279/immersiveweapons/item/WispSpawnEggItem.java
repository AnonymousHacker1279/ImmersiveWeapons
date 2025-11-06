package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
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
	protected InteractionResult spawnMob(@Nullable LivingEntity source, ItemStack stack, Level level, BlockPos pos, boolean shouldOffsetY, boolean shouldOffsetYMore) {
		WispEntity.create(level, pos, type);
		stack.consume(1, source);

		if (source instanceof Player player && !player.isCreative()) {
			player.addItem(Items.GLASS_BOTTLE.getDefaultInstance());
		}

		level.gameEvent(source, GameEvent.ENTITY_PLACE, pos);

		return InteractionResult.SUCCESS;
	}
}