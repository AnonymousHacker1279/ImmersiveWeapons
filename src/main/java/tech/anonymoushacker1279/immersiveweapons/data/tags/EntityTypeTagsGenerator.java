package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagsGenerator extends EntityTypeTagsProvider {

	public EntityTypeTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		tag(EntityTypes.BOSSES).add(EntityRegistry.CELESTIAL_TOWER_ENTITY.get(),
				EntityRegistry.SUPER_HANS_ENTITY.get());
	}
}