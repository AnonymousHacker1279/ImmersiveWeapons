package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.function.Supplier;

public class LogShardsLootModifierHandler extends LootModifier {

	public static final Supplier<Codec<LogShardsLootModifierHandler>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).and(
			inst.group(
					Codec.STRING.fieldOf("blockTag").forGetter(m -> m.blockTag),
					Codec.INT.fieldOf("numShards").forGetter(m -> m.numShardsToConvert),
					ForgeRegistries.ITEMS.getCodec().fieldOf("replacement").forGetter(m -> m.itemReward)
			)).apply(inst, LogShardsLootModifierHandler::new)
	));

	private final int numShardsToConvert;
	private final String blockTag;
	private final Item itemReward;

	/**
	 * Constructor for LogShardsLootModifierHandler.
	 *
	 * @param conditionsIn the <code>LootItemCondition</code>s
	 * @param tag          the block tag string
	 * @param numShards    the number of shards
	 * @param reward       the returned item
	 */
	LogShardsLootModifierHandler(LootItemCondition[] conditionsIn, String tag, int numShards, Item reward) {
		super(conditionsIn);
		blockTag = tag;
		numShardsToConvert = numShards;
		itemReward = reward;
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
		int numShards = 0;
		for (ItemStack stack : generatedLoot) {
			if (stack.is(ItemTags.create(new ResourceLocation(blockTag)))) {
				numShards += stack.getCount() + GeneralUtilities.getRandomNumber(2, 5);
			}
		}
		if (numShards >= numShardsToConvert) {
			generatedLoot.add(new ItemStack(itemReward, (numShards / numShardsToConvert)));
			numShards = numShards % numShardsToConvert;
			if (numShards > 0)
				generatedLoot.add(new ItemStack(itemReward, numShards));
			generatedLoot.remove(0); // The original item shouldn't drop, so remove it from the loot list
		}
		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}