package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.tags.GameEventTags;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.GameEventRegistry;

import java.util.concurrent.CompletableFuture;

public class GameEventTagsGenerator extends GameEventTagsProvider {

	public GameEventTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void addTags(Provider pProvider) {
		tag(GameEventTags.VIBRATIONS).add(
				GameEventRegistry.FLASHBANG_EXPLODE.key(),
				GameEventRegistry.SMOKE_GRENADE_HISS.key(),
				GameEventRegistry.PANIC_ALARM_TRIGGER.key());

		tag(GameEventTags.IGNORE_VIBRATIONS_SNEAKING).add(
				GameEventRegistry.FLASHBANG_EXPLODE.key(),
				GameEventRegistry.SMOKE_GRENADE_HISS.key());
	}
}