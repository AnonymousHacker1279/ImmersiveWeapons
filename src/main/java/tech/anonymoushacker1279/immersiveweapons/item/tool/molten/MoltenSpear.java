package tech.anonymoushacker1279.immersiveweapons.item.tool.molten;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class MoltenSpear extends Item implements HitEffectUtils {

	public MoltenSpear(Properties properties) {
		super(properties.spear(IWToolMaterials.MOLTEN,
						1.2F,
						1.25F,
						0.35F,
						2.45F,
						6.95F,
						5.45F,
						5.05F,
						8.25F,
						4.55F)
				.fireResistant());
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addMoltenEffects(target, attacker);
	}
}