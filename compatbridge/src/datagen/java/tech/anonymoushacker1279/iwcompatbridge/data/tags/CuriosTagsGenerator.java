package tech.anonymoushacker1279.iwcompatbridge.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;

import java.util.concurrent.CompletableFuture;

public class CuriosTagsGenerator extends TagsProvider<Item> {

	public static final TagKey<Item> BELT = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("curios", "belt"));
	public static final TagKey<Item> BODY = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("curios", "body"));
	public static final TagKey<Item> BRACELET = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("curios", "bracelet"));
	public static final TagKey<Item> CHARM = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("curios", "charm"));
	public static final TagKey<Item> HANDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("curios", "hands"));
	public static final TagKey<Item> HEAD = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("curios", "head"));
	public static final TagKey<Item> NECKLACE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("curios", "necklace"));
	public static final TagKey<Item> RING = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("curios", "ring"));
	public static final TagKey<Item> SPIRIT = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("curios", "spirit"));

	public CuriosTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, Registries.ITEM, lookupProvider, IWCompatBridge.MOD_ID);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void addTags(Provider provider) {
		tag(BELT).add(
				ItemRegistry.SATCHEL.key(),
				ItemRegistry.HOLY_MANTLE.key(),
				ItemRegistry.VENSTRAL_JAR.key());

		tag(BODY).add(
				ItemRegistry.POWDER_HORN.key(),
				ItemRegistry.BLOATED_HEART.key(),
				ItemRegistry.NETHERITE_SHIELD.key(),
				ItemRegistry.SUPER_BLANKET_CAPE.key());

		tag(BRACELET).add(ItemRegistry.AGILITY_BRACELET.key());

		tag(CHARM).add(
				ItemRegistry.BERSERKERS_AMULET.key(),
				ItemRegistry.BLADEMASTER_EMBLEM.key(),
				ItemRegistry.DEPTH_CHARM.key(),
				ItemRegistry.REINFORCED_DEPTH_CHARM.key(),
				ItemRegistry.ANCIENT_SCROLL.key(),
				ItemRegistry.MEDAL_OF_HONOR.key(),
				ItemRegistry.MEDAL_OF_DISHONOR.key());

		tag(HANDS).add(
				ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.key(),
				ItemRegistry.IRON_FIST.key(),
				ItemRegistry.GLOVE_OF_RAPID_SWINGING.key(),
				ItemRegistry.HAND_OF_DOOM.key());

		tag(HEAD).add(
				ItemRegistry.GOGGLES.key(),
				ItemRegistry.LAVA_GOGGLES.key(),
				ItemRegistry.NIGHT_VISION_GOGGLES.key(),
				ItemRegistry.BLOODY_CLOTH.key());

		tag(NECKLACE).add(
				ItemRegistry.DEADEYE_PENDANT.key(),
				ItemRegistry.MEDAL_OF_ADEQUACY.key(),
				ItemRegistry.INSOMNIA_AMULET.key());

		tag(RING).add(
				ItemRegistry.COPPER_RING.key(),
				ItemRegistry.IRON_RING.key(),
				ItemRegistry.COBALT_RING.key(),
				ItemRegistry.GOLDEN_RING.key(),
				ItemRegistry.AMETHYST_RING.key(),
				ItemRegistry.EMERALD_RING.key(),
				ItemRegistry.DIAMOND_RING.key(),
				ItemRegistry.NETHERITE_RING.key(),
				ItemRegistry.DEATH_GEM_RING.key());

		tag(SPIRIT).add(
				ItemRegistry.HANS_BLESSING.key(),
				ItemRegistry.CELESTIAL_SPIRIT.key(),
				ItemRegistry.VOID_BLESSING.key());
	}
}