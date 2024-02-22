package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.FieldMedicEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class FieldMedicHealEntitiesGoal extends Goal {

	private final FieldMedicEntity medic;
	private final List<Class<? extends LivingEntity>> targetedEntities = List.of(
			MinutemanEntity.class,
			IronGolem.class,
			Villager.class,
			FieldMedicEntity.class
	);

	private final ArrayList<LivingEntity> recentlyHealedEntities = new ArrayList<>(5);
	@Nullable
	private LivingEntity healTarget;

	public FieldMedicHealEntitiesGoal(FieldMedicEntity medic) {
		this.medic = medic;
	}

	@Override
	public boolean canUse() {
		return true;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		super.tick();

		if (medic.tickCount % 40 == 0) {
			scanForHurtEntities();
		}

		if (healTarget != null) {
			goHealEntity(healTarget);
		}

		// Clear the recently healed entities list every 20 seconds
		if (medic.tickCount % 400 == 0) {
			recentlyHealedEntities.clear();
		}
	}

	/**
	 * Scan the nearby area for hurt entities that are in the target list.
	 */
	private void scanForHurtEntities() {
		List<LivingEntity> nearbyEntities = medic.level().getEntitiesOfClass(LivingEntity.class,
				medic.getBoundingBox().inflate(24, 5, 24),
				(entity) -> {
					if (targetedEntities.contains(entity.getClass())) {
						if (entity.getHealth() < entity.getMaxHealth()) {
							return !recentlyHealedEntities.contains(entity);
						}
					}

					return false;
				});

		if (!nearbyEntities.isEmpty()) {
			if (healTarget == null) {
				healTarget = nearbyEntities.get(0);
			}
		} else {
			healTarget = null;
		}
	}

	/**
	 * Navigate to the target entity and heal them.
	 *
	 * @param entity the <code>LivingEntity</code> to heal
	 */
	private void goHealEntity(LivingEntity entity) {
		medic.getNavigation().moveTo(entity, 1.0D);

		if (medic.distanceTo(entity) <= 1.5D && entity.hasLineOfSight(entity)) {
			// If below half health, use a first aid kit, otherwise a bandage
			if (entity.getHealth() <= entity.getMaxHealth() / 2) {
				medic.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.FIRST_AID_KIT.get()));
				ItemRegistry.FIRST_AID_KIT.get().setEffects(entity);
			} else {
				medic.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.BANDAGE.get()));
				ItemRegistry.BANDAGE.get().setEffects(entity);
			}

			recentlyHealedEntities.add(entity);
			healTarget = null;
		}
	}
}