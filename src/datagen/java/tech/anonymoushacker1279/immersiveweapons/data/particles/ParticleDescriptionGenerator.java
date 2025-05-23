package tech.anonymoushacker1279.immersiveweapons.data.particles;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

public class ParticleDescriptionGenerator extends ParticleDescriptionProvider {

	public ParticleDescriptionGenerator(PackOutput output) {
		super(output);
	}

	@Override
	protected void addDescriptions() {
		spriteSet(ParticleTypesRegistry.BLOOD_PARTICLE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "blood/blood"), 4, false);
		spriteSet(ParticleTypesRegistry.BULLET_IMPACT_PARTICLE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bullet_impact/impact"), 4, false);
		spriteSet(ParticleTypesRegistry.DEADMANS_DESERT_AMBIENT_PARTICLE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "deadmans_desert/ambient"), 3, false);
		spriteSet(ParticleTypesRegistry.MOONGLOW_PARTICLE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "moonglow/moonglow"), 2, false);
		spriteSet(ParticleTypesRegistry.MUZZLE_FLASH_PARTICLE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "muzzle_flash/muzzle_flash"), 3, false);
		spriteSet(ParticleTypesRegistry.SMOKE_GRENADE_PARTICLE.get(), ResourceLocation.withDefaultNamespace("big_smoke"), 12, false);
		spriteSet(ParticleTypesRegistry.STARDUST_LEAVES_PARTICLE.get(), ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "stardust_leaves/stardust_leaves"), 2, false);
		spriteSet(ParticleTypesRegistry.TILTROS_PORTAL_PARTICLE.get(), ResourceLocation.withDefaultNamespace("generic"), 7, false);
	}
}