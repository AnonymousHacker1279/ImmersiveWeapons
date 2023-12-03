package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Fix Slimes not spawning in the correct size, as finalizeSpawn does not respect sizes in NBT data. See
 * <a href="https://bugs.mojang.com/browse/MC-87578">MC-87578</a>.
 * <p>
 * Also fixes health/movement speed/attack damage attributes being reset when setting the size, when the entity
 * is spawned from a structure.
 */
@Mixin(Slime.class)
public class SlimeMixin {

	@Unique
	@Nullable
	MobSpawnType immersiveWeapons$earlySpawnType;

	/**
	 * Modify the size of the slime when it spawns by modifying the randomly-generated size constant if the NBT data
	 * contains a size tag.
	 * <p>
	 * Also stores the spawn type reason for later use. This is set normally within the super method of
	 * {@link Slime#finalizeSpawn(ServerLevelAccessor, DifficultyInstance, MobSpawnType, SpawnGroupData, CompoundTag)},
	 * but the modified {@link Slime#setSize(int, boolean)} method is called before the super method, so the spawn type
	 * is not yet available.
	 *
	 * @param original    the original size
	 * @param pLevel      the <code>ServerLevelAccessor</code> instance
	 * @param pDifficulty the <code>DifficultyInstance</code> instance
	 * @param pReason     the <code>MobSpawnType</code> instance
	 * @param pSpawnData  the <code>SpawnGroupData</code> instance
	 * @param pDataTag    the <code>CompoundTag</code> instance
	 * @return int
	 */
	@ModifyVariable(method = "finalizeSpawn", at = @At("STORE"), ordinal = 1)
	private int modifySize(int original, ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
		if (pDataTag != null && pDataTag.contains("Size")) {
			return pDataTag.getInt("Size") + 1;
		}

		immersiveWeapons$earlySpawnType = pReason;

		return original;
	}

	/**
	 * If spawning from a structure, do not set any attributes as they should already be available from the entity's NBT
	 * which is loaded before the entity is spawned.
	 *
	 * @param instance  the <code>AttributeInstance</code> instance
	 * @param original  the original value
	 * @param operation the <code>Operation</code> instance
	 */
	@WrapOperation(method = "setSize", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;setBaseValue(D)V"))
	@SuppressWarnings("unused")
	private void setSize(AttributeInstance instance, double original, Operation<Double> operation) {
		if (immersiveWeapons$earlySpawnType != null && immersiveWeapons$earlySpawnType != MobSpawnType.STRUCTURE) {
			operation.call(instance, original);
			immersiveWeapons$earlySpawnType = null;
		}
	}
}