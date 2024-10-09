package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.HurtByTargetWithPredicateGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.*;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;

import java.util.List;

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

		targetSelector.addGoal(1, new HurtByTargetWithPredicateGoal(this, this::canTargetEntityWhenHurt, DyingSoldierEntity.class).setAlertOthers(DyingSoldierEntity.class));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, this::canTargetPlayer));
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
	protected AbstractGunItem getDefaultGunItem() {
		return ItemRegistry.FLINTLOCK_PISTOL.get();
	}

	@Override
	protected BulletItem<?> getDefaultBulletItem() {
		return ItemRegistry.IRON_MUSKET_BALL.get();
	}

	@Override
	protected int getAttackIntervalModifier(Difficulty difficulty) {
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
	protected AccessoryItem getAggroAccessory() {
		return ItemRegistry.MEDAL_OF_HONOR.get();
	}

	@Override
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
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

	@Override
	protected boolean canTargetPlayer(LivingEntity entity) {
		if (entity instanceof Player player) {
			if (AccessoryItem.isAccessoryActive(player, getPeaceAccessory())) {
				return false;
			}

			return !player.isCreative() || AccessoryItem.isAccessoryActive(player, getAggroAccessory());
		}

		return false;
	}
}