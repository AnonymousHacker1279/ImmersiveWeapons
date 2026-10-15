package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.VillagerRegistry;

import java.util.concurrent.CompletableFuture;

public class PoiTypeTagsGenerator extends PoiTypeTagsProvider {

	public PoiTypeTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(VillagerRegistry.GUNSMITH_POI.key());
	}
}
