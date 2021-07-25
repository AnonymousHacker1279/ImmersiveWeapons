package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.MOD)
public class CustomSpawnEggItem extends SpawnEggItem {

	private static final List<CustomSpawnEggItem> UNADDED_EGGS = new ArrayList<>(1);
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;

	/**
	 * Constructor for CustomSpawnEggItem.
	 * @param entityTypeSupplier a <code>RegistryObject</code> extending EntityType
	 * @param primaryColor the primary color
	 * @param secondaryColor the secondary color
	 * @param properties the <code>Properties</code> for the item
	 */
	public CustomSpawnEggItem(RegistryObject<? extends EntityType<?>> entityTypeSupplier, int primaryColor,
	                          int secondaryColor, Properties properties) {
		super(null, primaryColor, secondaryColor, properties);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier);
		UNADDED_EGGS.add(this);
	}

	/**
	 * Get the entity type.
	 * @param compoundNBT the <code>CompoundNBT</code> instance
	 * @return EntityType
	 */
	@Override
	public EntityType<?> getType(@Nullable CompoundTag compoundNBT) {
		return entityTypeSupplier.get();
	}

	/**
	 * Event handler for the FMLCommonSetupEvent
	 * @param event the <code>FMLCommonSetupEvent</code> instance
	 */
    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            try {
                Map<EntityType<?>, SpawnEggItem> spawnEggItemMap = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class,
                        null, "f_43201_");
                Minecraft minecraft = Minecraft.getInstance();
	            for (CustomSpawnEggItem spawnEggItem : UNADDED_EGGS) {
		            spawnEggItemMap.put(spawnEggItem.entityTypeSupplier.get(), spawnEggItem);
		            minecraft.getItemColors().register((color1, color2) -> spawnEggItem.getColor(color2), spawnEggItem);
	            }
            } catch (Exception e) {
                ImmersiveWeapons.LOGGER.warn("Unable to access SpawnEggItem.BY_ID: " + e);
            }
        });
    }
}