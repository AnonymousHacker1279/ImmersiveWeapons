package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class DeferredRegistryHandler {

	/**
	 * Initialize deferred registers.
	 */
	public static void init() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for items");
		ItemRegistry.ITEMS.register(modEventBus);

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

		ImmersiveWeapons.LOGGER.info("Initializing deferred registry for enchantments");
		EnchantmentRegistry.ENCHANTMENTS.register(modEventBus);
	}
}