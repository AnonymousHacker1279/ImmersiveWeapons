package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.function.Supplier;

public class CopperRingDropLootModifierHandler extends LootModifier {

	public static final Supplier<Codec<CopperRingDropLootModifierHandler>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.create(inst -> codecStart(inst).and(
							inst.group(
									Codec.FLOAT.fieldOf("drop_chance").forGetter(m -> m.dropChance),
									Codec.FLOAT.fieldOf("looting_multiplier").forGetter(m -> m.lootingMultiplier)))
					.apply(inst, CopperRingDropLootModifierHandler::new)
			));

	private final float dropChance;
	private final float lootingMultiplier;

	public CopperRingDropLootModifierHandler(LootItemCondition[] conditionsIn, float dropChance, float lootingMultiplier) {
		super(conditionsIn);
		this.dropChance = dropChance;
		this.lootingMultiplier = lootingMultiplier;

		if (dropChance < 0.0f || dropChance > 1.0f) {
			throw new JsonParseException("drop_chance must be between 0.0 and 1.0");
		}
		if (lootingMultiplier < 0.0f) {
			throw new JsonParseException("looting_multiplier must be greater than or equal to 0.0");
		}
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		// Check if the entity is undead
		if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Mob mob) {
			ImmersiveWeapons.LOGGER.debug("Entity type: " + mob.getMobType());
		}
		if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Mob mob && mob.getMobType() == MobType.UNDEAD) {
			// Add looting multiplier
			float dropChance = this.dropChance + (context.getLootingModifier() * this.lootingMultiplier);
			ImmersiveWeapons.LOGGER.debug("Drop chance: " + dropChance);
			if (context.getRandom().nextFloat() <= dropChance) {
				// Add the copper ring to the loot drop
				generatedLoot.add(new ItemStack(ItemRegistry.COPPER_RING.get()));
				ImmersiveWeapons.LOGGER.debug("Copper ring added to loot");
			}
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}