package tech.anonymoushacker1279.immersiveweapons.item.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.*;

import java.util.List;
import java.util.function.Predicate;

public class CustomBoatItem extends Item {
	private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
	private final CustomBoatType type;
	private final boolean hasChest;

	public CustomBoatItem(CustomBoatType type, Item.Properties properties, boolean hasChest) {
		super(properties);
		this.type = type;
		this.hasChest = hasChest;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(itemStack);
		} else {
			Vec3 viewVector = player.getViewVector(1.0F);
			List<Entity> entities = level.getEntities(player, player.getBoundingBox().expandTowards(viewVector.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
			if (!entities.isEmpty()) {
				Vec3 eyePosition = player.getEyePosition();

				for (Entity entity : entities) {
					AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
					if (aabb.contains(eyePosition)) {
						return InteractionResultHolder.pass(itemStack);
					}
				}
			}

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				BurnedOakBoatEntity boat = getBoat(level, hitResult);
				boat.setBoatType(type);
				boat.setYRot(player.getYRot());
				if (!level.noCollision(boat, boat.getBoundingBox().inflate(-0.1D))) {
					return InteractionResultHolder.fail(itemStack);
				} else {
					if (!level.isClientSide) {
						level.addFreshEntity(boat);
						level.gameEvent(player, GameEvent.ENTITY_PLACE, new BlockPos(hitResult.getLocation()));
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
					}

					player.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
				}
			} else {
				return InteractionResultHolder.pass(itemStack);
			}
		}
	}

	private BurnedOakBoatEntity getBoat(Level level, HitResult hitResult) {
		return hasChest
				? new BurnedOakChestBoat(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z)
				: new BurnedOakBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
	}
}