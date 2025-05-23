package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;

import java.util.concurrent.CompletableFuture;

public class EnchantmentTagsGenerator extends EnchantmentTagsProvider {

	public EnchantmentTagsGenerator(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider, ImmersiveWeapons.MOD_ID);
	}

	@Override
	protected void addTags(Provider provider) {
		tag(EnchantmentTags.NON_TREASURE).add(
				IWEnchantments.FIREPOWER,
				IWEnchantments.IMPACT,
				IWEnchantments.ENDLESS_MUSKET_POUCH,
				IWEnchantments.SCORCH_SHOT,
				IWEnchantments.RAPID_FIRE,
				IWEnchantments.VELOCITY,
				IWEnchantments.EXTENDED_REACH,
				IWEnchantments.SHARPENED_HEAD,
				IWEnchantments.CRIMSON_CLAW,
				IWEnchantments.EXCESSIVE_FORCE,
				IWEnchantments.REGENERATIVE_ASSAULT,
				IWEnchantments.HEAVY_COMET,
				IWEnchantments.BURNING_HEAT,
				IWEnchantments.CELESTIAL_FURY,
				IWEnchantments.NIGHTMARISH_STARE,
				IWEnchantments.MALEVOLENT_GAZE
		);
	}
}