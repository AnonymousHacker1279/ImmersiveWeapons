package tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectType;

public class AccessoryEffectScalingType {

	private final String name;

	public static final Codec<AccessoryEffectScalingType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("name").forGetter(AccessoryEffectScalingType::getName)
	).apply(instance, AccessoryEffectScalingType::new));

	public AccessoryEffectScalingType(String name) {
		this.name = name;
	}

	public AccessoryEffectScalingType(ResourceLocation name) {
		this.name = name.getPath();
	}

	public String getName() {
		return name;
	}

	public String createTranslation() {
		return "tooltip.immersiveweapons.accessory.effect_scaling_type." + name;
	}

	public double getEffectValue(Accessory accessory, AccessoryEffectType type, Player player) {
		return switch (name) {
			case "depth" -> handleDepthScaling(accessory, type, player);
			case "insomnia" -> handleInsomniaScaling(accessory, type, player);
			default -> accessory.getEffectValue(type);
		};
	}

	private double handleDepthScaling(Accessory accessory, AccessoryEffectType type, Player player) {
		double depth = player.getY();
		if (depth < 64) {
			double rawValue = accessory.getEffectValue(type);
			int worldFloor = player.level().getMinY();

			// The scaling is inverse proportionally to the player's depth
			double depthScaling = Mth.clamp(Math.min(1.0, ((64 - depth) / (64 - worldFloor))) * 100, 0, 100);
			return rawValue * depthScaling;
		}

		return accessory.getEffectValue(type);
	}

	private double handleInsomniaScaling(Accessory accessory, AccessoryEffectType type, Player player) {
		int timeSinceRest;
		if (player instanceof ServerPlayer serverPlayer) {
			timeSinceRest = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
		} else {
			// If this is on the client, use the debug tracing data. The result here does not need to be accurate
			// since it will only ever be used in the debug overlay
			timeSinceRest = DebugTracingData.TICKS_SINCE_REST;
		}

		if (timeSinceRest > 24000) {
			double rawValue = accessory.getEffectValue(type);
			// Scaling should max out after 7 in-game days and nights (168000 ticks)
			double insomniaScaling = Mth.clamp(Math.min(1.0, ((timeSinceRest - 24000) / 144000.0)) * 100, 0, 100);
			return rawValue * insomniaScaling;
		}

		return accessory.getEffectValue(type);
	}
}