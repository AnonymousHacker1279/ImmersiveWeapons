package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.SuperHansEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

/**
 * Handles the respawning of Super Hans when tossing a Hans' Blessing into a fire block.
 */
@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {

	@Inject(method = "entityInside", at = @At("RETURN"))
	private void checkForSuperHansSpawn(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, CallbackInfo ci) {
		if (entity instanceof ItemEntity itemEntity && itemEntity.getItem().is(ItemRegistry.HANS_BLESSING.get())) {
			// Check if inside a Champion Tower structure
			if (level instanceof ServerLevel serverLevel) {
				Structure structure = serverLevel.structureManager().registryAccess().lookupOrThrow(Registries.STRUCTURE).getValue(SuperHansEntity.CHAMPION_TOWER_KEY);
				if (structure != null) {
					StructureStart structureStart = serverLevel.structureManager().getStructureWithPieceAt(pos, structure);
					if (structureStart.isValid()) {
						SuperHansEntity superHans = new SuperHansEntity(EntityRegistry.SUPER_HANS_ENTITY.get(), level);
						superHans.setPos(entity.position());
						superHans.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(pos), EntitySpawnReason.TRIGGERED, null);
						level.addFreshEntity(superHans);

						// Destroy the item
						itemEntity.discard();

						// Extinguish the fire
						level.removeBlock(pos, false);
					}
				}
			}
		}
	}
}