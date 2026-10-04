package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWEntityTypeTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagsGenerator extends EntityTypeTagsProvider {

	public EntityTypeTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void addTags(Provider provider) {
		tag(IWEntityTypeTagGroups.MUSKET_BALLS).add(
				EntityRegistry.WOODEN_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.STONE_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.GOLDEN_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.COPPER_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.IRON_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.COBALT_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.DIAMOND_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.NETHERITE_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.MOLTEN_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.TESLA_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.VENTUS_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.ASTRAL_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.STARSTORM_MUSKET_BALL_ENTITY.key(),
				EntityRegistry.VOID_MUSKET_BALL_ENTITY.key());

		tag(EntityTypes.BOSSES).add(EntityRegistry.CELESTIAL_TOWER_ENTITY.key(),
				EntityRegistry.SUPER_HANS_ENTITY.key(),
				EntityRegistry.THE_COMMANDER_ENTITY.key());

		tag(EntityTypeTags.RAIDERS).add(
				EntityRegistry.DYING_SOLDIER_ENTITY.key(),
				EntityRegistry.THE_COMMANDER_ENTITY.key());
	}
}