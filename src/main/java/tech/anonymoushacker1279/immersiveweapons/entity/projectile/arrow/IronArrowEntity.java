package tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class IronArrowEntity extends CustomArrowEntity {

	public IronArrowEntity(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public IronArrowEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.IRON_ARROW_ENTITY.get(), shooter, level);
	}

	public IronArrowEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.IRON_ARROW_ENTITY.get(), level, x, y, z);
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.IRON_ARROW.get();
	}
}