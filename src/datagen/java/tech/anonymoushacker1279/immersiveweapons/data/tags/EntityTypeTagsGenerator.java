package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.CustomDataGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWEntityTypeTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagsGenerator extends EntityTypeTagsProvider {

	public EntityTypeTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		for (EntityType<?> entity : CustomDataGenerator.ALL_ENTITIES) {
			if (entity.getDescriptionId().contains("musket_ball")) {
				tag(IWEntityTypeTagGroups.MUSKET_BALLS).add(entity);
			}
		}

		tag(EntityTypes.BOSSES).add(EntityRegistry.CELESTIAL_TOWER_ENTITY.get(),
				EntityRegistry.SUPER_HANS_ENTITY.get(),
				EntityRegistry.THE_COMMANDER_ENTITY.get());
	}
}