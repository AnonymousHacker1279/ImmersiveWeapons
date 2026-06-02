package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

@Mixin(LightningRodBlock.class)
public abstract class LightningRodBlockMixin {

	@Unique
	private static final Identifier ABANDONED_FACTORY_KEY = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "abandoned_factory");

	@Inject(method = "onLightningStrike", at = @At(value = "RETURN"))
	public void activateTeslaOre(BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
		// Check if inside abandoned factory
		if (level instanceof ServerLevel serverLevel) {
			Structure structure = serverLevel.structureManager()
					.registryAccess()
					.lookupOrThrow(Registries.STRUCTURE)
					.getValue(ABANDONED_FACTORY_KEY);

			if (structure != null) {
				StructureStart structureStart = serverLevel.structureManager().getStructureWithPieceAt(pos.below(), structure);
				if (structureStart.isValid()) {
					// Check up to 15 blocks below for a Dormant Tesla Ore block, if so, replace it with an active one
					for (int i = 0; i < 15; ++i) {
						if (level.getBlockState(pos.below(i)).getBlock() == BlockRegistry.DORMANT_TESLA_ORE.get()) {
							level.setBlockAndUpdate(pos.below(i), BlockRegistry.ACTIVE_TESLA_ORE.get().defaultBlockState());
							serverLevel.sendParticles(
									ParticleTypes.ELECTRIC_SPARK,
									pos.getX() + 0.5f,
									pos.getY() - i + 0.5f,
									pos.getZ() + 0.5f,
									12,
									0.5f,
									0.5f,
									0.5f,
									0.1f
							);
							break;
						}
					}
				}
			}
		}
	}
}