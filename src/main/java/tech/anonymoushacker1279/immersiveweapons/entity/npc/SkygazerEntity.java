package tech.anonymoushacker1279.immersiveweapons.entity.npc;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.StarmiteEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.Objects;

public class SkygazerEntity extends AbstractMerchantEntity {

	public SkygazerEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected Item getSpawnEgg() {
		return ItemRegistry.SKYGAZER_SPAWN_EGG.get();
	}

	@Override
	public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
		// Give the attacker blindness and summon 2-3 Starmite entities on the attacker
		if (source.getDirectEntity() instanceof LivingEntity attacker) {
			if (TargetingConditions.forCombat().test(serverLevel, this, attacker)) {
				attacker.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 250, 0));

				for (int i = 0; i < getRandom().nextIntBetweenInclusive(2, 3); i++) {
					StarmiteEntity starmite = new StarmiteEntity(EntityRegistry.STARMITE_ENTITY.get(), level());
					starmite.moveTo(attacker.getX(), attacker.getY(), attacker.getZ(), attacker.getXRot(), attacker.getYRot());

					// Increase the attack damage
					Objects.requireNonNull(starmite.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(4.0D);

					level().addFreshEntity(starmite);
				}
			}
		}

		return super.hurtServer(serverLevel, source, amount);
	}
}