package com.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CustomArrowEntity extends AbstractArrowEntity {

	private final Item referenceItem;

    @SuppressWarnings("unchecked")
    public CustomArrowEntity(EntityType<?> type, World world) {
        super((EntityType<? extends AbstractArrowEntity>) type, world);
        this.referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
    }
    
    public CustomArrowEntity(LivingEntity shooter, World world, Item referenceItemIn) {
        super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), shooter, world);
        this.referenceItem = referenceItemIn;
    }

    public CustomArrowEntity(World worldIn, double x, double y, double z) {
    	super(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), x, y, z, worldIn);
		this.referenceItem = DeferredRegistryHandler.COPPER_ARROW.get();
     }

	@Override
    public ItemStack getArrowStack() {
        return new ItemStack(this.referenceItem);
    }
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
