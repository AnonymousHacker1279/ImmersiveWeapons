package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.DefendVillageTargetGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.HurtByTargetWithPredicateGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.DyingSoldierEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;

import java.util.List;

public class MinutemanEntity extends RangedSoldierEntity {

	private final RangedGunAttackGoal<MinutemanEntity> rangedGunAttackGoal =
			new RangedGunAttackGoal<>(this, 1.0D, 30, 12.0F);

	public MinutemanEntity(EntityType<? extends MinutemanEntity> type, Level level) {
		super(type, level, List.of(MinutemanEntity.class, IronGolem.class));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.27D)
				.add(Attributes.ARMOR, 4.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		goalSelector.addGoal(4, new MoveBackToVillageGoal(this, 0.65D, false));
		goalSelector.addGoal(4, new MoveThroughVillageGoal(this, 1.0D, false, 6, () -> true));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));

		targetSelector.addGoal(1, new HurtByTargetWithPredicateGoal(this, this::canTargetEntityWhenHurt, MinutemanEntity.class, IronGolem.class).setAlertOthers());
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, (entity, level) -> canTargetPlayer(entity)));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true, (entity, level) -> !(entity instanceof Creeper)));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, DyingSoldierEntity.class, true));
		targetSelector.addGoal(4, new DefendVillageTargetGoal(this));
		targetSelector.addGoal(6, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	@Override
	protected BulletItem<?> getDefaultBulletItem() {
		return ItemRegistry.COPPER_MUSKET_BALL.get();
	}

	public void aggroNearbyMinutemen(AABB aabb, LivingEntity target) {
		List<MinutemanEntity> list = level().getEntitiesOfClass(MinutemanEntity.class,
				aabb.inflate(24.0D, 8.0D, 24.0D));

		for (MinutemanEntity minutemanEntity : list) {
			minutemanEntity.setTarget(target);
			minutemanEntity.setPersistentAngerTarget(target.getUUID());
		}
	}

	@Override
	public RangedGunAttackGoal<MinutemanEntity> getRangedGunAttackGoal() {
		return rangedGunAttackGoal;
	}

	@Override
	protected AbstractGunItem getDefaultGunItem() {
		return ItemRegistry.BLUNDERBUSS.get();
	}

	@Override
	protected int getAttackIntervalModifier(Difficulty difficulty) {
		return switch (level().getDifficulty()) {
			case NORMAL -> 50;
			case HARD -> 40;
			default -> 70;
		};
	}

	@Override
	protected Item getPeaceAccessory() {
		return ItemRegistry.MEDAL_OF_HONOR.get();
	}

	@Override
	protected Item getAggroAccessory() {
		return ItemRegistry.MEDAL_OF_DISHONOR.get();
	}

	@Override
	protected boolean canTargetPlayer(LivingEntity entity) {
		if (entity instanceof Player player) {
			if (Accessory.isAccessoryActive(player, getPeaceAccessory())) {
				return false;
			}

			return !player.isCreative() && Accessory.isAccessoryActive(player, getAggroAccessory());
		}

		return false;
	}
}