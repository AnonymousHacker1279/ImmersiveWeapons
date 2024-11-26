package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.function.Supplier;

public class DeferredRegistryHandler {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ImmersiveWeapons.MOD_ID);

	public static final Supplier<CreativeModeTab> IMMERSIVE_WEAPONS_TAB = CREATIVE_MODE_TABS.register("immersive_weapons_tab", () -> CreativeModeTab.builder()
			.icon(() -> ItemRegistry.TESLA_SWORD.get().getDefaultInstance())
			.title(Component.translatable("itemGroup.immersiveweapons.creative_tab"))
			.withSearchBar(65)
			.backgroundTexture(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/gui/container/creative_inventory_tab.png"))
			.build());

	/**
	 * Initialize deferred registers.
	 */
	public static void init(IEventBus modEventBus) {
		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for items");
		ItemRegistry.ITEMS.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for creative tabs");
		CREATIVE_MODE_TABS.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for blocks");
		BlockRegistry.BLOCKS.register(modEventBus);
		BlockItemRegistry.bootstrap();

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for entities");
		EntityRegistry.ENTITY_TYPES.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for sound events");
		SoundEventRegistry.SOUND_EVENTS.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for menus");
		MenuTypeRegistry.MENU_TYPES.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for recipe serializers");
		RecipeSerializerRegistry.RECIPE_SERIALIZER.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for recipe types");
		RecipeTypeRegistry.RECIPE_TYPES.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for particle types");
		ParticleTypesRegistry.PARTICLE_TYPES.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for loot modifiers");
		LootModifierRegistry.GLOBAL_LOOT_MODIFIER_SERIALIZER.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for block entities");
		BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for effects");
		EffectRegistry.EFFECTS.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for tree decorators");
		TreeDecoratorRegistry.TREE_DECORATORS.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for potions");
		PotionRegistry.POTIONS.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for number providers");
		NumberProviderRegistry.NUMBER_PROVIDERS.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for criterion triggers");
		CriterionTriggerRegistry.TRIGGER_TYPE.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for game events");
		GameEventRegistry.GAME_EVENTS.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for data component types");
		DataComponentTypeRegistry.DATA_COMPONENT_TYPES.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for armor materials");
		ArmorMaterialRegistry.ARMOR_MATERIALS.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for attributes");
		AttributeRegistry.ATTRIBUTES.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for accessory effect types");
		AccessoryEffectTypeRegistry.ACCESSORY_EFFECT_TYPES.register(modEventBus);

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for accessory effect scaling types");
		AccessoryEffectScalingTypeRegistry.ACCESSORY_EFFECT_SCALING_TYPES.register(modEventBus);
	}
}