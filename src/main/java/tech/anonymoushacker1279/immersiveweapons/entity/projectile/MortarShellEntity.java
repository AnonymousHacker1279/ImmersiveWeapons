package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.block.MortarBlock;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class MortarShellEntity extends Projectile implements ItemSupplier {

	public MortarShellEntity(EntityType<? extends Projectile> entityType, Level level) {
		super(entityType, level);
	}

	private MortarShellEntity(Level level, BlockPos pos, double yOffset) {
		this(EntityRegistry.MORTAR_SHELL_ENTITY.get(), level);
		setPos(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
	}

	public static void create(Level level, BlockPos pos, double yOffset, BlockState state, @Nullable Player player) {
		if (!level.isClientSide) {
			MortarShellEntity mortarShellEntity = new MortarShellEntity(level, pos, yOffset);

			float rotationModifier = 0.0f;
			switch (state.getValue(MortarBlock.ROTATION)) {
				case 0 -> rotationModifier = 1.0f;
				case 1 -> rotationModifier = 0.75f;
				case 2 -> rotationModifier = 0.5f;
			}

			switch (state.getValue(MortarBlock.FACING)) {
				case NORTH -> mortarShellEntity.setDeltaMovement(0.0f, rotationModifier, 1.25f);
				case SOUTH -> mortarShellEntity.setDeltaMovement(0.0f, rotationModifier, -1.25f);
				case EAST -> mortarShellEntity.setDeltaMovement(-1.25f, rotationModifier, 0.0f);
				case WEST -> mortarShellEntity.setDeltaMovement(1.25f, rotationModifier, 0.0f);
			}

			if (player != null) {
				mortarShellEntity.setOwner(player);
			}

			level.addFreshEntity(mortarShellEntity);
		}
	}

	@Override
	public void tick() {
		super.tick();
		Vec3 deltaMovement = getDeltaMovement();

		// Check for collisions
		Vec3 currentPosition = position();
		Vec3 adjustedPosition = currentPosition.add(deltaMovement);
		HitResult rayTraceResult = level().clip(new ClipContext(currentPosition, adjustedPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		if (rayTraceResult.getType() != HitResult.Type.MISS) {
			adjustedPosition = rayTraceResult.getLocation();
		}

		while (isAlive()) {
			EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(level(),
					this, currentPosition, adjustedPosition,
					getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0D), this::canHitEntity);

			if (hitResult != null) {
				rayTraceResult = hitResult;
			}

			if (rayTraceResult != null && rayTraceResult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, rayTraceResult)) {
				onHit(rayTraceResult);
				hasImpulse = true;
			}

			if (hitResult == null) {
				break;
			}

			rayTraceResult = null;
		}

		deltaMovement = getDeltaMovement();
		double deltaX = deltaMovement.x;
		double deltaY = deltaMovement.y;
		double deltaZ = deltaMovement.z;
		double newX = getX() + deltaX;
		double newY = getY() + deltaY;
		double newZ = getZ() + deltaZ;

		float inertia = 0.99F;
		if (isInWater()) {
			for (int j = 0; j < 4; ++j) {
				level().addParticle(ParticleTypes.BUBBLE, newX - deltaX * 0.25D, newY - deltaY * 0.25D, newZ - deltaZ * 0.25D, deltaX, deltaY, deltaZ);
			}

			inertia = 0.6f;
		}

		setDeltaMovement(deltaMovement.scale(inertia));
		if (!isNoGravity()) {
			Vec3 newDeltaMovement = getDeltaMovement();
			setDeltaMovement(newDeltaMovement.x, newDeltaMovement.y - 0.0355f, newDeltaMovement.z);
		}

		setPos(newX, newY, newZ);
		// checkInsideBlocks(); TODO: check if still necessary
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		if (!level().isClientSide) {
			level().explode(this,
					IWDamageSources.mortar(level().registryAccess()),
					null,
					blockPosition().getX(), blockPosition().getY(), blockPosition().getZ(),
					4.0F, false, ExplosionInteraction.BLOCK);
		}

		discard();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
		return super.getAddEntityPacket(entity);
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(ItemRegistry.MORTAR_SHELL.get());
	}
}