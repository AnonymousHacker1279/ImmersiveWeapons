package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LogShardsLootModifierHandler extends LootModifier {

	public static final Supplier<Codec<LogShardsLootModifierHandler>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).and(
			inst.group(
					TagKey.codec(Registries.ITEM).fieldOf("blockTag").forGetter(m -> m.tag),
					Codec.INT.fieldOf("minShards").forGetter(m -> m.minShards),
					Codec.INT.fieldOf("maxShards").forGetter(m -> m.maxShards),
					ItemStack.CODEC.fieldOf("replacement").forGetter(m -> m.replacement)
			)).apply(inst, LogShardsLootModifierHandler::new)
	));

	private final int minShards;
	private final int maxShards;
	private final TagKey<Item> tag;
	private final ItemStack replacement;

	/**
	 * Create a new log shards modifier.
	 *
	 * @param itemConditions the <code>LootItemCondition</code>s
	 * @param tag            the tag of the item to replace
	 * @param minShards      the minimum number of shards to drop
	 * @param maxShards      the maximum number of shards to drop
	 * @param replacement    the returned item
	 */
	public LogShardsLootModifierHandler(LootItemCondition[] itemConditions, TagKey<Item> tag, int minShards, int maxShards, ItemStack replacement) {
		super(itemConditions);
		this.tag = tag;
		this.minShards = minShards;
		this.maxShards = maxShards;
		this.replacement = replacement;
	}

	/**
	 * Apply loot table modifications.
	 *
	 * @param generatedLoot the <code>List</code> of generated items
	 * @param context       the <code>LootContext</code> instance
	 * @return List extending ItemStack
	 */
	@Override
	public @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		int shardCount = 0;
		for (ItemStack stack : generatedLoot) {
			// Each wooden log stack can generate shards
			if (stack.is(tag)) {
				shardCount += stack.getCount() * context.getRandom().nextIntBetweenInclusive(minShards, maxShards);
			}
		}

		if (shardCount >= 1) {
			replacement.setCount(shardCount);
			generatedLoot.add(replacement);
			generatedLoot.remove(0); // The original item shouldn't drop, so remove it from the loot list
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}