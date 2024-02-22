package tech.anonymoushacker1279.immersiveweapons.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class HurtByTargetWithPredicateGoal extends HurtByTargetGoal {

	private final Predicate<LivingEntity> initialPredicate;

	public HurtByTargetWithPredicateGoal(PathfinderMob pMob, Predicate<LivingEntity> initialPredicate, Class<?>... pToIgnoreDamage) {
		super(pMob, pToIgnoreDamage);
		this.initialPredicate = initialPredicate;
	}

	@Override
	protected boolean canAttack(@Nullable LivingEntity pPotentialTarget, TargetingConditions pTargetPredicate) {
		if (!initialPredicate.test(pPotentialTarget)) {
			return false;
		}

		return super.canAttack(pPotentialTarget, pTargetPredicate);
	}
}