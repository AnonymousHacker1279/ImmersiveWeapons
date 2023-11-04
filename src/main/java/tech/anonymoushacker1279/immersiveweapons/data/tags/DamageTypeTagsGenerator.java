package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagsGenerator extends TagsProvider<DamageType> {

	public DamageTypeTagsGenerator(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper existingFileHelper) {
		super(output, Registries.DAMAGE_TYPE, provider, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		tag(DamageTypeTags.BYPASSES_ARMOR)
				.add(IWDamageTypes.BLEEDING_KEY)
				.add(IWDamageTypes.DEATHWEED_KEY)
				.add(IWDamageTypes.DEADMANS_DESERT_ATMOSPHERE_KEY)
				.add(IWDamageTypes.PUNJI_STICKS_FALL_KEY);

		tag(DamageTypeTags.IS_EXPLOSION)
				.add(IWDamageTypes.EXPLOSIVE_CHOCOLATE_BAR_KEY)
				.add(IWDamageTypes.EXPLOSIVE_CANNONBALL_KEY)
				.add(IWDamageTypes.EXPLOSIVE_ARROW_KEY)
				.add(IWDamageTypes.MORTAR_KEY)
				.add(IWDamageTypes.LANDMINE_KEY)
				.add(IWDamageTypes.METEOR_KEY);

		tag(DamageTypeTags.IS_PROJECTILE)
				.add(IWDamageTypes.BULLET_KEY)
				.add(IWDamageTypes.CANNONBALL_KEY)
				.add(IWDamageTypes.EXPLOSIVE_CANNONBALL_KEY)
				.add(IWDamageTypes.EXPLOSIVE_ARROW_KEY)
				.add(IWDamageTypes.MORTAR_KEY);

		tag(DamageTypeTags.BYPASSES_ENCHANTMENTS)
				.add(IWDamageTypes.BLEEDING_KEY);
	}
}