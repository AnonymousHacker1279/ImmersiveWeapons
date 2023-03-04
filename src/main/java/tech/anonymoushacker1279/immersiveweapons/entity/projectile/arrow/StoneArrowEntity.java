package tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class StoneArrowEntity extends CustomArrowEntity {

	public StoneArrowEntity(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public StoneArrowEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.STONE_ARROW_ENTITY.get(), shooter, level);
	}

	public StoneArrowEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.STONE_ARROW_ENTITY.get(), level, x, y, z);
	}

	@Override
	protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
		return new Vec3(x, y, z)
				.normalize()
				.add(random.triangle(0d, 0.0175d * GeneralUtilities.getRandomNumber(2.6f, 4.3f)),
						random.triangle(0d, 0.0175d * GeneralUtilities.getRandomNumber(2.6f, 4.3f)),
						random.triangle(0d, 0.0175d * GeneralUtilities.getRandomNumber(2.6f, 4.3f)))
				.scale(velocity);
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.STONE_ARROW.get();
	}
}