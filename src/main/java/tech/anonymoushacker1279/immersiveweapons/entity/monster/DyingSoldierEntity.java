package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.HurtByTargetWithPredicateGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.*;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

import java.util.List;

import static net.minecraft.world.entity.monster.Monster.isDarkEnoughToSpawn;

public class DyingSoldierEntity extends RangedSoldierEntity {

	private final RangedGunAttackGoal<DyingSoldierEntity> rangedGunAttackGoal =
			new RangedGunAttackGoal<>(this, 1.0D, 20, 18.0F);

	public DyingSoldierEntity(EntityType<? extends DyingSoldierEntity> type, Level level) {
		super(type, level, List.of(DyingSoldierEntity.class));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.27D)
				.add(Attributes.ARMOR, 5.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, false,
				6, () -> true));

		targetSelector.addGoal(1, new HurtByTargetWithPredicateGoal(this, (initialPredicate) ->
				!(initialPredicate instanceof Player player) || !AccessoryItem.isAccessoryActive(player, ItemRegistry.MEDAL_OF_DISHONOR.get()), DyingSoldierEntity.class)
				.setAlertOthers(DyingSoldierEntity.class));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, (targetPredicate) ->
				!AccessoryItem.isAccessoryActive((Player) targetPredicate, ItemRegistry.MEDAL_OF_DISHONOR.get())));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MinutemanEntity.class, true));
		targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, FieldMedicEntity.class, true));
		targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Villager.class, false));
	}

	@Override
	public void aiStep() {
		super.aiStep();
		updateNoActionTime();
	}

	@Override
	public RangedGunAttackGoal<DyingSoldierEntity> getRangedGunAttackGoal() {
		return rangedGunAttackGoal;
	}

	@Override
	protected AbstractGunItem getGunItem() {
		return ItemRegistry.FLINTLOCK_PISTOL.get();
	}

	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		BulletEntity bulletEntity = ItemRegistry.IRON_MUSKET_BALL.get().createBullet(level(), this);
		bulletEntity.setEnchantmentEffectsFromEntity(this, velocity);

		double deltaX = target.getX() - getX();
		double deltaY = target.getY(0.1D) - bulletEntity.getY();
		double deltaZ = target.getZ() - getZ();
		double sqrtXZ = Mth.sqrt((float) (deltaX * deltaX + deltaZ * deltaZ));

		bulletEntity.setOwner(this);

		bulletEntity.shoot(
				deltaX,
				deltaY + sqrtXZ * 0.2D,
				deltaZ, getGunItem().getFireVelocity(getGun(), AbstractGunItem.getPowderFromItem(ItemRegistry.BLACKPOWDER.get()).getVelocityModifier()),
				(float) (14 - level().getDifficulty().getId() * 4));

		playSound(SoundEventRegistry.FLINTLOCK_PISTOL_FIRE.get(), 1.0F,
				1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));

		level().addFreshEntity(bulletEntity);
	}

	@Override
	protected int getAttackInterval(Difficulty difficulty) {
		return switch (level().getDifficulty()) {
			case NORMAL -> 40;
			case HARD -> 20;
			default -> 50;
		};
	}

	@Override
	protected AccessoryItem getPeaceAccessory() {
		return ItemRegistry.MEDAL_OF_DISHONOR.get();
	}

	@Override
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	@Override
	public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType mobSpawnType) {
		if (pLevel instanceof ServerLevelAccessor serverLevelAccessor) {
			return pLevel.getDifficulty() != Difficulty.PEACEFUL
					&& (MobSpawnType.ignoresLightRequirements(mobSpawnType) || isDarkEnoughToSpawn(serverLevelAccessor, blockPosition(), random))
					&& checkMobSpawnRules(EntityRegistry.DYING_SOLDIER_ENTITY.get(), pLevel, mobSpawnType, blockPosition(), random);
		}

		return false;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader pLevel) {
		return super.checkSpawnObstruction(pLevel) && pLevel.canSeeSky(blockPosition());
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	protected void updateNoActionTime() {
		float magicValue = getLightLevelDependentMagicValue();
		if (magicValue > 0.5F) {
			this.noActionTime += 2;
		}
	}
}