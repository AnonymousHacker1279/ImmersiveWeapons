package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.function.Supplier;

public class AzulKeystoneFragmentInChestsLootModifierHandler extends LootModifier {

	public static final Supplier<Codec<AzulKeystoneFragmentInChestsLootModifierHandler>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).and(
			inst.group(
					Codec.INT.fieldOf("min_quantity").forGetter(m -> m.minQuantity),
					Codec.INT.fieldOf("max_quantity").forGetter(m -> m.maxQuantity),
					Codec.FLOAT.fieldOf("roll_chance").forGetter(m -> m.rollChance)
			)).apply(inst, AzulKeystoneFragmentInChestsLootModifierHandler::new)
	));

	private final int minQuantity;
	private final int maxQuantity;
	private final float rollChance;

	public AzulKeystoneFragmentInChestsLootModifierHandler(LootItemCondition[] conditionsIn, int minQuantity, int maxQuantity, float rollChance) {
		super(conditionsIn);
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.rollChance = rollChance;

		// Validate input values
		if (minQuantity < 0) {
			throw new JsonParseException("min_quantity must be >= 0");
		}

		if (maxQuantity < 0) {
			throw new JsonParseException("max_quantity must be >= 0");
		} else if (maxQuantity < minQuantity) {
			throw new JsonParseException("max_quantity must be >= min_quantity");
		}

		if (rollChance < 0.0f) {
			throw new JsonParseException("roll_chance must be >= 0.0");
		} else if (rollChance > 1.0f) {
			throw new JsonParseException("roll_chance must be <= 1.0");
		}
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (rollChance <= GeneralUtilities.getRandomNumber(0.0f, 1.00001f)) {
			int lootQuantity = GeneralUtilities.getRandomNumber(minQuantity, maxQuantity + 1);

			generatedLoot.add(new ItemStack(DeferredRegistryHandler.AZUL_KEYSTONE_FRAGMENT.get(), lootQuantity));
		}
		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}