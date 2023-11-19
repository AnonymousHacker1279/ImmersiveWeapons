package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SimpleChestModifierHandler extends LootModifier {

	public static final Supplier<Codec<SimpleChestModifierHandler>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.create(inst -> codecStart(inst).and(
							inst.group(
									Codec.INT.fieldOf("min_quantity").forGetter(m -> m.minQuantity),
									Codec.INT.fieldOf("max_quantity").forGetter(m -> m.maxQuantity),
									Codec.FLOAT.fieldOf("roll_chance").forGetter(m -> m.rollChance),
									ItemStack.CODEC.fieldOf("item").forGetter(m -> m.itemStack)))
					.apply(inst, SimpleChestModifierHandler::new)
			));

	private final int minQuantity;
	private final int maxQuantity;
	private final float rollChance;
	private final ItemStack itemStack;

	public SimpleChestModifierHandler(LootItemCondition[] conditionsIn, int minQuantity, int maxQuantity, float rollChance, ItemStack itemStack) {
		super(conditionsIn);
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.rollChance = rollChance;
		this.itemStack = itemStack;

		// Validate input values
		if (minQuantity < 0) {
			throw new JsonParseException("min_quantity must be >= 0");
		}

		if (maxQuantity < 0) {
			throw new JsonParseException("max_quantity must be >= 0");
		} else if (maxQuantity < minQuantity) {
			throw new JsonParseException("max_quantity must be >= min_quantity");
		}

		if (rollChance < 0.0f || rollChance > 1.0f) {
			throw new JsonParseException("roll_chance must be between 0.0 and 1.0");
		}

		if (!BuiltInRegistries.ITEM.containsValue(itemStack.getItem())) {
			throw new JsonParseException("item must exist in the registry");
		}
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (context.getRandom().nextFloat() <= rollChance) {
			int lootQuantity = context.getRandom().nextIntBetweenInclusive(minQuantity, maxQuantity);

			generatedLoot.add(itemStack.copyWithCount(lootQuantity));
		}
		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}