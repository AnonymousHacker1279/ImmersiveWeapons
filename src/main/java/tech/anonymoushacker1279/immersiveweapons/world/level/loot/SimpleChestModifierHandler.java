package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
									ItemStack.CODEC.fieldOf("item").forGetter(m -> m.itemStack),
									Codec.INT.fieldOf("max_enchant_levels").forGetter(m -> m.maxEnchantLevels),
									Codec.BOOL.fieldOf("allow_treasure").forGetter(m -> m.allowTreasure)))
					.apply(inst, SimpleChestModifierHandler::new)
			));

	private final int minQuantity;
	private final int maxQuantity;
	private final float rollChance;
	private final ItemStack itemStack;
	private final int maxEnchantLevels;
	private final boolean allowTreasure;

	/**
	 * Create a simple chest modifier. This will add the specified item to the loot pool if the conditions are met.
	 *
	 * @param itemConditions The conditions that must be met for the item to be added to the loot pool
	 * @param minQuantity    The minimum quantity of the item to add to the loot pool
	 * @param maxQuantity    The maximum quantity of the item to add to the loot pool
	 * @param rollChance     The chance that the item will be added to the loot pool
	 * @param itemStack      The item to add to the loot pool
	 */
	public SimpleChestModifierHandler(LootItemCondition[] itemConditions, int minQuantity, int maxQuantity, float rollChance, ItemStack itemStack) {
		this(itemConditions, minQuantity, maxQuantity, rollChance, itemStack, 0, false);
	}

	/**
	 * Create a simple chest modifier. This will add the specified item to the loot pool if the conditions are met.
	 * The item will be randomly enchanted given the specified max enchantment levels.
	 *
	 * @param itemConditions   The conditions that must be met for the item to be added to the loot pool
	 * @param minQuantity      The minimum quantity of the item to add to the loot pool
	 * @param maxQuantity      The maximum quantity of the item to add to the loot pool
	 * @param rollChance       The chance that the item will be added to the loot pool
	 * @param itemStack        The item to add to the loot pool
	 * @param maxEnchantLevels The maximum number of enchantment levels to apply to the item
	 * @param allowTreasure    Whether to allow treasure enchantments
	 */
	public SimpleChestModifierHandler(LootItemCondition[] itemConditions, int minQuantity, int maxQuantity, float rollChance, ItemStack itemStack, int maxEnchantLevels, boolean allowTreasure) {
		super(itemConditions);
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.rollChance = rollChance;
		this.itemStack = itemStack;
		this.maxEnchantLevels = maxEnchantLevels;
		this.allowTreasure = allowTreasure;

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

		if (maxEnchantLevels < 0) {
			throw new JsonParseException("max_enchant_levels must be >= 0");
		}

		if (maxEnchantLevels > 0 && maxQuantity > 1) {
			throw new JsonParseException("max_enchant_levels can only be used with a max_quantity of 1");
		}
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (context.getRandom().nextFloat() <= rollChance) {
			int lootQuantity = context.getRandom().nextIntBetweenInclusive(minQuantity, maxQuantity);
			ItemStack stack = itemStack.copyWithCount(lootQuantity);

			if (maxEnchantLevels > 0) {
				EnchantmentHelper.enchantItem(RandomSource.create(), stack, maxEnchantLevels, allowTreasure);
			}

			generatedLoot.add(stack);
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}