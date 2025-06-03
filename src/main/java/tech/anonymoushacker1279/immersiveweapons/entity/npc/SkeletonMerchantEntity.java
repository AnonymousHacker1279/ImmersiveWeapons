package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class SkeletonMerchantEntity extends AbstractMerchantEntity {

	public SkeletonMerchantEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected Item getSpawnEgg() {
		return ItemRegistry.SKELETON_MERCHANT_SPAWN_EGG.get();
	}

	@Override
	protected SoundEvent getTradeUpdatedSound(boolean isYesSound) {
		return SoundEvents.SKELETON_AMBIENT;
	}
}