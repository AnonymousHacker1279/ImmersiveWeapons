package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.tags.GameEventTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.GameEventRegistry;

import java.util.concurrent.CompletableFuture;

public class GameEventTagsGenerator extends GameEventTagsProvider {

	public GameEventTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider pProvider) {
		tag(GameEventTags.VIBRATIONS).add(
				GameEventRegistry.FLASHBANG_EXPLODE.get(),
				GameEventRegistry.SMOKE_GRENADE_HISS.get(),
				GameEventRegistry.PANIC_ALARM_TRIGGER.get());

		tag(GameEventTags.IGNORE_VIBRATIONS_SNEAKING).add(
				GameEventRegistry.FLASHBANG_EXPLODE.get(),
				GameEventRegistry.SMOKE_GRENADE_HISS.get());
	}
}