package tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import tech.anonymoushacker1279.immersiveweapons.event.ClientDataHandler;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectType;

public enum AccessoryEffectScalingType implements StringRepresentable {
	DEPTH("depth"),
	INSOMNIA("insomnia");

	private final String name;

	AccessoryEffectScalingType(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}

	public static final Codec<AccessoryEffectScalingType> CODEC = StringRepresentable.fromEnum(AccessoryEffectScalingType::values);
	public static final StreamCodec<FriendlyByteBuf, AccessoryEffectScalingType> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(AccessoryEffectScalingType.class);

	public String createTranslation() {
		return "tooltip.immersiveweapons.accessory.effect_scaling_type." + name;
	}

	public double getEffectValue(Accessory accessory, AccessoryEffectType type, Player player) {
		return switch (this) {
			case DEPTH -> handleDepthScaling(accessory, type, player);
			case INSOMNIA -> handleInsomniaScaling(accessory, type, player);
		};
	}

	private double handleDepthScaling(Accessory accessory, AccessoryEffectType type, Player player) {
		double depth = player.getY();
		double baseValue = accessory.getBaseEffectValue(type);
		if (depth < 64) {
			int worldFloor = player.level().getMinY();

			// The scaling is inverse proportionally to the player's depth
			double depthScaling = Mth.clamp(Math.min(1.0, ((64 - depth) / (64 - worldFloor))) * 100, 0, 100);
			return baseValue * depthScaling;
		}

		return 0.0d;
	}

	private double handleInsomniaScaling(Accessory accessory, AccessoryEffectType type, Player player) {
		int timeSinceRest;
		double baseValue = accessory.getBaseEffectValue(type);
		if (player instanceof ServerPlayer serverPlayer) {
			timeSinceRest = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
		} else {
			timeSinceRest = ClientDataHandler.getTicksSinceRest();
		}

		if (timeSinceRest > 24000) {
			// Scaling should max out after 7 in-game days and nights (168,000 ticks)
			double insomniaScaling = Mth.clamp(Math.min(1.0, ((timeSinceRest - 24000) / 144000.0)) * 100, 0, 100);
			return baseValue * insomniaScaling;
		}

		return 0.0d;
	}
}