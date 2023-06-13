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
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.function.Supplier;

public class MOADropModifierHandler extends LootModifier {

	public static final Supplier<Codec<MOADropModifierHandler>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.create(inst -> codecStart(inst).and(
							Codec.FLOAT.fieldOf("drop_chance").forGetter(m -> m.dropChance))
					.apply(inst, MOADropModifierHandler::new)
			));

	private final float dropChance;

	public MOADropModifierHandler(LootItemCondition[] conditionsIn, float dropChance) {
		super(conditionsIn);
		this.dropChance = dropChance;

		if (dropChance < 0.0f || dropChance > 1.0f) {
			throw new JsonParseException("drop_chance must be between 0.0 and 1.0");
		}
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (context.getRandom().nextFloat() <= dropChance) {
			// Add the Medal of Adequacy to the loot
			generatedLoot.add(new ItemStack(ItemRegistry.MEDAL_OF_ADEQUACY.get()));
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}