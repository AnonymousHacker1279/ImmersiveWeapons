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
				ItemRegistry.SATCHEL.getKey(),
				ItemRegistry.HOLY_MANTLE.getKey(),
				ItemRegistry.VENSTRAL_JAR.getKey());

		tag(BODY).add(
				ItemRegistry.POWDER_HORN.getKey(),
				ItemRegistry.BLOATED_HEART.getKey(),
				ItemRegistry.NETHERITE_SHIELD.getKey(),
				ItemRegistry.SUPER_BLANKET_CAPE.getKey());

		tag(BRACELET).add(ItemRegistry.AGILITY_BRACELET.getKey());

		tag(CHARM).add(
				ItemRegistry.BERSERKERS_AMULET.getKey(),
				ItemRegistry.BLADEMASTER_EMBLEM.getKey(),
				ItemRegistry.DEPTH_CHARM.getKey(),
				ItemRegistry.REINFORCED_DEPTH_CHARM.getKey(),
				ItemRegistry.ANCIENT_SCROLL.getKey(),
				ItemRegistry.MEDAL_OF_HONOR.getKey(),
				ItemRegistry.MEDAL_OF_DISHONOR.getKey());

		tag(HANDS).add(
				ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.getKey(),
				ItemRegistry.IRON_FIST.getKey(),
				ItemRegistry.GLOVE_OF_RAPID_SWINGING.getKey(),
				ItemRegistry.HAND_OF_DOOM.getKey());

		tag(HEAD).add(
				ItemRegistry.GOGGLES.getKey(),
				ItemRegistry.LAVA_GOGGLES.getKey(),
				ItemRegistry.NIGHT_VISION_GOGGLES.getKey(),
				ItemRegistry.BLOODY_CLOTH.getKey());

		tag(NECKLACE).add(
				ItemRegistry.DEADEYE_PENDANT.getKey(),
				ItemRegistry.MEDAL_OF_ADEQUACY.getKey(),
				ItemRegistry.INSOMNIA_AMULET.getKey());

		tag(RING).add(
				ItemRegistry.COPPER_RING.getKey(),
				ItemRegistry.IRON_RING.getKey(),
				ItemRegistry.COBALT_RING.getKey(),
				ItemRegistry.GOLDEN_RING.getKey(),
				ItemRegistry.AMETHYST_RING.getKey(),
				ItemRegistry.EMERALD_RING.getKey(),
				ItemRegistry.DIAMOND_RING.getKey(),
				ItemRegistry.NETHERITE_RING.getKey(),
				ItemRegistry.DEATH_GEM_RING.getKey());

		tag(SPIRIT).add(
				ItemRegistry.HANS_BLESSING.getKey(),
				ItemRegistry.CELESTIAL_SPIRIT.getKey(),
				ItemRegistry.VOID_BLESSING.getKey());
	}
}