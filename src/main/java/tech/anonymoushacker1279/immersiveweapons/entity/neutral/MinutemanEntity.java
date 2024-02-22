package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.*;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.DyingSoldierEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

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
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D, 0.35f));
		goalSelector.addGoal(4, new MoveBackToVillageGoal(this, 0.65D, false));
		goalSelector.addGoal(4, new MoveThroughVillageGoal(this, 1.0D, false, 6, () -> true));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		goalSelector.addGoal(4, new OpenDoorGoal(this, true));

		targetSelector.addGoal(1, new HurtByTargetWithPredicateGoal(this, (initialPredicate) ->
				!(initialPredicate instanceof Player player) || !AccessoryItem.isAccessoryActive(player, getPeaceAccessory()), MinutemanEntity.class, IronGolem.class)
				.setAlertOthers());
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, (targetPredicate) -> {
			if (targetPredicate instanceof Player player) {
				return !player.isCreative()
						&& (isAngryAt(player) || AccessoryItem.isAccessoryActive(player, ItemRegistry.MEDAL_OF_DISHONOR.get()))
						&& !AccessoryItem.isAccessoryActive(player, getPeaceAccessory());
			}

			return false;
		}));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true, (targetPredicate) -> !(targetPredicate instanceof Creeper)));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, DyingSoldierEntity.class, true));
		targetSelector.addGoal(4, new DefendVillageTargetGoal(this));
		targetSelector.addGoal(6, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		// Fire four bullets for the blunderbuss
		for (int i = 0; i <= 4; i++) {
			BulletEntity bulletEntity = ItemRegistry.COPPER_MUSKET_BALL.get().createBullet(level(), this);
			bulletEntity.setEnchantmentEffectsFromEntity(this, velocity);

			double deltaX = target.getX() - getX();
			double deltaY = target.getY(0.1D) - bulletEntity.getY();
			double deltaZ = target.getZ() - getZ();
			double sqrtXZ = Mth.sqrt((float) (deltaX * deltaX + deltaZ * deltaZ));

			bulletEntity.setKnockback(3);
			bulletEntity.setOwner(this);

			bulletEntity.shoot(
					deltaX + GeneralUtilities.getRandomNumber(-1.0f, 1.0f),
					deltaY + sqrtXZ * 0.2D + GeneralUtilities.getRandomNumber(-0.375f, 0.375f),
					deltaZ, getGunItem().getFireVelocity(getGun(), AbstractGunItem.getPowderFromItem(ItemRegistry.BLACKPOWDER.get()).getVelocityModifier()),
					18 - level().getDifficulty().getId() * 4 + GeneralUtilities.getRandomNumber(0.2f, 0.8f));
			level().addFreshEntity(bulletEntity);
		}

		playSound(SoundEventRegistry.BLUNDERBUSS_FIRE.get(), 1.0F,
				1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
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
	protected AbstractGunItem getGunItem() {
		return ItemRegistry.BLUNDERBUSS.get();
	}

	@Override
	protected int getAttackInterval(Difficulty difficulty) {
		return switch (level().getDifficulty()) {
			case NORMAL -> 50;
			case HARD -> 40;
			default -> 70;
		};
	}

	@Override
	protected AccessoryItem getPeaceAccessory() {
		return ItemRegistry.MEDAL_OF_HONOR.get();
	}
}