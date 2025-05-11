package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class DeathweedBlock extends FlowerBlock {

	public DeathweedBlock(Holder<MobEffect> mobEffect, int effectDuration, Properties properties) {
		super(mobEffect, effectDuration, properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(BlockRegistry.BLOOD_SAND.get());
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
		if (entity instanceof LivingEntity livingEntity) {
			if (!level.isClientSide) {
				float chance = livingEntity.getRandom().nextFloat();
				if (livingEntity.tickCount % 8 == 0 && chance <= 0.65f) {
					livingEntity.hurt(IWDamageSources.deathweed(level.registryAccess()), 1.0f);

					if (chance <= 0.25f) {
						livingEntity.addEffect(new MobEffectInstance(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT, 200));
					}
				}
			}
		}
	}
}