package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.function.Supplier;

public class DeathweedBlock extends FlowerBlock {

	public static final DamageSource DAMAGE_SOURCE = new DamageSource("immersiveweapons.deathweed")
			.bypassArmor().setMagic();

	public DeathweedBlock(Supplier<MobEffect> mobEffect, int effectDuration, Properties properties) {
		super(mobEffect, effectDuration, properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(BlockRegistry.BLOOD_SAND.get());
	}

	@Override
	public PlantType getPlantType(BlockGetter level, BlockPos pos) {
		return PlantType.DESERT;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			if (!level.isClientSide) {
				float chance = GeneralUtilities.getRandomNumber(0.0f, 1.0f);
				if (livingEntity.tickCount % 8 == 0 && chance <= 0.65f) {
					livingEntity.hurt(DAMAGE_SOURCE, 1.0f);

					if (chance <= 0.25f) {
						livingEntity.addEffect(new MobEffectInstance(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT.get(), 200));
					}
				}
			}
		}
	}
}