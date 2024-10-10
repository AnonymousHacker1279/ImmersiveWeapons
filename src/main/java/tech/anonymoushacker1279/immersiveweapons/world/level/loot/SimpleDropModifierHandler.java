package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonParseException;
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
									TagKey.codec(Registries.ENTITY_TYPE).optionalFieldOf("mob_type").forGetter(m -> m.mobType)
							))
					.apply(inst, SimpleDropModifierHandler::new)
			));

	private final ItemStack itemStack;
	private final Optional<TagKey<EntityType<?>>> mobType;

	public SimpleDropModifierHandler(LootItemCondition[] itemConditions, ItemStack itemStack) {
		this(itemConditions, itemStack, Optional.empty());
	}

	public SimpleDropModifierHandler(LootItemCondition[] itemConditions, ItemStack itemStack, Optional<TagKey<EntityType<?>>> type) {
		super(itemConditions);
		this.itemStack = itemStack;
		this.mobType = type;

		if (!BuiltInRegistries.ITEM.containsValue(itemStack.getItem())) {
			throw new JsonParseException("item must exist in the registry");
		}
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (mobType.isPresent()) {
			if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Mob mob && mob.getType().is(mobType.get())) {
				generatedLoot.add(itemStack);
			}
		} else {
			generatedLoot.add(itemStack);
		}

		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}