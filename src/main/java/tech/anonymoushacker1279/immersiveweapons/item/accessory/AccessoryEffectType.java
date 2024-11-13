package tech.anonymoushacker1279.immersiveweapons.item.accessory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record AccessoryEffectType(String name, boolean clamp) {

	public static final Codec<AccessoryEffectType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("name").forGetter(AccessoryEffectType::name),
			Codec.BOOL.fieldOf("clamp").forGetter(AccessoryEffectType::clamp)
	).apply(instance, AccessoryEffectType::new));

	public AccessoryEffectType(ResourceLocation name) {
		this(name.getPath(), false);
	}

	public AccessoryEffectType(ResourceLocation name, boolean clamp) {
		this(name.getPath(), clamp);
	}

	public String createTranslation() {
		return "tooltip.immersiveweapons.accessory.effect_type." + name;
	}
}