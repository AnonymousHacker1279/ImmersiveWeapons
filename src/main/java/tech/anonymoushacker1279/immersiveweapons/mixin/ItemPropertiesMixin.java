package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

@Mixin(Item.Properties.class)
public class ItemPropertiesMixin {

	/**
	 * Allow wolf armor to be equipped on Star Wolves.
	 *
	 * @param allowedEntities the original allowed entities
	 * @return the modified allowed entities
	 */
	@ModifyArg(method = "wolfArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/equipment/Equippable$Builder;setAllowedEntities(Lnet/minecraft/core/HolderSet;)Lnet/minecraft/world/item/equipment/Equippable$Builder;"))
	public HolderSet<EntityType<?>> modifyAllowedEntities(HolderSet<EntityType<?>> allowedEntities) {
		return HolderSet.direct(EntityType.WOLF.builtInRegistryHolder(), EntityRegistry.STAR_WOLF_ENTITY.getDelegate());
	}
}