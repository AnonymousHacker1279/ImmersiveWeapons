package tech.anonymoushacker1279.immersiveweapons.item.tool.ventus;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class VentusSword extends SwordItem implements HitEffectUtils {

	public VentusSword() {
		super(IWToolMaterials.VENTUS.applySwordProperties(new Properties(), 3, -2.0f));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addVentusEffects(target);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}