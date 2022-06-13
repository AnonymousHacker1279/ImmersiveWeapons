package tech.anonymoushacker1279.immersiveweapons.client.integration.patchouli;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.*;

import java.util.HashMap;
import java.util.Optional;

public class EntityStatisticsProcessor implements IComponentProcessor {

	private final Minecraft minecraft = Minecraft.getInstance();

	private EntityType<?> entityType;
	private int entitiesKilled = 0;
	private int deathsByEntity = 0;

	public static HashMap<EntityType<?>, int[]> STATS_DICT = new HashMap<>(10);

	@Override
	public void setup(@NotNull IVariableProvider variables) {
		String entity = variables.get("entity").asString();
		Optional<EntityType<?>> optionalEntityType = EntityType.byString(entity);
		entityType = optionalEntityType.orElse(null);

		if (!STATS_DICT.containsKey(entityType)) {
			STATS_DICT.put(entityType, new int[]{entitiesKilled, deathsByEntity});
		}
	}

	@Override
	public IVariable process(String key) {
		if (key.equals("header")) {
			if (entityType != null) {
				String name = I18n.get(entityType.getDescriptionId());
				if (minecraft.font.width(name) > 72) {
					return IVariable.wrap("Entity Statistics:");
				} else {
					return IVariable.wrap("Statistics: " + name);
				}
			}
		}

		return null;
	}

	@Override
	public void refresh(@NotNull Screen parent, int left, int top) {
		if (minecraft.getConnection() != null && minecraft.player != null) {
			minecraft.getConnection().send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.REQUEST_STATS));
			StatsCounter stats = minecraft.player.getStats();

			if (entityType != null) {
				entitiesKilled = stats.getValue(Stats.ENTITY_KILLED.get(entityType));
				deathsByEntity = stats.getValue(Stats.ENTITY_KILLED_BY.get(entityType));

				if (!STATS_DICT.containsKey(entityType)) {
					STATS_DICT.put(entityType, new int[]{entitiesKilled, deathsByEntity});
				} else {
					STATS_DICT.replace(entityType, new int[]{entitiesKilled, deathsByEntity});
				}
			}
		}
	}
}