package tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWEntityTypeTagGroups {

	public static final TagKey<EntityType<?>> MUSKET_BALLS = createEntityTypeTag("musket_balls");

	private static TagKey<EntityType<?>> createEntityTypeTag(String tag) {
		return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, tag));
	}
}