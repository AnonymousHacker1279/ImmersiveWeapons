package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class LogShardsLootModifierHandler extends LootModifier {

	private final int numShardsToConvert;
	private final String blockTag;
	private final Item itemReward;

	/**
	 * Constructor for LogShardsLootModifierHandler.
	 *
	 * @param conditionsIn the <code>ILootCondition</code>s
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

	public static class Serializer extends GlobalLootModifierSerializer<LogShardsLootModifierHandler> {

		/**
		 * Read from JSON.
		 *
		 * @param name         the <code>ResourceLocation</code> to read from
		 * @param object       the <code>JsonObject</code> instance
		 * @param conditionsIn the <code>ILootCondition</code>s
		 * @return LogShardsLootModifierHandler
		 */
		@Override
		public LogShardsLootModifierHandler read(ResourceLocation name, JsonObject object, LootItemCondition[] conditionsIn) {
			String blockTag = GsonHelper.getAsString(object, "blockTag");
			int numShards = GsonHelper.getAsInt(object, "numShards");
			Item replacementItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object, "replacement")));
			return new LogShardsLootModifierHandler(conditionsIn, blockTag, numShards, replacementItem);
		}

		/**
		 * Write to JSON.
		 *
		 * @param instance the <code>LogShardsLootModifierHandler</code> instance
		 * @return JsonObject
		 */
		@Override
		public JsonObject write(LogShardsLootModifierHandler instance) {
			return null;
		}
	}
}