package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class AzulKeystoneFragmentInChestsLootModifierHandler extends LootModifier {

	private final int minQuantity;
	private final int maxQuantity;
	private final float rollChance;

	public AzulKeystoneFragmentInChestsLootModifierHandler(LootItemCondition[] conditionsIn, int minQuantity, int maxQuantity, float rollChance) {
		super(conditionsIn);
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.rollChance = rollChance;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (rollChance <= GeneralUtilities.getRandomNumber(0.0f, 1.00001f)) {
			int lootQuantity = GeneralUtilities.getRandomNumber(minQuantity, maxQuantity + 1);

			generatedLoot.add(new ItemStack(DeferredRegistryHandler.AZUL_KEYSTONE_FRAGMENT.get(), lootQuantity));
		}
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<AzulKeystoneFragmentInChestsLootModifierHandler> {
		@Override
		public AzulKeystoneFragmentInChestsLootModifierHandler read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
			int minQuantity = GsonHelper.getAsInt(object, "min_quantity", 0);
			if (minQuantity < 0) {
				throw new JsonParseException("Unable to set a minimal quantity to a number lower than 0");
			}
			int maxQuantity = GsonHelper.getAsInt(object, "max_quantity", 2);
			if (maxQuantity < 0) {
				throw new JsonParseException("Unable to set a maximum quantity to a number lower than 0");
			} else if (maxQuantity < minQuantity) {
				throw new JsonParseException("Unable to set a maximum quantity to a number lower than the minimum quantity");
			}
			float rollChance = GsonHelper.getAsFloat(object, "chance_to_roll", 0.3f);
			if (rollChance < 0) {
				throw new JsonParseException("Unable to set a roll chance to a number lower than 0");
			} else if (rollChance > 1) {
				throw new JsonParseException("Unable to set a roll chance to a number higher than 1");
			}

			return new AzulKeystoneFragmentInChestsLootModifierHandler(conditions, minQuantity, maxQuantity, rollChance);
		}

		@Override
		public JsonObject write(AzulKeystoneFragmentInChestsLootModifierHandler instance) {
			JsonObject obj = makeConditions(instance.conditions);
			obj.addProperty("min_quantity", instance.minQuantity);
			obj.addProperty("max_quantity", instance.maxQuantity);
			obj.addProperty("roll_chance", instance.rollChance);
			return obj;
		}
	}
}