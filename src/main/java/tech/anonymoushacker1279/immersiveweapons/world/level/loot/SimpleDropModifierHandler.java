package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public class SimpleDropModifierHandler extends LootModifier {

	public static final Supplier<MapCodec<SimpleDropModifierHandler>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.mapCodec(inst -> codecStart(inst).and(
							inst.group(
									ItemStack.CODEC.fieldOf("item").forGetter(m -> m.itemStack),
									TagKey.codec(Registries.ENTITY_TYPE).optionalFieldOf("mob_type").forGetter(m -> m.mobType),
									Codec.INT.fieldOf("min_drops").forGetter(m -> m.minDrops),
									Codec.INT.fieldOf("max_drops").forGetter(m -> m.maxDrops)
							))
					.apply(inst, SimpleDropModifierHandler::new)
			));

	private final ItemStack itemStack;
	private final Optional<TagKey<EntityType<?>>> mobType;
	private final int minDrops;
	private final int maxDrops;

	public SimpleDropModifierHandler(LootItemCondition[] itemConditions, ItemStack itemStack) {
		this(itemConditions, itemStack, Optional.empty(), 1, 1);
	}

	public SimpleDropModifierHandler(LootItemCondition[] itemConditions, ItemStack itemStack, int minDrops, int maxDrops) {
		this(itemConditions, itemStack, Optional.empty(), minDrops, maxDrops);
	}


	public SimpleDropModifierHandler(LootItemCondition[] itemConditions, ItemStack itemStack, Optional<TagKey<EntityType<?>>> type, int minDrops, int maxDrops) {
		super(itemConditions);
		this.itemStack = itemStack;
		this.mobType = type;
		this.minDrops = minDrops;
		this.maxDrops = maxDrops;

		if (!BuiltInRegistries.ITEM.containsValue(itemStack.getItem())) {
			throw new JsonParseException("item must exist in the registry");
		}
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		int dropCount = context.getRandom().nextInt(maxDrops - minDrops + 1) + minDrops;
		ItemStack stack = itemStack.copyWithCount(dropCount);

		if (mobType.isPresent()) {
			if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Mob mob && mob.getType().is(mobType.get())) {
				generatedLoot.add(stack);
			}
		} else {
			generatedLoot.add(stack);
		}

		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}