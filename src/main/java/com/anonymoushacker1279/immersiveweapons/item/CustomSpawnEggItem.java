package com.anonymoushacker1279.immersiveweapons.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.MOD)
public class CustomSpawnEggItem extends SpawnEggItem {

	protected static final List<CustomSpawnEggItem> UNADDED_EGGS = new ArrayList<>();
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;

	public CustomSpawnEggItem(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, final int primaryColor,
	                          final int secondaryColor, final Properties properties) {
		super(null, primaryColor, secondaryColor, properties);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier);
		UNADDED_EGGS.add(this);
	}

	@Override
	public EntityType<?> getType(@Nullable final CompoundNBT p_208076_1_) {
		return entityTypeSupplier.get();
	}
	
    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            try {
                Map<EntityType<?>, SpawnEggItem> eggs = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class,
                        null, "field_195987_b");
                for (CustomSpawnEggItem egg : UNADDED_EGGS)
                    eggs.put(egg.entityTypeSupplier.get(), egg);
            } catch (Exception e) {
                ImmersiveWeapons.LOGGER.warn("Unable to access SpawnEggItem.BY_ID");
            }
        });
    }
}