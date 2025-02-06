package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import tech.anonymoushacker1279.immersiveweapons.item.armor.ArmorUtils;

import java.util.Optional;
import java.util.function.Function;

@Mixin(WindCharge.class)
public abstract class WindChargeMixin {

	@Unique
	private static final ExplosionDamageCalculator VENTUS_EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(
			true, false, Optional.of(2.44F), BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
	);

	@WrapMethod(method = "explode")
	private void modifyExplode(Vec3 pos, Operation<Void> original) {
		Entity self = (Entity) (Object) this;

		if (self instanceof WindCharge charge) {
			if (charge.getOwner() instanceof LivingEntity livingEntity) {
				if (ArmorUtils.isWearingVentusArmor(livingEntity)) {
					charge.level()
							.explode(
									charge,
									null,
									VENTUS_EXPLOSION_DAMAGE_CALCULATOR,
									pos.x(),
									pos.y(),
									pos.z(),
									2.4F,
									false,
									Level.ExplosionInteraction.TRIGGER,
									ParticleTypes.GUST_EMITTER_SMALL,
									ParticleTypes.GUST_EMITTER_LARGE,
									SoundEvents.WIND_CHARGE_BURST
							);

					return;
				}
			}
		}

		original.call(pos);
	}
}