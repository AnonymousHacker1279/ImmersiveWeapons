package tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity.SmokeGrenadeEntityPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public class SmokeGrenadeArrowEntity extends CustomArrowEntity {

	private int color = 0;

	public SmokeGrenadeArrowEntity(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public SmokeGrenadeArrowEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY.get(), shooter, level);
	}

	public SmokeGrenadeArrowEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.SMOKE_GRENADE_ARROW_ENTITY.get(), level, x, y, z);
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!level.isClientSide) {
			PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPosition())),
					new SmokeGrenadeEntityPacketHandler(getX(), getY(), getZ(), color));
		}
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.SMOKE_GRENADE_ARROW.get();
	}
}