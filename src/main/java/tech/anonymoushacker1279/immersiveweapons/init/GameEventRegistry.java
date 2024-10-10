package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class GameEventRegistry {

	// Game Event Register
	public static final DeferredRegister<GameEvent> GAME_EVENTS = DeferredRegister.create(Registries.GAME_EVENT, ImmersiveWeapons.MOD_ID);

	// Game Events
	public static final DeferredHolder<GameEvent, ? extends GameEvent> FLASHBANG_EXPLODE = GAME_EVENTS.register("flashbang_explode", () -> new GameEvent(16));
	public static final DeferredHolder<GameEvent, ? extends GameEvent> SMOKE_GRENADE_HISS = GAME_EVENTS.register("smoke_grenade_hiss", () -> new GameEvent(16));
	public static final DeferredHolder<GameEvent, ? extends GameEvent> PANIC_ALARM_TRIGGER = GAME_EVENTS.register("panic_alarm_trigger", () -> new GameEvent(16));
}