package tech.anonymoushacker1279.immersiveweapons.item.bow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity;

public class DragonBreathBow extends BowItem {

	public DragonBreathBow(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractArrow customArrow(AbstractArrow abstractArrow, ItemStack projectileStack, ItemStack weaponStack) {
		if (abstractArrow instanceof CustomArrowEntity customArrowEntity) {
			customArrowEntity.isExplosive = true;

			return customArrowEntity;
		} else if (abstractArrow.getOwner() instanceof LivingEntity owner) {
			// Create a new CustomArrowEntity
			CustomArrowEntity customArrowEntity = new CustomArrowEntity(EntityType.ARROW, owner, abstractArrow.level(), weaponStack);
			customArrowEntity.isExplosive = true;
			customArrowEntity.gravityModifier = 0.05d;

			return customArrowEntity;
		}

		return abstractArrow;
	}
}