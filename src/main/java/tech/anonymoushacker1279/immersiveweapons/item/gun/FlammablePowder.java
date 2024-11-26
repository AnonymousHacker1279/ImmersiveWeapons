package tech.anonymoushacker1279.immersiveweapons.item.gun;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FlammablePowder(float consumeChance, float velocityModifier, int weaponDamageAmount, int dirtiness,
                              boolean hasFlameTrail) {

	public FlammablePowder(float consumeChance, float velocityModifier, int weaponDamageAmount, int dirtiness) {
		this(consumeChance, velocityModifier, weaponDamageAmount, dirtiness, false);
	}

	public static final Codec<FlammablePowder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("consume_chance").forGetter(FlammablePowder::consumeChance),
			Codec.FLOAT.fieldOf("velocity_modifier").forGetter(FlammablePowder::velocityModifier),
			Codec.INT.fieldOf("weapon_damage_amount").forGetter(FlammablePowder::weaponDamageAmount),
			Codec.INT.fieldOf("dirtiness").forGetter(FlammablePowder::dirtiness),
			Codec.BOOL.fieldOf("has_flame_trail").forGetter(FlammablePowder::hasFlameTrail)
	).apply(instance, FlammablePowder::new));
}