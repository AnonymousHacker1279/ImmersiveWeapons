package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class CustomPotion extends Potion {

	public CustomPotion(MobEffectInstance mobEffectInstance) {
		super(mobEffectInstance);
	}

	@Override
	public boolean allowedInCreativeTab(Item item, CreativeModeTab tab, boolean isDefaultTab) {
		return tab == ItemRegistry.ITEM_GROUP;
	}

	@Override
	public String getName(String prefix) {
		prefix = prefix.replace("item.minecraft.", "")
				.replace(".effect", "");
		return super.getName("potion.immersiveweapons." + prefix);
	}
}